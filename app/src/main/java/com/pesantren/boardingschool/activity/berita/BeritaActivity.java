package com.pesantren.boardingschool.activity.berita;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.pesantren.boardingschool.R;
import com.pesantren.boardingschool.adapter.BeritaAdapter;
import com.pesantren.boardingschool.model.data.Berita;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BeritaActivity extends AppCompatActivity {

    @BindView(R.id.rv_berita)
    RecyclerView rvPesantren;

    private BeritaAdapter mAdapter;

    List<Berita> listBerita = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita);
        ButterKnife.bind(this);

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

    private void initBeritaData() {
        listBerita.add(new Berita("Judul Berita I", "ini konten berita ke 1", "01 Oktober 2018", ""));
        listBerita.add(new Berita("Judul Berita II", "ini konten berita ke 2", "02 Oktober 2018", ""));
        listBerita.add(new Berita("Judul Berita III", "ini konten berita ke 3", "03 Oktober 2018", ""));
        listBerita.add(new Berita("Judul Berita IV", "ini konten berita ke 4", "04 Oktober 2018", ""));
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
