package com.szhua.myparser;

import java.util.List;

import com.szhua.pojo.TextContextPOJO;
import com.szhua.util.ConnectUtil;

/**
 * 
 * 
 * */
public interface ContextParser {
	
	ConnectUtil connectUtil = null;
	/**
	 * 抽取纯文本信息
	 * 
	 * @param inputHtml
	 * @return
	 */
	List<TextContextPOJO> getTextList(String inputHtml);

	
}
