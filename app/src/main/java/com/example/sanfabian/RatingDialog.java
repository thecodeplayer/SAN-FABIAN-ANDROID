package com.example.sanfabian;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class RatingDialog extends AppCompatDialogFragment {

    private String collectionName, collectionID;
    private RatingBar ratingBar;
    private FirebaseFirestore firebaseFirestore;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.rating_dialog, null);
        ratingBar = view.findViewById(R.id._ratingBar);

        firebaseFirestore = FirebaseFirestore.getInstance();

        Bundle bundle = this.getArguments();
        collectionName = bundle.getString("COLLECTION");
        collectionID = bundle.getString("ID");

        builder.setView(view).setTitle("Rate")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Double rate = Double.valueOf(ratingBar.getRating());
                        if (rate == 0.0){
                            
                        }
                        else {
                            DocumentReference toRate = firebaseFirestore.collection(collectionName).document(collectionID);
                            toRate.update("rating", FieldValue.increment(rate));
                            toRate.update("nrate", FieldValue.increment(1));
                        }
                    }
                });

        return builder.create();
    }
}
