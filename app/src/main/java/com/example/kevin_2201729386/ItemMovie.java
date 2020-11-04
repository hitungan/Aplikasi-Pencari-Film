package com.example.kevin_2201729386;

public class ItemMovie {

    String Judul;
    String ID;
    String Year;
    String LinkGambar;

    public ItemMovie(String judul, String ID, String year, String linkGambar) {
        Judul = judul;
        this.ID = ID;
        Year = year;
        LinkGambar = linkGambar;
    }

    public void changeText1(String text){
        Judul = text;
    }

    public String getJudul() {
        return Judul;
    }

    public void setJudul(String judul) {
        Judul = judul;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getLinkGambar() {
        return LinkGambar;
    }

    public void setLinkGambar(String linkGambar) {
        LinkGambar = linkGambar;
    }
}
