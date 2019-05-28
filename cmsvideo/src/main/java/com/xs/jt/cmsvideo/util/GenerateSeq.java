package com.xs.jt.cmsvideo.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class GenerateSeq {
	protected static Log log = LogFactory.getLog(GenerateSeq.class);
	private static int seq = 0;
	
	public static synchronized String getSeq() throws InterruptedException {
		log.info("getSeq begin");
		String seqStr = "";
		if(seq >= 99) {
			seq = 0;
		}
		seq++;
		seqStr = (seq > 9 ? String.valueOf(seq) : "0"+seq);
		log.info("getSeq end "+seqStr);
		return seqStr;
	}


}
