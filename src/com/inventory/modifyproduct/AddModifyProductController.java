package com.inventory.modifyproduct;

import com.inventory.Controller;
import com.inventory.controls.TextFieldLimited;
import com.inventory.data.datamodel.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for FXML file addmodifyproduct.fxml
 *
 * Contains all event handling for Add/Modify Product form.
 *
 * Extends the Validator class, to gain its functionality and aid in textField Validation.
 */
public class AddModifyProductController extends Validator {

    /**
     * An <code>ObservableList</code> to temporarily hold all associatedParts.
     * Instantiated when the class is instantiated.
     */
    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * Getter for <code>associateParts</code> list.
     * @return
     * Returns <code>associateParts</code>
     */
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }

    /**
     * Adds a <code>Part</code> to <code>associateParts</code> list.
     * @param part
     * <code>Part</code> to be added.
     */
    public void addAssociatedPart(Part part) {
        this.associatedParts.add(part);
    }
    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML public Label addModLabel;
    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML public TextFieldLimited productId;
    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML public TextFieldLimited productName;
    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML public TextFieldLimited productStock;
    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML public TextFieldLimited productPrice;
    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML public TextFieldLimited maxStock;
    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML public TextFieldLimited minStock;
    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML public TextFieldLimited searchParts;
    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML public TableView<Part> associatedPartTable;
    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML public TableView<Part> availablePartTable;
    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML public TableColumn<Part, Integer> availablePartId;
    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML public TableColumn<Part, String> availablePartName;
    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML public TableColumn<Part, Integer> availablePartStock;
    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML public TableColumn<Part, Double> availablePartPrice;
    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML public TableColumn<Part, Integer> associatedPartId;
    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML public TableColumn<Part, String> associatedPartName;
    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML public TableColumn<Part, Integer> associatedPartStock;
    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML public TableColumn<Part, Double> associatedPartPrice;
    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML public Button addPartButton;
    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML public Button addProductAndSave;
    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML public Button cancelButton;
    /**
     * Points to corresponding element in .fxml file.
     */
    @FXML public Button removeAssociatedPart;


    /**
     * Called when the addmodifyproduct.fxml files is initialized.
     * Checks whether or not the product is to be modified or if it is added, and then populates the form if applicable, or creates a new
     * <code>productId</code> if applicable.
     */
    @FXML public void initialize() {
        displayTableView();
        addModLabel.setText(PassableData.getProductTitle());

        if(PassableData.isModifyProduct() && PassableData.getProductData() != null) {
            populateForm();
        } else {
            productId.setText(Controller.createProductId());
        }

    }

    /**
     * Connects the <code>availabePartsTable</code> and the <code>associatedPartsTable</code> to the appropriate values.
     */
    private void displayTableView() {
        availablePartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        availablePartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        availablePartStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        availablePartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        availablePartTable.setItems(Controller.getInventory().getAllParts());

        associatedPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedPartTable.setItems(associatedParts);

    }

    /**
     * Populates the Modify Product form with applicable data.
     */
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

    /**
     * Adds the selected part from the <code>availablePartsTable</code> to the <code>associatedPartsTable</code> or displays an error message if no parts have been created yet.
     */
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

    /**
     * Removes a part from the <code>associatedPartsTable</code>
     * This method first displays a confirmation dialog to confirm the user's actions. A
     * If the user confirms it removes the selected part from the <code>associatedPartsTable</code> as well as the <code>associatedParts</code> <code>ObservableList</code>.
     */
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

    /**
     * Checks for validity and completeness of all information in the Add/Modify Product form
     * Uses the extended functionality of the <code>Validator</code> class to correct text input, and check input for validity.
     * If The information is incomplete or invalid, displays an error message to the user stating the proper format, as well as input type for each field.
     */
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
                Controller.getInventory().updateProduct(PassableData.getProductIdIndex(), newProduct);
                for (Part part : associatedParts) {
                    newProduct.addAssociatePart(part);
                }
            } else {
                Controller.getInventory().addProduct(newProduct);
                for (Part part : associatedParts) {
                    newProduct.addAssociatePart(part);
                }
            }
            try {
                InventoryData.getInstance().storeProductIdIndex();
                InventoryData.getInstance().storeProductInventory();
            } catch (IOException e) {
                e.printStackTrace();
            }
            exit();

        }

    }

    /**
     * <h1>This method tests whether or not the entries into the Add/Modify Product form's fields are complete and valid input. </h1>
     * <p> The test checks first that there is valid text entries into each of the text fields
     *      * located on the add/modify product form.</p>
     * <p>The isDoubleValid(), isCSVTextValid(), and isIntegerValid() methods are members of the <code>Validator</code> class
     *      * which the Controller class extends. Each of these methods currently take a parameter which is required
     *      * to designate which field the method is validating. These overloaded methods only check for validity, but also
     *      * have an overloaded method which will correct the errors as well. this is used in textFieldValidator An alert message for each item that may be invalid is provided, even though they
     *      * currently are not all able to be used. This way if a change is made to the Validator class, which no longer
     *      * corrects the invalid text, or if a bug is introduced, the user will receive a Alert that informs them of their
     *      * errors and directions on how to provide valid input.</p>
     * @return
     * The method returns true if all text is complete and valid according to data type, and false otherwise.
     */
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

    /**<h1>Validates and checks for proper format of Data types</h1>
     *  <p>This method is used for validation of text fields. It checks for event type, then validates
     *      * based on the source based on what data should be in the field.</p>
     *  <p>The variable "source" refers to the source of the event, must be a <code>TextField</code>. </p>
     * @param event
     * Must be called by an event listener that passes a <code>KeyEvent</code>, and the source must be a <code>TextField</code>.
     */
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


    /**
     * Searches for parts based on the partID or the partName.
     * Automatically selects the part associated with the <code>partName</code> or <code>partId</code>.
     * If the part input is an integer, it searches by Id first then by name, if the input is non-integer it searches only by name.
     * @return
     * returns true if part is found, returns false if part is not found.
     */
    public boolean searchParts() {
        try{
        if (searchParts.getText().matches("[\\p{Digit}]+")) {
                availablePartTable.getSelectionModel().select(Controller.getInventory().lookupPart(Integer.parseInt(searchParts.getText())));
                if(Controller.getInventory().lookupPart(Integer.parseInt(searchParts.getText())) == null) {
                    availablePartTable.getSelectionModel().select(Controller.getInventory().lookupPart(searchParts.getText()));
                    if (Controller.getInventory().lookupPart(searchParts.getText()) == null) return false;
                };
            } else {
                availablePartTable.getSelectionModel().select(Controller.getInventory().lookupPart(searchParts.getText()));
                if (Controller.getInventory().lookupPart(searchParts.getText()) == null) return false;
            }
        } catch (NumberFormatException e) {
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
     * Adds functionality to the cancel button to close the window, as well as store the current partIDIndex and set <code>PassableData.modifyPart</code> to false.
     */
    @FXML
    public void exit(){
        try {
            InventoryData.getInstance().storeProductIdIndex();
            InventoryData.getInstance().storeProductInventory();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        PassableData.setModifyProduct(false);
    }


}
