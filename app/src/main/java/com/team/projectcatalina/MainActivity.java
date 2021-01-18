package com.team.projectcatalina;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.team.projectcatalina.sp.sp_manager;
import com.team.projectcatalina.clases.usuario;
import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    //declaracion de variables junto a su tipo
    Button logbtn;
    EditText loguser, logpass;
    private CheckBox checkBoxRememberMe;
    private sp_manager s_preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        s_preferences = new sp_manager(getApplicationContext());
        /*
        //test language preferences
        if(!new sp_manager(this).noLang()){
            setAppLocale(s_preferences.getLang());
        }*/

        //Checkbox
        checkBoxRememberMe = (CheckBox) findViewById(R.id.checkBox);
        //si el usuario no esta loggeado
        if (!new sp_manager(this).isUserLogedOut()) {
            //el usuario y la contraseña estan guardados en preferences
            gotomenu();
        }

        //De declaracion de variables
        loguser = findViewById(R.id.loguser);
        logpass = findViewById(R.id.logpass);
        logbtn = findViewById(R.id.logbtn);

        //codigo del botton
        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario user = new usuario();
                if ((loguser.getText().toString().trim().equals(user.getuser())) && (logpass.getText().toString().trim().equals(user.getpasswd()))){
                    if (checkBoxRememberMe.isChecked())
                        saveLoginDetails(loguser.getText().toString().trim(), logpass.getText().toString().trim());
                    SharedPreferences sp = getSharedPreferences("LoginDetails" ,Context.MODE_PRIVATE);
                    s_preferences.getUser();
                    s_preferences.getPass();
                    gotomenu();
                }
                else{
                    Snackbar.make(v, "ERROR AL INICIAR SESION", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }
    private void saveLoginDetails(String user, String password) {
        new sp_manager(this).saveLoginDetails(user, password);
    }

    public void gotomenu(){
        Intent intent = new Intent (MainActivity.this, MainActivity.class);
        startActivity(intent);
    }

    /*
    private void setAppLocale(String localeCode){
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(new Locale(localeCode.toLowerCase()));
        resources.updateConfiguration(configuration, displayMetrics);
        configuration.locale = new Locale(localeCode.toLowerCase());
        resources.updateConfiguration(configuration, displayMetrics);
    }
     */


}