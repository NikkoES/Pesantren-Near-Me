package com.pesantren.boardingschool.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pesantren.boardingschool.R;
import com.pesantren.boardingschool.model.Pesantren;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailPesantrenActivity extends AppCompatActivity {

    @BindView(R.id.image_pesantren)
    ImageView imagePesantren;
    @BindView(R.id.txt_nama_pesantren)
    TextView txtNamaPesantren;
    @BindView(R.id.txt_alamat)
    TextView txtAlamat;
    @BindView(R.id.txt_aliran)
    TextView txtAliran;

    Pesantren pesantren;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesantren);
        ButterKnife.bind(this);

        pesantren = (Pesantren) getIntent().getSerializableExtra("pesantren");

        initToolbar();
        initUI();
    }

    private void initUI() {
        imagePesantren.setImageResource(R.drawable.ic_apps);
        txtNamaPesantren.setText(pesantren.getNamaPesantren());
        txtAliran.setText("("+pesantren.getAliran()+")");
        txtAlamat.setText(pesantren.getAlamat());
    }

    private void initToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Pesantren");
        getSupportActionBar().setSubtitle(pesantren.getNamaPesantren());
    }

    @OnClick({R.id.btn_navigation, R.id.btn_shareloc})
    public void actionButton(View v){
        switch (v.getId()){
            case R.id.btn_navigation :
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+pesantren.getLatitude()+","+pesantren.getLongitude()+"");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
                break;
            case R.id.btn_shareloc :
                String uri = "http://maps.google.com/maps?saddr=" +pesantren.getLatitude()+","+pesantren.getLongitude();
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String subject = "Berikut adalah lokasi dari "+pesantren.getNamaPesantren();
                sharingIntent.putExtra(Intent.EXTRA_TEXT, subject+"\n"+uri);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                break;
        }
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
