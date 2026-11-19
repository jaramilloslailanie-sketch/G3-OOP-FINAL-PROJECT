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

###2. Inheritance
```java
public class MoneyMadeEasy {
    
    // Inner classes — share and extend the main class context
    static class Profile { ... }
    static class Transaction { ... }
    static class Ledger { ... }
}
```
###3. Polymorphism
```java
// Same file handles TWO different object types
if (p[0].equals("T")) {
    records.add(Transaction.fromParts(p));  // ← Transaction
} else if (p[0].equals("B")) {
    budgets.put(p[1], new BigDecimal(p[2])); // ← Budget
}
```
###4. Abstraction
```java
// Simple interface — no need to see the 100+ lines inside
ledger.add(transaction);
ledger.getSummary();
ledger.setBudget("Food", new BigDecimal("5000"));

```

## 🚀 How to Run

### Prerequisites
- Java JDK 8 or higher installed

### Steps
1. Save the code as `MoneyMadeEasy.java`
2. Compile:
   ```bash
   javac MoneyMadeEasy.java
