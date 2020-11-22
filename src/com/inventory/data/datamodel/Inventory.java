package com.inventory.data.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The class which holds all of the data which an Inventory item and list contains.
 * <p>
 *     When this class is instantiated, the ObservableList <code>allParts</code> and <code>allProducts</code>
 *     is also instantiated. Products and parts are added to these lists respectively to build the Inventory Lists.
 * <p>
 *     The class itself contains only the code necessary to represent the data model. The code necessary to manipulate the data
 *     is mostly held in the designated controller classes: <code>Controller</code> , <code>AddModifyPartController</code>, and
 *     <code>AddModifyProductController</code>. This is by design, to separate concerns and follow the Model, View, Controller paradigm
 *     of application design.
  */
public class Inventory {
    /**
     * The allParts list is an ObservableList which is instantiated when the Inventory class is instantiated.
     * This list will whole the list of all available parts. It is parameterized to take the type <code><Part></code>
     */
    private ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * The allProducts list is an ObservableList which is instantiated when the Inventory class is instantiated.
     * This list will whole the list of all available products. It is parameterized to take the type <code><Product></code>
     */
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds a new part to the list of <code>allParts</code>
     * @param newPart
     * The new Part to be added to the list.
     */
    public void addPart(Part newPart) {
        this.allParts.add(newPart);
    }

    /**
     * Adds a new Product to the list of <code>allProucts</code>
     * @param newProduct
     * The new Product to be added to the list.
     */
    public void addProduct(Product newProduct) {
        this.allProducts.add(newProduct);
    }

    /**
     * Looks up and returns a <code>Part</code> based on its <code>partId</code>.
     * @param partId
     * The int value of the part ID to be looked up.
     * @return
     * Returns the Part Object of the part which is being searched for, or returns null if part is not found.
     */
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

    /**
     * Looks up and returns a part based on its <code>partName</code>
     * THe method converts the string value that is passed to it to lower case via the <code>String.toLowerCase()</code>
     * method on in the String class. This is because of a bug in which the method would return <code>null</code> when the
     * <code>partName</code> parameter did not match the <code>partName</code> of the part strictly, including by case.
     *
     * @param partName
     * the String value of the part name to be searched for
     * @return
     * Returns the Part Object of the part which is being searched for, or returns null if part is not found.
     */
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

    /**
     * Looks up and returns a <code>Product</code> based on its <code>productId</code>.
     * The methdod uses the <code>ObservableList.contains()</code> to method to search the list.
     * @param productId
     * The int value of the <code>productId</code> to be searched for.
     * @return
     * Returns the Product Object of the Product being searched for, or null if the part is not found.
     */
    public Product lookupProduct(int productId) {
        // TODO: 11/19/2020 Continue writing javadoc comments
        for (Product product : allProducts) {
            if(product.getId() == productId) {
                return product;
            } else {
                continue;
            }
        }
        return null;


    }

    /**
     * Looks up and returns a <code>Product</code> based on its <code>productName</code>
     * THe method converts the string value that is passed to it to lower case via the <code>String.toLowerCase()</code>
     * method on in the String class. This is because of a bug in which the method would return <code>null</code> when the
     * <code>productName</code> parameter did not match the <code>productName</code> of the <code>Product</code> strictly, including by case.
     * @param productName
     * The String value of the <code>productName</code> to be searched for.
     * @return
     * Returns a reference to the <code>Product</code> being searched for, or null if no product is found.
     */
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

    /**
     * Updates a <code>Part</code> Object within the <code>ObservableList</code>
     * This method use the <code>getAllParts()</code> method to retrieve the current Inventory of Parts. It then
     * uses the <code>.set()</code> method from the <code>ObservableList</code> class to overwrite the new old part with a new one.
     * @param index
     * The index within the <code>ObservableList</code> that corresponds to the part to be
     * updated.
     * @param selectedPart
     * The new <code>Part</code> Object which contains the actual object to be updated.
     */
    public void updatePart(int index, Part selectedPart) {
        this.getAllParts().set(index, selectedPart);

    }

    /**
     * Updates a <code>Part</code> Object within the <code>ObservableList</code>
     * This method use the <code>getAllParts()</code> method to retrieve the current Inventory of Parts. It then
     * uses the <code>.set()</code> method from the <code>ObservableList</code> class to overwrite the new old part with a new one.
     * @param index
     * The index within the <code>ObservableList</code> that corresponds to the part to be
     * @param newProduct
     * The new <code>Product</code> Object which contains the actual object to be updated.
     */
    public void updateProduct(int index, Product newProduct) {
        this.getAllProducts().set(index, newProduct);
    }

    /**
     * Deletes a part from inventory.
     * This function uses an enhanced for loop to check if each product contains the selected Part
     * if any product contains the part in its list of associated parts, it returns false. Other wise, it deletes the part and returns true.
     * The returning of the boolean is used so that the view-controller can conditionally create an render a dialog box that
     * informs the user that part has or has not been deleted.
     * @param selectedPart
     * <code>Part</code> to be removed
     * @return
     * Returns true if part was deleted.
     */
    public boolean deletePart(Part selectedPart) {
        for(Product product : getAllProducts()) {
            if(product.getAllAssociatedParts().contains(selectedPart)){
                return false;
            }
        }
        getAllParts().remove(selectedPart);
        return true;
    }

    /**
     * Deletes a product from Inventory
     * This function uses an enhanced for loop to check if each product is associated with any parts.
     * Because the ObservableList class does not contain a <code>.size()</code> method, or similar,
     * it utilizes a counter to check if the number of parts in the list is greater than 0.
     * If the <code>selectedProduct</code> contains a part, it returns false. Otherwise, it deletes the product then returns true.
     * The returning of the boolean is used so that the view-controller can conditionally create an render a dialog box that
     * informs the user that product has or has not been deleted.
     * @param selectedProduct
     * <code>Product</code> to be deleted.
     * @return
     * Returns true if the product was deleted.
     */
    public boolean deleteProduct(Product selectedProduct) {
        int counter = 0;
        for(Part part : selectedProduct.getAllAssociatedParts()) {
            counter ++;
            if(counter > 0){
                return false;
            }
        }
        getAllProducts().remove(selectedProduct);
        return true;
    }

    /**
     * Getter used for gaining access the the list of parts in inventory.
     * @return
     * Returns an <code>ObservableList</code> containing all of the parts in inventory
     */
    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Getter used for gaining access the the list of products in inventory.
     * @return
     * Returns an <code>ObservableList</code> containing all of the products in inventory
     */
    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
