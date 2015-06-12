package com.itworks.festapp.helpers;

public class OriginHelper {
    public static String getOriginDrawableTitle(String origin){
        String name;
        switch (origin){
            case "LT":
                name = "squere_lithuania";
                break;
            case "UK":
                name = "squere_united_kingdom";
                break;
            case "UA":
                name = "squere_ukraine";
                break;
            case "ES":
                name = "squere_spain";
                break;
            case "DE":
                name = "squere_germany";
                break;
            default:
                name = "squere_lithuania";
                break;
        }
        return name;
    }
}
