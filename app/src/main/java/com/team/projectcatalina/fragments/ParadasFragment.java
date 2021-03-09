package com.team.projectcatalina.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.collection.LLRBNode;
import com.team.projectcatalina.R;
import com.team.projectcatalina.clases.RecyclerViewAdapter;
import com.team.projectcatalina.clases.Vert;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ParadasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ParadasFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    protected ArrayList<Vert> paradas;

    public ParadasFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ParadasFragment newInstance(String param1, String param2) {
        ParadasFragment fragment = new ParadasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("aa","ENTRO AL oncreate de paradas");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("aa","ENTRO AL ONRESUME del paradas");

        if (getArguments() != null) {
            Log.d("aa","ENTRO AL bundle");
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View listar = inflater.inflate(R.layout.fragment_paradas, container, false);

        if(getArguments()!=null){
            paradas = (ArrayList<Vert>) getArguments().getSerializable("arrayParadas");
        }

        Log.i("FIREBASED", "ff " + paradas.get(0).getName());

        RecyclerView recyclerView = (RecyclerView)listar.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(listar.getContext()));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, paradas);
        recyclerView.setAdapter(adapter);

        return listar;
    }
}