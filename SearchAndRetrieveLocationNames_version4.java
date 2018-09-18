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
    private List<String> StatesChildList = new ArrayList<>();

    private List<String> DistrictsChildList = new ArrayList<>();

    private List<String> MandalsChildList = new ArrayList<>();

    private List<String> PanchayatsChildList = new ArrayList<>();

    private List<String> VillagesChildList = new ArrayList<>();

    private ArrayAdapter<String> ArrayAdapter;

    private AutoCompleteTextView States , Districts , Mandals , Panchayats , Villages ;

    private long CurrentStates , CurrentsDistricts , CurrentMandals , CurrentPanchayats , CurrentVillages ;

    private DataSnapshot StatesSnapShot , DistrictsSnapShot , MandalsSnapShot , PanchayatsSnapShot , VillagesSnapShot ;

    private DatabaseReference DataBase = FirebaseDatabase.getInstance().getReference();

    private DatabaseReference DataBaseTemp;

    private long LongTemp;

    private boolean flag , there ;

    private String StringTemp , StringStates , StringDistricts , StringMandals , StringPanchayats , StringVillages ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        DataBaseTemp = DataBase.child("States");

        DataBaseTemp.addValueEventListener(new GenerateStatesList() );

        DataBaseTemp = DataBase.child("Districts");

        DataBaseTemp.addValueEventListener(new GenerateDistrictsList() );

        DataBaseTemp = DataBase.child("Mandals");

        DataBaseTemp.addValueEventListener(new GenerateMandalsList() );

        DataBaseTemp = DataBase.child("Panchayats");

        DataBaseTemp.addValueEventListener(new GeneratePanchayatsList() );

        DataBaseTemp = DataBase.child("Villages");

        DataBaseTemp.addValueEventListener(new GenerateVillagesList() );

        States = findViewById(R.id.StatesAutoEditText);

        Districts = findViewById(R.id.DisctrictsAutoEditText);

        Mandals = findViewById(R.id.MandalsAutoEditText);

        Panchayats = findViewById(R.id.PanchayatsAutoEditText);

        Villages = findViewById(R.id.VillagesAutoEditText);
    }

    public void FetchData(View v)
    {
        StringStates = States.getText().toString();

        StringDistricts = Districts.getText().toString();

        StringMandals = Mandals.getText().toString();

        StringPanchayats = Panchayats.getText().toString();

        StringVillages = Villages.getText().toString();

        if ( !(SearchValue(StringStates,StatesSnapShot)) )
        {
            AddChild("States",StringStates);
        }

        if ( !(SearchValue(StringDistricts,DistrictsSnapShot)) )
        {
            AddChild("Districts",StringDistricts);
        }

        if ( !(SearchValue(StringMandals,MandalsSnapShot)) )
        {
            AddChild("Mandals",StringMandals);
        }

        if ( !(SearchValue(StringPanchayats,PanchayatsSnapShot)) )
        {
            AddChild("Panchayats",StringPanchayats);
        }

        if ( !(SearchValue(StringVillages,VillagesSnapShot)) )
        {
            AddChild("Villages", StringPanchayats);
        }
    }

    public boolean SearchValue(String Compare,DataSnapshot DataSnapShot)
    {
        flag = true ;

        there = false ;

        for( DataSnapshot SnapShot : DataSnapShot.getChildren() )
        {
            if( flag )
            {
                flag = true;

                continue;
            }

            StringTemp = SnapShot.getValue().toString();

            if( StringTemp.equals(Compare) )
            {
                there = true;

                break;
            }
        }

        return there;
    }

    private void AddChild(String Parent,String Value)
    {
        StringTemp = Parent.toLowerCase().substring(0,Parent.length()-1);

        switch (Parent)
        {
            case "States":
            {
                LongTemp = CurrentStates;

                break;
            }

            case "Districts":
            {
                LongTemp = CurrentsDistricts;

                break;
            }

            case "Mandals":
            {
                LongTemp = CurrentMandals;

                break;
            }

            case "Panchayats":
            {
                LongTemp = CurrentPanchayats;

                break;
            }

            case "Villages":
            {
                LongTemp = CurrentVillages;

                break;
            }

        }

        DataBaseTemp = DataBase.child(Parent).child(StringTemp+Long.toString(++LongTemp));

        DataBaseTemp.setValue(Value);

        DataBaseTemp = DataBase.child(Parent);

        DataBaseTemp.child("Current"+Parent+"Count").setValue(LongTemp);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private class GenerateStatesList implements ValueEventListener
    {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot)
        {
            StatesSnapShot = dataSnapshot;

            flag = true ;

            StatesChildList.clear();

            for( DataSnapshot SnapShot : dataSnapshot.getChildren() )
            {
                if( flag )
                {
                    CurrentStates = Long.parseLong(SnapShot.getValue().toString());

                    flag = false;

                    continue;
                }

                StringTemp = SnapShot.getValue().toString();

                StatesChildList.add(StringTemp);
            }

            ArrayAdapter = new ArrayAdapter<String>(MainActivity.this,R.layout.support_simple_spinner_dropdown_item,StatesChildList);

            States.setThreshold(1);

            States.setAdapter(ArrayAdapter);
        }

        @Override
        public void onCancelled(DatabaseError databaseError)
        {

        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private class GenerateDistrictsList implements ValueEventListener
    {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot)
        {
            DistrictsSnapShot = dataSnapshot;

            flag = true ;

            DistrictsChildList.clear();

            for( DataSnapshot SnapShot : dataSnapshot.getChildren() )
            {
                if( flag )
                {
                    CurrentsDistricts = Long.parseLong(SnapShot.getValue().toString());

                    flag = false;

                    continue;
                }

                StringTemp = SnapShot.getValue().toString();

                DistrictsChildList.add(StringTemp);
            }

            ArrayAdapter = new ArrayAdapter<String>(MainActivity.this,R.layout.support_simple_spinner_dropdown_item,DistrictsChildList);

            Districts.setThreshold(1);

            Districts.setAdapter(ArrayAdapter);
        }

        @Override
        public void onCancelled(DatabaseError databaseError)
        {

        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private class GenerateMandalsList implements ValueEventListener
    {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot)
        {
            MandalsSnapShot = dataSnapshot;

            flag = true ;

            MandalsChildList.clear();

            for( DataSnapshot SnapShot : dataSnapshot.getChildren() )
            {
                if( flag )
                {
                    CurrentMandals = Long.parseLong(SnapShot.getValue().toString());

                    flag = false;

                    continue;
                }

                StringTemp = SnapShot.getValue().toString();

                MandalsChildList.add(StringTemp);
            }

            ArrayAdapter = new ArrayAdapter<String>(MainActivity.this,R.layout.support_simple_spinner_dropdown_item,MandalsChildList);

            Mandals.setThreshold(1);

            Mandals.setAdapter(ArrayAdapter);
        }

        @Override
        public void onCancelled(DatabaseError databaseError)
        {

        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private class GeneratePanchayatsList implements ValueEventListener
    {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot)
        {
            PanchayatsSnapShot = dataSnapshot;

            flag = true ;

            PanchayatsChildList.clear();

            for( DataSnapshot SnapShot : dataSnapshot.getChildren() )
            {
                if( flag )
                {
                    CurrentPanchayats = Long.parseLong(SnapShot.getValue().toString());

                    flag = false;

                    continue;
                }

                StringTemp = SnapShot.getValue().toString();

                PanchayatsChildList.add(StringTemp);
            }

            ArrayAdapter = new ArrayAdapter<String>(MainActivity.this,R.layout.support_simple_spinner_dropdown_item,PanchayatsChildList);

            Panchayats.setThreshold(1);

            Panchayats.setAdapter(ArrayAdapter);
        }

        @Override
        public void onCancelled(DatabaseError databaseError)
        {

        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private class GenerateVillagesList implements ValueEventListener
    {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot)
        {
            VillagesSnapShot = dataSnapshot;

            flag = true ;

            VillagesChildList.clear();

            for( DataSnapshot SnapShot : dataSnapshot.getChildren() )
            {
                if( flag )
                {
                    CurrentVillages = Long.parseLong(SnapShot.getValue().toString());

                    flag = false;

                    continue;
                }

                StringTemp = SnapShot.getValue().toString();

                VillagesChildList.add(StringTemp);
            }

            ArrayAdapter = new ArrayAdapter<String>(MainActivity.this,R.layout.support_simple_spinner_dropdown_item,VillagesChildList);

            Villages.setThreshold(1);

            Villages.setAdapter(ArrayAdapter);
        }

        @Override
        public void onCancelled(DatabaseError databaseError)
        {

        }
    }
}
