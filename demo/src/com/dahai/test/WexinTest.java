package com.dahai.test;

import net.sf.json.JSONObject;

import com.dahai.po.AccessToken;
import com.dahai.util.WexinUtil;

public class WexinTest {
	public static void main(String[] args) {
		try {
			AccessToken token = WexinUtil.getAccessToken();
			System.out.println("Ʊ��"+token.getToken());
			System.out.println("��Чʱ��"+token.getExpiresIn());
			
			//String path = "D:/imooc1.jpg";
			//String mediaId = WexinUtil.upload(path, token.getToken(), "thumb");
			//System.out.println(mediaId);
			
			
			String menu=JSONObject.fromObject(WexinUtil.initMenu()).toString();	
			int result=WexinUtil.createMenu(token.getToken(), menu);
			
			if (result==0) {
				System.out.println("�����˵��ɹ�");
			}else{
				System.out.println("������"+result);
			}
			
			
			//JSONObject jsonObject=WexinUtil.queryMenu(token.getToken());
			//System.out.println(jsonObject);
			
			/*int result=WexinUtil.deleteMenu(token.getToken());
			if(result==0){
				System.out.println("ɾ���ɹ�");
			}else{
				System.out.println("������"+result);
			}*/
			//String result = WexinUtil.translate("my name is laobi");
			//String result = WeixinUtil.translateFull("");
			//System.out.println(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}