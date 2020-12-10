package com.example.rentalsepeda;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.RenderScript;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class Registrasi extends AppCompatActivity {

    TextView txtRegristrasi;
    EditText edEmail;
    EditText edPassword;
    EditText edNama;
    EditText edNoKtp;
    EditText edNoHp;
    EditText edAlamat;
    Button btnDaftar;


    private ProgressDialog progressBar;
    SharedPreferences mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        mLogin = getSharedPreferences("login",MODE_PRIVATE);

        progressBar = new ProgressDialog(this);

        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        edNama = findViewById(R.id.edNama);
        edNoKtp = findViewById(R.id.edNoKtp);
        edNoHp = findViewById(R.id.edNoHp);
        edAlamat = findViewById(R.id.edAlamat);
        btnDaftar = findViewById(R.id.btnDaftar);

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edEmail.getText().toString();
                String password = edPassword.getText().toString();
                String nama = edNama.getText().toString();
                String noktp = edNoKtp.getText().toString();
                String nohp = edNoHp.getText().toString();
                String alamat = edAlamat.getText().toString().trim();
                progressBar.setTitle("Register In...");
                progressBar.show();
                AndroidNetworking.post("http://192.168.6.217/RentalSepeda/Register.php")
                        .addBodyParameter("email", email)
                        .addBodyParameter("password", password)
                        .addBodyParameter("nama", nama)
                        .addBodyParameter("noktp", noktp)
                        .addBodyParameter("nohp", nohp)
                        .addBodyParameter("alamat", alamat)
                        .addBodyParameter("roleuser", "1")
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i("hasil", "onResponse: ");
                                try {
                                    JSONObject hasil = response.getJSONObject("hasil");
                                    String status = hasil.getString("STATUS");
                                    String message = hasil.getString("MESSAGE");
                                    Log.i("STATUS", "onResponse: " + status);
                                    if (status.equals("SUCCESS")) {
                                        mLogin.edit().putBoolean("logged",true).apply();
                                        Intent intent = new Intent(Registrasi.this, Customer.class);
                                        startActivity(intent);
                                        finish();
                                        progressBar.dismiss();
                                    } else {
                                        Toast.makeText(Registrasi.this, message.toString(), Toast.LENGTH_SHORT).show();
                                        progressBar.dismiss();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                progressBar.dismiss();
                                Log.i("daa", "onError: " + anError.getErrorDetail());
                                Log.i("daa", "onError: " + anError.getErrorBody());
                                Log.i("daa", "onError: " + anError.getErrorCode());

                            }
                        });
            }
        });
    }
}