package Categories;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.sanfabian.FragmentBarangayDetails;
import com.example.sanfabian.FragmentDetails;
import com.example.sanfabian.FragmentDetails2;
import com.example.sanfabian.R;
import com.firebase.ui.firestore.SnapshotParser;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;

import Adapters.FirestoreAdapter;
import Adapters.FirestoreCategoriesAdapter;
import Interface.FirestoreViewPagerInterface;
import Models.BarangayModel;
import Models.CategoriesPagerModel;
import Models.RecyclerViewDataModel;

public class FragmentBarangays extends Fragment implements FirestoreViewPagerInterface {

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
        banks_items = inflater.inflate(R.layout.fragment_barangays, container, false);
        recyclerView = banks_items.findViewById(R.id.recyclerViewBanks);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        Query query = firebaseFirestore.collection("Barangays");

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
        String imgUrl = snapshot.getString("imageUrl");
        String hotline = snapshot.getString("hotline");
        GeoPoint geoPoint = snapshot.getGeoPoint("latlng");
        String attraction = snapshot.getString("attraction");

        ArrayList<String> attractions = (ArrayList<String>) snapshot.get("attractions");
        ArrayList<String> products = (ArrayList<String>) snapshot.get("products");
        ArrayList<String> attractions_name = (ArrayList<String>) snapshot.get("name_of_attractions");
        ArrayList<String> product_names = (ArrayList<String>) snapshot.get("name_of_products");
        String attraction1 = attractions.get(0);
        String attraction2 = attractions.get(1);
        String attraction3 = attractions.get(2);
        String product1 = products.get(0);
        String product2 = products.get(1);
        String product3 = products.get(2);
        String product_name1 = product_names.get(0);
        String product_name2 = product_names.get(1);
        String product_name3 = product_names.get(2);
        String attraction_name1 = attractions_name.get(0);
        String attraction_name2 = attractions_name.get(1);
        String attraction_name3 = attractions_name.get(2);
        double lat = geoPoint.getLatitude();
        double lng = geoPoint.getLongitude ();

        Bundle bundle = new Bundle();
        bundle.putString("TITLE", title);
        bundle.putString("DESCRIPTION", description);
        bundle.putString("IMAGEURL", imgUrl);
        bundle.putString("HOTLINE", hotline);
        bundle.putDouble("LATITUDE", lat);
        bundle.putDouble("LONGITUDE", lng);
        bundle.putString("ATTRACTION", attraction);
        bundle.putString("PRODUCT1", product1);
        bundle.putString("PRODUCT2", product2);
        bundle.putString("PRODUCT3", product3);
        bundle.putString("ATTRACTION1", attraction1);
        bundle.putString("ATTRACTION2", attraction2);
        bundle.putString("ATTRACTION3", attraction3);
        bundle.putString("PRODUCT_NAME1", product_name1);
        bundle.putString("PRODUCT_NAME2", product_name2);
        bundle.putString("PRODUCT_NAME3", product_name3);
        bundle.putString("ATTRACTION_NAME1", attraction_name1);
        bundle.putString("ATTRACTION_NAME2", attraction_name2);
        bundle.putString("ATTRACTION_NAME3", attraction_name3);
        FragmentBarangayDetails details = new FragmentBarangayDetails();
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
