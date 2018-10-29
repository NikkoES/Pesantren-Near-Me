package com.pesantren.boardingschool.activity.maps;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pesantren.boardingschool.R;
import com.pesantren.boardingschool.model.Pesantren;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailPesantrenActivity extends AppCompatActivity implements OnMapReadyCallback {

    @BindView(R.id.txt_alamat)
    TextView txtAlamat;
    @BindView(R.id.txt_kontak)
    TextView txtKontak;
    @BindView(R.id.txt_jarak)
    TextView txtJarak;

    Pesantren pesantren;
    Location myLocation;

    SharedPreferences pref;

    private GoogleMap mMap;
    private UiSettings mUiSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesantren);
        ButterKnife.bind(this);

        pref = getSharedPreferences("setting", Activity.MODE_PRIVATE);

        pesantren = (Pesantren) getIntent().getSerializableExtra("pesantren");
        myLocation = getIntent().getExtras().getParcelable("my_location");

        initMapFragment();
        initToolbar();
        initUI();
    }

    private void initMapFragment() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void initUI() {
        Location pesantrenLocation = new Location("");
        pesantrenLocation.setLatitude(Double.parseDouble(pesantren.getLatitude()));
        pesantrenLocation.setLongitude(Double.parseDouble(pesantren.getLongitude()));

        float distanceHarversine = convertTwo(calculateHarversine(myLocation.getLatitude(), myLocation.getLongitude(),
                Double.parseDouble(pesantren.getLatitude()), Double.parseDouble(pesantren.getLongitude())));
        float distanceEuclidean = convertTwo(myLocation.distanceTo(pesantrenLocation) / 1000);

        txtKontak.setText(pesantren.getTelp());
        if(pref.getBoolean("distance", true)){
            txtJarak.setText(distanceEuclidean + " km | " + distanceHarversine + " km");
        }
        else{
            txtJarak.setText(convertTwo((float) (distanceEuclidean * 0.621371)) + " mil | " + convertTwo((float) (distanceHarversine * 0.621371)) + " mil");
        }
        txtAlamat.setText(pesantren.getAlamat());
    }

    public float convertTwo(float n) {
        return BigDecimal.valueOf(n).setScale(4, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    public float calculateHarversine(double myLat, double myLong, double pesantrenLat, double pesantrenLong) {
        int R = 6371; // km (Earth radius)
        double dLat = toRadians(pesantrenLat - myLat);
        double dLon = toRadians(pesantrenLong - myLong);
        myLat = toRadians(myLat);
        pesantrenLat = toRadians(pesantrenLat);

        float a = (float) (Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(myLat) * Math.cos(pesantrenLat));
        float c = (float) (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)));
        return R * c;
    }

    public double toRadians(double deg) {
        return deg * (Math.PI / 180);
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mUiSettings = mMap.getUiSettings();

        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

        int height = 100;
        int width = 100;

        //icon marker
        BitmapDrawable bitMine = (BitmapDrawable) getResources().getDrawable(R.drawable.marker_mine);
        Bitmap bMine = bitMine.getBitmap();
        Bitmap smallMarkerMine = Bitmap.createScaledBitmap(bMine, width, height, false);

        LatLng lokasiPesantren = new LatLng(Double.parseDouble(pesantren.getLatitude()), Double.parseDouble(pesantren.getLongitude()));
        mMap.addMarker(new MarkerOptions().position(lokasiPesantren).title(pesantren.getNamaPesantren()).snippet("Aliran : " + pesantren.getAliran() + "\n" + pesantren.getAlamat()).icon(BitmapDescriptorFactory.fromBitmap(smallMarkerMine)));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(lokasiPesantren).zoom(15).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
