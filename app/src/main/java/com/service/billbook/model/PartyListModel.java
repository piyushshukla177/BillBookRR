package com.service.billbook.model;

import com.service.billbook.fragments.PartiesFragment;

public class PartyListModel implements Comparable {

    private String bigintPartyId;
    private String party_name;
    private String party_type;
    private String party_amount;
    private String mobileNo;
    private String strGSTNo;
    private String intCreditPeriod;
    private String dcCreditLimit;
    private String shippingAddress;
    private String placeOfSupply;
    private String billingAddrtess;

    public String getParty_name() {
        return party_name;
    }

    public void setParty_name(String party_name) {
        this.party_name = party_name;
    }

    public String getParty_type() {
        return party_type;
    }

    public void setParty_type(String party_type) {
        this.party_type = party_type;
    }

    public String getParty_amount() {
        return party_amount;
    }

    public void setParty_amount(String party_amount) {
        this.party_amount = party_amount;
    }

    public String getBigintPartyId() {
        return bigintPartyId;
    }

    public void setBigintPartyId(String bigintPartyId) {
        this.bigintPartyId = bigintPartyId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getStrGSTNo() {
        return strGSTNo;
    }

    public void setStrGSTNo(String strGSTNo) {
        this.strGSTNo = strGSTNo;
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

    @Override
    public int compareTo(Object o) {
        String compareage = ((PartyListModel) o).getParty_amount();
        int c = Integer.parseInt(compareage);
//      /* For Ascending order*/
        if (PartiesFragment.pp.sorting.equals("a")) {
            return Integer.parseInt(this.party_amount) - c;
        } else {
            return c - Integer.parseInt(this.party_amount);
        }
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getPlaceOfSupply() {
        return placeOfSupply;
    }

    public void setPlaceOfSupply(String placeOfSupply) {
        this.placeOfSupply = placeOfSupply;
    }

    public String getBillingAddrtess() {
        return billingAddrtess;
    }

    public void setBillingAddrtess(String billingAddrtess) {
        this.billingAddrtess = billingAddrtess;
    }
}
