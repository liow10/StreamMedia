package GB28181.Sys.XML;

/** 
 设备目录类型
 
*/
public enum DevCataType
{
	/** 
	 未知的
	 
	*/
	UnKnown,
	/** 
	 平台根
	 
	*/
	Root,
	/** 
	 省级行政区划
	 
	*/
	ProviceCata,
	/** 
	 市级行政区划
	 
	*/
	CityCata,
	/** 
	 区县级行政区划
	 
	*/
	AreaCata,
	/** 
	 基层接入单位行政区划
	 
	*/
	BasicUnit,
	/** 
	 系统目录项
	 
	*/
	SystemCata,
	/** 
	 业务分组目录项
	 
	*/
	BusinessGroupCata,
	/** 
	 虚拟目录分组项
	 
	*/
	VirtualGroupCata,
	/** 
	 设备
	 
	*/
	Device,
	/** 
	 其他
	 
	*/
	Other;

	public int getValue()
	{
		return this.ordinal();
	}

	public static DevCataType forValue(int value)
	{
		return values()[value];
	}
}