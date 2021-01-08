package WhatExcitesYou;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanfabian.FragmentDetails;
import com.example.sanfabian.R;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import Adapters.FirestoreAdapter;
import Interface.FirestoreViewPagerInterface;
import Models.RecyclerViewDataModel;

public class FragmentStaycations extends Fragment implements FirestoreViewPagerInterface {

    private FirebaseFirestore firebaseFirestore;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private View staycation_items;
    ArrayList<RecyclerViewDataModel> dataholder;
    private FirestoreAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        staycation_items = inflater.inflate(R.layout.fragment_staycations, container, false);
        recyclerView = staycation_items.findViewById(R.id.recyclerViewStays);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        firebaseFirestore = FirebaseFirestore.getInstance();

        Query query = firebaseFirestore.collection("Staycations");

        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(2)
                .setPageSize(3)
                .build();
        FirestorePagingOptions<RecyclerViewDataModel> options = new FirestorePagingOptions.Builder<RecyclerViewDataModel>()
                .setQuery(query, config, RecyclerViewDataModel.class)
                .build();

        adapter = new FirestoreAdapter(options, this);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        return staycation_items;
    }

    @Override
    public void onItemClick(DocumentSnapshot snapshot, int position) {
        String title = snapshot.getString("title");
        String description = snapshot.getString("description");
        GeoPoint geoPoint = snapshot.getGeoPoint("latlng");
        double lat = geoPoint.getLatitude();
        double lng = geoPoint.getLongitude ();

        Bundle bundle = new Bundle();
        bundle.putString("TITLE", title);
        bundle.putString("DESCRIPTION", description);
        bundle.putDouble("LATITUDE", lat);
        bundle.putDouble("LONGITUDE", lng);
        FragmentDetails details = new FragmentDetails();
        details.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.container, details).addToBackStack(null).commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

//    dataholder = new ArrayList<>();
//    RecyclerViewDataModel a_venue = new RecyclerViewDataModel(R.drawable.ic_launcher_background, "A-venue Beach Resort", "Brgy Nibaliw Vidal, San Fabian", 16.128126266701454, 120.3979575532951, "A simple resort when you can relax and the beach along the resort. The resort has swimming pools, cottages, rooms, and etc. This resort is located at Nibaliw San Fabian, Pangasinan");
//        dataholder.add(a_venue);
//    RecyclerViewDataModel grey_sands_beach_and_resort = new RecyclerViewDataModel(R.drawable.grey_sands, "Grey Sands Beach and\n Resort", "Brgy Nibaliw Vidal, San Fabian", 16.133869220866057, 120.40058881282489, "Formerly known as Boating World Resort, is a popular choice amongst travelers in San Fabian. It is located away from the hostile and bustle of the maddening crowd. It offers 30 rooms, outdoor pool facility consists of an Adult pool, kiddie pool, and water-slides, fun water activities, express check-in and check-out feature, in-house restaurant and many more. Visit their official Facebook Page.\n\nFacebook Page Name: Grey Sands Beach and Resort - San Fabian, Pangasinan\nLink (FB): https://www.facebook.com/greysandsresort");
//        dataholder.add(grey_sands_beach_and_resort);
//    RecyclerViewDataModel hollywood_beach_cottages = new RecyclerViewDataModel(R.drawable.hollywood, "Hollywood Beach Cottages", "Nibaliw West, Barangay Narvarte, San Fabian, Pangasinan", 16.12321405305473, 120.38836121096838, "Is a convenient place located at Barangay Mabilao facing the beachfront.  It offers garden, air-conditioned rooms, 24-hour front desk and shared kitchen for guests, as well as à la carte breakfast that is available every morning. Guests at the accommodation will be able to enjoy activities in and around Mabilao, like hiking and cycling. For more info you can visit their official Facebook page.\n\nFacebook Page Name: Hollywood Beach Cottages\nLink: https://www.facebook.com/sanfabianhollywoodbeach23/");
//        dataholder.add(hollywood_beach_cottages);
//    RecyclerViewDataModel lazy_a_beach_resort = new RecyclerViewDataModel(R.drawable.lazy_a, "Lazy A Beach Resort", "San Fabian, Pangasinan", 16.128660365950992, 120.39628376678526, "A perfect place where you can relax and enjoy with your family and friends. The resort offers swimming pools, rooms, cottages, as well as catering. The resort is located near the San Fabian beach. You can visit their official Facebook page for more info.\n\nFacebook Page Name: Lazy A Beach Resort San Fabian\nLink: https://www.facebook.com/itslazyA/");
//        dataholder.add(lazy_a_beach_resort);
//    RecyclerViewDataModel lbmfi_beach_resort = new RecyclerViewDataModel(R.drawable.lbmfi, "LBMFI Beach Resort", "San Fabian, Pangasinan", 16.11937314375494, 120.38337911282461, "A beach resort for family and friends outing that is near the supermarket. It offers swimming pools (1 for adults, 1 for kids), aircon rooms and cottages, as well as meals. They are also available for reservations. For more info visit their official Facebook Page.\n\nFacebook Page Name: LBMFI Beach Resort\nLink: https://www.facebook.com/LBMFIBeachResort/");
//        dataholder.add(lbmfi_beach_resort);
//    RecyclerViewDataModel nibaleo_beach_resort = new RecyclerViewDataModel(R.drawable.nibaleo_beach_house, "Nibaleo Beach Resort", "San Fabian, Pangasinan", 16.11937314375494, 120.38337911282461, "A resort located in San Fabian beach that offers rooms and nipa hut cottages, billiard table, souvenir shop, swimming pool, and restaurant that serve Filipino and Japanese foods. They are also available for reservations for any special occasions, as well as band and local shows as scheduled. For more details you can visit their official Facebook page and website.\n\nFacebook Page Name: Nibaleo Beach Resort\nLink (FB): https://www.facebook.com/pages/Nibaleo%20Beach%20Resort/1826419881006878/\nLink (Website): http://nibaleobeachresortandrestaurant.synthasite.com/");
//        dataholder.add(nibaleo_beach_resort);
//    RecyclerViewDataModel pta_beach_resort = new RecyclerViewDataModel(R.drawable.pta_beach_resort, "PTA Beach Resort", "San Fabian, Pangasinan", 16.151521912303547, 120.41858701096878, "The resort is operated by the PTA (Philippines Tourism Authority).  It boasts a year-round outdoor pool and views of the sea. Free private parking is available on site. It offers rooms, sheds, a 24-hour front desk at the property, as well as you can play billiards at the resort. For more info you can visit their official Facebook page.\n\nFacebook Page Name: San Fabian PTA Beach Resor\nLink: https://www.facebook.com/San-Fabian-PTA-Beach-Resort-168132086875123/");
//        dataholder.add(pta_beach_resort);
//    RecyclerViewDataModel roheim_farm_and_wellness_resort = new RecyclerViewDataModel(R.drawable.hollywood, "Roheim Farm and\n Wellness Resort", "Nibaliw West, Barangay Narvarte, San Fabian, Pangasinan", 16.137693398406306, 120.46119263980518, "A resort that serves as a unique nook for those who want to get away from the hustle and bustle of the complicated city life that offers overlooking the view of the mountain. It is surely the perfect getaway for events, conferences, seminars, team-building activities, educational tours, workshops, or simply for leisure, relaxation and much more.This exquisite resort offers swimming pools (infinity and whalepool), sheds, hanging gardens, campsite, tent city, biblical scenes tour, gazebo, pond and lagoon, view deck, and many more. Visit their official website and Facebook page.\n\nFacebook Page Name: Roheim Farm and Wellness Resort\nLink (FB): https://www.facebook.com/rfarmwellness/\nLink (Website): http://roheimfarmandresort.com/");
//        dataholder.add(roheim_farm_and_wellness_resort);
//    RecyclerViewDataModel sierra_vista_beach_resort = new RecyclerViewDataModel(R.drawable.sierra_vista, "Sierra Vista Beach Resort", "San Fabian, Pangasinan", 16.13273899035168, 120.40055969562195, "A resort with a nature inspired landscape that offers modern amenities and finest swimming area in Pangasinan, overlooking the wide South China Sea. Available with 16 air-conditioned rooms, restaurant, swimming pools (Adult and kiddie pool), cogon beach shades and beach front. You can visit their official Facebook Page for more info.\n\nFacebook Page Name: Sierra Vista Beach Resort\nLink: https://www.facebook.com/SierraVistaBeachResort/");
//        dataholder.add(sierra_vista_beach_resort);
//    RecyclerViewDataModel shane_beach_resort = new RecyclerViewDataModel(R.drawable.shane_beach_resort, "Shane Beach Resort", "San Fabian, Pangasinan", 16.18888997431389, 120.42569122631572, "A resort facing the Tiblong beachfront that can accommodate 30 to 50 pax. It offers two indoor pools, private pool, restaurant, free shuttle, beach umbrellas, 10 rooms and many more. For more info visit their official Facebook page.\n\nFacebook Page Name: Shane beach resort\nLink: https://www.facebook.com/Shane-beach-resort-118972369507879/");
//        dataholder.add(shane_beach_resort);
//    RecyclerViewDataModel the_waverunner_beach_resort = new RecyclerViewDataModel(R.drawable.waverunner, "The Waverunner Beach\n Resort", "San Fabian, Pangasinan", 16.123660559322634, 120.39018984166127, "A resort located at the San Fabian beach with an ideal place to relax and unwind. The resort has 8 cottages inside the resort and 2 sheds along the shoreline, videoke hut, 4 luxurious 3 room suites, as well as a function hall that is good for seminars, training, birthdays, baptismal, weddings and other special occasions. You can visit their official website and Facebook page for more details.\n\nFacebook Page Name: Waverunner Resort\nLink (FB): https://www.facebook.com/waverunnerresort/\nLink (Website): http://www.waverunnerresort.com/");
//        dataholder.add(the_waverunner_beach_resort);
//    RecyclerViewDataModel tokyo_near_by_river_resort = new RecyclerViewDataModel(R.drawable.tokyo_beach_resort, "Tokyo Near by River Resort", "San Fabian, Pangasinan", 16.123660559322634, 120.39018984166127, "Is one of the famous resorts here in Pangasinan that has a large portion of the area covered with grass. They have dining table floating in the water where you can feel the breeze while eating with your family and relatives. It also offers a private property sandy beach, swimming pool, private dock, boat launching ramp, cottages, volleyball/badminton net, as well as campfire pit (firewood is supplied) that is located at the beach, and many more. Visit their official Facebook page for more info.\n\nFacebook Page Name: Tokyo Resort Beach and Private Pool\nLink: https://www.facebook.com/TokyoPrivateResort/");
//        dataholder.add(tokyo_near_by_river_resort);
//
//        recyclerView.setAdapter(new WhatExcitesYouItemRecyclerViewAdapter(dataholder, this));
}
