package weibo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import net.sf.json.JSONObject;

public class login
{
	
	
	public static String readStreamByEncoding(String utf) throws UnsupportedEncodingException 
	{
		Document doc = Jsoup.parse(utf);
		Elements script_body = doc.getElementsByTag("script");
		String regex_FM = "([\\s\\S]*?)FM.view\\(([\\s\\S]*?)(\\);|\\))</script>";
		String regex = "([\\s\\S]*?)homefeed([\\s\\S]*?)</script>";
		Pattern p  = Pattern.compile(regex_FM);
		for(Element body: script_body)
		{
			//String utf8_body = URLEncoder.encode(body.toString(), "UTF-8");  
			Matcher m = p.matcher(body.toString());
			if(m.find())
			{
				String main_content =m.group(2);
				
				JSONObject json = JSONObject.fromObject(main_content);
				if(json.has("html"))
				{
				  String html_content = json.getString("html");
				  if(json.getString("ns").compareTo("pl.content.homeFeed.index")==0 && json.getString("domid").compareTo("Pl_Official_MyProfileFeed__22") ==0)
				  {
					  System.out.println("body:\n "+body.toString() +"\nmain content :\n"+ main_content);	  
				  }
				  Document div_doc = Jsoup.parseBodyFragment(html_content);
				  Element div_body = div_doc.body();
				  Elements v6_homefeeds = div_body.getElementsByAttributeValue("class","WB_feed WB_feed_v3 WB_feed_v4");
				  
				  if(v6_homefeeds !=null)
				  {
					  
					  for(Element v6_homefeed:v6_homefeeds)
					  {
						  Elements homefeeds_like  = v6_homefeed.getElementsByAttributeValue("class", "WB_cardwrap WB_feed_type S_bg2 WB_feed_like");
						  if(homefeeds_like !=null)
						  {
							  for(Element homefeed_like:homefeeds_like)
							  {
								  Element wb_detail= homefeed_like.getElementsByAttributeValue("class","WB_feed_detail clearfix").first().getElementsByAttributeValue("class", "WB_detail").first();
								  if(wb_detail !=null)
								  {
									  Element s_txt2 = wb_detail.getElementsByAttributeValue("class", "WB_from S_txt2").first();
									  Elements s_txt2_a =  s_txt2.getElementsByTag("a");
									  for(Element a: s_txt2_a)
									  {
										  if(a!=null && a.hasText())
											  System.out.println(a.text());
									  }
									  Element W_f14 = wb_detail.getElementsByAttributeValue("class", "WB_text W_f14").first();
									  if(W_f14!=null && W_f14.hasText())
										  System.out.println(W_f14.text());
									  
									  Element feed_expand = wb_detail.getElementsByAttributeValue("class", "WB_feed_expand").first();
									  if(feed_expand !=null) {
										  Element WB_text = feed_expand.getElementsByAttributeValue("class", "WB_text").first();
										  if(WB_text !=null && WB_text.hasText())
											  System.out.println(WB_text.text());
									  }
									  
								  }  
							  }
						  }					  
					  }
				  
				  }
						
				}
			}
		}
		return utf;
	}
	public static void main(String[] args) 
	{
		SinaWeibo weibo = new SinaWeibo();
		if(weibo.login_demo())
		{
			String uniqueid = weibo.getUniqueid();
			String userdomain = weibo.getUserDomain();
			HttpUtil hUtil =weibo.getUtils();
			System.out.println("µÇÂ½³É¹¦");
			try {
				String final_url = "https://weibo.com/"+uniqueid+userdomain ;
				System.out.println(final_url);
				String con = hUtil.getRequests(weibo.client, final_url,"UTF-8");
				/*
				 * Reading the main text from my website
				 * */	 
				readStreamByEncoding(con);
				
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else
		{
			System.out.println("µÇÂ¼Ê§°Ü");
		}
	
	
	}
	
}