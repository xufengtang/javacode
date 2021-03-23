
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.SSLContext;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 読み込みクラス
 * 
 */
public class ReadUtil {
	
	
	public static List<ReadBean> readRss(String url)throws Exception{

		List<ReadBean> readBeanList = new ArrayList<ReadBean>();

		Document document = httpRequest(url);
		Elements items = document.select("item");
		for (Element item : items) {
			ReadBean  readBean  = new ReadBean();
			Elements titles = item.select("title");
			for (Element title : titles) {
				readBean.setTitle(title.text());
			}
			Elements bodys = item.select("description");
			
			for (Element body : bodys) {
				readBean.setBody(body.text());
			}
			readBeanList.add(readBean);
		}
		return readBeanList;
		
	}
	
	public static List<ReadBean> readTxt(String pathName)throws Exception{
		List<ReadBean> readBeanList = new ArrayList<ReadBean>();
		List<String> lines = txtRequest(pathName);
		for(int i=0;i<lines.size();i=i+2){
			ReadBean readBean = new ReadBean();
			String lineTitle = lines.get(i);
			String lineBody = lines.get(i+1);
			readBean.setTitle(lineTitle.split("title:")[1]);
			readBean.setBody(lineBody.split("body:")[1]);
			readBeanList.add(readBean);
		}
		
		return readBeanList;
	}
	
	

	private static Document httpRequest(String req_url) throws Exception {

		String html = "";
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			SSLConnectionSocketFactory sslsf = createSSLConnSocketFactory();
			httpclient = HttpClients.custom().setSSLSocketFactory(sslsf)
					.build();
			HttpGet httpget = new HttpGet(req_url);
			httpget.addHeader(HttpHeaders.USER_AGENT,"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:86.0) Gecko/20100101 Firefox/86.0");
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(10000).setConnectTimeout(10000).build();
			httpget.setConfig(requestConfig);

			response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();

			int resStatu = response.getStatusLine().getStatusCode();
			if (resStatu == HttpStatus.SC_OK) {
				// 获得相应实体
				if (entity != null) {
					html = EntityUtils.toString(entity, "UTF-8");
					html = html.replaceAll("&nbsp;", " ");
				}
			}
			EntityUtils.consume(entity);
		} catch (Exception e) {
			throw e;
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					throw e;
				}
			}
			if (httpclient != null) {
				try {
					httpclient.close();
				} catch (IOException e) {
					throw e;
				}
			}
		}
		

		Document document = Jsoup.parse(html);
		
		return document;

	}

	private static SSLConnectionSocketFactory createSSLConnSocketFactory()
			throws Exception {
		SSLContext sslcontext = SSLContexts.createSystemDefault();

		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				sslcontext, new String[] { "TLSv1.2" }, null,
				SSLConnectionSocketFactory.getDefaultHostnameVerifier());
		return sslsf;
	}
	
	
	private static List<String> txtRequest(String txtUrl) throws Exception {
		String line;
		List<String> lines = new ArrayList<String>();	
		FileReader reader = new FileReader(txtUrl);
		BufferedReader br = new BufferedReader(reader);
		
		while ((line=br.readLine())!=null){
			if(!"".equals(line)){
				lines.add(line);
			}
			
		}
		return lines;
	}

}
