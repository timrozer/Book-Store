package coe528project;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Admin extends User{
    public static ArrayList<Book> books = new ArrayList<>(); 
    private static final ArrayList<Customer> customers = new ArrayList<>();

    public void addCustomer(Customer created){
        customers.add(created);
    }

    public void deleteCustomer(Customer selected){
        customers.remove(selected);
    }
    
    public ArrayList<Customer> getCustomers(){
        return (ArrayList<Customer>) customers.clone();
    }
    public void restockArrays() throws IOException {
        ArrayList<Book> keyBook = this.readBook();
        ArrayList<Customer> keyCustomers = this.readCustomer();
        books.addAll(keyBook);
        customers.addAll(keyCustomers);
    }
    
    public void bookWrite(ArrayList<Book> books) throws IOException{
        FileWriter bookFile = new FileWriter("book.txt", true);
        for(Book boo: books){
            String bookInfo = boo.getTitle() + ", " + boo.getPrice() + "\n";
            bookFile.write(bookInfo);
        }
        bookFile.close();
    }

    public void bookReset() throws IOException {
        FileWriter bookFile = new FileWriter("book.txt", false);
        bookFile.close();
    }
    
    public ArrayList<Book> readBook() throws IOException{
        Scanner scan = new Scanner(new FileReader("book.txt"));
        ArrayList<Book> tempBookHolder = new ArrayList<>();

        while(scan.hasNext()) {
            String[] bookInfo = scan.nextLine().split(",");
            String title = bookInfo[0];
            double price = Double.parseDouble(bookInfo[1]);
            tempBookHolder.add(new Book(title, price));
        }
        return tempBookHolder;
    }
    
    public void customerWrite(ArrayList<Customer> customers) throws IOException{
        FileWriter customerFile = new FileWriter("customer.txt", true);
        for(Customer cust: customers){
            String outputText = cust.getUsername() + ", " + cust.getPassword() + ", " + cust.getPoints() + "\n";
            customerFile.write(outputText);
        }
        customerFile.close();
    }

    public void customerReset() throws IOException {
        FileWriter customerFile = new FileWriter("customer.txt", false);
        customerFile.close();
    }

    public ArrayList<Customer> readCustomer() throws IOException{
        Scanner scan = new Scanner(new FileReader("customer.txt"));
        ArrayList<Customer> keyCustomerHolder = new ArrayList<>();

        while(scan.hasNext()) {
            String[] cInfo = scan.nextLine().split(", ");
            String user = cInfo[0];
            String pass = cInfo[1];
            int points = Integer.parseInt(cInfo[2]);
            keyCustomerHolder.add(new Customer(user, pass));
            keyCustomerHolder.get(keyCustomerHolder.size()-1).setPoints(points);
        }
        return keyCustomerHolder;
    }
}
