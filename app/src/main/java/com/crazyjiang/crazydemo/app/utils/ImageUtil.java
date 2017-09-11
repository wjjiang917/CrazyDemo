package com.crazyjiang.crazydemo.app.utils;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.Map;

/**
 * 图片加载辅助工具类
 * Created by ios on 15/6/25.
 */
public class ImageUtil {
    private static final String IMAGE_CONFIG = "{\"1\":{\"A\":800,\"B\":1280},\"2\":{\"A\":128,\"B\":480},\"3\":{\"A\":256},\"4\":{\"A\":48,\"B\":128},\"5\":{\"A\":256},\"6\":{\"D\":800,\"A\":128,\"B\":256,\"C\":480},\"7\":{\"A\":80,\"B\":128,\"C\":256},\"8\":{\"D\":640,\"A\":80,\"B\":128,\"C\":256},\"9\":{\"A\":480,\"B\":128},\"10\":{\"A\":1024}}";

    /**
     * 图片类型:广告、汽车、家族、礼物、节目、动态、相册、头像(用户)、头像(主播)
     */
    private enum Type {
        AD(1), CAR(2), AGENCY(3), GIFT(4), SHOW(5), ALBUM(6), HEAD(7), TALENT(8);

        Integer value;

        Type(Integer v) {
            value = v;
        }

        public Integer getValue() {
            return value;
        }
    }

    private static String getDefaultImg() {
        return "http://s3-us-west-2.amazonaws.com/thankyo/image/default.jpg";
    }

    private static String getPath(Type type, Integer userId) {
        String path = "http://s3-us-west-2.amazonaws.com/thankyo/image/" + type.getValue() + "/";
        if (Type.ALBUM.equals(type) || Type.HEAD.equals(type) || Type.TALENT.equals(type)) {
            String tempId = userId + "";

            // FIXME: 2017/4/20 if livebaze user, change bucket name to "thankyo3"
            if (tempId.startsWith("5")) {
                path = "http://s3-us-west-2.amazonaws.com/thankyo3/image/" + type.getValue() + "/";
            }

            String temp = "000000000000";
            temp = temp.substring(0, 12 - tempId.length()) + tempId;
            path += temp.substring(0, 4) + "/" + temp.substring(4, 8) + "/" + temp.substring(8, 12) + "/";
        }
        return path;
    }

    private static String getName(String img, int size) {
        if (img.indexOf('.') == -1) {
            return img;
        }
        return img.substring(0, img.indexOf('.')) + '_' + size + img.substring(img.indexOf('.'));
    }

    private static int getSize(String scene, int type) {
        int size = 0;
        Map<String, Integer> sceneMap;
        if (!TextUtils.isEmpty(scene)) {
            Map<Integer, Map<String, Integer>> imageConfig = new Gson().fromJson(IMAGE_CONFIG, Map.class);
            if (null != imageConfig && !imageConfig.isEmpty()) {
                sceneMap = imageConfig.get(type);
                if (null != sceneMap && !sceneMap.isEmpty() && null != sceneMap.get(scene)) {
                    size = sceneMap.get(scene);
                }
            }
        }
        return size;
    }

    /**
     * 用户头像
     */
    public static String head(String img, Integer userId, String scene) {
        if (TextUtils.isEmpty(img) || null == userId) {
            return getDefaultImg();
        } else {
            int size = getSize(scene, Type.HEAD.getValue());

            // from other platforms: https://s3-ap-southeast-1.amazonaws.com/thankyo7/image/7/0000/0500/0083/1476565196010_0.jpg
            if (img.startsWith("http")) {
                if (img.contains("_0") && size != 0) {
                    return img.replace("_0.", "_" + size + ".");
                } else {
                    return img;
                }
            } else {
                return getPath(Type.HEAD, userId) + getName(img, size);
            }
        }
    }

    /**
     * 主播图片
     */
    public static String talent(String img, Integer userId, String scene) {
        if (TextUtils.isEmpty(img) || null == userId) {
            return getDefaultImg();
        } else {
            int size = getSize(scene, Type.TALENT.getValue());

            if (img.startsWith("http")) {
                if (img.contains("_0") && size != 0) {
                    return img.replace("_0.", "_" + size + ".");
                } else {
                    return img;
                }
            } else {
                return getPath(Type.TALENT, userId) + getName(img, size);
            }
        }
    }
}
