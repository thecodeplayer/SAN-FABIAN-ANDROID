package WhatToDo;

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

import static java.lang.Float.isNaN;

public class FragmentOutdoorsandActivities extends Fragment implements FirestoreViewPagerInterface {

    private FirebaseFirestore firebaseFirestore;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private View outdoors_and_activities_items;
    private FirestoreAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        outdoors_and_activities_items = inflater.inflate(R.layout.fragment_outdoors_and_activities, container, false);
        recyclerView = outdoors_and_activities_items.findViewById(R.id.recyclerViewOutdoorsandActivities);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        Query query = firebaseFirestore.collection("Outdoors and Activities");

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

        return outdoors_and_activities_items;
    }

    @Override
    public void onItemClick(DocumentSnapshot snapshot, int position) {

        String title = snapshot.getString("title");
        String description = snapshot.getString("description");
        GeoPoint geoPoint = snapshot.getGeoPoint("latlng");
        String collection = "Outdoors and Activities";
        String id = snapshot.getId();
        Double rating = snapshot.getDouble("rating");
        Double nrate = snapshot.getDouble("nrate");
        ArrayList<String> photos = (ArrayList<String>) snapshot.get("photos");
        String photo1 = photos.get(0);
        String photo2 = photos.get(1);
        String photo3 = photos.get(2);
        double lat = geoPoint.getLatitude();
        double lng = geoPoint.getLongitude ();

        double finalRating = rating / nrate;
        if (isNaN((float) finalRating)) finalRating = 0.0;

        Bundle bundle = new Bundle();
        bundle.putString("TITLE", title);
        bundle.putString("DESCRIPTION", description);
        bundle.putString("ID", id);
        bundle.putString("COLLECTION", collection);
        bundle.putDouble("RATING", rating);
        bundle.putDouble("NRATE", nrate);
        bundle.putDouble("FRATING", finalRating);
        bundle.putDouble("LATITUDE", lat);
        bundle.putDouble("LONGITUDE", lng);
        bundle.putString("PHOTO1", photo1);
        bundle.putString("PHOTO2", photo2);
        bundle.putString("PHOTO3", photo3);
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

//    layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);
//
//    dataholder = new ArrayList<>();
//    RecyclerViewDataModel inmalog_bike_trail = new RecyclerViewDataModel(R.drawable.bike_trail, "Inmalog Bike Trail", "Mabilao-Inmalog Sur Road, San Fabian, Pangasinan", 16.159538622523716, 120.44567069747877, "A 13 kilometer stretch of ascending and descending trails which crisscross four villages: Barangay Inmalog Sur, Inmalog Norte, Bolaoen, and Lipit-tomeeng, are for adventurers and amateurs. \n" +
//            "The trail will lead you to:\n" +
//            "Biker's Den - Inmalog Sur\n" +
//            "Highlands Cafe in the Sky \n" +
//            "Susong Dalaga (maiden's breasts) - Bolaoen, one of the attractions in San Fabian.\n");
//        dataholder.add(inmalog_bike_trail);
//    RecyclerViewDataModel mangrove = new RecyclerViewDataModel(R.drawable.san_fabian_mangroove, "Mangrove", "San Fabian", 16.1419, 120.4473, "It is a 10-hectare mangrove plantation located in Bakawan River that was taken care of by the Lupang Pangako Association. You can help the locals in harvesting and planting mangrove. You can visit the official page of Tourism San Fabian for more info on mangroves plantation.\nhttps://www.facebook.com/SanFabianTourism/posts");
//        dataholder.add(mangrove);
//    RecyclerViewDataModel san_fabian_beach = new RecyclerViewDataModel(R.drawable.san_fabian_beach, "San Fabian Beach", "San Fabian, Pangasinan", 16.132293627138562, 120.40049054817221, "You can watch the sunset while relaxing in the sound of the waves and sight seeing the mountain of Cordillera. It offers different activities like riding on a banana boat/boat, swimming, etc., as well as resorts located along the beach if you want to stay overnight. You can rent a cottage and a videoke that is perfect for family and/or friends bonding.");
//        dataholder.add(san_fabian_beach);
//
//        recyclerView.setAdapter(new WhatExcitesYouItemRecyclerViewAdapter(dataholder, this));
}
