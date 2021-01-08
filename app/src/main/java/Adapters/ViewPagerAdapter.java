package Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanfabian.R;

import java.util.ArrayList;

import Models.CategoriesPagerModel;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {


    ArrayList<CategoriesPagerModel> dataholder;
    public ViewPagerAdapter(ArrayList<CategoriesPagerModel> dataholder) {
        this.dataholder = dataholder;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_items, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

//        holder.viewpagerImage.setBackgroundResource(dataholder.get(position).getView_pager_image());
//        holder.viewpagerName.setText(dataholder.get(position).getName());
//        holder.viewpagerDescription.setText(dataholder.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView viewpagerImage;
        TextView viewpagerName, viewpagerDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

//            viewpagerImage = itemView.findViewById(R.id.viewpagerImage);
//            viewpagerName = itemView.findViewById(R.id.viewpagerName);
//            viewpagerDescription = itemView.findViewById(R.id.viewpagerDescription);
        }
    }

}
