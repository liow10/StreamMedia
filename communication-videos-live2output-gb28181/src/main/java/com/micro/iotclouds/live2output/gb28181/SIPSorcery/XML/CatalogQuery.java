package com.micro.iotclouds.live2output.gb28181.SIPSorcery.XML;

/** 
 目录查询/订阅
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[XmlRoot("Query")]
public class CatalogQuery extends XmlHelper<CatalogQuery>
{
	private static CatalogQuery _instance;
	/** 
	 单例模式访问
	 
	*/
	public static CatalogQuery Instance => (_instance != null) ? _instance : (_instance = new CatalogQuery());

	/** 
	 命令类型
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("CmdType")]
	private CommandType privateCommandType = CommandType.forValue(0);
	public final CommandType getCommandType()
	{
		return privateCommandType;
	}
	public final void setCommandType(CommandType value)
	{
		privateCommandType = value;
	}

	/** 
	 序号
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("SN")]
	private int privateSN;
	public final int getSN()
	{
		return privateSN;
	}
	public final void setSN(int value)
	{
		privateSN = value;
	}

	/** 
	 系统编码/行政区划码/设备编码/业务分组编码/虚拟组织编码
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("DeviceID")]
	private String privateDeviceID;
	public final String getDeviceID()
	{
		return privateDeviceID;
	}
	public final void setDeviceID(String value)
	{
		privateDeviceID = value;
	}

	/** 
	 报警开始时间
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("StartAlarmPriority")]
	private String privateStartAlarmPriority;
	public final String getStartAlarmPriority()
	{
		return privateStartAlarmPriority;
	}
	public final void setStartAlarmPriority(String value)
	{
		privateStartAlarmPriority = value;
	}

	/** 
	 报警结束时间
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("EndAlarmPriority")]
	private String privateEndAlarmPriority;
	public final String getEndAlarmPriority()
	{
		return privateEndAlarmPriority;
	}
	public final void setEndAlarmPriority(String value)
	{
		privateEndAlarmPriority = value;
	}

	/** 
	 报警方法
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("AlarmMethod")]
	private String privateAlarmMethod;
	public final String getAlarmMethod()
	{
		return privateAlarmMethod;
	}
	public final void setAlarmMethod(String value)
	{
		privateAlarmMethod = value;
	}

	/** 
	 结束时间
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("StartTime")]
	private String privateStartTime;
	public final String getStartTime()
	{
		return privateStartTime;
	}
	public final void setStartTime(String value)
	{
		privateStartTime = value;
	}

	/** 
	 结束时间
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("EndTime")]
	private String privateEndTime;
	public final String getEndTime()
	{
		return privateEndTime;
	}
	public final void setEndTime(String value)
	{
		privateEndTime = value;
	}
}