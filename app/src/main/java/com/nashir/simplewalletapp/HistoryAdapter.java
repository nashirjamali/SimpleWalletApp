package com.nashir.simplewalletapp;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<History> historyList = new ArrayList<>();
    DatabaseHelper db;


    public HistoryAdapter(List<History> historyList) {
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final HistoryAdapter.ViewHolder holder, final int position) {
        final History history = historyList.get(position);

        Log.d("123444",history.getType());

        if (history.getType().equals("Kredit")){
            holder.imgType.setImageResource(R.drawable.ic_minimize_black_24dp);
            holder.imgType.setBackgroundResource(R.drawable.icon_kredit);
            holder.txtNominal.setTextColor(Color.parseColor("#FF375F"));
        }else {
            holder.imgType.setImageResource(R.drawable.ic_add_black_24dp);
            holder.imgType.setBackgroundResource(R.drawable.icon_debit);
            holder.txtNominal.setTextColor(Color.parseColor("#30D158"));
        }

        holder.txtDate.setText(history.getDate());
        holder.txtNominal.setText("Rp " + history.getNominal());
        holder.txtDesc.setText(history.getDescription());
        holder.id = history.getId();

        holder.txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                db = new DatabaseHelper(activity);
                boolean isDelete = db.deleteData(holder.id);
                if (isDelete){
                    Toast.makeText(activity, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                    historyList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeRemoved(position, historyList.size());

                }else {
                    Toast.makeText(activity, "Data Gagal Dihapus", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtDesc, txtNominal, txtDate, txtDelete;
        ImageView imgType;
        int id;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgType = itemView.findViewById(R.id.imgTipe);
            txtDesc = itemView.findViewById(R.id.txtUraian);
            txtNominal = itemView.findViewById(R.id.txtNominal);
            txtDate = itemView.findViewById(R.id.txtTanggal);
            txtDelete = itemView.findViewById(R.id.txtHapus);
        }




    }
}
