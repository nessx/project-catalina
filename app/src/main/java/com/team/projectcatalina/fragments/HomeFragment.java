package com.team.projectcatalina.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.team.projectcatalina.R;
import com.team.projectcatalina.clases.Dijkstra;
import com.team.projectcatalina.clases.Edge;
import com.team.projectcatalina.clases.Vert;
import com.team.projectcatalina.startmenu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //dikstra algorithm
    private static Spinner station_dest;
    protected ArrayList<Vert> paradas;
    private int item;
    //end

    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View HomeFragment = inflater.inflate(R.layout.fragment_home, container, false);

        station_dest = HomeFragment.findViewById(R.id.destination);
        Button btn = HomeFragment.findViewById(R.id.button);

        if(getArguments()!=null){
            paradas = (ArrayList<Vert>) getArguments().getSerializable("arrayParadas");
        }

        Log.i("FIREBASED", "ff " + paradas.get(0).getName());

        //mHashmap.put("ESPANYA/Estado", "DISPONIBLE");
        //mHashmap.put("VERDAGER/Estado", "DISPONIBLE");
        //mHashmap.put("DIAGONAL/Estado", "BLOQUEADA");

        /* ArrayList<Vert> listado = inicializarvert();
        Vert.showverts(paradas);

        for(int i=1;i<listado.size();i++){
            listado.get(i).showwdges();

            //importing stations for firebase
            //Log.i("logTes","chivato "+ listado.get(i));
            //mHashmap.put(listado.get(i)+"/Estado", "DISPONIBLE");
        }
        //myRef.updateChildren(mHashmap);

        for(int i=0;i<spinnerArray.size();i++){
            Log.d("FIREBASED", "Paradas " + spinnerArray.get(i));
        }

        //SPINNET CONFIG
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                spinnerArray
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        station_dest.setPrompt("Selecciona lugar de destino");
        station_dest.setAdapter(adapter);

        station_dest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                Toast.makeText(getContext(),station_dest.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                item = station_dest.getSelectedItemPosition();

                //destino
                Toast.makeText(getContext(),"Pos lista: "+item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //END

        //codigo del botton
        btn.setOnClickListener(v -> {
            Dijkstra.ShortestP(listado.get(item));
            Toast.makeText(getContext(),"TEXTO SELECCIONADO "+listado.get(item).toString(), Toast.LENGTH_SHORT).show();

            for(int i=0;i<listado.size();i++){
                Log.i("logTest","paradas minimas "+ Dijkstra.getShortestP(listado.get(i)));
            }
            Log.i("logTest","loged bottom "+ listado.get(item));
            Log.i("logTest","loged bottom pos "+ item);


        });
        */
        return HomeFragment;

    }
}