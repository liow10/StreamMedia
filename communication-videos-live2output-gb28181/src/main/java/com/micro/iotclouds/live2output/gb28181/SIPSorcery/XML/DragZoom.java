package com.micro.iotclouds.live2output.gb28181.SIPSorcery.XML;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[XmlRoot("Control")]
public class DragZoom extends XmlHelper<Control>
{
	private static DragZoom _instance;
	/** 
	 单例模式访问
	 
	*/
	public static DragZoom getInstance()
	{
		if(_instance == null)
		{
			_instance = new DragZoom();
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

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("DragZoomIn")]
	private DragZoomSet privateDragZoomIn;
	public final DragZoomSet getDragZoomIn()
	{
		return privateDragZoomIn;
	}
	public final void setDragZoomIn(DragZoomSet value)
	{
		privateDragZoomIn = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("DragZoomOut")]
	private DragZoomSet privateDragZoomOut;
	public final DragZoomSet getDragZoomOut()
	{
		return privateDragZoomOut;
	}
	public final void setDragZoomOut(DragZoomSet value)
	{
		privateDragZoomOut = value;
	}

}