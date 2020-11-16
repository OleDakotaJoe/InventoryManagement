package com.inventory.data.datamodel;

import com.inventory.Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

public class InventoryData {
    private static InventoryData instance = new InventoryData();
    private static String productsFileName = "ProductInventory.txt";
    private static String partsFileName = "PartsInventory.txt";
    private static String idIndexFileName = "idIndex.txt";

    public static InventoryData getInstance() {
        return instance;
    }

    public void loadPartInventory() throws IOException {
        Path path = Paths.get(partsFileName);
        BufferedReader br = Files.newBufferedReader(path);

        String input;

        try {
            while ((input = br.readLine()) != null) {
                String[] itemPieces = input.split(",\"");
                String id = itemPieces[0].replaceAll("\"","");
                String name = itemPieces[1].replaceAll("\"","");
                String price = itemPieces[2].replaceAll("\"","");
                String stock = itemPieces[3].replaceAll("\"","");
                String min = itemPieces[4].replaceAll("\"","");
                String max = itemPieces[5].replaceAll("\"","");
                String temp = itemPieces[6].replaceAll("\"","");
                String type = itemPieces[7].replaceAll("\"","");
                Part part = null;
                if (type=="InHouse") {
                    part = new InHouse(Integer.parseInt(id),name,Double.parseDouble(price),Integer.parseInt(stock),Integer.parseInt(min),Integer.parseInt(max),Integer.parseInt(temp));
                } else {
                    part = new Outsourced(Integer.parseInt(id),name,Double.parseDouble(price),Integer.parseInt(stock),Integer.parseInt(min),Integer.parseInt(max),temp);
                }


                Controller.getInventory().addPart(part);
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    public void storePartInventory() throws IOException {
        Path  path = Paths.get(partsFileName);
        BufferedWriter bw = Files.newBufferedWriter(path);

        try {
            Iterator<Part> iterator = Controller.getInventory().getAllParts().iterator();
            while(iterator.hasNext()) {
                Part part = iterator.next();
                boolean isInHouse = part.getClass() == InHouse.class;
                String type = isInHouse ? "InHouse" : "Outsourced";
                Object temp = isInHouse ? ((InHouse) part).getMachineId() : ((Outsourced) part).getCompanyName();
                bw.write(String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"",
                        part.getId(),
                        part.getName(),
                        part.getPrice(),
                        part.getStock(),
                        part.getMin(),
                        part.getMax(),
                        temp,
                        type));
                bw.newLine();
            }
        } finally {
            if (bw != null) {
                bw.close();
            }
        }
    }

    public void storeIdIndex() throws IOException {
        Path path = Paths.get(idIndexFileName);
        BufferedWriter bw = Files.newBufferedWriter(path);

        bw.write(String.valueOf(Controller.getIdCounter()));
        bw.newLine();
        bw.close();
    }

    public void readIdIndex() throws IOException {
        Path path = Paths.get(idIndexFileName);
        BufferedReader br = Files.newBufferedReader(path);

        String input;

        try {
            if ((input = br.readLine()) != null) {
                Controller.setIdCounter(Integer.parseInt(input));
            }
        } finally {
            if (br != null) br.close();
        }
    }


}





