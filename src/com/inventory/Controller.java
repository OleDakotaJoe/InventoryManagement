package com.inventory;

import com.inventory.addModifyPart.AddModifyPartController;
import com.inventory.addModifyProduct.AddModifyProductController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Controller {
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
    private GridPane main;

    @FXML
    private void initialize() {
        Node node = addPart;

        node.requestFocus();
    }

    public void openAddPartMenu(ActionEvent event) {


        if (event.getSource().equals(addPart)) {
            AddModifyPartController window = new AddModifyPartController("Add Part");
        } else if (event.getSource().equals(modifyPart)){
            AddModifyPartController window = new AddModifyPartController("Modify Part");
        } else {
            try {
                throw new Exception("Problem with code");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void openAddProductMenu(ActionEvent event) {

        if (event.getSource().equals(addProduct)) {
            AddModifyProductController window = new AddModifyProductController("Add Product");
        } else if (event.getSource().equals(modifyProduct)){
            AddModifyProductController window = new AddModifyProductController("Modify Product");
        } else {
            try {
                throw new Exception("Problem with code");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    public void exit(){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();

    }




}
