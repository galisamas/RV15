package com.itworks.festapp.helpers.comparators;


import com.itworks.festapp.models.ArtistModel;

import java.util.Comparator;

public class ArtistListComparator implements Comparator<ArtistModel> {
    @Override
    public int compare(ArtistModel o1, ArtistModel o2) {
        return o1.title.compareTo(o2.title);
    }
}
