package com.vbellos.dev.bookstore;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    Button register;
    EditText email,passwd;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fAuth=FirebaseAuth.getInstance();
        email = findViewById(R.id.emailTXT);
        passwd = findViewById(R.id.passwdTXT);

        register = findViewById(R.id.signupBTN);
        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (email.getText().toString().isEmpty()||passwd.getText().toString().isEmpty()) {
                    new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("Blank field")
                            .setMessage("Please fill all the fields")
                            .setCancelable(true)
                            .show();
                }
                else if(passwd.getText().toString().length()<8)
                {
                    new AlertDialog.Builder(RegisterActivity.this)
                    .setTitle("Weak Password")
                        .setMessage("Password must be at least 8 characters long")
                        .setCancelable(true)
                        .show();
                }
                else{
                    fAuth.createUserWithEmailAndPassword(email.getText().toString(),passwd.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(),"User Created Successfully",Toast.LENGTH_LONG).show();
                                        finish();

                                    }else{
                                        new AlertDialog.Builder(RegisterActivity.this)
                                                .setTitle("Fail")
                                                .setMessage("Database error , maybe user already exsists or poor internet connection")
                                                .setCancelable(true)
                                                .show();
                                    }
                                }
                            });
                }
            }

        });

    }

}