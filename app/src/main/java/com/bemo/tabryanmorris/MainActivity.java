package com.bemo.tabryanmorris;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText nama,jumlah,jenis;
    Button simpan,tampil,hapus,edit;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nama = findViewById(R.id.edtnama);
        jumlah = findViewById(R.id.jumlah);
        jenis = findViewById(R.id.jenis);
        simpan = findViewById(R.id.btnsimpan);
        tampil = findViewById(R.id.btntampil);
        hapus = findViewById(R.id.btnhapus);
        edit = findViewById(R.id.btnedit);
        db = new DBHelper(this);


        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String isinama = nama.getText().toString();
                String isijumlah = jumlah.getText().toString();
                String isijenis = jenis.getText().toString();

                if (TextUtils.isEmpty(isinama) || TextUtils.isEmpty(isinama) || TextUtils.isEmpty(isijumlah) || TextUtils.isEmpty(isijenis)){
                    Toast.makeText(MainActivity.this, "Semua Field Wajib diIsi", Toast.LENGTH_LONG).show();
                } else {
                    Boolean checkkode = db.checknama(isinama);
                    if (checkkode == false) {
                        Boolean insert = db.insertbarang(isinama, isijumlah, isijenis);
                        if (insert == true) {
                            Toast.makeText(MainActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                            //  Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            // startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Data gagal disimpan", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(MainActivity.this, "Data Mahasiswa Sudah Ada", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kb = nama.getText().toString();
                Boolean cekHapusData = db.hapusbarang(kb);
                if (cekHapusData == true)
                    Toast.makeText(MainActivity.this, "Data Terhapus", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
            }
        });

        tampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = db.tampilbarang();
                if (res.getCount()==0){
                    Toast.makeText(MainActivity.this, "Tidak ada Data", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("NIM Mahasiswa : "+res.getString(0)+"\n");
                    buffer.append("Nama Mahasiswa : "+res.getString(1)+"\n");
                    buffer.append("Jenis Kelamin : "+res.getString(2)+"\n");
                    buffer.append("Alamat : "+res.getString(3)+"\n");
                    buffer.append("Email : "+res.getString(4)+"\n\n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Biodata Mahasiswa");
                builder.setMessage(buffer.toString());
                builder.show();
            };
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String isinama = nama.getText().toString();
                String isijumlah = jumlah.getText().toString();
                String isijenis = jenis.getText().toString();

                if (TextUtils.isEmpty(isinama) || TextUtils.isEmpty(isijumlah) || TextUtils.isEmpty(isijenis)) {
                    Toast.makeText(MainActivity.this, "Semua Field Wajib diisi", Toast.LENGTH_SHORT).show();
                }else{
                    Boolean edit = db.editbarang(isinama, isijumlah, isijenis);
                    if (edit == true) {
                        Toast.makeText(MainActivity.this, "Data Berhasil Di Edit", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Data Gagal Di Edit", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

}