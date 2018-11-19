package com.pesantren.boardingschool.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pesantren.boardingschool.R;
import com.pesantren.boardingschool.activity.berita.DetailBeritaActivity;
import com.pesantren.boardingschool.model.data.Berita;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BeritaAdapter extends RecyclerView.Adapter<BeritaAdapter.ViewHolder> {

    private Context context;
    private List<Berita> listBerita;

    public BeritaAdapter(Context context, List<Berita> listBerita) {
        this.context = context;
        this.listBerita = listBerita;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_berita, null, false);

        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(layoutParams);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Berita berita = listBerita.get(position);
        holder.txtJudulBerita.setText(berita.getJudul());
        holder.txtTanggalBerita.setText(berita.getTanggal());
        holder.cvBerita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailBeritaActivity.class);
                i.putExtra("berita", berita);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBerita.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_berita)
        ImageView imageBerita;
        @BindView(R.id.txt_judul_berita)
        TextView txtJudulBerita;
        @BindView(R.id.txt_tanggal_berita)
        TextView txtTanggalBerita;
        @BindView(R.id.cv_berita)
        CardView cvBerita;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
