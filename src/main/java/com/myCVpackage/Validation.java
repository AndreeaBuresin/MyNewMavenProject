package com.myCVpackage;

import javax.xml.crypto.Data;
import java.util.regex.Pattern;

/**
 * Created by buresina on 21/10/2016.
 */
public class Validation {
//nume
    public static boolean nameValidation(String name){
        if (name.length() >= 3 && name.length() <= 20 && name.charAt(0)>= 'A' && name.charAt(0) <= 'Z' && name.matches("[A-Za-z]*")){
            return true;
        }

        return false;
    }
//cnp
    public static boolean cnpValidation(String cnp){
       if (cnp.matches("\\b[1-8]\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])(0[1-9]|[1-4]\\d|\n" +
               "5[0-2]|99)\\d{4}\\b")
               && cnp.length() == 13){
           return true;
       }
       return  false;

        /**
         * \b[1-8]   ->sex
         * \d{2} -> an
         * (0[1-9]|1[0-2]) ->luna
         * (0[1-9]|[12]\d|3[01]) ->ziua
         * (0[1-9]|[1-4]\d|5[0-2]|99) ->judet
         * \d{4}\b
         */
    }
    //data
    public static boolean dataValidation(String date){
        if (date.matches("^(0?[1-9]|[12][0-9]|3[01])[\\/\\-](0?[1-9]|1[012])[\\/\\-]\\d{4}$")) {
            return true;
        }

        return false;
    }
    public static boolean isPositiveInt(String number) {
        if (isInt(number)) {
            int i = Integer.parseInt(number);
            if (i >= 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInt(String number) {
        try {
            int i = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


}
