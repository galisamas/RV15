package com.itworks.festapp.helpers;

import android.content.Context;
import com.itworks.festapp.models.ArtistModel;
import com.itworks.festapp.models.PlaceModel;
import com.itworks.festapp.models.TimetableModel;

import java.util.ArrayList;
import java.util.List;

public class ModelsHelper {

    private final List<PlaceModel> places;
    private JSONHelper jsonHelper;

    public ModelsHelper(Context context) {
        jsonHelper = new JSONHelper(context);
        places = jsonHelper.getPlacesFromJSON();

    }

    public String convertPlaceIdToWhere(int id){
        for(int i =0;i< places.size();i++){
            if(places.get(i).id == id){
                return places.get(i).where;
            }
        }
        return "";
    }

    public String convertPlaceIdToTitle(int id){
        for(int i =0;i< places.size();i++){
            if(places.get(i).id == id){
                return places.get(i).name;
            }
        }
        return "";
    }

    public Double convertPlaceIdToLat(int id){
        for(int i =0;i< places.size();i++){
            if(places.get(i).id == id){
                return places.get(i).latitude;
            }
        }
        return 0.0;
    }

    public Double convertPlaceIdToLng(int id){
        for(int i =0;i< places.size();i++){
            if(places.get(i).id == id){
                return places.get(i).longitude;
            }
        }
        return 0.0;
    }

    public List<TimetableModel> getTimetableModelsByArtistId(int id){
        List<TimetableModel> result = new ArrayList<>();
        List<TimetableModel> timetableModels = jsonHelper.getTimetableFromJSON();
        for(int i =0;i<timetableModels.size();i++){
            if(timetableModels.get(i).artistId == id){
                result.add(timetableModels.get(i));
            }
        }
        return result;
    }

    public ArtistModel getArtistModelById(int id){
        List<ArtistModel> list = jsonHelper.getArtistsFromJSON();
        for(ArtistModel anArtistModel: list){
            if(anArtistModel.id == id){
                return anArtistModel;
            }
        }
        return new ArtistModel();
    }
}
