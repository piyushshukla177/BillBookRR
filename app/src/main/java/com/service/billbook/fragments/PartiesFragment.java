package com.service.billbook.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.service.billbook.R;
import com.service.billbook.activities.CreateNewParty;
import com.service.billbook.adapter.PartyListAdapter;
import com.service.billbook.model.PartyListModel;
import com.service.billbook.network.ApiHelper;
import com.service.billbook.network.RetrofitClient;
import com.service.billbook.servicemodels.PartyListResponseModel;
import com.service.billbook.bottomsheets.FilterPartySheet;
import com.service.billbook.util.PrefsHelper;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PartiesFragment extends Fragment {

    public static PartiesFragment pp = null;
    Context context;
    RecyclerView party_recyclerview;
    private PartyListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<PartyListModel> party_list = new ArrayList<>();
    CardView create_new_party_card;
    EditText search_edittext;
    ImageView close_imageview;
    RelativeLayout parties_filter_relative;
    View root;
    private ApiHelper apiHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_parties, container, false);
        init(root);
        return root;
    }

    String user_id;

    void init(View container) {
        pp = this;
        context = getActivity();
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        user_id = PrefsHelper.getString(context, "user_id");
        setHasOptionsMenu(true);  // It's important here

        party_recyclerview = container.findViewById(R.id.party_recyclerview);
        create_new_party_card = container.findViewById(R.id.create_new_party_card);
        close_imageview = container.findViewById(R.id.close_imageview);
        search_edittext = container.findViewById(R.id.search_edittext);
        parties_filter_relative = container.findViewById(R.id.parties_filter_relative);
        create_new_party_card.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), CreateNewParty.class);
                        startActivity(intent);
                    }
                }
        );
        search_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().isEmpty()) {
                    close_imageview.setVisibility(View.GONE);
                } else {
                    close_imageview.setVisibility(View.VISIBLE);
                }
                filter(editable.toString());
            }
        });
        close_imageview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        search_edittext.setText("");
                    }
                }
        );
        parties_filter_relative.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FilterPartySheet bottomSheet = new FilterPartySheet();
                        bottomSheet.show(getActivity().getSupportFragmentManager(), "exampleBottomSheet");
                    }
                }
        );
//        setData();
        getPartyList();
    }

    private void filter(String text) {
        ArrayList<PartyListModel> filteredList = new ArrayList<>();
        for (PartyListModel item : party_list) {
            if (item.getParty_name().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mAdapter.filterList(filteredList);
    }

    PartyListModel model;

//    void setData() {
//
//        model = new PartyListModel();
//        model.setParty_name("Piyusha");
//        model.setParty_type("Supplier");
//        model.setParty_amount(String.valueOf(100));
//        party_list.add(model);
//
//        model = new PartyListModel();
//        model.setParty_name("Aniket");
//        model.setParty_type("Customer");
//        model.setParty_amount(String.valueOf(90));
//        party_list.add(model);
//
//        model = new PartyListModel();
//        model.setParty_name("Abhilash");
//        model.setParty_type("Customer");
//        model.setParty_amount(String.valueOf(95));
//        party_list.add(model);
//
//        party_recyclerview.setHasFixedSize(true);
//        mLayoutManager = new LinearLayoutManager(context);
//        mAdapter = new PartyListAdapter(context, party_list);
//        party_recyclerview.setLayoutManager(mLayoutManager);
//        party_recyclerview.setAdapter(mAdapter);
//    }

    public String sorting = "";

    public void AppyFilter(String txt) {
        if (txt.equals("high_to_low")) {
            sorting = "d";
            sortByPrice();
        } else {
            sorting = "a";
            sortByPrice();
        }
    }

    public void sortByPrice() {
        Collections.sort(party_list);
        mAdapter = new PartyListAdapter(context, party_list);
        party_recyclerview.setLayoutManager(mLayoutManager);
        party_recyclerview.setAdapter(mAdapter);
    }

    private void getPartyList() {

        final ProgressDialog mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setOnCancelListener(new Dialog.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                // DO SOME STUFF HERE
            }
        });
        mProgressDialog.show();
        Call<PartyListResponseModel> partyListCall = apiHelper.getPartyList(user_id);
        partyListCall.enqueue(new Callback<PartyListResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<PartyListResponseModel> call,
                                   @NonNull Response<PartyListResponseModel> response) {
                mProgressDialog.hide();
                if (response.isSuccessful()) {
                    if (response != null) {
                        PartyListResponseModel m = response.body();
                        if (m.getResponse().equalsIgnoreCase("true")) {

                            PartyListModel model;
                            if (m.getBody() != null && m.getBody().size() > 0) {
                                for (int i = 0; i < m.getBody().size(); i++) {
                                    model = new PartyListModel();

                                    model.setParty_name(m.getBody().get(i).getStrPartyName());
                                    if (m.getBody().get(i).getIntPartyType() == null || m.getBody().get(i).getIntPartyType().equals(String.valueOf(1))) {
                                        model.setParty_type("customer");
                                    } else {
                                        model.setParty_type("supplier");
                                    }
                                    model.setParty_amount(String.valueOf(0));
                                    model.setBigintPartyId(String.valueOf(m.getBody().get(i).getBigintPartyId()));
                                    model.setDcCreditLimit(String.valueOf(m.getBody().get(i).getDcCreditLimit()));
                                    model.setMobileNo(m.getBody().get(i).getMobileNo());
                                    model.setStrGSTNo(m.getBody().get(i).getStrGSTNo());
                                    model.setBillingAddrtess(m.getBody().get(i).getStrAddress());
                                    model.setShippingAddress(m.getBody().get(i).getStrShippingAddress());
                                    model.setPlaceOfSupply(m.getBody().get(i).getStrGSTCity());
                                    party_list.add(model);
                                }
                            }
                            party_recyclerview.setHasFixedSize(true);
                            mLayoutManager = new LinearLayoutManager(context);
                            mAdapter = new PartyListAdapter(context, party_list);
                            party_recyclerview.setLayoutManager(mLayoutManager);
                            party_recyclerview.setAdapter(mAdapter);
                        } else {
                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    mProgressDialog.hide();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PartyListResponseModel> call,
                                  @NonNull Throwable t) {
                mProgressDialog.hide();
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }
}