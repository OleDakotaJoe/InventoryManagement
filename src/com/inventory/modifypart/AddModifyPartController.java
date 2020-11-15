package com.inventory.modifypart;

import com.inventory.Controller;
import com.inventory.data.datamodel.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AddModifyPartController {

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
    public Label varLabel;

    @FXML
    public Label addModLabel;

    @FXML
    public TextField minimumInventory;

    @FXML
    public Button saveButton;

    @FXML
    public Button cancelButton;

    @FXML
    public RadioButton inHouse;

    @FXML
    public RadioButton outsourced;

    @FXML
    public GridPane main;

    @FXML
    public void initialize() {
     changeVarLabel();
     addModLabel.setText(PassableData.getTitle());
     if(PassableData.getIsModify() && PassableData.getPartData()!= null) populateForm();
    }

    public void changeVarLabel() {
        if (inHouse.isSelected()) {
            varLabel.setText("Machine ID");
            variableTextField.setPromptText("Machine ID...");
        } else if (outsourced.isSelected()) {
            varLabel.setText("Company Name");
            variableTextField.setPromptText("Company Name...");
        }
        int textLength = partId.getText().length();
        variableTextField.setText(variableTextField.getText().replaceAll("[^\\d.]", ""));
        variableTextField.positionCaret(textLength);

    }

    // TODO: 11/14/2020 Implement functionality to add variableTextField property
    public void populateForm() {
        Part selectedPart = (Part) PassableData.getPartData();
        partPrice.setText(String.valueOf(selectedPart.getPrice()));
        partName.setText(selectedPart.getName());
        inventoryCount.setText(String.valueOf(selectedPart.getStock()));
        partId.setText(String.valueOf(selectedPart.getId()));
        maximumInventory.setText(String.valueOf(selectedPart.getMax()));
        minimumInventory.setText(String.valueOf(selectedPart.getMin()));

        if (PassableData.isOutsourced()) {
            Outsourced part =(Outsourced) selectedPart;
            variableTextField.setText(part.getCompanyName());
            outsourced.setSelected(true);

        } else if (!PassableData.isOutsourced()) {
            InHouse part = (InHouse) selectedPart;
            variableTextField.setText(String.valueOf(part.getMachineId()));
            inHouse.setSelected(true);
        }


    }



    public void addPart() {
        boolean isValid =
                !partPrice.getText().equals("")
                && !partName.getText().equals("")
                && !inventoryCount.getText().equals("")
                && !partId.getText().equals("")
                && !maximumInventory.getText().equals("")
                && !minimumInventory.getText().equals("")
                && !variableTextField.getText().equals("")
                && (Integer.parseInt(maximumInventory.getText()) > Integer.parseInt(minimumInventory.getText()));
        if(isValid) {
            double price = Double.parseDouble(partPrice.getText());
            String name = partName.getText();
            int stock = Integer.parseInt(inventoryCount.getText());
            int id = Integer.parseInt(partId.getText());
            int minimum = Integer.parseInt(minimumInventory.getText());
            int maximum = Integer.parseInt(maximumInventory.getText());
            String variableText = variableTextField.getText();

                if (inHouse.isSelected()) {
                    InHouse newPart = new InHouse(id,name,price,stock,minimum,maximum,Integer.parseInt(variableText));
                    Controller.getInventory().addPart(newPart);

                } else if (outsourced.isSelected()) {
                    Outsourced newPart = new Outsourced(id,name,price,stock,minimum,maximum,variableText);
                    Controller.getInventory().addPart(newPart);
                }
// TODO: 11/14/2020 add functionality to initialize an alert window for displaying error

            try {
                InventoryData.getInstance().storeTodoItems();
            } catch (IOException e) {
                e.printStackTrace();
            }
                exit();
            } else  {
            Alert errorMessage = new Alert(Alert.AlertType.INFORMATION);
            errorMessage.setTitle("Missing Information");
            errorMessage.setHeaderText("You didn't enter required information!");
            errorMessage.setContentText("All fields are required! \nYour part has not been saved. \nPress \"OK\" and try again.");



            errorMessage.show();
        }
    }



    public void validator(KeyEvent event) {
        //(partId.getText().matches("[0-" + Integer.MAX_VALUE + "]"));
        Object source = event.getSource();
        if (source.equals(partId)) {
            int caretPosition = partId.getCaretPosition();
            int textLength = partId.getText().length();
            if (!event.getText().equals("")) {
                partId.setText(partId.getText().replaceAll("[^\\d.]", ""));
                if(caretPosition < textLength) {
                    if(event.getText().matches("[^\\d.]")) {
                        partId.positionCaret(caretPosition - 1);
                    } else {
                        partId.positionCaret(caretPosition);
                    }
                } else {
                    partId.positionCaret(textLength);
                }

            }
        } else if (source.equals(maximumInventory)) {
            int caretPosition = maximumInventory.getCaretPosition();
            int textLength = maximumInventory.getText().length();
            if (!event.getText().equals("")) {
                maximumInventory.setText(maximumInventory.getText().replaceAll("[^\\d.]", ""));
                if(caretPosition < textLength) {
                    if(event.getText().matches("[^\\d.]")) {
                        maximumInventory.positionCaret(caretPosition - 1);
                    } else {
                        maximumInventory.positionCaret(caretPosition);
                    }
                } else {
                    maximumInventory.positionCaret(textLength);
                }

            }
        } else if (source.equals(inventoryCount)) {
            int caretPosition = inventoryCount.getCaretPosition();
            int textLength = inventoryCount.getText().length();
            if (!event.getText().equals("")) {
                inventoryCount.setText(inventoryCount.getText().replaceAll("[^\\d.]", ""));
                if(caretPosition < textLength) {
                    if(event.getText().matches("[^\\d.]")) {
                        inventoryCount.positionCaret(caretPosition - 1);
                    } else {
                        inventoryCount.positionCaret(caretPosition);
                    }
                } else {
                    inventoryCount.positionCaret(textLength);
                }

            }
        } else if (source.equals(minimumInventory)) {
            int caretPosition = minimumInventory.getCaretPosition();
            int textLength = minimumInventory.getText().length();
            if (!event.getText().equals("")) {
                minimumInventory.setText(minimumInventory.getText().replaceAll("[^\\d.]", ""));
                if(caretPosition < textLength) {
                    if(event.getText().matches("[^\\d.]")) {
                        minimumInventory.positionCaret(caretPosition - 1);
                    } else {
                        minimumInventory.positionCaret(caretPosition);
                    }
                } else {
                    minimumInventory.positionCaret(textLength);
                }

            }
        } else if (source.equals(variableTextField)) {
            if (inHouse.isSelected()) {
                int caretPosition = variableTextField.getCaretPosition();
                int textLength = variableTextField.getText().length();
                if (!event.getText().equals("")) {
                    variableTextField.setText(variableTextField.getText().replaceAll("[^\\d.]", ""));
                    if(caretPosition < textLength) {
                        if(event.getText().matches("[^\\d.]")) {
                            variableTextField.positionCaret(caretPosition - 1);
                        } else {
                            variableTextField.positionCaret(caretPosition);
                        }
                    } else {
                        variableTextField.positionCaret(textLength);
                    }
                }
            }
        } else if (source.equals(partPrice)) {
            int caretPosition = partPrice.getCaretPosition();
            int textLength = partPrice.getText().length();
            if (!event.getText().equals("")) {
                partPrice.setText(partPrice.getText().replaceAll("[^\\p{Digit}.]", ""));
                if(caretPosition < textLength) {
                    if(event.getText().matches("[^\\p{Digit}.]")) {
                        partPrice.positionCaret(caretPosition - 1);
                    } else {
                        partPrice.positionCaret(caretPosition);
                    }
                } else {
                    partPrice.positionCaret(textLength);
                }
            }
        } else  {
            return;
        }
    }

    @FXML
    public void exit(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        PassableData.setIsModify(false);
    }
}
