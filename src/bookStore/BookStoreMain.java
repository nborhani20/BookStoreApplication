/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package bookStore;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author cwambi
 */

//Main class for the application
public class BookStoreMain extends Application {
    private BookStore store = new BookStore();
    
    @Override
    public void start(Stage primaryStage) {
        try {
            // Set primary stage
            primaryStage.setTitle("Bookstore Application");
            primaryStage.setScene(new LoginUI(store, primaryStage).createScene());
            
            //Closing window
            primaryStage.setOnCloseRequest(event -> {
                store.logout(); // Save data to files
            });
            
            primaryStage.show();
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        launch(args); // Start application
    }
}
