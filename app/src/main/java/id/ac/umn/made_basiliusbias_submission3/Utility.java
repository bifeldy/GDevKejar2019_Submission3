package id.ac.umn.made_basiliusbias_submission3;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v4.os.ConfigurationCompat;
import android.support.v7.preference.PreferenceManager;
import android.util.DisplayMetrics;

import java.util.Locale;

public class Utility {

    public static int calcNumOfCols(Context context, float colWidthDp) {

        // For example column Width dp = 180
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;

        // +0.5 for correct rounding to int.
        return (int) (screenWidthDp / colWidthDp + 0.5);
    }

    // https://stackoverflow.com/questions/39705739/android-n-change-language-programmatically
    static ContextWrapper changeLanguage(Context context, String lang_code) {

        Resources rs = context.getResources();
        Configuration config = rs.getConfiguration();
        Locale locale = new Locale(lang_code);

        Locale.setDefault(locale);
        config.setLocale(locale);

        return new ContextWrapper(context.createConfigurationContext(config));
    }

    public static String getSystemLocale() {
        return ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).toLanguageTags();
    }

    public static String getAppLanguage(Context context) {

        // Get Language Shared Preference
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(
                context.getResources().getString(R.string.pref_language_list_key),
                Utility.getSystemLocale()
        );
    }
}