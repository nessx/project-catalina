package com.team.projectcatalina.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team.projectcatalina.R;
import com.team.projectcatalina.clases.RecyclerViewAdapter;

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

    public ParadasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment listar.
     */
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
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View listar = inflater.inflate(R.layout.fragment_paradas, container, false);

        HomeFragment hf = new HomeFragment();

        RecyclerView recyclerView = (RecyclerView)listar.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(listar.getContext()));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, hf.inicializarvert());

        recyclerView.setAdapter(adapter);


        return listar;
    }
}