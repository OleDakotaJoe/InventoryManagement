package com.inventory.data.datamodel;

/**
 * Class that extends the Part class.
 * This class designates a part that is made within the company.
 */
public class InHouse extends Part {
    private int machineId;

    /**
     * Constructs an <code>inHouse</code> Part.
     * @param id
     * an int for setting the id.
     * @param name
     * A string field for setting the name of the part.
     * @param price
     * A double for setting the price of the a part.
     * @param stock
     * An int for setting the current stock level of the part.
     * @param min
     * An int for setting the minimum stock level of the part.
     * @param max
     * An int for setting the maximum stock level of the part.
     * @param machineId
     * An int for setting the id of the machine that creates the part.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }


    /**
     * Getter that returns the machineId of the machine that creates the part.
     * @return
     * returns the machineId of the part.
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * Setter that sets the machineId of an InHouse Part
     * @param machineId
     * The int value of to be set as the <code>machineId</code>
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
