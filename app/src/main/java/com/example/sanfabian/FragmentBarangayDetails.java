package com.example.sanfabian;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.codesgood.views.JustifiedTextView;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.TimeUnit;

import Utilities.HelperClass;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconSize;

public class FragmentBarangayDetails extends Fragment implements OnMapReadyCallback,  PermissionsListener {

    private Context mContext;
    private MapView mapView;
    MapboxMap map;
    private View details;
    private String kms;
    private DirectionsRoute drivingRoute;
    HelperClass helperClass;
    private MapboxDirections client;
    TextView title, rate, rating, nRate, hotline, attraction_name1, attraction_name2, attraction_name3, product_name1, product_name2, product_name3;
    JustifiedTextView description;
    ImageView barangay_photo, attraction_photo1, attraction_photo2, attraction_photo3, product_photo1, product_photo2, product_photo3;
    String driving = DirectionsCriteria.PROFILE_DRIVING;
    Button getDirection;
    // latOrigin at lngOrig
    Double _latitude, _longtitude, latitudeOrigin, longtitudeOrigin;
    Point origin, destination;
    private String _imageurl, _title, _collection, _id, _description, collectionName, documentID, _hotline, _attraction1, _attraction2, _attraction3,_product1, _product2, _product3, _attractionname1, _attractionname2, _attractionname3, _productname1, _productname2, _productname3;
    Double _rating, _nrate, _fnate;

    private RatingBar ratingBar;

    //Bago
    private static final long DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L;
    private static final long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;
    private String ICON_GEOJSON_SOURCE_ID = "geojson_source_id";
    private LocationChangeListeningActivityLocationCallback callback =
            new LocationChangeListeningActivityLocationCallback(this);
    Location myLocation;
    //Bago

    LatLng sample;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
        Mapbox.getInstance(context, getString(R.string.access_token));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        details = inflater.inflate(R.layout.barangay_details_layout, container, false);

        title = details.findViewById(R.id.title);
        rate = details.findViewById(R.id._rate);
        nRate = details.findViewById(R.id._nRate);
        description = details.findViewById(R.id.description);
        hotline = details.findViewById(R.id.hotline);
        getDirection = details.findViewById(R.id.buttonGetDirection);
        ratingBar = details.findViewById(R.id.rating_bar);
        rating = details.findViewById(R.id.rating);
        barangay_photo = details.findViewById(R.id.barangay_photo);
        attraction_photo1 = details.findViewById(R.id.attraction_photo1);
        attraction_photo2 = details.findViewById(R.id.attraction_photo2);
        attraction_photo3 = details.findViewById(R.id.attraction_photo3);
        product_photo1 = details.findViewById(R.id.product_photo1);
        product_photo2 = details.findViewById(R.id.product_photo2);
        product_photo3 = details.findViewById(R.id.product_photo3);
        attraction_name1 = details.findViewById(R.id.attraction_name1);
        attraction_name2 = details.findViewById(R.id.attraction_name2);
        attraction_name3 = details.findViewById(R.id.attraction_name3);
        product_name1 = details.findViewById(R.id.product_name1);
        product_name2 = details.findViewById(R.id.product_name2);
        product_name3 = details.findViewById(R.id.product_name3);


        Bundle bundle = this.getArguments();
        _title = bundle.getString("TITLE");
        _collection = bundle.getString("COLLECTION");
        _id = bundle.getString("ID");
        _description = bundle.getString("DESCRIPTION");
        _imageurl = bundle.getString("IMAGEURL");
        _hotline = bundle.getString("HOTLINE");
        _rating = bundle.getDouble("RATING");
        _nrate = bundle.getDouble("NRATE");
        _fnate = bundle.getDouble("FRATING");
        _latitude = bundle.getDouble("LATITUDE");
        _longtitude = bundle.getDouble("LONGITUDE");
        _attraction1 = bundle.getString("ATTRACTION1");
        _attraction2 = bundle.getString("ATTRACTION2");
        _attraction3 = bundle.getString("ATTRACTION3");
        _product1 = bundle.getString("PRODUCT1");
        _product2 = bundle.getString("PRODUCT2");
        _product3 = bundle.getString("PRODUCT3");
        _attractionname1 = bundle.getString("ATTRACTION_NAME1");
        _attractionname2 = bundle.getString("ATTRACTION_NAME2");
        _attractionname3 = bundle.getString("ATTRACTION_NAME3");
        _productname1 = bundle.getString("PRODUCT_NAME1");
        _productname2 = bundle.getString("PRODUCT_NAME2");
        _productname3 = bundle.getString("PRODUCT_NAME3");

        String final_rating = String.format("%.1f", _fnate);
        Double finalRating = _rating / _nrate;
        Float _finalRating = finalRating.floatValue();
        rating.setText(final_rating);
        ratingBar.setRating(_finalRating);
        ratingBar.setIsIndicator(true);

        Picasso.get().load(_attraction1).into(attraction_photo1);
        Picasso.get().load(_attraction2).into(attraction_photo2);
        Picasso.get().load(_attraction3).into(attraction_photo3);
        Picasso.get().load(_product1).into(product_photo1);
        Picasso.get().load(_product2).into(product_photo2);
        Picasso.get().load(_product3).into(product_photo3);

        attraction_name1.setText(_attractionname1);
        attraction_name2.setText(_attractionname2);
        attraction_name3.setText(_attractionname3);
        product_name1.setText(_productname1);
        product_name2.setText(_productname2);
        product_name3.setText(_productname3);



        title.setText(_title);
        description.setText(_description);
        hotline.setText(_hotline);
        int noRating = _nrate.intValue();
        nRate.setText("(" + noRating + " Ratings)");
        Picasso.get().load(_imageurl).into(barangay_photo);


        sample = new LatLng(_latitude, _longtitude);
        destination = Point.fromLngLat(sample.getLatitude(), sample.getLongitude());

        mapView = details.findViewById(R.id.detailsMapview);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        return details;
    }

    public void openDialog() {
        collectionName = _collection;
        documentID = _id;

        Bundle bundle = new Bundle();
        bundle.putString("COLLECTION", collectionName);
        bundle.putString("ID", documentID);

        RatingDialog rating = new RatingDialog();
        rating.setArguments(bundle);
        rating.show(getFragmentManager(), "Rating Dialog");
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        map = mapboxMap;
        mapboxMap.getUiSettings().setAllGesturesEnabled(false);
        mapboxMap.setStyle(Style.OUTDOORS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                enableLocationComponent(style);
                mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                        new CameraPosition.Builder()
                                .target(new LatLng(sample.getLatitude(), sample.getLongitude()))
                                .zoom(14)
                                .tilt(50)
                                .build()), 2000);
                initMarkerIconSymbolLayer(style);
                getRoute(driving, map, destination, destination);
            }
        });
    }

    private void initMarkerIconSymbolLayer(@NonNull Style loadedMapStyle) {
        // Add the marker image to map
        loadedMapStyle.addImage("icon-image", BitmapFactory.decodeResource(
                this.getResources(), R.drawable.marker_icon));

        // Add the source to the map
        loadedMapStyle.addSource(new GeoJsonSource(ICON_GEOJSON_SOURCE_ID,
                Feature.fromGeometry(Point.fromLngLat(sample.getLongitude(), sample.getLatitude()))));

        loadedMapStyle.addLayer(new SymbolLayer("icon-layer-id", ICON_GEOJSON_SOURCE_ID).withProperties(
                iconImage("icon-image"),
                iconSize(1f),
                iconAllowOverlap(true),
                iconIgnorePlacement(true),
                iconOffset(new Float[] {0f, -7f})

        ));


//        map.getStyle(new Style.OnStyleLoaded() {
//            @Override
//            public void onStyleLoaded(@NonNull Style style) {
//                getRoute(driving, map, origin, destination);
//            }
//        });

        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                latitudeOrigin = myLocation.getLatitude();
                longtitudeOrigin = myLocation.getLongitude();
                bundle.putDouble("LATITUDE", _latitude);
                bundle.putDouble("LONGITUDE", _longtitude);
                //Bago
                bundle.putDouble("LATORG", latitudeOrigin);
                bundle.putDouble("LNGORG", longtitudeOrigin);
                //Bago
                FragmentGetDirection map = new FragmentGetDirection();
                map.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, map).addToBackStack(null).commit();
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
// Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(mContext)) {

            LocationComponentOptions customLocationComponentOptions = LocationComponentOptions.builder(mContext)
                    .trackingGesturesManagement(true)
                    .accuracyAlpha(0)
                    .trackingAnimationDurationMultiplier(20)
                    .pulseEnabled(true)
                    .pulseSingleDuration(4000)
                    .build();

            // Get an instance of the component
            LocationComponent locationComponent = map.getLocationComponent();

// Set the LocationComponent activation options
            LocationComponentActivationOptions locationComponentActivationOptions =
                    LocationComponentActivationOptions.builder(mContext, loadedMapStyle)
                            .locationComponentOptions(customLocationComponentOptions)

                            .useDefaultLocationEngine(false)
                            .build();

// Activate with the LocationComponentActivationOptions object
            locationComponent.activateLocationComponent(locationComponentActivationOptions);

// Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);


// Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING_COMPASS);

// Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);

            initLocationEngine();


        } else {
//            permissionsManager = new PermissionsManager(this);
//            permissionsManager.requestLocationPermissions(getActivity());
        }
    }

    /**
     * Set up the LocationEngine and the parameters for querying the device's location
     */
    @SuppressLint("MissingPermission")
    private void initLocationEngine() {
        LocationEngine locationEngine = LocationEngineProvider.getBestLocationEngine(mContext);

        LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
                .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build();

        locationEngine.requestLocationUpdates(request, callback, mContext.getMainLooper());
        locationEngine.getLastLocation(callback);

    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            map.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });

        }
    }

    private static class LocationChangeListeningActivityLocationCallback
            implements LocationEngineCallback<LocationEngineResult> {

        private final WeakReference<FragmentBarangayDetails> activityWeakReference;



        LocationChangeListeningActivityLocationCallback(FragmentBarangayDetails activity) {
            this.activityWeakReference = new WeakReference<>(activity);
        }

        /**
         * The LocationEngineCallback interface's method which fires when the device's location has changed.
         *
         * @param result the LocationEngineResult object which has the last known location within it.
         */
        @Override
        public void onSuccess(LocationEngineResult result) {
            FragmentBarangayDetails activity = activityWeakReference.get();
            if (activity != null) {
                activity.myLocation = result.getLastLocation();
                activity.origin = Point.fromLngLat(activity.myLocation.getLatitude(), activity.myLocation.getLongitude());
//                activity.getRoute(activity.driving, activity.map, activity.origin, activity.destination);
//                activity.distance.setText("yes" + activity.kms);
                if (activity.myLocation == null) {
                    return;
                }

                if (activity.map != null && result.getLastLocation() != null) {
                    activity.map.getLocationComponent().forceLocationUpdate(result.getLastLocation());
                }
            }
        }


        /**
         * The LocationEngineCallback interface's method which fires when the device's location can't be captured
         *
         * @param exception the exception message
         */
        @Override
        public void onFailure(@NonNull Exception exception) {
            FragmentBarangayDetails activity = activityWeakReference.get();
            if (activity != null) {
                Toast.makeText(activity.getActivity(), exception.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void getRoute(String profile, MapboxMap mapboxMap, Point origin, Point destination) {
        client = MapboxDirections.builder()
                .origin(origin)
                .destination(destination)
                .overview(DirectionsCriteria.OVERVIEW_FULL)
                .profile(profile)
                .accessToken(getString(R.string.access_token))
                .build();

        client.enqueueCall(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
// You can get the generic HTTP info about the response
                Timber.d("Response code: " + response.code());
                if (response.body() == null) {
                    Timber.e("No routes found, make sure you set the right user and access token.");
                    return;
                } else if (response.body().routes().size() < 1) {
                    Timber.e("No routes found");
                    return;
                }

                switch (profile) {
                    case DirectionsCriteria.PROFILE_DRIVING:
                        drivingRoute = response.body().routes().get(0);
                        Double km =  Math.round(helperClass.convertMeters(drivingRoute.distance())*100.0)/100.0;
                        kms = Double.toString(km)+"km";
                        String seconds =  String.valueOf(TimeUnit.SECONDS
                                .toMinutes(drivingRoute.duration().longValue()));
                        String formatedTime = helperClass.convertMins(seconds);
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                Timber.e("Error: " + throwable.getMessage());
                Toast.makeText(getActivity(), "Error: " + throwable.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
