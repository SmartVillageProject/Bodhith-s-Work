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


////////////////////////////////////////////// MAIN ///////////////////////////////////////////////////////

public class MainActivity extends AppCompatActivity
{

    private EditText Username,Password;

    private Button Submit;

    private DatabaseReference DataBase = FirebaseDatabase.getInstance().getReference();

    private DatabaseReference DataBaseTemp , Admins , Clients;

    private DataSnapshot SnapShot;

    private String username,password;

    private Users user = new Users();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();

        Username =  findViewById(R.id.Username);

        Password =  findViewById(R.id.Password);

        Submit =  findViewById(R.id.Submit);

        //Admins = DataBase.addChildEventListener(new AddAdminListener() );
    }

//////////////////////////////////////////////// END MAIN //////////////////////////////////////////////////

//////////////////////////////////////////////// LISTENERS /////////////////////////////////////////////////

    /*                                           ADMIN                                            */
    abstract class AddAdminListener implements ChildEventListener
    {

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s)
        {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot)
        {

        }

        @Override
        public void onCancelled(DatabaseError databaseError)
        {
            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }
    }
    /*                                          END ADMIN                                         */

/////////////////////////////////////////////// END LISTENERS /////////////////////////////////////////////

    public void Login(View v)
    {
        username = Username.getText().toString();

        password = Password.getText().toString();

        final String Check = CheckLogin(username,password);

        if( Check.equals("Admin") )
        {
            AddAdmins(username,password);

            Toast.makeText(this, "Admin Interface", Toast.LENGTH_SHORT).show();
        }
        else if( Check.equals("Client") )
        {
            AddClients(username,password);

            Toast.makeText(this, "Client Interface", Toast.LENGTH_SHORT).show();
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
        long NumberOfAdmins;

        DataBaseTemp = DataBase.child("Admins");

        user.setUsername(Username);

        user.setPassword(Password);

        DataBaseTemp.child("admin").setValue(user);
    }

    public void AddClients(String Username,String Password)
    {
        long NumberOfClients;

        DataBaseTemp = DataBase.child("Clients");

        user.setUsername(Username);

        user.setPassword(Password);

        DataBaseTemp.child("client").setValue(user);
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
}
