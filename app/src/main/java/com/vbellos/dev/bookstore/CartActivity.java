package com.vbellos.dev.bookstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vbellos.dev.bookstore.adapters.CartRecyclerViewAdapter;
import com.vbellos.dev.bookstore.models.Book;
import com.vbellos.dev.bookstore.models.Order;
import com.vbellos.dev.bookstore.models.OrderEntry;

public class CartActivity extends AppCompatActivity implements OrderChangedListener {

    private RecyclerView recyclerView;
    private FirebaseAuth FAuth;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private OrderEntry order;
    private TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Order.addListener(this);

        total = findViewById(R.id.carttotal);

        order = new OrderEntry();

        FAuth = FirebaseAuth.getInstance();



        db=FirebaseDatabase.getInstance();


        if(FAuth.getCurrentUser() == null)
        {
            Intent i = new Intent(this,StartActivity.class);
            startActivity(i);
            finish();
        }

        InitView();
    }

    @Override
    public void OrderChanged() {
        InitView();
    }

    public void InitView()
    {
        CartRecyclerViewAdapter rva=new CartRecyclerViewAdapter();
        recyclerView =findViewById(R.id.recyclerViewCart);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(rva);
        total.setText(Order.getTotal().toString());
    }



    public void buy(View view)
    {
        FirebaseUser fuser = FAuth.getCurrentUser();

            order.setUserUID(fuser.getUid());
            order.setUserEmail(fuser.getEmail());
            order.Inherit();
            ref=db.getReference("Order");
            ref.push().setValue(order).addOnCompleteListener(this,new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        for (Book book:order.getBooks())
                        {
                            int i = order.getBooks().indexOf(book);
                            int copies = Integer.parseInt(book.getCopies());
                            copies = copies - order.getCopies().get(i);
                            book.setCopies(String.valueOf(copies));
                            ref = db.getReference("Book");
                            ref.child(book.getUuid()).setValue(book);
                        }

                        new AlertDialog.Builder(CartActivity.this)
                                .setTitle("Order Completed")
                                .setMessage(order.getText())
                                .setCancelable(true)
                                .show();
                        Toast.makeText(getApplicationContext(),"Purchase Completed Successfully",Toast.LENGTH_LONG).show();

                        Order.Clear();
                        order.Inherit();

                    }
                    else{
                        new AlertDialog.Builder(CartActivity.this).setTitle("Unable to Complete Purchase")
                                .setMessage("Please try again later").setCancelable(true).show();
                    }
                }
            });
        }


}