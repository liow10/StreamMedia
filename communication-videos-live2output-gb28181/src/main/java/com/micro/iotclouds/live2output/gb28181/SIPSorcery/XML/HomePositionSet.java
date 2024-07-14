package com.micro.iotclouds.live2output.gb28181.SIPSorcery.XML;

public class HomePositionSet
{
	/** 
	 看守位 1：开启 0：关闭
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("Enabled")]
	private int privateEnabled;
	public final int getEnabled()
	{
		return privateEnabled;
	}
	public final void setEnabled(int value)
	{
		privateEnabled = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("ResetTime")]
	private int privateResetTime;
	public final int getResetTime()
	{
		return privateResetTime;
	}
	public final void setResetTime(int value)
	{
		privateResetTime = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("PresetIndex")]
	private int privatePresetIndex;
	public final int getPresetIndex()
	{
		return privatePresetIndex;
	}
	public final void setPresetIndex(int value)
	{
		privatePresetIndex = value;
	}
}