package com.inventory.data.datamodel;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This Class holds the data structure for an Product to be created.
 */
public class Product {
    /**
     * <code>ObservableList</code> of the type <code>Part</code> used to hold all parts that are associated with this product.
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /**
     * Field which holds the int value representing the product's ID number.
     */
    private int id;
    /**
     * Field which holds the String value representing the product's name.
     */
    private String name;
    /**
     * Field which holds the double value representing the product's name.
     */
    private double price;
    /**
     * Field which holds the int value representing the product's current quantity in inventory.
     */
    private int stock;
    /**
     * Field which holds the int value representign the product's minimum quantity in inventory.
     */
    private int min;
    /**
     * Field which holds the int value representign the product's maximum quantity in inventory.
     */
    private int max;

    /**
     * Constructor for the <code>Product</code> class.
     * @param id
     * int value to be set as <code>id</code>.
     * @param name
     * String value to be set as <code>name</code>.
     * @param price
     * Double value to be set as <code>price</code>.
     * @param stock
     * int value to be set as <code>stock</code>.
     * @param min
     * int value to be set as <code>min</code>.
     * @param max
     * int value to be set as <code>max</code>.
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }


    /**
     * Getter for the <code>id</code> field.
     * @return
     * Returns the current value of the field <code>id</code>/
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for the <code>id</code> field.
     * @param id
     * int value to be set as <code>id</code>.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for the <code>name</code> field.
     * @return
     * Returns the current value of the field <code>name</code>
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the <code>name</code> field.
     * @param name
     * String value to be set as <code>name</code>
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the <code>price</code> field.
     * @return
     * Returns the current value of the <code>price</code> field.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Setter for the <code>price</code> field.
     * @param price
     * Double value to be set as <code>price</code>.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Getter for the <code>stock</code> field.
     * @return
     * Returns the current value of the <code>stock</code> field.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Setter for the <code>stock</code> field.
     * @param stock
     * int value to be set as <code>stock</code>.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Getter for the <code>min</code> field.
     * @return
     * Returns the current value of he <code>min</code> field.
     */
    public int getMin() {
        return min;
    }

    /**
     * Setter for the <code>min</code> field.
     * @param min
     * int value to be set as <code>min</code>.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Getter for the <code>max</code> field.
     * @return
     * Returns the current value
     */
    public int getMax() {
        return max;
    }

    /**
     * Setter for the <code>max</code> field.
     * @param max
     * int value to be set as <code>max</code>.
     */
    public void setMax(int max) {
        this.max = max;
    }


    /**
     * Adds a <code>Part</code> to the <code>associatedParts</code> list.
     * @param part
     * <code>Part</code> object to be added to the list.
     */
    public void addAssociatePart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Deletes a part from the <code>associatedParts</code> list.
     * @param selectedAssociatedPart
     * <code>Part</code> object to be removed.
     * @return
     * Returns true, because part has been deleted.
     */
    public boolean deleteAssociatedParts(Part selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }

    /**
     * Getter for the <code>associatedParts</code> list.
     * @return
     * Returns the <code>ObservableList</code> containg all <code>associatedParts</code>.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
