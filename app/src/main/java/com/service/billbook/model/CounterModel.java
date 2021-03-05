package com.service.billbook.model;

public class CounterModel {

    private String item_id;
    private String item_name;
    private String barcode;
    private String item_rate;
    private String item_stock;
    private String item_purchase_price;
    private String item_sale_price;
    private String unit;
    private String selected_qty;

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_rate() {
        return item_rate;
    }

    public void setItem_rate(String item_rate) {
        this.item_rate = item_rate;
    }

    public String getItem_stock() {
        return item_stock;
    }

    public void setItem_stock(String item_stock) {
        this.item_stock = item_stock;
    }

    public String getItem_purchase_price() {
        return item_purchase_price;
    }

    public void setItem_purchase_price(String item_purchase_price) {
        this.item_purchase_price = item_purchase_price;
    }

    public String getItem_sale_price() {
        return item_sale_price;
    }

    public void setItem_sale_price(String item_sale_price) {
        this.item_sale_price = item_sale_price;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSelected_qty() {
        return selected_qty;
    }

    public void setSelected_qty(String selected_qty) {
        this.selected_qty = selected_qty;
    }
}
