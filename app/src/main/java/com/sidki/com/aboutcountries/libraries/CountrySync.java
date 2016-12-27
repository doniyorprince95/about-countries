package com.sidki.com.aboutcountries.libraries;

import android.util.Log;

import com.sidki.aboutcountries.models.Country;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by sidki on 24/12/2016.
 */
public class CountrySync {
    public String name, capital, code, region,currency,jsonString;
    JSONObject obj;
    ArrayList<Country> items = new ArrayList<Country>();
    public String syncData(String server_url){
        URL theUrl = null;
        try {
            theUrl = new URL(server_url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(theUrl.openStream(),"UTF-8"));
            jsonString = reader.readLine();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public ArrayList fillCountriesArray(JSONArray array){
        try {
            for(int i=0; i < array.length() ; i++) {
                obj = array.getJSONObject(i);
                name = obj.get("name").toString();
                capital = obj.get("capital").toString();
                code= obj.get("alpha2Code").toString();
                region = obj.get("region").toString();
                currency = obj.get("currencies").toString();
                Country country = new Country(name, code, currency, capital,region);
                items.add(country);
            }
        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + array + "\"");
        }
        return items;

    }

}
