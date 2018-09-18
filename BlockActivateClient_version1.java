package com.bodhith.smartvillages;

import android.content.pm.ApplicationInfo;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
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
    private  List<String> ClientChildList = new ArrayList<>();

    private AutoCompleteTextView Username;

    private String username;

    private DatabaseReference DataBase = FirebaseDatabase.getInstance().getReference();

    private DatabaseReference DataBaseTemp;

    private DataSnapshot ClientSnapShot;

    private Switch ActivateOrBlock;

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Username = findViewById(R.id.UsernameEditText);

        DataBaseTemp = DataBase.child("Clients");

        DataBaseTemp.addValueEventListener(new ClientGenerateList() );

        ActivateOrBlock = findViewById(R.id.ActivateOrBlock);

        ActivateOrBlock.setChecked(true);
    }

    public void FetchData(View v)
    {
        username = Username.getText().toString();

        long i = 0;

        String x,Status = null;

        DataBaseTemp = DataBase;

        for( DataSnapshot SnapShot : ClientSnapShot.getChildren() )
        {
            if( i == 0 )
            {
                i++;

                continue;
            }

            x = SnapShot.child("username").getValue().toString();

            Log.d("Values",""+x);

            if( username.equals(x) )
            {
                Status = SnapShot.child("status").getValue().toString();

                break;
            }

            i++;
        }

        if( Status.equals("Active") && ActivateOrBlock.isChecked() )
        {
            DataBaseTemp = DataBase.child("Clients").child("client"+Long.toString(i)).child("status");

            DataBaseTemp.setValue("Blocked");
        }

        else if( Status.equals("Active") && !(ActivateOrBlock.isChecked()) )
        {
            Log.d("Status","User Already Active");

            Toast.makeText(this, "User Already Active", Toast.LENGTH_SHORT).show();
        }

        else if( Status.equals("Blocked") && ActivateOrBlock.isChecked() )
        {
            Log.d("Status","User Already Blocked");

            Toast.makeText(this, "User Already Blocked", Toast.LENGTH_SHORT).show();
        }

        else if( Status.equals("Blocked") && !(ActivateOrBlock.isChecked()) )
        {
            DataBaseTemp = DataBase.child("Clients").child("client"+Long.toString(i)).child("status");

            DataBaseTemp.setValue("Active");
        }

    }

    class ClientGenerateList implements ValueEventListener
    {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot)
        {
            ClientSnapShot = dataSnapshot;

            String x;

            boolean flag = true;

            ClientChildList.clear();

            for( DataSnapshot SnapShot : dataSnapshot.getChildren() )
            {
                if( flag )
                {
                    flag = false;

                    continue;
                }

                x = SnapShot.child("username").getValue().toString();

                ClientChildList.add(x);
            }

            /*for(int i=0;i<ClientChildList.size();i++)
                Log.d("Values",ClientChildList.get(i));*/

            adapter=new ArrayAdapter<String>(MainActivity.this,R.layout.support_simple_spinner_dropdown_item,ClientChildList);

            Username.setThreshold(2);

            Username.setAdapter(adapter);
        }

        @Override
        public void onCancelled(DatabaseError databaseError)
        {

        }
    }
}
