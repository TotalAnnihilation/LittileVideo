package com.example.littlevideo;


public class PhotoItem {

    private String url;
    private String videoURL;
    private String videoName;
    private boolean availability=true;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    @Override
    public String toString() {
        return "PhotoItem{" +
                "url='" + url + '\'' +
                ", videoURL='" + videoURL + '\'' +
                ", videoName='" + videoName + '\'' +
                ", availability=" + availability +
                '}';
    }
}
