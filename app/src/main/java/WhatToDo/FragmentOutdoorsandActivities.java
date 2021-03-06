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
import com.google.firebase.firestore.Transaction;
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
        ArrayList<Transaction> photos = (ArrayList<Transaction>) snapshot.get("photos");
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
        bundle.putSerializable("PHOTOS", photos);
        FragmentDetails details = new FragmentDetails();
        details.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(R.id.container, details).addToBackStack(null).commit();

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
