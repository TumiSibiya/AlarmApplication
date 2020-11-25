package  com.myapplication.alarm;

public class Changes {

    private final String TAG = Changes.class.getName();

    private String tPatten = "HH:mm:ss";
    private String dPatten = "dd-MM-yyyy";


    public Changes(){}

    public Changes(String tPatten){
        this.tPatten = tPatten;
    }

    public String gettPatten(){
        return tPatten;
    }

    public void settPatten(String tPatten) {

        this.tPatten = tPatten;
    }


    public String getdPatten() {
        return dPatten;
    }

    public void setdPatten(String dPatten) {

        this.dPatten = dPatten;
    }
}
