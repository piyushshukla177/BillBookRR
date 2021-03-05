package com.service.billbook.persistent;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

public class InvoiceDAO {
    @Dao
    public interface ItemsDao {
        @Insert
        void insert(InvoiceTable item);

        @Update
        void update(InvoiceTable item);

        @Delete
        void delete(InvoiceTable item);

        @Query("DELETE FROM invoice_table")
        void deleteAllItems();

        @Query("SELECT * FROM invoice_table")
        List<InvoiceTable> getAllItems();
    }
}
