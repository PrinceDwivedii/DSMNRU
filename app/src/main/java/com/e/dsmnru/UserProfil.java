package com.e.dsmnru;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfil extends AppCompatActivity {
    TextInputLayout fullName, email, password;
    TextView usernameLabel, fullNameLabel;
    String _Username, _Email, _Password, _Name;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        reference = FirebaseDatabase.getInstance().getReference("user");
        fullName = findViewById(R.id.full_name_profile);
        email = findViewById(R.id.email_profile);
        password = findViewById(R.id.password_profile);
        fullNameLabel = findViewById(R.id.fullname_field);
        usernameLabel = findViewById(R.id.username_field);


        ShowAllUserData();
    }

    private void ShowAllUserData() {

        Intent intent = getIntent();
        _Username = intent.getStringExtra("username");
        _Name = intent.getStringExtra("name");
        _Email = intent.getStringExtra("email");
        _Password = intent.getStringExtra("password");

        fullNameLabel.setText(_Name);
        usernameLabel.setText(_Username);

        fullName.getEditText().setText(_Name);
        email.getEditText().setText(_Email);
        password.getEditText().setText(_Password);
    }

    public void Update(View view) {
        if (isNameChanged() || isPasswordChanged()) {
            Toast.makeText(this, "Data Has Been Updated", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Data not updated", Toast.LENGTH_LONG).show();
        }


    }

    private boolean isNameChanged() {
        if (!_Name.equals(fullName.getEditText().getText().toString())) {
            reference.child(_Username).setValue(fullName.getEditText().getText().toString());
            _Name = fullName.getEditText().getText().toString();

            return true;
        } else {
            return false;
        }
    }


    private boolean isPasswordChanged() {
        if (!_Password.equals(password.getEditText().getText().toString())) {
            reference.child(_Username).child("(password").setValue(password.getEditText().getText().toString());
            _Password = password.getEditText().getText().toString();
            return true;
        } else {
            return false;
        }
    }
}