package com.micro.iotclouds.live2output.gb28181.SIPSorcery.net.SDP;

public class SDPMediaAnnouncement
{
	public static final String MEDIA_FORMAT_ATTRIBUE_PREFIX = "a=rtpmap:";
	public static final String MEDIA_FORMAT_PARAMETERS_ATTRIBUE_PREFIX = "a=fmtp:";

	public static final String m_CRLF = "\r\n";

	public SDPConnectionInformation Connection;

	// Media Announcement fields.
	public SDPMediaTypesEnum Media = SDPMediaTypesEnum.audio; // Media type for the stream.
	public int Port; // For UDP transports should be in the range 1024 to 65535 and for RTP compliance should be even (only even ports used for data).
	public String Transport = "RTP/AVP"; // Defined types RTP/AVP (RTP Audio Visual Profile) and udp.

	public java.util.ArrayList<String> BandwidthAttributes = new java.util.ArrayList<String>();
	public java.util.ArrayList<SDPMediaFormat> MediaFormats = new java.util.ArrayList<SDPMediaFormat>(); // For AVP these will normally be a media payload type as defined in the RTP Audio/Video Profile.
	public java.util.ArrayList<String> ExtraAttributes = new java.util.ArrayList<String>(); // Attributes that were not recognised.

	public SDPMediaAnnouncement()
	{
	}

	public SDPMediaAnnouncement(int port)
	{
		Port = port;
	}

	public SDPMediaAnnouncement(SDPConnectionInformation connection)
	{
		Connection = connection;
	}

	public SDPMediaAnnouncement(SDPMediaTypesEnum mediaType, int port, java.util.ArrayList<SDPMediaFormat> mediaFormats)
	{
		Media = mediaType;
		Port = port;
		MediaFormats = mediaFormats;
	}

	public final void ParseMediaFormats(String formatList)
	{
		if(!org.springframework.util.StringUtils.isEmpty(formatList))
		{
			String[] formatIDs = formatList.split("\\s");
			if(formatIDs != null)
			{
				for (String formatID : formatIDs)
				{
					int format = 0;
					RefObject<Integer> tempRef_format = new RefObject<Integer>(format);
					boolean tempVar = Integer.TryParse(formatID, tempRef_format);
						format = tempRef_format.argvalue;
					if (tempVar)
					{
						MediaFormats.add(new SDPMediaFormat(format));
					}
				}
			}
		}
	}

	public final boolean HasMediaFormat(int formatID)
	{
		for (SDPMediaFormat mediaFormat : MediaFormats)
		{
			if(mediaFormat.FormatID == formatID)
			{
				return true;
			}
		}

		return false;
	}

	public final void AddFormatAttribute(int formatID, String formatAttribute)
	{
		for (int index = 0; index < MediaFormats.size(); index++)
		{
			if(MediaFormats.get(index).FormatID == formatID)
			{
				MediaFormats.get(index).SetFormatAttribute(formatAttribute);
			}
		}
	}

	public final void AddFormatParameterAttribute(int formatID, String formatAttribute)
	{
		for (int index = 0; index < MediaFormats.size(); index++)
		{
			if(MediaFormats.get(index).FormatID == formatID)
			{
				MediaFormats.get(index).SetFormatParameterAttribute(formatAttribute);
			}
		}
	}

	@Override
	public String toString()
	{
		String announcement = "m=" + Media + " " + Port + " " + Transport + " " + GetFormatListToString() + m_CRLF;
		announcement += (Connection == null) ? null : Connection.toString();

		for (String bandwidthAttribute : BandwidthAttributes)
		{
			announcement += "b=" + bandwidthAttribute + m_CRLF;
		}

		announcement += GetFormatListAttributesToString();
		//announcement += "a=recvonly" + m_CRLF;

		for (String extra : ExtraAttributes)
		{
			announcement += org.springframework.util.StringUtils.isEmpty(extra) ? null : extra + m_CRLF;
		}

		return announcement;
	}

	public final String GetFormatListToString()
	{
		String mediaFormatList = null;
		for (SDPMediaFormat mediaFormat : MediaFormats)
		{
			mediaFormatList += mediaFormat.FormatID + " ";
		}

		return (mediaFormatList != null) ? mediaFormatList.trim() : null;
	}

	public final String GetFormatListAttributesToString()
	{
		String formatAttributes = null;

		if(MediaFormats != null)
		{
			//foreach (SDPMediaFormat mediaFormat in MediaFormats.Where(x => x.IsStandardAttribute == false))
			for (SDPMediaFormat mediaFormat : MediaFormats)
			{
				//if (mediaFormat.FormatAttribute != null)
				//{
				//    formatAttributes += SDPMediaAnnouncement.MEDIA_FORMAT_ATTRIBUE_PREFIX + mediaFormat.FormatID + " " + mediaFormat.FormatAttribute + m_CRLF;
				//}
				//else
				//{
					formatAttributes += SDPMediaAnnouncement.MEDIA_FORMAT_ATTRIBUE_PREFIX + mediaFormat.FormatID + " " + mediaFormat.getName() + "/" + mediaFormat.getClockRate() + m_CRLF;
				//}
				//if (mediaFormat.FormatParameterAttribute != null)
				//{
				//    formatAttributes += SDPMediaAnnouncement.MEDIA_FORMAT_PARAMETERS_ATTRIBUE_PREFIX + mediaFormat.FormatID + " " + mediaFormat.FormatParameterAttribute + m_CRLF;
				//}
				//else if(SDPMediaFormat.GetDefaultFormatAttribute(mediaFormat.FormatID) != null)
				//{
				//    formatAttributes += SDPMediaAnnouncement.MEDIA_FORMAT_ATTRIBUE_PREFIX + mediaFormat.FormatID + " " + SDPMediaFormat.GetDefaultFormatAttribute(mediaFormat.FormatID) + m_CRLF;
				//}
			}
		}

		return formatAttributes;
	}
	public final void AddExtra(String attribute)
	{
		if(!org.springframework.util.StringUtils.isEmpty(attribute))
		{
			ExtraAttributes.add(attribute);
		}
	}

}