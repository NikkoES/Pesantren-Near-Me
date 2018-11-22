package com.pesantren.boardingschool.activity.kategori;

import android.Manifest;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.widget.Toast;

import com.pesantren.boardingschool.R;
import com.pesantren.boardingschool.adapter.PesantrenAdapter;
import com.pesantren.boardingschool.api.BaseApiService;
import com.pesantren.boardingschool.api.UtilsApi;
import com.pesantren.boardingschool.model.data.Pesantren;
import com.pesantren.boardingschool.model.response.ResponsePesantren;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    BaseApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi_pesantren);

        pref = getSharedPreferences("setting", Activity.MODE_PRIVATE);
        apiService = UtilsApi.getAPIService();

        ButterKnife.bind(this);
        initToolbar();
        initSearchView();
        initLocation();
        initRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initPesantrenData();
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
        apiService.getAllPesantren().enqueue(new Callback<ResponsePesantren>() {
            @Override
            public void onResponse(Call<ResponsePesantren> call, Response<ResponsePesantren> response) {
                if (response.isSuccessful()) {
                    listPesantren = response.body().getPondokPesantren();

                    for (int i = 0; i < listPesantren.size(); i++) {
                        final Pesantren pesantren = listPesantren.get(i);

                        Location pesantrenLocation = new Location("");
                        pesantrenLocation.setLatitude(Double.parseDouble(pesantren.getLat()));
                        pesantrenLocation.setLongitude(Double.parseDouble(pesantren.getLng()));

                        float distance = myLocation.distanceTo(pesantrenLocation);
                        float radius = pref.getInt("radius", 10000);

                        if (listPesantren.get(i).getKategori().equalsIgnoreCase(getIntent().getStringExtra("aliran")) && distance < radius) {
                            listAliranPesantren.add(listPesantren.get(i));
                        }
                    }

                    rvPesantren.setAdapter(new PesantrenAdapter(LokasiPesantrenActivity.this, listAliranPesantren, myLocation));
                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(LokasiPesantrenActivity.this, "Data menu tidak ditemukan !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePesantren> call, Throwable t) {
                Toast.makeText(LokasiPesantrenActivity.this, "Failed to Connect Internet !", Toast.LENGTH_SHORT).show();
            }
        });

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
