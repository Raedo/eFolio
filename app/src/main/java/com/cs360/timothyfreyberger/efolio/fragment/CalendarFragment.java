package com.cs360.timothyfreyberger.efolio.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cs360.timothyfreyberger.efolio.R;
import com.cs360.timothyfreyberger.efolio.other.Contact;
import com.cs360.timothyfreyberger.efolio.other.ContactAdapter;
import com.cs360.timothyfreyberger.efolio.other.DatabaseHelper;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class CalendarFragment extends Fragment {

    private RecyclerView contacts;
    private RecyclerView.Adapter adapter;
    private Context thisContext;

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);
        thisContext = container.getContext();

        ArrayList<Contact> list = initList();

        this.contacts = (RecyclerView) root.findViewById(R.id.contacts);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(thisContext);
        this.contacts.setLayoutManager(mLayoutManager);

        adapter = new ContactAdapter(list, thisContext);
        this.contacts.setAdapter(adapter);

        contacts.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        return root;
    }

    private ArrayList<Contact> initList() {
        ArrayList<Contact> list = new ArrayList<>();
        DatabaseHelper myDB = new DatabaseHelper(thisContext);
        Cursor res = myDB.getSortedByDate();
        Contact temp;

        while(res.moveToNext()) {
            temp = new Contact(res.getString(1), res.getString(2), res.getString(3),
                    res.getString(4), res.getString(5), res.getString(6));
            list.add(temp);
            Toast.makeText(getContext(),temp.getDate(),Toast.LENGTH_SHORT);
        }

        return list;
    }

}
