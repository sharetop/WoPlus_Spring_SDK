package cn.chinaunicom.woplus.openapi.spring;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


/**
 * Unit test for simple App.
 */
public class AppTest 
{
	/**
	 * 以下是我的测试应用的信息
	 * */
	//private static final String appKey="6fabfc63e6f1d23ee28172fc88ee24ca8ef7e044";
	//private static final String appSecret="e1b7aba30436d063f586cd67d0161e86b17e438a";
	
	private static final String platformID="501e9618-4305-4834-8847-d8044f8bad28";
	private static final String password ="sharetop1234";
	
	static WoPlusClient client;
	
	@BeforeClass
	public static void config(){
		
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
		client = (WoPlusClient) context.getBean("woplusClient");
		
	}
	
	@Test
	public void a_channel_getchannelpaymentsms(){
		String api_url="http://open.wo.com.cn/openapi/getchannelpaymentsms/v1.0";
		
		HashMap<String,Object> params = new HashMap<String,Object>();
		long num=new Random().nextLong();
		params.put("outTradeNo",Long.toString(num));
		params.put("timeStamp", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		params.put("subject", "金币一堆");
		params.put("totalFee", 0.05f);
		params.put("callbackUrl", "http://test.com:8080/notifycallback");
		params.put("appKey", "000000001");
		params.put("appName", "APP");
		
		try {
			WoPlusResponse resp = client.post(api_url, params, platformID, password);
			assertNotNull(resp);
			assertEquals("0",resp.resultCode);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertNull(e);
		}
	}
	
	@Test
	public void b_payment_paymentcodesms(){
		String api_url="http://open.wo.com.cn/openapi/rpc/paymentcodesms/v2.0";
		
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("paymentUser", "{mobile_number}");
		params.put("paymentType", 0);
		params.put("outTradeNo","1000018");
		params.put("paymentAcount", "001");
		params.put("subject", "金币一堆");
		params.put("totalFee", 0.01f);
		
		try {
			WoPlusResponse resp = client.post(api_url, params);
			assertNotNull(resp);
			assertEquals("0",resp.resultCode);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertNull(e);
		}
	}
	
	@Test
	public void c_payment_apppayment(){
		String api_url="http://open.wo.com.cn/openapi/rpc/apppayment/v2.0";
		
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("paymentUser", "{mobile_number}");
		params.put("outTradeNo","100008");
		params.put("paymentAcount", "001");
		params.put("subject", "金币一堆");
		params.put("totalFee", 0.01);
		params.put("paymentcodesms",362508);
		params.put("timeStamp", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		
		try {
			WoPlusResponse resp = client.post(api_url, params);
			assertNotNull(resp);
			assertEquals("0",resp.resultCode);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void d_authcode_authorizeSMS(){
		String api_url="http://open.wo.com.cn/openapi/authsms/v1.0";
		
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("scope", "instantLocation");
		params.put("number","{mobile_number}");
		
		try {
			WoPlusResponse resp = client.post(api_url, params);
			assertNotNull(resp);
			assertEquals("0",resp.resultCode);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
