# Encapsulation:



// In MoneyMadeEasyUI

private final JFrame frame = new JFrame("Money Made Easy");

private String profile = "Student";

private Ledger ledger;



// In Ledger

private final Path file;

private final List<Transaction> records = new ArrayList<>();

private final Map<String, BigDecimal> budgets = new TreeMap<>();



# Inheritance:



// JFrame, JPanel, JLabel, JButton — all extend java.awt.Component

private final JFrame frame;

private final JLabel profileLabel;

JButton overview = sidebarButton("Overview", true);





# Polymorphism:



@Override

public boolean isCellEditable(int row, int column) { return false; }



private JTable findTable(Component c) {

&#x20;   if (c instanceof JTable) return (JTable) c;

&#x20;   if (c instanceof Container) {

&#x20;       for (Component child : ((Container) c).getComponents()) {

&#x20;           JTable found = findTable(child);

&#x20;           if (found != null) return found;

&#x20;       }

&#x20;   }

&#x20;   return null;

}





# Abstraction:



// ✅ Public — easy to use

public void add(Transaction t) { ... }

public void remove(int i) { ... }

public String getSummary() { ... }



// 🔒 Private — hidden complexity

private void save() { ... }

private void load() { ... }



