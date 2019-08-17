package id.ac.umn.made_basiliusbias_submission3.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import id.ac.umn.made_basiliusbias_submission3.LangApp;
import id.ac.umn.made_basiliusbias_submission3.R;
import id.ac.umn.made_basiliusbias_submission3.fragments.MainFragment;

public class MainActivity extends LangApp implements NavigationView.OnNavigationItemSelectedListener {

    // Navigation Drawer Menu Opened
    private int DRAWER_MENU_NUMBER;

    // Loading UI
    private ImageView loading_image;
    private TextView loading_text;

    // Left NavMenu
    private DrawerLayout drawer;
    private NavigationView navigationView;

    // Custom Setting For 2x Back To Close App
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Loading UI
        loading_image = findViewById(R.id.loading_image);
        loading_text = findViewById(R.id.loading_text);

        // Show Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Navigation Drawer
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        // Navigation User Account
        ImageView userImg = navigationView.getHeaderView(0).findViewById(R.id.nav_user_img);
        TextView userName = navigationView.getHeaderView(0).findViewById(R.id.nav_user_name);
        TextView userEmail = navigationView.getHeaderView(0).findViewById(R.id.nav_user_email);

        // Setting Up Navigation Menu
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        // Get Last Drawer Position
        if (savedInstanceState != null) {

            // Have Opened Previously
            navigationView.setCheckedItem(navigationView.getMenu().findItem(savedInstanceState.getInt("DRAWER_MENU_NUMBER")));
            onNavigationItemSelected(navigationView.getMenu().findItem(savedInstanceState.getInt("DRAWER_MENU_NUMBER")));
        }
        else {

            // First-Time Open Apps
            navigationView.setCheckedItem(navigationView.getMenu().findItem(R.id.nav_discover_movie));
            onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_discover_movie));
        }

        // TODO :: Ganti Pake Data Hasil Login
        // Load User Login Info Into Nav Menu Profile
        Glide.with(this)
            .load(R.mipmap.maido2)
            .transition(DrawableTransitionOptions.withCrossFade(1234)) // Transition Effect Load Image
            .apply(
                    RequestOptions.circleCropTransform().override(85, 85)
            )
            .placeholder(R.mipmap.maido)
            .into(userImg);
        userName.setText(R.string.nav_user_name);
        userEmail.setText(R.string.nav_user_email);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        // Save Number Position Drawer Menu
        outState.putInt("DRAWER_MENU_NUMBER", DRAWER_MENU_NUMBER);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onBackPressed() {

        // When NavMenu Opened
        if (drawer.isDrawerOpen(GravityCompat.START)) {

            // Close It First
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (doubleBackToExitPressedOnce) {

            // 2x Back Button Pressed To Close App
            super.onBackPressed();
        }
        else {

            // In Other Menu Except Menu 1
            if (!navigationView.getMenu().findItem(R.id.nav_discover_movie).isChecked()) {

                // Back To Menu 1 First
                navigationView.setCheckedItem(R.id.nav_discover_movie);
                onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_discover_movie));
            }
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.main_back_pressed, Toast.LENGTH_SHORT).show();

        // Handler Back Button Checker Can Close App Within 1 Second On 2x Press
        new Handler().postDelayed(() -> {

            // Reset Condition
            doubleBackToExitPressedOnce = false;
        }, 1234);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent;

        // Noinspection Simplifiable If Statement
        if (id == R.id.action_main_settings) {

            // Go To About Activity
            intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Loading Information
        loading_image.setVisibility(View.VISIBLE);
        loading_text.setVisibility(View.VISIBLE);

        // Show Loading Animation
        Glide.with(this)
                .load(getResources().getString(R.string.animated_loading_data))
                .transition(DrawableTransitionOptions.withCrossFade(125))
                .placeholder(R.mipmap.maido)
                .override(256, 256)
                .into(loading_image)
        ;
        loading_text.setText(R.string.loading_text);
        loading_text.setTextColor(Color.GREEN);

        // Drawer Menu
        if (id == R.id.nav_discover_movie) {

            // Show Movies Fragment
            if (getSupportActionBar() != null) getSupportActionBar().setTitle(getResources().getString(R.string.nav_discover) + " -- " + getResources().getString(R.string.nav_movie));
            fragmentManager.beginTransaction().replace(
                R.id.frame_main,
                MainFragment.newInstance("DiscoverMovie","GridLayout")).commit();
        }
        else if(id == R.id.nav_discover_tv_shows) {

            // Show Tv Shows Fragment
            if (getSupportActionBar() != null) getSupportActionBar().setTitle(getResources().getString(R.string.nav_discover) + " -- " + getResources().getString(R.string.nav_tv_shows));
            fragmentManager.beginTransaction().replace(
                R.id.frame_main,
                MainFragment.newInstance("DiscoverTV", "LinearLayout")).commit();
        }

        // TODO :: Fitur Menu Lainnya
        else if(id == R.id.nav_movies_popular) {

            // Show Tv Shows Fragment
            if (getSupportActionBar() != null) getSupportActionBar().setTitle(getResources().getString(R.string.nav_movie) + " -- " + getResources().getString(R.string.nav_movie_popular));
            fragmentManager.beginTransaction().replace(
                R.id.frame_main,
                MainFragment.newInstance("ComingSoon", null)
            ).commit();
        }
        else if(id == R.id.nav_movies_top_rated) {

            // Show Tv Shows Fragment
            if (getSupportActionBar() != null) getSupportActionBar().setTitle(getResources().getString(R.string.nav_movie) + " -- " + getResources().getString(R.string.nav_movie_top_rated));
            fragmentManager.beginTransaction().replace(
                R.id.frame_main,
                MainFragment.newInstance("ComingSoon", null)
            ).commit();
        }
        else if(id == R.id.nav_movies_upcoming) {

            // Show Tv Shows Fragment
            if (getSupportActionBar() != null) getSupportActionBar().setTitle(getResources().getString(R.string.nav_movie) + " -- " + getResources().getString(R.string.nav_movie_upcoming));
            fragmentManager.beginTransaction().replace(
                R.id.frame_main,
                MainFragment.newInstance("ComingSoon", null)
            ).commit();
        }
        else if(id == R.id.nav_movies_now_playing) {

            // Show Tv Shows Fragment
            if (getSupportActionBar() != null) getSupportActionBar().setTitle(getResources().getString(R.string.nav_movie) + " -- " + getResources().getString(R.string.nav_movie_now_playing));
            fragmentManager.beginTransaction().replace(
                R.id.frame_main,
                MainFragment.newInstance("ComingSoon", null)
            ).commit();
        }

        else if(id == R.id.nav_tv_shows_popular) {

            // Show Tv Shows Fragment
            if (getSupportActionBar() != null) getSupportActionBar().setTitle(getResources().getString(R.string.nav_tv_shows) + " -- " + getResources().getString(R.string.nav_tv_popular));
            fragmentManager.beginTransaction().replace(
                R.id.frame_main,
                MainFragment.newInstance("ComingSoon", null)
            ).commit();
        }
        else if(id == R.id.nav_tv_shows_top_rated) {

            // Show Tv Shows Fragment
            if (getSupportActionBar() != null) getSupportActionBar().setTitle(getResources().getString(R.string.nav_tv_shows) + " -- " + getResources().getString(R.string.nav_tv_top_rated));
            fragmentManager.beginTransaction().replace(
                R.id.frame_main,
                MainFragment.newInstance("ComingSoon", null)
            ).commit();
        }
        else if(id == R.id.nav_tv_shows_on_tv) {

            // Show Tv Shows Fragment
            if (getSupportActionBar() != null) getSupportActionBar().setTitle(getResources().getString(R.string.nav_tv_shows) + " -- " + getResources().getString(R.string.nav_tv_on_tv));
            fragmentManager.beginTransaction().replace(
                R.id.frame_main,
                MainFragment.newInstance("ComingSoon", null)
            ).commit();
        }
        else if(id == R.id.nav_tv_shows_airing_today) {

            // Show Tv Shows Fragment
            if (getSupportActionBar() != null) getSupportActionBar().setTitle(getResources().getString(R.string.nav_tv_shows) + " -- " + getResources().getString(R.string.nav_tv_airing_today));
            fragmentManager.beginTransaction().replace(
                R.id.frame_main,
                MainFragment.newInstance("ComingSoon", null)
            ).commit();
        }

        else if(id == R.id.nav_about) {

            // Remove Loading Perk
            loading_image.setVisibility(View.GONE);
            loading_text.setVisibility(View.GONE);

            // Go To About Activity
            intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        }

        // Save To Preferences Last Opened Menu
        DRAWER_MENU_NUMBER = item.getItemId();

        // Close The Drawer
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
