package com.sidki.aboutcountiries;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sidki.com.aboutcountries.libraries.CountrySync;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout myDrawer;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    String[] countries;
    ListView country_list;
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

        Resources r = getResources();
        countries = r.getStringArray(R.array.countries);
        CountriesAdapter ca = new CountriesAdapter(this, countries, images);
        country_list.setAdapter(ca);
        CountrySync sync = new CountrySync();
        sync.getAllCountries();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    class CountryHolder{
        ImageView countryImg;
        TextView countryName;

        CountryHolder(View v){
            countryImg = (ImageView)v.findViewById(R.id.country_image);
            countryName = (TextView)v.findViewById(R.id.country_name);
        }
    }
    class CountriesAdapter extends ArrayAdapter<String>{
        Context context;
        int[] countryImages;
        String[] countryArray;
        public CountriesAdapter(Context c, String[] countries, int[] images){
            super(c, R.layout.elem_pays, R.id.country_name, countries);
            this.context = c;
            this.countryArray= countries;
            this.countryImages = images;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View row = convertView;
            CountryHolder holder = null;
            if(row ==null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.elem_pays,parent,false);
                holder = new CountryHolder(row);
                row.setTag(holder);
            }else{
                holder = (CountryHolder) row.getTag();
            }

            holder.countryImg.setImageResource(countryImages[position]);
            holder.countryName.setText(countryArray[position]);
            return row;
        }
    }
}
