package com.micro.iotclouds.live2output.gb28181.SIPSorcery.Sys;

import NUnit.Framework.*;

// ============================================================================
// FileName: SIPSorceryPerformanceMonitor.cs
//
// Description:
// Contains helper and configuration functions to allow application wide use of
// Windows performance monitor counters to monitor application metrics.
//
// Author(s):
// Aaron Clauson
//
// History:
// 27 Jul 2010  Aaron Clauson   Created.
//
// License: 
// BSD 3-Clause "New" or "Revised" License, see included LICENSE.md file.
//


//using GB28181.Logger4Net;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if UNITTEST
//#endif

public class SIPSorceryPerformanceMonitor
{
	private static final String PERFORMANCE_COUNTER_CATEGORY_NAME = "SIPSorcery";

	public static final String PROXY_PREFIX = "Proxy";
	public static final String REGISTRAR_PREFIX = "Registrar";
	public static final String REGISTRATION_AGENT_PREFIX = "RegistrationAgent";

	public static final String SIP_TRANSPORT_STUN_REQUESTS_PER_SECOND_SUFFIX = "STUNRequestsPerSecond";
	public static final String SIP_TRANSPORT_SIP_REQUESTS_PER_SECOND_SUFFIX = "SIPRequestsPerSecond";
	public static final String SIP_TRANSPORT_SIP_RESPONSES_PER_SECOND_SUFFIX = "SIPResponsesPerSecond";
	public static final String SIP_TRANSPORT_SIP_BAD_MESSAGES_PER_SECOND_SUFFIX = "SIPBadMessagesPerSecond";

	public static final String PROXY_STUN_REQUESTS_PER_SECOND = PROXY_PREFIX + SIP_TRANSPORT_STUN_REQUESTS_PER_SECOND_SUFFIX;
	public static final String PROXY_SIP_REQUESTS_PER_SECOND = PROXY_PREFIX + SIP_TRANSPORT_SIP_REQUESTS_PER_SECOND_SUFFIX;
	public static final String PROXY_SIP_RESPONSES_PER_SECOND = PROXY_PREFIX + SIP_TRANSPORT_SIP_RESPONSES_PER_SECOND_SUFFIX;
	public static final String PROXY_SIP_BAD_MESSAGES_PER_SECOND = PROXY_PREFIX + SIP_TRANSPORT_SIP_BAD_MESSAGES_PER_SECOND_SUFFIX;

	public static final String REGISTRAR_STUN_REQUESTS_PER_SECOND = REGISTRAR_PREFIX + SIP_TRANSPORT_STUN_REQUESTS_PER_SECOND_SUFFIX;
	public static final String REGISTRAR_SIP_REQUESTS_PER_SECOND = REGISTRAR_PREFIX + SIP_TRANSPORT_SIP_REQUESTS_PER_SECOND_SUFFIX;
	public static final String REGISTRAR_SIP_RESPONSES_PER_SECOND = REGISTRAR_PREFIX + SIP_TRANSPORT_SIP_RESPONSES_PER_SECOND_SUFFIX;
	public static final String REGISTRAR_SIP_BAD_MESSAGES_PER_SECOND = REGISTRAR_PREFIX + SIP_TRANSPORT_SIP_BAD_MESSAGES_PER_SECOND_SUFFIX;
	public static final String REGISTRAR_REGISTRATION_REQUESTS_PER_SECOND = REGISTRAR_PREFIX + "RegistersReceivedPerSecond";

	public static final String REGISTRATION_AGENT_STUN_REQUESTS_PER_SECOND = REGISTRATION_AGENT_PREFIX + SIP_TRANSPORT_STUN_REQUESTS_PER_SECOND_SUFFIX;
	public static final String REGISTRATION_AGENT_SIP_REQUESTS_PER_SECOND = REGISTRATION_AGENT_PREFIX + SIP_TRANSPORT_SIP_REQUESTS_PER_SECOND_SUFFIX;
	public static final String REGISTRATION_AGENT_SIP_RESPONSES_PER_SECOND = REGISTRATION_AGENT_PREFIX + SIP_TRANSPORT_SIP_RESPONSES_PER_SECOND_SUFFIX;
	public static final String REGISTRATION_AGENT_SIP_BAD_MESSAGES_PER_SECOND = REGISTRATION_AGENT_PREFIX + SIP_TRANSPORT_SIP_BAD_MESSAGES_PER_SECOND_SUFFIX;
	public static final String REGISTRATION_AGENT_REGISTRATIONS_PER_SECOND = REGISTRATION_AGENT_PREFIX + "RegistrationsPerSecond";

	//private static ILog logger = AppState.logger;

	private static boolean m_sipsorceryCategoryReady = false;

	//private static Dictionary<string, PerformanceCounterType> m_counterNames = new Dictionary<string, PerformanceCounterType>()
	//{

	//    // Proxy
	//    { PROXY_STUN_REQUESTS_PER_SECOND, PerformanceCounterType.RateOfCountsPerSecond32 },
	//    { PROXY_SIP_REQUESTS_PER_SECOND, PerformanceCounterType.RateOfCountsPerSecond32 },
	//    { PROXY_SIP_RESPONSES_PER_SECOND, PerformanceCounterType.RateOfCountsPerSecond32 },
	//    { PROXY_SIP_BAD_MESSAGES_PER_SECOND, PerformanceCounterType.RateOfCountsPerSecond32 },

	//    // Registrar
	//    { REGISTRAR_STUN_REQUESTS_PER_SECOND, PerformanceCounterType.RateOfCountsPerSecond32 },
	//    { REGISTRAR_SIP_REQUESTS_PER_SECOND, PerformanceCounterType.RateOfCountsPerSecond32 },
	//    { REGISTRAR_SIP_RESPONSES_PER_SECOND, PerformanceCounterType.RateOfCountsPerSecond32 },
	//    { REGISTRAR_SIP_BAD_MESSAGES_PER_SECOND, PerformanceCounterType.RateOfCountsPerSecond32 },
	//    { REGISTRAR_REGISTRATION_REQUESTS_PER_SECOND, PerformanceCounterType.RateOfCountsPerSecond32 },

	//    // Registration agent counters.
	//    { REGISTRATION_AGENT_STUN_REQUESTS_PER_SECOND, PerformanceCounterType.RateOfCountsPerSecond32 },
	//    { REGISTRATION_AGENT_SIP_REQUESTS_PER_SECOND, PerformanceCounterType.RateOfCountsPerSecond32 },
	//    { REGISTRATION_AGENT_SIP_RESPONSES_PER_SECOND, PerformanceCounterType.RateOfCountsPerSecond32 },
	//    { REGISTRATION_AGENT_SIP_BAD_MESSAGES_PER_SECOND, PerformanceCounterType.RateOfCountsPerSecond32 },
	//    { REGISTRATION_AGENT_REGISTRATIONS_PER_SECOND, PerformanceCounterType.RateOfCountsPerSecond32 }
	//};

	//private static Dictionary<string, PerformanceCounter> m_counters = new Dictionary<string, PerformanceCounter>();

	static
	{
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
		ThreadPool.QueueUserWorkItem(delegate
		{
			CheckCounters();
		}
	   );
	}

	public static boolean Initialise()
	{
		CheckCounters();
		return m_sipsorceryCategoryReady;
	}

	public static void IncrementCounter(String counterName)
	{
		IncrementCounter(counterName, 1);
	}

	public static void IncrementCounter(String counterName, int incrementBy)
	{
		//try
		//{
		//    if (m_sipsorceryCategoryReady)
		//    {
		//        if (m_counters.ContainsKey(counterName))
		//        {
		//            m_counters[counterName].IncrementBy(incrementBy);
		//        }
		//        else
		//        {
		//            PerformanceCounter counter = new PerformanceCounter(PERFORMANCE_COUNTER_CATEGORY_NAME, counterName, false);
		//            m_counters.Add(counterName, counter);
		//            counter.IncrementBy(incrementBy);
		//        }
		//    }
		//}
		//catch (Exception excp)
		//{
		//    logger.Error("Exception SIPSorceryPerformanceMonitor IncrementCounter (" + counterName + "). " + excp.Message);
		//}
	}

	private static void CheckCounters()
	{
		//try
		//{
		//    if (!PerformanceCounterCategory.Exists(PERFORMANCE_COUNTER_CATEGORY_NAME))
		//    {
		//        CreateCategory();
		//    }
		//    else
		//    {
		//        foreach (var counter in m_counterNames)
		//        {
		//            if (!PerformanceCounterCategory.CounterExists(counter.Key, PERFORMANCE_COUNTER_CATEGORY_NAME))
		//            {
		//                CreateCategory();
		//                break;
		//            }
		//        }
		//    }

		//    m_sipsorceryCategoryReady = true;
		//}
		//catch (Exception excp)
		//{
		//    logger.Error("Exception SIPSorceryPerformanceMonitor CheckCounters. " + excp.Message);
		//}
	}

	private static void CreateCategory()
	{
		//try
		//{
		//    logger.Debug("SIPSorceryPerformanceMonitor creating " + PERFORMANCE_COUNTER_CATEGORY_NAME + " category.");

		//    if (PerformanceCounterCategory.Exists(PERFORMANCE_COUNTER_CATEGORY_NAME))
		//    {
		//        PerformanceCounterCategory.Delete(PERFORMANCE_COUNTER_CATEGORY_NAME);
		//    }

		//     CounterCreationDataCollection ccdc = new CounterCreationDataCollection();

		//     foreach (var counter in m_counterNames)
		//     {
		//         CounterCreationData counterData = new CounterCreationData();
		//         counterData.CounterType = counter.Value;
		//         counterData.CounterName = counter.Key;
		//         ccdc.Add(counterData);

		//         logger.Debug("SIPSorceryPerformanceMonitor added counter " + counter.Key + ".");
		//     }

		//    PerformanceCounterCategory.Create(PERFORMANCE_COUNTER_CATEGORY_NAME, "SIP Sorcery performance counters", PerformanceCounterCategoryType.SingleInstance, ccdc);
		//}
		//catch (Exception excp)
		//{
		//    logger.Error("Exception SIPSorceryPerformanceMonitor CreateCategory. " + excp.Message);
		//    throw;
		//}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Unit testing.

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		//#if UNITTEST

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[TestFixture]
	public static class SIPDialPlanUnitTest
	{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[TestFixtureSetUp]
		public final void Init()
		{
		}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[TestFixtureTearDown]
		public final void dispose()
		{
		}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[Test]
		public final void SampleTest()
		{
			System.out.println("--> " + System.Reflection.MethodBase.GetCurrentMethod().getName());

			Assert.IsTrue(true, "True was false.");

			System.out.println("---------------------------------");
		}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[Test]
		public final void IncrementCounterUnitTest()
		{
			System.out.println("--> " + System.Reflection.MethodBase.GetCurrentMethod().getName());

			SIPSorceryPerformanceMonitor.IncrementCounter("UnitTestCounter");

			System.out.println("---------------------------------");
		}
	}

		//#endif

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

}