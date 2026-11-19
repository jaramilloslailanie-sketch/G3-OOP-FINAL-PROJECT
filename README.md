# Money Made Easy: Your Daily Low-Stress Personal Money Manager

## 📌 Project Description
**Money Made Easy** is a console-based personal finance application built in Java. It helps users track daily income and expenses, set monthly budget limits, and view clear spending summaries — all with locally saved data that stays private. Designed to be simple and low-stress, it offers two user profiles — **Student** and **Worker** — each with tailored tips and guidance. No internet connection or account creation needed.

---

## ❗ Problem Being Addressed
- Full-featured finance apps can be complicated and overwhelming
- Online apps raise privacy concerns about personal financial data
- Students and workers have different financial needs but often use the same tools
- It's difficult to track spending and know when you're approaching your budget limits

---

## 🎯 Project Objectives
- Provide a quick and simple way to log income and expenses
- Help users set and track monthly budgets per category
- Display clear financial summaries — both all-time and current month
- Warn users when approaching or exceeding budget limits
- Keep data saved locally for privacy and offline access
- Support two distinct usage modes: Student and Worker

---

## 👥 Target Users
- 🎓 **Students** — managing allowances and school expenses
- 💼 **Working Professionals** — tracking salaries, bills, and savings
- 👤 **Anyone** who wants a simple, private money management tool

---

## ✨ Main Features
- **Two Profiles:** Student Mode and Worker Mode with personalized tips
- **Transaction Logging:** Quickly record Income or Expense entries
- **Categories:** Food, Transport, Bills, Savings, Other — or create your own
- **Budget Tracking:** Set monthly limits; get warnings at 80% usage and alerts when over budget
- **Spending Summary:** View total income, expenses, and net balance
- **Recent Transactions:** Review your latest entries
- **Delete Entries:** Remove mistakes easily
- **Local Storage:** Data saved in plain text files — your info stays on your device

---

## 🛠️ Technologies Used
- **Language:** Java 8+
- **No external libraries or dependencies required**
- Uses standard Java APIs: `java.io`, `java.nio.file`, `java.time`, `java.math`

---

## 👨‍💻 Team Members and Roles
| Name | Role |
|------|------|
|Perez, Klein lei renz J.|Lead Proggrammer|
|Montalbo, Jan Denver D.| Project Leader|
|Robles, Ma. Ronalene  | System/Design Lead|
|Falcutila, Matthew Ken | Documentation | 

---

## 🧩 OOP Concepts Demonstrated
---
### 1. Encapsulation
Data is kept `private` inside each class and accessed only through controlled methods, protecting it from unintended changes.

```java
public class MoneyMadeEasy {
    
    // Inner classes — share and extend the main class context
    static class Profile { ... }
    static class Transaction { ... }
    static class Ledger { ... }
}
```

### 2. Inheritance
```java
public class MoneyMadeEasy {
    
    // Inner classes — share and extend the main class context
    static class Profile { ... }
    static class Transaction { ... }
    static class Ledger { ... }
}
```
### 3. Polymorphism
```java
// Same file handles TWO different object types
if (p[0].equals("T")) {
    records.add(Transaction.fromParts(p));  // ← Transaction
} else if (p[0].equals("B")) {
    budgets.put(p[1], new BigDecimal(p[2])); // ← Budget
}
```
### 4. Abstraction
```java
// Simple interface — no need to see the 100+ lines inside
ledger.add(transaction);
ledger.getSummary();
ledger.setBudget("Food", new BigDecimal("5000"));

```

## 🚀 Steps to Run

1. Save the code as **`MoneyMadeEasy.java`** in a folder on your computer.
2. Open that folder in **VS Code** (or any Java IDE).
3. Open a terminal and compile the program:
   ```bash
   javac MoneyMadeEasy.java
4. Run the compiled program:
```
java MoneyMadeEasy
```
5. Use the number-based menu to navigate through the app:
   - Select your profile (Student or Worker)
   - Log income or expenses
   - Set monthly budget limits
   - View spending summaries and transactions
   - Delete entries or switch profiles
   - Exit when done
```
## 🖥️ Sample Output

### Welcome & Profile Selection
```

==================================================
Money Made Easy
Your Daily Low-Stress Personal Money Manager
==================================================

----------- PROFILE SELECTION -----------

1. Student Mode
2. Worker Mode
3. Exit
Enter your choice: 1

Loaded 0 saved transaction(s) for Student profile.

```



### Main Menu
```

----------- MAIN MENU -----------
Current Profile: Student
Mode: Schedule adapted for class and study routines
Tip: set a weekly-friendly Food and Transport budget to stretch your allowance.

---

1. Log Income or Expense
2. Set Budget Limit
3. View Budgets (this month)
4. View Spending Summary
5. View Recent Transactions
6. Delete a Transaction
7. Switch Profile
8. Exit

Enter your choice:

```



### Logging a Transaction
```

----- Quick Transaction Logging -----

1. Income
2. Expense
Select type: 2
Enter amount: 150.00
Categories: 1=Food, 2=Transport, 3=Bills, 4=Savings, 5=Other
Pick a number or type a category (blank = Other): 1
Enter short description (optional): Lunch at school
Transaction saved successfully.

```



### Setting a Budget
```

----- Set Budget Limit -----
Categories: 1=Food, 2=Transport, 3=Bills, 4=Savings, 5=Other
Pick a number or type a category (blank = Other): 1
Enter monthly budget limit (0 removes the limit): 2000

Monthly budget limit for Food set to 2,000.00.

```



### Viewing Budgets
```

----- Budgets for 2026-11 -----
Category          Limit        Spent        Left

---

Food             2,000.00       150.00     1,850.00

```

### Spending Summary
```

----- Clear Spending Summary -----
ALL TIME
Total Income:            0.00
Total Expense:         150.00
Net Balance:          -150.00

THIS MONTH (2026-11)
Income:                  0.00
Expense:                150.00
Net:                   -150.00

Spending by Category (all time):
Food:                     150.00

All financial data saved locally. Safe and Private.

```



### Recent Transactions
```

----- Recent Transactions (newest first) -----
#1    2026-11-19 23:45    Expense        150.00    Food        Lunch at school

```


### Budget Warning Example
```

----- Quick Transaction Logging -----

1. Income
2. Expense
Select type: 2
Enter amount: 1,900.00
Categories: 1=Food, 2=Transport, 3=Bills, 4=Savings, 5=Other
Pick a number or type a category (blank = Other): 1
Enter short description (optional): Groceries for the week

NOTICE: You are approaching your monthly budget for Food. Limit: 2,000.00, spent so far: 150.00, after this entry: 2,050.00
Transaction saved successfully.

```



### Deleting a Transaction
```

----- Recent Transactions (newest first) -----
#1    2026-11-19 23:45    Expense        150.00    Food        Lunch at school

Enter the # to delete (or press Enter to cancel): 1
Delete transaction #1? (y/n): y
Transaction deleted.

```



### Switching Profile & Exiting
```

Enter your choice: 7

----------- PROFILE SELECTION -----------

1. Student Mode
2. Worker Mode
3. Exit
Enter your choice: 2

Loaded 0 saved transaction(s) for Worker profile.

Enter your choice: 8
Exiting system. Goodbye.
