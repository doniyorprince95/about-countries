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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
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
        DownloadJSON downloadJSON = new DownloadJSON();
        downloadJSON.execute();

    }

    public void getAllCountries(){
        DownloadJSON download = new DownloadJSON();
        download.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class DownloadJSON extends AsyncTask<Void,Void,Void> {
        public static final String SERVER_URL="https://restcountries.eu/rest/v1/all";
        public String result;
        @Override
        protected void onPostExecute(Void aVoid) {
            //super.onPostExecute(aVoid);
            try {
                JSONArray array = new JSONArray(result);
                CountrySync sync = new CountrySync();
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
            try {
                URL theUrl = new URL(SERVER_URL);
                BufferedReader reader = new BufferedReader(new InputStreamReader(theUrl.openStream(),"UTF-8"));
                String jsonString = reader.readLine();
                result = jsonString;

            }catch (MalformedURLException e){

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
