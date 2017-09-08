package com.crazyjiang.crazydemo.mvp.model.api;

import com.crazyjiang.crazydemo.mvp.model.entity.GankEntity;
import com.crazyjiang.crazydemo.mvp.model.entity.QueryResp;
import com.crazyjiang.crazydemo.mvp.model.entity.Video;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 存放通用的一些API
 */
public interface HttpApi {
    @GET("api/data/{type}/{pageSize}/{page}")
    Observable<GankEntity> gank(@Path("type") String type, @Path("pageSize") int pageSize, @Path("page") String page);

    // 随机获取一个妹子
    @GET("api/random/data/福利/1")
    Observable<GankEntity> getRandomGirl();


    // get video list
    @GET("userVideos/list")
    Observable<QueryResp<List<Video>>> queryVideos(@Query("sort") String sort, @Query("userId") Integer userId, @Query("userType") Integer userType,
                                                   @Query("start") int start, @Query("offset") int offset);
}
