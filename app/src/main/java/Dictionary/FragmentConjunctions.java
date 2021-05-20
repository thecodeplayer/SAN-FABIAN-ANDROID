package Dictionary;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sanfabian.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import Adapters.WordsAdapter;
import Interface.RecyclerViewInterface;
import Utilities.DatabaseHelper;
import Models.DictionaryModel;

public class FragmentConjunctions extends Fragment implements RecyclerViewInterface {

    private RecyclerView recyclerView;
    private WordsAdapter wordsAdapter;
    private List<DictionaryModel> dictionaryModelList;
    private DatabaseHelper mDBHelper;
    private View pronoun;
    private SearchView searchView;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mDBHelper = new DatabaseHelper(context);

        File database = context.getDatabasePath(DatabaseHelper.DBNAME);

        if (!database.exists()) {
            mDBHelper.getReadableDatabase();
            if (copyDatabase(context)) {

            } else {

                return;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        pronoun = inflater.inflate(R.layout.dictionary_layout, container, false);
        recyclerView = pronoun.findViewById(R.id.recyclerviewWord);
        searchView = pronoun.findViewById(R.id.searchView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        dictionaryModelList = mDBHelper.conjunctionWords("");
        wordsAdapter = new WordsAdapter();
        wordsAdapter.setData(dictionaryModelList, this);
        recyclerView.setAdapter(wordsAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchWord(newText);
                return false;
            }
        });

        return pronoun;
    }

    private void searchWord(String wordSearch) {
        dictionaryModelList.clear();
        dictionaryModelList = mDBHelper.conjunctionWords(wordSearch);
        wordsAdapter.setData(dictionaryModelList, this);
        recyclerView.setAdapter(wordsAdapter);
    }

    private boolean copyDatabase(Context context) {
        try {
            InputStream inputStream = context.getAssets().open(DatabaseHelper.DBNAME);
            String outFileName = DatabaseHelper.DBLOCATION + DatabaseHelper.DBNAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length = 0;

            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }

            outputStream.flush();
            outputStream.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void onItemClick(int position) {
        DictionaryModel dictionaryModel = dictionaryModelList.get(position);
        Bundle bundle = new Bundle();

        bundle.putString("WORD", dictionaryModel.getWord());
        bundle.putString("CLASSIFICATION", dictionaryModel.getClassification());
        bundle.putString("PILIPINO", dictionaryModel.getPilipino_word());
        bundle.putString("PANGASINAN", dictionaryModel.getPangasinan_word());
        bundle.putString("ENGLISH_EXAMPLE", dictionaryModel.getEnglish_example());
        bundle.putString("PANGASINAN_EXAMPLE", dictionaryModel.getPangasinan_example());
        bundle.putString("TAGALOG_EXAMPLE", dictionaryModel.getFilipino_example());
        FragmentDefinition definition = new FragmentDefinition();
        definition.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(R.id.container, definition).addToBackStack(null).commit();
    }
}
