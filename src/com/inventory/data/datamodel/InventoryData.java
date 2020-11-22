package com.inventory.data.datamodel;

import com.inventory.Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Reads and writes inventory Data to a flat .txt file
 */
public class InventoryData {
    /**
     * Creates a singleton instance of the  <code>InventoryData</code> class
     * This is so that the instance of this class can be accessed publicly without the need for instantiation.
     */
    private static final InventoryData instance = new InventoryData();
    /**
     * The name of the file that all products in the inventory stored in.
     * This member exists to ensure no typos occur.
     */
    private static final String productsFileName = "ProductInventory.txt";
    /**
     * The name of the file that all parts in the inventory stored in.
     * This member exists to ensure no typos occur.
     */
    private static final String partsFileName = "PartsInventory.txt";
    /**
     * The name of the file the current part index is stored in.
     * This member exists to ensure no typos occur.
     */
    private static final String idIndexFileName = "partIdIndex.txt";
    /**
     * The name of the file the currnet product index is stored in.
     * This member exists to ensure no typos occur.
     */
    private static final String productIdIndexFileName = "productIdIndex.txt";


    /**
     * Returns the instance of the <code>InventoryData</code> Object, which is instantiated when the member <code>instance</code> is declared.
     * @return
     * Returns the instance of the <code>InventoryData</code> Object.
     */
    public static InventoryData getInstance() {
        return instance;
    }

    /**
     * Loads Part data into the instance of the Inventory class in the Controller Object.
     * This class creates a <code>BufferedReader</code> and reads comma-delimited file Data about Parts, and creates new instances of the Parts,
     * then adds each instance into Inventory
     * @throws IOException
     * Will throw an <code>IOException</code> if the file to be read does not exist, or if the buffered reader encounters any errors during the process
     */
    public void loadPartInventory() throws IOException {
        Path path = Paths.get(partsFileName);

        try (BufferedReader br = Files.newBufferedReader(path)) {
            String input;
            while (null != (input = br.readLine())) {
                String[] partPieces = input.split(",\"");
                String id = partPieces[0].replaceAll("\"", "");
                String name = partPieces[1].replaceAll("\"", "");
                String price = partPieces[2].replaceAll("\"", "");
                String stock = partPieces[3].replaceAll("\"", "");
                String min = partPieces[4].replaceAll("\"", "");
                String max = partPieces[5].replaceAll("\"", "");
                String temp = partPieces[6].replaceAll("\"", "");
                String type = partPieces[7].replaceAll("\"", "");
                Part part = null;
                if (type.equals("InHouse")) {
                    part = new InHouse(Integer.parseInt(id), name, Double.parseDouble(price), Integer.parseInt(stock), Integer.parseInt(min), Integer.parseInt(max), Integer.parseInt(temp));
                } else {
                    part = new Outsourced(Integer.parseInt(id), name, Double.parseDouble(price), Integer.parseInt(stock), Integer.parseInt(min), Integer.parseInt(max), temp);
                }
                Controller.getInventory().addPart(part);
            }
        }
    }

    /**
     * This method stores Part Data in comma-delimited format so that the data may persist in memory.
     * Creates a  <code>BufferedWriter</code> and writes the values from each part in Inventory to a flat .txt file.
     * @throws IOException
     * If file path is invalid or other errors occur during the write Process this method will
     * throw an <code>IOException</code>
     */
    public void storePartInventory() throws IOException {
        Path  path = Paths.get(partsFileName);

        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            for (Part part : Controller.getInventory().getAllParts()) {
                boolean isInHouse = part.getClass() == InHouse.class;
                String type = isInHouse ? "InHouse" : "Outsourced";
                Object temp = isInHouse ? ((InHouse) part).getMachineId() : ((Outsourced) part).getCompanyName();
                bw.write("\"" + part.getId() + "\",\"" + part.getName() + "\",\"" + part.getPrice() + "\",\"" + part.getStock() + "\",\"" + part.getMin() + "\",\"" + part.getMax() + "\",\"" + temp + "\",\"" + type + "\"");
                bw.newLine();
            }
        }
    }

    /**
     * Writes current partID Index into a .txt file for storage.
     * @throws IOException
     * If file path is invalid or other errors occur during the write Process this method will
     * throw an <code>IOException</code>
     */
    public void storePartIdIndex() throws IOException {
        Path path = Paths.get(idIndexFileName);
        BufferedWriter bw = Files.newBufferedWriter(path);

        bw.write(String.valueOf(Controller.getPartIdCounter()));
        bw.newLine();
        bw.close();
    }

    /**
     * Loads partIdIndex into the AtomicInteger member of Controller class.
     * @throws IOException
     * If file path is invalid or other errors occur during the write Process this method will
     * throw an <code>IOException</code>
     */
    public void loadIdIndex() throws IOException {
        Path path = Paths.get(idIndexFileName);

        try (BufferedReader br = Files.newBufferedReader(path)) {
            String input;
            if ((input = br.readLine()) != null) {
                Controller.setPartIdCounter(Integer.parseInt(input));
            }
        }
    }

    /**
     * Writes current productID Index into a .txt file for storage.
     * @throws IOException
     * If file path is invalid or other errors occur during the write Process this method will
     * throw an <code>IOException</code>
     */
    public void storeProductIdIndex() throws IOException {
        Path path = Paths.get(productIdIndexFileName);
        BufferedWriter bw = Files.newBufferedWriter(path);
        bw.write(String.valueOf(Controller.getProductIdCounter()));
        bw.newLine();
        bw.close();
    }

    /**
     * Loads productIdIndex into the AtomicInteger member of Controller class.
     * @throws IOException
     * If file path is invalid or other errors occur during the write Process this method will
     * throw an <code>IOException</code>
     */
    public void loadProductIdIndex() throws IOException {
        Path path = Paths.get(productIdIndexFileName);
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String input;
            if ((input = br.readLine()) != null) {
                Controller.setProductIdCounter(Integer.parseInt(input));
            }
        }
    }

    /**
     * Loads Product data into the instance of the Inventory class in the Controller Object.
     * This class creates a <code>BufferedReader</code> and reads comma-delimited file Data about Products, and creates new instances of the Products,
     * then adds each instance into Inventory
     * @throws IOException
     * Will throw an <code>IOException</code> if the file to be read does not exist, or if the buffered reader encounters any errors during the process
     */
    public void loadProductInventory() throws IOException {
        Path path = Paths.get(productsFileName);

        try (BufferedReader br = Files.newBufferedReader(path)) {
            String input;
            while ((input = br.readLine()) != null) {
                String[] productPieces = input.split(",\"");
                String productId = productPieces[0].replaceAll("\"", "");
                String productName = productPieces[1].replaceAll("\"", "");
                String productPrice = productPieces[2].replaceAll("\"", "");
                String productStock = productPieces[3].replaceAll("\"", "");
                String productMin = productPieces[4].replaceAll("\"", "");
                String productMax = productPieces[5].replaceAll("\"", "");
                Product product = new Product(Integer.parseInt(productId), productName, Double.parseDouble(productPrice), Integer.parseInt(productStock), Integer.parseInt(productMin), Integer.parseInt(productMax));
                Controller.getInventory().addProduct(product);

                Path currentDirectory = Paths.get(".");
                Path finalPath = Paths.get(String.valueOf(currentDirectory), "src", "com", "inventory", "products", productId + ".txt");
                try (BufferedReader brPart = Files.newBufferedReader(finalPath)) {
                    while ((input = brPart.readLine()) != null) {
                        String[] partPieces = input.split(",\"");
                        String id = partPieces[0].replaceAll("\"", "");
                        String name = partPieces[1].replaceAll("\"", "");
                        String price = partPieces[2].replaceAll("\"", "");
                        String stock = partPieces[3].replaceAll("\"", "");
                        String min = partPieces[4].replaceAll("\"", "");
                        String max = partPieces[5].replaceAll("\"", "");
                        String temp = partPieces[6].replaceAll("\"", "");
                        String type = partPieces[7].replaceAll("\"", "");
                        Part part;
                        if (type.equals("InHouse")) {
                            part = new InHouse(Integer.parseInt(id), name, Double.parseDouble(price), Integer.parseInt(stock), Integer.parseInt(min), Integer.parseInt(max), Integer.parseInt(temp));
                        } else {
                            part = new Outsourced(Integer.parseInt(id), name, Double.parseDouble(price), Integer.parseInt(stock), Integer.parseInt(min), Integer.parseInt(max), temp);
                        }
                        Controller.getInventory().getAllProducts().get(Integer.parseInt(productId)).addAssociatePart(part);
                    }
                }
            }
        }
    }
    /**
     * This method stores Product Data in comma-delimited format so that the data may persist in memory.
     * Creates a  <code>BufferedWriter</code> and writes the values from each part in Inventory to a flat .txt file.
     * @throws IOException
     * If file path is invalid or other errors occur during the write Process this method will
     * throw an <code>IOException</code>
     */
    public void storeProductInventory() throws IOException {
        Path  path = Paths.get(productsFileName);
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {

            for (Product product : Controller.getInventory().getAllProducts()) {
                String id = String.valueOf(product.getId());

                bw.write("\"" + id + "\",\"" + product.getName() + "\",\"" + product.getPrice() + "\",\"" + product.getStock() + "\",\"" + product.getMin() + "\",\"" + product.getMax() + "\"");
                bw.newLine();
                Path currentDirectory = Paths.get(".");
                Path finalPath = Paths.get(String.valueOf(currentDirectory), "src", "com", "inventory", "products", id + ".txt");
                try (BufferedWriter bwPart = Files.newBufferedWriter(finalPath)) {
                    for (Part part : product.getAllAssociatedParts()) {
                        boolean isInHouse = part.getClass() == InHouse.class;
                        String type = isInHouse ? "InHouse" : "Outsourced";
                        Object temp = isInHouse ? ((InHouse) part).getMachineId() : ((Outsourced) part).getCompanyName();
                        bwPart.write(String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"",
                                part.getId(),
                                part.getName(),
                                part.getPrice(),
                                part.getStock(),
                                part.getMin(),
                                part.getMax(),
                                temp,
                                type));
                        bwPart.newLine();
                    }
                }
            }
        }
    }
}





