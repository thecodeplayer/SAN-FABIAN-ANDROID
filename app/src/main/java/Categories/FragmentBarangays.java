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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

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

import Adapters.FirestoreCategoriesAdapter;
import Models.BarangayModel;
import Models.CategoriesPagerModel;

public class FragmentBarangays extends Fragment {

    private Context context;
    ViewPager2 vpHorizontal;
    ArrayList<BarangayModel> barangayModels;
    FusedLocationProviderClient fusedLocationProviderClient;
    Button _getDirection;
    private int position;
    Location location;
    LatLng origin_position;
    private RecyclerView recyclerView;
    private FirestoreCategoriesAdapter adapter;
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView.LayoutManager layoutManager;
    private View barangays;
    GeoPoint latlng;
    Double lat, lng;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        barangays = inflater.inflate(R.layout.fragment_barangays, container, false);
        recyclerView = barangays.findViewById(R.id.recyclerview_barangays);

        firebaseFirestore = FirebaseFirestore.getInstance();

        Query query = firebaseFirestore.collection("Barangays");

        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(2)
                .setPageSize(3)
                .build();
        FirestorePagingOptions<CategoriesPagerModel> options = new FirestorePagingOptions.Builder<CategoriesPagerModel>()
                .setQuery(query, config, new SnapshotParser<CategoriesPagerModel>() {
                    @NonNull
                    @Override
                    public CategoriesPagerModel parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                        CategoriesPagerModel categoriesPagerModel = snapshot.toObject(CategoriesPagerModel.class);
                        latlng =  snapshot.getGeoPoint("latlng");
                        lat = latlng.getLatitude();
                        lng = latlng.getLongitude ();
                        return categoriesPagerModel;
                    }
                })
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


        return barangays;
    }



    @SuppressLint("MissingPermission")
    private void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
//                    Double latitude = location.getLatitude();
//                    Double longitude = location.getLongitude();

                    origin_position = new LatLng(location.getLatitude(), location.getLongitude());
                }
            }
        });
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

//    vpHorizontal = view.findViewById(R.id.vp_horizontal);
//    _getDirection = view.findViewById(R.id.barangayGetDirection);
//    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mContext);
//
//    barangayModels = new ArrayList<>();
//    BarangayModel poblacion = new BarangayModel(R.drawable.hall, "Poblacion", "A barangay in the municipality of San Fabian that has a population of 4,002 based on the 2015 Census, that is 4.82% of the San fabian total population.\nIt shares a common border with barangay(s): Cabaruan, Cayanga, Lekep-Butao, Nibaliw Central, Sagud-Bahley, and Tempra-Guilig.\n\nArea(has): 59.38\nPopulation: 4002", 16.1230, 120.4063);
//        barangayModels.add(poblacion);
//    BarangayModel tempra_guilig = new BarangayModel(R.drawable.hall, "Tempra-Guilig", "A barangay in the municipality of San Fabian that has a population of 3,886 based on the 2015 Census, that is 4.68% of the San fabian total population.\n It shares a common border with barangay(s): Cabaruan, Nibaliw Central, Nibaliw East, Poblacion, Sobol, and Tocok.\n\nArea(has): 70.15\nPopulation: 3886", 16.1312, 120.4119);
//        barangayModels.add(tempra_guilig);
//    BarangayModel nibaliw_vidal = new BarangayModel(R.drawable.hall, "Nibaliw Vidal", "A barangay in the municipality of San Fabian that has a population of 3,405 based on the 2015 Census, that is 4.10% of the San fabian total population.\n It shares a common border with barangay(s): Nibaliw Central, Nibaliw East, Nibaliw Magliba, and Sagud-Bahley.\n\nArea(has): 68.44\nPopulation: 3405", 16.1275, 120.3959);
//        barangayModels.add(nibaliw_vidal);
//    BarangayModel nibaliw_center = new BarangayModel(R.drawable.hall, "Nibaliw Center", "A barangay in the municipality of San Fabian that has a population of 979 based on the 2015 Census, that is 1.18% of the San fabian total population.\n  It shares a common border with barangay(s): Cayanga, Nibaliw East, Nibaliw Vidal, Poblacion, Sagud-Bahley, and Tempra-Guilig.\n\nArea(has): 16.16\nPopulation: 979", 16.1282, 120.4021);
//        barangayModels.add(nibaliw_center);
//    BarangayModel cayanga = new BarangayModel(R.drawable.hall, "Cayanga", "A barangay in the municipality of San Fabian that has a population of 4,586 based on the 2015 Census, that is 5.52% of the San fabian total population.\n It shares a common border with barangay(s): Cabaruan, Lekep-Butao, Longos Proper, Longos, Longos Amongonan Parac-parac, Nibaliw Central, Nibaliw Narvarte, Poblacion, and Sagud-Bahley.\n\nArea(has): 84.99\nPopulation: 4586", 16.1158, 120.3995);
//        barangayModels.add(cayanga);
//    BarangayModel nibaliw_east = new BarangayModel(R.drawable.hall, "Nibaliw East", "A barangay in the municipality of San Fabian that has a population of 2,464 based on the 2015 Census, that is 2.97% of the San fabian total population.\n It shares a common border with barangay(s): Nibaliw Central, Nibaliw Vidal, Sagud-Bahley, Sobol,  and Tempra-Guilig.\n\nArea(has): 44.77\nPopulation: 2464", 16.1342, 120.4042);
//        barangayModels.add(nibaliw_east);
//    BarangayModel sagud_bahley = new BarangayModel(R.drawable.hall, "Sagud Bahley", "A barangay in the municipality of San Fabian that has a population of 4,125 based on the 2015 Census, that is 4.97% of the San fabian total population\n It shares a common border with barangay(s): Cayanga, Nibaliw Central, Nibaliw East, Nibaliw Magliba, Nibaliw Narvarte, Nibaliw Vidal, and Poblacion.\n\nArea(has): 41.27\nPopulation: 4125", 16.1202, 120.3959);
//        barangayModels.add(sagud_bahley);
//    BarangayModel angio = new BarangayModel(R.drawable.hall, "Angio", "A barangay in the municipality of San Fabian that has a population of 3,227 based on the 2015 Census, that is 3.89% of the San fabian total population.\n It shares a common border with barangay(s): Anonang, Aramal, Cabaruan, Lekep-Butao, Macayug(San Jacinto), and Tocok.\n\nArea(has): 279.53\nPopulation: 3227", 16.1163, 120.4303);
//        barangayModels.add(angio);
//    BarangayModel anonang = new BarangayModel(R.drawable.hall, "Anonang", "A barangay in the municipality of San Fabian that has a population of 4,520 based on the 2015 Census, that is 5.44% of the San fabian total population.\n It shares a common border with barangay(s): Angio, Aramal, Binday, Bolo(San Jacinto), Labney(San Jacinto), Lekep-Butao,  Macayug(San Jacinto), and Santa Cruz(San Jacinto).\n\nArea(has): 488.72\nPopulation: 4520", 16.1115, 120.4469);
//        barangayModels.add(anonang);
//    BarangayModel alacan = new BarangayModel(R.drawable.hall, "Alacan", "A barangay in the municipality of San Fabian that has a population of 1,812 based on the 2015 Census, that is 2.18% of the San fabian total population.\n It shares a common border with barangay(s): Bigbiga, Inmalog Norte, Inmalog Sur, Lipit-Tomeeng, Mabilao, and Tiblong.\n\nArea(has): 173.14\nPopulation: 1812", 16.1787, 16.1787);
//        barangayModels.add(alacan);
//    BarangayModel bolasi = new BarangayModel(R.drawable.hall, "Bolasi", "A barangay in the municipality of San Fabian that has a population of 2,983 based on the 2015 Census, that is 3.59% of the San fabian total population.\n It shares a common border with barangay(s): Aramal, Lipit-Tomeeng, Mabilao, Palapad, and Tocok.\n\nArea(has): 224.97\nPopulation: 2983", 16.1491, 120.4317);
//        barangayModels.add(bolasi);
//    BarangayModel lekep_butao = new BarangayModel(R.drawable.hall, "Lekep-Butao", "A barangay in the municipality of San Fabian that has a population of 2,796 based on the 2015 Census, that is 3.37% of the San fabian total population.\n It shares a common border with barangay(s): Angio, Anonang, Cabaruan, Cayanga, Inlambo(Mangaldan), Longos, Macayug(San Jacinto), Palua(Mangaldan), Poblacion, Pogo(Mangaldan).\n\nArea(has): 308.98\nPopulation: 2796", 16.1055, 120.4182);
//        barangayModels.add(lekep_butao);
//    BarangayModel binday = new BarangayModel(R.drawable.hall, "Binday", "A barangay in the municipality of San Fabian that has a population of 2,254 based on the 2015 Census, that is 2.71% of the San fabian total population.\n It shares a common border with barangay(s): Ambalangan-Dalin, Anonang, Aramal, Balacag(Pozorrubio), Casanfernandoan(Pozorrubio), Colisao, Palapad, and Santa Cruz(San Jacinto).\n\nArea(has): 330.46\nPopulation: 2254", 16.1333, 120.4585);
//        barangayModels.add(binday);
//    BarangayModel nibaliw_west = new BarangayModel(R.drawable.hall, "Nibaliw West Compound(Magliba)", "A barangay in the municipality of San Fabian that has a population of 1,993 based on the 2015 Census, that is 2.40% of the San fabian total population.\n It shares a common border with barangay(s): Nibaliw Narvarte, Nibaliw Vidal, and Sagud-Bahley.\n\nArea(has): 38.48\nPopulation: 1993", 16.1235, 120.3928);
//        barangayModels.add(nibaliw_west);
//    BarangayModel rabon = new BarangayModel(R.drawable.hall, "Rabon", "A barangay in the municipality of San Fabian that has a population of 2,351 based on the 2015 Census, that is 2.83% of the San fabian total population.\n It shares a common border with barangay(s): Bigbiga, Rabon, Gumot-Nagcolaran(Rosario), and Tiblong.\n\nArea(has): 201.09\nPopulation: 2351", 16.2016, 120.4218);
//        barangayModels.add(rabon);
//    BarangayModel lipit_tomeeng = new BarangayModel(R.drawable.hall, "Lipit-Tomeeng", "A barangay in the municipality of San Fabian that has a population of 2,104 based on the 2015 Census, that is 2.53% of the San fabian total population.\n It shares a common border with barangay(s): Alacan, Ambalangan-Dalin, Bolasi, Inmalog-Norte, Inmalog Sur, Mabilao, and Palapad.\n\nArea(has): 396.94\nPopulation: 2104", 16.1583, 120.4460);
//        barangayModels.add(lipit_tomeeng);
//    BarangayModel ambalangan_dalin = new BarangayModel(R.drawable.hall, "Ambalangan Dalin", "A barangay in the municipality of San Fabian that has a population of 2,190 based on the 2015 Census, that is 2.64% of the San fabian total population.\n It shares a common border with barangay(s): Binday, Bolaoen, Bulaoen West(Sison), Colisao, Inmalog Sur, Lipit-Tomeeng, Palapad, Pindangan(Sison), and Tara-tara(Sison).\n\nArea(has): 841.13\nPopulation: 2190", 16.1579, 120.4636);
//        barangayModels.add(tempra_guilig);
//    BarangayModel palapad = new BarangayModel(R.drawable.hall, "Palapad", "A barangay in the municipality of San Fabian that has a population of 1,818 based on the 2015 Census, that is 2.19% of the San fabian total population.\n It shares a common border with barangay(s): Ambalangan-Dalin, Aramal, Binday, Bolasi, Colisao, Lipit-Tomeeng, and Tocok.\n\nArea(has): 447.18\nPopulation: 1818", 16.1409, 120.4486);
//        barangayModels.add(palapad);
//    BarangayModel inmalog_sur = new BarangayModel(R.drawable.hall, "Inmalog Sur", "A barangay in the municipality of San Fabian that has a population of 1,552 based on the 2015 Census, that is 1.87% of the San fabian total population.\n It shares a common border with barangay(s): Alacan, Ambalangan-Dalin, Bolaoen, Inmalog Norte, Lipit-Tomeeng, and Mabilao.\n\nArea(has): 361.87\nPopulation: 1552", 16.1695, 120.4534);
//        barangayModels.add(inmalog_sur);
//    BarangayModel sobol = new BarangayModel(R.drawable.hall, "Sobol", "A barangay in the municipality of San Fabian that has a population of 2,479 based on the 2015 Census, that is 2.99% of the San fabian total population..\n It shares a common border with barangay(s): Nibaliw East, Tempra-Guilig, and Tocok.\n\nArea(has): 177.86\nPopulation: 2479", 16.1432, 120.4129);
//        barangayModels.add(sobol);
//    BarangayModel nibaliw_narvarte = new BarangayModel(R.drawable.hall, "Nibaliw Narvarte", "A barangay in the municipality of San Fabian that has a population of 2,422 based on the 2015 Census, that is 2.92% of the San fabian total population.\n It shares a common border with barangay(s): Bonuan Binloc(Dagupan), Cayanga, Longos Amangonan Parac-parac, Nibaliw Magliba, and Sagud-Bahley.\n\nArea(has): 170.96\nPopulation: 2422", 16.1180, 120.3884);
//        barangayModels.add(nibaliw_narvarte);
//    BarangayModel inmalog_norte = new BarangayModel(R.drawable.hall, "Inmalog Norte", "A barangay in the municipality of San Fabian that has a population of 1,469 based on the 2015 Census, that is 1.77% of the San fabian total population.\n It shares a common border with barangay(s): Alacan, Bigbiga, Bolaoen, Gumot, Inmalog Sur, Lipit-Tomeeng, and Mabilao.\n\nArea(has): 372.36\nPopulation: 1469", 16.1806, 120.4466);
//        barangayModels.add(inmalog_norte);
//    BarangayModel cabaruan = new BarangayModel(R.drawable.hall, "Cabaruan", "A barangay in the municipality of San Fabian that has a population of 1,461 based on the 2015 Census, that is 1.76% of the San fabian total population.\n It shares a common border with barangay(s): Angio, Cayanga, Lekep-Butao, Poblacion, Tempra-Guilig, and Tocok.\n\nArea(has): 268.45\nPopulation: 1461", 16.1215, 120.415);
//        barangayModels.add(cabaruan);
//    BarangayModel tiblong = new BarangayModel(R.drawable.hall, "Tiblong", "A barangay in the municipality of San Fabian that has a population of 1,136 based on the 2015 Census, that is 1.37% of the San fabian total population.\n It shares a common border with barangay(s): Alacan, Bigbiga, and Rabon.\n\nArea(has): 175.64\nPopulation: 1136", 16.1890, 120.4248);
//        barangayModels.add(tiblong);
//    BarangayModel tocok = new BarangayModel(R.drawable.hall, "Tocok", "A barangay in the municipality of San Fabian that has a population of 3,446 based on the 2015 Census, that is 4.15% of the San fabian total population.\n It shares a common border with barangay(s): Angio, Aramal, Bolasi, Cabaruan, Palapad, Sobol, and Tempra-Guilig.\n\nArea(has): 328.84\nPopulation: 3446", 16.1369, 120.4247);
//        barangayModels.add(tocok);
//    BarangayModel bolaoen = new BarangayModel(R.drawable.hall, "Bolaoen", "A barangay in the municipality of San Fabian that has a population of 1,159 based on the 2015 Census, that is 1.40% of the San fabian total population.\n It shares a common border with barangay(s): Ambalangan-Dalin, Bantay Insik(Sison), Bulaoen West(Sison), Gumot, Inmalog Norte, Inmalog Sur, and Pinmilapil(Sison).\n\nArea(has): 329.16\nPopulation: 1154", 16.1841, 120.4568);
//        barangayModels.add(bolaoen);
//    BarangayModel bigbiga = new BarangayModel(R.drawable.hall, "Bigbiga", "A barangay in the municipality of San Fabian that has a population of 1,154 based on the 2015 Census, that is 1.39% of the San fabian total population.\n It shares a common border with barangay(s): Alacan, Cataguingtingan(Rosario), Gumot, Gumot-Nagcolaran(Rosario), Inmalog Norte, Rabon(Rosario), Rabon, and Tiblong.\n\nArea(has): 329.16\nPopulation: 1154", 16.2004, 120.4330);
//        barangayModels.add(bigbiga);
//    BarangayModel colisao = new BarangayModel(R.drawable.hall, "Colisao", "A barangay in the municipality of San Fabian that has a population of 842 based on the 2015 Census, that is 1.01% of the San fabian total population.\n It shares a common border with barangay(s): Ambalangan-Dalin, Balacag(Pozorrubio), Binday, Palapad, and Tara-tara(Sison).\n\nArea(has): 249.58\nPopulation: 842", 16.1471, 120.4666);
//        barangayModels.add(colisao);
//    BarangayModel aramal = new BarangayModel(R.drawable.hall, "Aramal", "A barangay in the municipality of San Fabian that has a population of 3,274 based on the 2015 Census, that is 3.94% of the San fabian total population.\n It shares a common border with barangay(s): Angio, Anonang, Binday, Bolasi, Palapad, and Tocok.\n\nArea(has): 308.13\nPopulation: 3274", 16.1232, 120.4423);
//        barangayModels.add(aramal);
//    BarangayModel gumot = new BarangayModel(R.drawable.hall, "Gumot", "A barangay in the municipality of San Fabian that has a population of 770 based on the 2015 Census, that is 0.93% of the San fabian total population.\n It shares a common border with barangay(s): Bantay Insik(Sison), Bigbiga, Bolaoen, Cataguingtingan(Rosario), Gumot-Nagcolaran(Rosario), Inmalog Norte, Pinmilapil(Sison).\n\nArea(has): 283.21\nPopulation: 770", 16.1996, 120.4445);
//        barangayModels.add(gumot);
//    BarangayModel longos_central = new BarangayModel(R.drawable.hall, "Longos Central", "A barangay in the municipality of San Fabian that has a population of 2,818 based on the 2015 Census, that is 3.39% of the San fabian total population.\n It shares a common border with barangay(s): Pogo(Mangaldan), Longos Proper, Cayanga, and Lekep-Butao.\n\nArea(has): 134.99\nPopulation: 2818", 16.1037, 120.4000);
//        barangayModels.add(longos_central);
//    BarangayModel longos_amangonan = new BarangayModel(R.drawable.hall, "Longos Amangonan Parac-parac", "A barangay in the municipality of San Fabian that has a population of 2,602 based on the 2015 Census, that is 3.13% of the San fabian total population.\n It shares a common border with barangay(s): Bonuan Binloc(Dagupan), Cayanga, Longos Proper, Nibaliw Narvarte, and Pogo(Mangaldan).\n\nArea(has): 108.37\nPopulation: 2602", 16.1066, 120.3887);
//        barangayModels.add(longos_amangonan);
//    BarangayModel longos_proper = new BarangayModel(R.drawable.hall, "Longos Proper", "A barangay in the municipality of San Fabian that has a population of 2,233 based on the 2015 Census, that is 2.69% of the San fabian total population..\n It shares a common border with barangay(s): Cayanga, Longos Central, Longos Amangonan Parac-parac, and Pogo(Mangaldan).\n\nArea(has): 132.03\nPopulation: 2233", 16.1053, 120.3939);
//        barangayModels.add(longos_proper);
//    BarangayModel mabilao = new BarangayModel(R.drawable.hall, "Mabilao", "A barangay in the municipality of San Fabian that has a population of 2,713 based on the 2015 Census, that is 3.27% of the San fabian total population.\n It shares a common border with barangay(s): Alacan, Bolasi, Inmalog Norte, Inmalog Sur, and Lipt-Tomeeng.\n\nArea(has): 182.42\nPopulation: 2713", 16.1660, 120.4326);
//        barangayModels.add(mabilao);
//
//
//        vpHorizontal.setClipToPadding(false);
//        vpHorizontal.setClipChildren(false);
//        vpHorizontal.setOffscreenPageLimit(3);
//        vpHorizontal.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
//        vpHorizontal.setAdapter(new ViewPagerAdapter2(barangayModels));
//
//    CompositePageTransformer transformer = new CompositePageTransformer();
//        transformer.addTransformer(new MarginPageTransformer(8));
//        transformer.addTransformer(new ViewPager2.PageTransformer() {
//        @Override
//        public void transformPage(@NonNull View page, float position) {
//            float v = 1 - Math.abs(position);
//            page.setScaleY(0.8f + v * 0.2f);
//        }
//    });
//
//        vpHorizontal.setPageTransformer(transformer);
}
