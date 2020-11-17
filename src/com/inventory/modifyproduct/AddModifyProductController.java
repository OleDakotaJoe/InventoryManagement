package com.inventory.modifyproduct;

import com.inventory.Controller;
import com.inventory.controls.TextFieldLimited;
import com.inventory.data.datamodel.Part;
import com.inventory.data.datamodel.PassableData;
import com.inventory.data.datamodel.Product;
import com.inventory.data.datamodel.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AddModifyProductController extends Validator {

    @FXML public Label addModLabel;

    @FXML public TextFieldLimited productId;

    @FXML public TextFieldLimited productName;

    @FXML public TextFieldLimited productStock;

    @FXML public TextFieldLimited productPrice;

    @FXML public TextFieldLimited maxStock;

    @FXML public TextFieldLimited minStock;

    @FXML public TextFieldLimited partSearch;

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

        if(PassableData.isModifyProduct() && PassableData.getPartData()!= null) {
            populateForm();
        } else {
            //productId.setText(Controller.createProductId());
        }
    }

    private void displayTableView() {
        availablePartId.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        availablePartName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        availablePartStock.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        availablePartPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        availablePartTable.setItems(Controller.getInventory().getAllParts());


    }

    private void populateForm() {
        Product selectedProduct =  PassableData.getProductData();
        productPrice.setText(String.valueOf(selectedProduct.getPrice()));
        productId.setText(String.valueOf(selectedProduct.getId()));
        productName.setText(selectedProduct.getName());
        productStock.setText(String.valueOf(selectedProduct.getStock()));
        minStock.setText(String.valueOf(selectedProduct.getMin()));
        maxStock.setText(String.valueOf(selectedProduct.getMax()));
    }




}
