package com.example.edulockapp.ui.registerkid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.edulockapp.R;
import com.example.edulockapp.ui.lockapp.PatternLockAct;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterKid extends AppCompatActivity {

    EditText inputNama;
    Button btnSimpanDataAnak;
    String nama;


    String[] items =  {"Kelas 1 SD","Kelas 2 SD","Kelas 3 SD","Kelas 4 SD","Kelas 5 SD", "Kelas 6 SD"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_kid);


        inputNama =findViewById(R.id.namaAnakInput);
        btnSimpanDataAnak = findViewById(R.id.buttonSimpanDataAnak);

        TextInputLayout layoutAnak = findViewById(R.id.textNamaAnak);
        TextInputEditText eTextAnak= findViewById(R.id.namaAnakInput);

        autoCompleteTxt = findViewById(R.id.auto_complete_txt);


        btnSimpanDataAnak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = inputNama.getText().toString().trim();

                if(nama.isEmpty()){
                    layoutAnak.setError("Nama Anak Tidak Boleh Kosong");
                    eTextAnak.requestFocus();
                    return;
                } else {
                    layoutAnak.setHelperText(" ");
                }
                simpanDataAnak();
            }
        });

        eTextAnak.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String nama = s.toString().trim();
                if(nama.isEmpty()){
                    layoutAnak.setError("Nama Anak Tidak Boleh Kosong");
                } else {
                    layoutAnak.setHelperText(" ");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,items);
        autoCompleteTxt.setAdapter(adapterItems);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),"Terpilih "+item,Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void simpanDataAnak(){
        Toast.makeText(getApplicationContext(),"Berhasil menyimpan data anak ",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegisterKid.this, PatternLockAct.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        savePrefsData();
    }

    private boolean restorePreData(){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        return preferences.getBoolean("isIntroOpened", true);
    }

    private void savePrefsData(){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isIntroOpened", true);
        editor.apply();
    }
}