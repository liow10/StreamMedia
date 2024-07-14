package GB28181.Sys.XML;

/** 
 设备状态
 
*/
public enum DevStatus
{
	/** 
	 正常
	 
	*/
	ON(0),
	/** 
	 故障
	 
	*/
	OFF(1),
	/** 
	 正常
	 
	*/
	OK(2);

	private int intValue;
	private static java.util.HashMap<Integer, DevStatus> mappings;
	private synchronized static java.util.HashMap<Integer, DevStatus> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, DevStatus>();
		}
		return mappings;
	}

	private DevStatus(int value)
	{
		intValue = value;
		DevStatus.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static DevStatus forValue(int value)
	{
		return getMappings().get(value);
	}
}