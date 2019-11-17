package com.nashir.simplewalletapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class HistoryFragment extends Fragment {
    TextView txtBalance;
    RecyclerView rvHistory;
    DatabaseHelper db;

    private HistoryAdapter historyAdapter;
    private List<History> historyList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Riwayat Transaksi");

        initView(view);
        loadData();


        return view;
    }

    public void loadData() {

        db = new DatabaseHelper(getContext());
        historyList = db.getAllData();
        historyAdapter = new HistoryAdapter(historyList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvHistory.setLayoutManager(mLayoutManager);
        rvHistory.setItemAnimator(new DefaultItemAnimator());
        rvHistory.setAdapter(historyAdapter);

        txtBalance.setText("Rp " + db.getBalance());
    }



    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void initView(View view) {
        txtBalance = view.findViewById(R.id.txtSaldo);
        rvHistory = view.findViewById(R.id.rvRiwayat);
    }

}
