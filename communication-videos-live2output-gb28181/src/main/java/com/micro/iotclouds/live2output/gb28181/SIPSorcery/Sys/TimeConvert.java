package com.micro.iotclouds.live2output.gb28181.SIPSorcery.Sys;

/** 
 日期/时间戳转换
 
*/
public final class TimeConvert
{
	/** 
	 日期类型转换为时间戳
	 返回自1970年以来的秒数
	 
	 @param time 时间
	 @return 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static uint DateToTimeStamp(DateTime date)
	public static int DateToTimeStamp(java.util.Date date)
	{
		//return (uint)TimeZoneInfo.Local.GetUtcOffset(date).Seconds;
		return (int)((date.ToUniversalTime().Ticks - 621355968000000000) / 10000000);
	}

	//private static string LongDateTimeToDateTimeString(string longDateTime)
	//{
	//    //用来格式化long类型时间的,声明的变量
	//    long unixDate;
	//    DateTime start;
	//    DateTime date;
	//    //ENd

	//    unixDate = long.Parse(longDateTime);
	//    start = new DateTime(1970, 1, 1, 0, 0, 0, DateTimeKind.Utc);
	//    date = start.AddMilliseconds(unixDate).ToLocalTime();

	//    return date.ToString("yyyy-MM-dd HH:mm:ss");

	//}
	/** 
	 时间戳转换为日期类型
	 返回自1970以来的时间
	 
	 @param timestamp 时间戳(1147763686)
	 @return 
	//public static DateTime TimeStampToDate(uint timestamp)
	//{
	*/

	//    return TimeZoneInfo.ConvertTimeToUtc(DateTime.Now.AddSeconds(timestamp));
	//}
}