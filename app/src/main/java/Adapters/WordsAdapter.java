package Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanfabian.R;

import java.util.List;

import Interface.RecyclerViewInterface;
import Models.DictionaryModel;

public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.ViewHolder> {

    RecyclerViewInterface recyclerViewInterface;
    List<DictionaryModel> dictionaryModelList;

    public void setData(List<DictionaryModel> data, RecyclerViewInterface recyclerViewInterface) {
        this.dictionaryModelList = data;
        this.recyclerViewInterface = recyclerViewInterface;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dictionary_item,parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.dictionaryWord.setText(dictionaryModelList.get(position).getWord());

    }

    @Override
    public int getItemCount() {

        return dictionaryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView dictionaryWord;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dictionaryWord = itemView.findViewById(R.id.dictionaryItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewInterface.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
