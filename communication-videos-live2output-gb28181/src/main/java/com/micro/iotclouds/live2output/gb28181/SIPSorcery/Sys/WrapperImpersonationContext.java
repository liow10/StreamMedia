import NUnit.Framework.*;

// ============================================================================
// FileName: WrapperImpersonationContext.cs
//
// Description:
// Creates a Windows impersonation context for a specified set of login credentials.
//
// Author(s):
// Aaron Clauson
//
// History:
// 18 Jul 2010  Aaron Clauson   Created from http://www.vanotegem.nl/PermaLink,guid,36633846-2eca-40fe-9957-2859d8a244dc.aspx.
//
// License: 
// Public domain.


//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if UNITTEST
//#endif

public class WrapperImpersonationContext
{
	public static native boolean LogonUser(String lpszUsername, String lpszDomain, String lpszPassword, int dwLogonType, int dwLogonProvider, RefObject<IntPtr> phToken);
	static
	{
		System.loadLibrary("advapi32.dll");
	}

	public static native boolean CloseHandle(IntPtr handle);
	static
	{
		System.loadLibrary("kernel32.dll");
	}

	private static final int LOGON32_PROVIDER_DEFAULT = 0;
	private static final int LOGON32_LOGON_INTERACTIVE = 2;

	private String m_Domain;
	private String m_Password;
	private String m_Username;
	private IntPtr m_Token = new IntPtr();

 //   private WindowsImpersonationContext m_Context = null;

	private boolean privateIsInContext;
	protected final boolean getIsInContext()
	{
		return privateIsInContext;
	}
	//{
	//    get { return m_Context != null; }
	//}

	public WrapperImpersonationContext(String domain, String username, String password)
	{
		m_Domain = domain;
		m_Username = username;
		m_Password = password;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[PermissionSetAttribute(SecurityAction.Demand, Name = "FullTrust")]
	public final void Enter()
	{
		if(this.getIsInContext())
		{
			return;
		}
		m_Token = new IntPtr(0);
		try
		{
			m_Token = IntPtr.Zero;
			RefObject<IntPtr> tempRef_m_Token = new RefObject<IntPtr>(m_Token);
			boolean logonSuccessfull = LogonUser(m_Username, m_Domain, m_Password, LOGON32_LOGON_INTERACTIVE, LOGON32_PROVIDER_DEFAULT, tempRef_m_Token);
			m_Token = tempRef_m_Token.argvalue;
			if(logonSuccessfull == false)
			{
				int error = Marshal.GetLastWin32Error();
				throw new Win32Exception(error);
			}
			WindowsIdentity identity = new WindowsIdentity(m_Token);
		 //   m_Context = identity.Impersonate();
		}
		catch (java.lang.Exception e)
		{
			throw e;
		}
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[PermissionSetAttribute(SecurityAction.Demand, Name = "FullTrust")]
	public final void Leave()
	{
		if(this.getIsInContext() == false)
		{
			return;
		}
	   // m_Context.Undo();

		if(m_Token != IntPtr.Zero)
		{
			CloseHandle(m_Token);
		}
	  //  m_Context = null;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#region Unit testing.

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	//#if UNITTEST

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[TestFixture]
	public static class WrapperImpersonationContextUnitTest
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
			System.out.println(System.Reflection.MethodBase.GetCurrentMethod().getName());
		}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
		//[Test]
		public final void EnterContextUnitTest()
		{
			System.out.println(System.Reflection.MethodBase.GetCurrentMethod().getName());

			System.out.println("identity=" + WindowsIdentity.GetCurrent().getName() + ".");
//C# TO JAVA CONVERTER NOTE: The following 'using' block is replaced by its Java equivalent:
//			using (StreamReader sr = new StreamReader("c:\\temp\\impersonationtest.txt"))
			StreamReader sr = new StreamReader("c:\\temp\\impersonationtest.txt");
			try
			{
				System.out.println(sr.ReadToEnd());
			}
			finally
			{
				sr.dispose();
			}

			WrapperImpersonationContext context = new WrapperImpersonationContext(null, "sipsorcery-appsvr", "password");
			context.Enter();
			System.out.println("identity=" + WindowsIdentity.GetCurrent().getName() + ".");

//C# TO JAVA CONVERTER NOTE: The following 'using' block is replaced by its Java equivalent:
//			using (StreamReader sr = new StreamReader("c:\\temp\\impersonationtest.txt"))
			StreamReader sr = new StreamReader("c:\\temp\\impersonationtest.txt");
			try
			{
				System.out.println(sr.ReadToEnd());
			}
			finally
			{
				sr.dispose();
			}

			context.Leave();
			System.out.println("identity=" + WindowsIdentity.GetCurrent().getName() + ".");
		}
	}

	//#endif

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#endregion
}