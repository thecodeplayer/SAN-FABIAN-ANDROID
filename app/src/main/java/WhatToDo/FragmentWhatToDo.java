package WhatToDo;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.sanfabian.R;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import Adapters.SliderAdapter;
import Models.SliderItem;
import Utilities.HelperClass;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.INTERNET;

public class FragmentWhatToDo extends Fragment{

    private Context mContext;
    private SliderAdapter adapter;
    private static final String OPEN_WEATHER_MAP_URL = "http://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&units=metric";
    private static final String OPEN_WEATHER_MAP_API = "410064f1725089d365cda1c268df31fe";

    private TextView cityField, detailsField, currentTemperatureField, weatherIconField, updatedField;
    Typeface weatherFont;
    static String latitude;
    static String longtitude;
    HelperClass helperClass = new HelperClass();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_what_to_do, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
        adapter = new SliderAdapter(context);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SliderView sliderView = view.findViewById(R.id.imageSlider);

        adapter.addItem(new SliderItem("android.resource://com.example.sanfabian/" + R.drawable.san_fabian_church2));
        adapter.addItem(new SliderItem("android.resource://com.example.sanfabian/" + R.drawable.san_fabian_mangrove2));
        adapter.addItem(new SliderItem("android.resource://com.example.sanfabian/" + R.drawable.san_fabian_tupig2));
        adapter.addItem(new SliderItem("android.resource://com.example.sanfabian/" + R.drawable.san_fabian_sunset2));
        adapter.addItem(new SliderItem("android.resource://com.example.sanfabian/" + R.drawable.san_fabian_bike_trail));

        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

        CardView food_and_dining = view.findViewById(R.id.food_and_dining);
        CardView stays = view.findViewById(R.id.staycation);
        CardView transport = view.findViewById(R.id.transport);
        CardView outdoors_and_activities = view.findViewById(R.id.outdoors_and_activities);
        CardView banks = view.findViewById(R.id.banks);
        CardView relaxation = view.findViewById(R.id.relaxation);
        CardView gas = view.findViewById(R.id.gas_station);
        CardView market = view.findViewById(R.id.marketplace);
        CardView money_converter = view.findViewById(R.id.money_converter);

        food_and_dining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container, new FragmentFoodandDining()).addToBackStack(null).commit();
            }
        });
        stays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container, new FragmentStaycations()).addToBackStack(null).commit();
            }
        });
        transport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container, new FragmentTransport()).addToBackStack(null).commit();
            }
        });
        outdoors_and_activities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container, new FragmentOutdoorsandActivities()).addToBackStack(null).commit();
            }
        });

        banks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container, new FragmentBanks()).addToBackStack(null).commit();
            }
        });

        relaxation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container, new FragmentRelaxation()).addToBackStack(null).commit();
            }
        });

        gas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container, new FragmentGasStation()).addToBackStack(null).commit();
            }
        });

        market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container, new FragmentMarket()).addToBackStack(null).commit();
            }
        });

        money_converter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container, new FragmentConverter()).addToBackStack(null).commit();
            }
        });


        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        requestPermissions();

        latitude = String.valueOf(15.6);
        longtitude = String.valueOf(120.91);

        weatherFont = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(), "fonts/weathericons_regular_webfont.ttf");

        cityField = view.findViewById(R.id.city_field);
        currentTemperatureField = view.findViewById(R.id.current_temperature_field);
        updatedField = view.findViewById(R.id.updated_field);
        detailsField = view.findViewById(R.id.details_field);
        weatherIconField = view.findViewById(R.id.weather_icon);
        weatherIconField.setTypeface(weatherFont);

        try {
            String[] jsonData = getJSONResponse();
            cityField.setText(jsonData[0]);
            detailsField.setText(jsonData[1]);
            currentTemperatureField.setText(jsonData[2]);
            updatedField.setText(jsonData[3]);
            weatherIconField.setText(Html.fromHtml(jsonData[4]));
        } catch (Exception e){

        }



    }

    private String[] getJSONResponse() {
        String[] jsonData = new String[5];
        JSONObject jsonWeather = null;
        try {
            jsonWeather = getWeatherJSON(latitude, longtitude);
        }catch (Exception e){
            Log.d("Error", "Cannot Process JSON results", e);
        }

        try {
            if(jsonWeather != null){
                JSONObject details = jsonWeather.getJSONArray("weather").getJSONObject(0);
                JSONObject main = jsonWeather.getJSONObject("main");
                DateFormat df = DateFormat.getDateInstance();

                String city = jsonWeather.getString("name") + ", "
                        + jsonWeather.getJSONObject("sys").getString("country");
                String description = details.getString("description").toLowerCase(Locale.US);
                String temperature = String.format("%.0f", main.getDouble("temp")) + "\u2103";
                String updatedOn = df.format(new Date(jsonWeather.getLong("dt")*1000));
                String iconText = setWeatherIcon(details.getInt("id"),
                        jsonWeather.getJSONObject("sys").getLong("sunrise")*1000,
                        jsonWeather.getJSONObject("sys").getLong("sunset")*1000);

                jsonData[0] = city;
                jsonData[1] = helperClass.toTitleCase(description);
                jsonData[2] = temperature;
                jsonData[3] = updatedOn;
                jsonData[4] = iconText;
            }
        }catch (Exception e){

        }
        return jsonData;
    }

    private String setWeatherIcon(int actualId, long sunrise, long sunset) {
        int id = actualId /100;
        String icon = "";
        if(actualId == 800){
            long currentTime = new Date().getTime();
            if(currentTime >= sunrise && currentTime <= sunset){
                icon = "&#xf00d;";
            }
            else {
                icon = "&#xf02e;";
            }
        }else {
            switch (id){
                case 2:
                    icon = "&#xf01e;";
                    break;
                case 3:
                    icon = "&#xf01c;";
                    break;
                case 7:
                    icon = "&#xf014;";
                    break;
                case 8:
                    icon = "&#xf013;";
                    break;
                case 6:
                    icon = "&#xf01b;";
                    break;
                case 5:
                    icon = "&#xf019;";
                    break;
            }
        }
        return icon;
    }

    public static JSONObject getWeatherJSON(String lat, String lng){
        try {
            URL url = new URL(String.format(OPEN_WEATHER_MAP_URL, lat, lng));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("x-api-key", OPEN_WEATHER_MAP_API);
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer json = new StringBuffer(1024);
            String tmp = "";

            while ((tmp = reader.readLine()) != null){
                json.append(tmp).append("\n");
            }
            reader.close();
            JSONObject data = new JSONObject(json.toString());
            if(data.getInt("cod") != 200){
                return null;
            }
            return data;
        }catch (Exception e){
            return null;
        }
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{ACCESS_NETWORK_STATE}, 1);
        ActivityCompat.requestPermissions(getActivity(), new String[]{ACCESS_FINE_LOCATION}, 1);
    }



}