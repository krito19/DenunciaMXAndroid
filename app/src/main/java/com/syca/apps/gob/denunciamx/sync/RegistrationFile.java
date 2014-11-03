package com.syca.apps.gob.denunciamx.sync;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import com.syca.apps.gob.denunciamx.R;

/**
 * Created by JARP on 11/3/14.
 */
public class RegistrationFile {

    private Context mContext;

    String mFilName;

    private SharedPreferences mSharedPreferences;

    boolean deleteKey = false;

    String tokenKey;

    private void init (Context context)
    {
        mContext=context;

        mFilName = mContext.getString(R.string.preference_file);

        mSharedPreferences = mContext.getSharedPreferences(mFilName,context.MODE_PRIVATE);
        if(Build.VERSION.SDK_INT>9)
            mSharedPreferences= mContext.getSharedPreferences(mFilName,context.MODE_MULTI_PROCESS);

        mSharedPreferences= PreferenceManager.getDefaultSharedPreferences(mContext);


    }

    public RegistrationFile(Context context)
    {
        init(context);

    }

    public RegistrationFile(Context context, boolean deleteKey)
    {
        init(context);

        if(deleteKey)
            mSharedPreferences.edit().putBoolean(mContext.getString(R.string.is_register_in_service),false).commit();
    }

    public void  writeToken(String token)
    {
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(mContext.getString(R.string.is_register_in_service),true);
        mEditor.putString(mContext.getString(R.string.registerToken),token);
        mEditor.commit();
    }

    public boolean isRegister() {
        return mSharedPreferences.getBoolean(mContext.getString(R.string.is_register_in_service),false);
    }

    public String getTokenKey()
    {
        return mSharedPreferences.getString(mContext.getString(R.string.registerToken),null);
    }
}
