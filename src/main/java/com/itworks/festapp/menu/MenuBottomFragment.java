package com.itworks.festapp.menu;


import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.itworks.festapp.R;
import com.itworks.festapp.helpers.*;
import com.itworks.festapp.helpers.comparators.TimetableListComparator;
import com.itworks.festapp.models.TimetableModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MenuBottomFragment extends Fragment {

    TextView title, now, after;
    private MenuBottomElement element1, element2, element3, element4;
    int index = 0;
    private ModelsController modelsController;
    RelativeLayout parent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.menu_bottom_fragment, container, false);
        title = (TextView)v.findViewById(R.id.textView3);
        now = (TextView)v.findViewById(R.id.textView);
        after = (TextView)v.findViewById(R.id.textView4);
        parent = (RelativeLayout) v.findViewById(R.id.bottomParent);
        setTypefaces();
        modelsController = new ModelsController(getActivity());
        element1 = new MenuBottomElement();
        element2 = new MenuBottomElement();
        element3 = new MenuBottomElement();
        element4 = new MenuBottomElement();
        setTimetableForBottom();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.now1, element1);
        transaction.add(R.id.now2, (PhotoController.isItSmallScreen(getActivity()))?element3:element2);
        transaction.add(R.id.after1, (PhotoController.isItSmallScreen(getActivity()))?element2:element3);
        transaction.add(R.id.after2, element4);
        transaction.commit();
        if (PhotoController.isItSmallScreen(getActivity())) {
            FrameLayout frameLayout = (FrameLayout) v.findViewById(R.id.now2);
            frameLayout.getLayoutParams().height = 0;
            frameLayout.requestLayout();
            FrameLayout frameLayout2 = (FrameLayout) v.findViewById(R.id.after2);
            frameLayout2.getLayoutParams().height = 0;
            frameLayout2.requestLayout();
            now.getLayoutParams().height = 0;
            now.requestLayout();
            after.getLayoutParams().height = 0;
            after.requestLayout();
            title.setText("DABAR ANT SCENOS LIPA");
        }else{
            now.setText(getString(R.string.menu_bottom_name1));
            after.setText(getString(R.string.menu_bottom_name2));
        }
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onWindowFocusChanged(getResources().getDimension(R.dimen.bottom_width));
    }

    public void onWindowFocusChanged(float width) {
        if(PhotoController.isItSmallScreen(getActivity())) {
            parent.setPadding(0, 0, 0, 0);
        }else{
            Display display = getActivity().getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int padding = (int) ((size.x - width) / 2);
            parent.setPadding(0, 0, 0, padding);
        }
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
        JSONRepository jsonRepository = new JSONRepository(getActivity());
        List<TimetableModel> timetables = jsonRepository.getTimetableFromJSON();
        int dayNumber = DateController.getFestivalDay();
        List<TimetableModel> timetableModels = modelsController.findNowTimetables(dayNumber, timetables);
        TimetableModel timetable1 = modelsController.getTimetable(timetableModels, 0);
        TimetableModel timetable2 = modelsController.getTimetable(timetableModels, 1);
        element1.setTimetableModel(timetable1);
        element2.setTimetableModel(timetable2);
        setNextTimetables(timetable1, timetable2, timetables, dayNumber);
    }

    private void setTypefaces() {
        TypefaceController typefaceController = new TypefaceController(getActivity().getAssets());
        typefaceController.setFutura(title);
        typefaceController.setFutura(now);
        typefaceController.setFutura(after);
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
        setTimtableByIndex(timetables, index + 1, element3);
        setTimtableByIndex(timetables, index + 2, element4);
    }

    private void setTimtableByIndex(List<TimetableModel> timetables, int index, MenuBottomElement element) {
        TimetableModel timetableModel = new TimetableModel();
        if(timetables.size() > index)
            timetableModel =timetables.get(index);
        element.setTimetableModel(timetableModel);
    }

    private List<TimetableModel> prepareTimetableList(List<TimetableModel> list, int dayNumber, TimetableModel notThis){
        List<TimetableModel> stageTimetable = new ArrayList<>();
        for (TimetableModel aList : list) {
            if (aList.day == dayNumber && notThis.id != aList.id) {
                stageTimetable.add(aList);
            }
        }
        Collections.sort(stageTimetable, new TimetableListComparator());
        return stageTimetable;
    }
}