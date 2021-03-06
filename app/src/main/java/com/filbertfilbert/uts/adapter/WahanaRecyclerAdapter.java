package com.filbertfilbert.uts.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.filbertfilbert.uts.DAO.WahanaDAO;
import com.filbertfilbert.uts.R;
import com.filbertfilbert.uts.fragment.WahanaFragment;

import java.util.List;
import java.util.stream.Collectors;

public class WahanaRecyclerAdapter extends RecyclerView.Adapter <WahanaRecyclerAdapter.RoomViewHolder> {

    private List<WahanaDAO> dataList;
   // private List<WahanaDAO> filteredDataList;
    private Context context;
    public String isAdmin;
    public WahanaRecyclerAdapter(List<WahanaDAO> dataList, Context context) {
        this.dataList = dataList;
     //   this.filteredDataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public WahanaRecyclerAdapter.RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_wahana_adapter, parent, false);
        return new WahanaRecyclerAdapter.RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WahanaRecyclerAdapter.RoomViewHolder holder, int position) {
        final WahanaDAO whn = dataList.get(position);
        holder.twNamaWahana.setText(whn.getNama_wahana());
        //holder.twLokasi.setText(whn.getLokasi());
//        System.out.println(whn.getLokasi());
//        holder.twRating.setText(whn.getRating());
//        holder.twDeskripsi.setText(whn.getDeskripsi());
//            Glide.with(twFoto.getContext())
//                    .load("https://uaspbp.000webhostapp.com/public_html"+sFoto)
//                    .apply(new RequestOptions().centerCrop())
//                    .into(twFoto);
//            progressDialog.dismiss();

        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                WahanaFragment dialog = new WahanaFragment();
                dialog.show(manager, "dialog");

                Bundle args = new Bundle();
                args.putInt("id", whn.getId());
                args.putString("isAdmin",isAdmin);
                dialog.setArguments(args);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class RoomViewHolder extends RecyclerView.ViewHolder {
        private TextView twNamaWahana, twLokasi, twRating, twDeskripsi;
        private ImageView twFoto;
        private LinearLayout mParent;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            twNamaWahana = itemView.findViewById(R.id.twNama_wahana);
//            twLokasi = itemView.findViewById(R.id.twLokasi);
//            twRating = itemView.findViewById(R.id.twRating);
//            twDeskripsi = itemView.findViewById(R.id.twDeskripsi);
//            twFoto = itemView.findViewById(R.id.twFoto);
            mParent = itemView.findViewById(R.id.linearLayout);
        }
    }

//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(final CharSequence charSequence) {
//
//                filteredDataList = charSequence == null ? dataList :
//                        dataList.stream().filter(data -> data.getNama_wahana().toLowerCase().contains(charSequence.toString().toLowerCase())).collect(Collectors.toList());
//
//                FilterResults results = new FilterResults();
//                results.values = filteredDataList;
//                return results;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                filteredDataList = (List<WahanaDAO>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
    }

