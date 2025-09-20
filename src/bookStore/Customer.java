/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookStore;

import java.util.*;//entire javautil

/**
 *
 * @author cwambi
 */
public class Customer extends User {
    private int points;
    private State status;
    private List<Book> selectedBooks;//I got rid of one of the lists, found a way to only use one
    
//Constructor
    public Customer(String username, String password) {
        super(username, password);
        this.points = 0;
        this.status = new Silver(); 
        this.selectedBooks = new ArrayList<>();
    }

// Status management
    private void updateStatus() {
        if (this.points >= 1000) {
            this.status = new Gold();
        } else {
            this.status = new Silver();
        }
    }

// other Methods
    //Used this to implement the second list
    public void addToCart(Book book) {
        selectedBooks.add(book);
    }

    //new cart for each login
    public void clearCart() {
        selectedBooks.clear();
    }

    //calculate total cost
    private double calculateTotalCost() {
        double sum = 0;
        for (Book book : selectedBooks) sum += book.getPrice();
        return sum;
    }
    
    //regular purchase
    public double buy() {
        double totalCost = calculateTotalCost();
        status.awardPoints(this, totalCost);
        updateStatus();
        return totalCost;
    }
    
    //points redemption
    public double redeemAndBuy() {
        double totalCost = calculateTotalCost();
        double discountedCost = status.redeemPoints(this, totalCost);
        status.awardPoints(this, discountedCost);
        updateStatus();
        return discountedCost;
    }
    
    // Getters and setters
    public int getPoints() { return points; }
    public String getStatus() { return status.toString(); }
    public List<Book> getSelectedBooks() { return selectedBooks; }
    public void setPoints(int points) { 
        this.points = points; 
        updateStatus(); // Auto-update state according to points
    }
}
