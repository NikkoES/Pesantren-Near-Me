package com.pesantren.boardingschool.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pesantren.boardingschool.R;
import com.pesantren.boardingschool.activity.maps.DetailPesantrenActivity;
import com.pesantren.boardingschool.model.data.Pesantren;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PesantrenAdapter extends RecyclerView.Adapter<PesantrenAdapter.ViewHolder> {

    private Context context;
    private List<Pesantren> listPesantren;
    private Location myLocation;

    SharedPreferences pref;

    public PesantrenAdapter(Context context, List<Pesantren> listPesantren, Location myLocation) {
        this.context = context;
        this.listPesantren = listPesantren;
        this.myLocation = myLocation;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_lokasi, null, false);

        pref = context.getSharedPreferences("setting", Activity.MODE_PRIVATE);

        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(layoutParams);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Pesantren pesantren = listPesantren.get(position);
        Glide.with(context).load(pesantren.getGambar()).into(holder.imagePesantren);
        holder.txtNamaPesantren.setText(pesantren.getNama());

        Location pesantrenLocation = new Location("");
        pesantrenLocation.setLatitude(Double.parseDouble(pesantren.getLat()));
        pesantrenLocation.setLongitude(Double.parseDouble(pesantren.getLng()));

        float distanceHarversine = convertTwo(calculateHarversine(myLocation.getLatitude(), myLocation.getLongitude(),
                Double.parseDouble(pesantren.getLat()), Double.parseDouble(pesantren.getLng())));
        float distanceEuclidean = convertTwo(myLocation.distanceTo(pesantrenLocation) / 1000);

        if (pref.getBoolean("distance", true)) {
            holder.txtJarakPesantren.setText(distanceEuclidean + " km | " + distanceHarversine + " km");
        } else {
            holder.txtJarakPesantren.setText(convertTwo((float) (distanceEuclidean * 0.621371)) + " mil | " + convertTwo((float) (distanceHarversine * 0.621371)) + " mil");
        }
        holder.cvPesantren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailPesantrenActivity.class);
                i.putExtra("pesantren", pesantren);
                i.putExtra("my_location", myLocation);
                context.startActivity(i);
            }
        });
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

    @Override
    public int getItemCount() {
        return listPesantren.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_pesantren)
        ImageView imagePesantren;
        @BindView(R.id.txt_nama_pesantren)
        TextView txtNamaPesantren;
        @BindView(R.id.txt_jarak_pesantren)
        TextView txtJarakPesantren;
        @BindView(R.id.cv_pesantren)
        CardView cvPesantren;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
