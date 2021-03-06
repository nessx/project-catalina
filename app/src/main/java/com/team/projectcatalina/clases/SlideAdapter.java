package com.team.projectcatalina.clases;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.google.android.gms.common.SignInButton;
import com.team.projectcatalina.MainActivity;
import com.team.projectcatalina.R;

public class SlideAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SlideAdapter(Context context){
        this.context = context;
    }

    public int[] slide_images = {
            R.drawable.h_bg,
            R.drawable.captura
    };

    public String[] slide_heading = {
        "SECURE TRANSPORT",
        "LOGIN"
    };

    public String [] slide_descs = {
            "HOLA BIENVENIDO/A A SECURE TRANSPORT, ERES COMO TE MUEVES, POR ESO NOS PREOCUPAMOS " +
                    "POR TU SEGURIDAD EN EL TRANSPORTE PUBLICO, REGISTRATE Y EMPIEZA A MOVERTE DE MANERA SEGURA!",
            "Hola, bienvenido/a de nuevo, inicia sesión!"

    };

    @Override
    public int getCount(){
        return slide_heading.length;
    }


    @Override
    public boolean isViewFromObject(View view, Object o){
        return view == (ConstraintLayout) o;
    }

    public Object instantiateItem(ViewGroup container, int position){
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);
        TextView slideHeading = (TextView) view.findViewById(R.id.slide_heading);
        TextView slideDesc = (TextView) view.findViewById(R.id.slide_desc);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_heading[position]);
        slideDesc.setText(slide_descs[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((ConstraintLayout)object);
    }
}
