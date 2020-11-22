package com.inventory.data.datamodel;

/**
 * This class extends the <code>Part</code> class
 * An i
 */
public class Outsourced extends Part {
    /**
     * String value to represent the name of the Company that makes the Part
     */
    private String companyName;

    /**
     * Constucts an instnce of the <code>Outsourced</code> class.
     * @param id
     * int value for the ID of the part
     * @param name
     * String value for the name of the part
     * @param price
     * Double value for the price of the part
     * @param stock
     * int value for the current inventory level of the part
     * @param min
     * int value representing the minimum allowable quantity of the part
     * @param max
     * int value representign the maximum allowable quantity of the part
     * @param companyName
     * String value representing the name of the company that makes the part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @return
     * Returns the <code>companyName</code> variable
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets a new company name
     * @param companyName
     * String value of the companyName to be set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
