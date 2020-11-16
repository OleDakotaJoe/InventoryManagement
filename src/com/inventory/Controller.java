package com.inventory;

import com.inventory.controls.TextFieldLimited;
import com.inventory.data.datamodel.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class Controller {

    private static Inventory inventory = new Inventory();
    public static Inventory getInventory() {
        return inventory;
    }

    public static AtomicInteger getIdCounter() {
        return idCounter;
    }

    public static void setIdCounter(int idCounter) {
        Controller.idCounter.set(idCounter);
    }

    private static AtomicInteger idCounter = new AtomicInteger();


    public static String createId() {
        String currentCounter = String.valueOf(idCounter.getAndIncrement());
        return currentCounter;
    }

    @FXML private Button addPart;

    @FXML private Button modifyPart;

    @FXML private Button addProduct;

    @FXML private Button modifyProduct;

    @FXML private Button deletePart;

    @FXML private Button exitButton;

    @FXML private TableView<Part> partTable;

    @FXML private TableColumn<Part, Integer> partId;

    @FXML private TableColumn<Part, String> partName;

    @FXML private TableColumn<Part, Integer> partStock;

    @FXML private TableColumn<Part, Double> partPrice;

    @FXML private TextFieldLimited searchProducts;

    @FXML private TextFieldLimited searchParts;

    @FXML private TableView<Product> productTable;


    @FXML
    private void initialize() {
        Node node = addPart;
        node.requestFocus();
        displayTableView();
        try {
            InventoryData.getInstance().readIdIndex();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        partStock.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

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
    /*public void passProductToPassableData() {
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


    public void searchParts() {
        try {
            if (searchParts.getText().matches("[\\p{Digit}]+")) {
            partTable.getSelectionModel().select(inventory.lookupPart(Integer.parseInt(searchParts.getText())));

            } else {
                partTable.getSelectionModel().select(inventory.lookupPart(searchParts.getText()));
            }
        } catch(NumberFormatException e) {
            System.out.println(e);

        }

    }

    public void searchProducts() {
        try {
            if (searchProducts.getText().matches("[\\p{Digit}]+")) {
                productTable.getSelectionModel().select(inventory.lookupProduct(Integer.parseInt(searchProducts.getText())));

            } else {
                productTable.getSelectionModel().select(inventory.lookupProduct(searchProducts.getText()));
            }
        } catch(NumberFormatException e) {
            System.out.println(e);

        }
    }

    public void deletePart() {

        // TODO: 11/16/2020 Make sure to implement functionality to check if part is currently associated with any products 
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm Delete Part");
        confirmation.setHeaderText("Are you sure you want to delete this part?");
        confirmation.setContentText("This action cannot be undone! Only proceed if you are sure you want to delete this part!");
        confirmation.showAndWait();
        if (confirmation.getResult().getButtonData().isCancelButton()) return;
        inventory.deletePart(partTable.getSelectionModel().getSelectedItem());
        try {
            InventoryData.getInstance().storePartInventory();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

/*    public void deleteProduct() {
        inventory.deleteProduct(productTable.getSelectionModel().getSelectedItem());
    }*/
    @FXML
    public void exit(){
        try {
            InventoryData.getInstance().storePartInventory();
            InventoryData.getInstance().storeIdIndex();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();

    }

}
