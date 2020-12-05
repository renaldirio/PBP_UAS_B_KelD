package com.filbertfilbert.uts.adapter;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.filbertfilbert.uts.R;
import com.filbertfilbert.uts.model.WahanaModel;

import java.util.List;

public class WahanaAdapter extends RecyclerView.Adapter<WahanaAdapter.ViewHolder> {

    List<WahanaModel> Model;
    Context context;
    ProgressDialog dialog;
    Service mApiService;
    public WahanaAdapter(List<WahanaModel> kelList, Context context){
        this.Model = kelList;
        this.context = context;
    }
    @Override
    public WahanaAdapter.ViewHolder onCreateViewHolder(
            ViewGroup viewGroup,
            int i) {
        View itemview = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_wahana,viewGroup,false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(WahanaAdapter.ViewHolder holder,int posisi) {
        final WahanaModel model = Model.get(posisi);

//        Picasso.get()
//                .load(model.getFoto())
//                .placeholder(R.drawable.ic_baseline_crop_original_24)
//                .error(R.drawable.ic_baseline_coronavirus_24)
//                .fit()
//                .into(holder.gambar);

        holder.nama.setText(model.getNamaWahana());
        holder.alamat.setText(model.getLokasi());
        holder.rating.setText(model.getRating());
    }

    @Override
    public int getItemCount() {
        return Model.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nama,alamat,rating;
        CardView parent;
        ImageView gambar;

        public ViewHolder(View view){
            super(view);

            nama = itemView.findViewById(R.id.txtnamaWahana);
            alamat = itemView.findViewById(R.id.txtalamatWahana);
            rating = itemView.findViewById(R.id.txtratingWahana);
            gambar = itemView.findViewById(R.id.ivFotoProfil);

        }
    }

}
