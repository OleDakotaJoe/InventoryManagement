package com.inventory.addModifyPart;

import com.inventory.dataModel.InHouse;
import com.inventory.dataModel.Part;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddModifyPartController {
    public AddModifyPartController(String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addModifyPart.fxml"));
            Parent root1 = fxmlLoader.load();
            fxmlLoader.setController(this);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(title);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (
                IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public TextField partId;

    @FXML
    public TextField partName;

    @FXML
    public TextField inventoryCount;

    @FXML
    public TextField partPrice;

    @FXML
    public TextField maximumInventory;

    @FXML
    public TextField variableTextField;

    @FXML
    public TextField minimumInventory;

    @FXML
    public Button saveButton;
    @FXML
    public Button cancelButton;

    private Part selectedPart;

    public Part getSelectedPart() {
        return selectedPart;
    }

    public void setSelectedPart(Part selectedPart) {
        this.selectedPart = selectedPart;
    }



    public void addPart() {
        double price = Double.parseDouble(partPrice.getText());
        String name = partName.getText();;
        int stock = Integer.parseInt(inventoryCount.getText());
        int id = Integer.parseInt(partId.getText());
        int minimum = Integer.parseInt(minimumInventory.getText());
        int maximum = Integer.parseInt(maximumInventory.getText());
        String variableText = variableTextField.getText();


        InHouse newPart = new InHouse(id,name,price,stock,minimum,maximum,Integer.parseInt(variableText));
        System.out.println(newPart);

    }

}
