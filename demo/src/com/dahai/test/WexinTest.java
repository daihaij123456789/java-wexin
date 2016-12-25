package com.dahai.test;

import com.dahai.po.AccessToken;
import com.dahai.util.WexinUtil;

public class WexinTest {
	public static void main(String[] args) {
		try {
			AccessToken token = WexinUtil.getAccessToken();
			System.out.println("票据"+token.getToken());
			System.out.println("有效时间"+token.getExpiresIn());
			
			String path = "D:/imooc1.jpg";
			String mediaId = WexinUtil.upload(path, token.getToken(), "thumb");
			System.out.println(mediaId);
			
			//String result = WexinUtil.translate("my name is laobi");
			//String result = WeixinUtil.translateFull("");
			//System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}