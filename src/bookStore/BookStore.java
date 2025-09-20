/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookStore;

import java.io.*;
import java.util.*;

/**
 *
 * @author cwambi
 */
public class BookStore {
    private final List<Book> books;
    private final List<Customer> customers;
    private final Owner owner;

    public BookStore() {
        books = new ArrayList<>();
        customers = new ArrayList<>();
        owner = new Owner(); // Owner: username="admin", password="admin"
        loadData(); // Load data on initialization
    }

//Manage Books
    //add a book
    public void addBook(Book book) {
        for (Book b : books) {
            if (b.equals(book)) { // Uses overridden equals() for title check
                System.out.println("[Error] Book '" + book.getTitle() + "' already exists.");
                return;
            }
        }
        books.add(book);
    }
    //delete a book
    public boolean deleteBook(String title) {
        return books.removeIf(b -> b.getTitle().equalsIgnoreCase(title));
    }
    
    //find a book
    public Book findBook(String title) throws NoSuchElementException {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) return book;
        }
        throw new NoSuchElementException("Book '" + title + "' not found.");
    }

//Manage Customers  
    //Add a customer
    public void addCustomer(Customer customer) {
        for (Customer c : customers) {
            if (c.getUsername().equalsIgnoreCase(customer.getUsername())) {
                System.out.println("[Error] Customer '" + customer.getUsername() + "' already exists.");
                return;
            }
        }
        customers.add(customer);
    }
    
    //delete a customer
    public boolean deleteCustomer(String username) {
        return customers.removeIf(c -> c.getUsername().equalsIgnoreCase(username));
    }
    
    //Find a Customer
    public Customer findCustomer(String username) throws NoSuchElementException {
        for (Customer c : customers) {
            if (c.getUsername().equalsIgnoreCase(username)) return c;
        }
        throw new NoSuchElementException("Customer '" + username + "' not found.");
    }

//Authentication
    public boolean login(String username, String password) {
        
    // Owner login
        if (owner.getUsername().equals(username) && owner.getPassword().equals(password)) return true;
        
    // Customer login
        return customers.stream().anyMatch(c -> 
            c.getUsername().equalsIgnoreCase(username) && 
            c.getPassword().equals(password)
        );
    }
    public void logout() {
        saveData(); // Saves books and customers to files
    }

//Data Management 
    //Save Data
    public void saveData() {
        saveBooks();
        saveCustomers();
    }
    
    private void saveBooks() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("books.txt"))) {
            books.forEach(b -> writer.println(b.getTitle() + "," + b.getPrice()));
        } catch (IOException e) {
            System.out.println("Failed to save books: " + e.getMessage());
        }
    }
    
    private void saveCustomers() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("customers.txt"))) {
            customers.forEach(c -> writer.println(
                    c.getUsername() + "," + c.getPassword() + "," + c.getPoints()
            ));
        } catch (IOException e) {
            System.out.println("Failed to save customers: " + e.getMessage());
        }
    }
    
    
    //Load Data
    private void loadData() {
        loadBooks();
        loadCustomers();
    }

    private void loadBooks() {
        try (BufferedReader reader = new BufferedReader(new FileReader("books.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String title = parts[0].trim();
                    double price = Double.parseDouble(parts[1].trim());
                    books.add(new Book(title, price));
                }
            }
        } catch (IOException e) {
            System.out.println("books.txt not found. Starting with empty inventory.");
        }
    }

    private void loadCustomers() {
        try (BufferedReader reader = new BufferedReader(new FileReader("customers.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    int points = Integer.parseInt(parts[2].trim());
                    Customer c = new Customer(username, password);
                    c.setPoints(points); // Automatically updates status
                    customers.add(c);
                }
            }
        } catch (IOException e) {
            System.out.println(" customers.txt not found. Starting with no registered customers.");
        }
    }


    //Getters
    public List<Book> getBooks() { return Collections.unmodifiableList(books); }
    public List<Customer> getCustomers() { return Collections.unmodifiableList(customers); }
}
