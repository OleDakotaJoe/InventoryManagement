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
    /**
     * Creates an instance of the <code>Inventory</code> class.
     */
    private static final Inventory inventory = new Inventory();
    /**
     * Creates an instance of the <code>AtomicInteger</code> class.
     */
    private static final AtomicInteger productIdCounter = new AtomicInteger();
    /**
     * Creates an instance of the <code>AtomicInteger</code> class.
     */
    private static final AtomicInteger partIdCounter = new AtomicInteger();


    /**
     * Getter for retrieving the <code>inventory</code> instance.
     * @return
     * Returns the <code>inventory</code> instance.
     */
    public static Inventory getInventory() {
        return inventory;
    }


    /**
     * Gets and increments the count fo the <code>partIdCounter</code>.
     * @return
     * Returns the string value of the partIdCounter.
     */
    public static String createPartId() {
        String currentCounter = String.valueOf(partIdCounter.getAndIncrement());
        return currentCounter;
    }

    /**
     * Gets current count fo the <code>partIdCounter</code>.
     * @return
     * Returns the string value of the partIdCounter.
     */
    public static AtomicInteger getPartIdCounter() {
        return partIdCounter;
    }

    /**
     * Sets the value of the <code>partIdCounter</code>.
     * This method is used in when loading part data in the <code>InventoryData</code> class.
     * @param partIdCounter
     * The int value to be set as the <code>partIdCounter</code>
     */
    public static void setPartIdCounter(int partIdCounter) {
        Controller.partIdCounter.set(partIdCounter);
    }


    /**
     * Gets and increments the count for the <code>productIdCounter</code>.
     * @return
     * Returns the string value of the productIdCounter.
     */
    public static String createProductId() {
        String currentCounter = String.valueOf(productIdCounter.getAndIncrement());
        return currentCounter;
    }

    /**
     * Gets current count fo the <code>productIdCounter</code>.
     * @return
     * Returns the string value of the productIdCounter.
     */
    public static AtomicInteger getProductIdCounter() {
        return productIdCounter;
    }

    /**
     * Sets the value of the <code>productIdCounter</code>.
     * This method is used in when loading part data in the <code>InventoryData</code> class.
     * @param productIdCounter
     * The int value to be set as the <code>productIdCounter</code>
     */
    public static void setProductIdCounter(int productIdCounter) {
        Controller.productIdCounter.set(productIdCounter);
    }


    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML private Button addPart;

    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML private Button modifyPart;
    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML private Button addProduct;

    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML private Button modifyProduct;

    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML private Button exitButton;

    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML private TableView<Part> partTable;

    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML private TableColumn<Part, Integer> partId;

    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML private TableColumn<Part, String> partName;

    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML private TableColumn<Part, Integer> partStock;

    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML private TableColumn<Part, Double> partPrice;
    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML private TableView<Product> productTable;

    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML private TableColumn<Product, Integer> productId;

    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML private TableColumn<Product, String> productName;

    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML private TableColumn<Product, Integer> productStock;

    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML private TableColumn<Product, Double> productPrice;

    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML private TextFieldLimited searchProducts;

    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML private TextFieldLimited searchParts;


    /**
     * Upon initializtion of the main.fxml file, this method is called and loads all inventory data, and sets the values of the table views.
     */
    @FXML
    private void initialize(){
        Node node = addPart;
        node.requestFocus();
        displayTableView();
        try {
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
        }
    }

    /**
     * Connects the <code>partsTable</code> and the <code>productsTable</code> to the appropriate values.
     */
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

    /**
     * Passes a reference to the selected <code>Part</code> to the <code>PassableData.partData</code> field.
     */
    public void passPartToPassableData() {
        int index = partTable.getSelectionModel().getFocusedIndex();
        if(index >=0) {
            Part part = inventory.getAllParts().get(index);
            PassableData.setPartIdIndex(index);
            PassableData.setPartData(part);
            PassableData.setOutsourced(part.getClass().equals(Outsourced.class));
        }
    }
    /**
     * Passes a reference to the selected <code>Product</code> to the <code>PassableData.productData</code> field.
     */
    public void passProductToPassableData() {
        int index = productTable.getSelectionModel().getFocusedIndex();
        if(index >= 0) {
            Product product = inventory.getAllProducts().get(index);
            PassableData.setProductIdIndex(index);
            PassableData.setProductData(product);
        }

    }


    /**
     * Opens the Add/Modify Part Form
     * Checks to see whether the source of the event was the <code>addPart</code> or <code>modifyPart</code> buttons.
     * Passes part data when appropriate, and sets appropriate values for the <code>modifyPart</code> field in the <code>PassableData</code> class.
     * if there are no parts in the <code>partTable</code> and <code>modifyPart</code> was the button that was clicked, displays an error message, stating that no part has been created.
     * @param event
     * The <code>ActionEvent</code> object that is passed when calling the method.
     */
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
    /**
     * Opens the Add/Modify Product Form
     * Checks to see whether the source of the event was the <code>addProduct</code> or <code>modifyProduct</code> buttons.
     * Passes part data when appropriate, and sets appropriate values for the <code>modifyProduct</code> field in the <code>PassableData</code> class.
     * if there are no products in the <code>productTable</code> and <code>modifyProduct</code> was the button that was clicked, displays an error message, stating that no Product has been created.
     * @param event
     * The <code>ActionEvent</code> object that is passed when calling the method.
     */
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

    /**
     * Searches for parts based on the partID or the partName.
     * Automatically selects the part associated with the <code>partName</code> or <code>partId</code>.
     * If the part input is an integer, it searches by Id first then by name, if the input is non-integer it searches only by name.
     * @return
     * returns true if part is found, returns false if part is not found.
     */
    public boolean searchParts() {
        try {
            if (searchParts.getText().matches("[\\p{Digit}]+")) {
            partTable.getSelectionModel().select(inventory.lookupPart(Integer.parseInt(searchParts.getText())));
            if(inventory.lookupPart(Integer.parseInt(searchParts.getText())) == null){
                partTable.getSelectionModel().select(inventory.lookupPart(searchParts.getText()));
                System.out.println("fired");
                if (inventory.lookupPart(searchParts.getText()) == null) return false;
            }

            } else {
                partTable.getSelectionModel().select(inventory.lookupPart(searchParts.getText()));
                if (inventory.lookupPart(searchParts.getText()) == null) return false;
            }
        } catch(NumberFormatException e) {
            e.printStackTrace();
        }
        return true;

    }
    /**
     * When the user presses enter while typing in a part name or ID, if part is not found, displays a "No Parts Found!" message.
     * Uses the <code>searchParts()</code> method and if the method returns false, displays an informational message alerting the user that
     * the part is not found
     */
    public void searchPartsOnEnter() {
        if(!searchParts()) {
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("No Parts Found!");
            info.setHeaderText("There were no parts found by that description!");
            info.setContentText("Please check your spelling, and try again. You may also search by Part ID.");
            info.show();
        }
    }

    /**
     * Searches for products based on the <code>productId</code> or <code>productName</code>.
     * Automatically selects the part associated with the <code>productId</code> or <code>productName</code>.
     * If the part input is an integer, it searches by Id first then by name, if the input is non-integer it searches only by name.
     *
     * @return
     * returns true if part is found, returns false if part is not found.
     */
    public boolean searchProducts() {
        try {
            if (searchProducts.getText().matches("[\\p{Digit}]+")) {
                productTable.getSelectionModel().select(Controller.getInventory().lookupProduct(Integer.parseInt(searchProducts.getText())));
                if(Controller.getInventory().lookupProduct(Integer.parseInt(searchProducts.getText())) == null) {
                    productTable.getSelectionModel().select(Controller.getInventory().lookupProduct(searchProducts.getText()));
                    if (Controller.getInventory().lookupProduct(searchProducts.getText()) == null) return false;
                }
            } else {
                productTable.getSelectionModel().select(Controller.getInventory().lookupProduct(searchProducts.getText()));
                if (Controller.getInventory().lookupProduct(searchProducts.getText()) == null) return false;
            }
        } catch(NumberFormatException e) {
            System.out.println(e);
        }
        return true;
    }
    /**
     * Deletes the selected part after displaying a confirmation message to the user.
     * If the part is associated with any products, displays an error message stating which product it is associated with, and that it cannot be deleted
     */
    public void deletePart() {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm Delete Part");
        confirmation.setHeaderText("Are you sure you want to delete this part?");
        confirmation.setContentText("This action cannot be undone! Only proceed if you are sure you want to delete this part!");
        confirmation.showAndWait();
        if (confirmation.getResult().getButtonData().isCancelButton()) return;

        Part currentPart = partTable.getSelectionModel().getSelectedItem();
        boolean isDeleted = inventory.deletePart(currentPart);;

        Product associatedProduct = null;
        for(Product product : inventory.getAllProducts()) {
            if(product.getAllAssociatedParts().contains(currentPart)){
                associatedProduct = product;
                break;
            }
        }

        if (!isDeleted) {

            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
            errorMessage.setTitle("Part Cannot be Deleted!");
            errorMessage.setHeaderText("This part has products associated with it.");
            errorMessage.setContentText("In order to delete this part, you must first delete all parts " +
                    "associated with " + associatedProduct.getName() +". After you do this, you may delete the part.");
            errorMessage.show();
            return;
        }

        try {
            InventoryData.getInstance().storePartInventory();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * When the user presses enter while typing in a <code>productId</code> or <code>productName</code>, if product is not found, displays a "No products Found!" message.
     * Uses the <code>searchProducts()</code> method and if the method returns false, displays an informational message alerting the user that
     * the product is not found
     */
    public void searchProductsOnEnter() {
        if(!searchProducts()) {
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("No Products Found!");
            info.setHeaderText("There were no Products found by that description!");
            info.setContentText("Please check your spelling, and try again. You may also search by Product ID.");
            info.show();
        }
    }
    /**
     * Deletes the selected product after displaying a confirmation message to the user.
     * If the product has any parts associated with it, displays an error message stating that it has parts associated with it, and that it cannot be deleted.
     */
    public void deleteProduct() {
        Product currentProduct = productTable.getSelectionModel().getSelectedItem();
        if (currentProduct == null) return;
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm Delete Product");
        confirmation.setHeaderText("Are you sure you want to delete this product?");
        confirmation.setContentText("This action cannot be undone! Only proceed if you are sure you want to delete this product!");
        confirmation.showAndWait();
        if (confirmation.getResult().getButtonData().isCancelButton()) return;

        boolean isDeleted = inventory.deleteProduct(currentProduct);
        if (!isDeleted) {

            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
            errorMessage.setTitle("Product Cannot be Deleted!");
            errorMessage.setHeaderText("This product has parts associated with it.");
            errorMessage.setContentText("In order to delete this product, you must first remove all parts " +
                    "from it's associated parts list. After you do this, you may delete the product.");
            errorMessage.show();
            return;
        }



        try {
            InventoryData.getInstance().storeProductInventory();
        } catch (IOException e) {
            e.printStackTrace();
         }
    }

    /**
     * Adds functionality to the <code>exitButton</code> element which closes the program, and stores all Inventory Data.
     */
    @FXML
    public void exit(){
        try {
            InventoryData.getInstance().storePartInventory();
            InventoryData.getInstance().storePartIdIndex();
            InventoryData.getInstance().storeProductIdIndex();
            InventoryData.getInstance().storeProductInventory();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();

    }

}
