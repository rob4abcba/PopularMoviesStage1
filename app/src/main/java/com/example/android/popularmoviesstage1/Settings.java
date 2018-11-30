package com.example.android.popularmoviesstage1;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class Settings extends PreferenceActivity implements Preference.OnPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_preference);
        setPreference(findPreference(getString(R.string.shared_pref_sort_key)));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String stringPref = newValue.toString();
        if(preference  instanceof ListPreference) {
            ListPreference listPref = (ListPreference) preference;
            int prefIndex = listPref.findIndexOfValue(stringPref);
            if(prefIndex >=0) {
                preference.setSummary(listPref.getEntries()[prefIndex]);
            }
        } else {
            preference.setSummary(stringPref);
        }
        return true;
    }

    // Set preference change listener when the user selects their preference
    private void setPreference(Preference preference) {
        preference.setOnPreferenceChangeListener(this);
        onPreferenceChange(preference, PreferenceManager.getDefaultSharedPreferences(preference.getContext())
        .getString(preference.getKey(),""));
    }
}