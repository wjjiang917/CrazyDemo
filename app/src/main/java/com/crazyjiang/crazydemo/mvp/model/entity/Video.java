package com.crazyjiang.crazydemo.mvp.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Video {
    /**
     * video id
     */
    @Id
    Integer id;

    /**
     * user id
     */
    Integer userId;

    /**
     * video source.    4-Instagram
     */
    int source;

    String mediaId;

    /**
     * caption
     */
    String caption;

    /**
     * video url with low resolution
     */
    String lowUrl;

    /**
     * video url with standard resolution
     */
    String standardUrl;

    /**
     * video image
     */
    String lowImages;
    String standardImages;

    /**
     * link
     */
    String link;
    String tags;
    int oriLikes;
    int likes;
    int comments;
    int plays;

    /**
     * 0: available    -1: unavailable   1: private
     */
    int status;
    String rawData;
    Integer nextMaxId;
    Long pubTime;
    Long createTime;
    String nickName;
    @Generated(hash = 353230225)
    public Video(Integer id, Integer userId, int source, String mediaId,
            String caption, String lowUrl, String standardUrl, String lowImages,
            String standardImages, String link, String tags, int oriLikes,
            int likes, int comments, int plays, int status, String rawData,
            Integer nextMaxId, Long pubTime, Long createTime, String nickName) {
        this.id = id;
        this.userId = userId;
        this.source = source;
        this.mediaId = mediaId;
        this.caption = caption;
        this.lowUrl = lowUrl;
        this.standardUrl = standardUrl;
        this.lowImages = lowImages;
        this.standardImages = standardImages;
        this.link = link;
        this.tags = tags;
        this.oriLikes = oriLikes;
        this.likes = likes;
        this.comments = comments;
        this.plays = plays;
        this.status = status;
        this.rawData = rawData;
        this.nextMaxId = nextMaxId;
        this.pubTime = pubTime;
        this.createTime = createTime;
        this.nickName = nickName;
    }
    @Generated(hash = 237528154)
    public Video() {
    }
    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getUserId() {
        return this.userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public int getSource() {
        return this.source;
    }
    public void setSource(int source) {
        this.source = source;
    }
    public String getMediaId() {
        return this.mediaId;
    }
    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
    public String getCaption() {
        return this.caption;
    }
    public void setCaption(String caption) {
        this.caption = caption;
    }
    public String getLowUrl() {
        return this.lowUrl;
    }
    public void setLowUrl(String lowUrl) {
        this.lowUrl = lowUrl;
    }
    public String getStandardUrl() {
        return this.standardUrl;
    }
    public void setStandardUrl(String standardUrl) {
        this.standardUrl = standardUrl;
    }
    public String getLowImages() {
        return this.lowImages;
    }
    public void setLowImages(String lowImages) {
        this.lowImages = lowImages;
    }
    public String getStandardImages() {
        return this.standardImages;
    }
    public void setStandardImages(String standardImages) {
        this.standardImages = standardImages;
    }
    public String getLink() {
        return this.link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public String getTags() {
        return this.tags;
    }
    public void setTags(String tags) {
        this.tags = tags;
    }
    public int getOriLikes() {
        return this.oriLikes;
    }
    public void setOriLikes(int oriLikes) {
        this.oriLikes = oriLikes;
    }
    public int getLikes() {
        return this.likes;
    }
    public void setLikes(int likes) {
        this.likes = likes;
    }
    public int getComments() {
        return this.comments;
    }
    public void setComments(int comments) {
        this.comments = comments;
    }
    public int getPlays() {
        return this.plays;
    }
    public void setPlays(int plays) {
        this.plays = plays;
    }
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getRawData() {
        return this.rawData;
    }
    public void setRawData(String rawData) {
        this.rawData = rawData;
    }
    public Integer getNextMaxId() {
        return this.nextMaxId;
    }
    public void setNextMaxId(Integer nextMaxId) {
        this.nextMaxId = nextMaxId;
    }
    public Long getPubTime() {
        return this.pubTime;
    }
    public void setPubTime(Long pubTime) {
        this.pubTime = pubTime;
    }
    public Long getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
    public String getNickName() {
        return this.nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}

