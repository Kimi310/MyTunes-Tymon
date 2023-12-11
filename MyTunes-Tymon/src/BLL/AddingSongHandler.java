package BLL;

import javafx.scene.control.TextField;

public class AddingSongHandler {

    public String[] textFieldsToString (javafx.scene.control.TextField titletxt, TextField artisttxt, TextField categorytxt, TextField timetxt , TextField filetxt) {
        String[] s = new String[5];
        s[0] = titletxt.getText();
        s[1] = artisttxt.getText();
        s[2] = categorytxt.getText();
        s[3] = timetxt.getText();
        s[4] = filetxt.getText();
        return s;
    }
    public boolean checkNewSong (String[] s){
        if (s[0].isEmpty() || s[1].isEmpty() || s[2].isEmpty() || s[3].isEmpty() || s[4].isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    public AddingSongHandler() {
    }
}
