package  com.myapplications.alarm;

import android.util.Log;

public class Changes {

    private final String TAG = Changes.class.getName();

    private String tPatten = "HH:mm:ss";
    private String dPatten = "dd-MM-yyyy";


    public Changes(){}

    public Changes(String tPatten){
        this.tPatten = tPatten;
    }

    public String gettPatten(){
        return tPatten.toString();
    }

    public void settPatten(String tPatten) {

        this.tPatten = tPatten;
    }


    public String getdPatten() {
        return dPatten.toString();
    }

    public void setdPatten(String dPatten) {

        this.dPatten = dPatten;
    }
}
