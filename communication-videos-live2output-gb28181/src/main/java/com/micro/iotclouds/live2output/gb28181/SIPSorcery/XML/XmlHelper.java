package com.micro.iotclouds.live2output.gb28181.SIPSorcery.XML;

//using GB28181.Logger4Net;

/** 
 XML操作访问类
 
 <typeparam name="T">泛型</typeparam>
*/
//C# TO JAVA CONVERTER TODO TASK: The C# 'class' constraint has no equivalent in Java:
public abstract class XmlHelper<T extends class>
{
	//private static ILog logger = AppState.logger;

	/** 
	 文档路径
	 
	*/
	private String m_xml_path;

	private static String m_dir = AppDomain.CurrentDomain.BaseDirectory + "\\Config\\";
	/** 
	 存储对象
	 
	*/
	private T t;

	public XmlHelper()
	{
	}
	/** 
	 序列化
	 
	*/
	private void Serialize(T t)
	{
		//XmlSerializer xs = new XmlSerializer(typeof(T));
		//MemoryStream stream = new MemoryStream();
		//XmlWriterSettings settings = new XmlWriterSettings();
		//settings.Indent = true;
		//settings.Encoding = Encoding.GetEncoding("GB2312");
		//settings.Encoding = new UTF8Encoding(false);
		//settings.NewLineOnAttributes = true;
		//settings.OmitXmlDeclaration = false;
		//using (XmlWriter writer = XmlWriter.Create("c:\\catalog.xml", settings))
		//{
		//    var xns = new XmlSerializerNamespaces();

		//    xns.Add(string.Empty, string.Empty);
		//    去除默认命名空间
		//    xs.Serialize(writer, t, xns);
		//}



		XmlWriterSettings tempVar = new XmlWriterSettings();
		tempVar.OmitXmlDeclaration = false;
		tempVar.Encoding = Encoding.GetEncoding("utf-8");
		tempVar.Indent = true;
		XmlWriterSettings settings = tempVar;
		XmlSerializer s = new XmlSerializer(t.getClass());
		XmlSerializerNamespaces xns = new XmlSerializerNamespaces();
		xns.Add("", "");
		XmlWriter w = XmlWriter.Create(m_xml_path, settings);
		s.Serialize(w, t, xns);
		w.Flush();
		w.Close();
		//TextReader r = new StreamReader("c:\\catalog.xml");
		//string xmlBody = r.ReadToEnd();


		//XmlSerializer s = new XmlSerializer(t.GetType());
		//TextWriter w = new StreamWriter("c:\\catalog.xml");
		//s.Serialize(w, t);
		//w.Flush();
		//w.Close();
	}

	public <T1> String Serialize(T1 obj)
	{
		MemoryStream stream = new MemoryStream();
		XmlSerializer xml = new XmlSerializer(T1.class);
		try
		{
			XmlSerializerNamespaces xns = new XmlSerializerNamespaces();
			xns.Add("", "");
			//序列化对象
			xml.Serialize(stream, obj, xns);
		}
		catch (RuntimeException ex)
		{
			//logger.Error("序列化对象为xml字符串出错" + ex);
		}
		//XmlSerializer xs = new XmlSerializer(typeof(T));
		//MemoryStream stream = new MemoryStream();
		//XmlWriterSettings settings = new XmlWriterSettings();
		//settings.Indent = true;
		//settings.Encoding =  Encoding.GetEncoding("GB2312");
		////settings.Encoding = new UTF8Encoding(false);
		//settings.NewLineOnAttributes = true;
		//settings.OmitXmlDeclaration = false;
		//using (XmlWriter writer = XmlWriter.Create(stream, settings))
		//{
		//    var xns = new XmlSerializerNamespaces();

		//    xns.Add(string.Empty, string.Empty);
		//    //去除默认命名空间
		//    xs.Serialize(writer, obj, xns);
		//}
		return Encoding.UTF8.GetString(stream.toArray()); //.Replace("\r", "");
	}

	///// <summary>  
	///// 对象序列化成 XML String  
	///// </summary>  
	//public string Serialize<T>(T obj)
	//{
	//    string xmlString = string.Empty;
	//    XmlSerializer xmlSerializer = new XmlSerializer(typeof(T));

	//    using (MemoryStream ms = new MemoryStream())
	//    {
	//        xmlSerializer.Serialize(ms, obj);
	//        xmlString = Encoding.UTF8.GetString(ms.ToArray());
	//    }
	//    return xmlString;
	//}  

	//public string Serialize<T>(T entity)
	//{
	//    StringBuilder buffer = new StringBuilder();

	//    XmlSerializer serializer = new XmlSerializer(typeof(T));
	//    using (TextWriter writer = new StringWriter(buffer))
	//    {
	//        serializer.Serialize(writer, entity);
	//    }

	//    return buffer.ToString();

	//}  

	///// <summary>
	///// 序列化
	///// </summary>
	///// <typeparam name="T">类型</typeparam>
	///// <param name="entity">实体类型</param>
	///// <returns>XML格式字符串</returns>
	//public string Serialize<T>(T entity)
	//{
	//    //StringBuilder 
	//    MemoryStream stream = new MemoryStream();
	//    XmlSerializer serializer = new XmlSerializer(typeof(T));
	//    XmlWriterSettings settings = new XmlWriterSettings();
	//    settings.Indent = true;
	//    settings.Encoding = new UTF8Encoding(false);
	//    settings.NewLineOnAttributes = true;
	//    settings.OmitXmlDeclaration = false;
	//    try
	//    {
	//        using (XmlWriter write = XmlWriter.Create(stream, settings))
	//        {
	//            XmlSerializerNamespaces ns = new XmlSerializerNamespaces();
	//            //去除默认命名空间
	//            ns.Add("", "");
	//            serializer.Serialize(stream, entity, ns);
	//        }
	//    }
	//    catch (Exception ex)
	//    {
	//        logger.Error("对象序列化到XML格式字符串错误" + ex.Message+ex.StackTrace.ToString());
	//    }
	//    stream.Close();
	//    return Encoding.UTF8.GetString(stream.ToArray()).Replace("\r", "");
	//} 

	/** 
	 反序列
	 
	 @return 
	*/
	private T Deserialize()
	{
		//MemoryStream stream = new MemoryStream(Encoding.GetEncoding("utf-8").GetBytes(m_xml_path));
		//StreamReader sr = new StreamReader(stream, Encoding.GetEncoding("utf-8"));

		if(File.Exists(m_xml_path))
		{
			TextReader r = new StreamReader(m_xml_path);
			XmlSerializer s = new XmlSerializer(T.class);
			Object obj;
			try
			{
				obj = (T)s.Deserialize(r);
			}
			catch (RuntimeException e)
			{
				r.Close();
				return null;
			}
			if(obj instanceof T)
			{
				t = (T)((obj instanceof T) ? obj : null);
			}
			r.Close();
		}
		return t;
	}

	public final String ConvertUtf8ToDefault(String message)
	{
		System.Text.Encoding utf8;
		utf8 = System.Text.Encoding.GetEncoding("utf-8");
		byte[] array = Encoding.Unicode.GetBytes(message);
		byte[] s4 = System.Text.Encoding.Convert(System.Text.Encoding.UTF8, System.Text.Encoding.GetEncoding("gb2312"), array);
		String str = Encoding.Default.GetString(s4);
		return str;
	}


	/** 
	 反序列
	 
	 @return 
	*/
	private T Deserialize(String xmlBody)
	{
		MemoryStream stream = new MemoryStream(Encoding.GetEncoding("utf-8").GetBytes(xmlBody));
		StreamReader sr = new StreamReader(stream, Encoding.GetEncoding("utf-8"));

		//TextReader sr = new StringReader(xmlBody);
		XmlSerializer s = new XmlSerializer(T.class);
		Object obj;
		try
		{
			obj = (T)s.Deserialize(sr);
		}
		catch (RuntimeException ex)
		{
			//logger.Error("反序列化错误" + ex.Message + ex.StackTrace.ToString());
			sr.Close();
			return null;
		}
		if(obj instanceof T)
		{
			t = (T)((obj instanceof T) ? obj : null);
		}
		sr.Close();
		return t;
	}

	/** 
	 读取文件并返回并构建成类
	 
	 @param type 类型
	 @return 需要返回的类型格式
	*/
	public T Read(java.lang.Class type)
	{
		CheckConstructPath(type);
		return this.Deserialize();
	}
	/** 
	 读取文件并返回并构建成类
	 
	 @param xmlBody XML文档
	 @return 需要返回的类型格式
	*/
	public T Read(String xmlBody)
	{
		return this.Deserialize(xmlBody);
	}

	/** 
	 //检查并构造路径
	 
	 @param type
	*/
	private void CheckConstructPath(java.lang.Class type)
	{
		//构造路径
		String temppath = m_dir + type.getName() + ".xml";

		//如果路径相等则返回
		if(this.m_xml_path.equals(temppath))
		{
			return;
		}

		//是否存在Config目录，不存在则返回
		if(!Directory.Exists(m_dir))
		{
			Directory.CreateDirectory(m_dir);
		}

		this.m_xml_path = temppath;

	}

	/** 
	 保存文件
	 
	 @param t 类型
	*/
	public void Save(T t)
	{
		CheckConstructPath(t.getClass());
		Serialize(t);
	}

	/** 
	 保存文件
	 
	 <typeparam name="T1">类型1</typeparam>
	 @param t 类型实例
	 @return 
	*/
	public <T1> String Save(T1 t)
	{
		CheckConstructPath(t.getClass());
		return Serialize(t);
	}
}