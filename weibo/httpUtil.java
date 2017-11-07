
package weibo;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpMessage;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

class HttpUtil{
	public Header[] getRequestHeader(String url)
	{
		Header[] responseHeader = null;
		HttpEntity entity =null ;
		String content = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet hGet = new HttpGet(url);
		try {
			CloseableHttpResponse response = httpClient.execute(hGet);
			responseHeader = response.getAllHeaders();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseHeader;
		
		
	}
	
	public String getRequest(CloseableHttpClient httpClient, String url) throws ParseException, IOException
	{
		HttpEntity entity =null ;
		String content = null;
		try {
			HttpGet hGet = new HttpGet(url);
			hGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:55.0) Gecko/20100101 Firefox/55.0");
			CloseableHttpResponse response = httpClient.execute(hGet);
			entity = response.getEntity();
			System.out.println("----------------------------");
			if(entity != null)
			{
				content = EntityUtils.toString(entity);	
			}
			System.out.println("----------------------------");
				
		}
		catch(ClientProtocolException e)
		{
			e.printStackTrace();
		}
		catch(ParseException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	
		return content;
		
	}
	
	public String postRequest(CloseableHttpClient httpclient,String url,List<NameValuePair> parms)
	{
		   // 创建默认的httpClient实例.  
		
        String content = null;
        // 创建httppost    
        HttpPost httppost = new HttpPost(url);  
        // 创建参数队列    
        UrlEncodedFormEntity uefEntity;  
        HttpEntity entity = null;
        try {  
            uefEntity = new UrlEncodedFormEntity(parms, "UTF-8");  
            httppost.setEntity(uefEntity);  
            System.out.println("executing request " + httppost.getURI());  
            CloseableHttpResponse response = httpclient.execute(httppost);  
            entity = response.getEntity();  
            if (entity != null) {
              content = EntityUtils.toString(entity, "UTF-8");
           }
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } 
		return content;  
	}
	
	public String getRequests(CloseableHttpClient client,String url,String utf) throws ClientProtocolException, IOException
	{
		 String instream = null;
	        HttpGet httpget = new HttpGet(url); 
	        CloseableHttpResponse response = client.execute(httpget);  
	        try {  
	            HttpEntity entity = response.getEntity();  
	            if (entity != null) {  
	                //创建一个输入流对象  
	                 instream = EntityUtils.toString(entity,utf);   
	                 System.out.println(instream);
	                 //如何从InputStream中读取数据到字符串  
	                //从输入流中读取数据   
	                  
	            }  
	         }catch(ClientProtocolException e)
	        {
	        	 e.printStackTrace();
	        }
			return instream;  
		
	}
	
	public InputStream getText (String content)
	{
		return null;
	}
	
}