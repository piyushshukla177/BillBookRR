package com.service.billbook.activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.service.billbook.R;
import com.service.billbook.adapter.TransactionListAdapter;
import com.service.billbook.model.TransactionListModel;
import com.service.billbook.bottomsheets.FilterTransactionSheet;
import java.util.ArrayList;

public class AllTransactionHistoryActivity extends AppCompatActivity implements FilterTransactionSheet.TransactionFilterListener {

    Context context;
    RecyclerView transaction_recylerview;
    private TransactionListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<TransactionListModel> transaction_list = new ArrayList<>();
    ImageView back_image, filter_transaction_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_transaction_history);
        init();
    }

    void init() {
        context = this;
        transaction_recylerview = findViewById(R.id.transaction_recylerview);
        back_image = findViewById(R.id.back_image);
        filter_transaction_image = findViewById(R.id.filter_transaction_image);
        setTransactionListData();

        back_image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AllTransactionHistoryActivity.super.onBackPressed();
                    }
                }
        );
        filter_transaction_image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FilterTransactionSheet bottomSheet = new FilterTransactionSheet();
                        bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
                    }
                }
        );
    }

    TransactionListModel model;

    void setTransactionListData() {

        model = new TransactionListModel();
        model.setParty_name("Piyusha");
        model.setInvoice_amount("400");
        model.setInvoice_date("10/01/21");
        model.setInvoice_number("4");
        model.setPayment_status("PAID");
        model.setDue_date("17/01/21");
        transaction_list.add(model);

        model = new TransactionListModel();
        model.setParty_name("Piyusha");
        model.setInvoice_amount("400");
        model.setInvoice_date("10/01/21");
        model.setInvoice_number("4");
        model.setDue_date("17/01/21");
        model.setPayment_status("UNPAID");
        transaction_list.add(model);

        model = new TransactionListModel();
        model.setParty_name("Piyusha");
        model.setInvoice_amount("400");
        model.setInvoice_date("10/01/21");
        model.setInvoice_number("4");
        model.setDue_date("17/01/21");
        model.setPayment_status("UNPAID");
        transaction_list.add(model);

        model = new TransactionListModel();
        model.setParty_name("Piyusha");
        model.setInvoice_amount("400");
        model.setInvoice_date("10/01/21");
        model.setDue_date("17/01/21");
        model.setInvoice_number("4");
        model.setPayment_status("UNPAID");
        transaction_list.add(model);

        model = new TransactionListModel();
        model.setParty_name("Piyusha");
        model.setInvoice_amount("400");
        model.setInvoice_date("10/01/21");
        model.setDue_date("17/01/21");
        model.setInvoice_number("4");
        model.setPayment_status("PAID");
        transaction_list.add(model);

        model = new TransactionListModel();
        model.setParty_name("Piyusha");
        model.setInvoice_amount("400");
        model.setInvoice_date("10/01/21");
        model.setInvoice_number("4");
        model.setPayment_status("PAID");
        transaction_list.add(model);
        model = new TransactionListModel();
        model.setParty_name("Piyusha");
        model.setInvoice_amount("400");
        model.setInvoice_date("10/01/21");
        model.setInvoice_number("4");
        model.setPayment_status("PAID");
        model.setDue_date("17/01/21");
        transaction_list.add(model);

        model = new TransactionListModel();
        model.setParty_name("Piyusha");
        model.setInvoice_amount("400");
        model.setInvoice_date("10/01/21");
        model.setInvoice_number("4");
        model.setDue_date("17/01/21");
        model.setPayment_status("UNPAID");
        transaction_list.add(model);

        model = new TransactionListModel();
        model.setParty_name("Piyusha");
        model.setInvoice_amount("400");
        model.setInvoice_date("10/01/21");
        model.setInvoice_number("4");
        model.setDue_date("17/01/21");
        model.setPayment_status("UNPAID");
        transaction_list.add(model);

        model = new TransactionListModel();
        model.setParty_name("Piyusha");
        model.setInvoice_amount("400");
        model.setInvoice_date("10/01/21");
        model.setDue_date("17/01/21");
        model.setInvoice_number("4");
        model.setPayment_status("UNPAID");
        transaction_list.add(model);

        transaction_recylerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        mAdapter = new TransactionListAdapter(context, transaction_list);
        transaction_recylerview.setLayoutManager(mLayoutManager);
        transaction_recylerview.setAdapter(mAdapter);
    }

    @Override
    public void onTransactionFilter(String text) {

    }
}
