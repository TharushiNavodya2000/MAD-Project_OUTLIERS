package com.example.project;

public class Model
{
    private String date;
    private String discription;
    private String place;
    private String time;

    public Model(String date,String discription,String place,String time)
    {
        this.date = date;
        this.discription = discription;
        this.place = place;
        this.time = time;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Model()
    {

    }

}
