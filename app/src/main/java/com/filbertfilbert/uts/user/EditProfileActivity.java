package com.filbertfilbert.uts.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.filbertfilbert.uts.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class EditProfileActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    ImageView ivFotoProfil;
    FirebaseAuth fauth;
    FirebaseFirestore fstore;
    FirebaseUser fuser;
    Button btnSave;
    TextInputEditText txtNamaUser,txtAlamatUser,txtNomorTelpUser;
    String userID;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ivFotoProfil = findViewById(R.id.ivFotoProfil);
        fauth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        fuser = fauth.getCurrentUser();
        btnSave = findViewById(R.id.btn_saveProfile);
        txtNamaUser = findViewById(R.id.input_nama_user);
        txtAlamatUser = findViewById(R.id.input_alamat_user);
        txtNomorTelpUser = findViewById(R.id.input_nomortelp_user);
        userID = fauth.getCurrentUser().getUid();

        //Inisialisasi Layanan Firebase
        fauth = FirebaseAuth.getInstance();
        fstore= FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        //Mengambil UID user sebagai acuan untuk nama file, untuk upload dan download
        userID=fauth.getCurrentUser().getUid();

        //Menggunakan UID user untuk pencarian file,
        // fungsi ini untuk load foto dari firebase menggunakan Picasso dan firebase storage
        StorageReference profileRef = storageReference.child("users/"+fauth.getCurrentUser().getUid()+"profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(ivFotoProfil);
            }
        });

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
                Intent profileIntent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                startActivity(profileIntent);
                finish();
            }
        });
    }
}