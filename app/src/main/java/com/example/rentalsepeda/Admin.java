package com.example.rentalsepeda;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class Admin extends AppCompatActivity {

    LinearLayout LnDaftarUser;
    Button logoutadmin;


    SharedPreferences mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mLogin = getSharedPreferences("login",MODE_PRIVATE);
        mLogin.edit().putString("logged", mLogin.getString("logged", "missing")).apply();

        LnDaftarUser = findViewById(R.id.LnDaftarUser);
        logoutadmin = findViewById(R.id.btnLogout);
        SharedPreferences mLogin = getSharedPreferences("login", Context.MODE_PRIVATE);

        logoutadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        switch (i) {

                            case DialogInterface.BUTTON_POSITIVE:
                                SharedPreferences mLogin = getSharedPreferences("login", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = mLogin.edit();
                                editor.clear();
                                editor.apply();
                                Intent intent = new Intent(Admin.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                        }

                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Apakah anda yakin ingin keluar?").setPositiveButton("Ya", dialog)
                        .setNegativeButton("Tidak", dialog).show();

            }
        });


        LnDaftarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin.this, List.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}