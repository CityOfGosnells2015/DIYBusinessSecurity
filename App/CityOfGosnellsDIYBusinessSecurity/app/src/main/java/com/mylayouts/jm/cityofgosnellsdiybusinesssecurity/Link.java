package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

/**
 * Created by Gustavo on 15/04/2015.
 *
 * Represent an object for Important Link option on the main menu
 */
public class Link {

    private String name;
    private String phone;
    private String webPage;

    /*
        Constructors
     */
    public Link() {
    }

    public Link(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public Link(String name){
        this.name = name;
    }



    /*
        Get and Setters
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebPage() {
        return webPage;
    }

    public void setWebPage(String webPage) {
        this.webPage = webPage;
    }

    @Override
    public String toString() {
        return name + "," + phone + "," + webPage + "\n";
    }
}
