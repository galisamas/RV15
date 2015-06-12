package com.itworks.festapp.helpers.comparators;

import com.itworks.festapp.models.TimetableModel;

import java.util.Comparator;

public class TimetableListComparator implements Comparator<TimetableModel> {
    @Override
    public int compare(TimetableModel o1, TimetableModel o2) {
        return o1.start_time.compareTo(o2.start_time);
    }
}
