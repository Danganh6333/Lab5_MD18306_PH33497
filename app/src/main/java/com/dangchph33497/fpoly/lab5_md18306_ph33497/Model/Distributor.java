package com.dangchph33497.fpoly.lab5_md18306_ph33497.Model;

import com.google.gson.annotations.SerializedName;

public class Distributor {
    //Có thể dùnh Annotations của gson để đổi tên cho các trường nhận vào
    //Ví dụ trường _id nhận từ api ,thay vì đặt tên trường trong object là _id
    //Có thể đặt lại là id và thêm vào Annotation @SerializedName("_id")
    @SerializedName("_id")
    private  String id;
    private  String name,createdAt,updatedAt;

    public Distributor() {
    }

    public Distributor(String id, String name) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Distributor{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
