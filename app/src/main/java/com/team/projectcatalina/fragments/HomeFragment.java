package com.team.projectcatalina.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.team.projectcatalina.MainActivity;
import com.team.projectcatalina.R;
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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    TextView name;
    GoogleSignInClient mGoogleSignInClient;

    //dikstra algorithm
    private static Spinner station_dest;
    protected ArrayList<Vert> parada;
    protected ArrayList<Vert> spinnerarray;
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
    
    /*
    public static HomeFragment newInstance(ArrayList<Vert> arraypar) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putSerializable("ArrayParadas", arraypar);
        myFragment.setArguments(args);

        return fragment;
    }
    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        args.putSerializable("arrayParadas", paradas);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }*/

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Log.d("aa","ENTRO AL oncreate de home");

            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

            parada = (ArrayList<Vert>) getArguments().getSerializable("arrayParadas");
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

        if(getArguments()!=null){
            parada = (ArrayList<Vert>) getArguments().getSerializable("arrayParadas");
        }

        //Log.i("FIREBASED", "ff " + parada.get(0).getName());

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
