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
import com.example.sanfabian.FragmentDetails4;
import com.example.sanfabian.R;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Transaction;
import java.util.ArrayList;
import Adapters.FirestoreAdapter;
import Interface.FirestoreViewPagerInterface;
import Models.RecyclerViewDataModel;

public class FragmentHistory extends Fragment implements FirestoreViewPagerInterface {

    private Context context;
    private RecyclerView recyclerView;
    private FirestoreAdapter adapter;
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView.LayoutManager layoutManager;
    private View history;

    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        history = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView = history.findViewById(R.id.recyclerview_history);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        Query query = firebaseFirestore.collection("History");

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

        return history;
    }

    @Override
    public void onItemClick(DocumentSnapshot snapshot, int position) {
        String title = snapshot.getString("title");
        String description = snapshot.getString("description");
        String imgUrl = snapshot.getString("imageUrl");
        ArrayList<Transaction> mayors = (ArrayList<Transaction>) snapshot.get("municipal_mayors");

        Bundle bundle = new Bundle();
        bundle.putString("TITLE", title);
        bundle.putString("DESCRIPTION", description);
        bundle.putString("IMAGEURL", imgUrl);
        bundle.putSerializable("MAYORS", mayors);
        FragmentDetails4 details = new FragmentDetails4();
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
