package com.pesantren.boardingschool.activity.berita;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.pesantren.boardingschool.R;
import com.pesantren.boardingschool.activity.kategori.LokasiPesantrenActivity;
import com.pesantren.boardingschool.adapter.BeritaAdapter;
import com.pesantren.boardingschool.adapter.PesantrenAdapter;
import com.pesantren.boardingschool.api.BaseApiService;
import com.pesantren.boardingschool.api.UtilsApi;
import com.pesantren.boardingschool.model.data.Berita;
import com.pesantren.boardingschool.model.data.Pesantren;
import com.pesantren.boardingschool.model.response.ResponseBerita;
import com.pesantren.boardingschool.model.response.ResponsePesantren;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeritaActivity extends AppCompatActivity {

    @BindView(R.id.rv_berita)
    RecyclerView rvPesantren;

    private BeritaAdapter mAdapter;

    List<Berita> listBerita = new ArrayList<>();

    BaseApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita);
        ButterKnife.bind(this);

        apiService = UtilsApi.getAPIService();

        initToolbar();
        initBeritaData();
        initRecyclerView();
    }

    private void initRecyclerView() {
        rvPesantren.setHasFixedSize(true);
        rvPesantren.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BeritaAdapter(this, listBerita);
        rvPesantren.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initBeritaData();
    }

    private void initBeritaData() {
        apiService.getAllBerita().enqueue(new Callback<ResponseBerita>() {
            @Override
            public void onResponse(Call<ResponseBerita> call, Response<ResponseBerita> response) {
                if (response.isSuccessful()) {
                    listBerita = response.body().getListBerita();

                    rvPesantren.setAdapter(new BeritaAdapter(BeritaActivity.this, listBerita));
                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(BeritaActivity.this, "Data menu tidak ditemukan !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBerita> call, Throwable t) {
                Toast.makeText(BeritaActivity.this, "Failed to Connect Internet !", Toast.LENGTH_SHORT).show();
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
