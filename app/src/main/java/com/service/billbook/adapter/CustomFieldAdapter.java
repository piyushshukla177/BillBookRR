package com.service.billbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.service.billbook.R;
import com.service.billbook.activities.AdditionalItemColumnActivity;
import com.service.billbook.model.CustomFieldModel;
import java.util.ArrayList;

public class CustomFieldAdapter extends RecyclerView.Adapter<CustomFieldAdapter.CustomFieldViewHolder> {
    private ArrayList<CustomFieldModel> custom_field_list;
    private Context context;

    public class CustomFieldViewHolder extends RecyclerView.ViewHolder {
        public TextView field_name;
        public ImageView menu_imageview;

        public CustomFieldViewHolder(View itemView) {
            super(itemView);
            field_name = itemView.findViewById(R.id.field_name);
            menu_imageview = itemView.findViewById(R.id.menu_imageview);
        }
    }

    public CustomFieldAdapter(Context context, ArrayList<CustomFieldModel> list) {
        custom_field_list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomFieldViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_field_layout, parent, false);
        CustomFieldViewHolder evh = new CustomFieldViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomFieldViewHolder holder, int i) {
        final CustomFieldModel currentItem = custom_field_list.get(i);
        holder.field_name.setText(currentItem.getFieldName());
        holder.menu_imageview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popup = new PopupMenu(context, holder.menu_imageview);
                        //Inflating the Popup using xml file
                        popup.getMenuInflater().inflate(R.menu.delete_menu, popup.getMenu());
                        //registering popup with OnMenuItemClickListener
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {

                                if (item.getTitle().equals("Delete")) {
//                                    removeAt(i);
                                    ((AdditionalItemColumnActivity) context).DeleteCustomColumn(currentItem.getFieldId(),i);

                                }
                                return true;
                            }
                        });
                        popup.show(); //showing popup menu
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return custom_field_list.size();
    }

//    public void removeAt(int position) {
//        custom_field_list.remove(position);
//        notifyDataSetChanged();
//    }
}
