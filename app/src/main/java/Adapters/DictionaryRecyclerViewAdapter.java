package Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import Interface.RecyclerViewInterface;
import Models.DictionaryItem;
import com.example.sanfabian.R;

import java.util.ArrayList;

public class DictionaryRecyclerViewAdapter extends RecyclerView.Adapter<DictionaryRecyclerViewAdapter.ViewHolder> {

    RecyclerViewInterface recyclerViewInterface;
    ArrayList<DictionaryItem> dataholder;

    public DictionaryRecyclerViewAdapter(ArrayList<DictionaryItem> dataholder, RecyclerViewInterface recyclerViewInterface) {
        this.dataholder = dataholder;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_dictionary_item,parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.title.setText(dataholder.get(position).getDictionary_item());
    }

    @Override
    public int getItemCount() {

        return dataholder.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.dictionaryItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewInterface.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
