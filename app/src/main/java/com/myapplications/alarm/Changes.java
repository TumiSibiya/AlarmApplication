package  com.myapplications.alarm;

import android.util.Log;

public class Changes {

    private final String TAG = Changes.class.getName();

    private StringBuilder tPatten = new StringBuilder("HH:mm:ss");
    private StringBuilder dPatten = new StringBuilder("dd-MM-yyyy");


    public String gettPatten(){
        return tPatten.toString();
    }

    public void settPatten(String tPatten) {

        this.tPatten = new StringBuilder(tPatten);
    }


    public String getdPatten() {
        return dPatten.toString();
    }

    public void setdPatten(String dPatten) {

        this.dPatten = new StringBuilder(dPatten);
    }
}
