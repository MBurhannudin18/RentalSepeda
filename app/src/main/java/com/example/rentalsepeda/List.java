package com.example.rentalsepeda;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.RenderScript;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.error.ANError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class List extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Adapter adapter;

    ArrayList<Model> datalist;

    CardView cardview;

    TextView txtnama, txtemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        txtnama = findViewById(R.id.txtemail);
        txtemail = findViewById(R.id.txtnama);

        cardview = findViewById(R.id.cardku);
        recyclerView = findViewById(R.id.list);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Data Customer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getData();

    }

    private void getData() {
        datalist = new ArrayList<>();
        Log.i("daa", "onCreate: ");

        AndroidNetworking.post("http://192.168.137.1/RentalKamera/Viewdata.php")
                .addBodyParameter("roleuser", "2")
                .setTag("test")
                .setPriority(RenderScript.Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = response.getJSONArray("result");

                            for (int i = 0; i < data.length(); i++) {

                                Model model = new Model();
                                JSONObject object = data.getJSONObject(i);
                                model.setId(object.getString("id"));
                                model.setNama(object.getString("nama"));
                                model.setEmail(object.getString("email"));
                                model.setNohp(object.getString("nohp"));
                                model.setAlamat(object.getString("alamat"));
                                model.setNoktp(object.getString("noktp"));
                                datalist.add(model);

                            }

                            adapter = new Adapter(datalist);

                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(List.this);

                            recyclerView.setLayoutManager(layoutManager);

                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.i("daa", "onResponse: " + anError.toString());
                        Log.i("daa", "onResponse: " + anError.getErrorBody());
                        Log.i("daa", "onResponse: " + anError.getErrorCode());
                        Log.i("daa", "onResponse: " + anError.getErrorDetail());
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 23 && data.getStringExtra("refresh") != null) {
            //refresh list
            getData();
            Toast.makeText(this, "iiin", Toast.LENGTH_SHORT).show();

        }
    }
}