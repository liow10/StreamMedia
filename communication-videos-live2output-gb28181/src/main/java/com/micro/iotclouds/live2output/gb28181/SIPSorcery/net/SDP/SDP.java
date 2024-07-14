package com.micro.iotclouds.live2output.gb28181.SIPSorcery.net.SDP;

//-----------------------------------------------------------------------------
// Filename: SDP.cs
//
// Description: Session Description Protocol implementation as defined in RFC 2327.
//
// Relevant Bits from the RFC:
// "SDP is intended for describing mulitmedia sessions for the purposes of session
// announcement, session inviatation, and other forms of multimedia session
// initiation." 
//
// SDP Includes:
// - Session name and Purpose,
// - Time(s) the session is active,
// - The media comprising the session,
// - Information to receive those media (addresses, ports, formats etc.)
// As resources to participate in the session may be limited, some additional information
// may also be deisreable:
// - Information about the bandwidth to be used,
// - Contatc information for the person responsible for the conference.
//
// Media Information, SDP Includes:
// - The type of media (video, audio, etc),
// - The transport protocol (RTP/UDP/IP, H.320, ext),
// - The format of the media (H.261 video, MPEG video, etc).
//
// An SDP session description consists of a number of lines of text of the form
// <type>=<value> where <type> is always exactly one character and is case-significant.
// <value> is a structured test string whose format depends on <type> and is also
// case-significant unless the <type> permits otherwise. Whitespace is not permitted
// either side of the = sign.
//
// An announcement consists of a session-level section followed by zero
// or more media-level sections.  The session-level part starts with a
// 'v=' line and continues to the first media-level section.  The media
// description starts with an `m=' line and continues to the next media
// description or end of the whole session description.
//
// The sequence CRLF (0x0d0a) is used to end a record, although parsers should be
// tolerant and also accept records terminated with a single newline character. 
//
// Session description
// v=  (protocol version)
// o=  (owner/creator and session identifier).
//     <username> <session id> <version> <network type> <address type> <address>
// s=  (session name)
// i=* (session information)
//
// u=* (URI of description)
// e=* (email address)
// p=* (phone number)
// c=* (connection information - not required if included in all media)
// b=* (bandwidth information)
// One or more time descriptions (see below)
// z=* (time zone adjustments)
// k=* (encryption key)
// a=* (zero or more session attribute lines)
// Zero or more media descriptions (see below)
//
// Time description
// t=  (time the session is active)
// r=* (zero or more repeat times)
//
// Media description
// m=  (media name and transport address)
//     <media> <port> <transport> <fmt list>
// i=* (media title)
// c=* (connection information - optional if included at session-level)
// b=* (bandwidth information)
// k=* (encryption key)
// a=* (zero or more media attribute lines)
//
// Example SDP Description:
// 
// v=0
// o=mhandley 2890844526 2890842807 IN IP4 126.16.64.4
// s=SDP Seminar
// i=A Seminar on the session description protocol
// u=http://www.cs.ucl.ac.uk/staff/M.Handley/sdp.03.ps
// e=mjh@isi.edu (Mark Handley)
// c=IN IP4 224.2.17.12/127
// t=2873397496 2873404696
// a=recvonly
// m=audio 49170 RTP/AVP 0
// m=video 51372 RTP/AVP 31
// m=application 32416 udp wb
// a=orient:portrait
// 
// History:
// 20 Oct 2005	Aaron Clauson	Created.
//
// License: 
// Aaron Clauson

//using GB28181.Sys;
//using GB28181.Logger4Net;

public class SDP
{
	public static final String CRLF = "\r\n";
	public static final String SDP_MIME_CONTENTTYPE = "application/sdp";
	public static final java.math.BigDecimal SDP_PROTOCOL_VERSION = 0M;
	public static final String ICE_UFRAG_ATTRIBUTE_PREFIX = "ice-ufrag";
	public static final String ICE_PWD_ATTRIBUTE_PREFIX = "ice-pwd";
	public static final String ICE_CANDIDATE_ATTRIBUTE_PREFIX = "candidate";

	//private static ILog logger = AppState.logger;

	public java.math.BigDecimal Version = SDP_PROTOCOL_VERSION;

	// Owner fields.
	public String Username = "-"; // Username of the session originator.
	public String SessionId = "-"; // Unique Id for the session.
	public int AnnouncementVersion = 0; // Version number for each announcement, number must be increased for each subsequent SDP modification.
	public String NetworkType = "IN"; // Type of network, IN = Internet.
	public String AddressType = "IP4"; // Address type, typically IP4 or IP6.
	public String Address; // IP Address of the machine that created the session, either FQDN or dotted quad or textual for IPv6.
	public final String getOwner()
	{
		return Username + " " + SessionId + " " + AnnouncementVersion + " " + NetworkType + " " + AddressType + " " + Address;
	}

	public String SessionName = "-"; // Common name of the session.
	public String Timing;
	public java.util.ArrayList<String> BandwidthAttributes = new java.util.ArrayList<String>();

	// Optional fields.
	public String SessionDescription;
	public String URI; // URI for additional information about the session.
	public String[] OriginatorEmailAddresses; // Email addresses for the person responsible for the session.
	public String[] OriginatorPhoneNumbers; // Phone numbers for the person responsible for the session.
	public String IceUfrag; // If ICE is being used the username for the STUN requests.
	public String IcePwd; // If ICE is being used the password for the STUN requests.
	public java.util.ArrayList<String> IceCandidates;

	public SDPConnectionInformation Connection;

	// Media.
	public java.util.ArrayList<SDPMediaAnnouncement> Media = new java.util.ArrayList<SDPMediaAnnouncement>();

	public java.util.ArrayList<String> ExtraAttributes = new java.util.ArrayList<String>(); // Attributes that were not recognised.

	public SDP()
	{
	}

	public SDP(String address)
	{
		Address = address;
	}

	//public static SDP ParseSDPDescription(string sdpDescription)
	//{

	//}

	public final void AddExtra(String attribute)
	{
		if(!org.springframework.util.StringUtils.isEmpty(attribute))
		{
			ExtraAttributes.add(attribute);
		}
	}

	@Override
	public String toString()
	{
		//SDP˳sample
//            
//             * v=0
//             * o=34020000002000000001 0 0 IN IP4 192.168.10.60
//             * s=Playback
//             * u=34020000001320000020:3
//             * c=IN IP4 192.168.10.60
//             * t=1481852021 1481855621
//             * m=video 10004 RTP/AVP 96 98
//             * a=recvonly
//             * a=rtpmap:96 PS/90000
//             * a=rtpmap:98 H264/90000
//             


		String sdp = "v=" + SDP_PROTOCOL_VERSION.add(CRLF) + "o=" + getOwner() + CRLF + "s=" + SessionName + CRLF;
		sdp += org.springframework.util.StringUtils.isEmpty(URI) ? null : "u=" + URI + CRLF;
		sdp += ((Connection != null) ? Connection.toString() : null);
		for (String bandwidth : BandwidthAttributes)
		{
			sdp += "b=" + bandwidth + CRLF;
		}

		sdp += "t=" + Timing + CRLF;

		sdp += !org.springframework.util.StringUtils.isEmpty(IceUfrag) ? "a=" + ICE_UFRAG_ATTRIBUTE_PREFIX + ":" + IceUfrag + CRLF : null;
		sdp += !org.springframework.util.StringUtils.isEmpty(IcePwd) ? "a=" + ICE_PWD_ATTRIBUTE_PREFIX + ":" + IcePwd + CRLF : null;
		sdp += org.springframework.util.StringUtils.isEmpty(SessionDescription) ? null : "i=" + SessionDescription + CRLF;

		if(OriginatorEmailAddresses != null && OriginatorEmailAddresses.length > 0)
		{
			for (String originatorAddress : OriginatorEmailAddresses)
			{
				sdp += org.springframework.util.StringUtils.isEmpty(originatorAddress) ? null : "e=" + originatorAddress + CRLF;
			}
		}

		if(OriginatorPhoneNumbers != null && OriginatorPhoneNumbers.length > 0)
		{
			for (String originatorNumber : OriginatorPhoneNumbers)
			{
				sdp += org.springframework.util.StringUtils.isEmpty(originatorNumber) ? null : "p=" + originatorNumber + CRLF;
			}
		}

		for (String extra : ExtraAttributes)
		{
			sdp += org.springframework.util.StringUtils.isEmpty(extra) ? null : extra + CRLF;
		}

		for (SDPMediaAnnouncement media : Media)
		{
			sdp += (media == null) ? null : media.toString();
		}

		return sdp;
	}

	public static IPEndPoint GetSDPRTPEndPoint(String sdpMessage)
	{
		// Process the SDP payload.
		Match portMatch = Regex.Match(sdpMessage, "m=audio (?<port>\\d+)", RegexOptions.Singleline);
		if(portMatch.Success)
		{
			int rtpServerPort = Integer.parseInt(portMatch.Result("${port}"));

			Match serverMatch = Regex.Match(sdpMessage, "c=IN IP4 (?<ipaddress>(\\d+\\.){3}\\d+)", RegexOptions.Singleline);
			if(serverMatch.Success)
			{
				String rtpServerAddress = serverMatch.Result("${ipaddress}");
				IPAddress ipAddress = null;

				RefObject<IPAddress> tempRef_ipAddress = new RefObject<IPAddress>(ipAddress);
				boolean tempVar = IPAddress.TryParse(rtpServerAddress, tempRef_ipAddress);
					ipAddress = tempRef_ipAddress.argvalue;
				if (tempVar)
				{
					IPEndPoint serverEndPoint = new IPEndPoint(ipAddress, rtpServerPort);
					return serverEndPoint;
				}
			}
		}

		return null;
	}
}