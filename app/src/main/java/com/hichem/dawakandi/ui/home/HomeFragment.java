package com.hichem.dawakandi.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hichem.dawakandi.R;
import com.hichem.dawakandi.ui.Listpharmacies;

import java.util.ArrayList;

import static java.lang.Double.parseDouble;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    Button btnSearch;
    EditText searchEdit;
    Bundle bundle = new Bundle();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("medicaments");
    final DatabaseReference myRef2 = database.getReference("Pharmacies");
    ArrayList<String> list = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        btnSearch = (Button) root.findViewById(R.id.btnsearch);


        searchEdit = (EditText) root.findViewById(R.id.medicamentinput);


        final Intent intent = new Intent(getActivity().getApplicationContext(), Listpharmacies.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);



        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = String.valueOf(searchEdit.getText());
                final ArrayList<Object> listfinal = new ArrayList<>();


                final Query myquery = myRef.orderByChild("nom").equalTo(s);

                myquery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        //     Log.d("medicament    : ", String.valueOf(dataSnapshot));
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            //list.add(ds.child("pharmacies").getValue()) ;
                            //Log.d("medicament    : ", String.valueOf(ds.child("pharmacies").getChildren()));
                            for (DataSnapshot d : ds.child("pharmacies").getChildren()) {
                              //  Log.d("medicament    : ", String.valueOf(d.child("id").getValue()));
                                list.add(String.valueOf(d.child("id").getValue()));
                            }
                            //   Log.d("medicament    : ", String.valueOf(list.get(0)));
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                for (int i = 0; i < list.size(); i++) {
                    Query myquery1 = myRef2.orderByChild("Id_Pharmacie").equalTo(list.get(i));
                    myquery1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {

                            for (DataSnapshot dr : dataSnapshot1.getChildren()) {

                              //  Log.d("pharmacie", dr.child("nom").getValue().toString());
                                try {
                                    Thread.sleep(10000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                bundle.putDouble("lat", Double.parseDouble(dr.child("geopoint").child("lat").getValue().toString()));
                                bundle.putDouble("lng", Double.parseDouble(dr.child("geopoint").child("lng").getValue().toString()));
                                /*   bundle.putString("nom",dr.child("nom").getValue().toString() );*/
                                /*  intent.putExtra("nom"+i, String.valueOf(dr.child("nom").getValue())) ;*/
/*                                        Log.d("geoPoint",dr.child("geopoint").child("lng").getValue().toString());
                                        Log.d("geoPoint",dr.child("geopoint").child("lat").getValue().toString());*/
                            }
                            /*bundle.putSerializable("pharmacies", listfinal);*/
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }

                Log.d("bundle ::::::", bundle.toString());
//
//            while( bundle != null){
//
//            }

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });



        return root;
    }

}
