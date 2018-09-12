package com.bodhith.smartvillages;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Users
{
    private String Username;

    private String Password;

    @JsonIgnore
    private String Key;

    public Users()
    {

    }

    public Users(String Username, String Password)
    {
        this.Username = Username;
        this.Password = Password;
    }

    public String getUsername()
    {
        return Username;
    }

    public void setUsername(String Username)
    {
        this.Username = Username;
    }

    public String getPassword()
    {
        return Password;
    }

    public void setPassword(String Password)
    {
        this.Password = Password;
    }
}
