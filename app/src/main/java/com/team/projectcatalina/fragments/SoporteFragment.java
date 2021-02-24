package com.team.projectcatalina.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.team.projectcatalina.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SoporteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SoporteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SoporteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SoporteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SoporteFragment newInstance(String param1, String param2) {
        SoporteFragment fragment = new SoporteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View SoporteFragment = inflater.inflate(R.layout.fragment_soporte, container, false);
        Button BtnReportMetro= SoporteFragment.findViewById(R.id.button);
        BtnReportMetro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GoToSoportev2();
            }
        });
        return SoporteFragment;
    }
    public void GoToSoportev2() {
        
    }

}