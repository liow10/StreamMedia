package com.micro.iotclouds.live2output.gb28181.SIPSorcery.XML;

/** 
 移动设备位置数据查询
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[XmlRoot("Query")]
public class MobilePositionQuery extends XmlHelper<MobilePositionQuery>
{
	private static MobilePositionQuery _instance;

	/** 
	 以单例模式访问
	 
	*/
	public static MobilePositionQuery getInstance()
	{
		if(_instance == null)
		{
			_instance = new MobilePositionQuery();
		}
		return _instance;
	}

	/** 
	 指令类型
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("CmdType")]
	private CommandType privateCmdType = CommandType.forValue(0);
	public final CommandType getCmdType()
	{
		return privateCmdType;
	}
	public final void setCmdType(CommandType value)
	{
		privateCmdType = value;
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
	 设备编码
	 
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
	 移动设备位置信息上报时间间隔
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("Interval")]
	private int privateInterval;
	public final int getInterval()
	{
		return privateInterval;
	}
	public final void setInterval(int value)
	{
		privateInterval = value;
	}


}