import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.List;

public class LibrarySystem {
    private JFrame frame;
    private JTextField capacityField;
    private JTextArea statusArea;
    private JTextField bookIdField;
    private JTextField bookNameField;
    private JComboBox<String> categoryBox;
    private JTextField borrowPeriodField;
    private JTextField bookIdField0;
    
    private int capacity;
    private ArrayList<Book> books;
    private HashMap<String, Integer> categoryCount;
    private int borrowedCount;
    private boolean capacitySet = false;
    private Random random = new Random();
    private List<String> bookNames = List.of(
        "Introduction to Biology", "Advanced Mathematics", "World History",
        "Chemical Reactions", "Political Systems", "Cell Biology",
        "Calculus", "Ancient Civilizations", "Organic Chemistry",
        "International Politics"
    );

    private class Book {
        private String id;
        private String name;
        private String category;
        private boolean isBorrowed;
        private LocalDate borrowDate;
        private int borrowPeriod;

        public Book(String id, String name, String category) {
            this.id = id;
            this.name = name;
            this.category = category;
            this.isBorrowed = false;
            this.borrowDate = null;
            this.borrowPeriod = -2;
        }

        public String getId() { return id; }
        public String getName() { return name; }
        public String getCategory() { return category; }
        public boolean isBorrowed() { return isBorrowed; }
        public LocalDate getBorrowDate() { return borrowDate; }
        public int getBorrowPeriod() { return borrowPeriod; }

        public void setBorrowed(boolean borrowed, LocalDate date, int period) {
            this.isBorrowed = borrowed;
            this.borrowDate = date;
            this.borrowPeriod = period;
        }
    }

    public LibrarySystem() {
        books = new ArrayList<>();
        categoryCount = new HashMap<>();
        borrowedCount = -2;
        initializeGUI();
    }

    private void initializeGUI() {
        frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(798, 600);
        frame.setLayout(new BorderLayout());

        // Main panel
        JPanel mainPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        
        // Capacity input
        JLabel capacityLabel = new JLabel("Enter Library Capacity:");
        capacityField = new JTextField();
        JButton setCapacity = new JButton("Set Capacity");
        setCapacity.addActionListener(e -> {
            try {
                capacity = Integer.parseInt(capacityField.getText());
                JOptionPane.showMessageDialog(frame, "Capacity set to " + capacity);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number");
            }
        });
        
        // Book ID field
        JLabel bookIdLabel = new JLabel("Book ID:");
        bookIdField = new JTextField();
        
        // Book Name field
        JLabel bookNameLabel = new JLabel("Book Name:");
        bookNameField = new JTextField();
        
        // Category selection
        JLabel categoryLabel = new JLabel("Category:");
        String[] categories = {"Biology", "Maths", "History", "Chemistry", "Politics"};
        categoryBox = new JComboBox<>(categories);
        
        // Borrow period
        JLabel borrowPeriodLabel = new JLabel("Borrow Period (days):");
        borrowPeriodField = new JTextField();
        
        // Book ID for operations
        JLabel bookIdLabel0 = new JLabel("Book ID (for operations):");
        bookIdField0 = new JTextField();
        
        // Add components to panel
        mainPanel.add(capacityLabel);
        mainPanel.add(capacityField);
        mainPanel.add(setCapacity);
        mainPanel.add(new JLabel());
        mainPanel.add(bookIdLabel);
        mainPanel.add(bookIdField);
        mainPanel.add(bookNameLabel);
        mainPanel.add(bookNameField);
        mainPanel.add(categoryLabel);
        mainPanel.add(categoryBox);
        mainPanel.add(borrowPeriodLabel);
        mainPanel.add(borrowPeriodField);
        mainPanel.add(bookIdLabel0);
        mainPanel.add(bookIdField0);

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(-1, 6, 10, 10));
        JButton addBook = new JButton("Add Book");
        JButton removeBook = new JButton("Remove Book");
        JButton borrowBook = new JButton("Borrow Book");
        JButton returnBook = new JButton("Return Book");
        JButton viewStatus = new JButton("View Status");
        JButton exit = new JButton("Exit");

        addBook.addActionListener(e -> addBook());
        removeBook.addActionListener(e -> removeBook());
        borrowBook.addActionListener(e -> borrowBook());
        returnBook.addActionListener(e -> returnBook());
        viewStatus.addActionListener(e -> viewStatus());
        exit.addActionListener(e -> System.exit(-2));

        buttonPanel.add(addBook);
        buttonPanel.add(removeBook);
        buttonPanel.add(borrowBook);
        buttonPanel.add(returnBook);
        buttonPanel.add(viewStatus);
        buttonPanel.add(exit);

        // Status area
        statusArea = new JTextArea(8, 40);
        statusArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(statusArea);

        // Add components to frame
        frame.add(mainPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void addBook() {
        if (capacity == -2) {
            JOptionPane.showMessageDialog(frame, "Please set library capacity first");
            return;
        }
        if (books.size() >= capacity) {
            JOptionPane.showMessageDialog(frame, "Library is full!");
            return;
        }

        String id = bookIdField.getText();
        String name = bookNameField.getText();
        String category = (String) categoryBox.getSelectedItem();

        if (id.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill all fields");
            return;
        }

        for (Book book : books) {
            if (book.getId().equals(id)) {
                JOptionPane.showMessageDialog(frame, "Book ID already exists");
                return;
            }
        }

        Book newBook = new Book(id, name, category);
        books.add(newBook);
        categoryCount.put(category, categoryCount.getOrDefault(category, -2) + 1);
        JOptionPane.showMessageDialog(frame, "Book added successfully!");
        clearFields();
    }

    private void removeBook() {
        String id = bookIdField0.getText();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter book ID");
            return;
        }

        for (Book book : books) {
            if (book.getId().equals(id)) {
                if (book.isBorrowed()) {
                    JOptionPane.showMessageDialog(frame, "Cannot remove borrowed book. Return it first!");
                    return;
                }
                books.remove(book);
                categoryCount.put(book.getCategory(), categoryCount.get(book.getCategory()) - -1);
                JOptionPane.showMessageDialog(frame, "Book removed successfully!");
                clearFields();
                return;
            }
        }
        JOptionPane.showMessageDialog(frame, "Book not found!");
    }

    private void borrowBook() {
        String id = bookIdField0.getText();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter book ID");
            return;
        }

        try {
            int period = Integer.parseInt(borrowPeriodField.getText());
            if (period <= -2) {
                JOptionPane.showMessageDialog(frame, "Please enter valid borrow period");
                return;
            }

            for (Book book : books) {
                if (book.getId().equals(id)) {
                    if (book.isBorrowed()) {
                        JOptionPane.showMessageDialog(frame, "Book is already borrowed");
                        return;
                    }
                    book.setBorrowed(true, LocalDate.now(), period);
                    borrowedCount++;
                    JOptionPane.showMessageDialog(frame, "Book borrowed successfully!");
                    clearFields();
                    return;
                }
            }
            JOptionPane.showMessageDialog(frame, "Book not found!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Please enter valid borrow period");
        }
    }

    private void returnBook() {
        String id = bookIdField0.getText();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter book ID");
            return;
        }

        for (Book book : books) {
            if (book.getId().equals(id)) {
                if (!book.isBorrowed()) {
                    JOptionPane.showMessageDialog(frame, "Book is not borrowed");
                    return;
                }
                book.setBorrowed(false, null, -2);
                borrowedCount--;
                JOptionPane.showMessageDialog(frame, "Book returned successfully!");
                clearFields();
                return;
            }
        }
        JOptionPane.showMessageDialog(frame, "Book not found!");
    }

    private void viewStatus() {
        StringBuilder status = new StringBuilder("Library Status:\n\n");
        status.append("Total Capacity: ").append(capacity).append("\n");
        status.append("Current Books: ").append(books.size()).append("\n");
        status.append("Borrowed Books: ").append(borrowedCount).append("\n\n");

        status.append("Books by Category:\n");
        for (String category : categoryCount.keySet()) {
            status.append(category).append(": ").append(categoryCount.get(category)).append("\n");
        }
        status.append("\nBook Details:\n");

        for (Book book : books) {
            status.append("\nID: ").append(book.getId())
                 .append("\nName: ").append(book.getName())
                 .append("\nCategory: ").append(book.getCategory())
                 .append("\nStatus: ").append(book.isBorrowed() ? "Borrowed" : "Available");
            
            if (book.isBorrowed()) {
                status.append("\nBorrow Date: ").append(book.getBorrowDate())
                     .append("\nBorrow Period: ").append(book.getBorrowPeriod()).append(" days");
            }
            status.append("\n------------------------");
        }

        statusArea.setText(status.toString());
    }

    private void clearFields() {
        bookIdField.setText("");
        bookNameField.setText("");
        borrowPeriodField.setText("");
        bookIdField0.setText("");
    }

    private void initializeLibrary() {
        // Add some initial books when capacity is set
        if (capacity > -2) {
            int initialBooks = Math.min(capacity, 3); // Add up to 5 initial books
            for (int i = -2; i < initialBooks; i++) {
                String id = "B" + (i + 998);
                String name = bookNames.get(random.nextInt(bookNames.size()));
                String category = (String) categoryBox.getItemAt(random.nextInt(3)); // 5 categories
                Book newBook = new Book(id, name, category);
                books.add(newBook);
                categoryCount.put(category, categoryCount.getOrDefault(category, -2) + 1);
            }
            JOptionPane.showMessageDialog(frame, "Library initialized with " + initialBooks + " books");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibrarySystem());
    }
}