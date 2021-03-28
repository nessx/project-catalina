package com.team.projectcatalina.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.team.projectcatalina.R;
import com.team.projectcatalina.clases.Dijkstra;
import com.team.projectcatalina.clases.Vert;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    TextView name;
    GoogleSignInClient mGoogleSignInClient;

    //dikstra algorithm
    private static Spinner station_dest, station_start;
    protected ArrayList<Vert> paradas;
    protected ArrayList<Vert> spinnerarray;
    private int from,to;
    //end

    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Log.d("aa","ENTRO AL oncreate de home");
            paradas = (ArrayList<Vert>) getArguments().getSerializable("arrayParadas");

            //Log.d("aa","ENTRO AL oncreate de home: " + mParam1 + "tam" + getArguments().size() + "aa" + parada.get(0).getName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View HomeFragment = inflater.inflate(R.layout.fragment_home, container, false);

        //spinner get data
        station_dest = HomeFragment.findViewById(R.id.destination);
        station_start = HomeFragment.findViewById(R.id.start);

        Button btn = HomeFragment.findViewById(R.id.button);
        TextView user_name = HomeFragment.findViewById(R.id.name);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());

        if (acct != null) {
            user_name.setText("Hola!, "+acct.getGivenName());
        }

        //Log.i("FIREBASED", "ff " + parada.get(0).getName());

        //mHashmap.put("ESPANYA/Estado", "DISPONIBLE");
        //mHashmap.put("VERDAGER/Estado", "DISPONIBLE");
        //mHashmap.put("DIAGONAL/Estado", "BLOQUEADA");

        Vert.showverts(paradas);

        for(int i=1;i<paradas.size();i++){
            paradas.get(i).showwdges();

            //importing stations for firebase
            //Log.i("logTes","chivato "+ listado.get(i));
            //mHashmap.put(listado.get(i)+"/Estado", "DISPONIBLE");
        }
        //myRef.updateChildren(mHashmap);

        for(int i=0;i<paradas.size();i++){
            Log.d("FIREBASED", "Paradas " + paradas.get(i));
        }

        //SPINNET CONFIG
        ArrayAdapter<Vert> adapter = new ArrayAdapter<Vert>(
                getContext(),
                android.R.layout.simple_spinner_item,
                paradas
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //STARTING STOPS
        station_start.setPrompt("Selecciona lugar de inicio");
        station_start.setAdapter(adapter);
        station_start.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                Toast.makeText(getContext(),station_start.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                from = station_start.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //FINISH STOPS
        station_dest.setPrompt("Selecciona lugar de destino");
        station_dest.setAdapter(adapter);

        station_dest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                Toast.makeText(getContext(),station_dest.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                to = station_dest.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //END

        //codigo del botton
        btn.setOnClickListener(v -> {
            Dijkstra.getShortestP(paradas.get(from));
            Dijkstra.ShortestP(paradas.get(to));

            Toast.makeText(getContext(),"TEXTO SELECCIONADO "+paradas.get(to).toString(), Toast.LENGTH_SHORT).show();

            for(int i=0;i<paradas.size();i++){
                Log.i("logTest","paradas minimas "+ Dijkstra.getShortestP(paradas.get(from)));
            }

            Log.i("logTest","loged bottom "+ paradas.get(to));
            Log.i("logTest","loged bottom pos "+ to);

        });

        return HomeFragment;
    }
}
