package com.inventory.data.datamodel;

public class PassableData {

    private static Part partData;
    private static int partIndex;

    private static Product productData;

    private static String title;
    private static boolean isModify;
    private static boolean outsourced;
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


    public static boolean getIsModify() {
        return isModify;
    }

    public static void setIsModify(boolean modify) {
        PassableData.isModify = modify;
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

    public static String getTitle() {
        return title;
    }

    public static void setTitle(String title) {
        PassableData.title = title;
    }
}
