package com.myvelux.myvelux;

import java.io.Serializable;

/**
 * Created by geoffrey on 16/06/16.
 */
public class User  implements Serializable {

    private String id;
    private String userName;
    private String password;
    private int isAdmin;

    public User(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }


}
