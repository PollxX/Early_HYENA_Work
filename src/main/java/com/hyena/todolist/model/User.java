package com.hyena.todolist.model;

/**
 *
 * @author k1715939
 */
public class User {
    
    private String name;
    private String password;
    private int userLevel;
    
    public User(String n, String p, int ul){
        this.name = n;
        this.password = p;
        this.userLevel = ul;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }
    
    
}
