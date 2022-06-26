package com.vbellos.dev.bookstore.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vbellos.dev.bookstore.R;
import com.vbellos.dev.bookstore.models.Book;
import com.vbellos.dev.bookstore.models.Order;

import java.util.ArrayList;

public class StoreRecyclerViewAdapter extends RecyclerView.Adapter<StoreRecyclerViewAdapter.MyViewHolder>{
    private ArrayList<Book> books;
    private TextToSpeechEventListener mListener;

    private String textToSpeak;

    public String getTextToSpeak() {
        return textToSpeak;
    }

    public interface TextToSpeechEventListener {

        void onTextToSpeech();
    }
    public void setTextToSpeechEventListener(StoreRecyclerViewAdapter.TextToSpeechEventListener eventListener) {
        mListener = eventListener;
    }

    public StoreRecyclerViewAdapter(ArrayList<Book> books){
        this.books = books;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title, description, price, copies;
        private Button addtocart,tts;
        private ImageView image;
        public MyViewHolder(final View view){
            super(view);
            title =(TextView) view.findViewById(R.id.textViewtitle);
            description =(TextView) view.findViewById(R.id.textViewdescription);
            price =(TextView) view.findViewById(R.id.textViewprice);
            copies =(TextView) view.findViewById(R.id.textViewCopies);
            image =(ImageView)view.findViewById(R.id.imageViewcover);
            addtocart =(Button) view.findViewById(R.id.addtocartbutton);
            tts=(Button) view.findViewById(R.id.ttsBTN);

        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.control_book,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


            String name = books.get(position).getTitle();
            String description = books.get(position).getDescription();
            String image = books.get(position).getImage();
            String price = books.get(position).getPrice();
            String copies = books.get(position).getCopies();
            Book book = books.get(position);
            if(!image.isEmpty())
            {
                Picasso.get().load(image).into(holder.image);
            }

            holder.title.setText(name);
            holder.description.setText(description);
            holder.price.setText(price);
            holder.copies.setText(copies);

            holder.tts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textToSpeak = book.getDescription();
                    if (mListener != null) {
                        mListener.onTextToSpeech();
                    }
                }
            });

            holder.addtocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Order.add(book);
                }
            });
        }



    @Override
    public int getItemCount() {
        return books.size();
    }


}