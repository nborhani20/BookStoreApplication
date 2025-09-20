/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookStore;

/**
 *
 * @author cwambi
 */
public class Book {
    private final String title;
    private final double price;

    public Book(String title, double price) {
        this.title = title;
        this.price = price;
    }

    public String getTitle() { return title; }
    public double getPrice() { return price; }
    
    
//equality check for books
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book other = (Book) obj;
        return title.equalsIgnoreCase(other.title); // Case-insensitive comparison
    }

    @Override
    public int hashCode() {
        return title.toLowerCase().hashCode();
    }
}
