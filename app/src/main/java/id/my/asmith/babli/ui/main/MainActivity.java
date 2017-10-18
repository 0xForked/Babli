package id.my.asmith.babli.ui.main;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;
import com.vistrav.ask.Ask;
import com.vistrav.ask.annotations.AskDenied;
import com.vistrav.ask.annotations.AskGranted;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.my.asmith.babli.R;
import id.my.asmith.babli.ui.auth.LoginActivity;
import id.my.asmith.babli.ui.auth.RegisterActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MaterialSearchBar.OnSearchActionListener {

    String names = "Agus Adhi Sumitro";
    String emails = "aasumito@gmail.com";
    String status = "login";

    //Get view with butterKnife Injection
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.searchBar) MaterialSearchBar searchBar;

    //OnClick ButterKnife Injection
    @OnClick(R.id.fab) void myCart (){
        Toast.makeText(MainActivity.this, "My shopping cart", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ButterKnife initialization
        ButterKnife.bind(this);
        //ButterKnife OnClick
        myCart();

        //Search Material
        searchBar.setOnSearchActionListener(this);
        searchBar.setHint("Search...");

        //Navigation Menu
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        Menu menu = navigationView.getMenu();

        //Set Navigation Menu invisible or visible
        if (Objects.equals(status, "logout")){
            MenuItem gal = menu.findItem(R.id.nav_purchase);
            gal.setVisible(false);
            MenuItem wish = menu.findItem(R.id.nav_wish);
            wish.setVisible(false);
            MenuItem share = menu.findItem(R.id.nav_contact);
            share.setVisible(false);
            MenuItem rate = menu.findItem(R.id.nav_help);
            rate.setVisible(false);
            MenuItem logout = menu.findItem(R.id.nav_logout);
            logout.setVisible(false);
            MenuItem login = menu.findItem(R.id.nav_login);
            login.setVisible(true);
            MenuItem register = menu.findItem(R.id.nav_register);
            register.setVisible(true);
        }

        //Get View from drawer header
        TextView name = (TextView) header.findViewById(R.id.txt_nav_header_name);
        TextView email = (TextView) header.findViewById(R.id.txt_nav_header_email);
        ImageView pic = (ImageView) header.findViewById(R.id.img_nav_header_pic);
        LinearLayout lay = (LinearLayout) header.findViewById(R.id.lay_nav_header_profile);
        //Layout on click on drawer header
        lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Open Profile", Toast.LENGTH_LONG).show();
            }
        });
        //Set text in drawer header
        name.setText(names);
        email.setText(emails);

        //Display Home Fragment when the activity is loaded
        displaySelectedScreen(R.id.nav_home);

        Ask.on(this)
                .forPermissions(Manifest.permission.ACCESS_COARSE_LOCATION
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE
                        , Manifest.permission.GET_ACCOUNTS
                        , Manifest.permission.ACCESS_NETWORK_STATE
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .go();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_cart) {
            //startActivity(new Intent(MainActivity.this, PhoneConfirmation.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //calling the method displayselectedscreen and passing the id of selected menu
        int id = item.getItemId();

        if (id == R.id.nav_login) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            MainActivity.this.overridePendingTransition(R.anim.slide_in_right,
                    R.anim.slide_out_left);
        }else if (id == R.id.nav_register){
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            MainActivity.this.overridePendingTransition(R.anim.slide_in_right,
                    R.anim.slide_out_left);
        }

        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;
    }

    private void displaySelectedScreen(int itemId) {
        //creating fragment object
        Fragment fragment = null;
        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_home:
                fragment = new MainHomeFragment();
                break;
            case R.id.nav_purchase:
                fragment = new MainPurchaseFragment();
                break;
            case R.id.nav_wish:
                fragment = new MainWishlistFragment();
                break;
            case R.id.nav_manage:
                fragment = new MainSettingFragment();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContent, fragment);
            ft.commit();
        }
        //close the drawer when menu pressed
        drawer.closeDrawer(GravityCompat.START);
    }

    //Search bar
    @Override
    public void onSearchStateChanged(boolean enabled) {
        Fragment fragment = new MainSerachFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flContent, fragment);
        ft.commit();

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        Toast.makeText(MainActivity.this, "Search " + text, Toast.LENGTH_SHORT).show();
    }

    //Search Bar button clicked
    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode){
            case MaterialSearchBar.BUTTON_NAVIGATION:
                drawer.openDrawer(Gravity.LEFT);
                break;
            case MaterialSearchBar.BUTTON_SPEECH:
                break;
            case MaterialSearchBar.BUTTON_BACK:
                searchBar.disableSearch();
                Fragment fragment = new MainHomeFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.flContent, fragment);
                ft.commit();
                break;
        }
    }

    //on ask grated & denied
    //optional
    @AskGranted(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void fileAccessGranted() {
        Log.i("WRITE_EXTERNAL_STORAGE", "FILE  GRANTED");
    }

    //optional
    @AskDenied(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void fileAccessDenied() {
        Log.i("WRITE_EXTERNAL_STORAGE", "FILE  DENiED");
    }

    //optional
    @AskGranted(Manifest.permission.GET_ACCOUNTS)
    public void getAccountsGranted() {
        Log.i("GET_ACCOUNTS", "MAP GRANTED");
    }

    //optional
    @AskDenied(Manifest.permission.GET_ACCOUNTS)
    public void getAccountsDenied() {
        Log.i("GET_ACCOUNTS", "MAP DENIED");
    }

}
