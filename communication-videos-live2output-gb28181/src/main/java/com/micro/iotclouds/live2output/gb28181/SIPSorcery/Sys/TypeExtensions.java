package com.micro.iotclouds.live2output.gb28181.SIPSorcery.Sys;

public final class TypeExtensions
{

	public static final char[] codes = { ',', '\'', ';', ':', '/', '?', '<', '>', '.', '#', '%','&','?', '^', '\\', '@', '*', '~', '`', '$', '{', '}', '[', ']','"'};
	/**   
	 非法字符转换  
	   
	 @param str 携带(特殊字符)字符串  
	 @return   
	*/
//C# TO JAVA CONVERTER TODO TASK: Extension methods are not available in Java:
//ORIGINAL LINE: public static string Replace(this string str)
	public static String Replace(String str)
	{
		for (int i = 0; i < codes.length; i++)
		{
			str = str.replace(codes[i], Character.MIN_VALUE);
		}

		return str;
	}

}