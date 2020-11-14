package com.inventory.data.datamodel;

public class UserData {

    private static String userData;


    public static String getUserData() {
        return userData;
    }

    public static void setUserData(String userData) {
        UserData.userData = userData;
    }
}
