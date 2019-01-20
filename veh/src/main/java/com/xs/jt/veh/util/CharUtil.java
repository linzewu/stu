package com.xs.jt.veh.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharUtil {

	public static String toHexString(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch);
			str = str + s4;
		}
		return str;// 0x表示十六进制
	}

	// 转换十六进制编码为字符串
	public static String toStringHex(String s) {
		if ("0x".equals(s.substring(0, 2))) {
			s = s.substring(2);
		}
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			s = new String(baKeyword, "utf-8");// UTF-16le:Not
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}

	public static byte[] hexStringToByte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	private static byte toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}

	// byte 与 int 的相互转换
	public static byte intToByte(int x) {
		return (byte) x;
	}

	public static int byteToInt(byte b) {
		// Java 总是把 byte 当做有符处理；我们可以通过将其和 0xFF 进行二进制与得到它的无符值
		return b & 0xFF;
	}

	private static String toHexUtil(int n) {
		String rt = "";
		switch (n) {
		case 10:
			rt += "A";
			break;
		case 11:
			rt += "B";
			break;
		case 12:
			rt += "C";
			break;
		case 13:
			rt += "D";
			break;
		case 14:
			rt += "E";
			break;
		case 15:
			rt += "F";
			break;
		default:
			rt += n;
		}
		return rt;
	}

	public static String toHex(int n) {
		StringBuilder sb = new StringBuilder();
		if (n / 16 == 0) {
			return toHexUtil(n);
		} else {
			String t = toHex(n / 16);
			int nn = n % 16;
			sb.append(t).append(toHexUtil(nn));
		}
		return sb.toString();
	}

	public static String parseAscii(String str) {
		StringBuilder sb = new StringBuilder();
		byte[] bs = str.getBytes();
		for (int i = 0; i < bs.length; i++)
			sb.append(toHex(bs[i]));
		return sb.toString();
	}

	/*public static void main(String args[]) {
		
		 * System.out.println(CharUtil.byte2HexOfString(new byte[] { 0x02, 0x32,
		 * 0x54, 0x4D, 0x2B }));
		 * System.out.println(CharUtil.byte2HexOfString(CharUtil.sumCheck(new
		 * byte[]{0xA,0x04,0xA},4)) );
		 
		byte[] s = new byte[] { 0x30 };
		
		System.out.println((char)s[0]);
		
		
		byte[] b = CharUtil.hexStringToByte("FF0007EE");
		
		System.out.println(CharUtil.getCheckSum("410452"));
	}*/

	public static String byte2HexOfString(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	public static String byte2HexOfString(Byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * @函数功能: BCD码转为10进制串(阿拉伯数据)
	 * @输入参数: BCD码
	 * @输出结果: 10进制串
	 */
	public static String bcd2Str(byte[] bytes) {
		StringBuffer temp = new StringBuffer(bytes.length * 2);

		for (int i = 0; i < bytes.length; i++) {
			temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
			temp.append((byte) (bytes[i] & 0x0f));
		}
		return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp.toString().substring(1) : temp.toString();
	}

	/**
	 * @函数功能: 10进制串转为BCD码
	 * @输入参数: 10进制串
	 * @输出结果: BCD码
	 */
	public static byte[] str2Bcd(String asc) {
		int len = asc.length();
		int mod = len % 2;

		if (mod != 0) {
			asc = "0" + asc;
			len = asc.length();
		}

		byte abt[] = new byte[len];
		if (len >= 2) {
			len = len / 2;
		}

		byte bbt[] = new byte[len];
		abt = asc.getBytes();
		int j, k;

		for (int p = 0; p < asc.length() / 2; p++) {
			if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
				j = abt[2 * p] - '0';
			} else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
				j = abt[2 * p] - 'a' + 0x0a;
			} else {
				j = abt[2 * p] - 'A' + 0x0a;
			}

			if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
				k = abt[2 * p + 1] - '0';
			} else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
				k = abt[2 * p + 1] - 'a' + 0x0a;
			} else {
				k = abt[2 * p + 1] - 'A' + 0x0a;
			}

			int a = (j << 4) + k;
			byte b = (byte) a;
			bbt[p] = b;
		}
		return bbt;
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public static byte[] sumCheck(byte[] msg, int length) {
		long mSum = 0;
		byte[] mByte = new byte[length];

		/** 逐Byte添加位数和 */
		for (byte byteMsg : msg) {
			long mNum = ((long) byteMsg >= 0) ? (long) byteMsg : ((long) byteMsg + 256);
			mSum += mNum;
		} /** end of for (byte byteMsg : msg) */

		/** 位数和转化为Byte数组 */
		for (int liv_Count = 0; liv_Count < length; liv_Count++) {
			mByte[length - liv_Count - 1] = (byte) (mSum >> (liv_Count * 8) & 0xff);
		} /** end of for (int liv_Count = 0; liv_Count < length; liv_Count++) */

		return mByte;
	}

	
	public static String getCheckSum(String hex) {

		byte[] vs = CharUtil.hexStringToByte(hex);
		byte i = 0;
		for (byte v : vs) {
			i += v;
		}
		byte b = (byte) (~i + 1);
		return CharUtil.byte2HexOfString(new byte[]{b});

	}
	
	
	
}
