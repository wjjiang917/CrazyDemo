package com.crazyjiang.crazydemo.mvp.model.api;

import com.crazyjiang.crazydemo.mvp.model.entity.LoginResultEntity;
import com.crazyjiang.crazydemo.mvp.model.entity.QueryResp;
import com.crazyjiang.crazydemo.mvp.model.entity.RankLabelEntity;
import com.crazyjiang.crazydemo.mvp.model.entity.RongLoginResp;
import com.crazyjiang.crazydemo.mvp.model.entity.RoomEntity;
import com.crazyjiang.crazydemo.mvp.model.entity.SimpResp;
import com.crazyjiang.crazydemo.mvp.model.entity.VideoEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 存放通用的一些API
 */
public interface HttpService {
    @FormUrlEncoded
    @POST("um/applogin")
    Observable<SimpResp<LoginResultEntity>> signIn(@Field("account") String account, @Field("password") String password, @Field("imei") String imei);

    @Headers({
            "App-Key: 8luwapkv8rb5l",
            "Signature: addf79447b8d30b23d7a683196bf2903e5c76433"
    })
    @FormUrlEncoded
    @POST("http://api.cn.ronghub.com/user/getToken.json")
    Observable<RongLoginResp> signInRong(@Field("userId") Integer userId, @Field("name") String name, @Field("portraitUri") String image);

    // get video list
    @GET("userVideos/list")
    Observable<QueryResp<List<VideoEntity>>> queryVideos(@Query("sort") String sort, @Query("userId") Integer userId, @Query("userType") Integer userType,
                                                         @Query("start") int start, @Query("offset") int offset);

    @GET("rank/popular")
    Observable<QueryResp<RankLabelEntity<RoomEntity>>> queryPopularTalents(@Query("start") int start, @Query("offset") int offset);
}
