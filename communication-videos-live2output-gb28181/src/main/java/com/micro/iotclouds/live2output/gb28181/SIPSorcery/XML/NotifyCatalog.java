package com.micro.iotclouds.live2output.gb28181.SIPSorcery.XML;

/** 
 设备目录订阅通知结果信息
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[XmlRoot("Notify")]
public class NotifyCatalog extends XmlHelper<NotifyCatalog>
{
	private static NotifyCatalog _instance;

	/** 
	 单例模式访问
	 
	*/
	public static NotifyCatalog getInstance()
	{
		if(_instance == null)
		{
			_instance = new NotifyCatalog();
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
	 设备总条数
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("SumNum")]
	private int privateSumNum;
	public final int getSumNum()
	{
		return privateSumNum;
	}
	public final void setSumNum(int value)
	{
		privateSumNum = value;
	}

	/** 
	 列表显示条数
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("DeviceList")]
	private DevList privateDeviceList;
	public final DevList getDeviceList()
	{
		return privateDeviceList;
	}
	public final void setDeviceList(DevList value)
	{
		privateDeviceList = value;
	}

	/** 
	 设备列表
	 
	*/
	public static class DevList
	{
		private java.util.ArrayList<Item> _devItem = new java.util.ArrayList<Item>();

		/** 
		 设备项
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("Item")]
		public final java.util.ArrayList<Item> getItems()
		{
			return _devItem;
		}
	}

	/** 
	 设备信息
	 
	*/
	public static class Item
	{
		/** 
		 设备/区域/系统编码(必选)
		 
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
		 事件类型
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("Event")]
		private EventType privateEvent = EventType.forValue(0);
		public final EventType getEvent()
		{
			return privateEvent;
		}
		public final void setEvent(EventType value)
		{
			privateEvent = value;
		}

		/** 
		 设备/区域/系统名称(必选)
		 
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

		/** 
		 当为设备时，设备厂商(必选)
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("Manufacturer")]
		private String privateManufacturer;
		public final String getManufacturer()
		{
			return privateManufacturer;
		}
		public final void setManufacturer(String value)
		{
			privateManufacturer = value;
		}

		/** 
		 当为设备时，设备型号(必选)
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("Model")]
		private String privateModel;
		public final String getModel()
		{
			return privateModel;
		}
		public final void setModel(String value)
		{
			privateModel = value;
		}

		/** 
		 当为设备时，设备归属(必选)
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("Owner")]
		private String privateOwner;
		public final String getOwner()
		{
			return privateOwner;
		}
		public final void setOwner(String value)
		{
			privateOwner = value;
		}

		/** 
		 行政区域(必选)
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("CivilCode")]
		private String privateCivilCode;
		public final String getCivilCode()
		{
			return privateCivilCode;
		}
		public final void setCivilCode(String value)
		{
			privateCivilCode = value;
		}

		/** 
		 警区(可选)
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("Block")]
		private String privateBlock;
		public final String getBlock()
		{
			return privateBlock;
		}
		public final void setBlock(String value)
		{
			privateBlock = value;
		}

		/** 
		 当为设备时，安装地址(必选)
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("Address")]
		private String privateAddress;
		public final String getAddress()
		{
			return privateAddress;
		}
		public final void setAddress(String value)
		{
			privateAddress = value;
		}

		/** 
		 当为设备时，是否有子设备(必选)，
		 1有
		 0没有
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlIgnore]
		private Integer privateParental;
		public final Integer getParental()
		{
			return privateParental;
		}
		public final void setParental(Integer value)
		{
			privateParental = value;
		}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("Parental")]
		public final String getParentalValue()
		{
			return getParental() != null ? getParental().toString() : null;
		}
		public final void setParentalValue(String value)
		{
//C# TO JAVA CONVERTER TODO TASK: The following method call contained an unresolved 'out' keyword - these cannot be converted using the 'RefObject' helper class unless the method is within the code being modified:
			setParental(Integer.TryParse(value, out int result) ? result : (Integer)null);
		}

		/** 
		 父设备/区域/系统ID(必选)
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("ParentID")]
		private String privateParentID;
		public final String getParentID()
		{
			return privateParentID;
		}
		public final void setParentID(String value)
		{
			privateParentID = value;
		}

		/** 
		 虚拟分组ID
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("BusinessGroupID")]
		private String privateBusinessGroupID;
		public final String getBusinessGroupID()
		{
			return privateBusinessGroupID;
		}
		public final void setBusinessGroupID(String value)
		{
			privateBusinessGroupID = value;
		}

		/** 
		 信令安全模式(可选)缺省为0； 
		 0：不采用
		 2：S/MIME签名方式 
		 3：S/MIME加密签名同时采用方式 
		 4：数字摘要方式
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlIgnore]
		private Integer privateSafetyWay;
		public final Integer getSafetyWay()
		{
			return privateSafetyWay;
		}
		public final void setSafetyWay(Integer value)
		{
			privateSafetyWay = value;
		}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("SafetyWay")]
		public final String getSafetyWayValue()
		{
			return getSafetyWay() != null ? getSafetyWay().toString() : null;
		}
		public final void setSafetyWayValue(String value)
		{
//C# TO JAVA CONVERTER TODO TASK: The following method call contained an unresolved 'out' keyword - these cannot be converted using the 'RefObject' helper class unless the method is within the code being modified:
			setSafetyWay(Integer.TryParse(value, out int result) ? result : (Integer)null);
		}

		/** 
		 注册方式(必选)缺省为1；
		 1:符合IETF FRC 3261标准的认证注册模式；
		 2:基于口令的双向认证注册模式；
		 3:基于数字证书的双向认证注册模式；
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlIgnore]
		private Integer privateRegisterWay;
		public final Integer getRegisterWay()
		{
			return privateRegisterWay;
		}
		public final void setRegisterWay(Integer value)
		{
			privateRegisterWay = value;
		}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("RegisterWay")]
		public final String getRegisterWayValue()
		{
			return getRegisterWay() != null ? getRegisterWay().toString() : null;
		}
		public final void setRegisterWayValue(String value)
		{
//C# TO JAVA CONVERTER TODO TASK: The following method call contained an unresolved 'out' keyword - these cannot be converted using the 'RefObject' helper class unless the method is within the code being modified:
			setRegisterWay(Integer.TryParse(value, out int result) ? result : (Integer)null);
		}

		/** 
		 证书序列号（有证书的设备必选）
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("CertNum")]
		private String privateCertNum;
		public final String getCertNum()
		{
			return privateCertNum;
		}
		public final void setCertNum(String value)
		{
			privateCertNum = value;
		}

		/** 
		 证书有效标志(有证书的设备必选)，
		 0无效
		 1有效
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlIgnore]
		private Integer privateCertifiable;
		public final Integer getCertifiable()
		{
			return privateCertifiable;
		}
		public final void setCertifiable(Integer value)
		{
			privateCertifiable = value;
		}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("Certifiable")]
		public final String getCertifiableValue()
		{
			return getCertifiable() != null ? getCertifiable().toString() : null;
		}
		public final void setCertifiableValue(String value)
		{
//C# TO JAVA CONVERTER TODO TASK: The following method call contained an unresolved 'out' keyword - these cannot be converted using the 'RefObject' helper class unless the method is within the code being modified:
			setCertifiable(Integer.TryParse(value, out int result) ? result : (Integer)null);
		}

		/** 
		 证书无效原因码(可选)
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlIgnore]
		private Integer privateErrCode;
		public final Integer getErrCode()
		{
			return privateErrCode;
		}
		public final void setErrCode(Integer value)
		{
			privateErrCode = value;
		}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("ErrCode")]
		public final String getErrCodeValue()
		{
			return getErrCode() != null ? getErrCode().toString() : null;
		}
		public final void setErrCodeValue(String value)
		{
//C# TO JAVA CONVERTER TODO TASK: The following method call contained an unresolved 'out' keyword - these cannot be converted using the 'RefObject' helper class unless the method is within the code being modified:
			setErrCode(Integer.TryParse(value, out int result) ? result : (Integer)null);
		}

		/** 
		 证书终止有效期(可选)
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("EndTime")]
		private String privateEndTime;
		public final String getEndTime()
		{
			return privateEndTime;
		}
		public final void setEndTime(String value)
		{
			privateEndTime = value;
		}

		/** 
		 保密属性(必选)
		 0：不涉密
		 1涉密
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlIgnore]
		private Integer privateSecrecy;
		public final Integer getSecrecy()
		{
			return privateSecrecy;
		}
		public final void setSecrecy(Integer value)
		{
			privateSecrecy = value;
		}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("Secrecy")]
		public final String getSecrecyValue()
		{
			return getSecrecy() != null ? getSecrecy().toString() : null;
		}
		public final void setSecrecyValue(String value)
		{
//C# TO JAVA CONVERTER TODO TASK: The following method call contained an unresolved 'out' keyword - these cannot be converted using the 'RefObject' helper class unless the method is within the code being modified:
			setSecrecy(Integer.TryParse(value, out int result) ? result : (Integer)null);
		}

		/** 
		 设备/区域/系统IP地址（可选）
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("IPAddress")]
		private String privateIPAddress;
		public final String getIPAddress()
		{
			return privateIPAddress;
		}
		public final void setIPAddress(String value)
		{
			privateIPAddress = value;
		}

		/** 
		 设备/区域/系统端口(可选)
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlIgnore]
		private Short privatePort;
		public final Short getPort()
		{
			return privatePort;
		}
		public final void setPort(Short value)
		{
			privatePort = value;
		}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("Port")]
		public final String getPortValue()
		{
			return getPort() != null ? getPort().toString() : null;
		}
		public final void setPortValue(String value)
		{
//C# TO JAVA CONVERTER TODO TASK: The following method call contained an unresolved 'out' keyword - these cannot be converted using the 'RefObject' helper class unless the method is within the code being modified:
			setPort(Short.TryParse(value, out short result) ? result : (Short)null);
		}

		/** 
		 设备口令（可选）
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("Password")]
		private String privatePassword;
		public final String getPassword()
		{
			return privatePassword;
		}
		public final void setPassword(String value)
		{
			privatePassword = value;
		}

		/** 
		 设备状态(必选)
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("Status")]
		private DevStatus privateStatus = DevStatus.forValue(0);
		public final DevStatus getStatus()
		{
			return privateStatus;
		}
		public final void setStatus(DevStatus value)
		{
			privateStatus = value;
		}

		/** 
		 经度(可选)
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlIgnore]
		private Double privateLongitude;
		public final Double getLongitude()
		{
			return privateLongitude;
		}
		public final void setLongitude(Double value)
		{
			privateLongitude = value;
		}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("Longitude")]
		 String getLongitudeValue()
		 void setLongitudeValue(String value)

		/** 
		 纬度(可选)
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlIgnore]
		private Double privateLatitude;
		public final Double getLatitude()
		{
			return privateLatitude;
		}
		public final void setLatitude(Double value)
		{
			privateLatitude = value;
		}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("Latitude")]
		public final String getLatitudeValue()
		{
			return getLatitude() != null ? getLatitude().toString() : null;
		}
		public final void setLatitudeValue(String value)
		{
//C# TO JAVA CONVERTER TODO TASK: The following method call contained an unresolved 'out' keyword - these cannot be converted using the 'RefObject' helper class unless the method is within the code being modified:
			setLatitude(Double.TryParse(value, out double result) ? result : (Double)null);
		}

		/** 
		 信息项
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("Info")]
		private Info privateInfList;
		public final Info getInfList()
		{
			return privateInfList;
		}
		public final void setInfList(Info value)
		{
			privateInfList = value;
		}

		/** 
		 远程设备终结点
		 
		*/
		private String privateRemoteEP;
		public final String getRemoteEP()
		{
			return privateRemoteEP;
		}
		public final void setRemoteEP(String value)
		{
			privateRemoteEP = value;
		}
	}

	/** 
	 扩展信息
	 
	*/
	public static class Info
	{
		/** 
		 摄像机类型扩展，标识摄像机类型
		 1，球机
		 2，半球
		 3，固定枪机
		 4，遥控枪机
		 当目录项为摄像机时可选
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlIgnore]
		private Integer privatePTZType;
		public final Integer getPTZType()
		{
			return privatePTZType;
		}
		public final void setPTZType(Integer value)
		{
			privatePTZType = value;
		}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("PTZType")]
		public final String getPTZTypeValue()
		{
			return getPTZType() != null ? getPTZType().toString() : null;
		}
		public final void setPTZTypeValue(String value)
		{
//C# TO JAVA CONVERTER TODO TASK: The following method call contained an unresolved 'out' keyword - these cannot be converted using the 'RefObject' helper class unless the method is within the code being modified:
			setPTZType(Integer.TryParse(value, out int result) ? result : (Integer)null);
		}

		/** 
		 摄像机位置类型扩展
		 1，省际检查站
		 2，党政机关
		 3，车站码头
		 4，中心广场
		 5，体育场馆
		 6，商业中心
		 7，宗教场所
		 8，校园周边
		 9，治安复杂区域
		 10，交通干线
		 当目录项为摄像机时可选
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlIgnore]
		private Integer privatePositionType;
		public final Integer getPositionType()
		{
			return privatePositionType;
		}
		public final void setPositionType(Integer value)
		{
			privatePositionType = value;
		}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("PositionType")]
		 String getPositionTypeValue()
		 void setPositionTypeValue(String value)


		/** 
		 摄像机按照位置室外、室内属性
		 1，室外
		 2，室内
		 当目录项为摄像机时可选，缺省为1
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlIgnore]
		private Integer privateRoomType;
		public final Integer getRoomType()
		{
			return privateRoomType;
		}
		public final void setRoomType(Integer value)
		{
			privateRoomType = value;
		}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("RoomType")]
		public final String getRoomTypeValue()
		{
			return getRoomType() != null ? getRoomType().toString() : null;
		}
		public final void setRoomTypeValue(String value)
		{
//C# TO JAVA CONVERTER TODO TASK: The following method call contained an unresolved 'out' keyword - these cannot be converted using the 'RefObject' helper class unless the method is within the code being modified:
			setRoomType(Integer.TryParse(value, out int result) ? result : (Integer)null);
		}

		/** 
		 摄像机用途属性
		 1，治安
		 2，交通
		 3，重点
		 当目录项为摄像机时可选
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlIgnore]
		private Integer privateUseType;
		public final Integer getUseType()
		{
			return privateUseType;
		}
		public final void setUseType(Integer value)
		{
			privateUseType = value;
		}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("UseType")]
		public final String getUseTypeValue()
		{
			return getUseType() != null ? getUseType().toString() : null;
		}
		public final void setUseTypeValue(String value)
		{
//C# TO JAVA CONVERTER TODO TASK: The following method call contained an unresolved 'out' keyword - these cannot be converted using the 'RefObject' helper class unless the method is within the code being modified:
			setUseType(Integer.TryParse(value, out int result) ? result : (Integer)null);
		}

		/** 
		 摄像机补光属性
		 1，无补光
		 2，红外补光
		 3，白光补光
		 当目录项为摄像机时可选，缺省为1
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlIgnore]
		private Integer privateSupplyLightType;
		public final Integer getSupplyLightType()
		{
			return privateSupplyLightType;
		}
		public final void setSupplyLightType(Integer value)
		{
			privateSupplyLightType = value;
		}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("SupplyLightType")]
		 String getSupplyLightTypeValue()
		 void setSupplyLightTypeValue(String value)

		/** 
		 摄像机监视方位属性
		 1，东
		 2，西
		 3，南
		 4，北
		 5，东南
		 6，东北
		 7，西南
		 8，西北
		 当目录项为摄像机时且为固定摄像机或设置看守位摄像机时可选
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlIgnore]
		private Integer privateDirectionType;
		public final Integer getDirectionType()
		{
			return privateDirectionType;
		}
		public final void setDirectionType(Integer value)
		{
			privateDirectionType = value;
		}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("DirectionType")]
		 String getDirectionTypeValue()
		 void setDirectionTypeValue(String value)

		/** 
		 摄像机支持的分辨率，可有多个分辨率值，各个取值间以"/"分隔。
		 分辨率取值参见附录F中SDP f字段规定。
		 当目录项为摄像机时可选
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("Resolution")]
		private String privateResolution;
		public final String getResolution()
		{
			return privateResolution;
		}
		public final void setResolution(String value)
		{
			privateResolution = value;
		}

		/** 
		 虚拟组织所属的业务分组ID，
		 业务分组根据特定的业务需求制定，
		 一个业务分组包含一组特定的虚拟组织。
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("BusinessGroupID")]
		private String privateBusinessGroupID;
		public final String getBusinessGroupID()
		{
			return privateBusinessGroupID;
		}
		public final void setBusinessGroupID(String value)
		{
			privateBusinessGroupID = value;
		}

		/** 
		 下载倍速范围(可选)，各可选参数以"/"分隔
		 如设备支持1,2,4倍下载则应写为"1/2/4"
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("DownloadSpeed")]
		private String privateDownloadSpeed;
		public final String getDownloadSpeed()
		{
			return privateDownloadSpeed;
		}
		public final void setDownloadSpeed(String value)
		{
			privateDownloadSpeed = value;
		}

		/** 
		 空域编码能力
		 0，不支持
		 1，1级增强
		 2，2级增强
		 3，3级增强
		 (可选)
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlIgnore]
		private Integer privateSVCSpaceSupportMode;
		public final Integer getSVCSpaceSupportMode()
		{
			return privateSVCSpaceSupportMode;
		}
		public final void setSVCSpaceSupportMode(Integer value)
		{
			privateSVCSpaceSupportMode = value;
		}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("SVCSpaceSupportMode")]
		 String getSVCSpaceSupportModeValue()
		 void setSVCSpaceSupportModeValue(String value)

		/** 
		 时域编码能力
		 0，不支持
		 1，1级增强
		 2，2级增强
		 3，3级增强
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlIgnore]
		private Integer privateSVCTimeSupportMode;
		public final Integer getSVCTimeSupportMode()
		{
			return privateSVCTimeSupportMode;
		}
		public final void setSVCTimeSupportMode(Integer value)
		{
			privateSVCTimeSupportMode = value;
		}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("SVCTimeSupportMode")]
		 String getSVCTimeSupportModeValue()
		 void setSVCTimeSupportModeValue(String value)
	}
}