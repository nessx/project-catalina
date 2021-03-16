package com.team.projectcatalina.clases;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.team.projectcatalina.R;
import com.team.projectcatalina.fragments.ParadasFragment;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private ArrayList<Vert> array_paradas;
    private ParadasFragment context;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    public RecyclerViewAdapter(ParadasFragment con, ArrayList<Vert> arrI){
        array_paradas = arrI;
        context = con;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.etiquetaNom.setText(array_paradas.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return array_paradas.size();
    }
/*
    public String Query (){
        Query estado =
                FirebaseDatabase.getInstance().getReference()
                    .child("Estaciones")
                    .orderByValue()
                    .equalTo("DISPONIBLE");
        String estadoS = estado.toString();
        Log.i("ESTADOS", "estado? " + estadoS);
        return estadoS;
    }*/


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView etiquetaNom,estado;
        CardView layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            estado = itemView.findViewById(R.id.estado);
            etiquetaNom = itemView.findViewById(R.id.itemListIncidencia);
            layout = itemView.findViewById(R.id.layout);
        }
    }
    

}
