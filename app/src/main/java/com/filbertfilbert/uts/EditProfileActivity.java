package com.filbertfilbert.uts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class EditProfileActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    FirebaseAuth fauth;
    FirebaseFirestore fstore;
    FirebaseUser fuser;
    Button btnSave;
    TextInputEditText txtNamaUser,txtAlamatUser,txtNomorTelpUser;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        fauth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        fuser = fauth.getCurrentUser();
        btnSave = findViewById(R.id.btn_saveProfile);
        txtNamaUser = findViewById(R.id.input_nama_user);
        txtAlamatUser = findViewById(R.id.input_alamat_user);
        txtNomorTelpUser = findViewById(R.id.input_nomortelp_user);
        userID = fauth.getCurrentUser().getUid();

        DocumentReference documentReference = fstore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e!=null){
                    Log.d("TAG","Error:"+e.getMessage());
                }else{
                    txtNamaUser.setText(documentSnapshot.getString("Nama"));
                    txtAlamatUser.setText(documentSnapshot.getString("Alamat"));
                    txtNomorTelpUser.setText(documentSnapshot.getString("Nomor Telefon"));
                }

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Mengecek apakah inputan kosong atau tidak
                if(txtNamaUser.getText().toString().isEmpty() || txtAlamatUser.getText().toString().isEmpty()
                   || txtNomorTelpUser.getText().toString().isEmpty()){
                    Toast.makeText(EditProfileActivity.this, "All input cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Mengupload data yang telah diinputkan ke firebase firestore
                //dengan users sebagai nama foldernya dan nama file yang berisikan data user
                //adalah userID atau UID
                DocumentReference documentReference = fstore.collection("users").document(userID);
                Map<String,Object> user = new HashMap<>();
                user.put("Nama",txtNamaUser.getText().toString());
                user.put("Alamat",txtAlamatUser.getText().toString());
                user.put("Nomor Telefon",txtNomorTelpUser.getText().toString());
                documentReference.update(user);
                Toast.makeText(EditProfileActivity.this, "Profile is Changed", Toast.LENGTH_SHORT).show();
                Intent profileIntent = new Intent(EditProfileActivity.this,ProfileActivity.class);
                startActivity(profileIntent);
                finish();
            }
        });
    }
}