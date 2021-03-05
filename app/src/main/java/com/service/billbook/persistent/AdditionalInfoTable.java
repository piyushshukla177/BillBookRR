package com.service.billbook.persistent;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "additional_table")
public class AdditionalInfoTable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String invoice_number;
    private String party_name;
    private String invoice_date;
    private String additional_charges;
    private String additional_charges_name;
    private String discount_percent;
    private String discount_rupee;
    private String round_off;
    private String round_type;
    private String notes;
    private String cash_recieved;

    public AdditionalInfoTable(String invoice_number, String party_name, String invoice_date, String additional_charges, String additional_charges_name, String discount_percent, String discount_rupee, String round_off, String round_type, String notes, String cash_recieved) {
        this.invoice_number = invoice_number;
        this.party_name = party_name;
        this.invoice_date = invoice_date;
        this.additional_charges = additional_charges;
        this.additional_charges_name = additional_charges_name;
        this.discount_percent = discount_percent;
        this.discount_rupee = discount_rupee;
        this.round_off = round_off;
        this.round_type = round_type;
        this.notes = notes;
        this.cash_recieved = cash_recieved;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public String getInvoice_number() {
        return invoice_number;
    }

    public String getParty_name() {
        return party_name;
    }

    public String getInvoice_date() {
        return invoice_date;
    }

    public String getAdditional_charges() {
        return additional_charges;
    }

    public String getDiscount_percent() {
        return discount_percent;
    }

    public String getDiscount_rupee() {
        return discount_rupee;
    }

    public String getRound_off() {
        return round_off;
    }

    public String getRound_type() {
        return round_type;
    }

    public String getNotes() {
        return notes;
    }

    public String getCash_recieved() {
        return cash_recieved;
    }

    public String getAdditional_charges_name() {
        return additional_charges_name;
    }
}
