package com.micro.iotclouds.live2output.gb28181.SIPSorcery.XML;

/** 
 系统设备配置类型
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[XmlRoot("Query")]
public class DeviceConfigType extends XmlHelper<DeviceConfigType>
{
	private static DeviceConfigType _instance;
	/** 
	 单例模式访问
	 
	*/
	public static DeviceConfigType getInstance()
	{
		if(_instance == null)
		{
			_instance = new DeviceConfigType();
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
	 配置类型参数
	 1，基本参数配置：BasicParam,
	 2，视频参数范围：VideoParamOpt
	 3，SVAC编码配置：SVACEncodeConfig
	 4，SVAC解码配置：SVACDecodeConfig
	 
	*/
	private String privateConfigType;
	public final String getConfigType()
	{
		return privateConfigType;
	}
	public final void setConfigType(String value)
	{
		privateConfigType = value;
	}
}