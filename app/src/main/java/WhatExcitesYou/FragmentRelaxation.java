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

public class FragmentRelaxation extends Fragment implements FirestoreViewPagerInterface {

    private FirebaseFirestore firebaseFirestore;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<RecyclerViewDataModel> dataholder;
    private View relaxation_items;
    private LayoutInflater layoutInflater;
    private FirestoreAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        relaxation_items = inflater.inflate(R.layout.fragment_relaxation, container, false);
        recyclerView = relaxation_items.findViewById(R.id.recyclerViewRelaxation);
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        Query query = firebaseFirestore.collection("Relaxation");

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

        return relaxation_items;
    }


    @Override
    public void onItemClick(DocumentSnapshot snapshot, int position) {
        String title = snapshot.getString("title");
        String img = snapshot.getString("imageUrl");
        System.out.println(img);
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
}

//
//        dataholder = new ArrayList<>();
//        RecyclerViewDataModel impression_spa_san_fabian = new RecyclerViewDataModel(R.drawable.impression_spa_san_fabian, "Impression Spa - San\n Fabian", "San Fabian, Pangasinan", 16.12520206406347, 120.40503554166139, "A spa that uses diverse manual techniques of touch, stroking to muscles and soft tissue to achieve relaxation and improve the client's well being. It offers different treatment rooms for massage and skincare service, a little restaurant, and accepts home service. For more information you can visit their official Facebook Page.\n" +
//                "Facebook Page Name: Impression Spa at San Fabian Pangasinan\n" +
//                "Link: https://facebook.com/impressionSpa/");
//        dataholder.add(impression_spa_san_fabian);
//        RecyclerViewDataModel essential_wellness_center = new RecyclerViewDataModel(R.drawable.essential_wellness_center_san_fabian, "Essential Wellness Center", "San Fabian, 2433 Pangasinan", 16.12845349913594, 120.40754398213193, "It is a place with different treatment rooms that offers different massage services, myotherapy, steam sauna, different acupuncture and detoxification services. You can visit their official Facebook Page for more info.\n" +
//                "Facebook Page Name: Essential wellness center\n" +
//                "Link: https://facebook.com/Essential-wellness-center-112951970288528/");
//        dataholder.add(essential_wellness_center);
//