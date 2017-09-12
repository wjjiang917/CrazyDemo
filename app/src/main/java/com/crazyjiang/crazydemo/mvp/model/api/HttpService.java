package com.crazyjiang.crazydemo.mvp.model.api;

import com.crazyjiang.crazydemo.mvp.model.entity.QueryResp;
import com.crazyjiang.crazydemo.mvp.model.entity.RankLabelEntity;
import com.crazyjiang.crazydemo.mvp.model.entity.RoomEntity;
import com.crazyjiang.crazydemo.mvp.model.entity.VideoEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 存放通用的一些API
 */
public interface HttpService {
    // get video list
    @GET("userVideos/list")
    Observable<QueryResp<List<VideoEntity>>> queryVideos(@Query("sort") String sort, @Query("userId") Integer userId, @Query("userType") Integer userType,
                                                         @Query("start") int start, @Query("offset") int offset);

    @GET("rank/popular")
    Observable<QueryResp<RankLabelEntity<RoomEntity>>> queryPopularTalents(@Query("start") int start, @Query("offset") int offset);
}
