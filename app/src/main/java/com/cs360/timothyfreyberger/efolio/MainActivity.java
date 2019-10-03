package com.cs360.timothyfreyberger.efolio;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editId, editName, editAddress, editPhone, editEmail, editDate, editTime;
    Button btnAddData;
    Button btnViewAll;
    Button btnUpdate;
    Button btnDelete;
    Button btnGallery;
    Button btnGetMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editId = (EditText)findViewById(R.id.editId);
        editName = (EditText)findViewById(R.id.editName);
        editAddress = (EditText)findViewById(R.id.editAddress);
        editPhone = (EditText)findViewById(R.id.editPhone);
        editEmail = (EditText)findViewById(R.id.editEmail);
        editDate = (EditText)findViewById(R.id.editDate);
        editTime = (EditText)findViewById(R.id.editTime);
        btnAddData = (Button)findViewById(R.id.buttonAdd);
        btnViewAll = (Button)findViewById(R.id.btnViewAll);
        btnUpdate = (Button)findViewById(R.id.btnUpdate);
        btnDelete = (Button)findViewById(R.id.btnDelete);

        btnGallery = (Button)findViewById(R.id.btnGallery);
        btnGetMap = (Button)findViewById(R.id.btnGetMap);

        //methods used to add, edit, delete or view the database.
        DeleteData();
        UpdateDate();
        AddData();
        viewAll();

        //methods that open other activities in the app
        openGallery();
        openMap();

    }

    /*
     * This method opens the portfolio gallery
     */
    public void openGallery() {
        btnGallery.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(MainActivity.this, Gallery.class));
                    }
                }
        );
    }

    /*
     * This method opens the map application to an address
     * TODO: integrate with a search method so that it can find any address in the database
     */
    public void openMap() {
        btnGetMap.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // URI used to create the Intent.
                        Uri gmmIntentUri = Uri.parse("geo:0,0?q=3900 Great Plains Dr S, Fargo, ND 58104");

                        // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        // Make the Intent explicit by setting the Google Maps package
                        mapIntent.setPackage("com.google.android.apps.maps");

                        // Attempt to start an activity that can handle the Intent
                        startActivity(mapIntent);

                    }
                }
        );
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
                             Toast.makeText(MainActivity.this,"Contact Deleted", Toast.LENGTH_LONG).show();
                         else
                             Toast.makeText(MainActivity.this,"Error Deleting Contact", Toast.LENGTH_LONG).show();
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
                            Toast.makeText(MainActivity.this,"Contact Updated Successfully", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Error Updating Contact", Toast.LENGTH_LONG).show();
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
                            Toast.makeText(MainActivity.this,"Contact Added Successfully", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Error Adding Contact", Toast.LENGTH_LONG).show();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
