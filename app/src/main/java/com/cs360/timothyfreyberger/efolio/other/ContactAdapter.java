package com.cs360.timothyfreyberger.efolio.other;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cs360.timothyfreyberger.efolio.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private ArrayList<Contact> contacts;
    private Context context;

    public ContactAdapter(ArrayList<Contact> contacts, Context context) {
        this.contacts = contacts;
        this.context = context;
    }

    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ViewHolder holder, final int position) {


        final Contact contact = contacts.get(position);

        holder.date.setText(contact.getDate());
        holder.time.setText(contact.getTime());
        holder.name.setText(contact.getName());
        holder.address.setText(contact.getAddress());
        holder.phone.setText(contact.getPhone());
        holder.email.setText(contact.getEmail());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent added = null;
                try {
                    added = AddEvent.addEvent(contact);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (added != null) {
                    context.startActivity(added);
                    Toast.makeText(context, "Event Created for " + contact.getName(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Event Failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (contacts != null) {
            return contacts.size();
        } else {
            return 0;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView name;
        public final TextView address;
        public final TextView phone;
        public final TextView email;
        public final TextView date;
        public final TextView time;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            name = view.findViewById(R.id.name);
            address = view.findViewById(R.id.address);
            phone = view.findViewById(R.id.phone);
            email = view.findViewById(R.id.email);
            date = view.findViewById(R.id.date);
            time = view.findViewById(R.id.time);
        }

    }
}
