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
|PE|------|
| *[Your Name]* | Lead Developer — Full system design & implementation |
| *[Teammate Names]* | *Add roles here* |

---

## 🧩 OOP Concepts Demonstrated
- **Encapsulation** — `Profile`, `Transaction`, and `Ledger` classes protect their internal data
- **Classes & Objects** — System organized into logical, reusable components
- **Enumerations** — `Transaction.Type` ensures consistent income/expense values
- **Abstraction** — File operations and data management are hidden inside the `Ledger` class
- **Instance & Static Members** — Clear separation between shared constants and object-specific data

---

## 🚀 How to Run

### Prerequisites
- Java JDK 8 or higher installed

### Steps
1. Save the code as `MoneyMadeEasy.java`
2. Compile:
   ```bash
   javac MoneyMadeEasy.java
