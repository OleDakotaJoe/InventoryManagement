package com.inventory.modifyproduct;

import com.inventory.data.datamodel.Product;

public class AddModifyProductController {
    private Product selectedProduct;


    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }


}
