package  com.myapplications.alarm;

import android.util.Log;

public class Changes {

    private final String TAG = Changes.class.getName();

    private String tPatten = "HH:mm:ss";
    private String dPatten = "dd-MM-yyyy";


    public String gettPatten() {
        return tPatten;
    }

    public void settPatten(String tPatten) {

        this.tPatten = tPatten;
        Log.d(TAG, "...........tPatten " + tPatten);
    }


    public String getdPatten() {
        return dPatten;
    }

    public void setdPatten(String dPatten) {
        Log.d(TAG, "...........dPatten " + dPatten);
        this.dPatten = dPatten;
    }
}
