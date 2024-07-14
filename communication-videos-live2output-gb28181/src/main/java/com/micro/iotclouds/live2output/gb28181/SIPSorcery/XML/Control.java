package com.micro.iotclouds.live2output.gb28181.SIPSorcery.XML;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
 //[XmlRoot("Control")]
 public class Control extends XmlHelper<Control>
{
	 private static Control _instance;

	 /** 
	  单例模式访问
	  
	 */
	 public static Control getInstance()
	 {
		 if(_instance == null)
		 {
			 _instance = new Control();
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
	 //[XmlElement("GuardCmd")]
	 private String privateGuardCmd;
	 public final String getGuardCmd()
	 {
		 return privateGuardCmd;
	 }
	 public final void setGuardCmd(String value)
	 {
		 privateGuardCmd = value;
	 }

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	 //[XmlElement("TeleBoot")]
	 private String privateTeleBoot;
	 public final String getTeleBoot()
	 {
		 return privateTeleBoot;
	 }
	 public final void setTeleBoot(String value)
	 {
		 privateTeleBoot = value;
	 }

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	 //[XmlElement("AlarmCmd")]
	 private String privateAlarmCmd;
	 public final String getAlarmCmd()
	 {
		 return privateAlarmCmd;
	 }
	 public final void setAlarmCmd(String value)
	 {
		 privateAlarmCmd = value;
	 }
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("RecordCmd")]
	private String privateRecordCmd;
	public final String getRecordCmd()
	{
		return privateRecordCmd;
	}
	public final void setRecordCmd(String value)
	{
		privateRecordCmd = value;
	}

}