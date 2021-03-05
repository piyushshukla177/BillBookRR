package com.service.billbook.persistent;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

public class AdditionalInfoDAO {
    @Dao
    public interface AdditionalDao {
        @Insert
        void insert(AdditionalInfoTable additional);

        @Update
        void update(AdditionalInfoTable additional);

        @Delete
        void delete(AdditionalInfoTable additional);

        @Query("DELETE FROM additional_table")
        void deleteAllAdditionalInfo();

        @Query("SELECT * FROM additional_table")
        List<AdditionalInfoTable> getAllAdditionalInfo();
    }
}
