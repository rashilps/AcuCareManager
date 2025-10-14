package com.example.acucaremanager.pojo;

public class ClientPojo {
    private String name;
    private String contact;
    private String notes;
    public ClientPojo(){}
    public ClientPojo(String name, String contact,String notes){
        this.name=name;
        this.contact = contact;
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
