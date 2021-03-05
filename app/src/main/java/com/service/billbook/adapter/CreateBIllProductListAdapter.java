package com.service.billbook.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.service.billbook.R;
import com.service.billbook.activities.CreateBillActivity;
import com.service.billbook.model.CreateBillItemListModel;
import com.service.billbook.bottomsheets.EditItemSheet;
import java.util.ArrayList;

public class CreateBIllProductListAdapter extends RecyclerView.Adapter<CreateBIllProductListAdapter.CraeteBillListViewHolder> {
    private ArrayList<CreateBillItemListModel> product_list;
    private Context context;

    public class CraeteBillListViewHolder extends RecyclerView.ViewHolder {
        public TextView name_tv, unit_tv, total_tv;
        public ImageView menu_imageview;

        public CraeteBillListViewHolder(View itemView) {
            super(itemView);
            name_tv = itemView.findViewById(R.id.name_tv);
            unit_tv = itemView.findViewById(R.id.unit_tv);
            total_tv = itemView.findViewById(R.id.total_tv);
            menu_imageview = itemView.findViewById(R.id.menu_imageview);
        }
    }

    public CreateBIllProductListAdapter(Context context, ArrayList<CreateBillItemListModel> list) {
        product_list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CraeteBillListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.create_bill_product_list_layout, parent, false);
        CraeteBillListViewHolder evh = new CraeteBillListViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final CraeteBillListViewHolder holder, int i) {
        final CreateBillItemListModel currentItem = product_list.get(i);
        holder.name_tv.setText(currentItem.getName());
        holder.total_tv.setText("₹ " + Float.parseFloat(currentItem.getRate()) * Integer.parseInt(currentItem.getQty()));
        holder.unit_tv.setText("Rate " + currentItem.getQty() + " " + currentItem.getUnit() + " X " + currentItem.getRate());
        holder.menu_imageview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popup = new PopupMenu(context, holder.menu_imageview);
                        //Inflating the Popup using xml file
                        popup.getMenuInflater().inflate(R.menu.item_edit_delete_menu, popup.getMenu());
                        //registering popup with OnMenuItemClickListener
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {
                                Toast.makeText(
                                        context,
                                        "You Clicked : " + item.getTitle(),
                                        Toast.LENGTH_SHORT
                                ).show();
                                if (item.getTitle().equals("Delete")) {
                                    removeAt(i);
                                } else if (item.getTitle().equals("Edit")) {
                                    EditItemSheet bottomSheetDialogFragment = new EditItemSheet();
                                    bottomSheetDialogFragment.product_name_txt = currentItem.getName();
                                    bottomSheetDialogFragment.price_txt = currentItem.getRate();
                                    bottomSheetDialogFragment.qty_txt = currentItem.getQty();
                                    bottomSheetDialogFragment.unit_txt = currentItem.getUnit();
                                    bottomSheetDialogFragment.product_name_txt = currentItem.getName();
                                    bottomSheetDialogFragment.show(((CreateBillActivity) context).getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
//                                  bottomSheetDialogFragment.setValues(currentItem.getName());
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
        return product_list.size();
    }

    public void removeAt(int position) {
        product_list.remove(position);
        notifyDataSetChanged();
//      notifyItemRemoved(position);
//      notifyItemRangeChanged(position, product_list.size());
        ((CreateBillActivity) context).setTotal(product_list, position);
    }
}
