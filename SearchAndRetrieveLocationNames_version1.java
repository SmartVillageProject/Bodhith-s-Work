package com.bodhith.smartvillages;

import android.content.pm.ApplicationInfo;
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

    private long CurrentNumber;

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
    }

    public void FetchData(View v)
    {
        DataBaseTemp = DataBase;

        Parent = ParentTextFeild.getText().toString();

        Child = ChildTextFeild.getText().toString();

        DataBaseTemp.child(Parent).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                boolean flag = true;

                boolean there = false;

                String x;

                for( DataSnapshot SnapShot : dataSnapshot.getChildren() )
                {
                    x = SnapShot.getValue().toString();

                    if( flag )
                    {
                        CurrentNumber = Long.parseLong(x);

                        flag = false;

                        continue;
                    }

                    if( Child.equals(x) )
                    {
                        there = true;
                    }

                    ChildList.add(x);
                }

                if( !there )
                {
                    StringTemp = Parent.substring(0,Parent.length()-1).toLowerCase();

                    StringTemp += Long.toString(++CurrentNumber);

                    DataBaseTemp.child(Parent).child(StringTemp).setValue(Child);

                    DataBaseTemp.child(Parent).removeEventListener(this);

                    DataBaseTemp.child(Parent).child("Current"+Parent+"Count").setValue(CurrentNumber);
                }

                else
                {
                    DataBaseTemp.child(Parent).removeEventListener(this);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }

}
