import java.io.InputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Properties;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;

/**
 * <br>
 * 标题: <br>
 * 功能概要: <br>
 * 版权: cityyouth.cn (c) 2005 <br>
 * 公司:上海城市青年网 <br>
 * 创建时间:2005-12-21 <br>
 * 修改时间: <br>
 * 修改原因：
 * 
 * @author 张伟
 * @version 1.0
 */
public class HtmlParserTest {

	public static void initProxy(String host, int port, final String username,
			final String password) {
		Authenticator.setDefault(new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username,
						new String(password).toCharArray());
			}
		});
		Properties prop = System.getProperties();
		prop.setProperty("http.proxyHost", host);
		prop.setProperty("http.proxyPort", Integer.toString(port));
		prop.setProperty("http.proxyType", "4");
		prop.setProperty("http.proxySet", "true");

		// 设置不需要通过代理服务器访问的主机，可以使用*通配符，多个地址用|分隔
		prop.setProperty("http.nonProxyHosts", "localhost|10.*|*.efoxconn.com");
		// 设置安全访问使用的代理服务器地址与端口
		// 它没有https.nonProxyHosts属性，它按照http.nonProxyHosts 中设置的规则访问
		// prop.setProperty("https.proxyHost", "ehome-a.efoxconn.com");
		// prop.setProperty("https.proxyPort", "443");
		// HttpURLConnection是基于HTTP协议的，其底层通过socket通信实现。如果不设置超时（timeout），在网络异常的情况下，可能会导致程序僵死而不继续往下执行。可以通过以下两个语句来设置相应的超时：
		// System.setProperty("sun.net.client.defaultConnectTimeout",
		// "30000");
		// System.setProperty("sun.net.client.defaultReadTimeout", "30000");
	}

	public static void testHtml() {
		try {
			String sCurrentLine;
			String sTotalString;
			sCurrentLine = "";
			sTotalString = "";
			String proxy = "ehome-a.efoxconn.com";
			int port = 3128;
			String username = "F3229233";
			String password = "password";
			initProxy(proxy, port, username, password);
			
			URL url = new URL("http://www.hao123.com"); //http://www.tuicool.com/ah/
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// JDK 1.5以前的版本，只能通过设置这两个系统属性来控制网络超时。在1.5中，还可以使用HttpURLConnection的父类URLConnection的以下两个方法：
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			// 增加报头，模拟浏览器，防止屏蔽
			conn.setRequestProperty(
					"User-Agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727)");
			conn.setRequestProperty("Accept", "text/html");// 只接受text/html类型，当然也可以接受图片,pdf,*/*任意，就是tomcat/conf/web里面定义那些
			conn.connect();
			InputStream is = conn.getInputStream();
			java.io.BufferedReader l_reader = new java.io.BufferedReader(new java.io.InputStreamReader(is));
			
			while ((sCurrentLine = l_reader.readLine()) != null) {
				sTotalString += sCurrentLine;
			}
			System.out.println(sTotalString);

			System.out.println("====================");
			String testText = getTextList(sTotalString);
			System.out.println(testText);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 抽取纯文本信息
	 * 
	 * @param inputHtml
	 * @return
	 */
	public static String extractText(String inputHtml) throws Exception {
		StringBuffer text = new StringBuffer();
		Parser parser = Parser.createParser(new String(inputHtml.getBytes(),
				"8859-1"), "8859-1");
//		parser.setEncoding("utf-8");
		// 遍历所有的节点
		NodeList nodes = parser.extractAllNodesThatMatch(new NodeFilter() {
			public boolean accept(Node node) {
				return true;
			}
		});
		Node node = nodes.elementAt(0);
		text.append(new String(node.toPlainTextString().getBytes("8859-1")));
		return text.toString();
	}

	/**
	 * 抽取纯文本信息
	 * 
	 * @param inputHtml
	 * @return
	 */
	public static String getTextList(String inputHtml) throws Exception {
		
		Parser parser = Parser.createParser(new String(inputHtml.getBytes("utf-8")),"utf-8");
//		Parser parser = Parser.createParser(inputHtml, "utf-8");
		
		AndFilter filter = new AndFilter(
				new NodeFilter[] {
						new TagNameFilter("a")
						//new TagNameFilter("table"),
						//new HasAttributeFilter("id", "tblSongList")
				}
			);
		NodeList nodes = parser.parse(filter);
		// 遍历所有的节点
		/*NodeList nodes = parser.extractAllNodesThatMatch(new NodeFilter() {
			public boolean accept(Node node) {
				if(node.get){
					return true;
				}
				return false;
			}
		});*/
		int size = nodes.size();
		for (int i = 0; i < size; i++) {
			Node node = nodes.elementAt(i);
			System.out.println(node.getText());
		}
		
		
		//text.append(new String(node.toPlainTextString().getBytes("8859_1")));
		return "";
	}
	
	/**
	 * 读取文件的方式来分析内容. filePath也可以是一个Url.
	 * 
	 * @param resource
	 *            文件/Url
	 */
	public static void test5(String resource) throws Exception {
		Parser myParser = new Parser(resource);

		// 设置编码
		myParser.setEncoding("GBK");
		String filterStr = "table";
		NodeFilter filter = new TagNameFilter(filterStr);
		NodeList nodeList = myParser.extractAllNodesThatMatch(filter);
		TableTag tabletag = (TableTag) nodeList.elementAt(11);

		System.out.println(tabletag.toHtml());

		System.out.println("==============");

	}

	public static void main(String[] args) throws Exception {
		//test5("http://www.baidu.com");
		testHtml();
	}
}