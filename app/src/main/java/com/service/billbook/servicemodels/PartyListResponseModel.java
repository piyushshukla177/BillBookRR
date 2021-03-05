package com.service.billbook.servicemodels;

import java.util.List;

public class PartyListResponseModel {

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
        private String bigintPartyId;
        private String bigintUserId;
        private String  strItemName;
        private String intIndustryTypeId;
        private String strPartyName;
        private String mobileNo;
        private String strAddress;
        private String bitIsAddressSame;
        private String strShippingAddress;
        private String strGSTCity;
        private String strGSTNo;
        private String intPartyType;
        private String intCreditPeriod;
        private String dcCreditLimit;
        private String dtCreateDate;
        private String isdeleted;
        private String _PartyList;

        public String getBigintPartyId() {
            return bigintPartyId;
        }

        public void setBigintPartyId(String bigintPartyId) {
            this.bigintPartyId = bigintPartyId;
        }

        public String getBigintUserId() {
            return bigintUserId;
        }

        public void setBigintUserId(String bigintUserId) {
            this.bigintUserId = bigintUserId;
        }

        public String getStrItemName() {
            return strItemName;
        }

        public void setStrItemName(String strItemName) {
            this.strItemName = strItemName;
        }

        public String getIntIndustryTypeId() {
            return intIndustryTypeId;
        }

        public void setIntIndustryTypeId(String intIndustryTypeId) {
            this.intIndustryTypeId = intIndustryTypeId;
        }

        public String getStrPartyName() {
            return strPartyName;
        }

        public void setStrPartyName(String strPartyName) {
            this.strPartyName = strPartyName;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public String getStrAddress() {
            return strAddress;
        }

        public void setStrAddress(String strAddress) {
            this.strAddress = strAddress;
        }

        public String getBitIsAddressSame() {
            return bitIsAddressSame;
        }

        public void setBitIsAddressSame(String bitIsAddressSame) {
            this.bitIsAddressSame = bitIsAddressSame;
        }

        public String getStrShippingAddress() {
            return strShippingAddress;
        }

        public void setStrShippingAddress(String strShippingAddress) {
            this.strShippingAddress = strShippingAddress;
        }

        public String getStrGSTCity() {
            return strGSTCity;
        }

        public void setStrGSTCity(String strGSTCity) {
            this.strGSTCity = strGSTCity;
        }

        public String getStrGSTNo() {
            return strGSTNo;
        }

        public void setStrGSTNo(String strGSTNo) {
            this.strGSTNo = strGSTNo;
        }

        public String getIntPartyType() {
            return intPartyType;
        }

        public void setIntPartyType(String intPartyType) {
            this.intPartyType = intPartyType;
        }

        public String getIntCreditPeriod() {
            return intCreditPeriod;
        }

        public void setIntCreditPeriod(String intCreditPeriod) {
            this.intCreditPeriod = intCreditPeriod;
        }

        public String getDcCreditLimit() {
            return dcCreditLimit;
        }

        public void setDcCreditLimit(String dcCreditLimit) {
            this.dcCreditLimit = dcCreditLimit;
        }

        public String getDtCreateDate() {
            return dtCreateDate;
        }

        public void setDtCreateDate(String dtCreateDate) {
            this.dtCreateDate = dtCreateDate;
        }

        public String getIsdeleted() {
            return isdeleted;
        }

        public void setIsdeleted(String isdeleted) {
            this.isdeleted = isdeleted;
        }

        public String get_PartyList() {
            return _PartyList;
        }

        public void set_PartyList(String _PartyList) {
            this._PartyList = _PartyList;
        }
    }
}
