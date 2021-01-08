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
import androidx.viewpager2.widget.ViewPager2;

import com.example.sanfabian.R;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import Adapters.FirestoreCategoriesAdapter;
import Interface.FirestoreViewPagerInterface;
import Models.CategoriesPagerModel;
import Models.RecyclerViewDataModel;

public class FragmentProducts extends Fragment {

//    ViewPager2 vpHorizontal;
//    ArrayList<ViewPagerModel> viewPagerModels;

    private Context context;
    private RecyclerView recyclerView;
    private FirestoreCategoriesAdapter adapter;
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView.LayoutManager layoutManager;
    private View product;

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
        product = inflater.inflate(R.layout.fragment_products, container, false);
        recyclerView = product.findViewById(R.id.recyclerview_products);

        firebaseFirestore = FirebaseFirestore.getInstance();

        Query query = firebaseFirestore.collection("Products");

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
        return product;
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
//        firestoreInterface = this;
//        products = FirebaseFirestore.getInstance().collection("Products");
//        getData();
//        viewPagerModels = new ArrayList<>();
//        ViewPagerModel dragon_fruit = new ViewPagerModel(R.drawable.dragon_fruit2, "Dragon Fruit", "Also known as pitaya that grows on the Hylocereus cactus or Honolulu queen, whose flowers only open at night. The taste of the dragon fruit has been described as a slightly sweet cross between a kiwi and a pear. This fun-looking fruit is a low-calorie fruit that is high in fiber and provides a good amount of several vitamins and minerals.\n" +
//                "Location - Dragon Fruit farm in Brgy. Bigbiga\n" +
//                "Reference from https://www.healthline.com/nutrition/dragon-fruit");
//        viewPagerModels.add(dragon_fruit);
//        ViewPagerModel tuyo = new ViewPagerModel(R.drawable.san_fabian_tuyo2, "Tuyo", "A salted dried fish that is good for breakfast and a proudly product of San Fabian. You can buy Tuyo and other dried fish(daing na bangus, tilapia, dried pusit or squid, dilis, etc.) in many dried fish stalls on the highway of Barangay Cayanga after you pass by the Cayanga bridge.\n" +
//                "Location - Brgy. Sabangan but can be bought in town’s market\n" +
//                "Reference from blogspot.com Hometown Products of San Fabian");
//        viewPagerModels.add(tuyo);
//        ViewPagerModel sukang_nipa = new ViewPagerModel(R.drawable.sukang_nipa2, "Sukang Nipa", "A traditional Filipino vinegar made from the sap of the nipa palm (Nypa fruticans). Tuba is extracted from the stem of a coconut tree better if the trees are with flowers. Tuba is good for making sauce for lumpia, barbecue, etc., as well as making ‘kinilaw’ (raw food). The sap of the coconut vinegar or tuba contains phosphorus, potassium, iron, magnesium, sulfur, boron zinc, manganese and copper, as well as amino acids.\n" +
//                "Location - Brgy. Tocok\n" +
//                "Reference from blogspot.com Hometown Products of San Fabian");
//        viewPagerModels.add(sukang_nipa);
//        ViewPagerModel tinapa = new ViewPagerModel(R.drawable.san_fabian_tinapa2, "Tinapa", "A Filipino culinary term to define smoked fish that is preserved in the process of smoking, it is a popular delicacy which is usually made out of milkfish or galunggong (mackerel scad). It is a popular Filipino breakfast together with garlic fried rice called sinangag, vinegar and egg.\n" +
//                "Location - Brgy. Cayanga\n" +
//                "Reference from angsarap.net Tinapa");
//        viewPagerModels.add(tinapa);
//        ViewPagerModel tupig = new ViewPagerModel(R.drawable.san_fabian_tupig3, "Tupig", "a popular native delicacy from Pangasinan which is made out of ground glutinous rice and coconuts strips wrapped in banana leaves then cooked over charcoal. The name tupig means \"flattened\", in reference to its shape after cooking. It was served only for Christmas and New Year during the old days, but it is now available everyday at the streets of Brgy. Tocok. It is typically eaten with ginger tea (salabat).\n" +
//                "Location - Brgy. Tocok\n" +
//                "Reference from angsarap.net Tupig");
//        viewPagerModels.add(tupig);


//        vpHorizontal.setClipToPadding(false);
//        vpHorizontal.setClipChildren(false);
//        vpHorizontal.setOffscreenPageLimit(3);
//        vpHorizontal.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
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