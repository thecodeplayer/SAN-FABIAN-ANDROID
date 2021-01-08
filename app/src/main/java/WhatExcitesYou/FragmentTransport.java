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

public class FragmentTransport extends Fragment implements FirestoreViewPagerInterface {

    private FirebaseFirestore firebaseFirestore;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private View transport_items;
//    ArrayList<RecyclerViewDataModel> dataholder;
    private FirestoreAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        transport_items = inflater.inflate(R.layout.fragment_transport, container, false);
        recyclerView = transport_items.findViewById(R.id.recyclerViewTransport);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        firebaseFirestore = FirebaseFirestore.getInstance();

        Query query = firebaseFirestore.collection("Transport");

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

        return transport_items;
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
//    RecyclerViewDataModel item1 = new RecyclerViewDataModel(R.drawable.cisco, "Luzon Cisco Transport\n Inc. Bus Terminal", "San Fabian, Pangasinan", 16.12982234480173, 120.40866162631501, "This simply known as Cisco Bus is a provincial bus company in the Philippines. It is first provincial terminal here at San Fabian, Pangasinan.");
//        dataholder.add(item1);
//    RecyclerViewDataModel san_fernando_dagupan = new RecyclerViewDataModel(R.drawable.san_fernando_dagupan_minibus, "San Fernando - Dagupan\n Minibus", "Pangasinan-La Union Rd, San Fabian, Pangasinan", 16.121125534030394, 120.4019819532201, "An ordinary and air-conditioned minibus that travels from San Fernando, La Union to Dagupan City and vice versa. Waiting area is in front of the town’s church.");
//        dataholder.add(san_fernando_dagupan);
//    RecyclerViewDataModel san_fabian_dagupan = new RecyclerViewDataModel(R.drawable.san_fabian_dagupan_jeep, "San Fabian - Dagupan\n Jeepney", "Braganza, San Fabian, Pangasinan", 16.120272994739338, 120.40181058880184, "");
//        dataholder.add(san_fabian_dagupan);
//
//        recyclerView.setAdapter(new WhatExcitesYouItemRecyclerViewAdapter(dataholder, this));
//
}
