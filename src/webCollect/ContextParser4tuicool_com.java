package webCollect;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Remark;
import org.htmlparser.Tag;
import org.htmlparser.Text;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;

/**
 * http://www.tuicool.com/ah/
 * 
 * @author Hua
 * 
 */
public class ContextParser4tuicool_com extends ContextParser {
	static List<TextContextPOJO> list = new ArrayList<TextContextPOJO>();

	/**
	 * 抽取纯文本信息
	 * 
	 * @param inputHtml
	 * @return
	 */
	private static List<TextContextPOJO> getTextList(String inputHtml) {
		if (inputHtml.length() > 0) {
			Parser parser = Parser.createParser(inputHtml, "utf-8");
			NodeFilter []nfFilters = { new TagNameFilter("div"),new HasAttributeFilter("class", "single_fake") };
			
			AndFilter filter = new AndFilter(nfFilters);
			
			try {
				NodeList nodes = parser.parse(filter);
				for (int i = 0; i < nodes.size(); i++) {
					TextContextPOJO text = new TextContextPOJO();
					Node node = nodes.elementAt(i);
					Parser parser0 = new Parser(node.toHtml());
					
					NodeList ns = parser0.parse(new HasAttributeFilter("class", "article_title"));
					Node n = ns.elementAt(0).getChildren().elementAt(0);//error effect
					if (n instanceof LinkTag) {
						LinkTag linkTag = (LinkTag) n;
						text.setTitleUrl(linkTag.getLink());
						text.setTitle(linkTag.getText());
					}
					
					ns = parser0.parse(new HasAttributeFilter("class", "article_cut"));
					text.setContext(ns.elementAt(0).getText());
					
					ns = parser0.parse(new HasAttributeFilter("class", "article_thumb"));
					n = ns.elementAt(0).getChildren().elementAt(0);
					if (n instanceof ImageTag) {
						ImageTag img = (ImageTag) n;
						text.setImgUrl(img.getImageURL());
//						String imgLocUrl = saveImg2Ftp(img.getImageURL());
//						text.setImgLocUrl(imgLocUrl);
					}
					
					text.setGettedDt(new Date());
					list.add(text);
				}
			} catch (ParserException e) {
				e.printStackTrace();
			}
			
			return list;
		}
		return null;
	}

	
	public static void main(String[] args) {
		String weburl = "http://www.tuicool.com/ah/";// tuicool文章
		
		//List<TextContextPOJO> texts = getTextList(ConnectUtil.fetchPageContext(weburl));
		List<TextContextPOJO> texts = getTextList(ConnectUtil.fromFile("G:\\html.html"));
		for (TextContextPOJO text : texts) {
			Jdbc4Text.saveTextContext(text);
		}
		Jdbc4Text.saveTextContext(new TextContextPOJO());
	}

}
