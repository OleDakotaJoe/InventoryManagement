package com.inventory;

import com.inventory.data.datamodel.InventoryData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Inventory Manager");
        primaryStage.setScene(new Scene(root, 875, 325));
        root.requestFocus();
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Overrides the  <code>stop()</code> method and stores all inventory data when user clicks the X button to close the window.
     * @throws Exception
     * If there is an issue storing data, throws an exception.
     */
    @Override
    public void stop() throws Exception {
        try {
            InventoryData.getInstance().storePartInventory();
            InventoryData.getInstance().storePartIdIndex();
            InventoryData.getInstance().storeProductIdIndex();
            InventoryData.getInstance().storeProductInventory();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.stop();
    }
}
