package com.szhua.myparser;

import java.util.List;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;

import com.szhua.pojo.TextContextPOJO;
import com.szhua.util.ConnectUtil;

/**
 * 
 * 
 * */
public class ContextParser {

	/**
	 * 抽取纯文本信息
	 * 
	 * @param inputHtml
	 * @return
	 */
	public static String extractText(String inputHtml) throws Exception {
		StringBuffer text = new StringBuffer();
		Parser parser = Parser.createParser(inputHtml, "8859-1");
		// parser.setEncoding("utf-8");
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
	private static List<TextContextPOJO> getTextList(String inputHtml) {
		
		return null;
	}

	public static void main(String[] args) {
		String weburl = "http://www.hao123.com";
//		List<TextContextPOJO> texts = getTextList();
//
		ConnectUtil.fetchPageContext(weburl);

	}

}
