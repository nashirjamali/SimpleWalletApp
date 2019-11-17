package com.nashir.simplewalletapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    DatabaseHelper db;
    TextView txtTime;
    EditText etName, etNominal;
    RadioGroup rgType;
    RadioButton radioButton;
    Button btnAdd;
    Toolbar toolbar;
    ImageView btnAddData;
    FloatingActionButton fabAdd;

    RecyclerView rvHistory;

    private HistoryAdapter historyAdapter;
    private List<History> historyList = new ArrayList<>();

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);

        initView();
//        getToday();
//        loadData();

        setSupportActionBar(toolbar);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddRecord.class);
                startActivity(intent);
            }
        });

        loadFragment(new HistoryFragment(), "history");
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

    }

    private void initView(){
        toolbar = findViewById(R.id.toolbar);
        fabAdd = findViewById(R.id.fabAdd);
        bottomNavigationView = findViewById(R.id.bottomNav);
    }


    private boolean loadFragment(Fragment fragment, String tag){
        if (fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_main, fragment, tag)
                    .commit();

            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        String tag = "";

        switch (item.getItemId()){
            case R.id.riwayat:
                fragment = new HistoryFragment();
                tag = "history";
                break;
            case R.id.laporan:
                fragment = new StatisticsFragment();
                tag = "statistics";
                break;
        }

        return loadFragment(fragment, tag);
    }
}
