package com.service.billbook.model;

import com.service.billbook.fragments.ItemsFragment;
import com.service.billbook.fragments.PartiesFragment;

public class ItemListModel implements Comparable {

    private String item_name;
    private String item_weight;
    private String unit;
    private String purchase_price;
    private String sale_price;
    private String quantity;
    private String intLowStock;
    private String intItemType;
    private String bigintItemId;
    private String bigintUserId;
    private String dcMRP;
    private String strHSNCode;
    private String intGst;
    private String bitisPurchaseTaxInclude;
    private String bitisSaleTaxInclude;
    private String strItemCode;
    private String bitIsAdditionalColumn;
    private String bigintCategoryId;
    private String categoryName;
    private String number_of_items;
    private String stock_value;
    private String item_description;
    private String unit_name;
    private String unit_short_name;

    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }

    public String getStock_value() {
        return stock_value;
    }

    public void setStock_value(String stock_value) {
        this.stock_value = stock_value;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_weight() {
        return item_weight;
    }

    public void setItem_weight(String item_weight) {
        this.item_weight = item_weight;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPurchase_price() {
        return purchase_price;
    }

    public void setPurchase_price(String purchase_price) {
        this.purchase_price = purchase_price;
    }

    public String getSale_price() {
        return sale_price;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getIntLowStock() {
        return intLowStock;
    }

    public void setIntLowStock(String intLowStock) {
        this.intLowStock = intLowStock;
    }

    public String getIntItemType() {
        return intItemType;
    }

    public void setIntItemType(String intItemType) {
        this.intItemType = intItemType;
    }

    public String getBigintItemId() {
        return bigintItemId;
    }

    public void setBigintItemId(String bigintItemId) {
        this.bigintItemId = bigintItemId;
    }

    public String getBigintUserId() {
        return bigintUserId;
    }

    public void setBigintUserId(String bigintUserId) {
        this.bigintUserId = bigintUserId;
    }

    public String getDcMRP() {
        return dcMRP;
    }

    public void setDcMRP(String dcMRP) {
        this.dcMRP = dcMRP;
    }

    public String getStrHSNCode() {
        return strHSNCode;
    }

    public void setStrHSNCode(String strHSNCode) {
        this.strHSNCode = strHSNCode;
    }

    public String getIntGst() {
        return intGst;
    }

    public void setIntGst(String intGst) {
        this.intGst = intGst;
    }

    public String getBitisPurchaseTaxInclude() {
        return bitisPurchaseTaxInclude;
    }

    public void setBitisPurchaseTaxInclude(String bitisPurchaseTaxInclude) {
        this.bitisPurchaseTaxInclude = bitisPurchaseTaxInclude;
    }

    public String getBitisSaleTaxInclude() {
        return bitisSaleTaxInclude;
    }

    public void setBitisSaleTaxInclude(String bitisSaleTaxInclude) {
        this.bitisSaleTaxInclude = bitisSaleTaxInclude;
    }

    public String getStrItemCode() {
        return strItemCode;
    }

    public void setStrItemCode(String strItemCode) {
        this.strItemCode = strItemCode;
    }

    public String getBitIsAdditionalColumn() {
        return bitIsAdditionalColumn;
    }

    public void setBitIsAdditionalColumn(String bitIsAdditionalColumn) {
        this.bitIsAdditionalColumn = bitIsAdditionalColumn;
    }

    public String getBigintCategoryId() {
        return bigintCategoryId;
    }

    public void setBigintCategoryId(String bigintCategoryId) {
        this.bigintCategoryId = bigintCategoryId;
    }

    public String getNumber_of_items() {
        return number_of_items;
    }

    public void setNumber_of_items(String number_of_items) {
        this.number_of_items = number_of_items;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getUnit_short_name() {
        return unit_short_name;
    }

    public void setUnit_short_name(String unit_short_name) {
        this.unit_short_name = unit_short_name;
    }

    @Override
    public int compareTo(Object o) {

//      /* For Ascending order*/
        if (ItemsFragment.ii.sorting.equals("a")) {
            String compareage = ((ItemListModel) o).getQuantity();
            int c = Integer.parseInt(compareage);
            return Integer.parseInt(this.quantity) - c;
        } else if (ItemsFragment.ii.sorting.equals("d")) {
            String compareage = ((ItemListModel) o).getQuantity();
            int c = Integer.parseInt(compareage);
            return c - Integer.parseInt(this.quantity);
        } else if (ItemsFragment.ii.sorting.equals("z_to_a")) {
            String compareage = ((ItemListModel) o).getItem_name();
            return compareage.compareToIgnoreCase(this.item_name);
        } else if (ItemsFragment.ii.sorting.equals("a_to_z")) {
            String compareage = ((ItemListModel) o).getItem_name();
            return this.item_name.compareToIgnoreCase(compareage);
        }
        return 0;
    }
}
