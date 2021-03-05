package com.service.billbook.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.service.billbook.R;
import com.service.billbook.bottomsheets.AddStockSheet;
import com.service.billbook.bottomsheets.DeletePartySheet;
import com.service.billbook.fragments.ItemDetailsTabFragment;
import com.service.billbook.fragments.PartyDetailTabFragment;
import com.service.billbook.fragments.PartyTransactionsTabFragment;
import com.service.billbook.fragments.StockTimelineTabFragment;

import java.util.ArrayList;
import java.util.List;

public class ItemDetailsActivity extends AppCompatActivity implements AddStockSheet.AddStockListener, DeletePartySheet.DeletePartySheetListener {

    Context context;
    ImageView back_image, edit_item_imageview, share_item_imageview, delete_item_imageview;
    TabLayout tabLayout;
    ViewPager viewPager;
    CardView add_stock_cardview, reduce_stock_cardview;
    TextView item_name_tv, category_name_tv, sale_price_tv, purchase_price_tv, wholesale_price_tv, stock_quantity_tv, stock_value_tv, mrp_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        init();
    }

    String category_id, item_id, mrp, item_type, item_name, quantity, sale_price, purchase_price, item_code, hsn_code, gst, unit, number_of_items, category_name, stock_value, low_stock, description;

    void init() {
        context = this;
        back_image = findViewById(R.id.back_image);
        category_name_tv = findViewById(R.id.category_name_tv);
        edit_item_imageview = findViewById(R.id.edit_item_imageview);
        share_item_imageview = findViewById(R.id.share_item_imageview);
        delete_item_imageview = findViewById(R.id.delete_item_imageview);
        add_stock_cardview = findViewById(R.id.add_stock_cardview);
        reduce_stock_cardview = findViewById(R.id.reduce_stock_cardview);
        item_name_tv = findViewById(R.id.item_name_tv);
        sale_price_tv = findViewById(R.id.sale_price_tv);
        purchase_price_tv = findViewById(R.id.purchase_price_tv);
        wholesale_price_tv = findViewById(R.id.wholesale_price_tv);
        stock_quantity_tv = findViewById(R.id.stock_quantity_tv);
        stock_value_tv = findViewById(R.id.stock_value_tv);
        mrp_tv = findViewById(R.id.mrp_tv);
        back_image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ItemDetailsActivity.super.onBackPressed();
                    }
                }
        );
        add_stock_cardview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddStockSheet bottomSheetDialogFragment = new AddStockSheet();
                        bottomSheetDialogFragment.stock_type = "Add Stock";
                        bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());

                    }
                }
        );
        reduce_stock_cardview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddStockSheet bottomSheetDialogFragment = new AddStockSheet();
                        bottomSheetDialogFragment.stock_type = "Reduce Stock";
                        bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
                    }
                }
        );
        delete_item_imageview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DeletePartySheet bottomSheet = new DeletePartySheet("item");
                        bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
                    }
                }
        );
        edit_item_imageview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ItemDetailsActivity.this, CreateItemActivity.class);
                        startActivity(intent);
                    }
                }
        );
        share_item_imageview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OpenWahstApp();
                    }
                }
        );
        Intent intent = getIntent();
        category_id = intent.getStringExtra("category_id");
        item_id = intent.getStringExtra("item_id");
        mrp = intent.getStringExtra("mrp");
        item_type = intent.getStringExtra("item_type");
        item_name = intent.getStringExtra("item_name");
        quantity = intent.getStringExtra("quantity");
        sale_price = intent.getStringExtra("sale_price");
        purchase_price = intent.getStringExtra("purchase_price");
        item_code = intent.getStringExtra("item_code");
        hsn_code = intent.getStringExtra("hsn_code");
        gst = intent.getStringExtra("gst");
        unit = intent.getStringExtra("unit");
        number_of_items = intent.getStringExtra("number_of_items");
        category_name = intent.getStringExtra("category_name");
        stock_value = intent.getStringExtra("stock_value");
        low_stock = intent.getStringExtra("low_stock");
        description = intent.getStringExtra("description");

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupViewPager(viewPager);

        item_name_tv.setText(item_name);
        if (category_name != null && !category_name.isEmpty()) {
            category_name_tv.setText("Catgeory : " + category_name);
        }
        sale_price_tv.setText("₹ " + sale_price);
        purchase_price_tv.setText("₹ " + purchase_price);
        wholesale_price_tv.setText("₹ " + sale_price);
        stock_quantity_tv.setText(quantity);
        stock_value_tv.setText("₹ " + stock_value);
        mrp_tv.setText(mrp);
    }

    private Bundle bundle;

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //bundle
        bundle = new Bundle();
        bundle.putString("item_code", item_code);
        bundle.putString("unit", unit);
        bundle.putString("low_stock", low_stock);
        bundle.putString("gst", gst);
        bundle.putString("hsn_code", hsn_code);
        bundle.putString("item_type", item_type);
        bundle.putString("description", description);
//      bundle.putString("party_name", party_name);
//      bundle.putString("party_mobile", party_mobile);
//      bundle.putString("credit_limit", credit_limit);
//      bundle.putString("party_amount", party_amount);
//      bundle.putString("party_type", party_type);
//      bundle.putString("credit_period", credit_period);
//      bundle.putString("billing_address", billing_address);
//      bundle.putString("shipping_address", shipping_address);
//      bundle.putString("gstin_number", gstin_number);
//      bundle.putString("place_of_supply", place_of_supply);

        StockTimelineTabFragment stock_timeline_fregment = new StockTimelineTabFragment();
        ItemDetailsTabFragment item_details_fregment = new ItemDetailsTabFragment();

        stock_timeline_fregment.setArguments(bundle);
        item_details_fregment.setArguments(bundle);

        adapter.addFragment(stock_timeline_fregment, "Stock Timeline");
        adapter.addFragment(item_details_fregment, "Details");
        viewPager.setAdapter(adapter);
    }


//    @Override
//    public void onDeleteParty(String text) {
//
//    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onAddStock(String text) {

    }

    @Override
    public void onDeleteParty(String text) {

    }

    void OpenWahstApp() {

        String phoneNumberWithCountryCode = "+919026547978";
        String message = "Hey, I have a query regarding Bill Book";

        startActivity(
                new Intent(Intent.ACTION_VIEW,
                        Uri.parse(
                                String.format("https://api.whatsapp.com/send?phone=%s&text=%s", phoneNumberWithCountryCode, message)
                        )
                )
        );
    }
}
