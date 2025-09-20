/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookStore;

import java.util.NoSuchElementException;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.*;
/**
 *
 * @author cwambi
 */

//for login
public class LoginUI {
    private final BookStore store;
    private final Stage primaryStage;

    public LoginUI(BookStore store, Stage primaryStage) {
        this.store = store;
        this.primaryStage = primaryStage;
    }

    public Scene createScene() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label userLabel = new Label("Username:");
        TextField userField = new TextField();
        Label passLabel = new Label("Password:");
        PasswordField passField = new PasswordField();
        Button loginBtn = new Button("Login");
        Label statusLabel = new Label();

        loginBtn.setOnAction((javafx.event.ActionEvent event) -> {
            String username = userField.getText().trim();
            String password = passField.getText().trim();
            
            if(store.login(username, password)) {
                if(username.equals("admin")) {
                    primaryStage.setScene(new OwnerUI(store, primaryStage).createScene());
                } else {
                    try {
                        Customer customer = store.findCustomer(username);
                        primaryStage.setScene(new CustomerUI(store, primaryStage, customer).createScene());
                    } catch (NoSuchElementException e) {
                        statusLabel.setText("Customer not found!");
                    }
                }
            } else {
                statusLabel.setText("Invalid credentials!");
            }
        });

        grid.add(userLabel, 0, 0);
        grid.add(userField, 1, 0);
        grid.add(passLabel, 0, 1);
        grid.add(passField, 1, 1);
        grid.add(loginBtn, 1, 2);
        grid.add(statusLabel, 1, 3);

        return new Scene(grid, 300, 200);
    }
}
