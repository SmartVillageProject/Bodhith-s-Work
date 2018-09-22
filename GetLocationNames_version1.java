package com.bodhith.smartvillages;

import android.content.pm.ApplicationInfo;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.AutoText;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


////////////////////////////////////////////// MAIN ////////////////////////////////////////////////

public class MainActivity extends AppCompatActivity
{

    private ArrayList<String> StatesList = new ArrayList<>() ;

    private ArrayList<String> DisctrictsList = new ArrayList<>() ;

    private ArrayList<String> MandalsList = new ArrayList<>() ;

    private ArrayList<String> PanchayatsList = new ArrayList<>() ;

    private ArrayList<String> VillagesList = new ArrayList<>() ;

    private DatabaseReference DataBase = FirebaseDatabase.getInstance().getReference() ;

    private DatabaseReference DataBaseTemp ;

    private DataSnapshot StatesSnapShot , DistrictsSnapShot ;

    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        DataBaseTemp = DataBase.child("States");

        DataBaseTemp.addValueEventListener(new StatesGenerateList());

        DataBaseTemp = DataBase.child("Disctricts");

        DataBaseTemp.addValueEventListener(new DistrictsGenerateList());

        DataBaseTemp = DataBase.child("Mandals");

        DataBaseTemp.addValueEventListener(new MandalsGenerateList());

        DataBaseTemp = DataBase.child("Panchayats");

        DataBaseTemp.addValueEventListener(new PanchayatsGenerateList());

        DataBaseTemp = DataBase.child("Villages");

        DataBaseTemp.addValueEventListener(new VillagesGenerateList());
    }

    ////////////////////////////////////////STATES//////////////////////////////////////////////////

    private class StatesGenerateList implements ValueEventListener
    {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot)
        {
            Log.d("Snap","1");

            flag = true;

            StatesSnapShot = dataSnapshot;

            for( DataSnapshot SnapShot : dataSnapshot.getChildren() )
            {
                if( flag )
                {
                    flag = false;

                    continue;
                }

                StatesList.add(SnapShot.getValue().toString());
            }

            SetLists();
        }

        @Override
        public void onCancelled(DatabaseError databaseError)
        {

        }
    }

    private void SetLists()
    {
        for(int i=0;i<StatesList.size();i++)
            Log.d("Values",StatesList.get(i));

        for(int i=0;i<DisctrictsList.size();i++)
            Log.d("Values",DisctrictsList.get(i));

        for(int i=0;i<MandalsList.size();i++)
            Log.d("Values",MandalsList.get(i));

        for(int i=0;i<PanchayatsList.size();i++)
            Log.d("Values",PanchayatsList.get(i));

        for(int i=0;i<VillagesList.size();i++)
            Log.d("Values",VillagesList.get(i));
    }

    ////////////////////////////////////////DISTRICTS///////////////////////////////////////////////

    private class DistrictsGenerateList implements ValueEventListener
    {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot)
        {
            Log.d("Snap","2");

            flag = true;

            DistrictsSnapShot = dataSnapshot;

            for( DataSnapshot SnapShot : dataSnapshot.getChildren() )
            {
                if( flag )
                {
                    flag = false;

                    continue;
                }

                DisctrictsList.add(SnapShot.getValue().toString());
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError)
        {

        }
    }

    /////////////////////////////////////////MANDALS////////////////////////////////////////////////

    private class MandalsGenerateList implements ValueEventListener
    {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot)
        {
            Log.d("Snap","3");

            flag = true;

            DistrictsSnapShot = dataSnapshot;

            for( DataSnapshot SnapShot : dataSnapshot.getChildren() )
            {
                if( flag )
                {
                    flag = false;

                    continue;
                }

                MandalsList.add(SnapShot.getValue().toString());
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError)
        {

        }
    }

    ////////////////////////////////////////PANCHAYATS//////////////////////////////////////////////

    private class PanchayatsGenerateList implements ValueEventListener
    {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot)
        {
            Log.d("Snap","4");

            flag = true;

            DistrictsSnapShot = dataSnapshot;

            for( DataSnapshot SnapShot : dataSnapshot.getChildren() )
            {
                if( flag )
                {
                    flag = false;

                    continue;
                }

                PanchayatsList.add(SnapShot.getValue().toString());
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError)
        {

        }
    }

    //////////////////////////////////////////VILLAGES//////////////////////////////////////////////

    private class VillagesGenerateList implements ValueEventListener
    {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot)
        {
            Log.d("Snap","5");

            flag = true;

            DistrictsSnapShot = dataSnapshot;

            for( DataSnapshot SnapShot : dataSnapshot.getChildren() )
            {
                if( flag )
                {
                    flag = false;

                    continue;
                }

                VillagesList.add(SnapShot.getValue().toString());
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError)
        {

        }
    }
}
