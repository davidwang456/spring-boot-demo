package com.davidwang.security;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.binary.Base64;
//

public class Md5Signature {
//	private final static Logger logger = LoggerFactory.getLogger(Md5Signature.class);
	
	public static String signatureWithMd5(String data,String key)throws Exception{
		//resolve ascii  and no ascii problem
		byte[] paramByte=Base64.decodeBase64(data);
		data=new String(paramByte);
//		logger.info("Md5Signature.signatureWithMd5 parse string="+data+",key="+key);
		String sortData=sort(data);
		//param first md5
		byte[] firstEncode =DigestUtils.md5(sortData.getBytes());
		String addKey=new String(firstEncode)+"&key="+key;
//		logger.info("Md5Signature.signatureWithMd5 base64 result"+Base64.encodeBase64String(encodeMd5(addKey.getBytes())));
		// param and key second md5
		return Base64.encodeBase64String(encodeMd5(addKey.getBytes()));
	}
	
	public static String signatureWithMd5(String data,String key,long timestamp)throws Exception{
		//resolve ascii  and no ascii problem
		byte[] paramByte=Base64.decodeBase64(data);
		data=new String(paramByte);
		String time=long2String(timestamp);
//		logger.info("Md5Signature.signatureWithMd5 parse string="+data+",key="+key+",time="+time);
		String sortData=sort(data);
		//param first md5
		byte[] firstEncode =DigestUtils.md5(sortData.getBytes());
		String addKey=new String(firstEncode)+"&key="+key+"&timestamp="+time;
		// param and key second md5
//		logger.info("Md5Signature.signatureWithMd5 base64 result="+Base64.encodeBase64String(encodeMd5(addKey.getBytes())));
		return Base64.encodeBase64String(encodeMd5(addKey.getBytes()));
	}
	
	public static String sort(String data){ 
		String[] arr=data.split("&");
		Arrays.sort(arr);
		StringBuffer buf=new StringBuffer();
		for(int i=0;i<arr.length;i++){
			if(i==0){
				buf.append(arr[i]);
			}else{
				buf.append("&"+arr[i]);
			}
		}
		return buf.toString();
	}
	
	public static byte[] encodeMd5(byte[] data) throws Exception{
		return DigestUtils.md5(data);
	}
	
	public static String long2String(long timestamp) { 
		Date d = new Date(timestamp); 
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH"); 
		String time = formatter.format(d); 
		return time; 
		}
	
	
	public static void main(String[] args) throws Exception {
		String url="http://www.nonobank.com?acc=11&taa=www&zxx=13&cba=中午";
		String para=url.split("\\?")[1];
		Long timstamp=new Date().getTime();
		System.out.println(signatureWithMd5(Base64.encodeBase64URLSafeString(para.getBytes()),"123",timstamp));
	}
}
