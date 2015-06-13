package com.itworks.festapp.menu;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.itworks.festapp.R;
import com.itworks.festapp.helpers.DateHelper;
import com.itworks.festapp.helpers.JSONHelper;
import com.itworks.festapp.helpers.comparators.TimetableListComparator;
import com.itworks.festapp.models.TimetableModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MenuBottomFragment extends Fragment { // TODO patikrint buga kur apacioj buvo neaiskus atlikejai (antra diena)
    TextView title, now, after;
    private MenuBottomElement element1 , element2, element3, element4;
    int index = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.menu_bottom_fragment, container, false);
        title = (TextView)v.findViewById(R.id.textView3);
        now = (TextView)v.findViewById(R.id.textView);
        after = (TextView)v.findViewById(R.id.textView4);
        setTypefaces();
        element1 = new MenuBottomElement();
        element2 = new MenuBottomElement();
        element3 = new MenuBottomElement();
        element4 = new MenuBottomElement();
        setTimetableForBottom();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.now1, element1);
        transaction.add(R.id.now2, element2);
        transaction.add(R.id.after1, element3);
        transaction.add(R.id.after2, element4);
        transaction.commit();
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        if(index != 0) {
            setTimetableForBottom();
            element1.setInfo();
            element2.setInfo();
            element3.setInfo();
            element4.setInfo();
        }
        index++;
    }

    private void setTimetableForBottom() {
        JSONHelper jsonHelper = new JSONHelper(getActivity());
        List<TimetableModel> timetables = jsonHelper.getTimetableFromJSON();
        int dayNumber = DateHelper.getFestivalDay();
        List<Integer> ids = findTimetables(dayNumber, timetables);
        TimetableModel timetable1 = getTimetableById(ids, 0, timetables);
        TimetableModel timetable2 = getTimetableById(ids, 1, timetables);
        element1.setTimetableModel(timetable1);
        element2.setTimetableModel(timetable2);
        setNextTimetables(timetable1, timetable2, timetables, dayNumber);
    }

    private void setTypefaces() {
        Typeface futura = Typeface.createFromAsset(getActivity().getAssets(), "fonts/futura_condensed_medium.ttf");
        title.setTypeface(futura);
        now.setTypeface(futura);
        after.setTypeface(futura);
    }

    private TimetableModel getTimetableById(List<Integer> id, int number, List<TimetableModel> timetables){
        TimetableModel timetableModel = new TimetableModel();
        if(id.size() > number)
            timetableModel = getTimetableModelById(id.get(number),timetables);
        return timetableModel;
    }

    private TimetableModel getTimetableModelById(int id, List<TimetableModel> timetables ) { // TODO refactor iskelt
        TimetableModel timetableModel = new TimetableModel();
        for(int i=0;i<timetables.size();i++) {
            if(timetables.get(i).id == id){
                timetableModel = timetables.get(i);
                break;
            }
        }
        return timetableModel;
    }

    private void setNextTimetables(TimetableModel current, TimetableModel other, List<TimetableModel> timetables, int dayNumber){
        timetables = prepareTimetableList(timetables, dayNumber, other);
        int index = (current.isItEmpty)?-1:current.id;
        for(int i=0;i<timetables.size();i++) {
            if(timetables.get(i).id == index){
                index = i;
                break;
            }
        }
        TimetableModel element = new TimetableModel();
        if(timetables.size() > index+1)
            element =timetables.get(index + 1);
        element3.setTimetableModel(element);
        element = new TimetableModel();
        if(timetables.size() > index+2)
            element =timetables.get(index + 2);
        element4.setTimetableModel(element);
    }

    private List<TimetableModel> prepareTimetableList(List<TimetableModel> list, int dayNumber, TimetableModel notThis){
        List<TimetableModel> stageTimetable = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            if (list.get(i).day == dayNumber) {
                stageTimetable.add(list.get(i));
            }
        }
        if(!notThis.isItEmpty)
            stageTimetable.remove(notThis);
        Collections.sort(stageTimetable, new TimetableListComparator());
        return stageTimetable;
    }

    private List<Integer> findTimetables(int dayNumber, List<TimetableModel> timetables){
        List<Integer> result = new ArrayList<>();
        for(int i=0;i<timetables.size();i++) {
            if(timetables.get(i).day == dayNumber) {
                if(DateHelper.calculateIsItNow(dayNumber, timetables.get(i).start_time, timetables.get(i).end_time)){
                    result.add(timetables.get(i).id);
                }
            }
        }
        return result;
    }
}