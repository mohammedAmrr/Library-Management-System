# Library-Management-System

Introduction
The Library Management System is a Java-based desktop application developed using Java Swing. It provides essential features for managing a small library, such as adding, removing, borrowing, and returning books. It also allows users to set the library capacity and view current status including book categories and borrowing status.
Graphical User Interface (GUI)
The application interface is built using Java Swing. The main window is organized using BorderLayout, dividing the interface into three sections: input fields at the top (NORTH), action buttons in the center (CENTER), and a status area at the bottom (SOUTH).
Components Description
1. Capacity Field: Allows the user to define the library's maximum number of books.
2. Book ID and Name Fields: Used for entering book information.
3. Category Box: A dropdown list for selecting the book category.
4. Borrow Period Field: Accepts the number of days a book is to be borrowed.
5. Buttons: Include Add Book, Remove Book, Borrow Book, Return Book, View Status, and Exit.
6. Status Area: Displays real-time data about the books and their borrowing status.
Backend Logic
The system uses an inner Book class to represent each book. It contains attributes such as ID, name, category, borrowing status, and borrow period. The main class uses an ArrayList to store books and a HashMap to count books per category. The application uses ActionListeners to handle user interactions.
Features
• Set Library Capacity
• Add New Book
• Remove Book (only if not borrowed)
• Borrow Book with specified period
• Return Book
• View Library Status
• Exit Application
Conclusion
This project serves as an effective tool for understanding GUI development in Java and implementing a mini-library management system. Future improvements may include persistent data storage, user roles, and a more advanced search functionality.
