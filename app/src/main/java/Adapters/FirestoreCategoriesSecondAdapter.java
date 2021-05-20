package Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanfabian.R;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;
import com.squareup.picasso.Picasso;

import Models.CategoriesPagerModel;

public class FirestoreCategoriesSecondAdapter extends FirestorePagingAdapter<CategoriesPagerModel, FirestoreCategoriesSecondAdapter.RelaxationViewHolder> {

    private LayoutInflater layoutInflater;
    private Context context;

    /**
     * Construct a new FirestorePagingAdapter from the given {@link FirestorePagingOptions}.
     *
     * @param options
     */
    public FirestoreCategoriesSecondAdapter(@NonNull FirestorePagingOptions<CategoriesPagerModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RelaxationViewHolder holder, int position, @NonNull CategoriesPagerModel model) {

        holder.title.setText(model.getTitle());
        holder.description.setText(model.getDescription());
    }

    @NonNull
    @Override
    public RelaxationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.viewpager_items3, parent, false);
        return new RelaxationViewHolder(view);
    }

    @Override
    protected void onLoadingStateChanged(@NonNull LoadingState state) {
        super.onLoadingStateChanged(state);

        switch (state) {
            case LOADING_INITIAL:
                Log.d("PAGING_LOG", "Loading Initial Data");
                break;
            case LOADING_MORE:
                Log.d("PAGING_LOG", "Loading Next Page");
                break;
            case FINISHED:
                Log.d("PAGING_LOG", "All Data Loaded");
                break;
            case ERROR:
                Log.d("PAGING_LOG", "ERROR");
                break;
            case LOADED:
                Log.d("PAGING_LOG", "Total Items Loaded" + getItemCount());
                break;
        }
    }

    public class RelaxationViewHolder extends RecyclerView.ViewHolder{

        TextView title, description;

        public RelaxationViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.pagerTitle);
            description = itemView.findViewById(R.id.pagerDescription);

        }
    }

}
