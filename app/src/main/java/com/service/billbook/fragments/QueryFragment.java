package com.service.billbook.fragments;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.google.android.material.tabs.TabLayout;
import com.service.billbook.R;
import java.util.ArrayList;
import java.util.List;

public class QueryFragment extends Fragment {
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;
    TabLayout tabLayout;
    ViewPager viewPager;
    Context context;
    ImageView whatsapp_imageview, call_imageview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_query, container, false);
        init(root);
        return root;
    }

    void init(View root) {
        viewPager = (ViewPager) root.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_ASK_PERMISSIONS);
        whatsapp_imageview = root.findViewById(R.id.whatsapp_imageview);
        call_imageview = root.findViewById(R.id.call_imageview);
        tabLayout = (TabLayout) root.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        whatsapp_imageview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OpenWahstApp();
                    }
                }
        );
        call_imageview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "9026547978"));
                        startActivity(intent);
                    }
                }
        );
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new PopularTabFragment(), "Popular");
        adapter.addFragment(new InvoiceTabFragment(), "Invoice");
        adapter.addFragment(new ItemTabFragment(), "Item");
        adapter.addFragment(new PartyTabFragment(), "Party");
        adapter.addFragment(new TransactionTabFragment(), "Transaction");
        adapter.addFragment(new OtherTabFragment(), "Other");

        viewPager.setAdapter(adapter);
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