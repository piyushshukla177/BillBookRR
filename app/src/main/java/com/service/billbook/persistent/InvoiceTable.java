package com.service.billbook.persistent;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "invoice_table")
public class InvoiceTable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String barcode;
    private String name;
    private String rate;
    private String quantity;
    private String total;

    public InvoiceTable(String barcode, String name, String rate, String quantity, String total) {
        this.barcode = barcode;
        this.name = name;
        this.rate = rate;
        this.quantity = quantity;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
