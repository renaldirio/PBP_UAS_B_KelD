package com.filbertfilbert.uts;

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

import com.filbertfilbert.uts.database.DatabaseClient;
import com.filbertfilbert.uts.model.Wahana;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextInputEditText txtnamaWahana, txtalamatWahana, txtratingWahana,txthargaWahana;
    private Button btnCancel ,btnAdd;
    Wahana wahana;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addWahana();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.hide(AddFragment.this).commit();
            }

        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.hide(AddFragment.this).commit();
            }
        });
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
        View view = inflater.inflate(R.layout.fragment_add,container,false);
        txtnamaWahana = view.findViewById(R.id.input_namaWahana);
        txtalamatWahana=view.findViewById(R.id.input_alamatWahana);
        txtratingWahana =view.findViewById(R.id.input_ratingWahana);
        txthargaWahana = view.findViewById(R.id.input_hargaWahana);

        btnAdd = view.findViewById(R.id.btn_update);
        btnCancel=view.findViewById(R.id.btn_cancel);
        return view;
    }

    private void addWahana() {
        final String namaWahana = txtnamaWahana.getText().toString();
        final String alamatWahana = txtalamatWahana.getText().toString();
        final String ratingWahana ="Rating : "+ txtratingWahana.getText().toString();
        final Double hargaWahana =Double.parseDouble(txthargaWahana.getText().toString());
        class AddUser extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                Wahana wahana = new Wahana();
                wahana.setNamaWahana(namaWahana);
                wahana.setAlamatWahana(alamatWahana);
                wahana.setRatingWahana(ratingWahana);
                wahana.setHargaWahana(hargaWahana);
                DatabaseClient.getInstance(getActivity().getApplicationContext()).getDatabase()
                        .wahanaDao()
                        .insert(wahana);
                return null;
            }

            @Override
            protected void onPostExecute (Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getActivity().getApplicationContext(), "Wahana Ditambah", Toast.LENGTH_LONG).show();
                txtnamaWahana.setText("-");
                txtalamatWahana.setText("-");
                txtratingWahana.setText("-");
                txthargaWahana.setText("-");
            }
        }

        AddUser add = new AddUser();
        add.execute();
    }
}