package com.service.billbook.servicemodels;

import java.util.List;

public  class GetAllDropDownModel {

    private Body body;
    private String message;
    private String response;

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public static class Body {
        private List<UnitType> unitType;
        private List<gstType> gstType;
        private List<IsTax> isTax;
        private List<ItemType> itemType;
        private List<StockValue> stockValue;

        public List<UnitType> getUnitType() {
            return unitType;
        }

        public void setUnitType(List<UnitType> unitType) {
            this.unitType = unitType;
        }

        public List<IsTax> getIsTax() {
            return isTax;
        }

        public void setIsTax(List<IsTax> isTax) {
            this.isTax = isTax;
        }

        public List<ItemType> getItemType() {
            return itemType;
        }

        public void setItemType(List<ItemType> itemType) {
            this.itemType = itemType;
        }

        public List<StockValue> getStockValue() {
            return stockValue;
        }

        public void setStockValue(List<StockValue> stockValue) {
            this.stockValue = stockValue;
        }

        public List<GetAllDropDownModel.gstType> getGstType() {
            return gstType;
        }

        public void setGstType(List<GetAllDropDownModel.gstType> gstType) {
            this.gstType = gstType;
        }
    }

    public static class UnitType {
        private String intUnitId;
        private String strUnitName;
        private String strShortName;
        private boolean isdeleted;

        public String getIntUnitId() {
            return intUnitId;
        }

        public void setIntUnitId(String intUnitId) {
            this.intUnitId = intUnitId;
        }

        public String getStrUnitName() {
            return strUnitName;
        }

        public void setStrUnitName(String strUnitName) {
            this.strUnitName = strUnitName;
        }

        public String getStrShortName() {
            return strShortName;
        }

        public void setStrShortName(String strShortName) {
            this.strShortName = strShortName;
        }

        public boolean isIsdeleted() {
            return isdeleted;
        }

        public void setIsdeleted(boolean isdeleted) {
            this.isdeleted = isdeleted;
        }
    }
    public static class gstType {
        private String intGstId;
        private String strGstName;
        private boolean isdeleted;
        private String myProperty;

        public String getIntGstId() {
            return intGstId;
        }

        public void setIntGstId(String intGstId) {
            this.intGstId = intGstId;
        }

        public String getStrGstName() {
            return strGstName;
        }

        public void setStrGstName(String strGstName) {
            this.strGstName = strGstName;
        }

        public boolean isIsdeleted() {
            return isdeleted;
        }

        public void setIsdeleted(boolean isdeleted) {
            this.isdeleted = isdeleted;
        }

        public String getMyProperty() {
            return myProperty;
        }

        public void setMyProperty(String myProperty) {
            this.myProperty = myProperty;
        }
    }

    public static class IsTax {
        private String disabled;
        private String group;
        private boolean selected;
        private String text;
        private String value;

        public String getDisabled() {
            return disabled;
        }

        public void setDisabled(String disabled) {
            this.disabled = disabled;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class ItemType {
        private String value;
        private String text;
        private boolean selected;
        private boolean disabled;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public boolean getSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public boolean getDisabled() {
            return disabled;
        }

        public void setDisabled(boolean disabled) {
            this.disabled = disabled;
        }
    }

    public static class StockValue {
        private String group;
        private String text;
        private String value;
        private boolean selected;
        private boolean disabled;


        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public boolean isDisabled() {
            return disabled;
        }

        public void setDisabled(boolean disabled) {
            this.disabled = disabled;
        }
    }
}
