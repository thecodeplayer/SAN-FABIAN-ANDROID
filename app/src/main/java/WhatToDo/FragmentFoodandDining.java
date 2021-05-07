package WhatToDo;

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

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import Adapters.FirestoreAdapter;
import Interface.FirestoreViewPagerInterface;
import Models.RecyclerViewDataModel;

import static java.lang.Float.isNaN;

public class FragmentFoodandDining extends Fragment implements FirestoreViewPagerInterface {

    private FirestoreAdapter adapter;
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private View food_and_dining_items;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        food_and_dining_items = inflater.inflate(R.layout.fragment_food_and_dining, container, false);
        recyclerView = food_and_dining_items.findViewById(R.id.recyclerViewFoodandDining);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        

        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        Query query = firebaseFirestore.collection("Food and Dining");

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


        return food_and_dining_items;

    }

    @Override
    public void onItemClick(DocumentSnapshot snapshot, int position) {
        String title = snapshot.getString("title");
        String description = snapshot.getString("description");
        String collection = "Food and Dining";
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

//        dataholder = new ArrayList<>();
//        RecyclerViewDataModel by_the_beach_cafe = new RecyclerViewDataModel(R.drawable.by_the_beach_cafe, "By The Beach Cafe", "San Fabian, Pangasinan", 16.1358, 120.4023, "It is one of the best places to chill and relax in San Fabian Beach. You can order your drinks and food until 11 pm. Also there are available rooms for reservation if you want to stay.");
//        dataholder.add(by_the_beach_cafe);
//        RecyclerViewDataModel cafe_tribu = new RecyclerViewDataModel(R.drawable.cafe_tribu, "Cafe Tribu", "Pangasinan-La Union Rd, San Fabian, Pangasinan", 16.11961665051088, 120.40103241096838, "A coffee shop that offers different drinks like freeze blend, tea coolers, hot coffee, ice coffee and additional foods like pastries, pasta, rice meals, etc. For more info you can visit their website.");
//        dataholder.add(cafe_tribu);
//        RecyclerViewDataModel calyx_restaurant = new RecyclerViewDataModel(R.drawable.calyx_restaurant, "Calyx Restaurant", "Quezon Highway, San Fabian, Pangasinan", 16.120032535987853, 120.40148871096832, "");
//        dataholder.add(calyx_restaurant);
//        RecyclerViewDataModel chollys_restaurant = new RecyclerViewDataModel(R.drawable.chollys_restaurant, "Cholly's Grill Seafood &\n Panciteria Restaurant", "131 Pangasinan-La Union Rd, San Fabian, Pangasinan", 16.123637866179344, 120.40302618398808, "A restaurant that offers different seafood dishes, lutong bahay and pancit in bilao (winnowing basket). They also cater to different occasions. For more info you can visit their official Facebook page.\n\n Facebook Page Name: Cholly's Grill And Seafood Restaurant\nLink: https://web.facebook.com/Chollys-Grill-And-Seafood-Restaurant-291561201383482/");
//        dataholder.add(chollys_restaurant);
//        RecyclerViewDataModel highlands_cafe_in_the_sky = new RecyclerViewDataModel(R.drawable.highlands, "Highlands Cafe In The Sky", "Mabilao-Inmalog Sur Road, San Fabian, Pangasinan", 16.159400137917522, 120.44598942674257, "A Coffee shop located at the  mountain that offers milk teas, coffees (cold and hot), mocktails, silog, mami and goto (unli soup) and more foods every day with a beautiful ambiance and instagrammable birds nest. You can visit their official Facebook Page.\n\nFacebook Page Name: Highlands Cafe In The Sky\nLink: https://www.facebook.com/highlandscafeinthesky/");
//        dataholder.add(highlands_cafe_in_the_sky);
//        RecyclerViewDataModel lil_bozz_cafe = new RecyclerViewDataModel(R.drawable.lil_bozz, "Lil Bozz Cafe", "131 Pangasinan-La Union Rd, San Fabian, Pangasinan", 16.123718959246574, 120.40301335329501, "A coffee house that offers a convenient place for meet ups and relaxation along with foods and drinks like coffee, milk and tea blends, as well as different lines of cakes, pizza and other finger foods. You can visit their official Facebook Page.\n\nFacebook Page Name: Little Bozz Coffee House\nLink: https://web.facebook.com/LittleBozzCoffeehouse/");
//        dataholder.add(lil_bozz_cafe);
//        RecyclerViewDataModel mama_deehs = new RecyclerViewDataModel(R.drawable.mama_deehs, "Mama Deeh’s Pigar-Pigar &\n Bulalo", "San Fabian, Pangasinan", 16.124553809039085, 120.4069079209867, "");
//        dataholder.add(mama_deehs);
//        RecyclerViewDataModel milk_tea_haus = new RecyclerViewDataModel(R.drawable.milktea_haus, "Milk Tea Haus", "Pangasinan - La Union Rd, San Fabian, Pangasinan", 16.11871585867054, 120.40089187049782, "ilovemilktea is a coffee house that offers a convenient place for meet ups and bonding along with foods and drinks like milk tea, fruit tea, cream puff, choco puff, frappe, rock salt cheese, as well as other finger foods and many more. Visit their official Facebook page for more info.\n\nFacebook Page Name: Ilovemilktea San Fabian\nLink: https://web.facebook.com/ilovemilkteasanfabian/");
//        dataholder.add(milk_tea_haus);
//        RecyclerViewDataModel matthews = new RecyclerViewDataModel(R.drawable.matthews, "Matthew's Noodle House", "Pangasinan-La Union Rd, San Fabian, Pangasinan", 16.11942704518389, 120.40103134537384, "A noodle house restaurant that started August 10, 2006. It offers different noodle foods like noodle pasta, noodle soup, pancit in bilao (winnowing basket), as well as finger foods, meals with rice, beverages and many more. For more info you can visit their official Facebook page.");
//        dataholder.add(matthews);
//        RecyclerViewDataModel mauis_restaurant = new RecyclerViewDataModel(R.drawable.mauis_restaurant, "Maui's Kambingan &\n Seafood Restaurant", "Sagud-Bahley, San Fabian, Pangasinan", 16.12179764846909, 120.39723333721174, "");
//        dataholder.add(mauis_restaurant);
//        RecyclerViewDataModel panlasang_dalampasigan = new RecyclerViewDataModel(R.drawable.panlasang_dalampasigan, "Panlasang Dalampasigan", "Cayanga Bridge, San Fabian, Pangasinan", 16.11333178172017, 120.39733304761857, "It is a mini restaurant that is located beside the shell gasoline station. They serve IHAW-IHAW, SEAFOODS, and etc. And also they are available for catering services.");
//        dataholder.add(panlasang_dalampasigan);
//        RecyclerViewDataModel niks_kitchenette = new RecyclerViewDataModel(R.drawable.niks_kitchenette, "Nik’s Kitchenette", "Cayanga Bridge, San Fabian, Pangasinan", 16.118570565942008, 120.40077098398805, "It is a fast food restaurant that offers convenient place for eating along with different drinks and foods like baked sushi, chicken leg in bilao (winnowing basket), fried chicken wings, flavored fried chicken (buffalo, bonchon style, plain gravy), lumpia in bilao, pasta in bilao and many more. Visit their official Facebook page and/or website for more info.\n\nFacebook Page Name: Nik's Kitchenette\nLink (Fb): https://web.facebook.com/nikskitchenettesanfabian/\nLink (website): https://niks-kitchenette.business.site/");
//        dataholder.add(niks_kitchenette);
//        RecyclerViewDataModel paps = new RecyclerViewDataModel(R.drawable.paps_restaurant, "Pap's Resto And Cafe", "San Fabian, Pangasinan", 16.125460943517744, 120.40491876864165, "A restaurant that offers a beautiful place, vegetarian menu like vegan menu, different meals, as well as pasalubong (souvenir) like fiesta sliced bread , dulong garlic crisp , gourmet tuyo, butcheron. It also accepts reservations for all occasions. For more info visit their official Facebook page.\n\nFacebook Page Name: Pap's Resto and Cafe\nLink: https://web.facebook.com/Paps-Resto-and-Cafe-2297889673821854/");
//        dataholder.add(paps);
//        RecyclerViewDataModel unlify = new RecyclerViewDataModel(R.drawable.unlify, "Unlify", "San Fabian, Pangasinan", 16.115164263935686, 120.39872823015514, "It is a good place for bonding and for food lovers. It offers unlimited foods like chicken wings, lechon kawali, pasta, fries, takoyaki, milk tea and nachos, as well as pancit in bilao (winnowing basket) and combo meals. You can visit their website.\n\nLink: https://unlify.business.site/");
//        dataholder.add(unlify);
//        RecyclerViewDataModel vi_kopa = new RecyclerViewDataModel(R.drawable.vi_kopa, "Vi Kopa", "San Fabian, Pangasinan", 16.114442297536755, 120.39899924983285, "A milk tea house that started July, 2020. It offers different flavors of milk teas in different styles of bottle, bubble tea and it serves the best burger in town. You can visit their Facebook Page for more info.\n\nFacebook Page Name: Vi Kopa\nLink: https://web.facebook.com/ViKopaPh/\n");
//        dataholder.add(vi_kopa);
//        RecyclerViewDataModel woa_pieng = new RecyclerViewDataModel(R.drawable.woa_pieng_food_haus, "Woa Pieng Food Haus\n Dine X Shop", "San Fabian, Pangasinan", 16.11961665051088, 120.40103241096838, "");
//        dataholder.add(woa_pieng);
//        RecyclerViewDataModel seven_eleven = new RecyclerViewDataModel(R.drawable.seven_eleven, "7-Eleven", "Pangasinan - La Union Rd, San Fabian, Pangasinan", 16.11957650687199, 120.40122986864142, "It is a convenience store that offers grab-and-go foods/beverages, as well as assorted newsstand items. They also offer CLiQQ, an app for rewards and payments. It is open 24 hours. You can visit their official website for more info. \nLink: https://www.7-eleven.com.ph/");
//        dataholder.add(seven_eleven);
//
//        recyclerView.setAdapter(new WhatExcitesYouItemRecyclerViewAdapter(dataholder, this));
