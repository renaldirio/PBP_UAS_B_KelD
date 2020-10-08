package com.filbertfilbert.uts;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class WahanaRecyclerViewAdapter  extends RecyclerView.Adapter<WahanaRecyclerViewAdapter.UserViewHolder>
        implements Filterable {
    private Context context;
    private List<Wahana> wahanaList;
    private List<Wahana> wahanaListFiltered;
    public Boolean isClickable = true;

    public WahanaRecyclerViewAdapter (Context context, List<Wahana> userList) {
        this.context = context;
        this.wahanaList = userList;
        wahanaListFiltered = userList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_wahana, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Wahana wahana = wahanaListFiltered.get(position);
        holder.txtnamaWahana.setText(wahana.getNamaWahana());
        holder.txtalamatWahana.setText(wahana.getAlamatWahana());
        holder.txtratingWahana.setText(wahana.getRatingWahana());
        holder.txtHargaWahana.setText(String.valueOf(wahana.getHargaWahana()));
    }

    @Override
    public int getItemCount() {
        return (wahanaListFiltered != null) ? wahanaListFiltered.size() : 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    wahanaListFiltered = wahanaList;
                } else {
                    List<Wahana> filteredList = new ArrayList<>();
                    for (Wahana wahana : wahanaList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (wahana.getNamaWahana().toLowerCase().contains(charString.toLowerCase()) ) {
                            filteredList.add(wahana);
                        }
                    }

                    wahanaListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = wahanaListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                wahanaListFiltered = (ArrayList<Wahana>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtnamaWahana, txtalamatWahana, txtratingWahana,txtHargaWahana;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            txtnamaWahana = itemView.findViewById(R.id.txtnamaWahana);
            txtalamatWahana = itemView.findViewById(R.id.txtalamatWahana);
            txtratingWahana = itemView.findViewById(R.id.txtratingWahana);
            txtHargaWahana = itemView.findViewById(R.id.hargaWahana);

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v){
            if(isClickable==true){
                AppCompatActivity activity= (AppCompatActivity) v.getContext();
                Wahana wahana = wahanaList.get(getAdapterPosition());
                Bundle data = new Bundle();
                data.putSerializable("pbp",wahana);
                UpdateWahanaFragment updateFragment = new UpdateWahanaFragment();
                updateFragment.setArguments(data);
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.admin_layout,updateFragment)
                        .commit();
            }else{
                Toast.makeText(context, "Yey berhasil", Toast.LENGTH_SHORT).show();
            }

        }
    }

}