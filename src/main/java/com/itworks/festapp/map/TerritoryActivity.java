package com.itworks.festapp.map;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.*;
import com.itworks.festapp.R;
import com.itworks.festapp.helpers.FloatingGroupExpandableListView.FloatingGroupExpandableListView;
import com.itworks.festapp.helpers.FloatingGroupExpandableListView.WrapperExpandableListAdapter;
import com.itworks.festapp.helpers.JSONRepository;
import com.itworks.festapp.helpers.UsageController;
import com.itworks.festapp.models.PlaceModel;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TerritoryActivity extends FragmentActivity implements android.location.LocationListener{

    GoogleMap googleMap;
    Location location;
    double latitude, longitude, lat, lng;
    LatLng cameraStart = new LatLng(55.160313, 25.309264);
    private FloatingGroupExpandableListView expListView;
    ExpandableListAdapter listAdapter;
    List<String> listDataChild;
    private ImageLoader imageLoader;
    private Queue<Marker> markerQueue;
    private String name, snippet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.territory_activity);
        System.gc();
        markerQueue = new LinkedList<>();
        Intent intent = getIntent();
        lat = intent.getDoubleExtra("place_latitude", 0.0);
        lng = intent.getDoubleExtra("place_longitude", 0.0);
        name = intent.getStringExtra("name");
        snippet = intent.getStringExtra("snippet");
        if(lat != 0.0 && lng != 0.0){
            cameraStart = new LatLng(lat, lng);
        }
        try{
            initilizeMap();
            googleMapSetting();
            configExpandableList();
            if(lat != 0.0 && lng != 0.0) {
                createMarker(cameraStart, name, snippet);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void configExpandableList() {
        expListView = (FloatingGroupExpandableListView) findViewById(R.id.expandableListView);
        JSONRepository jsonRepository = new JSONRepository(this);
        listDataChild = jsonRepository.getMapLegendFromJSON();
        listAdapter = new ExpandableListAdapter(this, listDataChild);
        WrapperExpandableListAdapter wrapperAdapter = new WrapperExpandableListAdapter(listAdapter);
        expListView.setAdapter(wrapperAdapter);
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                List<PlaceModel> places = jsonRepository.getPlacesFromJSON();
                PlaceModel place = places.get(childPosition);
                LatLng location = new LatLng(place.latitude, place.longitude);
                createMarker(location , place.name, "");
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(16));
                return true;
            }
        });
    }

    private void googleMapSetting(){

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.getBaseContext());
        if(status!= ConnectionResult.SUCCESS){
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();

        }else {
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setRotateGesturesEnabled(false);
            googleMap.getUiSettings().setTiltGesturesEnabled(false);
            googleMap.getUiSettings().setCompassEnabled(false);
            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60000, 10, this);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(cameraStart));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(16));

            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
                if (location == null) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 10, this);

                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
            }
            imageLoader = ImageLoader.getInstance();
            Log.d("TERRITORY","laisvos: " + UsageController.getMemoryUsage(this));
            imageLoader.loadImage("drawable://" + R.drawable.map_v3, new SimpleImageLoadingListener() { // TODO uzdeti geresnes kokybes mapus
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    LatLngBounds newarkBounds = new LatLngBounds(
                            new LatLng(55.157544, 25.299200),       // South west corner
                            new LatLng(55.162867, 25.315079));      // North east corner
                    GroundOverlayOptions newarkMap = new GroundOverlayOptions()
                            .image(BitmapDescriptorFactory.fromBitmap(loadedImage))
                            .positionFromBounds(newarkBounds);
                    googleMap.addGroundOverlay(newarkMap);
                    Log.d("TERRITORY", "laisva po!!!!!!!!!: " + UsageController.getMemoryUsage(TerritoryActivity.this));
                }
            });
        }
    }

    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

            if (googleMap == null) {
                Toast.makeText(this.getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void createMarker(LatLng place, String artist, String snippet) {
        MarkerOptions marker = new MarkerOptions().position(place).title(artist);
        if(snippet != null && !snippet.isEmpty()){
            marker.snippet(snippet);
        }
        Bitmap bmp = imageLoader.loadImageSync("drawable://" +R.drawable.location);
        marker.icon(BitmapDescriptorFactory.fromBitmap(bmp));
        Marker m = googleMap.addMarker(marker);
        m.showInfoWindow();
        markerQueue.add(m);
        if(markerQueue.size()>3){
            m = markerQueue.remove();
            m.remove();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
    }

    @Override
    public void onBackPressed() {
        if (!expListView.isGroupExpanded(0)) {
            super.onBackPressed();
            FragmentManager fm = getSupportFragmentManager();
            if(fm.getBackStackEntryCount() != 0) {
                fm.popBackStack();
            }
            finish();
        }else{
            expListView.collapseGroup(0);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        googleMap.clear();
        System.gc();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}