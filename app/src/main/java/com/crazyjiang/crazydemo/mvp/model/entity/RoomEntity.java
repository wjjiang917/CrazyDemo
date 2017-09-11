package com.crazyjiang.crazydemo.mvp.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Jiangwenjin on 2017/9/11.
 */
@Entity
public class RoomEntity {
    @Id
    private Integer id; // talent ID
    private String nickname;
    private int level;
    private int roomlevel;
    private String roomimg; // room poster
    private long roomexper;
    private String title; // title

    private String headimg;

    @Generated(hash = 1206795141)
    public RoomEntity(Integer id, String nickname, int level, int roomlevel,
            String roomimg, long roomexper, String title, String headimg) {
        this.id = id;
        this.nickname = nickname;
        this.level = level;
        this.roomlevel = roomlevel;
        this.roomimg = roomimg;
        this.roomexper = roomexper;
        this.title = title;
        this.headimg = headimg;
    }

    @Generated(hash = 1023035664)
    public RoomEntity() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getRoomlevel() {
        return this.roomlevel;
    }

    public void setRoomlevel(int roomlevel) {
        this.roomlevel = roomlevel;
    }

    public String getRoomimg() {
        return this.roomimg;
    }

    public void setRoomimg(String roomimg) {
        this.roomimg = roomimg;
    }

    public long getRoomexper() {
        return this.roomexper;
    }

    public void setRoomexper(long roomexper) {
        this.roomexper = roomexper;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeadimg() {
        return this.headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }
}
