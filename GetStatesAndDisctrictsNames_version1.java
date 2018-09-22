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

        for(int i=0;i<StatesList.size();i++)
            Log.d("Values",StatesList.get(i));

        for(int i=0;i<DisctrictsList.size();i++)
            Log.d("Values",DisctrictsList.get(i));
    }


    private class StatesGenerateList implements ValueEventListener
    {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot)
        {
            flag = true;

            StatesSnapShot = dataSnapshot;

            for( DataSnapshot SnapShot : dataSnapshot.getChildren() )
            {
                if( flag )
                {
                    flag = false;

                    continue;
                }

                StatesList.add(SnapShot.toString());
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError)
        {

        }
    }

    private class DistrictsGenerateList implements ValueEventListener
    {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot)
        {
            flag = true;

            DistrictsSnapShot = dataSnapshot;

            for( DataSnapshot SnapShot : dataSnapshot.getChildren() )
            {
                if( flag )
                {
                    flag = false;

                    continue;
                }

                DisctrictsList.add(SnapShot.toString());
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError)
        {

        }
    }
}
