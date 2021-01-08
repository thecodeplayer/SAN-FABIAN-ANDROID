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
import Adapters.ViewPagerAdapter3;
import Models.CategoriesPagerModel;

public class FragmentTouristSpots extends Fragment {

//    ViewPager2 vpHorizontal;
//    ArrayList<CategoriesPagerModel> categoriesPagerModels;

    private Context context;
    private RecyclerView recyclerView;
    private FirestoreCategoriesAdapter adapter;
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView.LayoutManager layoutManager;
    private View tourist_spots;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tourist_spots = inflater.inflate(R.layout.fragment_tourist_spots, container, false);
        recyclerView = tourist_spots.findViewById(R.id.recyclerview_tourist_spots);

        firebaseFirestore = FirebaseFirestore.getInstance();

        Query query = firebaseFirestore.collection("Tourist Spots");

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
        return tourist_spots;
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
//        CategoriesPagerModel san_fabian_church = new CategoriesPagerModel(R.drawable.san_fabian_church3, "Saint Fabian Pope and Martyr, Parish Church", "The belfry was destroyed during the American bombardment, and church bell with shrapnel damage is displayed outside the church. During the American liberation, civilians crowded into the church for protection. In 1952 the belfry was restored and today the church has been fully restored. \n" +
//                "Location: Nibaliw Center Barangay Hall, Braganza St.");
//        categoriesPagerModels.add(san_fabian_church);
//        CategoriesPagerModel crusaders = new CategoriesPagerModel(R.drawable.ic_launcher_background, "Crusaders of the Divine Church of Christ", "Founded by Rufino Sarmiento Magliba in the Philippines. It was there during the year of 1955 in the town that he began his teachings of God. Over the years Rufino Sarmiento Magliba started to gain recognition for his spiritual level, thus being given the title of Monsignor. Because of his talent of healing naturally, he was given the qualification of Doctor of Naturopathy Divine Healer. And he obtained the nicknames Apo Rufing or Apo Lakay as he started having faithful followers.");
//        categoriesPagerModels.add(crusaders);
//        CategoriesPagerModel resorts = new CategoriesPagerModel(R.drawable.resorts2, "San Fabian Beach and Resorts", "Nibaliw West Beach, Mabilao Beach, Bolasi Beach, Sierra Vista Beach Resort, LAZY A Beach Resort, Anne's Sea Breeze Resort, Tokyo Beach Resort, Charissa's Beach Houses, Najeska Beachfront Resort, Nibaleo Beach Resort and Restaurant and the Happy Ripples Beach Resort, Roheim Farm and Resort.");
//        categoriesPagerModels.add(resorts);
//        CategoriesPagerModel municipal_hall = new CategoriesPagerModel(R.drawable.municipal_hall2, "San Fabian Municipal Hall", "Liberated by the US Army on January 9, 1945. The town hall was used by the CIC (Counter Intelligence Corp) and civilian administration reestablished after liberation. During 2003, a new Municipal Hall building was constructed, and since then this building has been empty. In 2009, the old hall was under renovation to be used again in the future. \n" +
//                "Location: Caballero Ave");
//        categoriesPagerModels.add(municipal_hall);
//        CategoriesPagerModel theology = new CategoriesPagerModel(R.drawable.theology2, "Mary Help of Christians Theology Seminary", "An overlooking view of Seminary. The Mary Help of Christians Minor Seminary gave birth to the Mary Help of Christians College Seminary in Bonuan Gueset, Dagupan City in 1985. By the year 2013, the golden jubilee of the elevation of the See of Lingayen Dagupan to a metropolitan archdiocese, the seeds sown in Binmaley had reached its full maturity when the Mary Help of Christians Theology Seminary was opened in Palapad, San Fabian.\n" +
//                "Location: Brgy. Palapad");
//        categoriesPagerModels.add(theology);
//        CategoriesPagerModel park = new CategoriesPagerModel(R.drawable.ic_launcher_background, "Mutya ng Pilipinas Park", "A park in San Fabian that included a total land area of 2.0 hectares for parks, sports and recreational facilities in the town. It consists of different monuments and a garden. It is present for leisurely walk or for relaxation. The park is located near the town hall and town’s market.");
//        categoriesPagerModels.add(park);
//        CategoriesPagerModel white_beach = new CategoriesPagerModel(R.drawable.white_beach2, "PTA Beach Resort (White Beach)", "The resort is operated by the PTA (Philippines Tourism Authority).  It boasts year-round outdoor pool and views of the sea. The resort has a barbecue and views of the pool, and guests can enjoy a drink at the snack bar. Free private parking is available on site. Some rooms have a seating area for your convenience. All rooms are equipped with a private bathroom. You will find a 24-hour front desk at the property. You can play billiards at the resort.\n Location: Brgy. Bolasi");
//        categoriesPagerModels.add(white_beach);
//        CategoriesPagerModel roheim_farm_and_resort = new CategoriesPagerModel(R.drawable.roheim2, "Roheim Farm and Resort", "Roheim Farm and Resort serves as a unique nook for those who want to get away from the hustle and bustle of the complicated city life. It is surely the perfect getaway for events, conferences, seminars, team-building activities, educational tours, workshops, or simply for leisure, relaxation and much more.This exquisite resort offers simple amenities that you will need for a relaxing vacation.\n Location:  Alava Road. Brgy Colisao\n" +
//                "Link: http://roheimfarmandresort.com/");
//        categoriesPagerModels.add(roheim_farm_and_resort);
//        CategoriesPagerModel inmalog_bike_trail = new CategoriesPagerModel(R.drawable.bike_trail2, "Inmalog Bike Trail", "A 13 kilometer stretch of ascending and descending trails which crisscross four villages:Barangay Inmalog Sur; Inmalog Norte; Bolaoen; and Lipit-tomeeng, are for adventurers and amateurs. The trail will lead you to: - Biker's Den - Inmalog Sur - Susong Dalaga ( maiden's breasts) - Bolaoen, one of the attraction in San Fabian.\n" +
//                "Link: https://newsinfo.inquirer.net/1179551/in-pangasinan-village-trails-are-haven-for-mountain-bikers");
//        categoriesPagerModels.add(inmalog_bike_trail);
//        CategoriesPagerModel susong_dalaga = new CategoriesPagerModel(R.drawable.ic_launcher_background, "Susong Dalaga (maiden's breasts)", "A three perfectly shaped hills where you can watch the sun rise or set, as well as sight seeing the San Fabian Beach and Cordillera Mountain. There are different stores in the place if you want something to eat and/or drink. The place is one of the destinations in Inmalog Biking trail, and open for campers.\n" +
//                "Location: Brgy. Bolaoen");
//        categoriesPagerModels.add(susong_dalaga);
//        CategoriesPagerModel hobbit_farmville = new CategoriesPagerModel(R.drawable.hobbit2, "Hobbit Farmville", "It features the infamous ‘Shire’ or the village of the hobbits, which was based on the movie Lord of the Rings. It offers milktea shop with the biggest milk tea in Pangasinan (2.7 liters), samgyupsal, guests may participate in the whole cycle of planting, cultivating and harvesting of crops (depending on the season and the crop) and many more.\n" +
//                "Location: Brgy. Lipit-tomeeng\n" +
//                "Link: https://www.ilovepangasinan.com/hobbit-farmville-in-san-fabian/");
//        categoriesPagerModels.add(hobbit_farmville);
//        CategoriesPagerModel highlands_cafe_in_the_sky = new CategoriesPagerModel(R.drawable.highlands_in_the_sky2, "Highlands Cafe in the Sky", "A Coffee shop located at the  mountain that offer milkteas, coffees (cold and hot), mocktails, silog, mami and goto (unli soup) and more foods everyday with a beautiful ambiance and instagrammable birds nest.\n" +
//                "Location: Brgy. Lipit-tomeeng\n" +
//                "Link: https://www.facebook.com/highlandscafeinthesky/");
//        categoriesPagerModels.add(highlands_cafe_in_the_sky);


//        vpHorizontal.setClipToPadding(false);
//        vpHorizontal.setClipChildren(false);
//        vpHorizontal.setOffscreenPageLimit(3);
//        vpHorizontal.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
//        vpHorizontal.setAdapter(new ViewPagerAdapter3(categoriesPagerModels));
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
