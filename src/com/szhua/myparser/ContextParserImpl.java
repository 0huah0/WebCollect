package com.szhua.myparser;

import java.util.List;

import com.szhua.pojo.TextContextPOJO;
import com.szhua.util.ConnectUtil;

/**
 * 
 * 
 * */
public abstract class ContextParserImpl implements ContextParser{
	
	ConnectUtil connectUtil = null;
	
	ContextParserImpl() {
		this.connectUtil = new ConnectUtil();
	}
	
	/**
	 * 抽取纯文本信息
	 * 
	 * @param inputHtml
	 * @return
	 */
	private abstract List<TextContextPOJO> getTextList(String inputHtml);
	
	doTast(){
		
	}

}
