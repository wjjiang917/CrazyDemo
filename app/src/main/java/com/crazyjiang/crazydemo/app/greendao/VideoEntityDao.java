package com.crazyjiang.crazydemo.app.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.crazyjiang.crazydemo.mvp.model.entity.VideoEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "VIDEO_ENTITY".
*/
public class VideoEntityDao extends AbstractDao<VideoEntity, Integer> {

    public static final String TABLENAME = "VIDEO_ENTITY";

    /**
     * Properties of entity VideoEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Integer.class, "id", true, "ID");
        public final static Property UserId = new Property(1, Integer.class, "userId", false, "USER_ID");
        public final static Property Source = new Property(2, int.class, "source", false, "SOURCE");
        public final static Property MediaId = new Property(3, String.class, "mediaId", false, "MEDIA_ID");
        public final static Property Caption = new Property(4, String.class, "caption", false, "CAPTION");
        public final static Property LowUrl = new Property(5, String.class, "lowUrl", false, "LOW_URL");
        public final static Property StandardUrl = new Property(6, String.class, "standardUrl", false, "STANDARD_URL");
        public final static Property LowImages = new Property(7, String.class, "lowImages", false, "LOW_IMAGES");
        public final static Property StandardImages = new Property(8, String.class, "standardImages", false, "STANDARD_IMAGES");
        public final static Property Link = new Property(9, String.class, "link", false, "LINK");
        public final static Property Tags = new Property(10, String.class, "tags", false, "TAGS");
        public final static Property OriLikes = new Property(11, int.class, "oriLikes", false, "ORI_LIKES");
        public final static Property Likes = new Property(12, int.class, "likes", false, "LIKES");
        public final static Property Comments = new Property(13, int.class, "comments", false, "COMMENTS");
        public final static Property Plays = new Property(14, int.class, "plays", false, "PLAYS");
        public final static Property Status = new Property(15, int.class, "status", false, "STATUS");
        public final static Property RawData = new Property(16, String.class, "rawData", false, "RAW_DATA");
        public final static Property NextMaxId = new Property(17, Integer.class, "nextMaxId", false, "NEXT_MAX_ID");
        public final static Property PubTime = new Property(18, Long.class, "pubTime", false, "PUB_TIME");
        public final static Property CreateTime = new Property(19, Long.class, "createTime", false, "CREATE_TIME");
        public final static Property NickName = new Property(20, String.class, "nickName", false, "NICK_NAME");
    }


    public VideoEntityDao(DaoConfig config) {
        super(config);
    }
    
    public VideoEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"VIDEO_ENTITY\" (" + //
                "\"ID\" INTEGER PRIMARY KEY ," + // 0: id
                "\"USER_ID\" INTEGER," + // 1: userId
                "\"SOURCE\" INTEGER NOT NULL ," + // 2: source
                "\"MEDIA_ID\" TEXT," + // 3: mediaId
                "\"CAPTION\" TEXT," + // 4: caption
                "\"LOW_URL\" TEXT," + // 5: lowUrl
                "\"STANDARD_URL\" TEXT," + // 6: standardUrl
                "\"LOW_IMAGES\" TEXT," + // 7: lowImages
                "\"STANDARD_IMAGES\" TEXT," + // 8: standardImages
                "\"LINK\" TEXT," + // 9: link
                "\"TAGS\" TEXT," + // 10: tags
                "\"ORI_LIKES\" INTEGER NOT NULL ," + // 11: oriLikes
                "\"LIKES\" INTEGER NOT NULL ," + // 12: likes
                "\"COMMENTS\" INTEGER NOT NULL ," + // 13: comments
                "\"PLAYS\" INTEGER NOT NULL ," + // 14: plays
                "\"STATUS\" INTEGER NOT NULL ," + // 15: status
                "\"RAW_DATA\" TEXT," + // 16: rawData
                "\"NEXT_MAX_ID\" INTEGER," + // 17: nextMaxId
                "\"PUB_TIME\" INTEGER," + // 18: pubTime
                "\"CREATE_TIME\" INTEGER," + // 19: createTime
                "\"NICK_NAME\" TEXT);"); // 20: nickName
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"VIDEO_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, VideoEntity entity) {
        stmt.clearBindings();
 
        Integer id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Integer userId = entity.getUserId();
        if (userId != null) {
            stmt.bindLong(2, userId);
        }
        stmt.bindLong(3, entity.getSource());
 
        String mediaId = entity.getMediaId();
        if (mediaId != null) {
            stmt.bindString(4, mediaId);
        }
 
        String caption = entity.getCaption();
        if (caption != null) {
            stmt.bindString(5, caption);
        }
 
        String lowUrl = entity.getLowUrl();
        if (lowUrl != null) {
            stmt.bindString(6, lowUrl);
        }
 
        String standardUrl = entity.getStandardUrl();
        if (standardUrl != null) {
            stmt.bindString(7, standardUrl);
        }
 
        String lowImages = entity.getLowImages();
        if (lowImages != null) {
            stmt.bindString(8, lowImages);
        }
 
        String standardImages = entity.getStandardImages();
        if (standardImages != null) {
            stmt.bindString(9, standardImages);
        }
 
        String link = entity.getLink();
        if (link != null) {
            stmt.bindString(10, link);
        }
 
        String tags = entity.getTags();
        if (tags != null) {
            stmt.bindString(11, tags);
        }
        stmt.bindLong(12, entity.getOriLikes());
        stmt.bindLong(13, entity.getLikes());
        stmt.bindLong(14, entity.getComments());
        stmt.bindLong(15, entity.getPlays());
        stmt.bindLong(16, entity.getStatus());
 
        String rawData = entity.getRawData();
        if (rawData != null) {
            stmt.bindString(17, rawData);
        }
 
        Integer nextMaxId = entity.getNextMaxId();
        if (nextMaxId != null) {
            stmt.bindLong(18, nextMaxId);
        }
 
        Long pubTime = entity.getPubTime();
        if (pubTime != null) {
            stmt.bindLong(19, pubTime);
        }
 
        Long createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindLong(20, createTime);
        }
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(21, nickName);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, VideoEntity entity) {
        stmt.clearBindings();
 
        Integer id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Integer userId = entity.getUserId();
        if (userId != null) {
            stmt.bindLong(2, userId);
        }
        stmt.bindLong(3, entity.getSource());
 
        String mediaId = entity.getMediaId();
        if (mediaId != null) {
            stmt.bindString(4, mediaId);
        }
 
        String caption = entity.getCaption();
        if (caption != null) {
            stmt.bindString(5, caption);
        }
 
        String lowUrl = entity.getLowUrl();
        if (lowUrl != null) {
            stmt.bindString(6, lowUrl);
        }
 
        String standardUrl = entity.getStandardUrl();
        if (standardUrl != null) {
            stmt.bindString(7, standardUrl);
        }
 
        String lowImages = entity.getLowImages();
        if (lowImages != null) {
            stmt.bindString(8, lowImages);
        }
 
        String standardImages = entity.getStandardImages();
        if (standardImages != null) {
            stmt.bindString(9, standardImages);
        }
 
        String link = entity.getLink();
        if (link != null) {
            stmt.bindString(10, link);
        }
 
        String tags = entity.getTags();
        if (tags != null) {
            stmt.bindString(11, tags);
        }
        stmt.bindLong(12, entity.getOriLikes());
        stmt.bindLong(13, entity.getLikes());
        stmt.bindLong(14, entity.getComments());
        stmt.bindLong(15, entity.getPlays());
        stmt.bindLong(16, entity.getStatus());
 
        String rawData = entity.getRawData();
        if (rawData != null) {
            stmt.bindString(17, rawData);
        }
 
        Integer nextMaxId = entity.getNextMaxId();
        if (nextMaxId != null) {
            stmt.bindLong(18, nextMaxId);
        }
 
        Long pubTime = entity.getPubTime();
        if (pubTime != null) {
            stmt.bindLong(19, pubTime);
        }
 
        Long createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindLong(20, createTime);
        }
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(21, nickName);
        }
    }

    @Override
    public Integer readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0);
    }    

    @Override
    public VideoEntity readEntity(Cursor cursor, int offset) {
        VideoEntity entity = new VideoEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // userId
            cursor.getInt(offset + 2), // source
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // mediaId
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // caption
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // lowUrl
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // standardUrl
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // lowImages
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // standardImages
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // link
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // tags
            cursor.getInt(offset + 11), // oriLikes
            cursor.getInt(offset + 12), // likes
            cursor.getInt(offset + 13), // comments
            cursor.getInt(offset + 14), // plays
            cursor.getInt(offset + 15), // status
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // rawData
            cursor.isNull(offset + 17) ? null : cursor.getInt(offset + 17), // nextMaxId
            cursor.isNull(offset + 18) ? null : cursor.getLong(offset + 18), // pubTime
            cursor.isNull(offset + 19) ? null : cursor.getLong(offset + 19), // createTime
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20) // nickName
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, VideoEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0));
        entity.setUserId(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setSource(cursor.getInt(offset + 2));
        entity.setMediaId(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCaption(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setLowUrl(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setStandardUrl(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setLowImages(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setStandardImages(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setLink(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setTags(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setOriLikes(cursor.getInt(offset + 11));
        entity.setLikes(cursor.getInt(offset + 12));
        entity.setComments(cursor.getInt(offset + 13));
        entity.setPlays(cursor.getInt(offset + 14));
        entity.setStatus(cursor.getInt(offset + 15));
        entity.setRawData(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setNextMaxId(cursor.isNull(offset + 17) ? null : cursor.getInt(offset + 17));
        entity.setPubTime(cursor.isNull(offset + 18) ? null : cursor.getLong(offset + 18));
        entity.setCreateTime(cursor.isNull(offset + 19) ? null : cursor.getLong(offset + 19));
        entity.setNickName(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
     }
    
    @Override
    protected final Integer updateKeyAfterInsert(VideoEntity entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public Integer getKey(VideoEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(VideoEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
