package com.service.billbook.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.service.billbook.R;
import com.service.billbook.fragments.DashboardFragment;
import com.service.billbook.fragments.ItemsFragment;
import com.service.billbook.fragments.MoreFragment;
import com.service.billbook.fragments.PartiesFragment;
import com.service.billbook.fragments.QueryFragment;
import com.service.billbook.bottomsheets.AddStockSheet;
import com.service.billbook.bottomsheets.FilterItemBottomSheet;
import com.service.billbook.bottomsheets.FilterPartySheet;
import com.service.billbook.bottomsheets.SelectCategorySheet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class DashboardActivity extends AppCompatActivity implements FilterItemBottomSheet.FilterItemSheet, SelectCategorySheet.CategoryListener, FilterPartySheet.FilterPartySheetListener, GoogleApiClient.ConnectionCallbacks, AddStockSheet.AddStockListener {

    BottomNavigationView bottom_navigation;
    Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new DashboardFragment()).commit();
        }
        bottom_navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnNavigationItemSelectedListener(navListener);
    }

    //bottom menu click events
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {

                        case R.id.nav_dashboard:
                            selectedFragment = new DashboardFragment();
                            break;
                        case R.id.nav_parties:
                            selectedFragment = new PartiesFragment();
                            break;
                        case R.id.nav_items:
                            selectedFragment = new ItemsFragment();
                            break;
                        case R.id.nav_query:
                            selectedFragment = new QueryFragment();
                            break;
                        case R.id.nav_more:
                            selectedFragment = new MoreFragment();
                            break;
                    }
                    if (selectedFragment != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                selectedFragment).commit();
                    }
                    currentFragment = selectedFragment;
                    return true;
                }
            };

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.searchview_menu, menu);
//        return true;
//    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onFilterItemClicked(String text) {
        if (text.equals("qty_low_to_high")) {
            ((ItemsFragment) currentFragment).AppyFilter(text);
        }
        if (text.equals("high_to_low_qty")) {
            ((ItemsFragment) currentFragment).AppyFilter(text);
        }
        if (text.equals("a_to_z")) {
            ((ItemsFragment) currentFragment).AppyFilter(text);
        } if(text.equals("z_to_a")) {
            ((ItemsFragment) currentFragment).AppyFilter(text);
        }
    }

    @Override
    public void onCategorySelected(String text) {

    }

    @Override
    public void onFilterParty(String text) {

        if (text.equals("high_to_low")) {
//            PartiesFragment.pp.AppyFilter(text);-2
//
            ((PartiesFragment) currentFragment).AppyFilter(text);
        } else {
//            PartiesFragment.pp.AppyFilter(text);
            ((PartiesFragment) currentFragment).AppyFilter(text);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAddStock(String text) {

    }
}