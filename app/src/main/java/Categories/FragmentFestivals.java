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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.sanfabian.R;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import Adapters.FirestoreCategoriesAdapter;
import Adapters.ViewPagerAdapter;
import Models.CategoriesPagerModel;

public class FragmentFestivals extends Fragment {

//    ViewPager2 vpHorizontal;
//    ArrayList<CategoriesPagerModel> categoriesPagerModels;

    private Context context;
    private RecyclerView recyclerView;
    private FirestoreCategoriesAdapter adapter;
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView.LayoutManager layoutManager;
    private View festivals;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        festivals = inflater.inflate(R.layout.fragment_festivals, container, false);
        recyclerView = festivals.findViewById(R.id.recyclerview_festivals);

        firebaseFirestore = FirebaseFirestore.getInstance();

        Query query = firebaseFirestore.collection("Festivals");

        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(2)
                .setPageSize(3)
                .build();
        FirestorePagingOptions<CategoriesPagerModel> options = new FirestorePagingOptions.Builder<CategoriesPagerModel>()
                .setQuery(query, config, CategoriesPagerModel.class)
                .build();

        adapter = new FirestoreCategoriesAdapter(options);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);


        LinearSnapHelper snapHelper = new LinearSnapHelper() {
            @Override
            public int findTargetSnapPosition(RecyclerView.LayoutManager lm, int velocityX, int velocityY) {
                View centerView = findSnapView(lm);
                if (centerView == null)
                    return RecyclerView.NO_POSITION;

                int position = lm.getPosition(centerView);
                int targetPosition = -1;
                if (lm.canScrollHorizontally()) {
                    if (velocityX < 0) {
                        targetPosition = position - 1;
                    } else {
                        targetPosition = position + 1;
                    }
                }

                if (lm.canScrollVertically()) {
                    if (velocityY < 0) {
                        targetPosition = position - 1;
                    } else {
                        targetPosition = position + 1;
                    }
                }

                final int firstItem = 0;
                final int lastItem = lm.getItemCount() - 1;
                targetPosition = Math.min(lastItem, Math.max(targetPosition, firstItem));
                return targetPosition;
            }
        };
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
        return festivals;
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

//        vpHorizontal = view.findViewById(R.id.vp_horizontal);

//        categoriesPagerModels = new ArrayList<>();
//        CategoriesPagerModel san_fabian_fiesta = new CategoriesPagerModel(R.drawable.san_fabian_fiesta2, "San Fabian Fiesta", "");
//        categoriesPagerModels.add(san_fabian_fiesta);
//        CategoriesPagerModel darayat_festival = new CategoriesPagerModel(R.drawable.ic_launcher_background, "Darayat Festival", "");
//        categoriesPagerModels.add(darayat_festival);

//        vpHorizontal.setClipToPadding(false);
//        vpHorizontal.setClipChildren(false);
//        vpHorizontal.setOffscreenPageLimit(3);
//        vpHorizontal.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
//        vpHorizontal.setAdapter(new ViewPagerAdapter(categoriesPagerModels));
//
//        CompositePageTransformer transformer = new CompositePageTransformer();
//        transformer.addTransformer(new MarginPageTransformer(8));
//        transformer.addTransformer(new ViewPager2.PageTransformer() {
//            @Override
//            public void transformPage(@NonNull View page, float position) {
//                float v = 1 - Math.abs(position);
//                page.setScaleY(0.8f + v * 0.2f);
//            }
//        });
//
//        vpHorizontal.setPageTransformer(transformer);
