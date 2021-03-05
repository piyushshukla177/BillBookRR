package com.service.billbook.persistent;
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {InvoiceTable.class, AdditionalInfoTable.class}, version = 1)
public abstract class InvoiceDb extends RoomDatabase {
    private static InvoiceDb instance;

    public abstract InvoiceDAO.ItemsDao ItemsDao();
    public abstract AdditionalInfoDAO.AdditionalDao AdditionalDao();

    public static synchronized InvoiceDb getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    InvoiceDb.class, "invoice_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}