package coe528project;

import javafx.scene.control.CheckBox;

public class Book {

    private final String bookName;
    private final double bookPrice;
    public CheckBox check;

    public Book(String bookName, double bookPrice) {
        this.bookName = bookName;
        this.bookPrice = bookPrice;
        check = new CheckBox();
    }

    public String getTitle() {
        return this.bookName;
    }

    public double getPrice() {
        return this.bookPrice;
    }

    public CheckBox getSelect() {
        return check;
    }

    public void setSelect(CheckBox select) {
        this.check = select;
    }
}
