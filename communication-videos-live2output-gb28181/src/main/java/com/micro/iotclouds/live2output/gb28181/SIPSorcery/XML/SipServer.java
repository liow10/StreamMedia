package com.micro.iotclouds.live2output.gb28181.SIPSorcery.XML;

/** 
 sip服务器配置
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[XmlRoot("sipaccounts")]
public class SipServer extends XmlHelper<SipServer>
{
	private String _xml = AppDomain.CurrentDomain.BaseDirectory + "Config\\gb28181.xml";

	private static SipServer _instance;
	public static SipServer getInstance()
	{
		if(_instance == null)
		{
			_instance = new SipServer();
		}
		return _instance;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("sipaccount")]
	private java.util.ArrayList<Account> privateAccounts;
	public final java.util.ArrayList<Account> getAccounts()
	{
		return privateAccounts;
	}
	public final void setAccounts(java.util.ArrayList<Account> value)
	{
		privateAccounts = value;
	}

	/** 
	 账户信息
	 
	*/
	public static class Account
	{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("id")]
		private Guid privateid = new Guid();
		public final Guid getid()
		{
			return privateid;
		}
		public final void setid(Guid value)
		{
			privateid = value;
		}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("sipusername")]
		private String privatesipusername;
		public final String getsipusername()
		{
			return privatesipusername;
		}
		public final void setsipusername(String value)
		{
			privatesipusername = value;
		}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("sippassword")]
		private String privatesippassword;
		public final String getsippassword()
		{
			return privatesippassword;
		}
		public final void setsippassword(String value)
		{
			privatesippassword = value;
		}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("sipdomain")]
		private String privatesipdomain;
		public final String getsipdomain()
		{
			return privatesipdomain;
		}
		public final void setsipdomain(String value)
		{
			privatesipdomain = value;
		}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("owner")]
		private String privateowner;
		public final String getowner()
		{
			return privateowner;
		}
		public final void setowner(String value)
		{
			privateowner = value;
		}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("localID")]
		private String privatelocalID;
		public final String getlocalID()
		{
			return privatelocalID;
		}
		public final void setlocalID(String value)
		{
			privatelocalID = value;
		}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlElement("localSocket")]
		private String privatelocalSocket;
		public final String getlocalSocket()
		{
			return privatelocalSocket;
		}
		public final void setlocalSocket(String value)
		{
			privatelocalSocket = value;
		}
	}

//C# TO JAVA CONVERTER WARNING: There is no Java equivalent to C#'s shadowing via the 'new' keyword:
//ORIGINAL LINE: public new void Save<T>(T t)
	public final <T> void Save(T t)
	{
		XmlSerializer xs = new XmlSerializer(T.class);
//C# TO JAVA CONVERTER NOTE: The following 'using' block is replaced by its Java equivalent:
//		using var stream = new MemoryStream()
		try
		{
			;
		}
		finally
		{
		}
		XmlWriterSettings settings = new XmlWriterSettings();
		settings.Indent = true;
		settings.NewLineChars = "\r\n";

//C# TO JAVA CONVERTER NOTE: The following 'using' block is replaced by its Java equivalent:
//		using (XmlWriter writer = XmlWriter.Create(_xml, settings))
		XmlWriter writer = XmlWriter.Create(_xml, settings);
		try
		{
			XmlSerializerNamespaces xns = new XmlSerializerNamespaces();

			xns.Add("", "");
			//去除默认命名空间
			xs.Serialize(writer, t, xns);
			writer.Close();
			writer.dispose();
		}
		finally
		{
			writer.dispose();
		}
	}
}