package Categories;

import android.content.Context;
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

public class FragmentTouristSpots extends Fragment implements FirestoreViewPagerInterface {

    private Context context;
    private RecyclerView recyclerView;
    private FirestoreAdapter adapter;
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView.LayoutManager layoutManager;
    private View tourist_spots;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        tourist_spots = inflater.inflate(R.layout.fragment_tourist_spots, container, false);
        recyclerView = tourist_spots.findViewById(R.id.recyclerview_tourist_spots);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));


        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        Query query = firebaseFirestore.collection("Tourist Spots");

        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(5)
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

        return tourist_spots;
    }

    @Override
    public void onItemClick(DocumentSnapshot snapshot, int position) {
        String title = snapshot.getString("title");
        String description = snapshot.getString("description");
        String imageurl = snapshot.getString("imageUrl");
        String collection = "Tourist Spots";
        String id = snapshot.getId();
        Double rating = snapshot.getDouble("rating");
        Double nrate = snapshot.getDouble("nrate");
        GeoPoint geoPoint = snapshot.getGeoPoint("latlng");
        ArrayList<Transaction> photos = (ArrayList<Transaction>) snapshot.get("photos");
        double lat = geoPoint.getLatitude();
        double lng = geoPoint.getLongitude ();

        double finalRating = rating / nrate;
        if (isNaN((float) finalRating)) finalRating = 0.0;

        Bundle bundle = new Bundle();
        bundle.putString("IMAGEURL", imageurl);
        bundle.putString("TITLE", title);
        bundle.putString("ID", id);
        bundle.putString("COLLECTION", collection);
        bundle.putString("DESCRIPTION", description);
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
