package com.service.billbook.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.service.billbook.R;
import com.service.billbook.activities.AboutActivity;
import com.service.billbook.activities.AppSettingsActivity;
import com.service.billbook.activities.BulkUploadItemActivity;
import com.service.billbook.activities.CompanySettingsActivity;
import com.service.billbook.activities.ConnectToComputerActivity;
import com.service.billbook.activities.InvoiceSettingsActivity;
import com.service.billbook.activities.ManageStaffActivity;
import com.service.billbook.activities.OnlineStoreActivity;
import com.service.billbook.activities.ProfileSettingActivity;
import com.service.billbook.activities.UpiPaymentLinkActivity;

public class MoreFragment extends Fragment {

    RelativeLayout company_settings_relative, manage_staff_relative, profile_setting_relative, invoice_setting_relative, bulk_upload_items, add_upi_relative;
    LinearLayout desktop_login_linear, online_store_linear, app_settings_linear, about_linear;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_more, container, false);
        init(root);
        return root;
    }

    void init(View root) {
        company_settings_relative = root.findViewById(R.id.company_settings_relative);
        manage_staff_relative = root.findViewById(R.id.manage_staff_relative);
        profile_setting_relative = root.findViewById(R.id.profile_setting_relative);
        invoice_setting_relative = root.findViewById(R.id.invoice_setting_relative);
        desktop_login_linear = root.findViewById(R.id.desktop_login_linear);
        online_store_linear = root.findViewById(R.id.online_store_linear);
        bulk_upload_items = root.findViewById(R.id.bulk_upload_items);
        add_upi_relative = root.findViewById(R.id.add_upi_relative);
        app_settings_linear = root.findViewById(R.id.app_settings_linear);
        about_linear = root.findViewById(R.id.about_linear);
        company_settings_relative.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), CompanySettingsActivity.class);
                        startActivity(intent);
                    }
                }
        );
        manage_staff_relative.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ManageStaffActivity.class);
                        startActivity(intent);
                    }
                }
        );
        profile_setting_relative.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ProfileSettingActivity.class);
                        startActivity(intent);
                    }
                }
        );
        invoice_setting_relative.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), InvoiceSettingsActivity.class);
                        startActivity(intent);
                    }
                }
        );
        desktop_login_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ConnectToComputerActivity.class);
                        startActivity(intent);
                    }
                }
        );

        online_store_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), OnlineStoreActivity.class);
                        startActivity(intent);
                    }
                }
        );
        bulk_upload_items.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), BulkUploadItemActivity.class);
                        startActivity(intent);
                    }
                }
        );
        add_upi_relative.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), UpiPaymentLinkActivity.class);
                        startActivity(intent);
                    }
                }
        );
        app_settings_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), AppSettingsActivity.class);
                        startActivity(intent);
                    }
                }
        );
        about_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), AboutActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
}