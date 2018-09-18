package com.bodhith.smartvillages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
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

    private Switch AdminOrClient;

    private DatabaseReference DataBase = FirebaseDatabase.getInstance().getReference() ;

    private DatabaseReference DataBaseTemp , Admins , Clients ;

    private DataSnapshot SnapShot , AdminSnapShot , ClientSnapShot ;

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

        DataBase.child("Admins").addValueEventListener(new AddAdminValueEventListener());

        DataBase.child("Clients").addValueEventListener(new AddClientValueEventListener());

        Submit =  findViewById(R.id.Submit);

        AdminOrClient = findViewById(R.id.AdminOrClient);

        AdminOrClient.setChecked(true);
    }

//////////////////////////////////////////////// END MAIN //////////////////////////////////////////////////

//////////////////////////////////////////////// LISTENERS /////////////////////////////////////////////////

    /*                                           ADMIN                                            */
    class AddAdminValueEventListener implements ValueEventListener
    {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot)
        {
            AdminSnapShot = dataSnapshot;

            SnapShot = dataSnapshot.child("CurrentAdminsCount");

            NumberOfAdmins = Long.parseLong(SnapShot.getValue().toString());
        }

        @Override
        public void onCancelled(DatabaseError databaseError)
        {
            Log.d("snap","2.2");
        }
    }
    /*                                          END ADMIN                                         */

    /*                                         START CLIENT                                       */
    class AddClientValueEventListener implements ValueEventListener
    {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot)
        {
            ClientSnapShot = dataSnapshot;

            SnapShot = dataSnapshot.child("CurrentClientsCount");

            NumberOfClients = Long.parseLong(SnapShot.getValue().toString());
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
        String x;

        boolean flag;

        username = Username.getText().toString();

        password = Password.getText().toString();

        final String Check = CheckLogin(username,password);

        if( Check.equals("Admin") )
        {
            flag = true;

            for(DataSnapshot SnapShot : AdminSnapShot.getChildren() )
            {
                if( flag )
                {
                    flag = false;

                    continue;
                }

                x = SnapShot.child("username").getValue().toString();

                if( x.equals(username) )
                {
                    Log.d("Status","Failed,User Already Exists ");

                    Toast.makeText(this, "User Already Exists", Toast.LENGTH_SHORT).show();

                    return;
                }
            }
        }

        else if( Check.equals("Client") )
        {
            flag = true;

            for(DataSnapshot SnapShot : ClientSnapShot.getChildren() )
            {
                if( flag )
                {
                    flag = false;

                    continue;
                }

                x = SnapShot.child("username").getValue().toString();

                if( x.equals(username) )
                {
                    Log.d("Status","Failed,User Already Exists");

                    Toast.makeText(this, "User Already Exists", Toast.LENGTH_SHORT).show();

                    return;
                }
            }
        }

        if( Check.equals("Admin") )
        {
            AddAdmins(username,password);

            Toast.makeText(this,"User Created",Toast.LENGTH_SHORT);
        }
        else if( Check.equals("Client") )
        {
            AddClients(username,password);

            Toast.makeText(this,"User Created",Toast.LENGTH_SHORT);
        }
        else if( Check.equals("Fasaak") )
        {
            Toast.makeText(this, "Creation Unsuccessful", Toast.LENGTH_SHORT).show();
        }
    }

    public String CheckLogin(final String username,final String password)
    {
        if( !(AdminOrClient.isChecked()) )
        {
            return "Admin";
        }

        else
        {
            return "Client";
        }
    }

    public void AddAdmins(String Username,String Password)
    {
        DataBaseTemp = DataBase.child("Admins");

        user.setUsername(Username);

        user.setPassword(Password);

        DataBaseTemp.child("admin"+Long.toString(++NumberOfAdmins)).setValue(user);

        DataBaseTemp.child("CurrentAdminsCount").setValue(NumberOfAdmins);

        DataBaseTemp.child("admin"+Long.toString(NumberOfAdmins)).child("status").setValue("Active");

        Log.d("snap","4");
    }

    public void AddClients(String Username,String Password)
    {
        DataBaseTemp = DataBase.child("Clients");

        user.setUsername(Username);

        user.setPassword(Password);

        DataBaseTemp.child("client"+Long.toString(++NumberOfClients)).setValue(user);

        DataBaseTemp.child("CurrentClientsCount").setValue(NumberOfClients);

        DataBaseTemp.child("client"+Long.toString(NumberOfClients)).child("status").setValue("Active");

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
    Method Complete cheystae admin count aentha mandhi vunnaro telustundhi listeners vadi today
    ->Debug cheyali
    ->Event Listeners Nerchukovali
    ->Ae Even Listener First trigger avthundho check karo
    ->Search cheyatam nerchukvali
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
