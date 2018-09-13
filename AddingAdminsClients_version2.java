package com.bodhith.smartvillages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;


////////////////////////////////////////////// MAIN ///////////////////////////////////////////////////////

public class MainActivity extends AppCompatActivity
{

    private long NumberOfAdmins , NumberOfClients ;

    private EditText Username , Password ;

    private Button Submit ;

    private DatabaseReference DataBase = FirebaseDatabase.getInstance().getReference() ;

    private DatabaseReference DataBaseTemp , Admins , Clients ;

    private DataSnapshot SnapShot ;

    private String username , password ;

    private Users user = new Users() ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();

        Username =  findViewById(R.id.Username);

        Password =  findViewById(R.id.Password);

        DataBase.child("Admins").addChildEventListener(new AddAdminChildEventListener());

        DataBase.child("Admins").addValueEventListener(new AddAdminValueEventListener());

        DataBase.child("Clients").addChildEventListener(new AddClientChildEventListener());

        DataBase.child("Clients").addValueEventListener(new AddClientValueEventListener());

        Submit =  findViewById(R.id.Submit);
    }

//////////////////////////////////////////////// END MAIN //////////////////////////////////////////////////

//////////////////////////////////////////////// LISTENERS /////////////////////////////////////////////////

    /*                                           ADMIN                                            */
    class AddAdminChildEventListener implements ChildEventListener
    {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s)
        {
            Log.d("snap","1.1");
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s)
        {
            Log.d("snap","1.2");
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot)
        {
            Log.d("snap","1.3");
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s)
        {
            Log.d("snap","1.4");
        }

        @Override
        public void onCancelled(DatabaseError databaseError)
        {
            Log.d("snap","1.5");
        }
    }

    class AddAdminValueEventListener implements ValueEventListener
    {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot)
        {
            SnapShot = dataSnapshot.child("CurrentAdminsCount");

            NumberOfAdmins = Long.parseLong(SnapShot.getValue().toString());

            Log.d("snap","2");
        }

        @Override
        public void onCancelled(DatabaseError databaseError)
        {
            Log.d("snap","2.2");
        }
    }
    /*                                          END ADMIN                                         */

    /*                                         START CLIENT                                       */

    class AddClientChildEventListener implements ChildEventListener
    {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s)
        {
            Log.d("snap","1.1");
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s)
        {
            Log.d("snap","1.2");
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot)
        {
            Log.d("snap","1.3");
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s)
        {
            Log.d("snap","1.4");
        }

        @Override
        public void onCancelled(DatabaseError databaseError)
        {
            Log.d("snap","1.5");
        }
    }

    class AddClientValueEventListener implements ValueEventListener
    {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot)
        {
            SnapShot = dataSnapshot.child("CurrentClientsCount");

            NumberOfClients = Long.parseLong(SnapShot.getValue().toString());

            Log.d("snap","2");
        }

        @Override
        public void onCancelled(DatabaseError databaseError)
        {
            Log.d("snap","2.2");
        }
    }

    /*                                          END CLIENT                                        */

/////////////////////////////////////////////// END LISTENERS /////////////////////////////////////////////

    public void Login(View v)
    {
        username = Username.getText().toString();

        password = Password.getText().toString();

        final String Check = CheckLogin(username,password);

        if( Check.equals("Admin") )
        {
            AddAdmins(username,password);

            Log.d("snap","3");

        //    Toast.makeText(this, "Admin Interface", Toast.LENGTH_SHORT).show();
        }
        else if( Check.equals("Client") )
        {
            AddClients(username,password);

            Log.d("snap","3");

            //Toast.makeText(this, "Client Interface", Toast.LENGTH_SHORT).show();
        }
        else if( Check.equals("Fasaak") )
        {
            Toast.makeText(this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
        }
    }

    public String CheckLogin(final String username,final String password)
    {
        return "Client";
    }

    public void AddAdmins(String Username,String Password)
    {
        DataBaseTemp = DataBase.child("Admins");

        user.setUsername(Username);

        user.setPassword(Password);

        DataBaseTemp.child("admin"+Long.toString(++NumberOfAdmins)).setValue(user);

        DataBaseTemp.child("CurrentAdminsCount").setValue(NumberOfAdmins);

        Log.d("snap","4");
    }

    public void AddClients(String Username,String Password)
    {
        DataBaseTemp = DataBase.child("Clients");

        user.setUsername(Username);

        user.setPassword(Password);

        DataBaseTemp.child("client"+Long.toString(++NumberOfClients)).setValue(user);

        DataBaseTemp.child("CurrentClientsCount").setValue(NumberOfClients);

        Log.d("snap","4");
    }

    public void SetUsername(String Username)
    {

        user.setUsername(Username);
    }

    public void SetPassword(String Password)
    {
        user.setPassword(Password);
    }

    /* TO DO
    public long AdminCount()
    {
        DataBaseTemp = DataBase;

        SnapShot = DataBaseTemp.child("CurrentAdminCount");

        long AdminCount = SnapShot.child("Admins").getChildrenCount();

        Toast.makeText(this, ""+AdminCount, Toast.LENGTH_SHORT).show();

        return AdminCount;
    }*/

/////////////////////////////////////////  MISC ////////////////////////////////////////////////////



/////////////////////////////////////////// END MISC ///////////////////////////////////////////////
}
