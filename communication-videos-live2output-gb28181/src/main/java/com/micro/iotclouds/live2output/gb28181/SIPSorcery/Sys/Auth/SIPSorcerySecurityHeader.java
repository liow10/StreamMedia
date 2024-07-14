package com.micro.iotclouds.live2output.gb28181.SIPSorcery.Sys.Auth;

import SIPSorcery.Sys.*;

//using GB28181.Logger4Net;

public class SIPSorcerySecurityHeader
{
	private static final String SECURITY_NAMESPACE = "http://www.sipsorcery.com/security";
	private static final String SECURITY_HEADER_NAME = "Security";
	private static final String SECURITY_PREFIX = "sssec";
	private static final String AUTHID_ELEMENT_NAME = "AuthID";
	private static final String APIKEY_ELEMENT_NAME = "apikey";

	//private static ILog logger = AppState.logger;

	public String AuthID;
	public String APIKey;

	public final boolean getMustUnderstand()
	{
		return true;
	}
	public final String getName()
	{
		return SECURITY_HEADER_NAME;
	}
	public final String getNamespace()
	{
		return SECURITY_NAMESPACE;
	}

	public SIPSorcerySecurityHeader(String authID, String apiKey)
	{
		AuthID = authID;
		APIKey = apiKey;
	}

	protected final void OnWriteHeaderContents(XmlDictionaryWriter writer, String messageVersion)
	{
		if(!AuthID.IsNullOrBlank())
		{
			writer.WriteStartElement(SECURITY_PREFIX, AUTHID_ELEMENT_NAME, SECURITY_NAMESPACE);
			writer.WriteString(AuthID);
			writer.WriteEndElement();
		}

		if(!APIKey.IsNullOrBlank())
		{
			writer.WriteStartElement(SECURITY_PREFIX, APIKEY_ELEMENT_NAME, SECURITY_NAMESPACE);
			writer.WriteString(AuthID);
			writer.WriteEndElement();
		}
	}

	protected final void OnWriteStartHeader(XmlDictionaryWriter writer, String messageVersion)
	{
		writer.WriteStartElement(SECURITY_PREFIX, this.getName(), this.getNamespace());
	}

	public static SIPSorcerySecurityHeader ParseHeader() //OperationContext context
	{
		//try
		//{
		//    int headerIndex = context.IncomingMessageHeaders.FindHeader(SECURITY_HEADER_NAME, SECURITY_NAMESPACE);
		//    if (headerIndex != -1)
		//    {
		//        XmlDictionaryReader reader = context.IncomingMessageHeaders.GetReaderAtHeader(headerIndex);

		//        if (reader.IsStartElement(SECURITY_HEADER_NAME, SECURITY_NAMESPACE))
		//        {
		//            reader.ReadStartElement();
		//            reader.MoveToContent();

		//            if (reader.IsStartElement(AUTHID_ELEMENT_NAME, SECURITY_NAMESPACE))
		//            {
		//                string authID = reader.ReadElementContentAsString();
		//                return new SIPSorcerySecurityHeader(authID, null);
		//            }

		//            if (reader.IsStartElement(APIKEY_ELEMENT_NAME, SECURITY_NAMESPACE))
		//            {
		//                string apiKey = reader.ReadElementContentAsString();
		//                return new SIPSorcerySecurityHeader(null, apiKey);
		//            }
		//        }
		//    }
		//     return null;
		//}
		//catch (Exception excp)
		//{
		//    logger.Error("Exception SIPSorcerySecurityHeader ParseHeader. " + excp.Message);
		//    throw;
		//}
		return null;
	}
}