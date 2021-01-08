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

public class FragmentPersonalities extends Fragment {

//    ViewPager2 vpHorizontal;
//    ArrayList<CategoriesPagerModel> categoriesPagerModels;

    private Context context;
    private RecyclerView recyclerView;
    private FirestoreCategoriesAdapter adapter;
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView.LayoutManager layoutManager;
    private View personalities;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        personalities = inflater.inflate(R.layout.fragment_personalities, container, false);
        recyclerView = personalities.findViewById(R.id.recyclerview_personalities);

        firebaseFirestore = FirebaseFirestore.getInstance();

        Query query = firebaseFirestore.collection("Personalities");

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
        return personalities;
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
//        CategoriesPagerModel don_juan_alvear = new CategoriesPagerModel(R.drawable.don_juan_alvear2, "Don Juan Alvear", "Doyen of Filipino Faith Healers. Elected assemblyman of Pangasinan in the National assembly in 1907 and authored the law creating the University of the Philippines. Governor of Pangasinan in 1910. He authored the Bill establishing the University of the Philippines and the Philippine General Hospital. A co-founder of the 'Union Espiritista Cristiana de Filipinas'.");
//        categoriesPagerModels.add(don_juan_alvear);
//        CategoriesPagerModel dean_andres_narvasa = new CategoriesPagerModel(R.drawable.don_andres_narvasa2, "Dean Andres Narvasa", "Chief of Legal Panel, his memorandum reports on the Ninoy Aquino assassination in 1983 became the basis of the official majority reports submitted to the nation. Become appointed as Asst. Justice of the Supreme Court in 1986.");
//        categoriesPagerModels.add(dean_andres_narvasa);
//        CategoriesPagerModel maricar_devera_mendoza = new CategoriesPagerModel(R.drawable.miss_maricar_devera_mendoza2, "Miss Maricar de Vera-Mendoza", "1981 Bb. Pilipinas who presented the Philippines in the Miss Universe tilt in the US. According to opmb worldwide, \"she was the first Pinay representative from Filipino Communities abroad to win a major title at the Bb. Pilipinas when she joined in 1981. She succeeded Chat Silayan who placed 4th overall at the Miss Universe 1980 in Seoul Korea. She competed at the 30th edition of Miss Universe beauty pageant held at the newly opened Minskoff Theater in Broadway, New York. There were 77 contestants that year but Maricar failed to advance in the semis.");
//        categoriesPagerModels.add(maricar_devera_mendoza);
//        CategoriesPagerModel gen_eduardo_garcia = new CategoriesPagerModel(R.drawable.ic_launcher_background, "Gen. Eduardo M. Garcia", "Chief of the Philippines Constabulary , the first military general of San Fabian.");
//        categoriesPagerModels.add(gen_eduardo_garcia);
//        CategoriesPagerModel lolita_clark_rodriguez = new CategoriesPagerModel(R.drawable.lolita_rodriguez2, "Lolita Clark-Rodriguez", "The beautiful lass mestiza from Barangay Mabilao, was an accomplished movie star. She is a daughter of William Charles Clark (American) and Carmen Marquez(Filipino). According to IMDb, She was introduced as second lead in Pilya (1954) where she portrayed the older sister of Gloria Romero, and her first starring role was Jack and Jill (1955), the same movie that established Dolphy as the top comedian of the country.");
//        categoriesPagerModels.add(lolita_clark_rodriguez);
//        CategoriesPagerModel col_arturo_aruiza = new CategoriesPagerModel(R.drawable.col_arturo_aruiza2, "Col. Arturo C. Aruiza", "He is a graduate of the Philippine Military Academy in 1967 and directly picked by President Marcos a year after, from the ranks of the Philippine Constabulary. And become the longest military Senior Aide-de-Camp of late Pres. Ferdinand Marcos, a consistent companion for 21 years. He is the author of the book “Ferdinand E. Marcos – Malacanang to Makiki”");
//        categoriesPagerModels.add(col_arturo_aruiza);
//        CategoriesPagerModel dr_ernesto_ferreol = new CategoriesPagerModel(R.drawable.ic_launcher_background, "Dr. Ernesto V. Ferreol", "A known pathologist who was the President of the Philippine Medical Association and directors of the Philippine Veterans Memorial Hospital.");
//        categoriesPagerModels.add(dr_ernesto_ferreol);
//        CategoriesPagerModel dean_aurelio_juguilon = new CategoriesPagerModel(R.drawable.ic_launcher_background, "Dean Aurelio Juguilon", "He is the first Dean of UP College of Architecture. He graduated from the University of the Philippines and the University of St. Thomas as civil engineer and architect respectively. He earned his Master's degree in architecture in the University of Florida in Gainesville.");
//        categoriesPagerModels.add(dean_aurelio_juguilon);
//        CategoriesPagerModel prof_perfecto_fernandez = new CategoriesPagerModel(R.drawable.ic_launcher_background, "Professor Perfecto V. Fernandez", "He was born on May 31, 1931 and achieved his law degree in 1957. He is a Bar Topnotcher (10th placer in 1958 bar exams), became a law professor and a writer of bar reviewer, law books and lecturer, as well as political commentator in the Philippines and one of the country's legal luminaries.");
//        categoriesPagerModels.add(prof_perfecto_fernandez);
//        CategoriesPagerModel atty_arthur_galace = new CategoriesPagerModel(R.drawable.ic_launcher_background, "Atty. Arthur Erfe Galace", "NWhen he was 34 years old, he took the bar exam in 1976 and became the top 3 highest grade on that year. He is one of the most active members of the Free Legal Assistance Group (FLAG) in Baguio City, was a president of the local chapter and executive director of Integrated Bar of the Philippines (IBP) of its legal aid committee from 1983 to 1985, a member of Lawyers’ Committee for International Human Rights (New York, USA), was appointed deputy commissioner of the Presidential Committee on Human Rights (PCHR), and established the Northern Luzon Human Rights Organization (NL-HRO) in 1984 together with fellow rights advocate.");
//        categoriesPagerModels.add(atty_arthur_galace);
//        CategoriesPagerModel prof_teresa_bernabe = new CategoriesPagerModel(R.drawable.ic_launcher_background, "Prof. Teresa Fernandez-Bernabe", "UP's Budget Director.");
//        categoriesPagerModels.add(prof_teresa_bernabe);
//        CategoriesPagerModel engr_bevero_ferreria = new CategoriesPagerModel(R.drawable.ic_launcher_background, "Engr. Bevero Ferreria", "Board of Engineering topnotcher from Brgy. Tocok.");
//        categoriesPagerModels.add(engr_bevero_ferreria);
//        CategoriesPagerModel engr_albert_padilla = new CategoriesPagerModel(R.drawable.ic_launcher_background, "Engr. Albert Navarro Padilla", "Communication Engineer.");
//        categoriesPagerModels.add(engr_albert_padilla);
//        CategoriesPagerModel nonong_pedero = new CategoriesPagerModel(R.drawable.ic_launcher_background, "Nonong Pedero", "He is one of the country's known composers of songs and commercial jingles. His famous composition is the SM jingle. Metro Pop winners Narito Ako, Umiibig in 1978, Isang Mundo, Isang Awit in 1980, and the theme songs for Bb. Pilipinas and Miss Earth are compositions of Nonong Pedero.");
//        categoriesPagerModels.add(nonong_pedero);
//        CategoriesPagerModel esteban_dacayo = new CategoriesPagerModel(R.drawable.ic_launcher_background, "Esteban Dacayo", "A cyclist from Brgy. Palapad who presented the Philippines in many international road races together with Gintong Alay cyclist.");
//        categoriesPagerModels.add(esteban_dacayo);
//        CategoriesPagerModel roberto_guntang = new CategoriesPagerModel(R.drawable.ic_launcher_background, "Roberto Guntang", "A Farmer from Angio who was awarded the Philippine Most Outstanding Tobacco Farmer in 1981.");
//        categoriesPagerModels.add(roberto_guntang);

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
