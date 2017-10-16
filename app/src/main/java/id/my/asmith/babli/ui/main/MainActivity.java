package id.my.asmith.babli.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.my.asmith.babli.R;
import id.my.asmith.babli.ui.auth.LoginActivity;
import id.my.asmith.babli.ui.auth.RegisterActivity;
import id.my.asmith.babli.ui.splash.SplashActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String names = "Agus Adhi Sumitro";
    String emails = "aasumito@gmail.com";
    String status = "logout";

    //Get view with butterKnife Injection
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ButterKnife initialization
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Drawer Menu
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

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
                Toast.makeText(MainActivity.this, "Buka Profile", Toast.LENGTH_LONG).show();
            }
        });
        //Set text in drawer header
        name.setText(names);
        email.setText(emails);

        //Display Home Fragment when the activity is loaded
        displaySelectedScreen(R.id.nav_home);

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
        getMenuInflater().inflate(R.menu.main, menu);
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
}
