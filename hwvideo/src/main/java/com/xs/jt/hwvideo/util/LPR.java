package com.xs.jt.hwvideo.util;
// package com;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.ptr.ByteByReference;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.win32.StdCallLibrary;

public interface LPR extends StdCallLibrary {
	
	LPR INSTANCE = (LPR) Native.loadLibrary("VzLPRSDK", LPR.class);

	/**ͼ����Ϣ*/
	public static class VZ_LPRC_IMAGE_INFO_Pointer extends Structure
	{
		public int uWidth;			/**<���*/
		public int uHeight;		/**<�߶�*/
		public int uPitch;			/**<ͼ���ȵ�һ��������ռ�ڴ��ֽ���*/
		public int uPixFmt;		/**<ͼ�����ظ�ʽ���ο�ö�ٶ���ͼ���ʽ��ImageFormatXXX��*/
		public ByteByReference pBuffer;	/**<ͼ���ڴ���׵�ַ*/
		
	    public static class ByReference extends VZ_LPRC_IMAGE_INFO_Pointer implements Structure.ByReference {}  
	    public static class ByValue extends VZ_LPRC_IMAGE_INFO_Pointer implements Structure.ByValue {}  
	  
	    @Override  
	    protected List<String> getFieldOrder() {  
	        return Arrays.asList(new String[]{"uWidth", "uHeight", "uPitch", "uPixFmt","pBuffer"});  
	    }  
	}
	
	public static class VZ_TM extends Structure
	{
		public short nYear;	/**<��*/
		public short nMonth;	/**<��*/
		public short nMDay;	/**<��*/
		public short nHour;	/**<ʱ*/
		public short nMin;		/**<��*/
		public short nSec;		/**<��*/
		
	    public static class ByReference extends VZ_TM implements Structure.ByReference {}  
	    public static class ByValue extends VZ_TM implements Structure.ByValue {}  
	  
	    @Override  
	    protected List<String> getFieldOrder() {  
	        return Arrays.asList(new String[]{"nYear", "nMonth", "nMDay", "nHour", "nMin", "nSec"});  
	    }  
	}
	
	/*
	public static class VZ_TM_PERIOD_OR_RANGE extends Structure
	{
		public int			uEnable;
		public int			uIsSeg;
		public VZ_TM_WEEK_SEGMENT  struWeekSeg[8];
		public VZ_TM_RANGE			struRange;
	}
	*/
	
	
	/**�ڰ������еĳ�����¼*/
	public static class VZ_LPR_WLIST_VEHICLE extends Structure
	{
		public int	uVehicleID;
		public byte[] strPlateID = new byte[32];			/**<�����ַ���*/
		public int	uCustomerID;							/**<�ͻ������ݿ��ID����VZ_LPR_WLIST_CUSTOMER::uCustomerID��Ӧ*/
		public int	bEnable;								/**<�ü�¼��Ч���*/	
		public VZ_TM.ByReference		pStruTMOverdule;	/**<�ü�¼����ʱ��,Ϊ�ձ�ʾû�й���ʱ��*/
		public int	bUsingTimeSeg;							/**<�Ƿ�ʹ��ʱ���*/
		public byte[]	byTimeSegOrRange = new byte[256];   /**struTimeSegOrRange;			<ʱ�����Ϣ*/
		public int	bAlarm;									/**<�Ƿ񴥷���������������¼��*/
		public int	iColor;									/**<������ɫ*/
		public int	iPlateType;								/**<��������*/
		public byte[]	strCode = new byte[32];				/**<��������*/
		public byte[]	strComment = new byte[64];			/**<��������*/
		
	    public static class ByReference extends VZ_LPR_WLIST_VEHICLE implements Structure.ByReference {}  
	    public static class ByValue extends VZ_LPR_WLIST_VEHICLE implements Structure.ByValue {}  
	}
	
	public static class VZ_LPR_WLIST_ROW extends Structure
	{
		public Pointer pCustomer;			/**<�ͻ�*/
		public VZ_LPR_WLIST_VEHICLE.ByReference pVehicle;				/**<����������Ϊ��*/
		
	    public static class ByReference extends VZ_LPR_WLIST_ROW implements Structure.ByReference {}  
	    public static class ByValue extends VZ_LPR_WLIST_ROW implements Structure.ByValue {}  
	}
	
	/**��������ÿ�еĽ��*/
	public static class VZ_LPR_WLIST_IMPORT_RESULT extends Structure
	{
		public int ret;
		public int error_code;
		
	    public static class ByReference extends VZ_LPR_WLIST_IMPORT_RESULT implements Structure.ByReference {}  
	    public static class ByValue extends VZ_LPR_WLIST_IMPORT_RESULT implements Structure.ByValue {}  
	}
	
	/**ʶ����������*/
	public enum VZ_LPRC_RESULT_TYPE
	{
		VZ_LPRC_RESULT_REALTIME,		/**<ʵʱʶ����*/
		VZ_LPRC_RESULT_STABLE,			/**<�ȶ�ʶ����*/
		VZ_LPRC_RESULT_FORCE_TRIGGER,	/**<���á�VzLPRClient_ForceTrigger��������ʶ����*/
		VZ_LPRC_RESULT_IO_TRIGGER,		/**<�ⲿIO�źŴ�����ʶ����*/
		VZ_LPRC_RESULT_VLOOP_TRIGGER,	/**<������Ȧ������ʶ����*/
		VZ_LPRC_RESULT_MULTI_TRIGGER,	/**<��_FORCE_\_IO_\_VLOOP_�е�һ�ֻ����ͬʱ������������Ҫ����ÿ��ʶ������TH_PlateResult::uBitsTrigType���ж�*/
		VZ_LPRC_RESULT_TYPE_NUM			/**<����������*/
	}
	
	/**
	*  @brief ȫ�ֳ�ʼ��
	*  @return 0��ʾ�ɹ���-1��ʾʧ��
	*  @note �����нӿڵ���֮ǰ����
	*  @ingroup group_global
	*/
	int VzLPRClient_Setup();
	
	/**
	*  @brief ��һ���豸
	*  @param  [IN] pStrIP �豸��IP��ַ
	*  @param  [IN] wPort �豸�Ķ˿ں�
	*  @param  [IN] pStrUserName �����豸�����û���
	*  @param  [IN] pStrPassword �����豸��������
	*  @return �����豸�Ĳ������������ʧ��ʱ������0
	*  @ingroup group_device
	*/
	int VzLPRClient_Open(String pStrIP, int wPort, String pStrUserName, String pStrPassword);
	
	/**
	*  @brief �����������ͻ��ͳ�����¼
	*  @param  [IN] handle ��VzLPRClient_Open������õľ��
	*  @param  [IN] rowcount ��¼������
	*  @param  [IN] pRowDatas ��¼����������ĵ�ַ
	*  @param  [OUT] results ÿ�������Ƿ���ɹ�
	*  @return 0��ʾ�ɹ���-1��ʾʧ��
	*  @ingroup group_database
	*/
	int VzLPRClient_WhiteListImportRows(int handle, int rowcount, VZ_LPR_WLIST_ROW.ByReference pRowDatas, VZ_LPR_WLIST_IMPORT_RESULT.ByReference pResults);	
	
	/**
	*  @brief ����ʶ�����Ļص�����
	*  @param  [IN] handle ��VzLPRClient_Open������õľ��
	*  @param  [IN] func ʶ�����ص����������ΪNULL�����ʾ�رոûص������Ĺ���
	*  @param  [IN] pUserData �ص������е�������
	*  @param  [IN] bEnableImage ָ��ʶ�����Ļص��Ƿ���Ҫ������ͼ��Ϣ��1Ϊ��Ҫ��0Ϊ����Ҫ
	*  @return 0��ʾ�ɹ���-1��ʾʧ��
	*  @ingroup group_device
	*/
	int VzLPRClient_SetPlateInfoCallBack(int handle, VZLPRC_PLATE_INFO_CALLBACK func, Pointer pUserData, int bEnableImage);
	
	/**
	*  @brief ��ͼ�񱣴�ΪJPEG��ָ��·��
	*  @param  [IN] pImgInfo ͼ��ṹ�壬Ŀǰֻ֧��Ĭ�ϵĸ�ʽ����ImageFormatRGB
	*  @param  [IN] pFullPathName �������·����JPG��׺�����ļ����ַ���
	*  @param  [IN] nQuality JPEGѹ����������ȡֵ��Χ1~100��
	*  @return 0��ʾ�ɹ���-1��ʾʧ��
	*  @note   �������ļ����е�·����Ҫ����
	*  @ingroup group_global
	*/
	int VzLPRClient_ImageSaveToJpeg(VZ_LPRC_IMAGE_INFO_Pointer.ByReference pImgInfo, String pFullPathName, int nQuality);
	
	/** �������� */
	public static class TH_RECT_Pointer extends Structure {
		public int left;
		public int top;
		public int right;
		public int bottom;
		
	    public static class ByReference extends TH_RECT_Pointer implements Structure.ByReference {}  
	    public static class ByValue extends TH_RECT_Pointer implements Structure.ByValue {}  
	  
	    @Override  
	    protected List<String> getFieldOrder() {  
	        return Arrays.asList(new String[]{"left", "top", "right", "bottom"});  
	    }  
	}
	
	public static class VZ_TIMEVAL_Pointer extends Structure
	{
		public int uTVSec;
		public int uTVUSec;
		
	    public static class ByReference extends VZ_TIMEVAL_Pointer implements Structure.ByReference {}  
	    public static class ByValue extends VZ_TIMEVAL_Pointer implements Structure.ByValue {}  
	  
	    @Override  
	    protected List<String> getFieldOrder() {  
	        return Arrays.asList(new String[]{"uTVSec", "uTVUSec"});  
	    }  
	}
	
	/**�ֽ�ʱ��*/
	public static class VzBDTime_Pointer extends Structure
	{
	    public byte bdt_sec;    /**<�룬ȡֵ��Χ[0,59]*/
	    public byte bdt_min;    /**<�֣�ȡֵ��Χ[0,59]*/
	    public byte bdt_hour;   /**<ʱ��ȡֵ��Χ[0,23]*/
	    public byte bdt_mday;   /**<һ�����е����ڣ�ȡֵ��Χ[1,31]*/
	    public byte bdt_mon;    /**<�·ݣ�ȡֵ��Χ[1,12]*/
	    public byte[] res1 = new byte[3];    /**<Ԥ��*/
	    public int bdt_year;   /**<���*/
	    public byte[] res2 = new byte[4];    /**<Ԥ��*/
	    
	    public static class ByReference extends VzBDTime_Pointer implements Structure.ByReference {}  
	    public static class ByValue extends VzBDTime_Pointer implements Structure.ByValue {}  
	  
	    @Override  
	    protected List<String> getFieldOrder() {  
	        return Arrays.asList(new String[]{"bdt_sec", "bdt_min","bdt_hour","bdt_mday","bdt_mon","res1","bdt_year","res2"});  
	    } 
	}
	   //broken-down time
	
	/** ʶ�����ṹ�� */
	public static class TH_PlateResult_Pointer extends Structure {
		public byte[] license = new byte[16]; // ���ƺ���
		public byte[] color = new byte[8]; // ������ɫ
		public int nColor; // ������ɫ��
		public int nType; // ��������
		public int nConfidence; // ���ƿ��Ŷ�
		public int nBright; // ��������
		public int nDirection; // �˶�����0 unknown, 1 left, 2 right, 3 up , 4 down
		public TH_RECT_Pointer.ByValue rcLocation; // ����λ��  error
		public int nTime; // ʶ������ʱ��
		public VZ_TIMEVAL_Pointer.ByValue tvPTS;
		public int uBitsTrigType;
		public byte nCarBright; // ��������
		public byte nCarColor; // ������ɫ
		public byte[] reserved0 = new byte[2];
		public int uId;
		public VzBDTime_Pointer.ByValue struBDTime;
		public byte[] reserved = new byte[68]; // ������
		
	    public static class ByReference extends TH_PlateResult_Pointer implements Structure.ByReference {}  
	    public static class ByValue extends TH_PlateResult_Pointer implements Structure.ByValue {}  
	  
	    @Override  
	    protected List<String> getFieldOrder() {  
	        return Arrays.asList(new String[]{"license", "color", "nColor", "nType", "nConfidence", "nBright", "nDirection", "rcLocation", "nTime", "nCarBright", "nCarColor", "reserved"});  
	    }  
	}
	
	
	/**
	*  @brief ͨ���ûص�������ó���ʶ����Ϣ
	*  @param  [IN] handle			��VzLPRClient_Open������õľ��
	*  @param  [IN] pUserData		�ص�������������
	*  @param  [IN] pResult			������Ϣ�����׵�ַ������ṹ�嶨�� TH_PlateResult
	*  @param  [IN] uNumPlates		���������еĳ��Ƹ���
	*  @param  [IN] eResultType		����ʶ�������ͣ����ö�����Ͷ���VZ_LPRC_RESULT_TYPE
	*  @param  [IN] pImgFull		��ǰ֡��ͼ�����ݣ�����ṹ�嶨��VZ_LPRC_IMAGE_INFO
	*  @param  [IN] pImgPlateClip	��ǰ֡�г��������ͼ���������飬���е�Ԫ���복����Ϣ�����еĶ�Ӧ
	*  @return 0��ʾ�ɹ���-1��ʾʧ��
	*  @note   �����Ҫ�ûص��������ؽ�ͼ���� pImgFull ��pImgPlateClip����Ҫ�����ûص�������VzLPRClient_SetPlateInfoCallBack��ʱ�����ͼ���ݣ�����ûص��������ص�������ָ��Ϊ�գ�
	*  @note   ʵʱ�����VZ_LPRC_RESULT_REALTIME���Ļص��ǲ�����ͼ���ݵ�
	*  @ingroup group_callback
	*/
	public static interface VZLPRC_PLATE_INFO_CALLBACK extends StdCallCallback{
	    public void invoke(int handle,Pointer pUserData,LPR.TH_PlateResult_Pointer.ByReference pResult,int uNumPlates,
	    		int eResultType,LPR.VZ_LPRC_IMAGE_INFO_Pointer.ByReference pImgFull,LPR.VZ_LPRC_IMAGE_INFO_Pointer.ByReference pImgPlateClip);
	}
}
