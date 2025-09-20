/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookStore;

/**
 *
 * @author cwambi
 */
public class Gold extends State {
    @Override
    public void awardPoints(Customer customer, double totalCost) {
        customer.setPoints(customer.getPoints() + (int)(totalCost * 10));
    }

    @Override
    public double redeemPoints(Customer customer, double totalCost) {
        int pointsAvailable = customer.getPoints();
        double redeemable = Math.min(totalCost, pointsAvailable / 100.0);
        customer.setPoints(pointsAvailable - (int)(redeemable * 100));
        return Math.max(totalCost - redeemable, 0);
    }

    @Override
    public String toString() { return "Gold"; }
}

