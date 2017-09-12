package com.crazyjiang.crazydemo.mvp.model.entity;

/**
 * Created by Jiangwenjin on 2017/9/12.
 */

public class UserEntity {
    private Integer id;
    private String account;

    /**
     * 家族id
     */
    private Integer agency;
    private String email;
    private String nickname;

    /**
     * 状态:0 normal user, 1 anchor, 2 freeze (normal user), 3 freeze (anchor)
     */
    private Integer status;
    private String headimg;
    private Integer money;
    private Integer level;
    private long experience;
    private int roomlevel;
    private long roomexper;
    private Integer vip;// 1 Vip 2 Diamond Vip

    /**
     * 私聊留言
     */
    private String privateword;

    /**
     * 公告
     */
    private String notice;

    private String roomimg;
    private Integer concern;
    private Integer fans;
    private String cellphone;
    private Integer area;
    private String areaName;

    private Long birthday;

    /**
     * Sex 0: unknown, 1: female, 2: male
     */
    private Integer sex;

    private String comment;

    private Integer classify;

    private int count;
    private long time;

    //private String snsInfos;
    private boolean normalUser;

    private String authCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getAgency() {
        return agency;
    }

    public void setAgency(Integer agency) {
        this.agency = agency;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public long getExperience() {
        return experience;
    }

    public void setExperience(long experience) {
        this.experience = experience;
    }

    public int getRoomlevel() {
        return roomlevel;
    }

    public void setRoomlevel(int roomlevel) {
        this.roomlevel = roomlevel;
    }

    public long getRoomexper() {
        return roomexper;
    }

    public void setRoomexper(long roomexper) {
        this.roomexper = roomexper;
    }

    public Integer getVip() {
        return vip;
    }

    public void setVip(Integer vip) {
        this.vip = vip;
    }

    public String getPrivateword() {
        return privateword;
    }

    public void setPrivateword(String privateword) {
        this.privateword = privateword;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getRoomimg() {
        return roomimg;
    }

    public void setRoomimg(String roomimg) {
        this.roomimg = roomimg;
    }

    public Integer getConcern() {
        return concern;
    }

    public void setConcern(Integer concern) {
        this.concern = concern;
    }

    public Integer getFans() {
        return fans;
    }

    public void setFans(Integer fans) {
        this.fans = fans;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getClassify() {
        return classify;
    }

    public void setClassify(Integer classify) {
        this.classify = classify;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isNormalUser() {
        return normalUser;
    }

    public void setNormalUser(boolean normalUser) {
        this.normalUser = normalUser;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
}
