package com.inventory.data.datamodel;

/**
 * Acts as a data-container and middleware for between the different view-controller classes.
 */
public class PassableData {

    /**
     * Stores a reference to a Part Object.
     */
    private static Part partData;

    /**
     * Stores the int value of the partIndex
     */
    private static int partIdIndex;

    /**
     * Stores a String value of the title of the addPart/modifyPart Label
     */
    private static String partTitle;
    /**
     * stores the boolean value which indicates whether or not the AddModifyPart window is  for adding of modifying a part
     */
    private static boolean modifyPart;
    /**
     * stoes the boolean value which indicates whether or not the part is outsourced or not.
     */
    private static boolean outsourced;


    /**
     * Stores a reference to a Product object
     */
    private static Product productData;
    /**
     * Stores a Strign value of the title of the Add/Modify Product Label
     */
    private static String productTitle;
    /**
     * Stores a boolean value which represents whether or not the Product is being modified or added
     */
    private static boolean modifyProduct;
    /**
     * Stores the int value of the productIdIndex
     */
    private static int productIdIndex;

    /**
     * Getter for the title of the Product
     * @return
     * Returns <code>productTitle</code>
     */
    public static String getProductTitle() {
        return productTitle;
    }

    /**
     * Sets the <code>productTitle</code> field.
     * @param productTitle
     * The String value to be set as the new <code>productTitle</code>.
     */
    public static void setProductTitle(String productTitle) {
        PassableData.productTitle = productTitle;
    }

    /**
     * Getter for the <code>modifyProduct</code> field.
     * @return
     * Returns the boolean value <code>modifyProduct</code>.
     */
    public static boolean isModifyProduct() {
        return modifyProduct;
    }

    /**
     * Sets boolean value of <code>modifyProduct</code> and sets <code>productTitle</code> based
     * on whether or not <code>modifyProduct</code> is true or false.
     * @param modifyProduct
     * Boolean value to be set as <code>modifyProduct</code>.
     */
    public static void setModifyProduct(boolean modifyProduct) {
        if(modifyProduct) {
            setProductTitle("Modify Product");
        } else {
            setProductTitle("Add Product");
        }
        PassableData.modifyProduct = modifyProduct;
    }

    /**
     * Getter fro the <code>productIdIndex</code> field.
     * @return
     * Returns <code>productIdIndex</code>.
     */
    public static int getProductIdIndex() {
        return productIdIndex;
    }

    /**
     * Setter for the <code>productIdIndex</code> value.
     * @param productIdIndex
     * int value to be set as the <code>productIdIndex</code>.
     */
    public static void setProductIdIndex(int productIdIndex) {
        PassableData.productIdIndex = productIdIndex;
    }

    /**
     * Getter for <code>outsourced</code> value.
     * @return
     * Returns boolean value for <code>outsourced</code>
.     */
    public static boolean isOutsourced() {
        return outsourced;
    }

    /**
     * Setter for the <code>outsourced</code> value.
     * @param outsourced
     * boolean value to be set as <code>outsourced</code>.
     */
    public static void setOutsourced(boolean outsourced) {
        PassableData.outsourced = outsourced;
    }

    /**
     * Getter for <code>partIdIndex</code> field.
     * @return
     * Returns the current value of <code>partIdIndex</code>.
     */
    public static int getPartIdIndex() {
        return partIdIndex;
    }

    /**
     * Setter for the <code>partIdIndex</code> field.
     * @param partIdIndex
     * int Value to be set as <code>partIdIndex</code>.
     */
    public static void setPartIdIndex(int partIdIndex) {
        PassableData.partIdIndex = partIdIndex;
    }


    /**
     * Getter for the <code>modifyPart</code> field.
     * @return
     * Returns boolean value for <code>modifyPart</code>.
     */
    public static boolean isModifyPart() {
        return modifyPart;
    }

    /**
     * Setter for the <code>modifyPart</code> field.
     * @param modify
     * Boolean value to be set as <code>modifyPart</code>.
     */
    public static void setIsModifyPart(boolean modify) {
        if(modify) {
            setPartTitle("Modify Part");
        } else {
            setPartTitle("Add Part");
        }
        PassableData.modifyPart = modify;
    }

    /**
     * Getter for the <code>partData</code> field.
     * @return
     * Returns a <code>Part</code> Object. This is used for modifying current selected Part.
     */
    public static Part getPartData() {
        return partData;
    }

    /**
     * Setter for the <code>partData</code> field.
     * @param partData
     * <code>Part</code> Object to be set in the <code>partData</code> field.
     */
    public static void setPartData(Part partData) {
        PassableData.partData = partData;
    }

    /**
     * Getter for the <code>productData</code> field.
     * @return
     * Returns a <code>Product</code> Object. This is used for modifying current selected Product.
     */
    public static Product getProductData() {
        return productData;
    }

    /**
     * Setter for the <code>productData</code> field.
     * @param productData
     * <code>Part</code> Object to be set in the <code>partData</code> field.
     */
    public static void setProductData(Product productData) {
        PassableData.productData = productData;
    }

    /**
     * Getter for the <code>partTitle</code> value.
     * @return
     * Returns current value for <code>partTitle</code> field.
     */
    public static String getPartTitle() {
        return partTitle;
    }

    /**
     * Setter for the <code>partTitle</code> field.
     * @param partTitle
     * String value to be set as <code>partTitle</code>.
     */
    public static void setPartTitle(String partTitle) {
        PassableData.partTitle = partTitle;
    }
}
