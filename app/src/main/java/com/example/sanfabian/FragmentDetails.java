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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.codesgood.views.JustifiedTextView;
import com.google.firebase.firestore.Transaction;
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
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
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

public class FragmentDetails extends Fragment implements OnMapReadyCallback,  PermissionsListener, Serializable {

    private Context mContext;
    private MapView mapView;
    MapboxMap map;
    private View details;
    private DirectionsRoute drivingRoute;
    HelperClass helperClass;
    private MapboxDirections client;

    LinearLayout photos_layout;
    TextView title, rate, rating, nRate;
    JustifiedTextView description;
    String driving = DirectionsCriteria.PROFILE_DRIVING;
    Button getDirection;
    ProgressBar loading_user_location;
    Double _latitude, _longtitude, latitudeOrigin, longtitudeOrigin;
    Point origin, destination;
    private RatingBar ratingBar;
    private static final long DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L;
    private static final long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;
    private String ICON_GEOJSON_SOURCE_ID = "geojson_source_id";
    private LocationChangeListeningActivityLocationCallback callback =
            new LocationChangeListeningActivityLocationCallback(this);
    Location myLocation;
    private LatLng sample;
    private String _imageurl, _title, _collection, _id, _description, collectionName, documentID;
    Double _rating, _nrate, _fnate;

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
        details = inflater.inflate(R.layout.details_layout, container, false);

        title = details.findViewById(R.id.title);
        rate = details.findViewById(R.id._rate);
        description = details.findViewById(R.id.description);
        getDirection = details.findViewById(R.id.buttonGetDirection);
        ratingBar = details.findViewById(R.id.rating_bar);
        rating = details.findViewById(R.id.rating);
        nRate = details.findViewById(R.id._nRate);
        photos_layout = details.findViewById(R.id.photos_layout);
        loading_user_location = details.findViewById(R.id.loading_user_location);

        Bundle bundle = this.getArguments();
        _imageurl = bundle.getString("IMAGEURL");
        _title = bundle.getString("TITLE");
        _collection = bundle.getString("COLLECTION");
        _id = bundle.getString("ID");
        _description = bundle.getString("DESCRIPTION");
        _rating = bundle.getDouble("RATING");
        _nrate = bundle.getDouble("NRATE");
        _fnate = bundle.getDouble("FRATING");
        _latitude = bundle.getDouble("LATITUDE");
        _longtitude = bundle.getDouble("LONGITUDE");
        ArrayList<Transaction> photos = (ArrayList<Transaction>) bundle.getSerializable("PHOTOS");

        String final_rating = String.format("%.1f", _fnate);
        Double finalRating = _rating / _nrate;
        Float _finalRating = finalRating.floatValue();
        rating.setText(final_rating);
        ratingBar.setRating(_finalRating);
        ratingBar.setIsIndicator(true);
        getDirection.setVisibility(View.INVISIBLE);
        loading_user_location.setVisibility(View.VISIBLE);

        sample = new LatLng(_latitude, _longtitude);
        destination = Point.fromLngLat(sample.getLatitude(), sample.getLongitude());

        mapView = details.findViewById(R.id.detailsMapview);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        title.setText(_title);
        description.setText(_description);
        int noRating = _nrate.intValue();
        nRate.setText("(" + noRating + " Ratings)");

        Layout(photos, photos_layout);

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

    public void Layout(ArrayList items, LinearLayout layout){
        try {
            for (int item = 0; item <= items.size(); item++) {
                ImageView imgItem = new ImageView(mContext);
                Picasso.get().load(String.valueOf(items.get(item))).into(imgItem);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0,0,0,30);
                imgItem.setLayoutParams(params);
                layout.addView(imgItem);
            }
        } catch (Exception e){

        }
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

        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                latitudeOrigin = myLocation.getLatitude();
                longtitudeOrigin = myLocation.getLongitude();
                bundle.putDouble("LATITUDE", _latitude);
                bundle.putDouble("LONGITUDE", _longtitude);

                bundle.putDouble("LATORG", latitudeOrigin);
                bundle.putDouble("LNGORG", longtitudeOrigin);

                FragmentGetDirection map = new FragmentGetDirection();
                map.setArguments(bundle);
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, map).addToBackStack(null).commit();
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
        // permissionsManager = new PermissionsManager(this);
        // permissionsManager.requestLocationPermissions(getActivity());
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
    public void onExplanationNeeded(List<String> permissionsToExplain) { }

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

        private final WeakReference<FragmentDetails> activityWeakReference;

        LocationChangeListeningActivityLocationCallback(FragmentDetails activity) {
            this.activityWeakReference = new WeakReference<>(activity);
        }

        /**
         * The LocationEngineCallback interface's method which fires when the device's location has changed.
         *
         * @param result the LocationEngineResult object which has the last known location within it.
         */
        @Override
        public void onSuccess(LocationEngineResult result) {
            FragmentDetails activity = activityWeakReference.get();

            if (activity != null) {
                Location location = result.getLastLocation();

                if (location == null) {
                    return;
                }
            }

            if (activity != null) {
                activity.myLocation = result.getLastLocation();
                activity.origin = Point.fromLngLat(activity.myLocation.getLatitude(), activity.myLocation.getLongitude());
                activity.getDirection.setVisibility(View.VISIBLE);
                activity.loading_user_location.setVisibility(View.INVISIBLE);

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
            FragmentDetails activity = activityWeakReference.get();
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
