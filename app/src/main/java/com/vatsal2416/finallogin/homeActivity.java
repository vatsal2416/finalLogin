package com.vatsal2416.finallogin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class homeActivity extends AppCompatActivity {

    private Button btnLogout;
    GoogleSignInOptions gso;
    private FirebaseAuth mAuth;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        btnLogout = findViewById(R.id.btnLogout);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        mAuth = FirebaseAuth.getInstance();
        TextView textViewName = findViewById(R.id.textView7);
        imageView = findViewById(R.id.imageView);

        //imageView.setImageURI(mAuth.getCurrentUser().getPhotoUrl());

        String personName = account.getDisplayName();
        String personPhotoUrl = account.getPhotoUrl().toString();
        String email = account.getEmail();
        textViewName.setText(personPhotoUrl);

        textViewName.setText(mAuth.getCurrentUser().getDisplayName());

        Glide.with(getApplicationContext()).load(personPhotoUrl)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // mAuth.signOut();
                signOut();
                startActivity(new Intent(homeActivity.this, MainActivity.class));

            }
        });
    }

    private void signOut(){
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(),"Logged out",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
