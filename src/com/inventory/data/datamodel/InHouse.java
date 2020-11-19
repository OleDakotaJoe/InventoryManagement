package com.inventory.data.datamodel;

/**
 * Class that extends the Part class.
 * This class designates a part that is made within the company.
 */
public class InHouse extends Part {
    private int machineId;

    /**
     * Constructs an inHouse Part.
     * @param id
     *
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineId
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }


    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
