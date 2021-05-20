package Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sanfabian.R;
import java.util.List;
import Models.DictionarySecondModel;

public class WordsSecondAdapter extends RecyclerView.Adapter<WordsSecondAdapter.ViewHolder> {

    List<DictionarySecondModel> dictionarySecondModelList;

    public void setData(List<DictionarySecondModel> data) {
        this.dictionarySecondModelList = data;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dictionary_second_item,parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.english.setText(dictionarySecondModelList.get(position).getEnglish_phrase());
        holder.pangasinan.setText(dictionarySecondModelList.get(position).getPangasinan_phrase());
        holder.tagalog.setText(dictionarySecondModelList.get(position).getFilipino_phrase());

    }

    @Override
    public int getItemCount() {

        return dictionarySecondModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView english, pangasinan, tagalog;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            english = itemView.findViewById(R.id.englishPhrase);
            pangasinan = itemView.findViewById(R.id.pangasinanPhrase);
            tagalog = itemView.findViewById(R.id.filipinoPhrase);

        }
    }
}
