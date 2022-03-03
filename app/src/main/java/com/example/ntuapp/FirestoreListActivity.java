package com.example.ntuapp;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextWatcher;
import android.text.Editable;

import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.example.ntuapp.R;

import java.util.ArrayList;

public class FirestoreListActivity extends AppCompatActivity {

    private FirebaseFirestore mDb = FirebaseFirestore.getInstance();
    //private static final String TAG = "FirestoreListActivity";
    private static final String MODULES = "modules";

    private ArrayAdapter<moduleclass> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firestore_list);

        EditText searchBar =findViewById(R.id.searchbar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("firebase","search box has changed to "+ editable.toString());
            }
        });


        ListView moduleListView = findViewById(R.id.moduleListView);
        adapter = new ArrayAdapter<moduleclass>(
                FirestoreListActivity.this,//activity is this
                android.R.layout.simple_list_item_1,//how it list(text view, display and module.toString
                //if toString() method not defined in class, it will show class fullname@hex addr
                new ArrayList<moduleclass>()
        );
        //adapter = new PatientAdapter(this, new ArrayList<>());
        moduleListView.setAdapter(adapter);

    }
    /*

        class PatientAdapter extends ArrayAdapter<Patient> {

            ArrayList<Patient> patients;
            PatientAdapter(Context context, ArrayList<Patient> patients) {
                super(context, 0, patients);
                this.patients = patients;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.patient_list_item, parent, false);
                }
                //layoutinflater reads xml file and converts it into java widgets
                TextView patientName = convertView.findViewById(R.id.itemName);
                TextView patientAge = convertView.findViewById(R.id.itemAge);

                Patient p = patients.get(position);
                patientName.setText(p.getName());
                patientAge.setText(Integer.toString(p.getAge()));

                return convertView;
            }
        }
*/
    public void onRefreshClick(View view) {
        mDb.collection(MODULES)
                .get()//.get(); get all item
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<moduleclass> moduleslist = new ArrayList<>();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            moduleclass m =document.toObject(moduleclass.class);
                            moduleslist.add(m);
                            Log.d("firebase", m.getCoursecode() + " "+m.getName()+" "+m.getAU());
                        }
                        adapter.clear();
                        adapter.addAll(moduleslist);
                    }

                });


    }

    /*
    public void onSubmitClick(View view) {
        EditText nameEditText = findViewById(R.id.nameEditText);//get the name out from the "nameEditText"box
        EditText coursecodeEditText = findViewById(R.id.coursecodeEditText);//get the name out from the "coursecodeEditText"box
        EditText AUEditText =findViewById(R.id.AUEditText);

        String name = nameEditText.getText().toString();
        String coursecode = coursecodeEditText.getText().toString();
        int AU = Integer.parseInt(AUEditText.getText().toString());
        //int age = Integer.parseInt(ageString);

        moduleclass m = new moduleclass(AU, coursecode, name);
        //Log.d(TAG, "Submitted name: " + m.getName() + ", Cousecode: " + m.getCoursecode());
        mDb.collection(MODULES).add(m);
                //.add(p) .add generates random ID

    }
    */




}