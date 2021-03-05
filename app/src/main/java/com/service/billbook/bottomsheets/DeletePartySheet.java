package com.service.billbook.bottomsheets;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.service.billbook.R;

public class DeletePartySheet extends BottomSheetDialogFragment {

    private DeletePartySheetListener mListener;
    String type;
    Button delete_party_btn, no_button;
    TextView delete_party_tv, message_tv;

    public DeletePartySheet(String type) {
        this.type = type;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.delete_party_sheet_layout, container, false);

        delete_party_btn = v.findViewById(R.id.delete_party_btn);
        delete_party_tv = v.findViewById(R.id.delete_party_tv);
        message_tv = v.findViewById(R.id.message_tv);
        no_button = v.findViewById(R.id.no_button);
        delete_party_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                }
        );
        no_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                }
        );
        if (type != null && type.equals("item")) {
            delete_party_tv.setText("Delete Item");
            message_tv.setText(getString(R.string.Are_you_sure_want_to_delete_this_Item_Deleted_Item_can_not_be_retrieved));
        }
        return v;
    }

    public interface DeletePartySheetListener {
        void onDeleteParty(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (DeletePartySheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}