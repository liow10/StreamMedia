package com.micro.iotclouds.live2output.gb28181.SIPSorcery.XML;

/** 
 强制关键帧命令
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[XmlRoot("Control")]
public class KeyFrameCmd extends XmlHelper<KeyFrameCmd>
{
	private static KeyFrameCmd _instance;
	/** 
	 单例模式访问
	 
	*/
	public static KeyFrameCmd getInstance()
	{
		if(_instance == null)
		{
			_instance = new KeyFrameCmd();
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
	 强制关键帧
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("IFrameCmd")]
	private String privateIFrameCmd;
	public final String getIFrameCmd()
	{
		return privateIFrameCmd;
	}
	public final void setIFrameCmd(String value)
	{
		privateIFrameCmd = value;
	}


	private String privateIFameCmd;
	public final String getIFameCmd()
	{
		return privateIFameCmd;
	}
	public final void setIFameCmd(String value)
	{
		privateIFameCmd = value;
	}
}