package com.nashir.simplewalletapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class StatisticsFragment extends Fragment {
    Toolbar toolbar;
    DatabaseHelper db;
    BarChart chartLastTransaction;

    TextView txtCountTrans, txtNominalKredit, txtNominalDebit;

    private List<LastTransaction> lastTransactionList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Statistik");

        initView(view);
        loadChart();

        db = new DatabaseHelper(getContext());
        txtCountTrans.setText(db.getDataCount() + " Transaksi");
        txtNominalDebit.setText("Rp " + db.getDebit());
        txtNominalKredit.setText("Rp " + db.getKredit());

        return view;
    }

    private void initView(View view){
        chartLastTransaction = view.findViewById(R.id.chartTransaksi);
        txtCountTrans = view.findViewById(R.id.txtTransaksi);
        txtNominalDebit = view.findViewById(R.id.txtNominalDebit);
        txtNominalKredit = view.findViewById(R.id.txtNominalKredit);
    }

    private void loadChart(){
        db = new DatabaseHelper(getContext());
        lastTransactionList = db.getLastTransaction();
        Log.d("last", "loadChart: " + lastTransactionList.size());


        List<BarEntry> dataset = new ArrayList<BarEntry>();
        int y = 0;
        for (LastTransaction lastTransaction : lastTransactionList){
            float x = (float) lastTransaction.getCount();
            dataset.add(new BarEntry(x, y));
            y++;
        }

        List<String> labels = new ArrayList<String>();
        for (LastTransaction lastTransaction : lastTransactionList){
            labels.add(lastTransaction.date);
        }

        BarDataSet dataSet = new BarDataSet(dataset, "Jumlah Transaksi");
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS[0]);

        BarData barData = new BarData(dataSet);

        chartLastTransaction.setData(barData);

        XAxis xAxis = chartLastTransaction.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(labels.size());

    }
}
