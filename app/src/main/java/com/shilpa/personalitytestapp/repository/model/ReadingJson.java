package com.shilpa.personalitytestapp.repository.model;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class ReadingJson {

    private Context mContext;

    public ReadingJson(Context context) {
        mContext = context;
    }

    public String readJSONFromAsset() {

        String json = null;
        try {
            InputStream is = mContext.getAssets().open("data/data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (
                IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }

}
