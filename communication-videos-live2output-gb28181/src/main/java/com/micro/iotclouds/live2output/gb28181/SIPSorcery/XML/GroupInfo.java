package com.micro.iotclouds.live2output.gb28181.SIPSorcery.XML;

/** 
 组织结构
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[XmlRoot("GroupInfo")]
public class GroupInfo extends XmlHelper<GroupInfo> implements IDisposable
{
	private static GroupInfo instance;
	/** 
	 以单例模式访问
	 
	*/
	public static GroupInfo getInstance()
	{
		if(instance == null)
		{
			instance = new GroupInfo();
		}
		return instance;
	}

	private java.util.ArrayList<GroupInfoItem> mGroupInfoItems = new java.util.ArrayList<GroupInfoItem>(500);
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("GroupInfoItem")]
	public final java.util.ArrayList<GroupInfoItem> getItems()
	{
		return this.mGroupInfoItems;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 方法
	public final void Save()
	{
		super.Save(this);
	}
	public final void Read()
	{
		instance = this.Read(this.getClass());
	}

	public final GroupInfoItem Get(String id)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
		for (var item : GroupInfo.getInstance().getItems())
		{
			if(item.GroupCode.toString().equals(id))
			{
				return item;
			}
		}
		return null;
	}

	public final GroupInfoItem Get(int guid)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
		for (var item : GroupInfo.getInstance().getItems())
		{
			if(guid == item.Guid)
			{
				return item;
			}
		}
		return null;
	}

	public final void Remove(String id)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
		var item = getItems().Find(e => id.equals(e.GroupCode.toString()));
		getItems().remove(item);
	}

	private java.util.ArrayList<GroupInfo.GroupInfoItem> list = new java.util.ArrayList<GroupInfoItem>();
	public final void RemoveAll(String id)
	{
		list.clear();

//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
		var item = getItems().FirstOrDefault(e => id.equals(e.GroupCode));
		if(item == null)
		{
			throw new RuntimeException("文件中为找到相应节点！");
		}

		list.add(item);

//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
		var items = getItems().FindAll(e => id.equals(e.ParentID));
		if(items.size() > 0)
		{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
			for (var t : items)
			{
				list.add(t);
				findChildren(t.GroupCode);
			}
		}

//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
		for (var s : list)
		{
			getItems().remove(s);
		}
	}
	private void findChildren(String id)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
		var items = getItems().FindAll(e => id.equals(e.ParentID));
		if(items.size() > 0)
		{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
			for (var t : items)
			{
				list.add(t);
				findChildren(t.GroupCode);
			}
		}
	}

	public final GroupInfoItem GetParent(String id)
	{
		GroupInfoItem parent = null;

		GroupInfoItem item = Get(id);
		if(item != null)
		{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
			var p = getItems().FirstOrDefault(e => item.getGroupCode().equals(e.ParentID));
			if(p != null)
			{
				parent = p;
			}
		}
		return parent;
	}

	public final void MatchParent()
	{
		if(getItems().size() > 0)
		{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
			for (var item : getItems())
			{
				if(item.GroupIdentify.equals("0"))
				{
					continue;
				}
				if(DotNetToJavaStringHelper.isNullOrEmpty(item.ParentID))
				{
					if(item.GroupCode == item.SubordinatePlatform)
					{

					}
					if(item.GroupCode.getLength() == 2) //ProviceCata
					{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
						var root = getItems().FirstOrDefault(p => p.SubordinatePlatform == p.GroupCode);
						if(root == null)
						{
							continue;
						}

						item.ParentID = root.GroupCode;
					}
					else if(item.GroupCode.getLength() == 4) //CityCata
					{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
						var proviceCata = getItems().FirstOrDefault(p => p.GroupCode == item.GroupCode.substring(0, 2));
						if(proviceCata == null)
						{
							continue;
						}

						item.ParentID = proviceCata.GroupCode;
					}
					else if(item.GroupCode.getLength() == 6) //AreaCata
					{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
						var areaCata = getItems().FirstOrDefault(p => p.GroupCode == item.GroupCode.substring(0, 4));
						if(areaCata == null)
						{
							continue;
						}

						item.ParentID = areaCata.GroupCode;
					}
					else if(item.GroupCode.getLength() == 8) //BasicUnit
					{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
						var basicUnit = getItems().FirstOrDefault(p => p.GroupCode == item.GroupCode.substring(0, 6));
						if(basicUnit == null)
						{
							continue;
						}

						item.ParentID = basicUnit.GroupCode;
					}
				}
			}
			GroupInfo.getInstance().Save();
		}

	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ushort CreatGuid()
	public final short CreatGuid()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort Guid = 0;
		short Guid = 0;

//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
		for (var guidItem : this.mGroupInfoItems)
		{
			if(guidItem.Guid >= Guid)
			{
				Guid = guidItem.Guid;
			}
		}
		return (short)(Guid + 1);
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ushort MatchParent(ushort guid)
	public final short MatchParent(short guid)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort parent = 0;
		short parent = 0;

		GroupInfoItem item = this.Get(guid);
		if(item != null)
		{
			GroupInfoItem itemP = this.Get(item.getParentID());
			if(itemP != null)
			{
				parent = itemP.getGuid();
			}
		}

		return parent;

	}


//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 子类
	public static class GroupInfoItem
	{
		public GroupInfoItem()
		{

		}
		public GroupInfoItem(String groupCode, String name, String parentID, String groupType, String groupMark, String groupIdentify, String subordinatePlatform)
		{
			setGroupCode(groupCode);
			setName(name);
			setParentID(parentID);
			setGroupType(groupType);
			setGroupMark(groupMark);
			setGroupIdentify(groupIdentify);
			setSubordinatePlatform(subordinatePlatform);
		}
		/** 
		 索引
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlAttribute("Guid")]
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ushort privateGuid;
		private short privateGuid;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ushort getGuid()
		public final short getGuid()
		{
			return privateGuid;
		}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void setGuid(ushort value)
		public final void setGuid(short value)
		{
			privateGuid = value;
		}
		/** 
		 分组编码
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlAttribute("GroupCode")]
		private String privateGroupCode;
		public final String getGroupCode()
		{
			return privateGroupCode;
		}
		public final void setGroupCode(String value)
		{
			privateGroupCode = value;
		}

		/** 
		 分组名称
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlAttribute("Name")]
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
		 分组上级ID
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlAttribute("ParentID")]
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
		//[XmlAttribute("BusinessGroupID")]
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
		 分组类型
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlAttribute("GroupType")]
		private String privateGroupType;
		public final String getGroupType()
		{
			return privateGroupType;
		}
		public final void setGroupType(String value)
		{
			privateGroupType = value;
		}
		/** 
		 分组备注名
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlAttribute("GroupMark")]
		private String privateGroupMark;
		public final String getGroupMark()
		{
			return privateGroupMark;
		}
		public final void setGroupMark(String value)
		{
			privateGroupMark = value;
		}

		/** 
		 分组识别码
		 0，自建设备 
		 1，国标
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlAttribute("GroupIdentify")]
		private String privateGroupIdentify;
		public final String getGroupIdentify()
		{
			return privateGroupIdentify;
		}
		public final void setGroupIdentify(String value)
		{
			privateGroupIdentify = value;
		}

		/** 
		 所属平台编码
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlAttribute("SubordinatePlatform")]
		private String privateSubordinatePlatform;
		public final String getSubordinatePlatform()
		{
			return privateSubordinatePlatform;
		}
		public final void setSubordinatePlatform(String value)
		{
			privateSubordinatePlatform = value;
		}
		/** 
		 设备/区域/系统IP地址
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlAttribute("IPAddress")]
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
		 设备/区域/系统端口
		 
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
		//[XmlAttribute("Port")]
		public final String getPortValue()
		{
			return getPort() != null ? getPort().toString() : null;
		}
		public final void setPortValue(String value)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort result;
			short result = 0;
			RefObject<Short> tempRef_result = new RefObject<Short>(result);
			setPort(Short.TryParse(value, tempRef_result) ? result : (Short)null);
			result = tempRef_result.argvalue;
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
		//[XmlAttribute("RegisterWay")]
		public final String getRegisterWayValue()
		{
			return getRegisterWay() != null ? getRegisterWay().toString() : null;
		}
		public final void setRegisterWayValue(String value)
		{
			int result = 0;
			RefObject<Integer> tempRef_result = new RefObject<Integer>(result);
			setRegisterWay(Integer.TryParse(value, tempRef_result) ? result : (Integer)null);
			result = tempRef_result.argvalue;
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
		//[XmlAttribute("Secrecy")]
		public final String getSecrecyValue()
		{
			return getSecrecy() != null ? getSecrecy().toString() : null;
		}
		public final void setSecrecyValue(String value)
		{
			int result = 0;
			RefObject<Integer> tempRef_result = new RefObject<Integer>(result);
			setSecrecy(Integer.TryParse(value, tempRef_result) ? result : (Integer)null);
			result = tempRef_result.argvalue;
		}

		/** 
		 设备状态(必选)
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlAttribute("Status")]
		private String privateStatus;
		public final String getStatus()
		{
			return privateStatus;
		}
		public final void setStatus(String value)
		{
			privateStatus = value;
		}

	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	public final void dispose()
	{
		getItems().clear();
		instance = null;
	}
}