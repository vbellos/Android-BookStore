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
import com.vbellos.dev.bookstore.widgets.HorizontalNumberPicker;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, price,total;
        private Button remove;
        private ImageView image;
        private HorizontalNumberPicker orderquantity;


        public ViewHolder(final View view){
            super(view);
            title =(TextView) view.findViewById(R.id.ordertitle);
            total =(TextView) view.findViewById(R.id.ordertotal);
            price =(TextView) view.findViewById(R.id.orderprice);

            image =( ImageView)view.findViewById(R.id.orderimage);
            remove = (Button) view.findViewById(R.id.orderbutton);
            orderquantity =(HorizontalNumberPicker) view.findViewById(R.id.ordernumberpicker);

        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.control_order,parent,false);

        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull CartRecyclerViewAdapter.ViewHolder holder, int position) {
        Book book = Order.getBooks().get(position);
       String title = book.getTitle();
       String image = book.getImage();
       String price = book.getPrice();
       int copies = Order.getCopies().get(position);
       Float total = Order.getPrice().get(position);

       holder.orderquantity.setMin(0);
       holder.orderquantity.setValue(copies);
       holder.orderquantity.setMax(Integer.parseInt(book.getCopies()));

       holder.title.setText(title);
       holder.total.setText(total.toString());
       holder.price.setText(price);

        if(!image.isEmpty())
        {
            Picasso.get().load(image).into(holder.image);
        }

       holder.orderquantity.setValueChangedEventListener(new HorizontalNumberPicker.ValueChangedEventListener() {
           @Override
           public void onValueChanged() {
               Order.updatequantity(book,holder.orderquantity.getValue());
           }
       });

       holder.remove.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Order.remove(book);
           }
       });

    }

    @Override
    public int getItemCount() {
        return Order.getBooks().size();
    }



}
