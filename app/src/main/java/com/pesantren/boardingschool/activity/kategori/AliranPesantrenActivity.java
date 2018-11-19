package com.pesantren.boardingschool.activity.kategori;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;

import com.pesantren.boardingschool.R;
import com.pesantren.boardingschool.adapter.AliranAdapter;
import com.pesantren.boardingschool.model.data.Aliran;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AliranPesantrenActivity extends AppCompatActivity  {

    @BindView(R.id.rv_aliran)
    RecyclerView rvAliran;
    @BindView(R.id.search)
    SearchView searchView;

    private AliranAdapter mAdapter;
    List<Aliran> listAliran = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aliran_pesantren);
        ButterKnife.bind(this);
        initToolbar();
        initSearchView();
        initAliranData();
        initRecyclerView();
    }

    private void initSearchView() {
        searchView.setQueryHint("Pencarian Pesantren");
        searchView.setIconifiedByDefault(false);
    }

    private void initToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Kategori Pesantren");
    }

    private void initAliranData() {
        listAliran.add(new Aliran("1","NU"));
        listAliran.add(new Aliran("9","Muhammadiyah"));
        listAliran.add(new Aliran("2","Persis"));
    }

    private void initRecyclerView() {
        rvAliran.setHasFixedSize(true);
        rvAliran.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AliranAdapter(this, listAliran);
        rvAliran.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home : {
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
