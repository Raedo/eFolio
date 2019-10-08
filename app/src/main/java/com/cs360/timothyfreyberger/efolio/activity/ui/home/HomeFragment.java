package com.cs360.timothyfreyberger.efolio.activity.ui.home;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cs360.timothyfreyberger.efolio.R;
import com.cs360.timothyfreyberger.efolio.other.DatabaseHelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    DatabaseHelper myDb;
    EditText editId, editName, editAddress, editPhone, editEmail, editDate, editTime;
    Button btnAddData;
    Button btnViewAll;
    Button btnUpdate;
    Button btnDelete;
    Button btnGallery;
    Button btnGetMap;

    Context thisContext;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        thisContext = container.getContext();

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        myDb = new DatabaseHelper(thisContext);

        editId = (EditText)root.findViewById(R.id.editId);
        editName = (EditText)root.findViewById(R.id.editName);
        editAddress = (EditText)root.findViewById(R.id.editAddress);
        editPhone = (EditText)root.findViewById(R.id.editPhone);
        editEmail = (EditText)root.findViewById(R.id.editEmail);
        editDate = (EditText)root.findViewById(R.id.editDate);
        editTime = (EditText)root.findViewById(R.id.editTime);
        btnAddData = (Button)root.findViewById(R.id.buttonAdd);
        btnViewAll = (Button)root.findViewById(R.id.btnViewAll);
        btnUpdate = (Button)root.findViewById(R.id.btnUpdate);
        btnDelete = (Button)root.findViewById(R.id.btnDelete);

//      btnGallery = (Button)root.findViewById(R.id.btnGallery);
//      btnGetMap = (Button)root.findViewById(R.id.btnGetMap);

        //methods used to add, edit, delete or view the database.
        DeleteData();
        UpdateDate();
        AddData();
        viewAll();

        //methods that open other activities in the app
//        openGallery();
//        openMap();

        return root;
    }

    /*
     * This method will delete data based on a given ID number
     */
    public void DeleteData() {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Integer deletedRows = myDb.deleteData(editId.getText().toString());

                        if (deletedRows > 0)
                            Toast.makeText(thisContext,"Contact Deleted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(thisContext,"Error Deleting Contact", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    /*
     * This method uses an ID number to update data already existing in the database
     */
    public void UpdateDate() {
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isUpdated = myDb.updateData(editId.getText().toString(),
                                editName.getText().toString(),
                                editAddress.getText().toString(),
                                editPhone.getText().toString(),
                                editEmail.getText().toString(),
                                editDate.getText().toString(),
                                editTime.getText().toString());

                        if (isUpdated)
                            Toast.makeText(thisContext,"Contact Updated Successfully", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(thisContext,"Error Updating Contact", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    /*
     * This method adds a new contact to the database. It ignores the ID spot and automatically sets
     * a unique ID
     */
    public void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isInserted = myDb.insertData(editName.getText().toString(),
                                editAddress.getText().toString(),
                                editPhone.getText().toString(),
                                editEmail.getText().toString(),
                                editDate.getText().toString(),
                                editTime.getText().toString());

                        if (isInserted)
                            Toast.makeText(thisContext,"Contact Added Successfully", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(thisContext,"Error Adding Contact", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    /*
     * This method allows the user to view the entire database which is the contact list.
     */
    public void viewAll() {
        btnViewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor res = myDb.getAllData();
                        if(res.getCount() == 0) {
                            // show message
                            showMessage("Error", "Nothing Found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while(res.moveToNext()) {
                            buffer.append("ID : " + res.getString(0) + "\n");
                            buffer.append("Name : " + res.getString(1) + "\n");
                            buffer.append("Address : " + res.getString(2) + "\n");
                            buffer.append("Phone : " + res.getString(3) + "\n");
                            buffer.append("Email : " + res.getString(4) + "\n");
                            buffer.append("Date : " + res.getString(5) + "\n");
                            buffer.append("Time : " + res.getString(6) + "\n\n");
                        }

                        //Show all data
                        showMessage("Contacts", buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(thisContext);

        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}