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


public class FragmentBanks extends Fragment implements FirestoreViewPagerInterface {

    private FirebaseFirestore firebaseFirestore;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<RecyclerViewDataModel> dataholder;
    private View banks_items;
    private FirestoreAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        banks_items = inflater.inflate(R.layout.fragment_banks, container, false);
        recyclerView = banks_items.findViewById(R.id.recyclerViewBanks);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        Query query = firebaseFirestore.collection("Banks");

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

        return banks_items;
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

//    layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);
//
//    dataholder = new ArrayList<>();
//    RecyclerViewDataModel bpi_san_san_fabian = new RecyclerViewDataModel(R.drawable.bpi_san_fabian, "BPI LDP Farms ATM", "Main Gate LDP Multifoods Corp., National Road, Brgy, San Fabian, Pangasinan", 16.15351717829747, 120.42454628398845, "");
//        dataholder.add(bpi_san_san_fabian);
//    RecyclerViewDataModel card_sme_bank = new RecyclerViewDataModel(R.drawable.sme_logo, "Card Sme Bank", "Sison, San Fabian, Pangasinan", 16.15351717829747, 120.42454628398845, "");
//        dataholder.add(card_sme_bank);
//    RecyclerViewDataModel producers_bank_san_fabian = new RecyclerViewDataModel(R.drawable.producers_bank_san_fabian, "Producers Bank", "Rizal St, San Fabian, Pangasinan", 16.12078135609204, 120.40438555329519, "");
//        dataholder.add(producers_bank_san_fabian);
//    RecyclerViewDataModel rural_bank_of_rosario = new RecyclerViewDataModel(R.drawable.rural_bank, "Rural Bank of Rosario", "Rizal St, San Fabian, Pangasinan", 16.12045153303307, 120.40384911146438, "");
//        dataholder.add(rural_bank_of_rosario);
//    RecyclerViewDataModel rural_bank_san_fabian = new RecyclerViewDataModel(R.drawable.rural_bank_san_fabian, "Rural Bank of San Fabian\n Inc.", "35 Rizal St, San Fabian, 2433 Pangasinan", 16.120757128221967, 120.40446908213173, "");
//        dataholder.add(rural_bank_san_fabian);
//
//        recyclerView.setAdapter(new WhatExcitesYouItemRecyclerViewAdapter(dataholder, this));
}
