package com.example.sanfabian;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentAboutUs extends Fragment {

    TextView _credits, facebook_credits, website_credits;
    private View about_us;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        about_us = inflater.inflate(R.layout.fragment_aboutus, container, false);
        _credits = about_us.findViewById(R.id.credits);
        facebook_credits = about_us.findViewById(R.id.facebook_credits);
        website_credits = about_us.findViewById(R.id.website_credits);
        _credits.setText(
                "Municipality of San Fabian Tourism Office\n" +
                "Pangasinan-English Dictionary App\n" +
                "https://www.facebook.com/SanFabianTourism/posts\n");

        facebook_credits.setText(
                "https://web.facebook.com/Chollys-Grill-And-Seafood-Restaurant-291561201383482/\n" +
                "https://www.facebook.com/highlandscafeinthesky/\n" +
                "https://web.facebook.com/LittleBozzCoffeehouse/\n" +
                "https://web.facebook.com/ilovemilkteasanfabian/\n" +
                "https://web.facebook.com/MattNooHou/\n" +
                "https://web.facebook.com/Paps-Resto-and-Cafe-2297889673821854/\n" +
                        "https://web.facebook.com/nikskitchenettesanfabian/\n" +
                        "https://web.facebook.com/ViKopaPh/\n" +
                        "https://www.facebook.com/itslazyA/\n" +
                        "https://www.facebook.com/LBMFIBeachResort/\n" +
                        "https://www.facebook.com/greysandsresort\n" +
                        "https://www.facebook.com/sanfabianhollywoodbeach23/\n" +
                        "https://www.facebook.com/SierraVistaBeachResort/\n" +
                        "https://www.facebook.com/Shane-beach-resort-118972369507879/\n" +
                        "https://www.facebook.com/waverunnerresort/\n" +
                        "https://www.facebook.com/pages/Nibaleo%20Beach%20Resort/1826419881006878/\n" +
                        "https://www.facebook.com/TokyoPrivateResort/\n" +
                        "https://www.facebook.com/rfarmwellness/\n" +
                        "https://www.facebook.com/San-Fabian-PTA-Beach-Resort-168132086875123/\n" +
                        "https://facebook.com/impressionSpa/" +
                "https://facebook.com/Essential-wellness-center-112951970288528/");

        website_credits.setText(
                "https://www.cafe-tribu.com/\n" +
                "https://unlify.business.site/\n" +
                        "https://www.7-eleven.com.ph/\n" +
                "https://niks-kitchenette.business.site/\n" +
                "http://www.waverunnerresort.com/\n" +
                "http://nibaleobeachresortandrestaurant.synthasite.com/\n" +
                "http://roheimfarmandresort.com/\n" +
                "https://www.up.edu.ph/is-this-man-the-father-of-up/\n" +
                "https://www.abebooks.com/9789718820001/Ferdinand-E-Marcos-Malacan%CC%83ang-Makiki-9718820000/plp\n" +
                "http://angbuhaysaearth.blogspot.com/2016/07/bb-pilipinas-candidates-in-miss.html\n" +
                "blogspot.com\n" +
                "angsarap.net\n" +
                "philatlas.com\n");

        return about_us;
    }
}
