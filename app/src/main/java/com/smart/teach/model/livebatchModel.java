package com.smart.teach.model;

public class livebatchModel {
    String Title;
    String Discription;
    String Price;
    String Duration;
    String Roomid;
    String Thumbnail;

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDiscription() {
        return Discription;
    }

    public void setDiscription(String discription) {
        Discription = discription;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getRoomid() {
        return Roomid;
    }

    public void setRoomid(String roomid) {
        Roomid = roomid;
    }
//    public livebatchModel(String discription, String duration, String price, String roomid) {
//        this.discription = discription;
//        this.duration = duration;
//        this.price = price;
//       //his.roomid = roomid;
//    }


}
