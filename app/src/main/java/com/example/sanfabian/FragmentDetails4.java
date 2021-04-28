package com.example.sanfabian;


import android.content.Context;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.codesgood.views.JustifiedTextView;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.squareup.picasso.Picasso;

public class FragmentDetails4 extends Fragment  {

    private Context mContext;
    private View details;
    private MapboxDirections client;
    TextView title;
    JustifiedTextView description;
    ImageView details_photo;

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

        Bundle bundle = this.getArguments();
        String _title = bundle.getString("TITLE");
        String _description = bundle.getString("DESCRIPTION");
        String _imgUrl = bundle.getString("IMAGEURL");

        title.setText(_title);
        description.setText(_description);
        Picasso.get().load(_imgUrl).into(details_photo);


        return details;
    }
}
