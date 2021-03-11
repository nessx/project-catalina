package com.team.projectcatalina;
import androidx.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team.projectcatalina.clases.Dijkstra;
import com.team.projectcatalina.clases.Edge;
import com.team.projectcatalina.clases.Vert;
import com.team.projectcatalina.clases.user;
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
    protected ArrayList<Vert> paradas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_menu);

        //nav menu
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(HomeFragment.newInstance("", ""));
        //end

        inicializarvert();

        Log.d("aa","ENTRO EN EL ONCREATE");
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
        bundle.putSerializable("arrayParadas", paradas);
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
                            openFragment(HomeFragment.newInstance("",""));
                            return true;
                        case R.id.navigation_paradas:
                            openFragment(ParadasFragment.newInstance("", ""));
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
    public void inicializarvert(){

        Map<String, Vert> vertexMap = new HashMap<String, Vert>();

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("SECURE_TRANSPORT/Estaciones");
        Log.i("hola", String.valueOf(myRef));

        DatabaseReference myRef2 = database.getReference("SECURE_TRANSPORT/NODOS");

        //Writing Hashmap
        Map<String, Object> mHashmap = new HashMap<>();
        /*
        mHashmap.put("nodo"+i+"/FROM", vertexMap.get(vFrom).toString());
        mHashmap.put("nodo"+i+"/TO", vertexMap.get(vTo).toString());
        mHashmap.put("nodo"+i+"/WEIGHT", weight);
        myRef.updateChildren(mHashmap);
         */

        //Log.i("logTes","chivato "+ listado.get(i));

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();
                int i = 0;
                for (Map.Entry entry : value.entrySet()) {
                    Log.d("KEY", "key: " + entry.getKey() + "; value: " + entry.getValue());

                    Vert v = new Vert(entry.getKey().toString());
                    Log.d("vvv", "vvv " + v);
                    //spinnerArray.add(entry.getKey().toString());
                    paradas.add(v);
                    Log.d("Lista", "Lista " + paradas.get(i));
                    i++;
                }
                //Log.d("FIREBASEDB", "Value is: " + value.get("Hospital_clinic"));

                myRef2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot data : dataSnapshot.getChildren()) {
                            String vFrom = data.child("FROM").getValue(String.class);
                            String vTo = data.child("TO").getValue(String.class);
                            float weight = data.child("WEIGHT").getValue(float.class);
                            Log.d("aa", "+" + paradas.size());
                            for (int i=0;i<paradas.size();i++){
                                paradas.get(i).addNeighbour(new Edge(weight, vertexMap.get(vFrom), vertexMap.get(vTo)));
                            }
                            Log.d("FIREBASEDB", "FROM:" + vFrom + "; TO:" + vTo + "; WEIGHT;"+ weight);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("FIREBASEDB", "Failed to read value.", error.toException());
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("FIREBASEDB", "Failed to read value.", error.toException());
            }
        });




    }

}
