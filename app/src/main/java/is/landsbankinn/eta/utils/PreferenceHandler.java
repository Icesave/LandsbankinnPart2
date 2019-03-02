package is.landsbankinn.eta.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import is.landsbankinn.eta.models.User;

public class PreferenceHandler {

    private final String USER_NAME = "USER_NAME";
    private final String USER_PASS = "USER_PASS";
    private final String USER_EMAIL = "USER_EMAIL";
    private final String USER_TYPE = "USER_TYPE";
    private final String USER_LOGGED_IN = "USER_LOGGED_IN";

    private Context context;

    public PreferenceHandler(Context context) {
        this.context = context;
    }


    private SharedPreferences getPreferences(){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Returns the default preference editor.
     */
    private SharedPreferences.Editor getEditor() {
        return getPreferences().edit();
    }

    /**
     * Fetches the requested value for a key, returns null if it doesn't exist.
     */
    private String getString(String key) {
        return getPreferences().getString(key, null);
    }

    /**
     * Sets the string value for the requested key.
     */
    private void setString(String key, String value) {
        SharedPreferences.Editor editor = getEditor();
        editor.putString(key, value);
        editor.apply();
    }


    /**
     * Fetches the requested value for a key, returns null if it doesn't exist.
     */
    private boolean getBoolean(String key) {
        return getPreferences().getBoolean(key, false);
    }

    /**
     * Sets the boolean value for the requested key.
     */
    private void setBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = getEditor();
        editor.putBoolean(key, value);
        editor.apply();
    }


    public void setUserName(String username) {
        setString(USER_NAME, username);
    }

    public void setUserPassword(String pass) {
        setString(USER_PASS, pass);
    }

    public void setUserEmail(String email) {
        setString(USER_EMAIL, email);
    }

    public void setUserType(String type) {
        setString(USER_TYPE, type);
    }

    public String getUserName() {
        return getString(USER_NAME);
    }

    public String getUserPassword() {
        return getString(USER_PASS);
    }

    public String getUserType() {
        return getString(USER_TYPE);
    }
    public String getUserEmail() {
        return getString(USER_EMAIL);
    }

    /**
     * Save that the user has logged in
     */
    public void userHasLoggedIn() {
        setBoolean(USER_LOGGED_IN, true);
    }

    /**
     * Checks if the user is logged in
     */
    public boolean isUserLoggedIn() {
        return getBoolean(USER_LOGGED_IN);
    }
}
