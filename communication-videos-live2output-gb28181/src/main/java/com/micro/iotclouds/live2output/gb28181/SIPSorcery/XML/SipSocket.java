package com.micro.iotclouds.live2output.gb28181.SIPSorcery.XML;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[XmlRoot("sipServer")]
public class SipSocket extends XmlHelper<SipSocket>
{
	private static SipSocket _instance;
	public static SipSocket getInstance()
	{
		if(_instance == null)
		{
			_instance = new SipSocket();
		}
		return _instance;
	}

	@Override
	public <T> String Save(T t)
	{
		XmlSerializer xs = new XmlSerializer(T.class);
		MemoryStream stream = new MemoryStream();
		XmlWriterSettings settings = new XmlWriterSettings();
		settings.Indent = true;
		settings.NewLineChars = "\r\n";
		//settings.Encoding = Encoding.GetEncoding("GB2312");
		//settings.Encoding = new UTF8Encoding(false);
		//settings.NewLineOnAttributes = true;
		//settings.OmitXmlDeclaration = false;
//C# TO JAVA CONVERTER NOTE: The following 'using' block is replaced by its Java equivalent:
//		using (XmlWriter writer = XmlWriter.Create(stream, settings))
		XmlWriter writer = XmlWriter.Create(stream, settings);
		try
		{
			XmlSerializerNamespaces xns = new XmlSerializerNamespaces();

			xns.Add("", "");
			//去除默认命名空间
			xs.Serialize(writer, t, xns);
		}
		finally
		{
			writer.dispose();
		}
		return Encoding.UTF8.GetString(stream.toArray()).Replace("\r", "");
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[XmlElement("MonitorLoopbackPort")]
	private MonitorLoopbackPort privateMonitorPort;
	public final MonitorLoopbackPort getMonitorPort()
	{
		return privateMonitorPort;
	}
	public final void setMonitorPort(MonitorLoopbackPort value)
	{
		privateMonitorPort = value;
	}

	public static class MonitorLoopbackPort
	{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[XmlAttribute("value")]
		private int privatevalue;
		public final int getvalue()
		{
			return privatevalue;
		}
		public final void setvalue(int value)
		{
			privatevalue = value;
		}
	}

}