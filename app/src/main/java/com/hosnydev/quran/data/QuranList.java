package com.hosnydev.quran.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.hosnydev.quran.R;
import com.hosnydev.quran.model.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class QuranList {

    private Context context;
    private List<String> list;

    /**
     * Constructor
     *
     * @param context currentContext
     */

    public QuranList(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    /**
     * Method to read lines in .txt file and store in list
     */
    public List<String> getList() {
        if (context != null) {

            InputStream is = context.getResources().openRawResource(getFile(1));
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            try {
                while ((line = br.readLine()) != null) {
                    list.add(line);
                }
            } catch (
                    IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public List<String> getList(int srcFile) {
        if (context != null) {

            InputStream is = context.getResources().openRawResource(srcFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            try {
                while ((line = br.readLine()) != null) {
                    list.add(line);
                }
            } catch (
                    IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }


    public int getFile(int notyOrNot) {

        int sharedPref;
        int serFile = 0;

        if (notyOrNot == 1)
            sharedPref = context.getSharedPreferences("quranNum", Context.MODE_PRIVATE).getInt("num", 0);
        else {
            sharedPref = context.getSharedPreferences("notification", Context.MODE_PRIVATE).getInt("srcFile", 0);
            if (sharedPref>=113){
                context.getSharedPreferences("notification", MODE_PRIVATE)
                        .edit()
                        .putInt("srcFile", 0)
                        .putInt("positionName", 0)
                        .apply();
                return R.raw.q1;
            }
        }
        if (sharedPref == -1) {
            serFile = R.raw.q1;
            return serFile;
        } else {
            switch (sharedPref) {
                case 0:
                    serFile = R.raw.q1;
                    break;
                case 1:
                    serFile = R.raw.q2;
                    break;
                case 2:
                    serFile = R.raw.q3;
                    break;
                case 3:
                    serFile = R.raw.q4;
                    break;
                case 4:
                    serFile = R.raw.q5;
                    break;
                case 5:
                    serFile = R.raw.q6;
                    break;
                case 6:
                    serFile = R.raw.q7;
                    break;
                case 7:
                    serFile = R.raw.q8;
                    break;
                case 8:
                    serFile = R.raw.q9;
                    break;
                case 9:
                    serFile = R.raw.q10;
                    break;
                case 10:
                    serFile = R.raw.q11;
                    break;
                case 11:
                    serFile = R.raw.q12;
                    break;
                case 12:
                    serFile = R.raw.q13;
                    break;
                case 13:
                    serFile = R.raw.q14;
                    break;
                case 14:
                    serFile = R.raw.q15;
                    break;
                case 15:
                    serFile = R.raw.q16;
                    break;
                case 16:
                    serFile = R.raw.q17;
                    break;
                case 17:
                    serFile = R.raw.q18;
                    break;
                case 18:
                    serFile = R.raw.q19;
                    break;
                case 19:
                    serFile = R.raw.q20;
                    break;
                case 20:
                    serFile = R.raw.q21;
                    break;
                case 21:
                    serFile = R.raw.q22;
                    break;
                case 22:
                    serFile = R.raw.q23;
                    break;
                case 23:
                    serFile = R.raw.q24;
                    break;
                case 24:
                    serFile = R.raw.q25;
                    break;
                case 25:
                    serFile = R.raw.q26;
                    break;
                case 26:
                    serFile = R.raw.q27;
                    break;
                case 27:
                    serFile = R.raw.q28;
                    break;
                case 28:
                    serFile = R.raw.q29;
                    break;
                case 29:
                    serFile = R.raw.q30;
                    break;
                case 30:
                    serFile = R.raw.q31;
                    break;
                case 31:
                    serFile = R.raw.q32;
                    break;
                case 32:
                    serFile = R.raw.q33;
                    break;
                case 33:
                    serFile = R.raw.q34;
                    break;
                case 34:
                    serFile = R.raw.q35;
                    break;
                case 35:
                    serFile = R.raw.q36;
                    break;
                case 36:
                    serFile = R.raw.q37;
                    break;
                case 37:
                    serFile = R.raw.q38;
                    break;
                case 38:
                    serFile = R.raw.q39;
                    break;
                case 39:
                    serFile = R.raw.q40;
                    break;
                case 40:
                    serFile = R.raw.q41;
                    break;
                case 41:
                    serFile = R.raw.q42;
                    break;
                case 42:
                    serFile = R.raw.q43;
                    break;
                case 43:
                    serFile = R.raw.q44;
                    break;
                case 44:
                    serFile = R.raw.q45;
                    break;
                case 45:
                    serFile = R.raw.q46;
                    break;
                case 46:
                    serFile = R.raw.q47;
                    break;
                case 47:
                    serFile = R.raw.q48;
                    break;
                case 48:
                    serFile = R.raw.q49;
                    break;
                case 49:
                    serFile = R.raw.q50;
                    break;
                case 50:
                    serFile = R.raw.q51;
                    break;
                case 51:
                    serFile = R.raw.q52;
                    break;
                case 52:
                    serFile = R.raw.q53;
                    break;
                case 53:
                    serFile = R.raw.q54;
                    break;
                case 54:
                    serFile = R.raw.q55;
                    break;
                case 55:
                    serFile = R.raw.q56;
                    break;
                case 56:
                    serFile = R.raw.q57;
                    break;
                case 57:
                    serFile = R.raw.q58;
                    break;
                case 58:
                    serFile = R.raw.q59;
                    break;
                case 59:
                    serFile = R.raw.q60;
                    break;
                case 60:
                    serFile = R.raw.q61;
                    break;
                case 61:
                    serFile = R.raw.q62;
                    break;
                case 62:
                    serFile = R.raw.q63;
                    break;
                case 63:
                    serFile = R.raw.q64;
                    break;
                case 64:
                    serFile = R.raw.q65;
                    break;
                case 65:
                    serFile = R.raw.q66;
                    break;
                case 66:
                    serFile = R.raw.q67;
                    break;
                case 67:
                    serFile = R.raw.q68;
                    break;
                case 68:
                    serFile = R.raw.q69;
                    break;
                case 69:
                    serFile = R.raw.q70;
                    break;
                case 70:
                    serFile = R.raw.q71;
                    break;
                case 71:
                    serFile = R.raw.q72;
                    break;
                case 72:
                    serFile = R.raw.q73;
                    break;
                case 73:
                    serFile = R.raw.q74;
                    break;
                case 74:
                    serFile = R.raw.q75;
                    break;
                case 75:
                    serFile = R.raw.q76;
                    break;
                case 76:
                    serFile = R.raw.q77;
                    break;
                case 77:
                    serFile = R.raw.q78;
                    break;
                case 78:
                    serFile = R.raw.q79;
                    break;
                case 79:
                    serFile = R.raw.q80;
                    break;
                case 80:
                    serFile = R.raw.q81;
                    break;
                case 81:
                    serFile = R.raw.q82;
                    break;
                case 82:
                    serFile = R.raw.q83;
                    break;
                case 83:
                    serFile = R.raw.q84;
                    break;
                case 84:
                    serFile = R.raw.q85;
                    break;
                case 85:
                    serFile = R.raw.q86;
                    break;
                case 86:
                    serFile = R.raw.q87;
                    break;
                case 87:
                    serFile = R.raw.q88;
                    break;
                case 88:
                    serFile = R.raw.q89;
                    break;
                case 89:
                    serFile = R.raw.q90;
                    break;
                case 90:
                    serFile = R.raw.q91;
                    break;
                case 91:
                    serFile = R.raw.q92;
                    break;
                case 92:
                    serFile = R.raw.q93;
                    break;
                case 93:
                    serFile = R.raw.q94;
                    break;
                case 94:
                    serFile = R.raw.q95;
                    break;
                case 95:
                    serFile = R.raw.q96;
                    break;
                case 96:
                    serFile = R.raw.q97;
                    break;
                case 97:
                    serFile = R.raw.q98;
                    break;
                case 98:
                    serFile = R.raw.q99;
                    break;
                case 99:
                    serFile = R.raw.q100;
                    break;
                case 100:
                    serFile = R.raw.q101;
                    break;
                case 101:
                    serFile = R.raw.q102;
                    break;
                case 102:
                    serFile = R.raw.q103;
                    break;
                case 103:
                    serFile = R.raw.q104;
                    break;
                case 104:
                    serFile = R.raw.q105;
                    break;
                case 105:
                    serFile = R.raw.q106;
                    break;
                case 106:
                    serFile = R.raw.q107;
                    break;
                case 107:
                    serFile = R.raw.q108;
                    break;
                case 108:
                    serFile = R.raw.q109;
                    break;
                case 109:
                    serFile = R.raw.q110;
                    break;
                case 110:
                    serFile = R.raw.q111;
                    break;
                case 111:
                    serFile = R.raw.q112;
                    break;
                case 112:
                    serFile = R.raw.q113;
                    break;
                case 113:
                    serFile = R.raw.q114;
                    break;

            }
            return serFile;
        }
    }

}
