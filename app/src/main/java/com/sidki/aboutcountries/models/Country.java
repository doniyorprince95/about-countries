package com.sidki.aboutcountries.models;

import android.util.Log;

/**
 * Created by sidki on 23/12/2016.
 */
public class Country {
    private String countryName, countryCode, currency, capital, region;

    public Country (String countryName, String countryCode, String currency, String capital, String region){
        this.countryName = countryName;
        this.countryCode = countryCode;
        this.currency = currency;
        this.capital = capital;
        this.region = region;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCapital() {
        return capital;
    }

    public String getRegion() {
        return region;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public void setRegion(String region) {
        this.region = region;
    }
    public void getCountry(){
        Log.d("jsontag","This is:" + this.countryName + " and its capital is:" + this.capital);
    }
}
