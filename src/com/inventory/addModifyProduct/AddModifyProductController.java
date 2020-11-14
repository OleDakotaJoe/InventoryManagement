package com.inventory.addModifyProduct;

import com.inventory.dataModel.Product;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddModifyProductController {
    private Product selectedProduct;

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public AddModifyProductController(String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addModifyProduct.fxml"));
            fxmlLoader.setController(AddModifyProductController.class);
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
