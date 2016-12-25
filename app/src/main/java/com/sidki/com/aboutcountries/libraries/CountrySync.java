package com.sidki.com.aboutcountries.libraries;

import android.os.AsyncTask;
import android.util.Log;

import com.sidki.aboutcountries.models.Country;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by sidki on 24/12/2016.
 */
public class CountrySync {
    ArrayList<Country> items = new ArrayList<Country>();

    public void getAllCountries(){
        DownloadJSON download = new DownloadJSON();
        download.execute();
    }
    class DownloadJSON extends AsyncTask<Void,Void,Void> {
        public static final String SERVER_URL="https://restcountries.eu/rest/v1/all";
        public String result, name, capital, code, region,currency;
        JSONObject obj;
        @Override
        protected void onPostExecute(Void aVoid) {
            //super.onPostExecute(aVoid);

            try {
                JSONArray array = new JSONArray(result);
                //JSONObject obj = new JSONObject(array.getJSONObject(0).toString());
                //Log.d("country", obj.get("name").toString() + obj.get("capital").toString() + obj.get("alpha2Code").toString());
                for(int i=0; i < array.length() ; i++) {
                    obj = array.getJSONObject(i);
                    name = obj.get("name").toString();
                    capital = obj.get("capital").toString();
                    code= obj.get("alpha2Code").toString();
                    region = obj.get("region").toString();
                    currency = obj.get("currencies").toString();
                    Country country = new Country(name, code, currency, capital,region);
                    items.add(country);
                    country.getCountry();
                }
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
