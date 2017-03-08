package com.proyecto.proyectouf2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.clustering.RadiusMarkerClusterer;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {


    private MapView map;
    private MyLocationNewOverlay myLocationOverlay;
    private MinimapOverlay mMinimapOverlay;
    private ScaleBarOverlay mScaleBarOverlay;
    private CompassOverlay mCompassOverlay;
    private IMapController mapController;
    private RadiusMarkerClusterer bicingMarker;

    public MainActivityFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_main, container, false);


        map = (MapView) fragmentView.findViewById(R.id.map);

        abrirMapa();
        hacerZoom();
        ponerDespues();
        //ponMarkers();
        map.invalidate();


        return fragmentView;
    }


    private void abrirMapa(){
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setTilesScaledToDpi(true);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
    }

    private void hacerZoom(){
        mapController = map.getController();
        mapController.setZoom(14);
        //Ponemos un punto geolocalizado para ir directos a barcelona.
        GeoPoint point = new GeoPoint(41.3818, 2.1685);
        mapController.setCenter(point);
    }

    private void ponerDespues(){
        final DisplayMetrics dm = getResources().getDisplayMetrics();

        myLocationOverlay = new MyLocationNewOverlay(
                getContext(),
                new GpsMyLocationProvider(getContext()),
                map
        );
        myLocationOverlay.enableMyLocation();
        myLocationOverlay.runOnFirstFix(new Runnable() {
            public void run() {
                mapController.animateTo(myLocationOverlay
                        .getMyLocation());
            }
        });

        mMinimapOverlay = new MinimapOverlay(getContext(), map.getTileRequestCompleteHandler());
        mMinimapOverlay.setWidth(dm.widthPixels / 2);
        mMinimapOverlay.setHeight(dm.heightPixels / 5);

        mScaleBarOverlay = new ScaleBarOverlay(map);
        mScaleBarOverlay.setCentred(true);
        mScaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);

        mCompassOverlay = new CompassOverlay(
                getContext(),
                new InternalCompassOrientationProvider(getContext()),
                map
        );
        mCompassOverlay.enableCompass();

        map.getOverlays().add(myLocationOverlay);
        //map.getOverlays().add(this.mMinimapOverlay);
        map.getOverlays().add(this.mScaleBarOverlay);
        map.getOverlays().add(this.mCompassOverlay);
    }


//    private void ponMarkers(){
//        agruparMarkers();
//
//        MyApp app = (MyApp) getActivity().getApplication();
//
//        Firebase ref = app.getRef();
//
//        final Firebase stats = ref.child("stations");
//
//        stats.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
//                    Bici bici = postSnapshot.getValue(Bici.class);
//                    Log.e("XXXX", bici.toString());
//
//                    Marker marker = new Marker(map);
//
//                    GeoPoint point = new GeoPoint(
//                            Double.parseDouble(bici.getLatitude()),
//                            Double.parseDouble(bici.getLongitude())
//                    );
//
//                    marker.setPosition(point);
//
//                    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
//                    marker.setIcon(getResources().getDrawable(R.drawable.bike));
//                    marker.setTitle(bici.getStreetName());
//                    marker.setAlpha(0.6f);
//
//                    if(bici.getSlots()<= 7){
//                        marker.setIcon(getResources().getDrawable(R.drawable.bikered));
//
//                    }
//                    else if(bici.getSlots()<= 15){
//                        marker.setIcon(getResources().getDrawable(R.drawable.bikeyellow));
//
//                    }
//                    else if(bici.getSlots()> 15){
//                        marker.setIcon(getResources().getDrawable(R.drawable.bike));
//
//                    }
//
//                    bicingMarker.add(marker);
//                }
//                bicingMarker.invalidate();
//                map.invalidate();
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//                System.out.println(firebaseError.getMessage());
//
//            }
//        });
//    }
//    private void agruparMarkers() {
//        bicingMarker = new RadiusMarkerClusterer(getContext());
//        map.getOverlays().add(bicingMarker);
//
//        Drawable clusterIconD = getResources().getDrawable(R.drawable.mapmarkermultiple
//        );
//        Bitmap clusterIcon = ((BitmapDrawable)clusterIconD).getBitmap();
//
//        bicingMarker.setIcon(clusterIcon);
//        bicingMarker.setRadius(100);
//    }
}
