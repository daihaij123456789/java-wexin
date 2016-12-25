package com.dahai.test;

import com.dahai.po.AccessToken;
import com.dahai.util.WexinUtil;

public class WexinTest {
	public static void main(String[] args) {
		try {
			AccessToken token = WexinUtil.getAccessToken();
			System.out.println("Ʊ��"+token.getToken());
			System.out.println("��Чʱ��"+token.getExpiresIn());
			
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