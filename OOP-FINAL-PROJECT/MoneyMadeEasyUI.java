import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MoneyMadeEasyUI {
    private static final String[] CATEGORIES = {"Food", "Transport", "Bills", "Savings", "Other"};
    private static final DateTimeFormatter DISPLAY_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final JFrame frame = new JFrame("Money Made Easy");
    private final JLabel profileLabel = new JLabel("No profile selected");
    private final JLabel balanceLabel = new JLabel("0.00");
    private final JLabel incomeLabel = new JLabel("0.00");
    private final JLabel expenseLabel = new JLabel("0.00");
    private final JLabel monthLabel = new JLabel("0.00 income / 0.00 expense");
    private final DefaultTableModel transactionModel = new DefaultTableModel(
            new Object[]{"Date", "Type", "Amount", "Category", "Description"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };

    private String profile = "Student";
    private Ledger ledger;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MoneyMadeEasyUI().show());
    }

    public void show() {
        ledger = new Ledger(Paths.get("finance_" + profile.toLowerCase() + "_.txt"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(960, 620));
        frame.setSize(1180, 760);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBackground(new Color(24, 56, 47));
        sidebar.setLayout(new BorderLayout());
        sidebar.setBorder(new EmptyBorder(26, 18, 18, 18));

        JPanel brand = new JPanel();
        brand.setOpaque(false);
        brand.setLayout(new BoxLayout(brand, BoxLayout.Y_AXIS));
        JLabel brandName = new JLabel("MONEY MADE");
        brandName.setForeground(Color.WHITE);
        brandName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        JLabel brandName2 = new JLabel("EASY");
        brandName2.setForeground(new Color(137, 206, 174));
        brandName2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        brand.add(brandName);
        brand.add(brandName2);
        sidebar.add(brand, BorderLayout.NORTH);

        JPanel nav = new JPanel();
        nav.setOpaque(false);
        nav.setBorder(new EmptyBorder(38, 0, 0, 0));
        nav.setLayout(new BoxLayout(nav, BoxLayout.Y_AXIS));

        JLabel workspace = new JLabel("WORKSPACE");
        workspace.setForeground(new Color(158, 190, 178));
        workspace.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
        workspace.setAlignmentX(Component.LEFT_ALIGNMENT);
        nav.add(workspace);
        nav.add(Box.createVerticalStrut(12));

        JButton overview = sidebarButton("Overview", true);
        JButton summary = sidebarButton("Spending summary", false);
        JButton switchProfile = sidebarButton("Switch profile", false);
        overview.addActionListener(e -> {
            JTable table = findTable();
            if (table != null) table.requestFocusInWindow();
        });
        summary.addActionListener(e -> showSummary());
        switchProfile.addActionListener(e -> switchProfile(findTable()));
        nav.add(overview);
        nav.add(Box.createVerticalStrut(6));
        nav.add(summary);
        nav.add(Box.createVerticalStrut(6));
        nav.add(switchProfile);
        sidebar.add(nav, BorderLayout.CENTER);

        JPanel sideProfile = new JPanel();
        sideProfile.setOpaque(false);
        sideProfile.setBorder(new EmptyBorder(16, 0, 0, 0));
        sideProfile.setLayout(new BoxLayout(sideProfile, BoxLayout.Y_AXIS));
        JLabel modeCaption = new JLabel("ACTIVE PROFILE");
        modeCaption.setForeground(new Color(158, 190, 178));
        modeCaption.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
        profileLabel.setForeground(Color.WHITE);
        profileLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        sideProfile.add(modeCaption);
        sideProfile.add(Box.createVerticalStrut(5));
        sideProfile.add(profileLabel);
        sidebar.add(sideProfile, BorderLayout.SOUTH);

        JPanel main = new JPanel(new BorderLayout(18, 18));
        main.setBackground(new Color(244, 247, 245));
        main.setBorder(new EmptyBorder(24, 28, 24, 28));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel title = new JLabel("Financial overview");
        title.setForeground(new Color(38, 55, 49));
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 26));
        JLabel subtitle = new JLabel("Track income, expenses, and monthly budgets.");
        subtitle.setForeground(new Color(113, 126, 120));
        subtitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
        JPanel titleGroup = new JPanel();
        titleGroup.setOpaque(false);
        titleGroup.setLayout(new BoxLayout(titleGroup, BoxLayout.Y_AXIS));
        titleGroup.add(title);
        titleGroup.add(Box.createVerticalStrut(4));
        titleGroup.add(subtitle);
        header.add(titleGroup, BorderLayout.WEST);

        JPanel headerActions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        headerActions.setOpaque(false);
        JButton income = actionButton("Add income", new Color(52, 118, 91), true);
        JButton expense = actionButton("Add expense", new Color(180, 91, 72), true);
        JButton budget = actionButton("Set budget", new Color(91, 113, 137), true);
        headerActions.add(income);
        headerActions.add(expense);
        headerActions.add(budget);
        header.add(headerActions, BorderLayout.EAST);

        JPanel cards = new JPanel(new GridLayout(1, 3, 14, 0));
        cards.setOpaque(false);
        cards.add(statCard("AVAILABLE BALANCE", balanceLabel, new Color(52, 118, 91)));
        cards.add(statCard("TOTAL INCOME", incomeLabel, new Color(86, 130, 172)));
        cards.add(statCard("TOTAL EXPENSES", expenseLabel, new Color(180, 91, 72)));

        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.add(header);
        top.add(Box.createVerticalStrut(20));
        top.add(cards);

        JTable table = new JTable(transactionModel);
        table.setRowHeight(38);
        table.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(232, 236, 233));
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setSelectionBackground(new Color(222, 238, 231));
        table.setSelectionForeground(new Color(38, 55, 49));
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setPreferredSize(new Dimension(0, 42));
        table.getTableHeader().setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        table.getTableHeader().setForeground(new Color(100, 113, 107));
        table.getTableHeader().setBackground(new Color(249, 250, 249));
        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(90);
        table.getColumnModel().getColumn(2).setPreferredWidth(110);
        table.getColumnModel().getColumn(3).setPreferredWidth(110);
        table.getColumnModel().getColumn(4).setPreferredWidth(260);

        JScrollPane tablePane = new JScrollPane(table);
        tablePane.setBorder(BorderFactory.createLineBorder(new Color(225, 230, 226)));
        tablePane.getViewport().setBackground(Color.WHITE);

        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(BorderFactory.createLineBorder(new Color(225, 230, 226)));

        JPanel tableHeader = new JPanel(new BorderLayout());
        tableHeader.setBackground(Color.WHITE);
        tableHeader.setBorder(new EmptyBorder(14, 16, 14, 16));
        JLabel recentTitle = new JLabel("Recent transactions");
        recentTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        recentTitle.setForeground(new Color(38, 55, 49));
        JButton delete = actionButton("Delete selected", new Color(115, 125, 120), false);
        tableHeader.add(recentTitle, BorderLayout.WEST);
        tableHeader.add(delete, BorderLayout.EAST);
        tableCard.add(tableHeader, BorderLayout.NORTH);
        tableCard.add(tablePane, BorderLayout.CENTER);

        income.addActionListener(e -> openTransactionDialog(TransactionType.INCOME));
        expense.addActionListener(e -> openTransactionDialog(TransactionType.EXPENSE));
        budget.addActionListener(e -> openBudgetDialog());
        delete.addActionListener(e -> deleteSelected(table));

        main.add(top, BorderLayout.NORTH);
        main.add(tableCard, BorderLayout.CENTER);

        frame.add(sidebar, BorderLayout.WEST);
        frame.add(main, BorderLayout.CENTER);

        switchProfile(profile, table);
        frame.setVisible(true);
    }

    private JButton actionButton(String text, Color color, boolean primary) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setMargin(new Insets(primary ? 9 : 7, 14, primary ? 9 : 7, 14));
        return button;
    }

    private JButton sidebarButton(String text, boolean active) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBackground(active ? new Color(46, 91, 76) : new Color(24, 56, 47));
        button.setForeground(active ? Color.WHITE : new Color(203, 220, 213));
        button.setBorder(new EmptyBorder(8, 12, 8, 12));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font(Font.SANS_SERIF, active ? Font.BOLD : Font.PLAIN, 13));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JPanel statCard(String caption, JLabel value, Color accent) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 230, 226)),
                new EmptyBorder(16, 18, 16, 18)));

        JLabel label = new JLabel(caption);
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
        label.setForeground(new Color(122, 134, 128));

        value.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 23));
        value.setForeground(accent);

        JPanel accentLine = new JPanel();
        accentLine.setBackground(accent);
        accentLine.setPreferredSize(new Dimension(38, 3));

        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.add(label);
        text.add(Box.createVerticalStrut(8));
        text.add(value);

        card.add(accentLine, BorderLayout.NORTH);
        card.add(text, BorderLayout.CENTER);
        return card;
    }

    private void switchProfile(JTable table) {
        Object[] options = {"Student", "Worker"};
        int choice = JOptionPane.showOptionDialog(frame, "Choose a profile", "Profile",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (choice >= 0) {
            profile = (String) options[choice];
            switchProfile(profile, table);
        }
    }

    private void switchProfile(String selectedProfile, JTable table) {
        profile = selectedProfile;
        ledger = new Ledger(Paths.get("finance_" + profile.toLowerCase() + ".txt"));
        profileLabel.setText(profile + " mode");
        refresh(table);
    }

    private void openTransactionDialog(TransactionType type) {
        JPanel panel = new JPanel(new GridLayout(0, 2, 8, 8));
        JTextField amount = new JTextField();
        JComboBox<String> category = new JComboBox<>(CATEGORIES);
        JTextField description = new JTextField();

        panel.add(new JLabel("Amount:"));
        panel.add(amount);
        panel.add(new JLabel("Category:"));
        panel.add(category);
        panel.add(new JLabel("Description:"));
        panel.add(description);

        int result = JOptionPane.showConfirmDialog(frame, panel,
                type == TransactionType.INCOME ? "Add income" : "Add expense",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return;

        try {
            String cleanAmount = amount.getText().trim().replace(",", "");
            BigDecimal value = new BigDecimal(cleanAmount);
            if (value.signum() <= 0) throw new NumberFormatException();

            String warning = "";
            if (type == TransactionType.EXPENSE) {
                warning = ledger.checkBudget((String) category.getSelectedItem(), value);
            }
            ledger.add(new Transaction(type == TransactionType.INCOME ? TransactionKind.INCOME : TransactionKind.EXPENSE,
                    value, (String) category.getSelectedItem(), description.getText(), LocalDateTime.now()));
            refresh(findTable());

            if (!warning.isEmpty()) {
                JOptionPane.showMessageDialog(frame, warning, "Budget notice", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Enter a valid positive amount.", "Invalid amount", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openBudgetDialog() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 8, 8));
        JComboBox<String> category = new JComboBox<>(CATEGORIES);
        JTextField limit = new JTextField();
        panel.add(new JLabel("Category:"));
        panel.add(category);
        panel.add(new JLabel("Monthly limit (0 removes):"));
        panel.add(limit);

        int result = JOptionPane.showConfirmDialog(frame, panel, "Set budget",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return;

        try {
            BigDecimal value = new BigDecimal(limit.getText().trim().replace(",", ""));
            if (value.signum() < 0) throw new NumberFormatException();
            String categoryName = (String) category.getSelectedItem();
            if (value.signum() == 0) ledger.removeBudget(categoryName);
            else ledger.setBudget(categoryName, value);
            refresh(findTable());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Enter a valid budget amount.", "Invalid amount", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSelected(JTable table) {
        int viewRow = table.getSelectedRow();
        if (viewRow < 0) {
            JOptionPane.showMessageDialog(frame, "Select a transaction first.");
            return;
        }
        int modelRow = table.convertRowIndexToModel(viewRow);
        int ledgerIndex = ledger.size() - 1 - modelRow;
        int confirm = JOptionPane.showConfirmDialog(frame, "Delete the selected transaction?", "Confirm",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            ledger.remove(ledgerIndex);
            refresh(table);
        }
    }

    private void showSummary() {
        JTextArea area = new JTextArea(ledger.getSummary());
        area.setEditable(false);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        JOptionPane.showMessageDialog(frame, new JScrollPane(area), "Spending summary", JOptionPane.INFORMATION_MESSAGE);
    }

    private JTable findTable() {
        for (Component c : frame.getContentPane().getComponents()) {
            if (c instanceof JPanel) {
                JTable found = findTable(c);
                if (found != null) return found;
            }
        }
        return null;
    }

    private JTable findTable(Component c) {
        if (c instanceof JTable) return (JTable) c;
        if (c instanceof Container) {
            for (Component child : ((Container) c).getComponents()) {
                JTable found = findTable(child);
                if (found != null) return found;
            }
        }
        return null;
    }

    private void refresh(JTable table) {
        transactionModel.setRowCount(0);
        List<Transaction> records = ledger.getAll();
        for (int i = records.size() - 1; i >= 0; i--) {
            Transaction t = records.get(i);
            transactionModel.addRow(new Object[]{
                    t.date.format(DISPLAY_DATE), t.kind.label, money(t.amount), t.category, t.description
            });
        }

        BigDecimal income = BigDecimal.ZERO;
        BigDecimal expense = BigDecimal.ZERO;
        YearMonth now = YearMonth.now();
        BigDecimal monthIncome = BigDecimal.ZERO;
        BigDecimal monthExpense = BigDecimal.ZERO;
        for (Transaction t : records) {
            if (t.kind == TransactionKind.INCOME) income = income.add(t.amount);
            else expense = expense.add(t.amount);
            if (YearMonth.from(t.date).equals(now)) {
                if (t.kind == TransactionKind.INCOME) monthIncome = monthIncome.add(t.amount);
                else monthExpense = monthExpense.add(t.amount);
            }
        }
        balanceLabel.setText(money(income.subtract(expense)));
        incomeLabel.setText(money(income));
        expenseLabel.setText(money(expense));
        monthLabel.setText(money(monthIncome) + " income / " + money(monthExpense) + " expense");
    }

    private static String money(BigDecimal value) {
        return String.format("%,.2f", value);
    }

    enum TransactionType { INCOME, EXPENSE }

    enum TransactionKind {
        INCOME("Income"), EXPENSE("Expense");
        final String label;
        TransactionKind(String label) { this.label = label; }
    }

    static class Transaction {
        final TransactionKind kind;
        final BigDecimal amount;
        final String category;
        final String description;
        final LocalDateTime date;

        Transaction(TransactionKind kind, BigDecimal amount, String category, String description, LocalDateTime date) {
            this.kind = kind;
            this.amount = amount;
            this.category = category;
            this.description = description;
            this.date = date;
        }

        String toLine() {
            return "T\t" + kind.name() + "\t" + amount.toPlainString() + "\t" + category
                    + "\t" + description.replaceAll("[\\t\\r\\n]+", " ").trim() + "\t" + date;
        }

        static Transaction fromLine(String[] parts) {
            return new Transaction(TransactionKind.valueOf(parts[1]), new BigDecimal(parts[2]), parts[3], parts[4],
                    LocalDateTime.parse(parts[5]));
        }
    }

    static class Ledger {
        private final Path file;
        private final List<Transaction> records = new ArrayList<>();
        private final Map<String, BigDecimal> budgets = new TreeMap<>();

        Ledger(Path file) {
            this.file = file;
            load();
        }

        int size() { return records.size(); }
        List<Transaction> getAll() { return records; }
        void add(Transaction transaction) { records.add(transaction); save(); }
        void remove(int index) { records.remove(index); save(); }
        void setBudget(String category, BigDecimal limit) { budgets.put(category, limit); save(); }
        void removeBudget(String category) { budgets.remove(category); save(); }

        BigDecimal spentInMonth(String category, YearMonth month) {
            BigDecimal total = BigDecimal.ZERO;
            for (Transaction transaction : records) {
                if (transaction.kind == TransactionKind.EXPENSE
                        && transaction.category.equalsIgnoreCase(category)
                        && YearMonth.from(transaction.date).equals(month)) {
                    total = total.add(transaction.amount);
                }
            }
            return total;
        }

        String checkBudget(String category, BigDecimal newAmount) {
            BigDecimal limit = budgets.get(category);
            if (limit == null) return "";
            BigDecimal spent = spentInMonth(category, YearMonth.now());
            BigDecimal after = spent.add(newAmount);
            if (after.compareTo(limit) > 0) {
                return "You are OVER your " + category + " budget. Limit: " + money(limit)
                        + "; after this entry: " + money(after);
            }
            if (after.compareTo(limit.multiply(new BigDecimal("0.80"))) >= 0) {
                return "You are approaching your " + category + " budget. After this entry: " + money(after);
            }
            return "";
        }

        String getSummary() {
            BigDecimal income = BigDecimal.ZERO;
            BigDecimal expense = BigDecimal.ZERO;
            Map<String, BigDecimal> categoryTotals = new LinkedHashMap<>();
            for (Transaction transaction : records) {
                if (transaction.kind == TransactionKind.INCOME) income = income.add(transaction.amount);
                else {
                    expense = expense.add(transaction.amount);
                    categoryTotals.merge(transaction.category, transaction.amount, BigDecimal::add);
                }
            }
            StringBuilder result = new StringBuilder();
            result.append("ALL-TIME SUMMARY\n\n");
            result.append(String.format("Income:  %,.2f%n", income));
            result.append(String.format("Expense: %,.2f%n", expense));
            result.append(String.format("Balance: %,.2f%n%n", income.subtract(expense)));
            result.append("Spending by category\n");
            if (categoryTotals.isEmpty()) result.append("No expenses yet\n");
            else for (Map.Entry<String, BigDecimal> entry : categoryTotals.entrySet()) {
                result.append(String.format("%-14s %,.2f%n", entry.getKey(), entry.getValue()));
            }
            return result.toString();
        }

        private void save() {
            List<String> lines = new ArrayList<>();
            for (Map.Entry<String, BigDecimal> entry : budgets.entrySet()) {
                lines.add("B\t" + entry.getKey() + "\t" + entry.getValue().toPlainString());
            }
            for (Transaction transaction : records) lines.add(transaction.toLine());
            try { Files.write(file, lines, StandardCharsets.UTF_8); }
            catch (IOException exception) {
                JOptionPane.showMessageDialog(null, "Could not save data: " + exception.getMessage());
            }
        }

        private void load() {
            if (!Files.exists(file)) return;
            try {
                for (String line : Files.readAllLines(file, StandardCharsets.UTF_8)) {
                    String[] parts = line.split("\t", -1);
                    if (parts.length == 6 && parts[0].equals("T")) records.add(Transaction.fromLine(parts));
                    else if (parts.length == 3 && parts[0].equals("B")) budgets.put(parts[1], new BigDecimal(parts[2]));
                }
            } catch (IOException | RuntimeException exception) {
                JOptionPane.showMessageDialog(null, "Could not read saved data. Starting fresh.");
            }
        }
    }
}
