package com.inventory;

import com.inventory.data.datamodel.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    private static Inventory inventory = new Inventory();

    public static Inventory getInventory() {
        return inventory;
    }
    @FXML private Button addPart;

    @FXML private Button modifyPart;

    @FXML private Button addProduct;

    @FXML private Button modifyProduct;

    @FXML private Button exitButton;

    @FXML private TableView<Part> partTable;

    @FXML private TableColumn<Part, Integer> partId;

    @FXML private TableColumn<Part, String> partName;

    @FXML private TableColumn<Part, Integer> inventoryLevel;

    @FXML private TableColumn<Part, Double> price;




    @FXML
    private void initialize() {
        Node node = addPart;
        node.requestFocus();
        displayTableView();

    }


    private void displayTableView() {
        try {
            InventoryData.getInstance().loadPartInventory();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Disregard the error the first time you run the program.");
        }

        partId.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        inventoryLevel.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        price.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        partTable.setItems(inventory.getAllParts());
    }

    public void passPartToPassableData() {
        int index = partTable.getSelectionModel().getFocusedIndex();
        Part part = inventory.getAllParts().get(index);
        PassableData.setPartIndex(index);
        PassableData.setPartData(part);
        if(part.getClass().equals(Outsourced.class)) {
            PassableData.setOutsourced(true);
        } else  {
            PassableData.setOutsourced(false);
        }

    }

 /*   public void passProductToPassableData() {
        ObservableList<Part> part = productTable.getSelectionModel().getSelectedItems();
    }*/


    public void openAddPartMenu(ActionEvent event) {

        if (event.getSource().equals(addPart)) {
            PassableData.setTitle("Add Part");
        }
        if (event.getSource().equals(modifyPart)) {
            PassableData.setTitle("Modify Part");
            passPartToPassableData();
            PassableData.setIsModify(true);
        }
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
        // TODO: 11/14/2020 FINISH
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
        try {
            InventoryData.getInstance().storeTodoItems();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();

    }

}
