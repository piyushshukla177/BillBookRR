package com.service.billbook.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.service.billbook.R;
import com.service.billbook.bottomsheets.DeletePartySheet;
import com.service.billbook.bottomsheets.FilterItemBottomSheet;
import com.service.billbook.bottomsheets.SelectCategorySheet;
import com.service.billbook.fragments.PartyDetailTabFragment;
import com.service.billbook.fragments.PartyTransactionsTabFragment;

import java.util.ArrayList;
import java.util.List;

public class PartyDetailsActivity extends AppCompatActivity implements DeletePartySheet.DeletePartySheetListener {

    Context context;
    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView back_image;
    TextView party_name_tv, party_balance_tv, party_type_tv, delete_party_tv, edit_party_tv;
    String party_id, party_name, party_mobile, credit_limit, party_amount, party_type, credit_period, billing_address, shipping_address, gstin_number, place_of_supply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_details);
        init();
    }

    void init() {
        context = this;
        back_image = findViewById(R.id.back_image);
        party_name_tv = findViewById(R.id.party_name_tv);
        party_balance_tv = findViewById(R.id.party_balance_tv);
        party_type_tv = findViewById(R.id.party_type_tv);
        delete_party_tv = findViewById(R.id.delete_party_tv);
        edit_party_tv = findViewById(R.id.edit_party_tv);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        back_image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PartyDetailsActivity.super.onBackPressed();
                    }
                }
        );

        Intent intent = getIntent();
        party_id = intent.getStringExtra("party_id");
        party_name = intent.getStringExtra("party_name");
        party_mobile = intent.getStringExtra("party_mobile");
        credit_limit = intent.getStringExtra("credit_limit");
        party_amount = intent.getStringExtra("party_amount");
        party_type = intent.getStringExtra("party_type");
        credit_period = intent.getStringExtra("credit_period");
        billing_address = intent.getStringExtra("billing_address");
        shipping_address = intent.getStringExtra("shipping_address");
        gstin_number = intent.getStringExtra("gstin_number");
        place_of_supply = intent.getStringExtra("place_of_supply");

        party_name_tv.setText(party_name);
        party_type_tv.setText(party_type);
        party_balance_tv.setText("₹ " + party_amount);
        setupViewPager(viewPager);

        delete_party_tv.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DeletePartySheet bottomSheet = new DeletePartySheet("party");
                        bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
                    }
                }
        );
        edit_party_tv.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(PartyDetailsActivity.this, CreateNewParty.class);
                        intent.putExtra("party_id", party_id);
                        intent.putExtra("party_name", party_name);
                        intent.putExtra("party_mobile", party_mobile);
                        intent.putExtra("credit_limit", credit_limit);
                        intent.putExtra("party_amount", party_amount);
                        intent.putExtra("party_type", party_type);
                        intent.putExtra("credit_period", credit_period);
                        intent.putExtra("billing_address", billing_address);
                        intent.putExtra("shipping_address", shipping_address);
                        intent.putExtra("gstin_number", gstin_number);
                        intent.putExtra("place_of_supply", place_of_supply);
                        startActivity(intent);
                    }
                }
        );
    }

    private Bundle bundle;

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        //bundle
        bundle = new Bundle();
        bundle.putString("party_id", party_id);
        bundle.putString("party_name", party_name);
        bundle.putString("party_mobile", party_mobile);
        bundle.putString("credit_limit", credit_limit);
        bundle.putString("party_amount", party_amount);
        bundle.putString("party_type", party_type);
        bundle.putString("credit_period", credit_period);
        bundle.putString("billing_address", billing_address);
        bundle.putString("shipping_address", shipping_address);
        bundle.putString("gstin_number", gstin_number);
        bundle.putString("place_of_supply", place_of_supply);

        PartyTransactionsTabFragment party_transaction_fregment = new PartyTransactionsTabFragment();
        PartyDetailTabFragment party_details_fregment = new PartyDetailTabFragment();

        party_transaction_fregment.setArguments(bundle);
        party_details_fregment.setArguments(bundle);

        adapter.addFragment(party_transaction_fregment, "Transactions");
        adapter.addFragment(party_details_fregment, "Details");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onDeleteParty(String text) {

    }

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
}