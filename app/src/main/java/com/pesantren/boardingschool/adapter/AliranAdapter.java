package com.pesantren.boardingschool.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pesantren.boardingschool.R;
import com.pesantren.boardingschool.activity.LokasiPesantrenActivity;

import java.lang.reflect.Member;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AliranAdapter extends RecyclerView.Adapter<AliranAdapter.ViewHolder>{

    private Context context;
    private List<String> listAliran;

    public AliranAdapter(Context context, List<String> listAliran){
        this.context = context;
        this.listAliran = listAliran;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_aliran, null, false);

        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(layoutParams);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final String aliran = listAliran.get(position);
        holder.txtAliran.setText(aliran);
        holder.cvAliran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, LokasiPesantrenActivity.class);
                i.putExtra("aliran", aliran);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listAliran.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.txt_aliran)
        TextView txtAliran;
        @BindView(R.id.cv_aliran)
        CardView cvAliran;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
