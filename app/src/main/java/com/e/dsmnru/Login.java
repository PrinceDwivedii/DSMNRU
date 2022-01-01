package com.e.dsmnru;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    Button callSignUp, login_btn;
    ImageView image;
    TextView logotext, Slogantext;
    TextInputLayout regUsername, regPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        callSignUp = findViewById(R.id.signup_screen);
        image = findViewById(R.id.Logo_image);
        logotext = findViewById(R.id.l_textView2);
        Slogantext = findViewById(R.id.l_textView3);
        regUsername = findViewById(R.id.username);
        regPassword = findViewById(R.id.password);
        login_btn = findViewById(R.id.Login_btn);


        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Signup.class);
                Pair[] pairs = new Pair[6];
                pairs[0] = new Pair<View, String>(image, "Splash_image");
                pairs[1] = new Pair<View, String>(logotext, "Splash_text");
                pairs[2] = new Pair<View, String>(Slogantext, "Splash_text1");
                pairs[3] = new Pair<View, String>(regUsername, "Splash_username");
                pairs[4] = new Pair<View, String>(regPassword, "Splash_Password");
                pairs[5] = new Pair<View, String>(login_btn, "Splash_Gobtn");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                    startActivity(intent, options.toBundle());
                    return;
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        finish();
    }

    private Boolean validateUsername() {
        String val = regUsername.getEditText().getText().toString();
        if (val.isEmpty()) {
            regUsername.setError("Field cannot be empty");
            return false;
        } else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = regPassword.getEditText().getText().toString();
        if (val.isEmpty()) {
            regPassword.setError("Field cannot be empty");
            return false;
        } else {
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }

    public void call_profile(View view) {
        //Validate Login Info
        if (!validateUsername() | !validatePassword()) {
            return;
        }else{
            isUser();
        }


    }

    private void isUser() {
        final String UserEnteredUsername =regUsername.getEditText().getText().toString().trim();
        final String UserEnteredPassword =regPassword.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user");
        Query checkUser = reference.orderByChild("username").equalTo(UserEnteredUsername);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    regUsername.setError(null);
                    regUsername.setErrorEnabled(false);
                    String passwordFromDB = dataSnapshot.child(UserEnteredUsername).child("password").getValue(String.class);


                    if (passwordFromDB.equals(UserEnteredPassword)) {

                        regUsername.setError(null);
                        regUsername.setErrorEnabled(false);

                        String nameFromDB = dataSnapshot.child(UserEnteredUsername).child("name").getValue(String.class);
                        String usernameFromDB = dataSnapshot.child(UserEnteredUsername).child("username").getValue(String.class);
                        String phoneNoFromDB = dataSnapshot.child(UserEnteredUsername).child("phoneNo").getValue(String.class);
                        String emailFromDB = dataSnapshot.child(UserEnteredUsername).child("email").getValue(String.class);


                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("phoneNo", phoneNoFromDB);
                        intent.putExtra("password", passwordFromDB);
                        startActivity(intent);
                    } else {
                        regPassword.setError("Wrong Password");
                        regPassword.requestFocus();
                    }

                } else {
                    regUsername.setError("No Such User Exist");
                    regUsername.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}




