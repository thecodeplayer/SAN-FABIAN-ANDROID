package WhatToDo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sanfabian.MainActivity;
import com.example.sanfabian.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class FragmentConverter extends Fragment {

    private View converter;
    private Context context;
    private EditText baseCurrency;
    private TextView resultCurrency, convertFrom, convertTo, fromCurrencySym, toCurrencySym;
    private Button convert;
    private DatabaseReference Rootref;
    private ProgressBar load, submitLoad;
    private Dialog fromDialog;
    private ListView listView;
    private String input;
    ArrayAdapter<String> arrayAdapter1, arrayAdapter2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        converter = inflater.inflate(R.layout.fragment_converter, container, false);
        convertFrom = converter.findViewById(R.id.convert_from_dropdown_menu);
        convertTo = converter.findViewById(R.id.convert_to_dropdown_menu);
        baseCurrency = converter.findViewById(R.id.currency1);
        resultCurrency = converter.findViewById(R.id.showResult);
        convert = converter.findViewById(R.id.convertButton);
        load = converter.findViewById(R.id.load);
        submitLoad = converter.findViewById(R.id.submit_load);
        fromCurrencySym = converter.findViewById(R.id.fromCurrencySym);
        toCurrencySym = converter.findViewById(R.id.toCurrencySym);

        Rootref = FirebaseDatabase.getInstance().getReference();

        convertFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromDialog = new Dialog(context);
                fromDialog.setContentView(R.layout.from_spinner);
                fromDialog.getWindow().setLayout(1050,1800);
                fromDialog.show();

                listView = fromDialog.findViewById(R.id.list_view);
                EditText editText = fromDialog.findViewById(R.id.edit_text);

                load.setVisibility(View.VISIBLE);
                Rootref.child("Country").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        final List<String> list = new ArrayList<String>();
                        String symbol;
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String name2 = dataSnapshot.child("code").getValue(String.class);
                            String name1 = dataSnapshot.child("name").getValue(String.class);
                            symbol = dataSnapshot.child("symbol_native").getValue(String.class);
                            String finalString = name1 + " | "+symbol+" | " + name2;

                            list.add(finalString);
                        }

                        arrayAdapter1 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
                        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        listView.setAdapter(arrayAdapter1);
                        load.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        arrayAdapter1.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        convertFrom.setText(arrayAdapter1.getItem(i));
                        String countryCode = arrayAdapter1.getItem(i);
                        fromCurrencySym.setText(countryCode.substring(countryCode.length()-3));
                        fromDialog.dismiss();
                    }
                });

            }
        });

        convertTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromDialog = new Dialog(context);
                fromDialog.setContentView(R.layout.from_spinner);
                fromDialog.getWindow().setLayout(1050,1800);
                fromDialog.show();

                listView = fromDialog.findViewById(R.id.list_view);
                EditText editText = fromDialog.findViewById(R.id.edit_text);

                load.setVisibility(View.VISIBLE);
                Rootref.child("Country").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        final List<String> list = new ArrayList<String>();
                        String symbol;
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String name2 = dataSnapshot.child("code").getValue(String.class);
                            String name1 = dataSnapshot.child("name").getValue(String.class);
                            symbol = dataSnapshot.child("symbol_native").getValue(String.class);
                            String finalString = name1 + " | "+symbol+" | " + name2;

                            list.add(finalString);
                        }

                        arrayAdapter2 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
                        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        listView.setAdapter(arrayAdapter2);
                        load.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        arrayAdapter2.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        convertTo.setText(arrayAdapter2.getItem(i));
                        String countryCode = arrayAdapter2.getItem(i);
                        toCurrencySym.setText(countryCode.substring(countryCode.length()-3));
                        fromDialog.dismiss();

                    }
                });
            }
        });

        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitLoad.setVisibility(View.VISIBLE);
                input = baseCurrency.getText().toString();
                String s1 = convertFrom.getText().toString();
                String s2 = convertTo.getText().toString();



                if(convertFrom.getText().toString().isEmpty() || convertTo.getText().toString().isEmpty()){
                    Toast.makeText(context, "Please Select a Country!", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(input)) {
                    Toast.makeText(context, "Please enter the amount!", Toast.LENGTH_SHORT).show();
                }
                else {
                    String real1 = s1.substring(s1.length() - 3);
                    String real2 = s2.substring(s2.length() - 3);
                    FetchData(input, real1, real2);
                }
            }
        });

        return converter;
    }

    private void FetchData(String input, String real1, String real2) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://free.currconv.com/api/v7/convert?q=" + real1 + "_" + real2 + "&compact=ultra&apiKey=cc2db394e749f4cdc852";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        // resultCurrency.setText("Response is: "+ response.substring(0,500));
                        double i = Double.parseDouble(input);

                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            //Eto mga naging double
                            double string = Float.parseFloat(jsonObject.getString(real1 + "_" + real2));
                            double res = string * i;
                            double roundOff = (double) Math.round(res * 100) / 100;
                            resultCurrency.setText(String.valueOf(roundOff));
                            //resultCurrency.setText(String.valueOf(String.format("%.2f",res)));
                            submitLoad.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            submitLoad.setVisibility(View.GONE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
