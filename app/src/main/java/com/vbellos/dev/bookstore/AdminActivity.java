package com.vbellos.dev.bookstore;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vbellos.dev.bookstore.models.Book;

import java.util.UUID;

public class AdminActivity extends AppCompatActivity {

    EditText title,description,price,path,numcopies;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        fauth = FirebaseAuth.getInstance();

        title = findViewById(R.id.editTextTitle);
        description = findViewById(R.id.editTextDescription);
        price = findViewById(R.id.editTextPrice);
        path = findViewById(R.id.editTextPath);
        numcopies = findViewById(R.id.editTextNumber);

    }

    public void add(View view)
    {

        UUID id = UUID.randomUUID();
        String uuid = id.toString();

        Book book = new Book(uuid,title.getText().toString()
                ,description.getText().toString(),path.getText().toString(),price.getText().toString()
                ,numcopies.getText().toString());

        if(book.isAccepted()){pushToDB(book);}
        else
            {
                new AlertDialog.Builder(AdminActivity.this).setTitle("Unable to Add Book")
                        .setMessage("Fill in all the necessary fields").setCancelable(true).show();
            }

    }
    public void pushToDB(Book book)
    {
        db=FirebaseDatabase.getInstance();
        ref=db.getReference(Book.class.getSimpleName());
        ref.child(book.getUuid()).setValue(book).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"Book Added Successfully",Toast.LENGTH_LONG).show();
                    resetFields();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Failed to add the book",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void resetFields()
    {
        title.setText("");
        description.setText("");
        price.setText("");
        path.setText("");
        numcopies.setText("");
    }



}