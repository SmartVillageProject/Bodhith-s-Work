package com.bodhith.smartvillages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


////////////////////////////////////////////// MAIN ////////////////////////////////////////////////

public class MainActivity extends AppCompatActivity
{
    private boolean Flag;

    private long NumberOfAdmins , NumberOfClients ;

    private EditText Username , Password ;

    private Button Submit ;

    private Switch AdminOrClient;

    private DatabaseReference DataBase = FirebaseDatabase.getInstance().getReference() ;

    private DatabaseReference DataBaseTemp ;

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

        DataBase.child("Admins").addValueEventListener(new AddAdminValueEventListener());

        DataBase.child("Clients").addValueEventListener(new AddClientValueEventListener());

        AdminOrClient = findViewById(R.id.AdminClientSwitch);

        AdminOrClient.setChecked(true);

        Submit =  findViewById(R.id.Submit);
    }


//////////////////////////////////////////////// END MAIN //////////////////////////////////////////

    public void Login(View v)
    {
        username = Username.getText().toString();

        password = Password.getText().toString();

        if( username.trim().length() == 0 || password.trim().length() == 0 )
        {
            Toast.makeText(this,"Enter Username and Password",Toast.LENGTH_SHORT);

            return ;
        }

        Log.d("values","1");

        if( !(AdminOrClient.isChecked()) )
        {
            CheckAdminsLogin();

            DataBaseTemp = DataBase.child("Admins");
        }

        else if( AdminOrClient.isChecked() )
        {
            CheckClientsLogin();

            DataBaseTemp = DataBase.child("Clients");
        }
    }

///////////////////////////////////////// MISC /////////////////////////////////////////////////////

    /*                              START CHECKING USERS                                          */
    public void CheckAdminsLogin()
    {
        Log.d("values","3");

        DataBaseTemp = DataBase.child("Admins");

        DataBaseTemp.addListenerForSingleValueEvent(new SearchUsersSingleValueEventListener());
    }

    public void CheckClientsLogin()
    {
        Log.d("values","4");

        DataBaseTemp = DataBase.child("Clients");

        DataBaseTemp.addListenerForSingleValueEvent(new SearchUsersSingleValueEventListener());
    }
    /*                             END CHECKING USERS                                             */

    /*                             START ADDING USERS                                             */
    public void AddAdmins(String Username,String Password)
    {
        DataBaseTemp = DataBase.child("Admins");

        user.setUsername(Username);

        user.setPassword(Password);

        DataBaseTemp.child("admin"+Long.toString(++NumberOfAdmins)).setValue(user);

        DataBaseTemp.child("CurrentAdminsCount").setValue(NumberOfAdmins);
    }

    public void AddClients(String Username,String Password)
    {
        DataBaseTemp = DataBase.child("Clients");

        user.setUsername(Username);

        user.setPassword(Password);

        DataBaseTemp.child("client"+Long.toString(++NumberOfClients)).setValue(user);

        DataBaseTemp.child("CurrentClientsCount").setValue(NumberOfClients);
    }
    /*                                END ADDING USERS                                            */

    /*                                 START SETTERS                                              */
    public void SetUsername(String Username)
    {
        user.setUsername(Username);
    }

    public void SetPassword(String Password)
    {
        user.setPassword(Password);
    }
    /*                                  END SETTERS                                               */

    /*                             START INTERFACE SELECT                                         */
    public void InterfaceSelect()
    {
        if ( Flag )
        {
            Log.d("LoginStatus","Successful");

            if( AdminOrClient.isChecked() )
            {
                Log.d("Interface123","Client");
            }

            else
            {
                Log.d("Interface123","Admin");
            }
        }

        else
        {
            Log.d("LoginStatus","Incorrect Login Credentials");
        }
    }
    /*                              END INTERFACE SELECT                                          */

/////////////////////////////////////////// END MISC ///////////////////////////////////////////////

//////////////////////////////////////////////// LISTENERS /////////////////////////////////////////

    /*                                           ADMIN                                            */
    class AddAdminValueEventListener implements ValueEventListener
    {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot)
        {
            SnapShot = dataSnapshot.child("CurrentAdminsCount");

            NumberOfAdmins = Long.parseLong(SnapShot.getValue().toString());
        }

        @Override
        public void onCancelled(DatabaseError databaseError)
        {

        }
    }

    public class SearchAdminClientSingleValueEventListener implements ValueEventListener
    {
        boolean flag;

        String TempUsername , TempPassword ;

        @Override
        public void onDataChange(DataSnapshot dataSnapshot)
        {
            Log.d("values","5");

            String Temp;

            Flag = false;

            flag = true;

            for( DataSnapshot SnapShot : dataSnapshot.getChildren() )
            {
                if( flag )
                {
                    flag = false;

                    continue;
                }

                TempUsername = SnapShot.child("username").getValue().toString();

                TempPassword = SnapShot.child("password").getValue().toString();

                Log.d("CheckPassword",""+TempUsername+TempPassword);

                Log.d("CheckPassword",""+username+password);

                if( TempUsername.equals(username) && TempPassword.equals(password) )
                {
                    Flag = true;

                    break;
                }
            }

            Log.d("values","6"+Boolean.toString(Flag));

            InterfaceSelect();

            Log.d("values","7");
        }

        @Override
        public void onCancelled(DatabaseError databaseError)
        {

        }
    }
    /*                                          END ADMIN                                         */

    /*                                         START CLIENT                                       */
    class AddClientValueEventListener implements ValueEventListener
    {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot)
        {
            SnapShot = dataSnapshot.child("CurrentClientsCount");

            NumberOfClients = Long.parseLong(SnapShot.getValue().toString());
        }

        @Override
        public void onCancelled(DatabaseError databaseError)
        {

        }
    }

    public class SearchUsersSingleValueEventListener implements ValueEventListener
    {
        boolean flag;

        String TempUsername , TempPassword ;

        @Override
        public void onDataChange(DataSnapshot dataSnapshot)
        {
            Log.d("values","5");

            String Temp;

            Flag = false;

            flag = true;

            for( DataSnapshot SnapShot : dataSnapshot.getChildren() )
            {
                if( flag )
                {
                    flag = false;

                    continue;
                }

                TempUsername = SnapShot.child("username").getValue().toString();

                TempPassword = SnapShot.child("password").getValue().toString();

                Log.d("CheckPassword",""+TempUsername+TempPassword);

                Log.d("CheckPassword",""+username+password);

                if( TempUsername.equals(username) && TempPassword.equals(password) )
                {
                    Flag = true;

                    break;
                }
            }

            InterfaceSelect();
        }

        @Override
        public void onCancelled(DatabaseError databaseError)
        {

        }
    }
    /*                                          END CLIENT                                        */

/////////////////////////////////////////////// END LISTENERS //////////////////////////////////////

}
