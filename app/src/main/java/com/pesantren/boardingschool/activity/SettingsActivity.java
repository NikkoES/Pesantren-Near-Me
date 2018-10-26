package com.pesantren.boardingschool.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.pesantren.boardingschool.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.txt_radius)
    TextView txtRadius;
    @BindView(R.id.nb_radius)
    NumberPicker nbRadius;
    @BindView(R.id.layout_nb_radius)
    LinearLayout layoutRadius;
    @BindView(R.id.toggle_distance)
    ToggleButton toggleDistance;

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        initToolbar();

        pref = getSharedPreferences("setting", Activity.MODE_PRIVATE);

        txtRadius.setText("Pilih Radius (m) - "+pref.getInt("radius", 10000));
        toggleDistance.setChecked(pref.getBoolean("distance", true));
        initNumberPicker(new MyCallback() {
            @Override
            public void onCallback(int value) {
                txtRadius.setText("Pilih Radius (m) - "+value);
                setRadiusPref(value);
            }
        });
    }

    public void initNumberPicker(final MyCallback myCallback){
        nbRadius.setMinValue(1);
        nbRadius.setMaxValue(6);
        nbRadius.setDisplayedValues(new String[] {"1000m", "2000m", "5000m", "10000m", "20000m", "50000m"});
        nbRadius.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int pick = 0;
                switch (picker.getValue()){
                    case 1 : {
                        pick = 1000;
                        break;
                    }
                    case 2 : {
                        pick = 2000;
                        break;
                    }
                    case 3 : {
                        pick = 5000;
                        break;
                    }
                    case 4 : {
                        pick = 10000;
                        break;
                    }
                    case 5 : {
                        pick = 20000;
                        break;
                    }
                    case 6 : {
                        pick = 50000;
                        break;
                    }
                }
                myCallback.onCallback(pick);
            }
        });
    }

    @OnClick(R.id.btn_selesai)
    public void finishSetRadius(){
        layoutRadius.setVisibility(View.GONE);
    }

    @OnCheckedChanged(R.id.toggle_distance)
    public void checkToggle(CompoundButton button, boolean checked){
        if(checked){
            setDistancePref(true);
        }
        else {
            setDistancePref(false);
        }
    }

    @OnClick(R.id.btn_set_radius)
    public void setRadius(){
        layoutRadius.setVisibility(View.VISIBLE);
    }

    public interface MyCallback {
        void onCallback(int value);
    }

    public void setDistancePref(boolean distance){
        SharedPreferences.Editor editor = getSharedPreferences("setting", MODE_PRIVATE).edit();
        editor.putBoolean("distance", distance);
        editor.apply();
    }

    public void setRadiusPref(int radius){
        SharedPreferences.Editor editor = getSharedPreferences("setting", MODE_PRIVATE).edit();
        editor.putInt("radius", radius);
        editor.apply();
    }

    private void initToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Pengaturan");
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
