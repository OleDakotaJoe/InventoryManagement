package com.inventory.data.datamodel;

/**
 * An abstract class of the type Part which is implemented as either a InHouse part or an Outsourced Part
 */
public abstract class Part {
    /**
     * int value for the Id of the part
     */
    private int id;
    /**
     * String value of the name of the part
     */
    private String name;
    /**
     * Double value of the price of the part
     */
    private double price;
    /**
     * int value of the Stock of the part
     */
    private int stock;
    /**
     * int value of the Minimum stock level of the part
     */
    private int min;
    /**
     * int value of the Maximum stock level of the part
     */
    private int max;

    /**
     * Constructor for the Part class
     * @param id
     * int value for the Id of the part
     * @param name
     * String value of the name of the part
     * @param price
     * Double value of the price of the part
     * @param stock
     * int value of the Stock of the part
     * @param min
     * int value of the Minimum stock level of the part
     * @param max
     * int value of the Maximum stock level of the part
     */
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return
     * Returns the id of the part
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the part
     * @param id
     * Represents the new Id to be set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return
     * returns the String value of the ID of the part
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the String value of the name of the part
     * @param name
     * String value to be set as the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return
     * Returns the Double value of the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the new price of a part
     * @param price
     * the double value of to be set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return
     * Returns the int value of the current stock of an item
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets the current stock level of a part
     * @param stock
     * The int value to be set as the new current stock level
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return
     * Returns the minimum stock level of a Part
     */
    public int getMin() {
        return min;
    }

    /**
     * Sets the minimum stock level of a part
     * @param min
     * The int value to be set
     */
    public void setMin(int min) {
        this.min = min;
    }
    /**
     * @return
     * Returns the maximum stock level of a Part
     */
    public int getMax() {
        return max;
    }
    /**
     * Sets the maximum stock level of a part
     * @param max
     * The int value to be set
     */
    public void setMax(int max) {
        this.max = max;
    }
}
