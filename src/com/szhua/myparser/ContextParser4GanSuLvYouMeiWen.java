package com.szhua.myparser;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.szhua.pojo.TextContextPOJO;
import com.szhua.util.ConnectUtil;
import com.szhua.util.DateUtil;

/**
 * http://www.gsta.gov.cn/pub/lyzw/lvzx/gslydt/index.html
 * 
 * @author Hua 2013-4-21 下午11:08:23
 */
public class ContextParser4GanSuLvYouMeiWen extends ContextParser {

	public ContextParser4GanSuLvYouMeiWen() {
		//首页 » 旅游资讯 » 旅游美文 
		super("http://www.gsta.gov.cn/pub/lyzw/lvzx/lymw/index.html"); 
	}

	@Override
	public List<TextContextPOJO> getTextList(String inputHtml) {
		if (inputHtml.length() > 0) {
			Parser parser = Parser.createParser(inputHtml, "utf-8");
			CssSelectorNodeFilter filter = new CssSelectorNodeFilter ("td[class='font-108']"); 

			try {
				NodeList nodes = parser.parse(filter);
				for (int i = 0; i < nodes.size(); i++) {					
					TextContextPOJO text = new TextContextPOJO();
					
					Node trNode = nodes.elementAt(i).getParent();
					
					//标题&链接
					NodeList ns = trNode.getChildren().extractAllNodesThatMatch(new CssSelectorNodeFilter ("a"), true);
					Node n = ns.elementAt(0).getChildren().elementAt(1);
					if (n instanceof LinkTag) {
						LinkTag linkTag = (LinkTag) n;
						text.setTitleUrl(linkTag.getLink());
						text.setTitle(linkTag.getAttribute("title"));
					}

					//发布时间
					ns = trNode.getChildren().extractAllNodesThatMatch(new CssSelectorNodeFilter ("td[class='font-10']"), true);
					n = ns.elementAt(0).getFirstChild();
					text.setPublishDt(DateUtil.getDate(n.getText(), "yyyy-MM-dd"));

					//主内容或简要
					String str = ConnectUtil.fetchPageContext(text.getTitleUrl());
					if(str.length()<100){
						System.out.println("Download!-->"+str);
					}else{
						System.out.println("Download!-->"+str.length());
						Parser parser0 = Parser.createParser(str, "utf-8");
						CssSelectorNodeFilter filter0 = new CssSelectorNodeFilter ("div[id='wwkjArticleContent']"); 
						ns = parser0.extractAllNodesThatMatch(filter0);
						//内容html
						text.setContext(((Div)ns.elementAt(0).getFirstChild()).getChildrenHTML());
						
						/*<td height="30">
						 * <div align="center">来源:甘肃旅游政务网 | 添加时间:2013年04月16日 09:17:26 | 点击: 
						 * <script src="http://www.gsta.gov.cn/content/hitview.do?id=1366075046521" type="text/javascript" language="JavaScript">
						 * </script>16 次</div></td>*/
						//parser0.extractAllNodesThatMatch(new RegexFilter("<div align=\"center\">来源:.*|[^<]添加时间:.*"));
						
						Pattern pattern = Pattern.compile("来源:甘肃旅游政务网 \\| 添加时间:(.+)\\|\\s*点击:.*>(\\d+)");
						Matcher matcher = pattern.matcher(str);
						if (matcher.find()) {
							System.out.println(matcher.group());
							text.setPublishDt(DateUtil.getDate(matcher.group(1), "yyyy年MM月dd日 HH:mm:ss"));
							System.out.println(matcher.group(1));
							try{
								text.setVisitTimes(Integer.parseInt(matcher.group(2)));
							}catch (NumberFormatException e) {
								text.setVisitTimes(0);
							}
							System.out.println(matcher.group(2));
						}
					
						list.add(text);
					}
				}	
			} catch (ParserException e) {
				e.printStackTrace();
			}

			return list;
		}
		return null;
	}
}
