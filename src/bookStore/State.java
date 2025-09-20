/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookStore;

/**
 *
 * @author cwambi
 */
public abstract class State {
    public abstract void awardPoints(Customer customer, double totalCost);
    public abstract double redeemPoints(Customer customer, double totalCost);
}
