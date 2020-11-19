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
    private static final Inventory inventory = new Inventory();
    private static final AtomicInteger productIdCounter = new AtomicInteger();
    private static final AtomicInteger partIdCounter = new AtomicInteger();


    public static Inventory getInventory() {
        return inventory;
    }


    public static String createPartId() {
        String currentCounter = String.valueOf(partIdCounter.getAndIncrement());
        return currentCounter;
    }

    public static AtomicInteger getPartIdCounter() {
        return partIdCounter;
    }

    public static void setPartIdCounter(int partIdCounter) {
        Controller.partIdCounter.set(partIdCounter);
    }


    public static String createProductId() {
        String currentCounter = String.valueOf(productIdCounter.getAndIncrement());
        return currentCounter;
    }

    public static AtomicInteger getProductIdCounter() {
        return productIdCounter;
    }

    public static void setProductIdCounter(int productIdCounter) {
        Controller.productIdCounter.set(productIdCounter);
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

    @FXML private TableView<Product> productTable;

    @FXML private TableColumn<Product, Integer> productId;

    @FXML private TableColumn<Product, String> productName;

    @FXML private TableColumn<Product, Integer> productStock;

    @FXML private TableColumn<Product, Double> productPrice;

    @FXML private TextFieldLimited searchProducts;

    @FXML private TextFieldLimited searchParts;



    @FXML
    private void initialize(){
        Node node = addPart;
        node.requestFocus();
        displayTableView();
/*        try {
            InventoryData.getInstance().loadIdIndex();
            InventoryData.getInstance().loadPartInventory();
            InventoryData.getInstance().loadProductIdIndex();
            InventoryData.getInstance().loadProductInventory();
        } catch (IOException e) {
            e.printStackTrace();
            //Creates files if files did not exist
            try {
                InventoryData.getInstance().storePartIdIndex();
                InventoryData.getInstance().storePartInventory();
                InventoryData.getInstance().storeProductIdIndex();
                InventoryData.getInstance().storeProductInventory();

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }*/
    }


    private void displayTableView() {
        partId.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partStock.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        partTable.setItems(inventory.getAllParts());

        productId.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productStock.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        productPrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        productTable.setItems(inventory.getAllProducts());


    }

    public void passPartToPassableData() {
        int index = partTable.getSelectionModel().getFocusedIndex();
        if(index >=0) {
            Part part = inventory.getAllParts().get(index);
            PassableData.setPartIndex(index);
            PassableData.setPartData(part);
            PassableData.setOutsourced(part.getClass().equals(Outsourced.class));
        }
    }

    public void passProductToPassableData() {
        int index = productTable.getSelectionModel().getFocusedIndex();
        if(index >= 0) {
            Product product = inventory.getAllProducts().get(index);
            PassableData.setProductIndex(index);
            PassableData.setProductData(product);
        }

    }


    public void openAddPartMenu(ActionEvent event) {

        if (event.getSource().equals(addPart)) {
            PassableData.setIsModifyPart(false);
        }
        if (event.getSource().equals(modifyPart) && partTable.getSelectionModel().getFocusedIndex() >= 0) {
            PassableData.setIsModifyPart(true);
            passPartToPassableData();
        } else if(event.getSource().equals(modifyPart) && partTable.getSelectionModel().getFocusedIndex() < 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No parts in Inventory");
            alert.setHeaderText("You haven't created any parts yet!");
            alert.setContentText("This action can not be completed. No parts found in inventory. " +
                    "Try adding a part first.");
            alert.show();
            return;
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
        if (event.getSource().equals(addProduct)) {
            PassableData.setModifyProduct(false);
        }

        if(event.getSource().equals(modifyProduct) && productTable.getSelectionModel().getFocusedIndex() >= 0) {
            PassableData.setModifyProduct(true);
            passProductToPassableData();
        } else if(event.getSource().equals(modifyProduct) && productTable.getSelectionModel().getFocusedIndex() < 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No products in Inventory");
            alert.setHeaderText("You haven't created any products yet!");
            alert.setContentText("This action can not be completed. No products found in inventory. " +
                    "Try adding a part first.");
            alert.show();
            return;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/inventory/modifyproduct/addmodifyproduct.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            root1.requestFocus();
            stage.show();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }


    public boolean searchPartsType() {
        try {
            if (searchParts.getText().matches("[\\p{Digit}]+")) {
            partTable.getSelectionModel().select(inventory.lookupPart(Integer.parseInt(searchParts.getText())));
            if(inventory.lookupPart(Integer.parseInt(searchParts.getText())) == null) return false;

            } else {
                partTable.getSelectionModel().select(inventory.lookupPart(searchParts.getText()));
                if (inventory.lookupPart(searchParts.getText()) == null) return false;
            }
        } catch(NumberFormatException e) {
            e.printStackTrace();
        }
        return true;

    }

    public void searchPartsEnter() {
        if(!searchPartsType()) {
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("No Parts Found!");
            info.setHeaderText("There were no parts found by that description!");
            info.setContentText("Please check your spelling, and try again. You may also search by Part ID.");
            info.show();
        }
    }

    public boolean searchProductsOnType() {
        try {
            if (searchProducts.getText().matches("[\\p{Digit}]+")) {
                productTable.getSelectionModel().select(Controller.getInventory().lookupProduct(Integer.parseInt(searchProducts.getText())));
                if(Controller.getInventory().lookupProduct(Integer.parseInt(searchProducts.getText())) == null) return false;
            } else {
                productTable.getSelectionModel().select(Controller.getInventory().lookupProduct(searchProducts.getText()));
                if (Controller.getInventory().lookupProduct(searchProducts.getText()) == null) return false;
            }
        } catch(NumberFormatException e) {
            System.out.println(e);
        }
        return true;
    }

    public void searchProductsOnEnter() {
        if(!searchProductsOnType()) {
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("No Parts Found!");
            info.setHeaderText("There were no parts found by that description!");
            info.setContentText("Please check your spelling, and try again. You may also search by Part ID.");
            info.show();
        }
    }

    public void deletePart() {
        boolean hasAssociatedProduct = false;
        Product associatedProduct = null;

        Part currentPart = partTable.getSelectionModel().getSelectedItem();
        for(Product product : inventory.getAllProducts()) {
            if(product.getAllAssociatedParts().contains(currentPart)){
                hasAssociatedProduct = true;
                associatedProduct = product;
                break;
            }
        }

        if (hasAssociatedProduct) {

                Alert errorMessage = new Alert(Alert.AlertType.ERROR);
                errorMessage.setTitle("Part Cannot be Deleted!");
                errorMessage.setHeaderText("This part has products associated with it.");
                errorMessage.setContentText("In order to delete this part, you must first delete all parts " +
                        "associated with " + associatedProduct.getName() +". After you do this, you may delete the part.");
                errorMessage.show();
                return;
        }


        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm Delete Part");
        confirmation.setHeaderText("Are you sure you want to delete this part?");
        confirmation.setContentText("This action cannot be undone! Only proceed if you are sure you want to delete this part!");
        confirmation.showAndWait();
        if (confirmation.getResult().getButtonData().isCancelButton()) return;

        inventory.deletePart(currentPart);
        /*try {
            InventoryData.getInstance().storePartInventory();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    public void deleteProduct() {
        Product currentProduct = productTable.getSelectionModel().getSelectedItem();
        if (currentProduct == null) return;
        boolean hasAssociatedPart = false;
        int counter = 0;
        for(Part part : currentProduct.getAllAssociatedParts()) {
            counter ++;
            if(counter > 0){
                hasAssociatedPart = true;
                break;
            }
        }

        if (hasAssociatedPart) {

            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
            errorMessage.setTitle("Product Cannot be Deleted!");
            errorMessage.setHeaderText("This product has parts associated with it.");
            errorMessage.setContentText("In order to delete this product, you must first remove all parts " +
                    "from it's associated parts list. After you do this, you may delete the product.");
            errorMessage.show();
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm Delete Product");
        confirmation.setHeaderText("Are you sure you want to delete this product?");
        confirmation.setContentText("This action cannot be undone! Only proceed if you are sure you want to delete this product!");
        confirmation.showAndWait();
        if (confirmation.getResult().getButtonData().isCancelButton()) return;
        inventory.deleteProduct(currentProduct);

        /*try {
            InventoryData.getInstance().storeProductInventory();
        } catch (IOException e) {
            e.printStackTrace();
         }*/
    }
    @FXML
    public void exit(){
/*        try {
            InventoryData.getInstance().storePartInventory();
            InventoryData.getInstance().storePartIdIndex();
            InventoryData.getInstance().storeProductIdIndex();
            InventoryData.getInstance().storeProductInventory();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();

    }

}
