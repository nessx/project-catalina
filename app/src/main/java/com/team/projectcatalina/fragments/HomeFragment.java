package com.team.projectcatalina.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.team.projectcatalina.R;
import com.team.projectcatalina.startmenu;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button sign_out;
    TextView name;
    TextView email;
    TextView id;
    ImageView photo;

    public HomeFragment() {
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
        sign_out = HomeFragment.findViewById(R.id.log_out);
        name = HomeFragment.findViewById(R.id.name);
        email = HomeFragment.findViewById(R.id.email);
        id = HomeFragment.findViewById(R.id.id);
        photo = HomeFragment.findViewById(R.id.photo);

        startmenu sm = new startmenu();

        name.setText("Nombre: "+sm.personName);
        email.setText("Email: "+sm.personEmail);
        id.setText("ID: "+sm.personId);
        Glide.with(this).load(sm.personPhoto).into(photo);

        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sm.signOut();
            }
        });


        // Inflate the layout for this fragment
        return HomeFragment;
    }
}