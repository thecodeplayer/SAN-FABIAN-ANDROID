package com.example.sanfabian;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Adapters.DictionaryRecyclerViewAdapter;
import Dictionary.FragmentAdjectives;
import Dictionary.FragmentAdverbs;
import Dictionary.FragmentCommonWords;
import Dictionary.FragmentConjunctions;
import Dictionary.FragmentNouns;
import Dictionary.FragmentPoliteGreetings;
import Dictionary.FragmentPrepositions;
import Dictionary.FragmentPronouns;
import Dictionary.FragmentPublicNotices;
import Dictionary.FragmentVerbs;
import Interface.RecyclerViewInterface;
import Models.DictionaryItem;

public class FragmentDictionary extends Fragment implements RecyclerViewInterface {

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private View dictionary;
    ArrayList<DictionaryItem> dataholder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dictionary = inflater.inflate(R.layout.fragment_dictionary, container, false);
        recyclerView = dictionary.findViewById(R.id.recyclerViewDictionary);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        dataholder = new ArrayList<>();
        DictionaryItem common_words = new DictionaryItem("Common Words");
        dataholder.add(common_words);
        DictionaryItem nouns = new DictionaryItem("Nouns");
        dataholder.add(nouns);
        DictionaryItem adjectives = new DictionaryItem("Adjectives");
        dataholder.add(adjectives);
        DictionaryItem pronouns = new DictionaryItem("Pronouns");
        dataholder.add(pronouns);
        DictionaryItem conjunctions = new DictionaryItem("Conjunctions");
        dataholder.add(conjunctions);
        DictionaryItem verbs = new DictionaryItem("Verbs");
        dataholder.add(verbs);
        DictionaryItem adverbs = new DictionaryItem("Adverbs");
        dataholder.add(adverbs);
        DictionaryItem prepositions = new DictionaryItem("Prepositions");
        dataholder.add(prepositions);
        DictionaryItem greetings = new DictionaryItem("Polite Greetings");
        dataholder.add(greetings);
        DictionaryItem notices = new DictionaryItem("Public Notices");
        dataholder.add(notices);

        recyclerView.setAdapter(new DictionaryRecyclerViewAdapter(dataholder, this));
        return dictionary;
    }

    @Override
    public void onItemClick(int position) {


        if (position == 0) {
            getFragmentManager().beginTransaction().replace(R.id.container, new FragmentCommonWords()).addToBackStack(null).commit();
        }
        if (position == 1) {
            getFragmentManager().beginTransaction().replace(R.id.container, new FragmentNouns()).addToBackStack(null).commit();
        }
        if (position == 2) {
            getFragmentManager().beginTransaction().replace(R.id.container, new FragmentAdjectives()).addToBackStack(null).commit();
        }
        if (position == 3) {
            getFragmentManager().beginTransaction().replace(R.id.container, new FragmentPronouns()).addToBackStack(null).commit();
        }
        if (position == 4) {
            getFragmentManager().beginTransaction().replace(R.id.container, new FragmentConjunctions()).addToBackStack(null).commit();
        }
        if (position == 5) {
            getFragmentManager().beginTransaction().replace(R.id.container, new FragmentVerbs()).addToBackStack(null).commit();
        }
        if (position == 6) {
            getFragmentManager().beginTransaction().replace(R.id.container, new FragmentAdverbs()).addToBackStack(null).commit();
        }
        if (position == 7) {
            getFragmentManager().beginTransaction().replace(R.id.container, new FragmentPrepositions()).addToBackStack(null).commit();
        }
        if (position == 8) {
            getFragmentManager().beginTransaction().replace(R.id.container, new FragmentPoliteGreetings()).addToBackStack(null).commit();
        }
        if (position == 9) {
            getFragmentManager().beginTransaction().replace(R.id.container, new FragmentPublicNotices()).addToBackStack(null).commit();
        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        layoutManager = new GridLayoutManager(context,1);
    }
}
