package com.pesantren.boardingschool.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.pesantren.boardingschool.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.internal.Utils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_peta, R.id.btn_kategori, R.id.btn_bantuan, R.id.btn_tentang})
    public void actionButton(View v){
        switch (v.getId()){
            case R.id.btn_peta :
                startActivity(new Intent(this, MapsActivity.class));
                break;
            case R.id.btn_kategori :
                startActivity(new Intent(this, AliranPesantrenActivity.class));
                break;
            case R.id.btn_bantuan :
                Toast.makeText(this, "On Development", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_tentang :
                Toast.makeText(this, "On Development", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final Intent i = new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        switch(item.getItemId()){
            case R.id.nav_km :
                setSatuanPref("km");
                startActivity(i);
                break;
            case R.id.nav_mile :
                setSatuanPref("mile");
                startActivity(i);
                break;
            case R.id.nav_radius :
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Masukkan Radius (KM) :");

                final EditText etRadius = new EditText(MainActivity.this);
                builder.setView(etRadius);

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("SET", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!TextUtils.isEmpty(etRadius.getText().toString())){
                            setRadiusPref(""+Integer.parseInt(etRadius.getText().toString()) * 1000);
                            startActivity(i);
                        }
                    }
                });
                builder.show();
                break;
        }
        return true;
    }

    public void setSatuanPref(String satuan){
        SharedPreferences.Editor editor = getSharedPreferences("setting", MODE_PRIVATE).edit();
        editor.putString("satuan", satuan);
        editor.apply();
    }

    public void setRadiusPref(String radius){
        SharedPreferences.Editor editor = getSharedPreferences("setting", MODE_PRIVATE).edit();
        editor.putString("radius", radius);
        editor.apply();
    }


}
