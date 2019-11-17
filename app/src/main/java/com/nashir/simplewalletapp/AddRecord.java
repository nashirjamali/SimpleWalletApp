package com.nashir.simplewalletapp;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class AddRecord extends AppCompatActivity {

    Toolbar toolbar;
    TextView txtDateTime, txtSave;
    EditText etDesc, etNominal;
    RadioGroup rgType;
    DatabaseHelper db;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        db = new DatabaseHelper(this);
        initView();
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_close_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getToday();

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });

    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        txtDateTime = findViewById(R.id.txtDateTime);
        txtSave = findViewById(R.id.txtSimpan);
        etDesc = findViewById(R.id.etUraian);
        etNominal = findViewById(R.id.etNominal);
        rgType = findViewById(R.id.rgTipe);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getToday() {
        String[] strMonths = new String[]{
                "Januari",
                "Februari",
                "Maret",
                "April",
                "Mei",
                "Juni",
                "Juli",
                "Agustus",
                "September",
                "oktober",
                "November",
                "Desember"
        };

        Calendar now = Calendar.getInstance();
        String date = now.get(Calendar.DATE)
                + " "
                + strMonths[now.get(Calendar.MONTH)]
                + " "
                + now.get(Calendar.YEAR);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        String time = String.valueOf(LocalTime.now(ZoneId.of("Asia/Jakarta")).format(dtf));

        txtDateTime.setText(date + ", " + time);
    }

    private void insertData() {
        int selectedId = rgType.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedId);
        String type = radioButton.getText().toString();

        boolean isInserted = db.insertData(etDesc.getText().toString(), etNominal.getText().toString(), type);
        if (isInserted) {
            Toast.makeText(AddRecord.this, "Data Ditambahkan", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(AddRecord.this, "Data Gagal Ditambahkan", Toast.LENGTH_LONG).show();
        }
    }
}
