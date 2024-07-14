package com.micro.iotclouds.live2output.gb28181.SIPSorcery.XML;

/** 
 报警通知
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[XmlRoot("Response")]
public class AlarmInfo extends XmlHelper<AlarmInfo>
{
	private static AlarmInfo _instance;

	/** 
	 以单例模式访问
	 
	*/
	public static AlarmInfo getInstance()
	{
		if(_instance == null)
		{
			_instance = new AlarmInfo();
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
	 执行结果
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("Result")]
	private String privateResult;
	public final String getResult()
	{
		return privateResult;
	}
	public final void setResult(String value)
	{
		privateResult = value;
	}


}