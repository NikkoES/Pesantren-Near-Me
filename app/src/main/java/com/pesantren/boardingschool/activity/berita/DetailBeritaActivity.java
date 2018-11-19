package com.pesantren.boardingschool.activity.berita;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.pesantren.boardingschool.R;
import com.pesantren.boardingschool.model.data.Berita;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailBeritaActivity extends AppCompatActivity {

    @BindView(R.id.image_berita)
    ImageView imageBerita;
    @BindView(R.id.txt_judul_berita)
    TextView txtJudul;
    @BindView(R.id.txt_konten_berita)
    TextView txtKonten;
    @BindView(R.id.txt_tanggal_berita)
    TextView txtTanggal;

    Berita berita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_berita);
        ButterKnife.bind(this);

        berita = (Berita) getIntent().getSerializableExtra("berita");

        initToolbar();
        initUI();
    }

    private void initToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Berita");
        getSupportActionBar().setSubtitle(berita.getJudul());
    }

    private void initUI() {
        //TODO image belum di set pake glide
        txtJudul.setText(berita.getJudul());
        txtKonten.setText(berita.getKonten());
        txtTanggal.setText(berita.getTanggal());
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
