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
        fauth = FirebaseAuth.getInstance();
        fstore= FirebaseFirestore.getInstance();

        if(fauth.getCurrentUser() != null){
            //Jika ada, maka halaman akan langsung berpidah pada MainActivity
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
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

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this, "Email or Password cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.length() < 6){
                    Toast.makeText(RegisterActivity.this, "Password too short", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(RegisterActivity.this, "Email Invalid", Toast.LENGTH_SHORT).show();
                    return;
                }

                fauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();
                            userID = fauth.getCurrentUser().getUid();
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
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        }else {
                            Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}