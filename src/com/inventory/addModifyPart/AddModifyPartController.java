package com.inventory.addModifyPart;

import com.inventory.dataModel.Part;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddModifyPartController {
    private Part selectedPart;

    public Part getSelectedPart() {
        return selectedPart;
    }

    public void setSelectedPart(Part selectedPart) {
        this.selectedPart = selectedPart;
    }

    public AddModifyPartController(String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addModifyPart.fxml"));
            fxmlLoader.setController(AddModifyPartController.class);
            Parent root1 = (Parent) fxmlLoader.load();
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


}
