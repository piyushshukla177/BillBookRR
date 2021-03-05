package com.service.billbook.fragments;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.service.billbook.R;
import com.service.billbook.activities.AllTransactionHistoryActivity;
import com.service.billbook.activities.CompanySettingsActivity;
import com.service.billbook.activities.ConnectToComputerActivity;
import com.service.billbook.activities.CounterPosActivity;
import com.service.billbook.activities.CreateBillActivity;
import com.service.billbook.activities.CreatePurchaseActivity;
import com.service.billbook.activities.DeliveryChallanActivity;
import com.service.billbook.activities.ExpenseActivity;
import com.service.billbook.activities.NotificationsActivity;
import com.service.billbook.activities.PaymentOutActivity;
import com.service.billbook.activities.PurchaseOrderActivity;
import com.service.billbook.activities.PurchaseReturnActivity;
import com.service.billbook.activities.QuotationActivity;
import com.service.billbook.activities.RecievedPaymentActivity;
import com.service.billbook.activities.ReportsActivity;
import com.service.billbook.activities.SaleReturnActivity;
import com.service.billbook.adapter.TransactionListAdapter;
import com.service.billbook.model.TransactionListModel;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    NestedScrollView nestedScroll;
    Context context;
    LinearLayout create_new_purhase_linear, payment_in_linear, estimate_linear, reports_linear, expenses_linear, payment_out_linear, delivery_challan_linear, counter_pos_linear, purchase_order_linear, sales_return_linear, purchase_return_linear, third_row_linear, second_row_linear;
    CardView create_invoice_cardview;
    ImageView notification_imageview, desktop_imageview, sell_all_imageview, business_logo;

    RecyclerView recent_transactions_recylerview;
    private TransactionListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<TransactionListModel> transaction_list = new ArrayList<>();
    RelativeLayout sell_all_relative;
    TextView se_all_tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        init(root);
        return root;
    }

    int i = 0;

    void init(View root) {
        context = getActivity();
        nestedScroll = root.findViewById(R.id.nestedScroll);
        create_invoice_cardview = root.findViewById(R.id.create_invoice_cardview);
        recent_transactions_recylerview = root.findViewById(R.id.recent_transactions_recylerview);
        create_new_purhase_linear = root.findViewById(R.id.create_new_purhase_linear);
        payment_in_linear = root.findViewById(R.id.payment_in_linear);
        estimate_linear = root.findViewById(R.id.estimate_linear);
        reports_linear = root.findViewById(R.id.reports_linear);
        expenses_linear = root.findViewById(R.id.expenses_linear);
        payment_out_linear = root.findViewById(R.id.payment_out_linear);
        delivery_challan_linear = root.findViewById(R.id.delivery_challan_linear);
        counter_pos_linear = root.findViewById(R.id.counter_pos_linear);
        purchase_order_linear = root.findViewById(R.id.purchase_order_linear);
        sales_return_linear = root.findViewById(R.id.sales_return_linear);
        purchase_return_linear = root.findViewById(R.id.purchase_return_linear);
        notification_imageview = root.findViewById(R.id.notification_imageview);
        desktop_imageview = root.findViewById(R.id.desktop_imageview);
        sell_all_relative = root.findViewById(R.id.sell_all_relative);
        sell_all_imageview = root.findViewById(R.id.sell_all_imageview);
        se_all_tv = root.findViewById(R.id.se_all_tv);
        third_row_linear = root.findViewById(R.id.third_row_linear);
        second_row_linear = root.findViewById(R.id.second_row_linear);
        business_logo = root.findViewById(R.id.business_logo);

        business_logo.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), CompanySettingsActivity.class);
                        startActivity(intent);
                    }
                }
        );
        sell_all_relative.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (second_row_linear.getVisibility() == View.GONE) {
                            second_row_linear.setVisibility(View.VISIBLE);
                            third_row_linear.setVisibility(View.VISIBLE);
                            se_all_tv.setText(getString(R.string.See_Less));
                            sell_all_imageview.setImageResource(R.drawable.up_black_arrow_32);
                        } else {
                            second_row_linear.setVisibility(View.GONE);
                            third_row_linear.setVisibility(View.GONE);
                            se_all_tv.setText(getString(R.string.See_All));
                            sell_all_imageview.setImageResource(R.drawable.down_arrow_32);
                        }
                    }
                }
        );
        create_new_purhase_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), CreatePurchaseActivity.class);
                        startActivity(intent);
                    }
                }
        );
        payment_in_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), RecievedPaymentActivity.class);
                        startActivity(intent);
                    }
                }
        );
        estimate_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), QuotationActivity.class);
                        startActivity(intent);
                    }
                }
        );
        payment_out_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), PaymentOutActivity.class);
                        startActivity(intent);
                    }
                }
        );
        reports_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ReportsActivity.class);
                        startActivity(intent);
                    }
                }
        );
        expenses_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ExpenseActivity.class);
                        startActivity(intent);
                    }
                }
        );
        delivery_challan_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), DeliveryChallanActivity.class);
                        startActivity(intent);
                    }
                }
        );
        purchase_order_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), PurchaseOrderActivity.class);
                        startActivity(intent);
                    }
                }
        );
        sales_return_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), SaleReturnActivity.class);
                        startActivity(intent);
                    }
                }
        );
        purchase_return_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), PurchaseReturnActivity.class);
                        startActivity(intent);
                    }
                }
        );
        create_invoice_cardview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), CreateBillActivity.class);
                        startActivity(intent);
                    }
                }
        );
        counter_pos_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), CounterPosActivity.class);
                        startActivity(intent);
                    }
                }
        );
        notification_imageview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), NotificationsActivity.class);
                        startActivity(intent);
                    }
                }
        );
        desktop_imageview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ConnectToComputerActivity.class);
                        startActivity(intent);
                    }
                }
        );
        nestedScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (v.getChildAt(0).getBottom() <= (nestedScroll.getHeight() + scrollY)) {
//                    if (i > 0) {
                    Intent intent = new Intent(context, AllTransactionHistoryActivity.class);
                    startActivity(intent);
//                    }
//                    i++;
                }
            }
        });
        setTransactionListData();
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

        recent_transactions_recylerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        mAdapter = new TransactionListAdapter(context, transaction_list);
        recent_transactions_recylerview.setLayoutManager(mLayoutManager);
        recent_transactions_recylerview.setAdapter(mAdapter);
    }
}
