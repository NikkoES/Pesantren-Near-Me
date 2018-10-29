package com.pesantren.boardingschool.activity.kategori;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;

import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.pesantren.boardingschool.R;
import com.pesantren.boardingschool.adapter.AliranAdapter;
import com.pesantren.boardingschool.adapter.PesantrenAdapter;
import com.pesantren.boardingschool.model.Pesantren;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LokasiPesantrenActivity extends AppCompatActivity {

    @BindView(R.id.rv_lokasi)
    RecyclerView rvPesantren;
    @BindView(R.id.search)
    SearchView searchView;

    private PesantrenAdapter mAdapter;
    List<Pesantren> listPesantren = new ArrayList<>();
    List<Pesantren> listAliranPesantren = new ArrayList<>();

    Location myLocation;
    LocationManager locationManager;
    LocationListener listener;

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi_pesantren);

        pref = getSharedPreferences("setting", Activity.MODE_PRIVATE);

        ButterKnife.bind(this);
        initToolbar();
        initSearchView();
        initLocation();
        initPesantrenData();
        initRecyclerView();
    }

    private void initSearchView() {
        searchView.setQueryHint("Pencarian Pesantren");
        searchView.setIconifiedByDefault(false);
    }

    private void initLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        myLocation = new Location("");
        myLocation.setLatitude(location.getLatitude());
        myLocation.setLongitude(location.getLongitude());
    }

    private void initRecyclerView() {
        rvPesantren.setHasFixedSize(true);
        rvPesantren.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PesantrenAdapter(this, listAliranPesantren, myLocation);
        rvPesantren.setAdapter(mAdapter);
    }

    private void initPesantrenData() {
        listPesantren.add(new Pesantren("Mahad Universal", "NU", "Cipadung, Bandung", "08226227436", "-6.92746", "107.71706", ""));
        listPesantren.add(new Pesantren("Pesantren Al-Ihsan", "NU", "Cinunuk, Bandung", "08988190546", "-6.93760", "107.72246", ""));
        listPesantren.add(new Pesantren("Pesantren Persis I", "Persis", "Ujung Berung, Bandung", "08965552374", "-6.93972", "107.71205", ""));
        listPesantren.add(new Pesantren("Pesantren Persis II", "Persis", "Cilengkrang, Bandung", "0857826893", "-6.92775", "107.73265", ""));
        listPesantren.add(new Pesantren("Mahad Al-Jamiah", "Muhammadiyah", "Cipadung, Bandung", "0899471774", "-6.92937", "107.71878", ""));
        listPesantren.add(new Pesantren("Pesanten Al-Hidayah", "NU", "Manisi, Bandung", "-6.92707", "08787765473", "107.72376", ""));

        for (int i = 0; i < listPesantren.size(); i++) {
            final Pesantren pesantren = listPesantren.get(i);

            Location pesantrenLocation = new Location("");
            pesantrenLocation.setLatitude(Double.parseDouble(pesantren.getLatitude()));
            pesantrenLocation.setLongitude(Double.parseDouble(pesantren.getLongitude()));

            float distance = myLocation.distanceTo(pesantrenLocation);
            float radius = pref.getInt("radius", 10000);

            if (listPesantren.get(i).getAliran().equalsIgnoreCase(getIntent().getStringExtra("aliran")) && distance < radius) {
                listAliranPesantren.add(listPesantren.get(i));
            }
        }
    }

    private void initToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Daftar Pesantren");
        getSupportActionBar().setSubtitle(getIntent().getStringExtra("aliran"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
