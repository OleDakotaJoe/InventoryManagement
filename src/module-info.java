module InventoryManagement {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.inventory;
    exports com.inventory.modifypart;
    exports com.inventory.modifyproduct;
}