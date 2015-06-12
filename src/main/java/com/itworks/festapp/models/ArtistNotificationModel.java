package com.itworks.festapp.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "ArtistNotificationModels")
public class ArtistNotificationModel extends Model{

    @Column(name = "Notification")
    public Boolean notification;

    @Column(name = "ArtistId", index = true)
    public int artistId;

    public ArtistNotificationModel() {
        super();
    }

    public ArtistNotificationModel(int artistId, Boolean notification) {
        super();
        this.notification = notification;
        this.artistId = artistId;
    }
}
