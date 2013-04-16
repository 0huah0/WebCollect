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
 * ����: <br>
 * ���ܸ�Ҫ: <br>
 * ��Ȩ: cityyouth.cn (c) 2005 <br>
 * ��˾:�Ϻ����������� <br>
 * ����ʱ��:2005-12-21 <br>
 * �޸�ʱ��: <br>
 * �޸�ԭ��
 * 
 * @author ��ΰ
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

		// ���ò���Ҫͨ��������������ʵ�����������ʹ��*ͨ����������ַ��|�ָ�
		prop.setProperty("http.nonProxyHosts", "localhost|10.*|*.efoxconn.com");
		// ���ð�ȫ����ʹ�õĴ����������ַ��˿�
		// ��û��https.nonProxyHosts���ԣ�������http.nonProxyHosts �����õĹ������
		// prop.setProperty("https.proxyHost", "ehome-a.efoxconn.com");
		// prop.setProperty("https.proxyPort", "443");
		// HttpURLConnection�ǻ���HTTPЭ��ģ���ײ�ͨ��socketͨ��ʵ�֡���������ó�ʱ��timeout�����������쳣������£����ܻᵼ�³�����������������ִ�С�����ͨ���������������������Ӧ�ĳ�ʱ��
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
			// JDK 1.5��ǰ�İ汾��ֻ��ͨ������������ϵͳ�������������糬ʱ����1.5�У�������ʹ��HttpURLConnection�ĸ���URLConnection����������������
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			// ���ӱ�ͷ��ģ�����������ֹ����
			conn.setRequestProperty(
					"User-Agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727)");
			conn.setRequestProperty("Accept", "text/html");// ֻ����text/html���ͣ���ȻҲ���Խ���ͼƬ,pdf,*/*���⣬����tomcat/conf/web���涨����Щ
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
	 * ��ȡ���ı���Ϣ
	 * 
	 * @param inputHtml
	 * @return
	 */
	public static String extractText(String inputHtml) throws Exception {
		StringBuffer text = new StringBuffer();
		Parser parser = Parser.createParser(new String(inputHtml.getBytes(),
				"8859-1"), "8859-1");
//		parser.setEncoding("utf-8");
		// �������еĽڵ�
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
	 * ��ȡ���ı���Ϣ
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
		// �������еĽڵ�
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
	 * ��ȡ�ļ��ķ�ʽ����������. filePathҲ������һ��Url.
	 * 
	 * @param resource
	 *            �ļ�/Url
	 */
	public static void test5(String resource) throws Exception {
		Parser myParser = new Parser(resource);

		// ���ñ���
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