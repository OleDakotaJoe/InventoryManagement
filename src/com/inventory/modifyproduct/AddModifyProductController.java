package com.inventory.modifyproduct;

import com.inventory.Controller;
import com.inventory.controls.TextFieldLimited;
import com.inventory.data.datamodel.Part;
import com.inventory.data.datamodel.PassableData;
import com.inventory.data.datamodel.Product;
import com.inventory.data.datamodel.Validator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class AddModifyProductController extends Validator {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }

    public void addAssociatedPart(Part part) {
        this.associatedParts.add(part);
    }

    @FXML public Label addModLabel;

    @FXML public TextFieldLimited productId;

    @FXML public TextFieldLimited productName;

    @FXML public TextFieldLimited productStock;

    @FXML public TextFieldLimited productPrice;

    @FXML public TextFieldLimited maxStock;

    @FXML public TextFieldLimited minStock;

    @FXML public TextFieldLimited searchParts;

    @FXML public TableView<Part> associatedPartTable;

    @FXML public TableView<Part> availablePartTable;

    @FXML public TableColumn<Part, Integer> availablePartId;

    @FXML public TableColumn<Part, String> availablePartName;

    @FXML public TableColumn<Part, Integer> availablePartStock;

    @FXML public TableColumn<Part, Double> availablePartPrice;

    @FXML public TableColumn<Part, Integer> associatedPartId;

    @FXML public TableColumn<Part, String> associatedPartName;

    @FXML public TableColumn<Part, Integer> associatedPartStock;

    @FXML public TableColumn<Part, Double> associatedPartPrice;

    @FXML public Button addPartButton;

    @FXML public Button addProductAndSave;

    @FXML public Button cancelButton;

    @FXML public Button removeAssociatedPart;


    @FXML public void initialize() {
        displayTableView();
        addModLabel.setText(PassableData.getProductTitle());

        if(PassableData.isModifyProduct() && PassableData.getProductData() != null) {
            populateForm();
        } else {
            productId.setText(Controller.createProductId());
        }

    }

    private void displayTableView() {
        availablePartId.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        availablePartName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        availablePartStock.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        availablePartPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        availablePartTable.setItems(Controller.getInventory().getAllParts());

        associatedPartId.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        associatedPartStock.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        associatedPartPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        associatedPartTable.setItems(associatedParts);

    }

    private void populateForm() {
        Product selectedProduct =  PassableData.getProductData();
        productPrice.setText(String.valueOf(selectedProduct.getPrice()));
        productId.setText(String.valueOf(selectedProduct.getId()));
        productName.setText(selectedProduct.getName());
        productStock.setText(String.valueOf(selectedProduct.getStock()));
        minStock.setText(String.valueOf(selectedProduct.getMin()));
        maxStock.setText(String.valueOf(selectedProduct.getMax()));
        getAssociatedParts().addAll(selectedProduct.getAllAssociatedParts());
        int counter = 0;
        for(Part part : selectedProduct.getAllAssociatedParts()) {
            int partId = part.getId();
            Part partInInventory = Controller.getInventory().lookupPart(partId);
            if (partInInventory != part) {
                getAssociatedParts().set(counter, partInInventory);
            }
            counter++;
        }


    }

    public void addPartToAssociatedParts() {
        int partIndex = availablePartTable.getSelectionModel().getFocusedIndex();
        if (partIndex >= 0) {
            Part associatedPart = Controller.getInventory().getAllParts().get(partIndex);
            addAssociatedPart(associatedPart);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No parts to add!");
            alert.setHeaderText("There are no parts in your inventory!");
            alert.setContentText("You have not added any parts to your inventory. To build a product" +
                    "you must first go back to the main screen and add parts to the inventory. After " +
                    "you complete this step you may return to this form and create a product.");
            alert.show();
        }
    }

    public void removePartFromAssociatedParts() {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm Remove Part");
        confirmation.setHeaderText("Are you sure you want to remove this part from the product?");
        confirmation.setContentText("After removing the part, if you wish to re-add it you will need to select" +
                "the part from the table above, and click \"add\" to add it again.");
        confirmation.showAndWait();
        if (confirmation.getResult().getButtonData().isCancelButton()) return;

        int partIndex = associatedPartTable.getSelectionModel().getFocusedIndex();
        if(partIndex >= 0 ){
            getAssociatedParts().remove(partIndex);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No part to remove");
            alert.setHeaderText("No parts are associated with this product!");
            alert.setContentText("No part has been removed, you may continue building your product.");
            alert.show();
        }
    }

    public void addProductToInventory() {

        if(isValidProduct()) {
            double price = Double.parseDouble(productPrice.getText());
            String name = productName.getText();
            int stock = Integer.parseInt(productStock.getText());
            int id = Integer.parseInt(productId.getText());
            int minimum = Integer.parseInt(minStock.getText());
            int maximum = Integer.parseInt(maxStock.getText());


            Product newProduct= new Product(id,name,price,stock,minimum,maximum);

            if (PassableData.isModifyProduct()) {
                Controller.getInventory().updateProduct(PassableData.getProductIndex(), newProduct);
                for (Part part : associatedParts) {
                    newProduct.addAssociatePart(part);
                }
            } else {
                Controller.getInventory().addProduct(newProduct);
                for (Part part : associatedParts) {
                    newProduct.addAssociatePart(part);
                }
            }
            /*try {
                InventoryData.getInstance().storeProductIdIndex();
                InventoryData.getInstance().storeProductInventory();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            exit();

        }

    }

    public boolean isValidProduct() {
        boolean result = false;
        boolean isComplete = !productPrice.getText().equals("")
                && !productName.getText().equals("")
                && !productStock.getText().equals("")
                && !productId.getText().equals("")
                && !maxStock.getText().equals("")
                && !minStock.getText().equals("");
        boolean isValidPrice = isDoubleValid(productPrice);
        boolean isValidName = isCSVTextValid(productName);
        boolean isValidId = isIntegerValid(productId);
        boolean isValidMax = isIntegerValid(maxStock);
        boolean isValidMin = isIntegerValid(minStock);
        boolean isMinLessThanMax = false;
        if (isComplete)
            isMinLessThanMax = Integer.parseInt(maxStock.getText()) > Integer.parseInt(minStock.getText());

        if (!isComplete) {
            Alert errorMessage = new Alert(Alert.AlertType.INFORMATION);
            errorMessage.setTitle("Missing Information");
            errorMessage.setHeaderText("You didn't enter required information!");
            errorMessage.setContentText("All fields are required! Your product has not been saved. Press \"OK\" and try again.");
            errorMessage.show();
        } else if (!isMinLessThanMax) {
            Alert errorMessage = new Alert(Alert.AlertType.INFORMATION);
            errorMessage.setTitle("Invalid Entry");
            errorMessage.setHeaderText("Min must be less than Max!");
            errorMessage.setContentText("Re-enter your data! Your product has not been saved.Press \"OK\" and try again.");
            errorMessage.show();
        } else if (!isValidPrice) {
            Alert errorMessage = new Alert(Alert.AlertType.INFORMATION);
            errorMessage.setTitle("Price is not valid");
            errorMessage.setHeaderText("The value you entered for price is not valid");
            errorMessage.setContentText("Please ensure that the value you have entered does" +
                    " not include more than one decimal point (.), does not contain any letters,  and does not " +
                    "have more than two digits after the decimal.  ");
            errorMessage.show();
        } else if (!isValidName) {
            Alert errorMessage = new Alert(Alert.AlertType.INFORMATION);
            errorMessage.setTitle("Invalid Name");
            errorMessage.setHeaderText("The value you entered for name is not valid.");
            errorMessage.setContentText("Please ensure that the text you enter does not" +
                    " include any quotation marks," +
                    "(\"), or commas (,).");
            errorMessage.show();
        } else if (!isValidId) {
            Alert errorMessage = new Alert(Alert.AlertType.INFORMATION);
            errorMessage.setTitle("Incorrect ID");
            errorMessage.setHeaderText("The value you entered for ID is not valid");
            errorMessage.setContentText("Please ensure that the value you have entered" +
                    " is a whole number, with no letters, symbols or decimal points.  ");
            errorMessage.show();
        } else if (!isValidMax) {
            Alert errorMessage = new Alert(Alert.AlertType.INFORMATION);
            errorMessage.setTitle("Incorrect Max Inventory");
            errorMessage.setHeaderText("The value you entered for Max is not valid");
            errorMessage.setContentText("Please ensure that the value you have entered" +
                    " is a whole number, with no letters, symbols or decimal points.  ");
            errorMessage.show();
        } else if (!isValidMin) {
            Alert errorMessage = new Alert(Alert.AlertType.INFORMATION);
            errorMessage.setTitle("Incorrect Min Inventory");
            errorMessage.setHeaderText("The value you entered for Min is not valid");
            errorMessage.setContentText("Please ensure that the value you have entered" +
                    " is a whole number, with no letters, symbols or decimal points.  ");
            errorMessage.show();
        } else {
            result = true;
        }

        return result;
    }

    public void textFieldValidator(KeyEvent event) {
        TextFieldLimited source =(TextFieldLimited) event.getSource();
        if (source.equals(productId)) {
            isIntegerValid(event);
        } else if (source.equals(maxStock)) {
            isIntegerValid(event);
        } else if (source.equals(productName)) {
            isCSVTextValid(event);
        } else if (source.equals(productStock)) {
            isIntegerValid(event);
        } else if (source.equals(minStock)) {
            isIntegerValid(event);
        } else if (source.equals(productPrice)) {
            isDoubleValid(event);
        }
    }


    public boolean searchPartsType() {
        try{
        if (searchParts.getText().matches("[\\p{Digit}]+")) {
                availablePartTable.getSelectionModel().select(Controller.getInventory().lookupPart(Integer.parseInt(searchParts.getText())));
                if(Controller.getInventory().lookupPart(Integer.parseInt(searchParts.getText())) == null) return false;
            } else {
                availablePartTable.getSelectionModel().select(Controller.getInventory().lookupPart(searchParts.getText()));
                if (Controller.getInventory().lookupPart(searchParts.getText()) == null) return false;
            }
        } catch (NumberFormatException e) {
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

    @FXML
    public void exit(){
        /*try {
            InventoryData.getInstance().storeProductIdIndex();
            InventoryData.getInstance().storeProductInventory();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        PassableData.setModifyProduct(false);
    }


}
