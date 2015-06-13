package com.itworks.festapp.helpers;

import android.content.Context;
import com.itworks.festapp.models.ArtistModel;
import com.itworks.festapp.models.FoodModel;
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
        for (PlaceModel place : places) {
            if (place.id == id) {
                return place.where;
            }
        }
        return "";
    }

    public String convertPlaceIdToTitle(int id){
        for (PlaceModel place : places) {
            if (place.id == id) {
                return place.name;
            }
        }
        return "";
    }

    public Double convertPlaceIdToLat(int id){
        for (PlaceModel place : places) {
            if (place.id == id) {
                return place.latitude;
            }
        }
        return 0.0;
    }

    public Double convertPlaceIdToLng(int id){
        for (PlaceModel place : places) {
            if (place.id == id) {
                return place.longitude;
            }
        }
        return 0.0;
    }

    public List<TimetableModel> getTimetableModelsByArtistId(int id){
        List<TimetableModel> result = new ArrayList<>();
        List<TimetableModel> timetableModels = jsonHelper.getTimetableFromJSON();
        for (TimetableModel timetableModel : timetableModels) {
            if (timetableModel.artistId == id) {
                result.add(timetableModel);
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

    public FoodModel getFoodModelById(int id){
        List<FoodModel> list = jsonHelper.getFoodFromJSON();
        for(FoodModel foodModel: list){
            if(foodModel.id == id){
                return foodModel;
            }
        }
        return new FoodModel();
    }
}
