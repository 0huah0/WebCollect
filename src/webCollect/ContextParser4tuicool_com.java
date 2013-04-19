package webCollect;

import java.util.ArrayList;
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
			AndFilter filter = new AndFilter( // class="single_fake" 的div
					new NodeFilter[] { new TagNameFilter("div"),
							new HasAttributeFilter("class", "single_fake") });
			/*
			 * NodeList nodes = parser.extractAllNodesThatMatch(new NodeFilter()
			 * { public boolean accept(Node node) { if(node.get){ return true; }
			 * return false; } });
			 */

			try {
				NodeList nodes = parser.parse(filter);

	  			nodes.visitAllNodesWith(new NodeVisitor(true, false) {
					TextContextPOJO text = new TextContextPOJO();
					
					public void visitTag(Tag tag) {
						 message("This is Tag:"+tag.getText());
						if (tag instanceof Div) {
							Div div = ((Div) tag);
							if (div.getAttribute("class").equals(
									"article_title")) {
								if (div.getChild(0) instanceof LinkTag) {
									LinkTag linkTag = (LinkTag) div.getChild(0);
									text.setTitleUrl(linkTag
											.getAttribute("href"));
									text.setTitle(linkTag.getText());
								}
							} else if (div.getAttribute("class").equals(
									"article_cut")) {
								text.setContext(div.getText());
							}
						} else if (tag instanceof ImageTag) {
							ImageTag img = (ImageTag) tag;
							text.setImgUrl(img.getImageURL());
							text.setImgLocUrl("");
						}
						list.add(text);
					}

					public void visitStringNode(Text string) {
						 message("This is Text:"+string);
					}

					private void message(String string) {
						System.out.println(string);
					}

					public void visitRemarkNode(Remark remark) {
						 message("This is Remark:"+remark.getText());
					}

					public void beginParsing() {
						 message("beginParsing");
					}

					public void visitEndTag(Tag tag) {
						// message("visitEndTag:"+tag.getText());
					}

					public void finishedParsing() {
						// message("finishedParsing");
					}
				});
	  			return list;
			} catch (ParserException e) {
				e.printStackTrace();
				return null;
			}
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
