package GB28181.Sys.Net;

// ============================================================================
// FileName: NetServices.cs
//
// Description:
// Contains wrappers to access the functionality of the underlying operating
// system.
//
// Author(s):
// Aaron Clauson
//
// History:
// 26 Dec 2005	Aaron Clauson	Created.

//using GB28181.Logger4Net;

public enum PlatformEnum
{
	Windows(1),
	Linux(2);

	private int intValue;
	private static java.util.HashMap<Integer, PlatformEnum> mappings;
	private synchronized static java.util.HashMap<Integer, PlatformEnum> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, PlatformEnum>();
		}
		return mappings;
	}

	private PlatformEnum(int value)
	{
		intValue = value;
		PlatformEnum.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static PlatformEnum forValue(int value)
	{
		return getMappings().get(value);
	}
}