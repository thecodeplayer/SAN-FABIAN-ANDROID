package Categories;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sanfabian.R;
import java.util.ArrayList;
import Adapters.CategoriesRecyclerViewAdapter;
import Interface.RecyclerViewInterface;
import Models.CategoryItem;

public class FragmentCategories extends Fragment implements RecyclerViewInterface {

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private View categories;
    ArrayList<CategoryItem> dataholder;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        categories = inflater.inflate(R.layout.fragment_categories, container, false);
        recyclerView = categories.findViewById(R.id.recyclerViewCategories);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        dataholder = new ArrayList<>();
        CategoryItem products = new CategoryItem(R.drawable.san_fabian_products);
        dataholder.add(products);
        CategoryItem tourist_spots = new CategoryItem(R.drawable.san_fabian_tourist_spots);
        dataholder.add(tourist_spots);
        CategoryItem personalities = new CategoryItem(R.drawable.san_fabian_personalities);
        dataholder.add(personalities);
        CategoryItem history = new CategoryItem(R.drawable.san_fabian_history);
        dataholder.add(history);
        CategoryItem festivals = new CategoryItem(R.drawable.san_fabian_festivals);
        dataholder.add(festivals);
        CategoryItem barangays = new CategoryItem(R.drawable.san_fabian_barangays);
        dataholder.add(barangays);

        recyclerView.setAdapter(new CategoriesRecyclerViewAdapter(dataholder, this));
        return categories;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        layoutManager = new GridLayoutManager(context,1);
    }

    @Override
    public void onItemClick(int position) {
        if (position == 0) {
            getParentFragmentManager().beginTransaction().replace(R.id.container, new FragmentProducts()).addToBackStack(null).commit();
        }
        else if (position == 1) {
            getParentFragmentManager().beginTransaction().replace(R.id.container, new FragmentTouristSpots()).addToBackStack(null).commit();
        }
        else if (position == 2) {
            getParentFragmentManager().beginTransaction().replace(R.id.container, new FragmentPersonalities()).addToBackStack(null).commit();
        }
        else if (position == 3) {
            getParentFragmentManager().beginTransaction().replace(R.id.container, new FragmentHistory()).addToBackStack(null).commit();
        }
        else if (position == 4) {
            getParentFragmentManager().beginTransaction().replace(R.id.container, new FragmentFestivals()).addToBackStack(null).commit();
        }
        else if (position == 5) {
            getParentFragmentManager().beginTransaction().replace(R.id.container, new FragmentBarangays()).addToBackStack(null).commit();
        }
    }


    
}
