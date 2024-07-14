package com.micro.iotclouds.live2output.gb28181.SIPSorcery.XML;

/** 
 导出数据到mServer
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[XmlRoot("NvrTable")]
public class NvrExport extends XmlHelper<NvrExport> implements IDisposable
{

	private static NvrExport _instance;
	/** 
	 单例模式
	 
	*/
	public static NvrExport getInstance()
	{
		if(_instance == null)
		{
			_instance = new NvrExport();
		}
		return _instance;
	}

	/** 
	 是/否公安平台
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("IsGongAn")]
	private int privateIsGongAn;
	public final int getIsGongAn()
	{
		return privateIsGongAn;
	}
	public final void setIsGongAn(int value)
	{
		privateIsGongAn = value;
	}

	/** 
	 设备项
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("Item")]
	private java.util.ArrayList<Item> privateItems;
	public final java.util.ArrayList<Item> getItems()
	{
		return privateItems;
	}
	public final void setItems(java.util.ArrayList<Item> value)
	{
		privateItems = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 方法
	public final void Save()
	{
		super.Save(this);
	}

	public final void Read()
	{
		_instance = super.Read(this.getClass());
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 设备信息
	 
	*/
	public static class Item
	{
		/** 
		 设备Guid
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("Guid")]
		private String privateGuid;
		public final String getGuid()
		{
			return privateGuid;
		}
		public final void setGuid(String value)
		{
			privateGuid = value;
		}

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
		//[XmlElement("Parental")]
		private String privateParental;
		public final String getParental()
		{
			return privateParental;
		}
		public final void setParental(String value)
		{
			privateParental = value;
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
		//[XmlElement("SafetyWay")]
		private String privateSafetyWay;
		public final String getSafetyWay()
		{
			return privateSafetyWay;
		}
		public final void setSafetyWay(String value)
		{
			privateSafetyWay = value;
		}

		/** 
		 注册方式(必选)缺省为1；
		 1:符合IETF FRC 3261标准的认证注册模式；
		 2:基于口令的双向认证注册模式；
		 3:基于数字证书的双向认证注册模式；
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("RegisterWay")]
		private int privateRegisterWay;
		public final int getRegisterWay()
		{
			return privateRegisterWay;
		}
		public final void setRegisterWay(int value)
		{
			privateRegisterWay = value;
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
		//[XmlElement("Certifiable")]
		private String privateCertifiable;
		public final String getCertifiable()
		{
			return privateCertifiable;
		}
		public final void setCertifiable(String value)
		{
			privateCertifiable = value;
		}

		/** 
		 证书无效原因码(可选)
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("ErrCode")]
		private String privateErrCode;
		public final String getErrCode()
		{
			return privateErrCode;
		}
		public final void setErrCode(String value)
		{
			privateErrCode = value;
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
		//[XmlElement("Secrecy")]
		private int privateSecrecy;
		public final int getSecrecy()
		{
			return privateSecrecy;
		}
		public final void setSecrecy(int value)
		{
			privateSecrecy = value;
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
		//[XmlElement("Port")]
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ushort privatePort;
		private short privatePort;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ushort getPort()
		public final short getPort()
		{
			return privatePort;
		}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void setPort(ushort value)
		public final void setPort(short value)
		{
			privatePort = value;
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
		//[XmlElement("Longitude")]
		private double privateLongitude;
		public final double getLongitude()
		{
			return privateLongitude;
		}
		public final void setLongitude(double value)
		{
			privateLongitude = value;
		}

		/** 
		 纬度(可选)
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("Latitude")]
		private double privateLatitude;
		public final double getLatitude()
		{
			return privateLatitude;
		}
		public final void setLatitude(double value)
		{
			privateLatitude = value;
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
		 32项扩展信息
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("Extend")]
		private Extend privateExtendInf;
		public final Extend getExtendInf()
		{
			return privateExtendInf;
		}
		public final void setExtendInf(Extend value)
		{
			privateExtendInf = value;
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
		//[XmlElement("PTZType")]
		private String privatePTZType;
		public final String getPTZType()
		{
			return privatePTZType;
		}
		public final void setPTZType(String value)
		{
			privatePTZType = value;
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
		//[XmlElement("PositionType")]
		private String privatePositionType;
		public final String getPositionType()
		{
			return privatePositionType;
		}
		public final void setPositionType(String value)
		{
			privatePositionType = value;
		}

		/** 
		 摄像机按照位置室外、室内属性
		 1，室外
		 2，室内
		 当目录项为摄像机时可选，缺省为1
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("RoomType")]
		private String privateRoomType;
		public final String getRoomType()
		{
			return privateRoomType;
		}
		public final void setRoomType(String value)
		{
			privateRoomType = value;
		}

		/** 
		 摄像机用途属性
		 1，治安
		 2，交通
		 3，重点
		 当目录项为摄像机时可选
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("UseType")]
		private String privateUseType;
		public final String getUseType()
		{
			return privateUseType;
		}
		public final void setUseType(String value)
		{
			privateUseType = value;
		}

		/** 
		 摄像机补光属性
		 1，无补光
		 2，红外补光
		 3，白光补光
		 当目录项为摄像机时可选，缺省为1
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("SupplyLightType")]
		private String privateSupplyLightType;
		public final String getSupplyLightType()
		{
			return privateSupplyLightType;
		}
		public final void setSupplyLightType(String value)
		{
			privateSupplyLightType = value;
		}

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
		//[XmlElement("DirectionType")]
		private String privateDirectionType;
		public final String getDirectionType()
		{
			return privateDirectionType;
		}
		public final void setDirectionType(String value)
		{
			privateDirectionType = value;
		}
	}

	/** 
	 32项扩展属性
	 
	*/
	public static class Extend
	{
		/** 
		 地州市级
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("CityCode")]
		private String privateCityCode;
		public final String getCityCode()
		{
			return privateCityCode;
		}
		public final void setCityCode(String value)
		{
			privateCityCode = value;
		}

		/** 
		 县市区级
		 
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("CountyCode")]
		private String privateCountyCode;
		public final String getCountyCode()
		{
			return privateCountyCode;
		}
		public final void setCountyCode(String value)
		{
			privateCountyCode = value;
		}

		/** 
		 乡镇
		  
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("TownCode")]
		private String privateTownCode;
		public final String getTownCode()
		{
			return privateTownCode;
		}
		public final void setTownCode(String value)
		{
			privateTownCode = value;
		}

		/** 
		 村社区
		  
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("VillageCode")]
		private String privateVillageCode;
		public final String getVillageCode()
		{
			return privateVillageCode;
		}
		public final void setVillageCode(String value)
		{
			privateVillageCode = value;
		}

		/** 
		 小区
		  
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("CommunityCode")]
		private String privateCommunityCode;
		public final String getCommunityCode()
		{
			return privateCommunityCode;
		}
		public final void setCommunityCode(String value)
		{
			privateCommunityCode = value;
		}

		/** 
		 基层
		  
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("GrassRootsCode")]
		private String privateGrassRootsCode;
		public final String getGrassRootsCode()
		{
			return privateGrassRootsCode;
		}
		public final void setGrassRootsCode(String value)
		{
			privateGrassRootsCode = value;
		}

		/** 
		 行业
		  
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("Industry")]
		private String privateIndustry;
		public final String getIndustry()
		{
			return privateIndustry;
		}
		public final void setIndustry(String value)
		{
			privateIndustry = value;
		}

		/** 
		 网络标识
		  
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("NETId")]
		private String privateNETId;
		public final String getNETId()
		{
			return privateNETId;
		}
		public final void setNETId(String value)
		{
			privateNETId = value;
		}

		/** 
		 用户
		  
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("User")]
		private String privateUser;
		public final String getUser()
		{
			return privateUser;
		}
		public final void setUser(String value)
		{
			privateUser = value;
		}

		/** 
		 单位所在派出所
		  
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("UnitPoliceStation")]
		private String privateUnitPoliceStation;
		public final String getUnitPoliceStation()
		{
			return privateUnitPoliceStation;
		}
		public final void setUnitPoliceStation(String value)
		{
			privateUnitPoliceStation = value;
		}

		/** 
		 单位所在警务区
		  
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("UnitPoliceArea")]
		private String privateUnitPoliceArea;
		public final String getUnitPoliceArea()
		{
			return privateUnitPoliceArea;
		}
		public final void setUnitPoliceArea(String value)
		{
			privateUnitPoliceArea = value;
		}

		/** 
		 单位类别
		  
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("UnitType")]
		private String privateUnitType;
		public final String getUnitType()
		{
			return privateUnitType;
		}
		public final void setUnitType(String value)
		{
			privateUnitType = value;
		}

		/** 
		 单位法人
		  
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("UnitJuridical")]
		private String privateUnitJuridical;
		public final String getUnitJuridical()
		{
			return privateUnitJuridical;
		}
		public final void setUnitJuridical(String value)
		{
			privateUnitJuridical = value;
		}

		/** 
		 单位法人联系电话
		  
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("UnitJuridicalTEL")]
		private String privateUnitJuridicalTEL;
		public final String getUnitJuridicalTEL()
		{
			return privateUnitJuridicalTEL;
		}
		public final void setUnitJuridicalTEL(String value)
		{
			privateUnitJuridicalTEL = value;
		}

		/** 
		 责任人
		  
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("Liable")]
		private String privateLiable;
		public final String getLiable()
		{
			return privateLiable;
		}
		public final void setLiable(String value)
		{
			privateLiable = value;
		}

		/** 
		 录入时间
		  
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("EntryTime")]
		private String privateEntryTime;
		public final String getEntryTime()
		{
			return privateEntryTime;
		}
		public final void setEntryTime(String value)
		{
			privateEntryTime = value;
		}

		/** 
		 负责人姓名
		  
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("LiableName")]
		private String privateLiableName;
		public final String getLiableName()
		{
			return privateLiableName;
		}
		public final void setLiableName(String value)
		{
			privateLiableName = value;
		}

		/** 
		 负责人联系电话
		  
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("LiableTEL")]
		private String privateLiableTEL;
		public final String getLiableTEL()
		{
			return privateLiableTEL;
		}
		public final void setLiableTEL(String value)
		{
			privateLiableTEL = value;
		}

		/** 
		 记录更新时间
		  
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("RecordTime")]
		private String privateRecordTime;
		public final String getRecordTime()
		{
			return privateRecordTime;
		}
		public final void setRecordTime(String value)
		{
			privateRecordTime = value;
		}

		/** 
		 建设类型
		  
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("ConstructionType")]
		private String privateConstructionType;
		public final String getConstructionType()
		{
			return privateConstructionType;
		}
		public final void setConstructionType(String value)
		{
			privateConstructionType = value;
		}

		/** 
		 建设时间
		  
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("ConstructionTime")]
		private String privateConstructionTime;
		public final String getConstructionTime()
		{
			return privateConstructionTime;
		}
		public final void setConstructionTime(String value)
		{
			privateConstructionTime = value;
		}

		/** 
		 维护负责人
		  
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("Maintenance")]
		private String privateMaintenance;
		public final String getMaintenance()
		{
			return privateMaintenance;
		}
		public final void setMaintenance(String value)
		{
			privateMaintenance = value;
		}

		/** 
		 维护负责人手机
		  
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("MaintenanceTEL")]
		private String privateMaintenanceTEL;
		public final String getMaintenanceTEL()
		{
			return privateMaintenanceTEL;
		}
		public final void setMaintenanceTEL(String value)
		{
			privateMaintenanceTEL = value;
		}

		/** 
		 备注信息
		  
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("Desription")]
		private String privateDesription;
		public final String getDesription()
		{
			return privateDesription;
		}
		public final void setDesription(String value)
		{
			privateDesription = value;
		}

		/** 
		 是否是前端摄像头，用于列表展示,1代表是，0代表其它
		  
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("IsFront")]
		private int privateIsFront;
		public final int getIsFront()
		{
			return privateIsFront;
		}
		public final void setIsFront(int value)
		{
			privateIsFront = value;
		}
	}

	public final void dispose()
	{
		getItems().clear();
		_instance = null;
	}
}