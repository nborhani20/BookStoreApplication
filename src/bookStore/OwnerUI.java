package bookStore;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 *
 * @author cwambi
 */


public class OwnerUI {
    private final BookStore store;
    private final Stage primaryStage;

   
    private BorderPane root;

    public OwnerUI(BookStore store, Stage primaryStage) {
        this.store = store;
        this.primaryStage = primaryStage;
    }

    public Scene createScene() {
        root = new BorderPane();
        // By default, show the owner-start-screen in the center of the BorderPane
        root.setCenter(createOwnerStartScreen());
        return new Scene(root, 600, 400);
    }

    //Owner Start screen
    private Node createOwnerStartScreen() {
        VBox startScreen = new VBox(15);
        startScreen.setAlignment(Pos.CENTER);
        startScreen.setPadding(new Insets(20));

        Button booksBtn = new Button("Books");
        Button customersBtn = new Button("Customers");
        Button logoutBtn = new Button("Logout");

        booksBtn.setOnAction(e -> root.setCenter(OwnerBooksScreen()));
        customersBtn.setOnAction(e -> root.setCenter(OwnerCustomerScreen()));
        logoutBtn.setOnAction(e -> {
            store.saveData();
            primaryStage.setScene(new LoginUI(store, primaryStage).createScene());
        });

        startScreen.getChildren().addAll(booksBtn, customersBtn, logoutBtn);
        return startScreen;
    }

    
    private Node OwnerBooksScreen() {
        
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        //TOP: Table of Books
        TableView<Book> bookTable = new TableView<>();
        bookTable.setPrefHeight(200);
        
        TableColumn<Book, String> titleCol = new TableColumn<>("Book Name");
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        
        TableColumn<Book, Double> priceCol = new TableColumn<>("Book Price");
            priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        bookTable.getColumns().addAll(titleCol, priceCol);
        bookTable.setItems(FXCollections.observableArrayList(store.getBooks()));

        //MIDDLE: Add controls
        HBox addBox = new HBox(10);
        addBox.setPadding(new Insets(5));
        
        TextField titleField = new TextField();
            titleField.setPromptText("Title");
        
        TextField priceField = new TextField();
            priceField.setPromptText("Price");
        
        Button addBtn = new Button("Add");
        addBtn.setOnAction(e -> {
        
            String title = titleField.getText().trim();
            String priceStr = priceField.getText().trim();

                if (!title.isEmpty() && !priceStr.isEmpty()) {
                    try {
                        double price = Double.parseDouble(priceStr);
                        Book newBook = new Book(title, price);
                        store.addBook(newBook);
                        bookTable.setItems(FXCollections.observableArrayList(store.getBooks()));
                        titleField.clear();
                        priceField.clear();
                    } catch (NumberFormatException ex) {
                    showError("Invalid price: " + priceStr);
                }
            }
        });
        addBox.getChildren().addAll(new Label("Name:"), titleField,
                                    new Label("Price:"), priceField,
                                    addBtn);

        //BOTTOM: [Delete], [Back]
        HBox bottomBox = new HBox(10);
        bottomBox.setPadding(new Insets(5));
        
        Button deleteBtn = new Button("Delete");
            deleteBtn.setOnAction(e -> {
                Book selected = bookTable.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    store.deleteBook(selected.getTitle());
                    bookTable.setItems(FXCollections.observableArrayList(store.getBooks()));
                }
            });
        
        Button backBtn = new Button("Back");
            backBtn.setOnAction(e -> root.setCenter(createOwnerStartScreen()));
            bottomBox.getChildren().addAll(deleteBtn, backBtn);

        
        vbox.getChildren().addAll(bookTable, addBox, bottomBox);
        return vbox;
    }

    //Owner customer screen
    private Node OwnerCustomerScreen() {
        VBox vbox = new VBox(10);
            vbox.setPadding(new Insets(10));

        //TOP: Table of customers
        TableView<Customer> custTable = new TableView<>();
        custTable.setPrefHeight(200); // constrain table
        
        TableColumn<Customer, String> userCol = new TableColumn<>("Username");
            userCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        
        TableColumn<Customer, String> passCol = new TableColumn<>("Password");
            passCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        
        TableColumn<Customer, Integer> pointsCol = new TableColumn<>("Points");
            pointsCol.setCellValueFactory(new PropertyValueFactory<>("points"));
        
        custTable.getColumns().addAll(userCol, passCol, pointsCol);
        custTable.setItems(FXCollections.observableArrayList(store.getCustomers()));

        //MIDDLE: Add new customer
        HBox addBox = new HBox(10);
            addBox.setPadding(new Insets(5));
        
        TextField userField = new TextField();
            userField.setPromptText("Username");
        
        TextField passField = new TextField();
            passField.setPromptText("Password");
        
        Button addBtn = new Button("Add");
            addBtn.setOnAction((ActionEvent e) -> {
            String username = userField.getText().trim();
            String password = passField.getText().trim();
            if (!username.isEmpty() && !password.isEmpty()) {
                Customer newCust = new Customer(username, password);
                store.addCustomer(newCust);
                custTable.setItems(FXCollections.observableArrayList(store.getCustomers()));
                userField.clear();
                passField.clear();
            }
        });
        
        addBox.getChildren().addAll(new Label("Username:"), userField,
                                    new Label("Password:"), passField,
                                    addBtn);

        //BOTTOM: [Delete], [Back]
        HBox bottomBox = new HBox(10);
            bottomBox.setPadding(new Insets(5));
        
            Button deleteBtn = new Button("Delete");
        deleteBtn.setOnAction(e -> {
            Customer selected = custTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                store.deleteCustomer(selected.getUsername());
                custTable.setItems(FXCollections.observableArrayList(store.getCustomers()));
            }
        });
        
            Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> root.setCenter(createOwnerStartScreen()));
        bottomBox.getChildren().addAll(deleteBtn, backBtn);

        vbox.getChildren().addAll(custTable, addBox, bottomBox);
        return vbox;
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg);
        alert.showAndWait();
    }
}
