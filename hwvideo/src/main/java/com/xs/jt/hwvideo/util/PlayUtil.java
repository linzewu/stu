package com.xs.jt.hwvideo.util;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

@Component
public class PlayUtil {
	
	@Async
	public void play(String message,Integer count) {
		ActiveXComponent sap = new ActiveXComponent("Sapi.SpVoice");
		// Dispatch是做什么的？
		Dispatch sapo = sap.getObject();
		try {
			// 音量 0-100
			sap.setProperty("Volume", new Variant(100));
			// 语音朗读速度 -10 到 +10
			sap.setProperty("Rate", new Variant(-3));
			// 执行朗读
			for(int i=0;i<count;i++) {
				Dispatch.call(sapo, "Speak", new Variant(message));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sapo.safeRelease();
			sap.safeRelease();
		}
	}

}
