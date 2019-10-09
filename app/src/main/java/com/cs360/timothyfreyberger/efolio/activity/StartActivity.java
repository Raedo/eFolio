package com.cs360.timothyfreyberger.efolio.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cs360.timothyfreyberger.efolio.R;
import com.cs360.timothyfreyberger.efolio.fragment.CalendarFragment;
import com.cs360.timothyfreyberger.efolio.fragment.GalleryFragment;
import com.cs360.timothyfreyberger.efolio.fragment.MapFragment;
import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class StartActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private FragmentManager fm;
    private DrawerLayout drawer;
    private static GoogleSignInAccount account;
    private int requestCode;
    private int grantResults[];


    protected void onStart() {
        super.onStart();

    }

    public void updateUI(GoogleSignInAccount account) {
        GoogleSignInClient mGoogleSignInClient;

        if (account == null) {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .requestScopes(new Scope( "https://www.googleapis.com/auth/calendar.events"))
                    .build();

            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

            Intent signInIntent = mGoogleSignInClient.getSignInIntent();

            startActivityForResult(signInIntent, 101);
        } else {
            this.account = account;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case 101:
                    try {
                        // The Task returned from this call is always completed, no need to attach
                        // a listener.
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        onLoggedIn(account);
                    } catch (ApiException e) {
                        // The ApiException status code indicates the detailed failure reason.
                        e.printStackTrace();
                        Toast.makeText(this, "fail", Toast.LENGTH_LONG).show();
                    }
                    break;
            }
    }

    public void onLoggedIn(GoogleSignInAccount googleSignInAccount) {
        this.account = googleSignInAccount;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
        FacebookSdk.sdkInitialize(getApplicationContext());

        ActivityCompat.requestPermissions(this,  new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},requestCode);
        onRequestPermissionsResult(requestCode,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},grantResults);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);
        TextView textName = (TextView) headerView.findViewById(R.id.userName);
        TextView textEmail = (TextView) headerView.findViewById(R.id.userEmail);

        if (this.account != null) {
            textName.setText(this.account.getDisplayName());
            textEmail.setText(this.account.getEmail());
        }

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_map,
                R.id.nav_calendar, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener((new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                selectDrawerItem(menuItem);
                drawer.closeDrawers();
                return true;
            }
        }));

        //sets the opening fragment
        Class starter =  com.cs360.timothyfreyberger.efolio.fragment.ContactFragment.class;

        fm = getSupportFragmentManager();
        Fragment f = null;
        try {
            f = (Fragment) starter.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        fm.beginTransaction().replace(R.id.nav_host_fragment, f).commit();
    }

    @Override // android recommended class to handle permissions
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.d("permission", "granted");
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.uujm
                    Toast.makeText(this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();

                    //app cannot function without this permission for now so close it...
                    onDestroy();
                }
                return;
            }
        }
    }


            public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;
        Class fragmentClass;

        switch(menuItem.getItemId()) {
            case R.id.nav_home:
                fragmentClass = com.cs360.timothyfreyberger.efolio.fragment.ContactFragment.class;
                break;
            case R.id.nav_gallery:
                fragmentClass = GalleryFragment.class;
                break;
            case R.id.nav_map:
                fragmentClass = MapFragment.class;
                break;
            case R.id.nav_calendar:
                fragmentClass = CalendarFragment.class;
                break;
            default:
                fragmentClass = com.cs360.timothyfreyberger.efolio.ui.home.HomeFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch ( Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();

        for (int i=0; i<fragmentManager.getBackStackEntryCount(); i++){
            fragmentManager.popBackStack();
        }


        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();

        getSupportActionBar().setTitle(menuItem.getTitle());


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(Gravity.START);
                return true;
            case R.id.action_logout:
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .requestScopes(new Scope( "https://www.googleapis.com/auth/calendar.events"))
                        .build();
                GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
                mGoogleSignInClient.signOut();
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start, menu);
        return true;
    }

}
