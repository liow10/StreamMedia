package com.micro.iotclouds.live2output.gb28181.SIPSorcery.XML;

/** 
 看守位控制命令
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[XmlRoot("Control")]
public class HomePositionCmd extends XmlHelper<HomePositionCmd>
{
	private static HomePositionCmd _instance;
	/** 
	 单例模式访问
	 
	*/
	public static HomePositionCmd getInstance()
	{
		if(_instance == null)
		{
			_instance = new HomePositionCmd();
		}
		return _instance;
	}

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
	 看守位
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("HomePosition")]
	private HomePositionSet privateHomePosition;
	public final HomePositionSet getHomePosition()
	{
		return privateHomePosition;
	}
	public final void setHomePosition(HomePositionSet value)
	{
		privateHomePosition = value;
	}


}