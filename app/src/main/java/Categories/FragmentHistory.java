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

public class FragmentHistory extends Fragment {

//    ViewPager2 vpHorizontal;
//    ArrayList<CategoriesPagerModel> categoriesPagerModels;

    private Context context;
    private RecyclerView recyclerView;
    private FirestoreCategoriesAdapter adapter;
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView.LayoutManager layoutManager;
    private View history;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        history = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView = history.findViewById(R.id.recyclerview_history);

        firebaseFirestore = FirebaseFirestore.getInstance();

        Query query = firebaseFirestore.collection("History");

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
        return history;
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
//        CategoriesPagerModel hall = new CategoriesPagerModel(R.drawable.hall2, "Municipal Hall", "The first \"municipio or presidência\" was made of bricks and concrete that is now the West Central School. It was built in 1941 adjacent to the private school of Don Juan Alvear - the Jose Rizal College where he taught Secondary, Spanish, religion and occult science. In 1972, the present town hall was built under the administration of Don Hipolito S. Ulanday and almost demolished during World War II. It was used as a classroom for intermediate classes of the San Fabian Elementary School. It underwent a series of major renovations and the last major renovation with modern architectural design and amenities was done under Mayor Conrado P. Gubatan.");
//        categoriesPagerModels.add(hall);
//        CategoriesPagerModel church = new CategoriesPagerModel(R.drawable.church2, "San Fabian Church", "The present church and convent were built in 1860 under the stewardship of Fr. Ramon Fernandez. The church was made of bricks and mortar, they bore the marks Hispanic architecture built with Filipinos sweat and labor that hundreds of Filipinos took turns in making brick glossy.The convent was intended to serve as a hospital or a recuperating center for ailing priests. And the first Filipino priest assigned in San Fabian was Fr. Domingo de Vera in 1899 to 1920.");
//        categoriesPagerModels.add(church);

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