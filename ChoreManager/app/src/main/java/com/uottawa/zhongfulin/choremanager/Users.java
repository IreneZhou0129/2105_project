package com.uottawa.zhongfulin.choremanager;

/**
 * Created by zhongfulin on 2017-12-03.
 */

public class Users {
    private String name;
    private String Email;
    private String id;
    private String point;

    public Users(String id,String name,String Email,String point){
        this.id=id;
        this.name=name;
        this.Email=Email;
        this.point=point;


    }
    public Users() {
//        this.id = id;
//        this.name = name;
//        this.Email = Email;
//        this.point = 0;
    }

    public String getId(){
        return id;

    }
    public void setId(String id){
        this.id=id;
    }
    public String getName(){
        return name;

    }
    public void setName(String name){
        this.name=name;
    }
    public String getEmail(){
        return Email;

    }
    public void setEmail(String Email){
        this.Email=Email;
    }
    public String getPoint(){
        return point;

    }
    public String increasePoint(){
        int tem= Integer.parseInt(point);
        tem=tem+1;
        String temp= Integer.toString(tem);
        point=temp;
        return point;
    }
}
