package com.szhua.webCollect;

import java.util.ArrayList;
import java.util.List;

import com.szhua.myparser.ContextParser;
import com.szhua.myparser.ContextParser4tuicool_com;

public class TaskManager {

	private List<ContextParser> contextParsers = new ArrayList<ContextParser>();
	
	public TaskManager() {
		this.contextParsers.add(new ContextParser4tuicool_com());
	}



	public void doWork() {
		for(ContextParser contextParser:contextParsers){
			contextParser.
		}
	}

}
