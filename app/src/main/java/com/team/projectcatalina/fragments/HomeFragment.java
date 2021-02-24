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
    private static List<String> spinnerArray;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View HomeFragment = inflater.inflate(R.layout.fragment_home, container, false);

        station_dest = HomeFragment.findViewById(R.id.destination);
        Button btn = HomeFragment.findViewById(R.id.button);
        spinnerArray = new ArrayList<>();

        //test
        TextView txt = HomeFragment.findViewById(R.id.textView);

        ArrayList<Vert> listado = inicializarvert();
        Vert.showverts(listado);
        for(int i=1;i<listado.size();i++){
            listado.get(i).showwdges();
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
            Log.i("logTest","loged bottom "+ listado.get(item));
            Log.i("logTest","loged bottom pos "+ item);
            Dijkstra.ShortestP(listado.get(item));
            Toast.makeText(getContext(),"TEXTO SELECCIONADO "+listado.get(item).toString(), Toast.LENGTH_SHORT).show();

            for(int i=1;i<listado.size();i++){
                Log.i("logTest","paradas minimas "+ Dijkstra.getShortestP(listado.get(i)));
            }

        });

        return HomeFragment;
    }

    //DIKSTRA ALGORITHM
    public ArrayList<Vert> inicializarvert(){
        ArrayList<Vert> lista = new ArrayList<>();

        Map<String, Vert> vertexMap = new HashMap<String, Vert>();
        BufferedReader in = null;

        try {
            URL url = new URL("http://indomablesrp.online/file.txt");
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            boolean inVertex = true;

            while ((line = in.readLine()) != null) {
                if (line.charAt(0) == '#') {
                    inVertex = false;
                    continue;
                }
                if (inVertex) {
                    //store the vertices
                    int indexOfSpace = line.indexOf(' ');
                    String vertexId = line.substring(0, indexOfSpace);
                    String vertexName = line.substring(indexOfSpace + 1);
                    Vert v = new Vert(vertexName);
                    lista.add(v);
                    vertexMap.put(vertexId, v);

                    //adding items for the spinner
                    spinnerArray.add(vertexId);

                } else {
                    //store the edges
                    String[] parts = line.split(" ");
                    String vFrom = parts[0];
                    String vTo = parts[1];
                    double weight = Double.parseDouble(parts[2]);
                    Vert v = vertexMap.get(vFrom);
                    if (v != null) {
                        v.addNeighbour(new Edge(weight, vertexMap.get(vFrom), vertexMap.get(vTo)));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            if(in!= null)
                try {
                    in.close();
                } catch (IOException ignore) {
                    ignore.printStackTrace();
                }
        }
        return lista;
    }
}