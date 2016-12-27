package com.sidki.aboutcountiries;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.sidki.aboutcountries.models.Country;
import com.sidki.com.aboutcountries.libraries.CountrySync;

import org.json.JSONArray;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout myDrawer;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    ArrayList countries;
    ListView country_list;
    ArrayList<Country> items = new ArrayList<Country>();

    int[] images = {R.drawable.countries, R.mipmap.ic_account_circle_black_24dp, R.mipmap.ic_home_black_24dp};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        country_list = (ListView)findViewById(R.id.listView);
        toggle = new ActionBarDrawerToggle(this, myDrawer, R.string.open, R.string.close);
        toolbar = (Toolbar) findViewById(R.id.nav_bar);
        setSupportActionBar(toolbar);
        myDrawer.setDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getAllCountries();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getAllCountries(){
        DownloadJSON download = new DownloadJSON();
        download.execute();
    }

    class DownloadJSON extends AsyncTask<Void,Void,Void> {
        public String server_url="https://restcountries.eu/rest/v1/all";
        public String result;
        CountrySync sync = new CountrySync();

        @Override
        protected void onPostExecute(Void aVoid) {
            //super.onPostExecute(aVoid);
            try {
                JSONArray array = new JSONArray(result);
                Resources r = getResources();
                countries = sync.fillCountriesArray(array);
                CountriesAdapter ca = new CountriesAdapter(MainActivity.this, countries, images);
                country_list.setAdapter(ca);
            } catch (Throwable t) {
                Log.e("My App", "Could not parse malformed JSON: \"" + result + "\"");
            }
        }
        @Override
        protected Void doInBackground(Void... params) {
            result = sync.syncData(server_url);
            return null;
        }
    }
}
