package com.inventory.data.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public void addPart(Part newPart) {
        this.allParts.add(newPart);
    }

    public void addProduct(Product newProduct) {
        this.allProducts.add(newProduct);
    }

    public Part lookupPart(int partId) {
        for (Part part : allParts) {
            if(part.getId() == partId) {
                return part;
            } else {
                continue;
            }
        }
        return null;
    }

    public Part lookupPart(String partName) {
        for (Part part : allParts) {
            String partInInventory = part.getName().toLowerCase();
            if(partInInventory.contains(partName.toLowerCase())) {
                return part;
            } else {
                continue;
            }
        }
        return null;

    }
    public Product lookupProduct(int productId) {
        for (Product product : allProducts) {
            if(product.getId() == productId) {
                return product;
            } else {
                continue;
            }
        }
        return null;


    }

    public Product lookupProduct(String productName) {
        for (Product product : allProducts) {
            String productInInventory = product.getName().toLowerCase();
            if(productInInventory.contains(productName.toLowerCase())) {
                return product;
            } else {
                continue;
            }
        }
        return null;



    }

    public void updatePart(int index, Part selectedPart) {
        this.getAllParts().set(index, selectedPart);

    }

    public void updateProduct(int index, Product newProduct) {
        this.getAllProducts().set(index, newProduct);
    }

    public boolean deletePart(Part selectedPart) {
        boolean isRemoved = false;
        for(Part part : getAllParts()) {
            if (part == selectedPart) {
                allParts.remove(part);
                isRemoved = true;
                break;
            } else {
                isRemoved = false;
                continue;
            }

        }
        return isRemoved;
    }

    public boolean deleteProduct(Product selectedProduct) {
        boolean isRemoved = false;

        for(Product product : getAllProducts()) {
            if (product == selectedProduct) {
                allProducts.remove(product);
                isRemoved = true;
                break;
            } else {
                isRemoved = false;
                continue;
            }

        }
        return isRemoved;

    }

    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
