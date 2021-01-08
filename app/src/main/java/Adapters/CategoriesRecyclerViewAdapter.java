package Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import Interface.RecyclerViewInterface;
import Models.CategoryItem;
import com.example.sanfabian.R;

import java.util.ArrayList;

public class CategoriesRecyclerViewAdapter extends RecyclerView.Adapter<CategoriesRecyclerViewAdapter.ViewHolder> {


    RecyclerViewInterface recyclerViewInterface;
    ArrayList<CategoryItem> dataholder;

    public CategoriesRecyclerViewAdapter(ArrayList<CategoryItem> dataholder, RecyclerViewInterface recyclerViewInterface) {
        this.dataholder = dataholder;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_layout,parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.imageView.setImageResource(dataholder.get(position).getCategory_item());
    }

    @Override
    public int getItemCount() {

        return dataholder.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.categoryImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewInterface.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
