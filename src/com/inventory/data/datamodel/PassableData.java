package com.inventory.data.datamodel;

public class PassableData {

    private static Part partData;
    private static int partIndex;
    private static String partTitle;
    private static boolean modifyPart;
    private static boolean outsourced;


    private static Product productData;
    private static String productTitle;
    private static boolean modifyProduct;
    private static int productIndex;

    public static String getProductTitle() {
        return productTitle;
    }

    public static void setProductTitle(String productTitle) {
        PassableData.productTitle = productTitle;
    }

    public static boolean isModifyProduct() {
        return modifyProduct;
    }

    public static void setModifyProduct(boolean modifyProduct) {
        if(modifyProduct) {
            setProductTitle("Modify Product");
        } else {
            setProductTitle("Add Product");
        }
        PassableData.modifyProduct = modifyProduct;
    }

    public static int getProductIndex() {
        return productIndex;
    }

    public static void setProductIndex(int productIndex) {
        PassableData.productIndex = productIndex;
    }

    public static boolean isOutsourced() {
        return outsourced;
    }

    public static void setOutsourced(boolean outsourced) {
        PassableData.outsourced = outsourced;
    }

    public static int getPartIndex() {
        return partIndex;
    }

    public static void setPartIndex(int partIndex) {
        PassableData.partIndex = partIndex;
    }


    public static boolean isModifyPart() {
        return modifyPart;
    }

    public static void setIsModifyPart(boolean modify) {
        if(modify) {
            setPartTitle("Modify Part");
        } else {
            setPartTitle("Add Part");
        }
        PassableData.modifyPart = modify;
    }

    public static Part getPartData() {
        return partData;
    }

    public static void setPartData(Part partData) {
        PassableData.partData = partData;
    }

    public static Product getProductData() {
        return productData;
    }

    public static void setProductData(Product productData) {
        PassableData.productData = productData;
    }

    public static String getPartTitle() {
        return partTitle;
    }

    public static void setPartTitle(String partTitle) {
        PassableData.partTitle = partTitle;
    }
}
