package com.example.sanfabian;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.codesgood.views.JustifiedTextView;
import com.google.firebase.firestore.Transaction;
import com.squareup.picasso.Picasso;
import java.io.Serializable;
import java.util.ArrayList;

public class FragmentDetails4 extends Fragment implements Serializable {

    private Context mContext;
    private View details;
    TextView title;
    JustifiedTextView description;
    ImageView details_photo;
    LinearLayout dynamic_linearLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        details = inflater.inflate(R.layout.details_layout4, container, false);

        title = details.findViewById(R.id.title);
        description = details.findViewById(R.id.description);
        details_photo = details.findViewById(R.id.details_photo);
        dynamic_linearLayout = details.findViewById(R.id.dynamic_layout);

        Bundle bundle = this.getArguments();
        String _title = bundle.getString("TITLE");
        String _description = bundle.getString("DESCRIPTION");
        String _imgUrl = bundle.getString("IMAGEURL");
        ArrayList<Transaction> _mayors = (ArrayList<Transaction>) bundle.getSerializable("MAYORS");

        title.setText(_title);
        description.setText(_description);
        Picasso.get().load(_imgUrl).into(details_photo);

        try {
            for (int i = 0; i <= _mayors.size(); i++) {
                TextView txtItem = new TextView(mContext);
                txtItem.setText(String.valueOf(_mayors.get(i)));
                txtItem.setTextColor(this.getResources().getColor(R.color.black));
                txtItem.setTextSize(18);
                dynamic_linearLayout.addView(txtItem);
            }
        } catch (Exception e){

        }
        return details;
    }
}
