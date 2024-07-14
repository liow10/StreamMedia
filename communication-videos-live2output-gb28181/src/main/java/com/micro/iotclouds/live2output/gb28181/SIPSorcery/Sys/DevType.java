package GB28181.Sys.XML;

public class DevType
{
	/** 
	 获取设备目录类型
	 
	 @param devId 编码
	 @return 
	*/
	public static DevCataType GetCataType(String devId)
	{
		DevCataType devCata = DevCataType.UnKnown;

		switch (devId.length())
		{
			case 2:
				devCata = DevCataType.ProviceCata;
				break;
			case 4:
				devCata = DevCataType.CityCata;
				break;
			case 6:
				devCata = DevCataType.AreaCata;
				break;
			case 8:
				devCata = DevCataType.BasicUnit;
				break;
			case 20:
				int extId = Integer.parseInt(devId.substring(10, 13));
				if(extId == 200) //ID编码11-13位采用200标识系统ID类型
				{
					devCata = DevCataType.SystemCata;
				}
				else if(extId == 215) //业务分组标识，编码采用D.1中的20位ID格式，扩展215类型代表业务分组
				{
					devCata = DevCataType.BusinessGroupCata;
				}
				else if(extId == 216) //虚拟组织标识，编码采用D.1中的20位ID格式，扩展216类型代表虚拟组织
				{
					devCata = DevCataType.VirtualGroupCata;
				}
				else if(extId == 131 || extId == 132||extId==134||extId==137) //D.1中摄像机，网络摄像机编码
				{
					devCata = DevCataType.Device;
				}
				else
				{
					devCata = DevCataType.Other; //其他类型
				}
				break;
		}
		return devCata;
	}
}