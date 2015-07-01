package com.itworks.festapp.helpers;

public class OriginRepository {
    public static String getOriginDrawableTitle(String origin){
        String name = "squere_lithuania";
        if(origin !=null){
            if(origin.equals("UK"))
                name = "squere_united_kingdom";
            else if(origin.equals("UA"))
                name = "squere_ukraine";
            else if(origin.equals("ES"))
                name = "squere_spain";
            else if(origin.equals("DE"))
                name = "squere_germany";
            else
                name = "squere_lithuania";
        }
        return name;
    }
}
