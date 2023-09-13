package coe528project;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class GUI extends Application {
   
    public User user;
    private final Admin admin = new Admin();
    private Customer customer;
    
    TableView<Book> bookTable = new TableView<>();
    final TableView.TableViewFocusModel<Book> FocusModel = bookTable.getFocusModel();
    ObservableList<Book> books = FXCollections.observableArrayList();

    public ObservableList<Book> addBook(){
        books.addAll(Admin.books);
        return books; 
    }
    
    public Group loginScene(boolean loginError){
        Group log = new Group(); //Group helps with rendering all the buttons/"children" at once
        HBox top = new HBox();

        top.setSpacing(15);
        top.setAlignment(Pos.CENTER);
        Text bl = new Text();
        bl.setText("Bookstore Login");

        VBox loginBox = new VBox();
        loginBox.setPadding(new Insets(30,65,45,65));
        loginBox.setSpacing(6);
        Text username = new Text("Username");

        Text password = new Text("Password");
        myLogin.setMinWidth(174);
        loginBox.getChildren().addAll(username, userText, password, passText, myLogin);

        if(loginError){
            Text error = new Text("Invalid Input");
            error.setFill(Color.PINK);
            loginBox.getChildren().add(error);
        }

        VBox logScene = new VBox();
        logScene.getChildren().addAll(top, loginBox);
        logScene.setPadding(new Insets(80, 280, 200, 150));
        logScene.setSpacing(70);

        log.getChildren().addAll(logScene);
        return log;
    }
    
    public VBox adminHome() {
        VBox v = new VBox();
        v.setAlignment(Pos.CENTER);
        v.setSpacing(20);
        v.setPadding(new Insets(80,0,30,0));
        VBox buttons = new VBox();
        myBook.setPrefSize(150,50);
        myCustomers.setPrefSize(150,50);
        myLogout.setPrefSize(150, 50);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(20);
        buttons.getChildren().addAll(myBook, myCustomers);
        v.getChildren().addAll(buttons, myLogout);
        return v;
    }
    
    public Group adminBookScene() {
        Group aBS = new Group();//Group helps with rendering all the buttons/"children" at once
        hb.getChildren().clear();
        bookTable.setFocusModel(FocusModel);
        bookTable.getItems().clear();
        bookTable.getColumns().clear();
        
        Label label = new Label("Books Table");
        label.setFont(new Font("Arial", 20));

        TableColumn<Book, Double> priceColumn = new TableColumn<>("Book Price");//Constructing book table
        priceColumn.setMinWidth(110);
        priceColumn.setStyle("-fx-alignment: CENTER;");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        TableColumn<Book, String> titleColumn = new TableColumn<>("Book Name");
        titleColumn.setMinWidth(200);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
       
        final TextField addBookName = new TextField();
        addBookName.setPromptText("Book Name");
        addBookName.setMaxWidth(titleColumn.getPrefWidth());
        final TextField addBookPrice = new TextField();
        addBookPrice.setMaxWidth(priceColumn.getPrefWidth());
        addBookPrice.setPromptText("Price");
        VBox core = new VBox();
        final Button addButton = new Button("Add");
        Label bookAddErr = new Label("Invalid Input");
        bookAddErr.setTextFill(Color.color(1,0,0));
        
        bookTable.setItems(addBook());
        bookTable.getColumns().addAll(titleColumn, priceColumn);
        Button myDelete = new Button("Delete");
        
        myDelete.setOnAction(e -> {
            Book selectedItem = bookTable.getSelectionModel().getSelectedItem(); 
            bookTable.getItems().remove(selectedItem);
            Admin.books.remove(selectedItem);
        });
        
        addButton.setOnAction(e -> {
            try {
                double price = Math.round((Double.parseDouble(addBookPrice.getText()))*100);
                Admin.books.add(new Book(addBookName.getText(), price/100));
                bookTable.getItems().clear(); 
                bookTable.setItems(addBook());
                addBookName.clear(); 
                addBookPrice.clear();
                core.getChildren().remove(bookAddErr);
                
            }
            catch (Exception exception){
                if(!core.getChildren().contains(bookAddErr)){
                    core.getChildren().add(bookAddErr);
                
                   
                }
            }
        });
        
        hb.getChildren().addAll(addBookName, addBookPrice, addButton, myDelete);
        hb.setSpacing(3);
        hb.setAlignment(Pos.CENTER);

        HBox backButton = new HBox();
        backButton.setPadding(new Insets(5));
        backButton.getChildren().addAll(goBack);

        core.setAlignment(Pos.CENTER);
        core.setSpacing(5);
        core.setPadding(new Insets(0, 0, 0, 150));
        core.getChildren().addAll(label, bookTable, hb);

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(0, 200, 60, 0));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(backButton, core);

        aBS.getChildren().addAll(vbox);

        return aBS;
    }
        
    public Group adminCustomerScene() {
        Group aCS = new Group();//Group helps with rendering all the buttons/"children" at once
        hb.getChildren().clear();
        customersTable.getItems().clear();
        customersTable.getColumns().clear();

        Label label = new Label("Customer Table");
        label.setFont(new Font("Arial", 20));

        TableColumn<Customer, String> passwordColumn = new TableColumn<>("Password");//Constructing Customer Table//
        passwordColumn.setMinWidth(140);
        passwordColumn.setStyle("-fx-alignment: CENTER;");
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        TableColumn<Customer, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setMinWidth(140);
        usernameCol.setStyle("-fx-alignment: CENTER;");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        
        TableColumn<Customer, Integer> pointsCol = new TableColumn<>("Points");
        pointsCol.setMinWidth(100);
        pointsCol.setStyle("-fx-alignment: CENTER;");
        pointsCol.setCellValueFactory(new PropertyValueFactory<>("points"));

        final TextField addUsername = new TextField();
        addUsername.setPromptText("Username");
        addUsername.setMaxWidth(usernameCol.getPrefWidth());
        final TextField addPassword = new TextField();
        addPassword.setMaxWidth(passwordColumn.getPrefWidth());
        addPassword.setPromptText("Password");
        
        customersTable.setItems(addCustomers());
        customersTable.getColumns().addAll(usernameCol, passwordColumn, pointsCol);
        
        VBox vo = new VBox();
        Text cAddErr = new Text("Customer already exists!");
        cAddErr.setFill(Color.color(1,0,0));
        final Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            boolean duplicate = false;

            for(Customer c: admin.getCustomers()){
                if((c.getUsername().equals(addUsername.getText()) && c.getPassword().equals(addPassword.getText())) ||
                        (addUsername.getText().equals(admin.getUsername()) && addPassword.getText().equals(admin.getPassword()))){
                    duplicate = true;
                    if(!vo.getChildren().contains(cAddErr)){
                        vo.getChildren().add(cAddErr);
                    }
                }
            }

            if(!(addUsername.getText().equals("") || addPassword.getText().equals("")) && !duplicate) {
                admin.addCustomer(new Customer(addUsername.getText(), addPassword.getText())); 
                customersTable.getItems().clear(); 
                customersTable.setItems(addCustomers());
                vo.getChildren().remove(cAddErr); 
                addPassword.clear(); 
                addUsername.clear();
            }
        });

        final Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            Customer selectedItem = customersTable.getSelectionModel().getSelectedItem();
            customersTable.getItems().remove(selectedItem);
            admin.deleteCustomer(selectedItem); 
        });

        hb.getChildren().addAll(addUsername, addPassword, addButton, deleteButton);
        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(3);

        HBox back = new HBox();
        back.setPadding(new Insets(5));
        back.getChildren().addAll(goBack);
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(0, 150, 60, 0));
        vbox.getChildren().addAll(back, vo);
        vbox.setAlignment(Pos.CENTER);
        
        vo.setAlignment(Pos.CENTER);
        vo.setSpacing(5);
        vo.setPadding(new Insets(0,0,0,110));
        vo.getChildren().addAll(label, customersTable, hb);

        aCS.getChildren().addAll(vbox);
        return aCS;
    }
        
    public Group customerHome(int type){
        Group cH = new Group();//Group helps with rendering all the buttons/"children" at once
        bookTable.getItems().clear();
        bookTable.getColumns().clear();
        bookTable.setFocusModel(null);

        Font font = new Font(14);
        Text message1 = new Text("Welcome, " + customer.getUsername() + ".");
        message1.setFont(font);
        Text message2 = new Text(" Your status is: ");
        message2.setFont(font);
        Text message3 = new Text(customer.getStatus());
        message3.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 14));
        Text points = new Text(", You have " + customer.getPoints()+ " points");
        points.setFont(font);

        TableColumn<Book, String> titleColumn = new TableColumn<>("Book Name");//Constructing table
        titleColumn.setMinWidth(200);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, Double> priceColumn = new TableColumn<>("Book Price");
        priceColumn.setMinWidth(100);
        priceColumn.setStyle("-fx-alignment: CENTER;");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Book, String> selectColumn = new TableColumn<>("Select");
        selectColumn.setMinWidth(100);
        selectColumn.setStyle("-fx-alignment: CENTER;");
        selectColumn.setCellValueFactory(new PropertyValueFactory<>("select"));

        bookTable.setItems(addBook());
        bookTable.getColumns().addAll(titleColumn, priceColumn, selectColumn);

        HBox info = new HBox();
        info.getChildren().addAll(message2, message3, points);
        BorderPane header = new BorderPane();
        header.setLeft(message1);
        header.setRight(info);
        HBox bottom = new HBox();
        bottom.setAlignment(Pos.BOTTOM_CENTER);
        bottom.setSpacing(5);
        bottom.getChildren().addAll(myBuy, redeemPoints, myLogout);
        myLogout.setPrefSize(70, 20);

        VBox vbox = new VBox();
       
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(40, 200, 30, 100));
        vbox.getChildren().addAll(header, bookTable, bottom);
        cH.getChildren().addAll(vbox);

        return cH;
    }

    public Group finalScreen(boolean redeemedPoints){
        Group check = new Group();
        double total, sub = 0, discount;
        int bookCount = 0, pointsEarned, i = 0;
        String[][] booksBought = new String[25][2];

        for(Book b: Admin.books){
            if(b.getSelect().isSelected()){
                sub += b.getPrice();
                booksBought[i][0] = b.getTitle();
                booksBought[i][1] = String.valueOf(b.getPrice());
                i++;
            }
        }

        if(redeemedPoints){
            if((double)customer.getPoints()/100 >= sub){
                discount = sub;
                customer.setPoints(-(int)sub*100);
            }
            else{
                discount = ((double)customer.getPoints()/100);
                customer.setPoints(-customer.getPoints());
            }
        }else discount = 0;

        total = sub - discount;
        pointsEarned = (int)total*10;
        customer.setPoints(pointsEarned);

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER);
        header.setSpacing(15);
        header.setPadding(new Insets(0,0,25,0));

        VBox receipt = new VBox();
        receipt.setSpacing(7);
        Text receiptTxt = new Text("Purchase Complete!");
        receiptTxt.setFont(Font.font(null, FontWeight.BOLD, 12));
        Line thickLine = new Line(0, 150, 400, 150);
        thickLine.setStrokeWidth(3);
        receipt.getChildren().addAll(receiptTxt, thickLine);

        VBox receiptItems = new VBox();
        receiptItems.setSpacing(7);
        for (i = 0; i<25; i++) {
            if(booksBought[i][0] != null){
                Text bookTitle = new Text(booksBought[i][0]);
                Text bookPrice = new Text(booksBought[i][1]);
                BorderPane item = new BorderPane();
                item.setLeft(bookTitle);
                item.setRight(bookPrice);
                Line thinLine = new Line(0, 150, 400, 150);
                receiptItems.getChildren().addAll(item, thinLine);
                bookCount++;
            }
        }

        Text totalCost = new Text("Total Cost: $" + (Math.round(total*100.0))/100.0);
        totalCost.setFont(new Font("Arial", 30));
        Text totalPoints = new Text("Points: " + customer.getPoints());
        totalPoints.setFont(new Font("Arial", 30));
        Text totalStatus = new Text("Current Status: " + customer.getStatus());
        totalStatus.setFont(new Font("Arial", 30));
        totalCost.setFont(new Font("Arial", 30));
        receipt.getChildren().addAll(totalCost, totalPoints, totalStatus);

        VBox bottom = new VBox();
        bottom.getChildren().addAll(myLogout);

        VBox screen = new VBox();
        screen.setPadding(new Insets(60,105,500,100));
        screen.setAlignment(Pos.CENTER);
        screen.setSpacing(10);
        screen.getChildren().addAll(header, receipt, bottom);

        check.getChildren().addAll(screen);
        Admin.books.removeIf(b -> b.getSelect().isSelected());
        return check;
    }
    
    Button myLogin = new Button("Login");
    Button myBook = new Button("Books");
    Button myCustomers = new Button("Customers");
    Button myLogout = new Button("Logout");
    Button goBack = new Button("Back");
    Button myBuy = new Button("Buy");
    Button redeemPoints = new Button("Redeem points & Buy");
    HBox hb = new HBox();
    TextField userText = new TextField();
    PasswordField passText = new PasswordField();

    TableView<Customer> customersTable = new TableView<>();
    ObservableList<Customer> customers = FXCollections.observableArrayList();

    public ObservableList<Customer> addCustomers(){
        customers.addAll(admin.getCustomers());
        return customers;
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Bookstore App");

        primaryStage.setScene(new Scene(loginScene(false), 600, 550));
        primaryStage.show();

        myLogin.setOnAction(e -> {
            boolean logged_in = false;

            if(userText.getText().equals("admin") && passText.getText().equals("admin")) {
                primaryStage.setScene(new Scene(adminHome(), 600, 550));
                logged_in = true;
            }
            for(Customer custom: admin.getCustomers()) {
                if (userText.getText().equals(custom.getUsername()) && passText.getText().equals(custom.getPassword())) {
                    customer = custom;
                    primaryStage.setScene(new Scene(customerHome(0), 600, 550));
                    logged_in = true;
                }
            }
            if(!logged_in) {
                primaryStage.setScene(new Scene(loginScene(true), 600, 550));
            }
        });

        myLogout.setOnAction(e -> { //Initializing all buttons with generic (non-changing) functions
            primaryStage.setScene(new Scene(loginScene(false), 600, 550));
        });

        myBook.setOnAction(e -> {
                primaryStage.setScene(new Scene(adminBookScene(), 600, 550));
        });

        myCustomers.setOnAction(e -> {
            primaryStage.setScene(new Scene(adminCustomerScene(), 600, 550));
                });
        
        goBack.setOnAction(e -> {
                primaryStage.setScene(new Scene(adminHome(), 600, 550));
        });

        redeemPoints.setOnAction(e -> {
            boolean bookSelected = false;
            for(Book b: Admin.books) {
                if (b.getSelect().isSelected()) {
                    bookSelected = true;
                }
            }
            if(!bookSelected){
                primaryStage.setScene(new Scene(customerHome(1), 600, 550));
            } else if(customer.getPoints() == 0){
                primaryStage.setScene(new Scene(customerHome(2), 600, 550));
            } else if(customer.getPoints() != 0){
                primaryStage.setScene(new Scene(finalScreen(true), 600, 550));
            }
        });

        myBuy.setOnAction(e -> {
            boolean bookSelected = false;
            for(Book b: Admin.books) {
                if (b.getSelect().isSelected()) {
                    bookSelected = true;
                }
            }
            if(bookSelected){
                primaryStage.setScene(new Scene(finalScreen(false), 600, 550));
            } else primaryStage.setScene(new Scene(customerHome(1), 600, 550));
        }); 
        
        primaryStage.setOnCloseRequest(e -> {//When the application is closed, the information in the customer and book tables will be copied into text files
            try {
                admin.bookReset();
                admin.customerReset();
                admin.bookWrite(admin.books);
                admin.customerWrite(admin.getCustomers());
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });        
    try {
        admin.restockArrays();
    }
    catch (IOException e){  
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
