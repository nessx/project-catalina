package com.team.projectcatalina;
import androidx.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team.projectcatalina.clases.Edge;
import com.team.projectcatalina.clases.Estacion;
import com.team.projectcatalina.clases.Vert;
import com.team.projectcatalina.fragments.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class startmenu extends AppCompatActivity {
    BottomNavigationView bottomNavigation;

    private static List<String> spinnerArray;
    protected ArrayList<Vert> paradas;  // <- firebase
    protected ArrayList<Vert> parada; // <- archivo txt

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_menu);
        paradas = new ArrayList<Vert>();
        parada = inicializarver();

        //nav menu
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        //end

        inicializarvert();
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("aa","ENTRO AL ONRESUME");
    }

    //bottom menu
    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();

        Bundle bundle = new Bundle();
        //la verdadera arraylist es "paradas", parada es cogiendola del archivo
        //bundle.putSerializable("arrayParadas", paradas); <- firebase
        bundle.putSerializable("arrayParadas", parada);
        fragment.setArguments(bundle);

        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            openFragment(HomeFragment.newInstance());
                            return true;
                        case R.id.navigation_paradas:
                            openFragment(ParadasFragment.newInstance());
                            return true;
                        case R.id.navigation_premium:
                            openFragment(premium.newInstance("", ""));
                            return true;
                        case R.id.navigation_soporte_fragment_v2:
                            openFragment(SoporteFragmentV2.newInstance("", ""));
                            return true;
                        case R.id.navigation_perfil:
                            openFragment(profileFragment.newInstance("", ""));
                            return true;
                    }
                    return false;
                }
            };

    //DIKSTRA ALGORITHM
    public void inicializarvert() {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("SECURE_TRANSPORT/Estaciones");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();
                int i = 0;

                for (Map.Entry entry : value.entrySet()) {
                    Log.d("KEY", "key: " + entry.getKey() + "; value: " + entry.getValue());
                    Vert v = new Vert(entry.getKey().toString());

                    //Estacion y nombre
                    Estacion est = new Estacion(entry.getKey().toString(),entry.getValue().toString() );
                    Log.d("ESTADOS", "ESTADO:" + est.getEstado() + "; PARADA:" +est.getName());

                    paradas.add(v);

                    Log.i("provesMarta", "Lista " + paradas.get(i));
                    i++;
                    Log.i("provesMarta", "" + paradas.size());
                }
                addNei();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("FIREBASEDB", "Failed to read value.", error.toException());
            }
        });
    }

    public void addNei(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef2 = database.getReference("SECURE_TRANSPORT/NODOS");
        Map<String, Vert> vertexMap = new HashMap<String, Vert>();

        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()) {

                    String vFrom = data.child("FROM").getValue(String.class);
                    String vTo = data.child("TO").getValue(String.class);
                    double weight = data.child("WEIGHT").getValue(double.class);

                    Vert v = new Vert(vFrom);
                    vertexMap.put(vTo, v);

                    if (v != null) {
                        v.addNeighbour(new Edge(weight, vertexMap.get(vFrom), vertexMap.get(vTo)));
                    }

                    Log.d("aa", "+" + paradas.size());
                    Log.d("FIREBASEDB", "FROM:" + vFrom + "; TO:" + vTo + "; WEIGHT;"+ weight);
                }
                openFragment(HomeFragment.newInstance());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("FIREBASEDB", "Failed to read value.", error.toException());
            }
        });
        Log.i("provesMarta", "" + paradas.size());
    }



    //DIKSTRA ALGORITHM
    //probando con el archivo txt por que con firebase no funciona
    public ArrayList<Vert> inicializarver(){
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
                    //spinnerArray.add(vertexId);

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