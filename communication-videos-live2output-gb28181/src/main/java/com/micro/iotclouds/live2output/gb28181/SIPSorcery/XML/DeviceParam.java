package com.micro.iotclouds.live2output.gb28181.SIPSorcery.XML;

/** 
 设备配置参数(基本)
 
*/
public class DeviceParam
{
	/** 
	
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("Name")]
	private String privateName;
	public final String getName()
	{
		return privateName;
	}
	public final void setName(String value)
	{
		privateName = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("Expiration")]
	private int privateExpiration;
	public final int getExpiration()
	{
		return privateExpiration;
	}
	public final void setExpiration(int value)
	{
		privateExpiration = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("HeartBeatInterval")]
	private int privateHeartBeatInterval;
	public final int getHeartBeatInterval()
	{
		return privateHeartBeatInterval;
	}
	public final void setHeartBeatInterval(int value)
	{
		privateHeartBeatInterval = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("HeartBeatCount")]
	private int privateHeartBeatCount;
	public final int getHeartBeatCount()
	{
		return privateHeartBeatCount;
	}
	public final void setHeartBeatCount(int value)
	{
		privateHeartBeatCount = value;
	}
}