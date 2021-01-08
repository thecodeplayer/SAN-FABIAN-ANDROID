package Dictionary;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import Adapters.WordsSecondAdapter;
import Models.DictionarySecondModel;
import Utilities.DatabaseHelper;

public class FragmentCommonWords extends Fragment {
    private RecyclerView recyclerView;
    private WordsAdapter wordsAdapter;
    private List<DictionarySecondModel> dictionarySecondModelList;
    private DatabaseHelper mDBHelper;
    private View common;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mDBHelper = new DatabaseHelper(context);

        File database = context.getDatabasePath(DatabaseHelper.DBNAME);

        if (!database.exists()) {
            mDBHelper.getReadableDatabase();
            if(copyDatabase(context)) {

            }else {

                return;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        common = inflater.inflate(R.layout.dictionary_second_layout, container, false);
        recyclerView = common.findViewById(R.id.recyclerviewWord);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        dictionarySecondModelList = mDBHelper.commonWords();
        WordsSecondAdapter wordsSecondAdapter = new WordsSecondAdapter();
        wordsSecondAdapter.setData(dictionarySecondModelList);
        recyclerView.setAdapter(wordsSecondAdapter);

        return common;
    }

    private  boolean copyDatabase(Context context) {
        try {
            InputStream inputStream = context.getAssets().open(DatabaseHelper.DBNAME);
            String outFileName = DatabaseHelper.DBLOCATION + DatabaseHelper.DBNAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length = 0;

            while ( (length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }

            outputStream.flush();
            outputStream.close();

//            Log.w("Database Message", "Copy Success");
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
