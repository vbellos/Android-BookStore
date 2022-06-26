package com.vbellos.dev.bookstore;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vbellos.dev.bookstore.adapters.StoreRecyclerViewAdapter;
import com.vbellos.dev.bookstore.models.Book;

import java.util.ArrayList;
import java.util.Locale;

public class StoreActivity extends AppCompatActivity {

    private ArrayList<Book> books;
    private RecyclerView recyclerView;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private FirebaseAuth fAuth;
    private TextToSpeech tts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        fAuth = FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser() ==null)
        {
            Intent i = new Intent(this,StartActivity.class);
            startActivity(i);
            finish();
        }

        tts  = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                tts.setLanguage(Locale.UK);
            }
        });


        db=FirebaseDatabase.getInstance();
        ref=db.getReference(Book.class.getSimpleName());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                books=new ArrayList<Book>();
                for(DataSnapshot sn:snapshot.getChildren()){
                    Book book=sn.getValue(Book.class);
                    if(book.isAccepted())
                    {
                        books.add(book);
                    }
                }
                StoreRecyclerViewAdapter rva=new StoreRecyclerViewAdapter(books);
                rva.setTextToSpeechEventListener(new StoreRecyclerViewAdapter.TextToSpeechEventListener() {
                    @Override
                    public void onTextToSpeech() {
                        tts.speak(rva.getTextToSpeak(),TextToSpeech.QUEUE_FLUSH,null);
                    }
                });
                recyclerView =findViewById(R.id.recyclerView);
                RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(rva);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Do something
            } });

    }

    public void GoToCart(View view)
    {
        Intent i = new Intent(this,CartActivity.class);
        startActivity(i);
    }

    public void Logout(View view)
    {
        fAuth.signOut();
        Intent i = new Intent(this,StartActivity.class);
        startActivity(i);
    }


}