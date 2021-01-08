package com.example.sanfabian;

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

import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import Adapters.FirestoreAdapter;
import Interface.FirestoreViewPagerInterface;
import Models.RecyclerViewDataModel;

public class FragmentHotlines extends Fragment implements FirestoreViewPagerInterface {

    private FirebaseFirestore firebaseFirestore;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private View hotline_items;
    ArrayList<RecyclerViewDataModel> dataholder;
    private FirestoreAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        hotline_items = inflater.inflate(R.layout.fragment_hotlines, container, false);
        recyclerView = hotline_items.findViewById(R.id.recyclerViewHotlines);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        firebaseFirestore = FirebaseFirestore.getInstance();

        Query query = firebaseFirestore.collection("Hotlines");

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

        return hotline_items;
    }

    @Override
    public void onItemClick(DocumentSnapshot snapshot, int position) {
        String title = snapshot.getString("title");
        String img = snapshot.getString("imageUrl");
        System.out.println(img);
        String description = snapshot.getString("description");
        GeoPoint geoPoint = snapshot.getGeoPoint("latlng");
        double lat = geoPoint.getLatitude();
        double lng = geoPoint.getLongitude();

        Bundle bundle = new Bundle();
        bundle.putString("TITLE", title);
        bundle.putString("DESCRIPTION", description);
        bundle.putDouble("LATITUDE", lat);
        bundle.putDouble("LONGITUDE", lng);
        FragmentDetails details = new FragmentDetails();
        details.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, details).addToBackStack(null).commit();
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

    //        dataholder = new ArrayList<>();
//        RecyclerViewDataModel ambulance = new RecyclerViewDataModel(R.drawable.ambulance, "Ambulance", "0918-331-2065", 16.12085704080993, 120.40247056478772, "");
//        dataholder.add(ambulance);
//        RecyclerViewDataModel bfp = new RecyclerViewDataModel(R.drawable.bfp, "Fire Department", "0932-211-7117", 16.123177523797697, 120.4029839728502, "");
//        dataholder.add(bfp);
//        RecyclerViewDataModel mdrrmc = new RecyclerViewDataModel(R.drawable.mdrmmc, "MDRRMC", "0939-575-3737", 16.122039167427502, 120.40301396221042, "");
//        dataholder.add(mdrrmc);
//        RecyclerViewDataModel auxillary = new RecyclerViewDataModel(R.drawable.ic_launcher_background, "Police Auxillary", "0910-055-7023", 16.121083343120283, 120.4026855944084, "");
//        dataholder.add(auxillary);
//        RecyclerViewDataModel pnp = new RecyclerViewDataModel(R.drawable.pnp, "San Fabian PNP", "0916-625-3417", 16.121083343120283, 120.4026855944084, "");
//        dataholder.add(pnp);
//        RecyclerViewDataModel doh = new RecyclerViewDataModel(R.drawable.doh, "Rural Health Unit", "636-38-18", 16.12085704080993, 120.40247056478772, "");
//        dataholder.add(doh);
//        RecyclerViewDataModel medical_centrum_dagupan = new RecyclerViewDataModel(R.drawable.ic_launcher_background, "Medical Centrum\n Dagupan", "(075) 515 2326", 16.052102974773227, 120.35068317896126, "");
//        dataholder.add(medical_centrum_dagupan);
//        RecyclerViewDataModel lmc_hospital = new RecyclerViewDataModel(R.drawable.lmc_hospital, "LMC Hospital", "(075) 515 8852", 16.047852587025243, 120.36879594492105, "");
//        dataholder.add(lmc_hospital);
//        RecyclerViewDataModel the_medical_city_pangasinan = new RecyclerViewDataModel(R.drawable.the_medical_city, "The Medical City\n Pangasinan", "(075) 615 2273", 16.053358957438498, 120.34004017303722, "");
//        dataholder.add(the_medical_city_pangasinan);
//        RecyclerViewDataModel region_one = new RecyclerViewDataModel(R.drawable.region_1_medical_center, "Region I Medical\n Center", "0915 906 3375", 16.054577485646323, 120.34098431105659, "");
//        dataholder.add(region_one);
//        RecyclerViewDataModel nazareth_general_hospital = new RecyclerViewDataModel(R.drawable.ic_launcher_background, "Nazareth General\n Hospital", "(075) 515 6373", 16.04959502066009, 120.34141346409491, "");
//        dataholder.add(nazareth_general_hospital);
//        RecyclerViewDataModel dagupan_doctors_villaflor_memorial_hospital = new RecyclerViewDataModel(R.drawable.doh, "Dagupan Doctors Villaflor\n Memorial Hospital", "(075) 523 2222", 16.047162374719, 120.34827991929532, "");
//        dataholder.add(dagupan_doctors_villaflor_memorial_hospital);
//        RecyclerViewDataModel trauma_center = new RecyclerViewDataModel(R.drawable.dagupan_doctors, "Specialist Group Hospital\n & Trauma Center", "(075) 522 2254", 16.041553230777055, 120.33000614505701, "");
//        dataholder.add(trauma_center);
//        RecyclerViewDataModel dagupan_orthopedic_center = new RecyclerViewDataModel(R.drawable.ic_launcher_background, "Dagupan Orthopedic\n Center", "(075) 523 0876", 16.0405633654543, 120.32794620842672, "");
//        dataholder.add(dagupan_orthopedic_center);
}

