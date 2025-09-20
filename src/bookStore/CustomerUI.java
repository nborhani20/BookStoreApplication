package bookStore;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author cwambi
 */

//Customer scenes

public class CustomerUI {
    private final BookStore store;
    private final Stage primaryStage;
    private final Customer customer;

    // Tracks which books the user has selected
    private final Map<Book, BooleanProperty> selectionMap = new HashMap<>();

    public CustomerUI(BookStore store, Stage primaryStage, Customer customer) {
        this.store = store;
        this.primaryStage = primaryStage;
        this.customer = customer;
    }

    public Scene createScene() {
        BorderPane root = new BorderPane();

        // TOP: label
        Label topLabel = new Label(
            "Welcome " + customer.getUsername() +
            ". You have " + customer.getPoints() + " points." +
            " Your status is " + customer.getStatus() + "."
        );
        topLabel.setPadding(new Insets(10));
        root.setTop(topLabel);

        // MIDDLE: Table of Books
        TableView<Book> bookTable = createBookTable();
        root.setCenter(bookTable);

        // BOTTOM: buttons
        HBox bottomBox = new HBox(10);
        bottomBox.setPadding(new Insets(10));
        bottomBox.setAlignment(Pos.CENTER);

        Button buyBtn = new Button("Buy");
        buyBtn.setOnAction(e -> handleBuy(false, bookTable));

        Button redeemBtn = new Button("Redeem points and Buy");
        redeemBtn.setOnAction(e -> handleBuy(true, bookTable));

        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction((ActionEvent e) -> {
            store.saveData();
            primaryStage.setScene(new LoginUI(store, primaryStage).createScene());
        });

        bottomBox.getChildren().addAll(buyBtn, redeemBtn, logoutBtn);
        root.setBottom(bottomBox);

        return new Scene(root, 600, 400);
    }

    //The book table
    private TableView<Book> createBookTable() {
        TableView<Book> table = new TableView<>();
        table.setEditable(true);  // allow editing of checkboxes
        
        //Title
        TableColumn<Book, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("title"));
        
        //Price
        TableColumn<Book, Double> priceCol = new TableColumn<>("Book Price");
        priceCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("price"));

        // Checkbox column
        TableColumn<Book, Boolean> selectCol = new TableColumn<>("Select");
        selectCol.setEditable(true);

        // Create a cell factory:
        selectCol.setCellFactory(CheckBoxTableCell.forTableColumn(index -> {
            // For each row index, find the corresponding Book
            Book b = table.getItems().get(index);
            // Return the BooleanProperty for that Book from selectionMap
            return selectionMap.computeIfAbsent(b, x -> new SimpleBooleanProperty(false));
        }));
        
        
        table.getColumns().addAll(titleCol, priceCol, selectCol);
        table.setItems(FXCollections.observableArrayList(store.getBooks()));

        return table;
    }

    //On click of either buy button
    private void handleBuy(boolean redeemPoints, TableView<Book> table) {
        // Clear the customer's cart first
        customer.getSelectedBooks().clear();

        // Collect all books whose checkboxes are set to true
        for (Map.Entry<Book, BooleanProperty> tick : selectionMap.entrySet()) {
            if (tick.getValue().get()) {
                customer.addToCart(tick.getKey());
            }
        }

        // Perform the purchase
        double cost;
        if (redeemPoints) {
            cost = customer.redeemAndBuy();
        } else {
            cost = customer.buy();
        }

        // Uncheck all boxes after the purchase
        for (BooleanProperty bp : selectionMap.values()) {
            bp.set(false);
        }

        // Switch to the cost screen
        primaryStage.setScene(CostScreen.createScene(primaryStage, store, customer, cost, redeemPoints));
    }
}
