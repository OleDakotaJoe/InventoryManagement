package com.inventory;

import com.inventory.data.datamodel.Inventory;
import com.inventory.data.datamodel.UserData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    private static Inventory inventory;

    public static Inventory getInventory() {
        return inventory;
    }
    @FXML
    private Button addPart;

    @FXML
    private Button modifyPart;

    @FXML
    private Button addProduct;

    @FXML
    private Button modifyProduct;

    @FXML
    private Button exitButton;


    @FXML
    private void initialize() {
        Node node = addPart;
        node.requestFocus();
        inventory = new Inventory();
    }


    public void openAddPartMenu(ActionEvent event) {

        if (event.getSource().equals(addPart)) UserData.setUserData("Add Part");
        if (event.getSource().equals(modifyPart)) UserData.setUserData("Modify Part");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/inventory/modifypart/addmodifypart.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));

            stage.show();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    public void openAddProductMenu(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/inventory/modifyproduct/addmodifyproduct.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (
                IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    public void exit(){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();

    }

}
