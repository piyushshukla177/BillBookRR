package com.service.billbook.servicemodels;

import java.util.List;

public  class ItemListResponseModel {

    private List<Body> body;
    private String message;
    private String response;

    public List<Body> getBody() {
        return body;
    }

    public void setBody(List<Body> body) {
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
        private int number_of_items;
        private int bigintCategoryId;
        private String dtCreatedDate;
        private int dcOpeningStock;
        private int bigIntStockId;
        private int stockvalue;
        private boolean bitisdeleted;
        private String strSAC;
        private boolean bitIsAdditionalColumn;
        private String strPicE;
        private String strPicD;
        private String strPicC;
        private String strPicB;
        private String strPicA;
        private String strItemCode;
        private boolean bitisSaleTaxInclude;
        private boolean bitisPurchaseTaxInclude;
        private int intLowStock;
        private int intGst;
        private String strHSNCode;
        private int intSecondaryUnit;
        private int intPriceUnit;
        private int dcMRP;
        private int dcPurchasePrice;
        private int dcSalePrice;
        private String strDescription;
        private int intItemType;
        private String strItemName;
        private String strCategoryName;
        private int bigintUserId;
        private int bigintItemId;
        private String strUnitName;
        private String strShortName;

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

        public boolean isBitisdeleted() {
            return bitisdeleted;
        }

        public boolean isBitIsAdditionalColumn() {
            return bitIsAdditionalColumn;
        }

        public boolean isBitisSaleTaxInclude() {
            return bitisSaleTaxInclude;
        }

        public boolean isBitisPurchaseTaxInclude() {
            return bitisPurchaseTaxInclude;
        }

        public String getStrCategoryName() {
            return strCategoryName;
        }

        public void setStrCategoryName(String strCategoryName) {
            this.strCategoryName = strCategoryName;
        }

        public int getNumber_of_items() {
            return number_of_items;
        }

        public void setNumber_of_items(int number_of_items) {
            this.number_of_items = number_of_items;
        }

        public int getBigintCategoryId() {
            return bigintCategoryId;
        }

        public void setBigintCategoryId(int bigintCategoryId) {
            this.bigintCategoryId = bigintCategoryId;
        }

        public String getDtCreatedDate() {
            return dtCreatedDate;
        }

        public void setDtCreatedDate(String dtCreatedDate) {
            this.dtCreatedDate = dtCreatedDate;
        }

        public int getDcOpeningStock() {
            return dcOpeningStock;
        }

        public void setDcOpeningStock(int dcOpeningStock) {
            this.dcOpeningStock = dcOpeningStock;
        }

        public int getBigIntStockId() {
            return bigIntStockId;
        }

        public void setBigIntStockId(int bigIntStockId) {
            this.bigIntStockId = bigIntStockId;
        }

        public int getStockvalue() {
            return stockvalue;
        }

        public void setStockvalue(int stockvalue) {
            this.stockvalue = stockvalue;
        }

        public boolean getBitisdeleted() {
            return bitisdeleted;
        }

        public void setBitisdeleted(boolean bitisdeleted) {
            this.bitisdeleted = bitisdeleted;
        }

        public String getStrSAC() {
            return strSAC;
        }

        public void setStrSAC(String strSAC) {
            this.strSAC = strSAC;
        }

        public boolean getBitIsAdditionalColumn() {
            return bitIsAdditionalColumn;
        }

        public void setBitIsAdditionalColumn(boolean bitIsAdditionalColumn) {
            this.bitIsAdditionalColumn = bitIsAdditionalColumn;
        }

        public String getStrPicE() {
            return strPicE;
        }

        public void setStrPicE(String strPicE) {
            this.strPicE = strPicE;
        }

        public String getStrPicD() {
            return strPicD;
        }

        public void setStrPicD(String strPicD) {
            this.strPicD = strPicD;
        }

        public String getStrPicC() {
            return strPicC;
        }

        public void setStrPicC(String strPicC) {
            this.strPicC = strPicC;
        }

        public String getStrPicB() {
            return strPicB;
        }

        public void setStrPicB(String strPicB) {
            this.strPicB = strPicB;
        }

        public String getStrPicA() {
            return strPicA;
        }

        public void setStrPicA(String strPicA) {
            this.strPicA = strPicA;
        }

        public String getStrItemCode() {
            return strItemCode;
        }

        public void setStrItemCode(String strItemCode) {
            this.strItemCode = strItemCode;
        }

        public boolean getBitisSaleTaxInclude() {
            return bitisSaleTaxInclude;
        }

        public void setBitisSaleTaxInclude(boolean bitisSaleTaxInclude) {
            this.bitisSaleTaxInclude = bitisSaleTaxInclude;
        }

        public boolean getBitisPurchaseTaxInclude() {
            return bitisPurchaseTaxInclude;
        }

        public void setBitisPurchaseTaxInclude(boolean bitisPurchaseTaxInclude) {
            this.bitisPurchaseTaxInclude = bitisPurchaseTaxInclude;
        }

        public int getIntLowStock() {
            return intLowStock;
        }

        public void setIntLowStock(int intLowStock) {
            this.intLowStock = intLowStock;
        }

        public int getIntGst() {
            return intGst;
        }

        public void setIntGst(int intGst) {
            this.intGst = intGst;
        }

        public String getStrHSNCode() {
            return strHSNCode;
        }

        public void setStrHSNCode(String strHSNCode) {
            this.strHSNCode = strHSNCode;
        }

        public int getIntSecondaryUnit() {
            return intSecondaryUnit;
        }

        public void setIntSecondaryUnit(int intSecondaryUnit) {
            this.intSecondaryUnit = intSecondaryUnit;
        }

        public int getIntPriceUnit() {
            return intPriceUnit;
        }

        public void setIntPriceUnit(int intPriceUnit) {
            this.intPriceUnit = intPriceUnit;
        }

        public int getDcMRP() {
            return dcMRP;
        }

        public void setDcMRP(int dcMRP) {
            this.dcMRP = dcMRP;
        }

        public int getDcPurchasePrice() {
            return dcPurchasePrice;
        }

        public void setDcPurchasePrice(int dcPurchasePrice) {
            this.dcPurchasePrice = dcPurchasePrice;
        }

        public int getDcSalePrice() {
            return dcSalePrice;
        }

        public void setDcSalePrice(int dcSalePrice) {
            this.dcSalePrice = dcSalePrice;
        }

        public String getStrDescription() {
            return strDescription;
        }

        public void setStrDescription(String strDescription) {
            this.strDescription = strDescription;
        }

        public int getIntItemType() {
            return intItemType;
        }

        public void setIntItemType(int intItemType) {
            this.intItemType = intItemType;
        }

        public String getStrItemName() {
            return strItemName;
        }

        public void setStrItemName(String strItemName) {
            this.strItemName = strItemName;
        }

        public int getBigintUserId() {
            return bigintUserId;
        }

        public void setBigintUserId(int bigintUserId) {
            this.bigintUserId = bigintUserId;
        }

        public int getBigintItemId() {
            return bigintItemId;
        }

        public void setBigintItemId(int bigintItemId) {
            this.bigintItemId = bigintItemId;
        }
    }
}
