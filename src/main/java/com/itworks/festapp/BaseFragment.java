package com.itworks.festapp;

import android.support.v4.app.Fragment;
import com.itworks.festapp.models.BaseModel;

public class BaseFragment extends Fragment{
    public BaseModel baseModel;
    void setBaseModel (BaseModel baseModel){
        this.baseModel = baseModel;
    };
}
