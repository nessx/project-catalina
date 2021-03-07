package com.team.projectcatalina;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team.projectcatalina.clases.SlideAdapter;
import com.team.projectcatalina.fragments.HomeFragment;
import com.team.projectcatalina.sp.sp_manager;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    //declaracion de variables junto a su tipo
    private sp_manager s_preferences;
    private static final String TAG = "TEST";

    private static final int RC_SIGN_IN = 101;
    public static SignInButton signInButton;
    GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;


    //Slider
    private ViewPager mSliderViewPager;
    private LinearLayout mDoLayout;
    private SlideAdapter sliderAdapter;
    private TextView[] mDots;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        s_preferences = new sp_manager(getApplicationContext());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("SECURE_TRANSPORT/USERS");

        //PERMISION POLICY
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //END

        //si el usuario no esta loggeado
        if (!new sp_manager(this).isUserGoogleLogedOut()) {
            gotomenu();
        }

        //SLIDER INITIALATION VARIABLES
        mSliderViewPager = (ViewPager) findViewById(R.id.ViewPager);
        mDoLayout = (LinearLayout) findViewById(R.id.LinerLayout);
        sliderAdapter = new SlideAdapter(this);
        mSliderViewPager.setAdapter(sliderAdapter);
        addDotsIndicator(0);
        mSliderViewPager.addOnPageChangeListener(viewlistener);
        DisplayMetrics dm =new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //END


        //GOOGLE SING
        //Initializing Views
        signInButton = findViewById(R.id.signInButton);
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder (GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken (getString (R.string.default_web_client_id))
                .requestEmail ()
                .build ();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        //END
    }

    public void gotomenu(){
        Intent intent = new Intent (MainActivity.this, startmenu.class);
        startActivity(intent);
    }

    //FIREBASE LOGIN GOOGLE ACCOUNT
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.i(TAG, "firebaseAuthWithGoogle:" + account.getId());
                SharedPreferences sp = getSharedPreferences("GoogleLoginDetails" ,Context.MODE_PRIVATE);
                s_preferences.SaveGoogleLoginDetails(account.getId());
                firebaseAuthWithGoogle(account.getIdToken());

                //Writing Hashmap
                Map<String, Object> mHashmap = new HashMap<>();

                //Log.i("logTes","chivato "+ listado.get(i));

                // Read from the database
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();

                        for (Map.Entry entry : value.entrySet()) {
                            //Log.d("FIREBASEUSER", "key: " + entry.getKey() + "; value: " + entry.getValue());
                        }

                        if (value.get(account.getId()) != null){
                            Log.d("FIREBASEUSER", "EXISTE");
                        }else{
                            Log.d("FIREBASEUSER", "NO EXISTE");
                            mHashmap.put(account.getId()+"/PREMIUM", 0);
                            mHashmap.put(account.getId()+"/MAIL", account.getEmail());
                            mHashmap.put(account.getId()+"/FULLNAME", account.getDisplayName());
                            myRef.updateChildren(mHashmap);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("FIREBASEDB", "Failed to read value.", error.toException());
                    }
                });

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this,"Google sign in failed "+e.toString(), Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Google sign in failed "+e.toString());
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        Intent intent = new Intent(MainActivity.this, startmenu.class);
        startActivity(intent);
    }

    //SLIDER
    public void addDotsIndicator(int position){
        mDots = new TextView[2];
        mDoLayout.removeAllViews();

        for (int i = 0; i < mDots.length; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));

            mDoLayout.addView(mDots[i]);
            Log.i("pos", "POSITION:" + position);

            //controlar la posicion del slider para poner visible el btn cuando este en el slider numero 2
            if (position == 1) {
                signInButton.setVisibility(View.VISIBLE);
            }
        }


        if (mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }
    ViewPager.OnPageChangeListener viewlistener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    //END
}