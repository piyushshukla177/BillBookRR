package com.service.billbook.activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.SearchView;
import com.service.billbook.R;
import com.service.billbook.adapter.AddProductsinCategoryAdapter;
import com.service.billbook.model.AddProductsinCategoryModel;
import java.util.ArrayList;

public class AddProductsInCategoryActivity extends AppCompatActivity {

    public RecyclerView item_list_recyclerview;
    RelativeLayout relative_click_search, relative_search;
    ImageView back_imageview, item_search_imageview;
    SearchView item_searchview;
    TextView category_name_tv;
    Context context;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<AddProductsinCategoryModel> product_list = new ArrayList<>();
    public static AddProductsInCategoryActivity app;

    String category_name, category_id, catgeory_items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products_in_category);
        init();
    }

    AddProductsinCategoryModel m;

    void init() {
        app = this;
        Intent intent = getIntent();
        category_name = intent.getStringExtra("category_name");
        category_id = intent.getStringExtra("category_id");
        catgeory_items = intent.getStringExtra("category_items");

        item_list_recyclerview = findViewById(R.id.item_list_recyclerview);
        relative_click_search = findViewById(R.id.relative_click_search);
        relative_search = findViewById(R.id.relative_search);
        back_imageview = findViewById(R.id.back_imageview);
        item_search_imageview = findViewById(R.id.item_search_imageview);
        item_searchview = findViewById(R.id.item_searchview);
        category_name_tv = findViewById(R.id.category_name_tv);
        category_name_tv.setText(category_name+" ("+catgeory_items+")");

        item_search_imageview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        item_searchview.setIconified(false);
                        relative_click_search.setVisibility(View.GONE);
                        relative_search.setVisibility(View.VISIBLE);
                    }
                }
        );

        back_imageview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        relative_click_search.setVisibility(View.VISIBLE);
                        relative_search.setVisibility(View.GONE);
                    }
                }
        );
        m = new AddProductsinCategoryModel();
        m.setProduct_name_tv("Sample Item");
        m.setItem_selected_state("F");
        product_list.add(m);

        m = new AddProductsinCategoryModel();
        m.setProduct_name_tv("Anik Cow Ghee 200 ML");
        m.setItem_selected_state("F");
        product_list.add(m);

        m = new AddProductsinCategoryModel();
        m.setProduct_name_tv("Ghee");
        m.setItem_selected_state("F");
        product_list.add(m);

        item_list_recyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        mAdapter = new AddProductsinCategoryAdapter(context, product_list);
        item_list_recyclerview.setLayoutManager(mLayoutManager);
        item_list_recyclerview.setAdapter(mAdapter);
    }
}