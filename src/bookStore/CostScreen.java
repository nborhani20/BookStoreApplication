/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookStore;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author cwambi
 */

//CostScreen
public class CostScreen {
    public static Scene createScene(Stage primaryStage, BookStore store, 
                                  Customer customer, double cost, boolean redeemed) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        Label costLabel = new Label("Total Cost: $" + String.format("%.2f", cost));
        Label pointsLabel = new Label("Points: " + customer.getPoints() + 
                                    " | Status: " + customer.getStatus());
        
        if(redeemed) {
            costLabel.setText("Final Cost After Redemption: $" + String.format("%.2f", cost));
        }

        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction((javafx.event.ActionEvent event) -> {
            store.saveData();
            primaryStage.setScene(new LoginUI(store, primaryStage).createScene());
        });

        root.getChildren().addAll(costLabel, pointsLabel, logoutBtn);
        
        return new Scene(root, 400, 200);
    }
}
