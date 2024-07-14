package com.micro.iotclouds.live2output.gb28181.SIPSorcery.net.SDP;

public class SDPMediaFormat
{
	private static final int DEFAULT_CLOCK_RATE = 90000;

	//private static Dictionary<int, string> m_defaultFormatNames = new Dictionary<int, string>();

	public int FormatID;
	private String privateFormatAttribute;
	public final String getFormatAttribute()
	{
		return privateFormatAttribute;
	}
	private void setFormatAttribute(String value)
	{
		privateFormatAttribute = value;
	}
	private String privateFormatParameterAttribute;
	public final String getFormatParameterAttribute()
	{
		return privateFormatParameterAttribute;
	}
	private void setFormatParameterAttribute(String value)
	{
		privateFormatParameterAttribute = value;
	}
	private String privateName;
	public final String getName()
	{
		return privateName;
	}
	private void setName(String value)
	{
		privateName = value;
	}
	private int privateClockRate;
	public final int getClockRate()
	{
		return privateClockRate;
	}
	private void setClockRate(int value)
	{
		privateClockRate = value;
	}
	private boolean privateIsStandardAttribute;
	public final boolean getIsStandardAttribute()
	{
		return privateIsStandardAttribute;
	}
	public final void setIsStandardAttribute(boolean value)
	{
		privateIsStandardAttribute = value;
	}

	static
	{
		//m_defaultFormatNames.Add((int)SDPMediaFormatsEnum.PCMU, "PCMU/8000");
		//m_defaultFormatNames.Add((int)SDPMediaFormatsEnum.GSM, "GSM/8000");
		//m_defaultFormatNames.Add((int)SDPMediaFormatsEnum.PCMA, "PCMA/8000");
		//m_defaultFormatNames.Add((int)SDPMediaFormatsEnum.G723, "G723/8000");
	}

	public SDPMediaFormat(int formatID)
	{
		FormatID = formatID;
		if(Enum.IsDefined(SDPMediaFormatsEnum.class, formatID))
		{
//ORIGINAL LINE: Name = Enum.Parse(typeof(SDPMediaFormatsEnum), formatID.ToString(), true).ToString();
//C# TO JAVA CONVERTER WARNING: Java does not have a 'ignoreCase' parameter for the static 'valueOf' method of enum types:
			setName(SDPMediaFormatsEnum.valueOf((new Integer(formatID)).toString()).toString());
		}
		setClockRate(DEFAULT_CLOCK_RATE);
	}

	public SDPMediaFormat(int formatID, String name)
	{
		FormatID = formatID;
		setName(name);
		setFormatAttribute((getClockRate() == 0) ? getName() : getName());
	}

	public SDPMediaFormat(int formatID, String name, int clockRate)
	{
		FormatID = formatID;
		setName(name);
		setClockRate(clockRate);
		setFormatAttribute((getClockRate() == 0) ? getName() : getName() + "/" + getClockRate());
	}

	public SDPMediaFormat(SDPMediaFormatsEnum format)
	{
		FormatID = format.getValue();
		setName(format.toString());
		setIsStandardAttribute(true);
		setClockRate(DEFAULT_CLOCK_RATE);
	}

	public final void SetFormatAttribute(String attribute)
	{
		setFormatAttribute(attribute);

		Match attributeMatch = Regex.Match(attribute, "(?<name>\\w+)/(?<clockrate>\\d+)\\s*");
		if(attributeMatch.Success)
		{
			setName(attributeMatch.Result("${name}"));
			int clockRate = 0;
			RefObject<Integer> tempRef_clockRate = new RefObject<Integer>(clockRate);
			boolean tempVar = Integer.TryParse(attributeMatch.Result("${clockrate}"), tempRef_clockRate);
				clockRate = tempRef_clockRate.argvalue;
			if (tempVar)
			{
				setClockRate(clockRate);
			}
		}
	}

	public final void SetFormatParameterAttribute(String attribute)
	{
		setFormatParameterAttribute(attribute);
	}

	//public static string GetDefaultFormatAttribute(int mediaFormat)
	//{
	//    if (m_defaultFormats.ContainsKey(mediaFormat))
	//    {
	//        return m_defaultFormats[mediaFormat];
	//    }
	//    else
	//    {
	//        return null;
	//    }
	//}
}