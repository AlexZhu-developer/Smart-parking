package com.nineclient.cherry.wcc.util;

import java.util.Random;

public class RadomStr {

	public static String getRandomString(int length) { //length��ʾ����ַ�ĳ���  
	    String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIKLMNOPQRSTUVWXYZ0123456789";     
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < length; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number)); 
	    }     
	    return sb.toString();     
	 } 
	public static void main(String args[]){
		System.out.println(getRandomString(64));
	}
}
