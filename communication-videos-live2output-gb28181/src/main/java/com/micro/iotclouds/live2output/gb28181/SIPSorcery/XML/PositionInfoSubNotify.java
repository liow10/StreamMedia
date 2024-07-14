package com.micro.iotclouds.live2output.gb28181.SIPSorcery.XML;

/** 
 语音广播通知
 <see cref="GB/T 28181-2016 附录A.2.5通知命令(d,e节点)"/>
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[XmlRoot("Notify")]
public class PositionInfoSubNotify extends XmlHelper<VoiceBroadcastNotify>
{
	private static VoiceBroadcastNotify _instance;

	/** 
	 单例模式
	 
	*/
	public static VoiceBroadcastNotify getInstance()
	{
		if(_instance == null)
		{
			_instance = new VoiceBroadcastNotify();
		}
		return _instance;
	}

	/** 
	 命令类型
	 
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
	 命令序列号
	 
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
	 语音输入设备的设备编码
	 
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
	 产生通知时间
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("Interval")]
	private String privateInterval;
	public final String getInterval()
	{
		return privateInterval;
	}
	public final void setInterval(String value)
	{
		privateInterval = value;
	}

}