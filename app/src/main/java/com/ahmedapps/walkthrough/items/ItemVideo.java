package com.ahmedapps.walkthrough.items;

public class ItemVideo {

    private final String youtube_video_id;
    private final String image_cover;

    public ItemVideo(String youtube_video_id, String image_cover) {
        this.youtube_video_id = youtube_video_id;
        this.image_cover = image_cover;
    }

    public String getYoutube_video_id() {
        return youtube_video_id;
    }

    public String getImage_cover() {
        return image_cover;
    }
}