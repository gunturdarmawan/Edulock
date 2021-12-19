package com.example.edulockapp.ui.auth;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.edulockapp.foregroundservice.MainActivity;
import com.example.edulockapp.R;
import com.example.edulockapp.ui.registerkid.RegisterKid;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    EditText inputEmail, inputPassword;
    Button btnDaftar;
    FirebaseAuth mAuth;
    String email, password;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        TextView textView = findViewById(R.id.textLinkToLogin);
        String text = "Sudah punya akun? Login";
        SpannableString ss = new SpannableString(text);
        ForegroundColorSpan fcsBlue = new ForegroundColorSpan(Color.BLUE);

        ss.setSpan(fcsBlue, 18, 23, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });

        inputEmail = findViewById(R.id.textEmailRegist);
        inputPassword = findViewById(R.id.textPasswordRegist);
        btnDaftar = findViewById(R.id.buttonNextRegister);
        mAuth = FirebaseAuth.getInstance();

        TextInputLayout layoutEmail = findViewById(R.id.textEmailRegister);
        TextInputEditText eTextEmail= findViewById(R.id.textEmailRegist);
        TextInputLayout layoutPassword = findViewById(R.id.textPasswordRegister);
        TextInputEditText eTextPassword = findViewById(R.id.textPasswordRegist);

        email = inputEmail.getText().toString().trim();
        password = inputPassword.getText().toString().trim();

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = inputEmail.getText().toString().trim();
                password = inputPassword.getText().toString().trim();

                if(email.isEmpty()){
                    layoutEmail.setError("Email Tidak Boleh kosong");
                    eTextEmail.requestFocus();
                    return;
                }

                if(password.length() >= 6){
                    layoutPassword.setHelperText("Panjang karakter baik");
                    eTextPassword.requestFocus();
                    registrasi();
                    return;
                }

                if(password.isEmpty()) {
                    layoutPassword.setError("Password Tidak Boleh Kosong");
                    eTextPassword.requestFocus();
                    return;
                }

                if (password.length() <=6) {
                    layoutPassword.setError("Minimum 6 Karakter");
                    eTextPassword.requestFocus();
                    return;
                }
                registrasi();
                resetDataAnak();
            }
        });


        eTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = s.toString().trim();
                if(email.isEmpty()){
                    layoutEmail.setError("Email tidak boleh kosong");
                } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    layoutEmail.setError("Email tidak Valid");
                } else {
                    layoutEmail.setHelperText(" ");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        eTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String password = s.toString().trim();
                    if(password.length() >= 6){
                        layoutPassword.setHelperText("Panjang karakter baik");
                    } else if(password.isEmpty()) {
                        layoutPassword.setError("Password Tidak Boleh Kosong");
                    } else {
                        layoutPassword.setError("Minimum 6 Karakter");
                    }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(Register.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void registrasi() {
        email= inputEmail.getText().toString();
        password = inputPassword.getText().toString();

        TextInputLayout layoutEmail = findViewById(R.id.textEmailRegister);
        TextInputEditText eTextEmail= findViewById(R.id.textEmailRegist);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(Register.this, "Registrasi Sukses", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Register.this, RegisterKid.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Register.this, "Registrasi Gagal", Toast.LENGTH_SHORT).show();
                            layoutEmail.setError("Email Telah Terdaftar");
                            eTextEmail.requestFocus();
                        }
                    }
                });
    }

    private void resetDataAnak(){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isIntroOpened", false);
        editor.apply();
    }


}