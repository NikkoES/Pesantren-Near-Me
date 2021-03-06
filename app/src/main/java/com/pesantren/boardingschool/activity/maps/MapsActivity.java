package com.pesantren.boardingschool.activity.maps;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.pesantren.boardingschool.activity.kategori.LokasiPesantrenActivity;
import com.pesantren.boardingschool.adapter.PesantrenAdapter;
import com.pesantren.boardingschool.api.BaseApiService;
import com.pesantren.boardingschool.api.UtilsApi;
import com.pesantren.boardingschool.model.data.Pesantren;
import com.pesantren.boardingschool.model.response.ResponsePesantren;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    final static int PERMISSION_ALL = 1;
    final static String[] PERMISSIONS = {android.Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};

    LocationManager locationManager;

    private GoogleMap mMap;

    private UiSettings mUiSettings;

    BaseApiService apiService;

    List<Pesantren> listPesantren = new ArrayList<>();

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        pref = getSharedPreferences("setting", Activity.MODE_PRIVATE);

        apiService = UtilsApi.getAPIService();

        ButterKnife.bind(this);
        initToolbar();
        initPesantrenData();
        initMapFragment();
    }

    private void initPesantrenData() {

    }

    private void initMapFragment() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT >= 23 && !isPermissionGranted())
            requestPermissions(PERMISSIONS, PERMISSION_ALL);
        else
            requestLocation();
        if (!isLocationEnabled())
            showAlert(1);
    }

    private void initToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lokasi Pesantren");
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

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mUiSettings = mMap.getUiSettings();

        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(true);
//        mUiSettings.setMapToolbarEnabled();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

        int height = 100;
        int width = 100;

        //icon marker
        BitmapDrawable bitNu = (BitmapDrawable) getResources().getDrawable(R.drawable.marker_nu);
        BitmapDrawable bitPersis = (BitmapDrawable) getResources().getDrawable(R.drawable.marker_persis);
        BitmapDrawable bitMu = (BitmapDrawable) getResources().getDrawable(R.drawable.marker_muhammadiyah);
        BitmapDrawable bitMine = (BitmapDrawable) getResources().getDrawable(R.drawable.marker_mine);
        Bitmap bNU = bitNu.getBitmap();
        Bitmap bPersis = bitPersis.getBitmap();
        Bitmap bMuhammadiyah = bitMu.getBitmap();
        Bitmap bMine = bitMine.getBitmap();
        final Bitmap smallMarkerNu = Bitmap.createScaledBitmap(bNU, width, height, false);
        final Bitmap smallMarkerPersis = Bitmap.createScaledBitmap(bPersis, width, height, false);
        final Bitmap smallMarkerMuhammadiyah = Bitmap.createScaledBitmap(bMuhammadiyah, width, height, false);
        Bitmap smallMarkerMine = Bitmap.createScaledBitmap(bMine, width, height, false);

        //init my location
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        final Location myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        LatLng lokasiMember = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
//        mo = new MarkerOptions().position(lokasiMember).title("Lokasi saya").icon(BitmapDescriptorFactory.fromBitmap(smallMarkerMine));
//        marker = mMap.addMarker(mo);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(lokasiMember).zoom(15).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

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

                        if (distance < radius) {
                            LatLng lokasiPesantren = new LatLng(Double.parseDouble(pesantren.getLat()), Double.parseDouble(pesantren.getLng()));
                            if (pesantren.getKategori().equalsIgnoreCase("1")) {
                                mMap.addMarker(new MarkerOptions().position(lokasiPesantren).title(pesantren.getNama()).snippet("Aliran : " + pesantren.getKategori() + "\n" + pesantren.getAlamat()).icon(BitmapDescriptorFactory.fromBitmap(smallMarkerNu)));
                            } else if (pesantren.getKategori().equalsIgnoreCase("2")) {
                                mMap.addMarker(new MarkerOptions().position(lokasiPesantren).title(pesantren.getNama()).snippet("Aliran : " + pesantren.getKategori() + "\n" + pesantren.getAlamat()).icon(BitmapDescriptorFactory.fromBitmap(smallMarkerPersis)));
                            } else if (pesantren.getKategori().equalsIgnoreCase("9")) {
                                mMap.addMarker(new MarkerOptions().position(lokasiPesantren).title(pesantren.getNama()).snippet("Aliran : " + pesantren.getKategori() + "\n" + pesantren.getAlamat()).icon(BitmapDescriptorFactory.fromBitmap(smallMarkerMuhammadiyah)));
                            }
                            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                                @Override
                                public View getInfoWindow(Marker arg0) {
                                    return null;
                                }

                                @Override
                                public View getInfoContents(Marker marker) {

                                    LinearLayout info = new LinearLayout(getApplicationContext());
                                    info.setOrientation(LinearLayout.VERTICAL);

                                    TextView title = new TextView(getApplicationContext());
                                    title.setTextColor(Color.BLACK);
                                    title.setGravity(Gravity.CENTER);
                                    title.setTypeface(null, Typeface.BOLD);
                                    title.setText(marker.getTitle());

                                    TextView snippet = new TextView(getApplicationContext());
                                    snippet.setTextColor(Color.GRAY);
                                    snippet.setGravity(Gravity.CENTER);
                                    snippet.setText(marker.getSnippet());

                                    info.addView(title);
                                    info.addView(snippet);

                                    return info;
                                }
                            });
                        }
                    }

                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            for (int i = 0; i < listPesantren.size(); i++) {
                                Pesantren pesantren = listPesantren.get(i);
                                if (marker.getTitle().equalsIgnoreCase(pesantren.getNama())) {
                                    Intent intent = new Intent(MapsActivity.this, DetailPesantrenActivity.class);
                                    intent.putExtra("pesantren", pesantren);
                                    intent.putExtra("my_location", myLocation);
                                    startActivity(intent);
                                    break;
                                }
                            }
                        }
                    });
                } else {
                    Toast.makeText(MapsActivity.this, "Data menu tidak ditemukan !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePesantren> call, Throwable t) {
                Toast.makeText(MapsActivity.this, "Failed to Connect Internet !", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    private void requestLocation() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        String provider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(provider, 10000, 10, this);
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("mylog", "Permission is granted");
                return true;
            } else {
                Log.v("mylog", "Permission not granted");
                return false;
            }
        }
        return false;
    }

    private void showAlert(final int status) {
        String message, title, btnText;
        if (status == 1) {
            message = "Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                    "use this app";
            title = "Enable Location";
            btnText = "Location Settings";
        } else {
            message = "Please allow this app to access location!";
            title = "Permission access";
            btnText = "Grant";
        }
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(false);
        dialog.setTitle(title)
                .setMessage(message)
                .setPositiveButton(btnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        if (status == 1) {
                            finish();
                            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(myIntent);
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(PERMISSIONS, PERMISSION_ALL);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        finish();
                    }
                });
        dialog.show();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
