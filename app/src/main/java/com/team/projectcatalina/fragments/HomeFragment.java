package com.team.projectcatalina.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.team.projectcatalina.MainActivity;
import com.team.projectcatalina.R;
import com.team.projectcatalina.clases.Dijkstra;
import com.team.projectcatalina.clases.Vert;
import com.team.projectcatalina.clases.user;

import org.w3c.dom.Text;

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
    private static Spinner station_dest;
    protected ArrayList<Vert> paradas;
    protected ArrayList<Vert> spinnerarray;
    private int item;
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
    
    /*
    public static HomeFragment newInstance(ArrayList<Vert> arraypar) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putSerializable("ArrayParadas", arraypar);
        fragment.setArguments(args);

        return fragment;
    }*/


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

        station_dest = HomeFragment.findViewById(R.id.destination);
        Button btn = HomeFragment.findViewById(R.id.button);
        TextView user_name = HomeFragment.findViewById(R.id.name);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
        user usr = new user();
        if (acct != null) {
            usr.setid(acct.getId());
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

                //spinner array es la arraylist de la que se alimenta el spinner
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
            Dijkstra.ShortestP(paradas.get(item));
            Toast.makeText(getContext(),"TEXTO SELECCIONADO "+paradas.get(item).toString(), Toast.LENGTH_SHORT).show();
            for(int i=0;i<paradas.size();i++){
                Log.i("logTest","paradas minimas "+ Dijkstra.getShortestP(paradas.get(i)));
            }
            Log.i("logTest","loged bottom "+ paradas.get(item));
            Log.i("logTest","loged bottom pos "+ item);


        });
        return HomeFragment;
    }
}
