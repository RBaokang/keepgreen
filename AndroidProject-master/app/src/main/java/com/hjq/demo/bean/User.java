package com.hjq.demo.bean;

public class User {
    private int id;
    private String name;
    private String password;
    private String phone;
    private String diqu;
    private String image;
    public User(int id, String name, String password, String phone, String diqu, String image) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.diqu = diqu;
        this.image = image;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getDiqu() {
        return diqu;
    }

    public String getImage() {
        return image;
    }
}
