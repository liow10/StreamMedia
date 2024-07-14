package com.micro.iotclouds.live2output.gb28181.SIPSorcery.Sys;

//-----------------------------------------------------------------------------
// Filename: WCFUtility.cs
//
// Description: Class to provide utility mehtods for working with WCF. 
// 
// History:
// 23 Feb 2010	Aaron Clauson	Created.
//



public class WCFUtility
{
	public static boolean DoesWCFServiceExist(String serviceName)
	{
		//ServiceModelSectionGroup serviceModelSectionGroup = ServiceModelSectionGroup.GetSectionGroup(ConfigurationManager.OpenExeConfiguration(ConfigurationUserLevel.None));
		//foreach (ServiceElement serviceElement in serviceModelSectionGroup.Services.Services)
		//{
		//    if (serviceElement.Name == serviceName)
		//    {
		//        return true;
		//    }
		//}

		return false;
	}
}