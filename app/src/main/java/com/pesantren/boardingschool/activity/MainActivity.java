package com.pesantren.boardingschool.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.pesantren.boardingschool.R;
import com.pesantren.boardingschool.activity.about.AboutActivity;
import com.pesantren.boardingschool.activity.berita.BeritaActivity;
import com.pesantren.boardingschool.activity.kategori.AliranPesantrenActivity;
import com.pesantren.boardingschool.activity.maps.MapsActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_peta, R.id.btn_kategori, R.id.btn_berita, R.id.btn_tentang})
    public void actionButton(View v) {
        switch (v.getId()) {
            case R.id.btn_peta:
                startActivity(new Intent(this, MapsActivity.class));
                break;
            case R.id.btn_kategori:
                startActivity(new Intent(this, AliranPesantrenActivity.class));
                break;
            case R.id.btn_berita:
                startActivity(new Intent(this, BeritaActivity.class));
                break;
            case R.id.btn_tentang:
                startActivity(new Intent(this, AboutActivity.class));
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
        switch (item.getItemId()) {
            case R.id.nav_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
        }
        return true;
    }
}
