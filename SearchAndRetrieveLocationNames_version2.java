package com.bodhith.smartvillages;

import android.content.pm.ApplicationInfo;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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
    private List<String> ChildList = new ArrayList<>();

    private boolean there;

    private long CurrentNumber;

    Spinner spin;

    DataSnapshot SnapShot;

    private String Parent , Child , StringTemp;

    private EditText ParentTextFeild , ChildTextFeild;

    private DatabaseReference DataBase = FirebaseDatabase.getInstance().getReference();

    private DatabaseReference DataBaseTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ParentTextFeild = findViewById(R.id.ParentTextFeild);

        ChildTextFeild = findViewById(R.id.ChildTextFeild);

        DataBaseTemp = DataBase.child("States");         //Change this to States,Panchayats,Districts,Mandals

        DataBaseTemp.addValueEventListener(new GenerateList() );

        spin=findViewById(R.id.FidgetSpinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,R.layout.support_simple_spinner_dropdown_item,ChildList);

        spin.setAdapter(adapter);
    }

    public void FetchData(View v)
    {
        String x;

        DataBaseTemp = DataBase;

        Parent = ParentTextFeild.getText().toString();

        Child = ChildTextFeild.getText().toString();

        there = false;

        for( DataSnapshot dataSnapshot : SnapShot.getChildren() )
        {
            x = dataSnapshot.getValue().toString();

            if( x.equals(Child) )
            {
                there = true;

                break;
            }
        }

        if( !there )
        {
            ChildList.clear();

            DataBaseTemp = DataBase.child(Parent);

            DataBaseTemp.child(Parent.toLowerCase().substring(0,Parent.length()-1)+Long.toString(++CurrentNumber)).setValue(Child);

            DataBaseTemp.child("Current"+Parent+"Count").setValue(CurrentNumber);
        }
    }

    class GenerateList implements ValueEventListener
    {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot)
        {
            String x;

            boolean flag = true;

            ChildList.clear();

            Log.d("Snap","2");

            SnapShot = dataSnapshot;

            for( DataSnapshot SnapShot : dataSnapshot.getChildren() )
            {
                if( flag )
                {
                    CurrentNumber = Long.parseLong(SnapShot.getValue().toString());

                    flag = false;

                    continue;
                }

                x = SnapShot.getValue().toString();

                Log.d("Values",""+x);

                ChildList.add(x);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError)
        {

        }
    }
}
