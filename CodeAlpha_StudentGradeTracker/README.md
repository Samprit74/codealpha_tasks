
---

## ✨ Features Implemented

### 🔹 Dashboard
- Total students count
- Average score
- Highest score
- Clean and modern UI

### 🔹 Student Management
- ➕ Add new student
- 🔍 View student by ID
- ✏️ Edit existing student
- 🗑️ Delete student with confirmation

### 🔹 Performance Summary
- Average, highest, lowest score
- Auto-generated performance analysis
- Live data fetched from database

---

## 🗄️ Database Design

**Table: `student`**

| Column Name | Type |
|------------|------|
| id | INT (PK, AUTO_INCREMENT) |
| name | VARCHAR |
| department | VARCHAR |
| year | INT |
| semester | INT |
| subject1 | INT |
| subject2 | INT |
| subject3 | INT |
| score | DOUBLE |

---

## 🚀 How to Run the Project

1. Clone the main repository:
   ```bash
   git clone https://github.com/Samprit74/codealpha_tasks.git


2.Open Eclipse IDE

3.Import project:

     File → Import → Existing Projects into Workspace

4.Configure MySQL in:

     src/util/DBConnection.java

5.Run:

    ui/MainFrame.java   


👤 Author
Samprit Roy
Java Intern – CodeAlpha
GitHub: @Samprit74


