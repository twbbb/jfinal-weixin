/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.jfinal.weixin.sdk.api;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.kit.HttpKit;
import com.jfinal.weixin.sdk.kit.ParaMap;
import com.jfinal.weixin.sdk.utils.JsonUtils;

/**
 * 用户管理 API
 * https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
 */
public class UserApi {
	
	private static String getUserInfo = "https://api.weixin.qq.com/cgi-bin/user/info";
	private static String getFollowers = "https://api.weixin.qq.com/cgi-bin/user/get";
	private static String batchGetUserInfo = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=";

	/**
	 * 获取用户基本信息（包括UnionID机制）
	 * @param openId 普通用户的标识，对当前公众号唯一
	 * @return ApiResult
	 */
	public static ApiResult getUserInfo(String openId) {
		ParaMap pm = ParaMap.create("access_token", AccessTokenApi.getAccessTokenStr()).put("openid", openId).put("lang", "zh_CN");
		return new ApiResult(HttpKit.get(getUserInfo, pm.getData()));
	}
	
	/**
	 * 获取用户列表
	 * @param nextOpenid 第一个拉取的OPENID，不填默认从头开始拉取
	 * @return ApiResult
	 */
	public static ApiResult getFollowers(String nextOpenid) {
		ParaMap pm = ParaMap.create("access_token", AccessTokenApi.getAccessTokenStr());
		if (nextOpenid != null)
			pm.put("next_openid", nextOpenid);
		return new ApiResult(HttpKit.get(getFollowers, pm.getData()));
	}
	
	/**
	 * 获取用户列表
	 * @return ApiResult
	 */
	public static ApiResult getFollows() {
		return getFollowers(null);
	}

	/**
	 * 批量获取用户基本信息, by Unas
	 */
	public static ApiResult batchGetUserInfo(String jsonStr) {
		String jsonResult = HttpKit.post(batchGetUserInfo + AccessTokenApi.getAccessTokenStr(), jsonStr);
		return new ApiResult(jsonResult);
	}
	
	private static String updateRemarkUrl = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=";
	
	/**
	 * 设置备注名
	 * @param openid
	 * @param remark
	 * @return
	 */
	public static ApiResult updateRemark(String openid, String remark) {
		String url = updateRemarkUrl + AccessTokenApi.getAccessTokenStr();
		
		Map<String, String> mapData = new HashMap<String, String>();
		mapData.put("openid", openid);
		mapData.put("remark", remark);
		String jsonResult = HttpKit.post(url, JsonUtils.toJson(mapData));
		
		return new ApiResult(jsonResult);
	}
}
