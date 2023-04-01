package com.example.recycleviewdemocrud.model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleviewdemocrud.R;

import java.util.ArrayList;
import java.util.List;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.CatViewHolder> {

    private Context context;
    private List<Cat> cats;
    private CatItemListener catItemListener;
    private List<Cat> listBackUp;
    public CatAdapter(Context context) {
        this.context = context;
        cats = new ArrayList<>();
        listBackUp = new ArrayList<>();
    }
    public List<Cat> getBackup(){
        return listBackUp;
    }
    public void setCats(List<Cat> cats){
        this.cats = cats;
    }
    public void filterList(List<Cat> filterList){
        cats = filterList;
        notifyDataSetChanged();
    }
    public void setClickListener(CatItemListener catItemListener) {
        this.catItemListener = catItemListener;
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new CatViewHolder(view);
    }

    public Cat getItem(int position) {
        return cats.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull CatViewHolder holder, int position) {
        Cat cat = cats.get(position);
        if (cat == null)
            return;
        holder.img.setImageResource(cat.getImg());
        holder.tvName.setText(cat.getName());
        holder.tvDes.setText(cat.getDes());
        holder.tvPrice.setText(cat.getPrice() + "");
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo xóa");
                builder.setMessage("Bạn chắc chắn muốn xóa " + cat.getName() + " này không");
                builder.setIcon(R.drawable.remove);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listBackUp.remove(position);
                        cats.remove(position);
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (cats != null)
            return cats.size();
        return 0;
    }

    public void add(Cat cat) {
        listBackUp.add(cat);
        cats.add(cat);
        notifyDataSetChanged();
    }

    public void update(int position, Cat cat) {
        listBackUp.set(position, cat);
        cats.set(position, cat);
        notifyDataSetChanged();
    }

    public class CatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView img;
        private TextView tvName, tvDes, tvPrice;
        private Button btnRemove;

        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            tvName = itemView.findViewById(R.id.txName);
            tvDes = itemView.findViewById(R.id.txDes);
            tvPrice = itemView.findViewById(R.id.txPrice);
            btnRemove = itemView.findViewById(R.id.btnRemove);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (catItemListener != null) {
                catItemListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public interface CatItemListener {
        void onItemClick(View view, int position);
    }
}
