package com.micro.iotclouds.live2output.gb28181.SIPSorcery;

import PropertyChanged.*;
import SIPSorcery.SIP.*;
import SIPSorcery.SIP.App.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[AddINotifyPropertyChangedInterface]
public class SIPAccountBinding
{
	private SIPRequest privateSIPRequest;
	public final SIPRequest getSIPRequest()
	{
		return privateSIPRequest;
	}
	public final void setSIPRequest(SIPRequest value)
	{
		privateSIPRequest = value;
	}
	private SIPAccount privateSIPAccount;
	public final SIPAccount getSIPAccount()
	{
		return privateSIPAccount;
	}
	public final void setSIPAccount(SIPAccount value)
	{
		privateSIPAccount = value;
	}
	private SIPURI privateRegisteredContact;
	public final SIPURI getRegisteredContact()
	{
		return privateRegisteredContact;
	}
	public final void setRegisteredContact(SIPURI value)
	{
		privateRegisteredContact = value;
	}
	private SIPEndPoint privateRemoteEndPoint;
	public final SIPEndPoint getRemoteEndPoint()
	{
		return privateRemoteEndPoint;
	}
	public final void setRemoteEndPoint(SIPEndPoint value)
	{
		privateRemoteEndPoint = value;
	}
	private SIPEndPoint privateLocalEndPoint;
	public final SIPEndPoint getLocalEndPoint()
	{
		return privateLocalEndPoint;
	}
	public final void setLocalEndPoint(SIPEndPoint value)
	{
		privateLocalEndPoint = value;
	}
	private int privateExpiry;
	public final int getExpiry()
	{
		return privateExpiry;
	}
	public final void setExpiry(int value)
	{
		privateExpiry = value;
	}

	/** 
	 用于记录 INVITE 通信过程中的 CallId;
	 
	*/
	private String privateCallId;
	public final String getCallId()
	{
		return privateCallId;
	}
	public final void setCallId(String value)
	{
		privateCallId = value;
	}

	private String privateFromTag;
	public final String getFromTag()
	{
		return privateFromTag;
	}
	public final void setFromTag(String value)
	{
		privateFromTag = value;
	}
	private String privateToTag;
	public final String getToTag()
	{
		return privateToTag;
	}
	public final void setToTag(String value)
	{
		privateToTag = value;
	}

	/** 
	 是否发起过直播;
	 
	*/
	private boolean privateIsLived;
	public final boolean getIsLived()
	{
		return privateIsLived;
	}
	public final void setIsLived(boolean value)
	{
		privateIsLived = value;
	}

	public SIPAccountBinding(SIPRequest sipRequest, SIPAccount sipAccount, SIPURI contact, SIPEndPoint remote, SIPEndPoint local, int expiry)
	{
		setSIPRequest(sipRequest);
		setSIPAccount(sipAccount);
		setRegisteredContact(contact);
		setRemoteEndPoint(remote);
		setLocalEndPoint(local);
		setExpiry(expiry);
	}
}