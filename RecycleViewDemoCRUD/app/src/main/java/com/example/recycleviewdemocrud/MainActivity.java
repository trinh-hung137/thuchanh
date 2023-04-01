package com.example.recycleviewdemocrud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.recycleviewdemocrud.model.Cat;
import com.example.recycleviewdemocrud.model.CatAdapter;
import com.example.recycleviewdemocrud.model.SpinnerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements CatAdapter.CatItemListener, SearchView.OnQueryTextListener {

    private Spinner spinner;
    private RecyclerView recyclerView;
    private CatAdapter adapter;
    private EditText eName, eDes, ePrice;
    private Button btnAdd, btnUpdate;
    private SearchView searchView;
    private int pcurr;
    private int[] imgs = {
            R.drawable.doan1,
            R.drawable.doan2,
            R.drawable.doan3,
            R.drawable.doan4,
            R.drawable.doan5,
            R.drawable.doan6,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        adapter = new CatAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);
        searchView.setOnQueryTextListener(this);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the InputMethodManager
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                // Hide the soft keyboard
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Cat cat = new Cat();
                String i = spinner.getSelectedItem().toString();
                String name = eName.getText().toString();
                String des = eDes.getText().toString();
                String pri = ePrice.getText().toString();
                int img = R.drawable.cat1;
                double price = 0;
                try {
                    img = imgs[Integer.parseInt(i)];
                    price = Double.parseDouble(pri);
                    cat.setImg(img);
                    cat.setName(name);
                    cat.setDes(des);
                    cat.setPrice(price);

                    adapter.add(cat);
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Nhap lai", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the InputMethodManager
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                // Hide the soft keyboard
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Cat cat = new Cat();
                String i = spinner.getSelectedItem().toString();
                String name = eName.getText().toString();
                String des = eDes.getText().toString();
                String pri = ePrice.getText().toString();
                int img = R.drawable.doan1;
                double price = 0;
                try {
                    img = imgs[Integer.parseInt(i)];
                    price = Double.parseDouble(pri);
                    cat.setImg(img);
                    cat.setName(name);
                    cat.setDes(des);
                    cat.setPrice(price);

                    adapter.update(pcurr, cat);
                    btnAdd.setEnabled(true);
                    btnUpdate.setEnabled(false);

                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Nhap lai", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        spinner = findViewById(R.id.img);
        SpinnerAdapter adapter = new SpinnerAdapter(this);
        spinner.setAdapter(adapter);
        recyclerView = findViewById(R.id.recyclerView);
        eName = findViewById(R.id.name);
        eDes = findViewById(R.id.des);
        ePrice = findViewById(R.id.price);
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setEnabled(false);
        searchView = findViewById(R.id.search);
    }


    @Override
    public void onItemClick(View view, int position) {
        btnAdd.setEnabled(false);
        btnUpdate.setEnabled(true);
        pcurr = position;
        Cat cat = adapter.getItem(position);
        int img = cat.getImg();
        int p = 0;
        for (int i = 0; i < imgs.length; i++) {
            if (img == imgs[i]) {
                p = i;
                break;
            }
        }
        spinner.setSelection(p);
        eName.setText(cat.getName());
        eDes.setText(cat.getDes());
        ePrice.setText(cat.getPrice() + "");

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        filter(s);
        return false;
    }

    private void filter(String s) {
        List<Cat> filterList = new ArrayList<>();
        for (Cat i : adapter.getBackup()) {
            if (i.getName().toLowerCase().contains(s.toLowerCase())) {
                filterList.add(i);
            }
        }
        if (filterList.isEmpty()) {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else {
            adapter.filterList(filterList);
        }
    }
}