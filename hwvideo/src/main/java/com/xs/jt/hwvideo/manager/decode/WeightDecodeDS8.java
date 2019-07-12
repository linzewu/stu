package com.xs.jt.hwvideo.manager.decode;

import java.util.ArrayList;
import java.util.List;

import com.xs.jt.hwvideo.manager.WeightDecodeAbstract;
import com.xs.jt.hwvideo.util.CharUtil;

public class WeightDecodeDS8 extends WeightDecodeAbstract {

	private List<Byte> temp = new ArrayList<Byte>();

	@Override
	public Float decode(byte[] data) {
		
		for(byte b:data) {
			
			if(temp.size()>=12) {
				String t = CharUtil.byte2HexOfString(new byte[] {temp.get(2),temp.get(3),temp.get(4),temp.get(5),temp.get(6),temp.get(7)});
				System.out.println(t);
				temp.clear();
			}
			
			temp.add(b);
		}

		// System.out.println(new String(new
		// byte[]{data[2],data[3],data[4],data[5],data[6],data[7],data[8],data[9]}));

		return 0f;
	}

}
