package com.example.rentalsepeda;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.UserViewHolder> {

    private ArrayList<ColorSpace.Model> dataList;
    View viewku;

    public Adapter(ArrayList<ColorSpace.Model> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        viewku = layoutInflater.inflate(R.layout.listdata, parent, false);
        return new UserViewHolder(viewku);
    }

        @Override
        public void onBindViewHolder(@NonNull final UserViewHolder holder, final int position) {
            holder.txtemail.setText(dataList.get(position).getEmail());
            holder.txtnama.setText(dataList.get(position).getNama());
            holder.cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(holder.itemView.getContext(), Info.class);
                    in.putExtra("id", dataList.get(position).getId());
                    in.putExtra("nama", dataList.get(position).getEmail());
                    in.putExtra("email", dataList.get(position).getNama());
                    in.putExtra("nohp", dataList.get(position).getNohp());
                    in.putExtra("alamat", dataList.get(position).getAlamat());
                    in.putExtra("noktp", dataList.get(position).getNoktp());

                    holder.itemView.getContext().startActivity(in);
                }
            });
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        class UserViewHolder extends RecyclerView.ViewHolder{
            private TextView txtemail, txtnama;
            CardView cardview;

            UserViewHolder(View itemView) {
                super(itemView);
                cardview = itemView.findViewById(R.id.cardku);
                txtemail = itemView.findViewById(R.id.txtemail);
                txtnama = itemView.findViewById(R.id.txtnama);
            }
        }
}
