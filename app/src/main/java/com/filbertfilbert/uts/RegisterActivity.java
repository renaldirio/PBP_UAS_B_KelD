package com.filbertfilbert.uts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    public static final String TAG = "TAG";

    TextInputEditText txtNamaUser,txtAlamatUser,txtNomorTelpUser,txtEmailUser,txtPasswordUser;
    Button btnRegister;
    TextView txtLogin;
    FirebaseAuth fauth;
    FirebaseFirestore fstore;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtEmailUser = findViewById(R.id.input_email_user);
        txtPasswordUser = findViewById(R.id.input_password_user);
        txtNamaUser = findViewById(R.id.input_nama_user);
        txtAlamatUser = findViewById(R.id.input_alamat_user);
        txtNomorTelpUser = findViewById(R.id.input_nomortelp_user);
        btnRegister = findViewById(R.id.btn_register);
        txtLogin = findViewById(R.id.txt_login);

        //Inisialisasi layanan firebase
        fauth = FirebaseAuth.getInstance();
        fstore= FirebaseFirestore.getInstance();

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = txtEmailUser.getText().toString();
                String password = txtPasswordUser.getText().toString();
                final String namaUser = txtNamaUser.getText().toString();
                final String alamatUser = txtAlamatUser.getText().toString();
                final String nomorTelpUser = txtNomorTelpUser .getText().toString();

                //Mengecek apakah inputan kosong atau tidak
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ||
                    TextUtils.isEmpty(namaUser) || TextUtils.isEmpty(alamatUser) ||
                    TextUtils.isEmpty(nomorTelpUser) ) {
                    Toast.makeText(RegisterActivity.this, "All input cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Mengecek apakah inputan password kurang dari 6 karakter
                if(password.length() < 6){
                    Toast.makeText(RegisterActivity.this, "Password too short", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Mengecek apakah inputan email sesuai dengan format yang benar
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(RegisterActivity.this, "Email Invalid", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Membuat akun user menggunakan firebase authentication
                fauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser fuser = fauth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(RegisterActivity.this, "Register Successful and Email is Sent", Toast.LENGTH_SHORT).show();
                                   txtEmailUser.setText("");
                                    txtPasswordUser.setText("");
                                    txtNamaUser.setText("");
                                    txtAlamatUser.setText("");
                                    txtNomorTelpUser.setText("");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RegisterActivity.this, "Email is not Sent", Toast.LENGTH_SHORT).show();
                                }
                            });
                            userID = fauth.getCurrentUser().getUid();
                            //Mengupload data yang telah diinputkan ke firebase firestore
                            //dengan users sebagai nama foldernya dan nama file yang berisikan data user
                            //adalah userID atau UID
                            DocumentReference documentReference = fstore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("Nama",namaUser);
                            user.put("Alamat",alamatUser);
                            user.put("Email",email);
                            user.put("Nomor Telefon",nomorTelpUser);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user Profile is created for "+userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: "+ e.toString());
                                }
                            });
                            Intent loginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                            startActivity(loginIntent);
                        }else {
                            Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}