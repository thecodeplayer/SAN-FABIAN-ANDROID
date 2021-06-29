package Dictionary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.sanfabian.R;

public class FragmentDefinition extends Fragment {

    private View definition;
    TextView word, classification, filipino_word, pangasinan_word, ilocano_word, eng_example, pang_example, tag_example, iloc_example;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        definition = inflater.inflate(R.layout.fragment_definition, container, false);

        word = definition.findViewById(R.id.textWord);
        classification = definition.findViewById(R.id.wordClassification);
        filipino_word = definition.findViewById(R.id.wordPilipino);
        pangasinan_word = definition.findViewById(R.id.wordPangasinan);
        ilocano_word = definition.findViewById(R.id.wordIlocano);
        eng_example = definition.findViewById(R.id.english_example);
        pang_example = definition.findViewById(R.id.pangasinan_example);
        tag_example = definition.findViewById(R.id.filipino_example);
        iloc_example = definition.findViewById(R.id.ilocano_example);
        word.getPaint().setUnderlineText(true);

        Bundle bundle = this.getArguments();
        String dictionary_word = bundle.getString("WORD");
        String word_classification = bundle.getString("CLASSIFICATION");
        String pilipino = bundle.getString("PILIPINO");
        String pangasinan = bundle.getString("PANGASINAN");
        String ilocano = bundle.getString("ILOCANO");
        String english_example = bundle.getString("ENGLISH_EXAMPLE");
        String pangasinan_example = bundle.getString("PANGASINAN_EXAMPLE");
        String tagalog_example = bundle.getString("TAGALOG_EXAMPLE");
        String ilocano_example = bundle.getString("ILOCANO_EXAMPLE");

        word.setText(dictionary_word);
        classification.setText(word_classification);
        filipino_word.setText(pilipino);
        pangasinan_word.setText(pangasinan);
        ilocano_word.setText(ilocano);
        eng_example.setText(english_example);
        pang_example.setText(pangasinan_example);
        tag_example.setText(tagalog_example);
        iloc_example.setText(ilocano_example);

        return definition;
    }
}
