package com.vbellos.dev.bookstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {

    EditText email,passwd;
    FirebaseAuth fAuth;
    FirebaseUser fuser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        fAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editTextTextEmailAddress);
        passwd = findViewById(R.id.editTextTextPassword);
        fuser = fAuth.getCurrentUser();
        if(fuser!=null){
                Intent i=new Intent(StartActivity.this,StoreActivity.class);
                startActivity(i);
                finish();
        }
    }

    public void login(View view)
    {

        if(!email.getText().toString().isEmpty() && !passwd.getText().toString().isEmpty()){
            fAuth.signInWithEmailAndPassword(email.getText().toString(),passwd.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"User logged in Successfully",Toast.LENGTH_LONG).show();
                                fuser = fAuth.getCurrentUser();

                                    Intent i=new Intent(StartActivity.this,StoreActivity.class);
                                    startActivity(i);
                                    finish();


                            }else{
                                new AlertDialog.Builder(StartActivity.this).setTitle("Unable to authenticate")
                                        .setMessage("Incorrect credentials provided").setCancelable(true).show();
                            }
                        }
                    });
        }else{
            new AlertDialog.Builder(StartActivity.this).setTitle("Unable to authenticate")
                    .setMessage("Please fill in all the fields").setCancelable(true).show();
        }
    }

    public void adminClick(View view)
    {
        Intent i = new Intent(this,AdminActivity.class);
        startActivity(i);
    }

    public void regclick(View view)
    {
        Intent i = new Intent(this,RegisterActivity.class);
        startActivity(i);
    }

}