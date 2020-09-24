package com.filbertfilbert.uts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateWahanaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateWahanaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextInputEditText txtnamaWahana, txtalamatWahana, txtratingWahana,txthargaWahana;
    private Button btnCancel ,btnDelete,btnUpdate;
    Wahana wahana;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpdateWahanaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateWahanaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateWahanaFragment newInstance(String param1, String param2) {
        UpdateWahanaFragment fragment = new UpdateWahanaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_wahana,container,false);
        wahana  = (Wahana) getArguments().getSerializable("pbp");
        txtnamaWahana = view.findViewById(R.id.input_namaWahana);
        txtalamatWahana=view.findViewById(R.id.input_alamatWahana);
        txtratingWahana =view.findViewById(R.id.input_ratingWahana);
        txthargaWahana = view.findViewById(R.id.input_hargaWahana);
        btnUpdate = view.findViewById(R.id.btn_update);
        btnDelete = view.findViewById(R.id.btn_delete);
        btnCancel=view.findViewById(R.id.btn_cancel);

        try{
            if(wahana.getNamaWahana()!=null) {
                txtnamaWahana.setText(wahana.getNamaWahana());
                txtalamatWahana.setText(wahana.getAlamatWahana());
                txtratingWahana.setText(wahana.getRatingWahana());
                txthargaWahana.setText(String.valueOf(wahana.getHargaWahana()));
            }
            else{
                txtnamaWahana.setText("-");
                txtalamatWahana.setText("-");
                txtratingWahana.setText("-");
                txthargaWahana.setText("-");
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return  view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wahana.setNamaWahana(txtnamaWahana.getText().toString());
                wahana.setAlamatWahana(txtalamatWahana.getText().toString());
                wahana.setRatingWahana(txtratingWahana.getText().toString());
                wahana.setHargaWahana(Double.parseDouble(txthargaWahana.getText().toString()));
                update(wahana);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure to delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                delete(wahana);
                            }
                        }).setNegativeButton("Cancel",null)
                        .show();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.hide(UpdateWahanaFragment.this).commit();
            }
        });
    }
    private  void update (final Wahana wahana){
        class UpdateUser extends AsyncTask<Void,Void,Void> {
            @Override
            protected Void  doInBackground(Void... voids){
                DatabaseClient.getInstance(getActivity().getApplicationContext()).getDatabase()
                        .wahanaDao()
                        .update(wahana);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid){
                super.onPostExecute(aVoid);
                Toast.makeText(getActivity().getApplicationContext(),"User Updated",Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.hide(UpdateWahanaFragment.this).commit();
            }
        }
        UpdateUser update = new UpdateUser();
        update.execute();
    }
    private void delete (final Wahana wahana){
        class DeleteUser extends AsyncTask<Void,Void,Void>{
            protected Void  doInBackground(Void... voids){
                DatabaseClient.getInstance(getActivity().getApplicationContext()).getDatabase()
                        .wahanaDao()
                        .delete(wahana);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid){
                super.onPostExecute(aVoid);
                Toast.makeText(getActivity().getApplicationContext(),"User Deleted",Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.hide(UpdateWahanaFragment.this).commit();
            }
        }
        DeleteUser delete = new DeleteUser();
        delete.execute();
    }
}