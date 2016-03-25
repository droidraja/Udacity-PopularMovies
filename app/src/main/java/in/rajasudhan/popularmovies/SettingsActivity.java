package in.rajasudhan.popularmovies;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by raja sudhan on 3/26/2016.
 */
public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_preference);
    }
}
