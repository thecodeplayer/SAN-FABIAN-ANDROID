package com.example.sanfabian;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
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
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
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
import com.mapbox.mapboxsdk.plugins.building.BuildingPlugin;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.model.PlaceOptions;
import com.mapbox.mapboxsdk.plugins.traffic.TrafficPlugin;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;
import com.mapbox.navigation.core.MapboxNavigation;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.TimeUnit;


import Utilities.HelperClass;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.mapbox.core.constants.Constants.PRECISION_6;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconSize;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconTextFit;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineCap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineJoin;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth;


public class FragmentGetDirection extends Fragment implements OnMapReadyCallback, PermissionsListener{

    private static final long DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L;
    private static final long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;

    private Context mContext;
    private MapView mapView;
    private MapboxMap map;
    MapboxNavigation navigation;
    private int c=0;
    private double distance;
    private String st;
    private String startLocation = "";
    private String destinationLocation = "";
    private LatLng mylocationlatlng;
    private LatLng latlng_details, orgLatlng;
    double _latitude, _longitude, latitudeOrigin, longtitudeOrigin;
    private String ICON_GEOJSON_SOURCE_ID = "geojson_source_id";
    private Bundle bundle;
    private String profile;

    private LocationEngine locationEngine;
    private CarmenFeature home;
    private CarmenFeature work;
    private String geojsonSourceLayerId = "geojsonSourceLayerId";
    private String symbolIconId = "symbolIconId";
    private LocationChangeListeningActivityLocationCallback callback =
            new LocationChangeListeningActivityLocationCallback(this);

    private HelperClass helperClass = new HelperClass();
    private Button startNavigating;
    private PermissionsManager permissionsManager;
    public Location myLocation;
    private LatLng currentPosition = new LatLng();
    private GeoJsonSource geoJsonSource;
    private GeoJsonSource iconGeoJsonSource;
    private LineLayer routeLayer;
    private ValueAnimator animator;

    private Point originPosition, destinationPosition;
    Point latlng_details_point;
    private DirectionsRoute drivingRoute;
    private DirectionsRoute walkingRoute;
    private DirectionsRoute cyclingRoute;
    private DirectionsRoute currentRoute;
    private MapboxDirections client;

    private String lastSelectedDirectionsProfile = DirectionsCriteria.PROFILE_DEFAULT_USER;
    private Button drivingButton;
    private Button walkingButton;
    private Button cyclingButton;
    private FloatingActionButton floatingTraffic;
    private FloatingActionButton floatingSetCamera;
    private boolean firstRouteDrawn = false;
    private String[] profiles = new String[]{
            DirectionsCriteria.PROFILE_DRIVING,
            DirectionsCriteria.PROFILE_CYCLING,
            DirectionsCriteria.PROFILE_WALKING};

    private static final String TAG = "Map_Fragment";
    private static final String ROUTE_LAYER_ID = "route-layer-id";
    private static final String ROUTE_SOURCE_ID = "route-source-id";
    private static final String ICON_LAYER_ID = "icon-layer-id";
    private static final String ICON_SOURCE_ID = "icon-source-id";
    private static final String RED_PIN_ICON_ID = "red-pin-icon-id";


    public boolean flag = true;
    private ImageView imageView;
    private BuildingPlugin buildingPlugin;

    private View get_direction;

    public static FragmentGetDirection newInstance() {
        Bundle args = new Bundle();
        FragmentGetDirection fragment = new FragmentGetDirection();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mapbox.getInstance(mContext, getString(R.string.access_token));
        get_direction =  inflater.inflate(R.layout.fragment_get_direction, container, false);

        return get_direction;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = (MapView) getActivity().findViewById(R.id.mapview);
        startNavigating = (Button) getActivity().findViewById(R.id.startNavigating);

        drivingButton = getActivity().findViewById(R.id.driving_profile_button);
        drivingButton.setTextColor(Color.WHITE);
        walkingButton = getActivity().findViewById(R.id.walking_profile_button);
        cyclingButton = getActivity().findViewById(R.id.cycling_profile_button);
        floatingTraffic = getActivity().findViewById(R.id.floatingTraffic);
        floatingSetCamera = getActivity().findViewById(R.id.floatingSetCamera);
        imageView = getActivity().findViewById(R.id.trafficLabel);


        drivingButton.setVisibility(View.INVISIBLE);
        walkingButton.setVisibility(View.INVISIBLE);
        cyclingButton.setVisibility(View.INVISIBLE);

        imageView.setVisibility(View.INVISIBLE);
        startNavigating.setEnabled(false);
        mapView.onSaveInstanceState(savedInstanceState);
        mapView.getMapAsync(this);

        startNavigating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.animateCamera(CameraUpdateFactory.newCameraPosition(
                        new CameraPosition.Builder()
                                .target(new LatLng(mylocationlatlng.getLatitude(), mylocationlatlng.getLongitude()))
                                .zoom(22)
                                .tilt(50)
                                .build()), 4000);

                map.getStyle(new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        enableLocationComponent(style);

                    }
                });

            }

        });


        floatingSetCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helperClass.setCameraPosition(mylocationlatlng, map);
            }
        });


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    //Pagkabukas ng Map
    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        bundle = this.getArguments();
        _latitude = bundle.getDouble("LATITUDE");
        _longitude = bundle.getDouble("LONGITUDE");
        latitudeOrigin = bundle.getDouble("LATORG");
        longtitudeOrigin = bundle.getDouble("LNGORG");

        latlng_details = new LatLng(_latitude, _longitude);
        orgLatlng = new LatLng(latitudeOrigin, longtitudeOrigin);

        latlng_details_point = Point.fromLngLat(_longitude,_latitude);
        map = mapboxMap;
        map.setMinZoomPreference(3);
        map.setMaxZoomPreference(20);
        PlaceAutocomplete.clearRecentHistory(mContext);

        mapboxMap.setStyle(Style.MAPBOX_STREETS,
                new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        enableLocationComponent(style);
                        buildingPlugin = new BuildingPlugin(mapView, map, style);
                        buildingPlugin.setMinZoomLevel(15f);
                        buildingPlugin.setVisibility(true);

                        TrafficPlugin trafficPlugin = new TrafficPlugin(mapView, mapboxMap, style);
                        trafficPlugin.setVisibility(false);

                        floatingTraffic.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (map != null) {
                                    trafficPlugin.setVisibility(!trafficPlugin.isVisible());
                                    if(imageView.isShown()){
                                        imageView.setVisibility(View.INVISIBLE);
                                        floatingTraffic.setTitle("Show Traffic Status");
                                    }else {
                                        imageView.setVisibility(View.VISIBLE);
                                        floatingTraffic.setTitle("Hide Traffic Status");
                                    }

                                }
                            }
                        });

                        //ITO NADAGDAG

                        initMarkerIconSymbolLayer(style, orgLatlng,latlng_details);


//                          style.addImage(MARKER_RESTO_ID, BitmapFactory.decodeResource(getResources(), R.drawable.marker_resto));
//                          style.addImage(MARKER_BANK_ID, BitmapFactory.decodeResource(getResources(), R.drawable.marker_bank));
//                          style.addImage(MARKER_TOURISTSPOT_ID, BitmapFactory.decodeResource(getResources(), R.drawable.marker_tourist));
                        style.addImage(("marker_icon"), BitmapFactory.decodeResource(
                                getResources(), R.drawable.marker_icon));

                        // style.addSource(new GeoJsonSource((SOURCE_ID, FeatureCollection.fromFeature())));
                        style.addSource(geoJsonSource);
                        style.addLayer(new SymbolLayer("layer-id", "source-id")
                                .withProperties(
                                        iconImage(RED_PIN_ICON_ID),
                                        iconIgnorePlacement(true),
                                        iconAllowOverlap(true),
                                        iconOffset(new Float[] {0f, -9f})));
                    }
                });


        geoJsonSource = new GeoJsonSource("source-id",
                Feature.fromGeometry(Point.fromLngLat(latlng_details.getLongitude(),
                        latlng_details.getLatitude())));
    }

//    private void getAllRoutes(boolean fromMapClick) {
//        for (String profile : profiles) {
//            getSingleRoute(profile, fromMapClick);
//        }
//
//    }

    /**
     * Display the Directions API route line depending on which profile was last
     * selected.
     */
    private void showRouteLine() {
        if (map != null) {
            map.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {

// Retrieve and update the source designated for showing the directions route
                    GeoJsonSource routeLineSource = style.getSourceAs(ROUTE_SOURCE_ID);

// Create a LineString with the directions route's geometry and
// reset the GeoJSON source for the route LineLayer source
                    if (routeLineSource != null) {
                        switch (lastSelectedDirectionsProfile) {
                            case DirectionsCriteria.PROFILE_DRIVING:
                                routeLineSource.setGeoJson(LineString.fromPolyline(drivingRoute.geometry(),
                                        PRECISION_6));
                                break;
                            case DirectionsCriteria.PROFILE_WALKING:
                                routeLineSource.setGeoJson(LineString.fromPolyline(walkingRoute.geometry(),
                                        PRECISION_6));
                                break;
                            case DirectionsCriteria.PROFILE_CYCLING:
                                routeLineSource.setGeoJson(LineString.fromPolyline(cyclingRoute.geometry(),
                                        PRECISION_6));
                                break;
                            default:
                                break;
                        }
                    }
                }
            });
        }
    }


    /**
     * Make a request to the Mapbox Directions API. Once successful, pass the route to the
     * route layer.
     *
     * @param profile the directions profile to use in the Directions API request
     */
    private void getSingleRoute(String profile) {
        client = MapboxDirections.builder()
                .origin(originPosition)
                .destination(destinationPosition)
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
                        String kms = Double.toString(km)+"km";
                        String seconds =  String.valueOf(TimeUnit.SECONDS
                                .toMinutes(drivingRoute.duration().longValue()));
                        String formatedTime = helperClass.convertMins(seconds);
                        String btnText = formatedTime+"\n"+kms;
                        drivingButton.setText(btnText);
                        if (!firstRouteDrawn) {
                            //showRouteLine();
                            firstRouteDrawn = true;
                        }
                        break;
                    case DirectionsCriteria.PROFILE_WALKING:
                        walkingRoute = response.body().routes().get(0);
                        Double km2 =  Math.round(helperClass.convertMeters(walkingRoute.distance())*100.0)/100.0;
                        String kms2 = Double.toString(km2)+"km";
                        String seconds2 =  String.valueOf(TimeUnit.SECONDS
                                .toMinutes(walkingRoute.duration().longValue()));
                        String formatedTime2 = helperClass.convertMins(seconds2);
                        String btnText2 = formatedTime2+"\n"+kms2;
                        walkingButton.setText(btnText2);
                        break;
                    case DirectionsCriteria.PROFILE_CYCLING:
                        cyclingRoute = response.body().routes().get(0);
                        Double km3 =  Math.round(helperClass.convertMeters(cyclingRoute.distance())*100.0)/100.0;
                        String kms3 = Double.toString(km3)+"km";
                        String seconds3 =  String.valueOf(TimeUnit.SECONDS
                                .toMinutes(cyclingRoute.duration().longValue()));
                        String formatedTime3 = helperClass.convertMins(seconds3);
                        String btnText3 = formatedTime3+"\n"+kms3;
                        cyclingButton.setText(btnText3);
                        ;
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                Timber.e("Error: " + throwable.getMessage());
                Toast.makeText(getActivity(),
                        "Error: " + throwable.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }



    /**
     * Set up the click listeners on the buttons for each Directions API profile.
     */
    private void initButtonClickListeners() {
        drivingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drivingButton.setTextColor(Color.WHITE);
                walkingButton.setTextColor(Color.BLACK);
                cyclingButton.setTextColor(Color.BLACK);
                lastSelectedDirectionsProfile = DirectionsCriteria.PROFILE_DRIVING;
                showRouteLine();
            }
        });
        walkingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drivingButton.setTextColor(Color.BLACK);
                walkingButton.setTextColor(Color.WHITE);
                cyclingButton.setTextColor(Color.BLACK);
                lastSelectedDirectionsProfile = DirectionsCriteria.PROFILE_WALKING;
                showRouteLine();
            }
        });
        cyclingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drivingButton.setTextColor(Color.BLACK);
                walkingButton.setTextColor(Color.BLACK);
                cyclingButton.setTextColor(Color.WHITE);
                lastSelectedDirectionsProfile = DirectionsCriteria.PROFILE_CYCLING;
                showRouteLine();
            }
        });
    }

    /**
     * Move the destination marker to wherever the map was tapped on.
     *
     * @param pointToMoveMarkerTo where the map was tapped on
     */
    private void moveDestinationMarkerToNewLocation(LatLng pointToMoveMarkerTo) {
        map.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                GeoJsonSource destinationIconGeoJsonSource = style.getSourceAs(ICON_SOURCE_ID);
                if (destinationIconGeoJsonSource != null) {
                    destinationIconGeoJsonSource.setGeoJson(Feature.fromGeometry(Point.fromLngLat(
                            pointToMoveMarkerTo.getLongitude(), pointToMoveMarkerTo.getLatitude())));
                }
            }
        });
    }


    /**
     * Add the route and marker sources to the map
     */
    private void initSource(@NonNull Style loadedMapStyle, Point latLng) {
        loadedMapStyle.addSource(new GeoJsonSource(ROUTE_SOURCE_ID));

        iconGeoJsonSource = new GeoJsonSource(ICON_SOURCE_ID, FeatureCollection.fromFeatures(new Feature[] {
                // Feature.fromGeometry(Point.fromLngLat(originPosition.longitude(), originPosition.latitude())),
                Feature.fromGeometry(Point.fromLngLat(latLng.longitude(), latLng.latitude()))}));

    }

    /**
     * Add the route and marker icon layers to the map
     */
    private void initLayers(@NonNull Style loadedMapStyle) {
        routeLayer = new LineLayer(ROUTE_LAYER_ID, ROUTE_SOURCE_ID);

// Add the LineLayer to the map. This layer will display the directions route.
        routeLayer.setProperties(
                lineCap(Property.LINE_CAP_ROUND),
                lineJoin(Property.LINE_JOIN_ROUND),
                lineWidth(5f),
                lineColor(Color.parseColor("#0473ad"))
        );
        loadedMapStyle.addLayer(routeLayer);
// Add the red marker icon image to the map
        loadedMapStyle.addImage(RED_PIN_ICON_ID, BitmapUtils.getBitmapFromDrawable(getResources().getDrawable(R.drawable.marker_icon)));


// Add the red marker icon SymbolLayer to the map
        loadedMapStyle.addSource(iconGeoJsonSource);
        loadedMapStyle.addLayer(new SymbolLayer(ICON_LAYER_ID, ICON_SOURCE_ID).withProperties(
                iconImage(RED_PIN_ICON_ID),
                iconIgnorePlacement(true),
                iconAllowOverlap(true),
                iconOffset(new Float[] {0f, -9f})));
    }



    //Para ito sa Intent Builder ng Search
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            CarmenFeature feature = PlaceAutocomplete.getPlace(data);


            if (map != null) {
                Style style = map.getStyle();
                if (style != null) {
                    GeoJsonSource source = style.getSourceAs(geojsonSourceLayerId);
                    if (source != null) {
                        source.setGeoJson(FeatureCollection.fromFeatures(
                                new Feature[] {Feature.fromJson(feature.toJson())}));
                    }

                    // Move map camera to the selected location
                    LatLng point = new LatLng(((Point) feature.geometry()).latitude(), ((Point) feature.geometry()).longitude());

                    helperClass.setCameraPosition(point, map);

                    if (animator != null && animator.isStarted()) {
                        currentPosition = (LatLng) animator.getAnimatedValue();
                        animator.cancel();
                    }

                    animator = ObjectAnimator
                            .ofObject(latLngEvaluator, currentPosition, point)
                            .setDuration(2000);
                    animator.addUpdateListener(animatorUpdateListener);
                    animator.start();

                    currentPosition = point;
                    originPosition = Point.fromLngLat(myLocation.getLongitude(), myLocation.getLatitude());
                    destinationPosition = Point.fromLngLat(point.getLongitude(), point.getLatitude());
                    map.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                        @Override
                        public void onStyleLoaded(@NonNull Style style) {
                            moveDestinationMarkerToNewLocation(point);
//                            getAllRoutes(true);
                            initButtonClickListeners();
                            initSource(style, destinationPosition);
                            initLayers(style);
                        }
                    });
                    drivingButton.setVisibility(View.VISIBLE);
                    walkingButton.setVisibility(View.VISIBLE);
                    cyclingButton.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    //Location Component
    @SuppressWarnings({"MissingPermission"})
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
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(getActivity());
        }
    }

    /**
     * Set up the LocationEngine and the parameters for querying the device's location
     */
    @SuppressLint("MissingPermission")
    private void initLocationEngine() {
        locationEngine = LocationEngineProvider.getBestLocationEngine(mContext);

        LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
                .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build();

        locationEngine.requestLocationUpdates(request, callback, mContext.getMainLooper());
        locationEngine.getLastLocation(callback);

    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
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

    @Override
    @SuppressLint("MissingPermission")
    public void onStart() {
        super.onStart();
        mapView.onStart();


    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    @SuppressLint("MissingPermission")
    public void onPause() {
        super.onPause();

        mapView.onPause();
    }

    @Override
    @SuppressLint("MissingPermission")
    public void onStop() {
        super.onStop();

        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if(locationEngine != null){
//            locationEngine.deactivate();
//        }
//        mapView.onDestroy();
    }




    private final ValueAnimator.AnimatorUpdateListener animatorUpdateListener =
            new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    LatLng animatedPosition = (LatLng) valueAnimator.getAnimatedValue();
                    geoJsonSource.setGeoJson(Point.fromLngLat(animatedPosition.getLongitude(), animatedPosition.getLatitude()));
                }
            };

    // Class is used to interpolate the marker animation.
    private static final TypeEvaluator<LatLng> latLngEvaluator = new TypeEvaluator<LatLng>() {
        private final LatLng latLng = new LatLng();

        @Override
        public LatLng evaluate(float fraction, LatLng startValue, LatLng endValue) {
            latLng.setLatitude(startValue.getLatitude()
                    + ((endValue.getLatitude() - startValue.getLatitude()) * fraction));
            latLng.setLongitude(startValue.getLongitude()
                    + ((endValue.getLongitude() - startValue.getLongitude()) * fraction));
            return latLng;
        }
    };




    private static class LocationChangeListeningActivityLocationCallback
            implements LocationEngineCallback<LocationEngineResult> {

        private final WeakReference<FragmentGetDirection> activityWeakReference;



        LocationChangeListeningActivityLocationCallback(FragmentGetDirection activity) {
            this.activityWeakReference = new WeakReference<>(activity);
        }

        /**
         * The LocationEngineCallback interface's method which fires when the device's location has changed.
         *
         * @param result the LocationEngineResult object which has the last known location within it.
         */
        @Override
        public void onSuccess(LocationEngineResult result) {
            FragmentGetDirection activity = activityWeakReference.get();

            if (activity != null) {
                activity.myLocation = result.getLastLocation();
                activity.mylocationlatlng = new LatLng(result.getLastLocation().getLatitude(), result.getLastLocation().getLongitude());
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
            FragmentGetDirection activity = activityWeakReference.get();
            if (activity != null) {
                Toast.makeText(activity.getActivity(), exception.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initMarkerIconSymbolLayer(@NonNull Style loadedMapStyle, LatLng org, LatLng des) {
        // Add the marker image to map

        originPosition = Point.fromLngLat(org.getLongitude(), org.getLatitude());
        //Dito ipapasa
        destinationPosition = Point.fromLngLat(des.getLatitude(), des.getLongitude());
        currentPosition = des;
//        Point latlng_details_point = Point.fromLngLat(_longitude,_latitude);
        helperClass.setCameraPosition(org, map);

        map.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                for (String profile : profiles) {
                    moveDestinationMarkerToNewLocation(des);
                    getRoute(profile, map, originPosition, latlng_details_point);
                    initButtonClickListeners();
                }
                initSource(loadedMapStyle, latlng_details_point);
                initLayers(loadedMapStyle);
                startNavigating.setEnabled(true);
                drivingButton.setVisibility(View.VISIBLE);
                walkingButton.setVisibility(View.VISIBLE);
                cyclingButton.setVisibility(View.VISIBLE);
            }
        });

    }

    private void getRoute(String profile, MapboxMap mapboxMap, Point origin, Point destination) {
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
                        String kms = Double.toString(km)+"km";
                        String seconds =  String.valueOf(TimeUnit.SECONDS
                                .toMinutes(drivingRoute.duration().longValue()));
                        String formatedTime = helperClass.convertMins(seconds);
                        String btnText = formatedTime+"\n"+kms;
                        drivingButton.setText(btnText);
                        if (!firstRouteDrawn) {
                            //showRouteLine();
                            firstRouteDrawn = true;
                        }
                        break;
                    case DirectionsCriteria.PROFILE_WALKING:
                        walkingRoute = response.body().routes().get(0);
                        Double km2 =  Math.round(helperClass.convertMeters(walkingRoute.distance())*100.0)/100.0;
                        String kms2 = Double.toString(km2)+"km";
                        String seconds2 =  String.valueOf(TimeUnit.SECONDS
                                .toMinutes(walkingRoute.duration().longValue()));
                        String formatedTime2 = helperClass.convertMins(seconds2);
                        String btnText2 = formatedTime2+"\n"+kms2;
                        walkingButton.setText(btnText2);
                        break;
                    case DirectionsCriteria.PROFILE_CYCLING:
                        cyclingRoute = response.body().routes().get(0);
                        Double km3 =  Math.round(helperClass.convertMeters(cyclingRoute.distance())*100.0)/100.0;
                        String kms3 = Double.toString(km3)+"km";
                        String seconds3 =  String.valueOf(TimeUnit.SECONDS
                                .toMinutes(cyclingRoute.duration().longValue()));
                        String formatedTime3 = helperClass.convertMins(seconds3);
                        String btnText3 = formatedTime3+"\n"+kms3;
                        cyclingButton.setText(btnText3);
                        ;
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