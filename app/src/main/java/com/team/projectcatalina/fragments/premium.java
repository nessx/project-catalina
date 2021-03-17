package com.team.projectcatalina.fragments;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team.projectcatalina.R;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link premium#newInstance} factory method to
 * create an instance of this fragment.
 */
public class premium extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button btn_premium;
    private DatabaseReference myRef;

    public premium() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static premium newInstance(String param1, String param2) {
        premium fragment = new premium();
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
        // Inflate the layout for this fragment
        View premium = inflater.inflate(R.layout.fragment_premium, container, false);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("SECURE_TRANSPORT/USERS");
        btn_premium = premium.findViewById(R.id.BtnPremium);
        Map<String, Object> mHashmap = new HashMap<>();

        btn_premium.setOnClickListener(v -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
            dialog.setTitle( "Seguro/a?" )
                    .setMessage("Estas apunto de comprar el paquete premium de SECURE TRASPORT")
                 .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialoginterface, int i) {
                      dialoginterface.cancel();
                      }})
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            // Read from the database
                            myRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
                                    String personId = acct.getId();
                                    mHashmap.put(personId+"/PREMIUM", 1);
                                    myRef.updateChildren(mHashmap);

                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                    // Failed to read value
                                    Log.w("FIREBASEDB", "Failed to read value.", error.toException());
                                }
                            });
                        }
                    }).show();
        });

        return premium;
    }
}