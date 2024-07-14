package com.micro.iotclouds.communication.admin;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动程序
 * 
 * @author admin
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
//@MapperScan({"com.mkst.**.mapper","com.micro.**.mapper"})
@ComponentScan({ "com.micro", "com.mkst" })
//@ServletComponentScan
//@CrossOrigin
//@NacosPropertySource(dataId = "communication-video-flv2output-nacos-config", autoRefreshed = true) 
public class RtmpServerApplication {
//
//	@Autowired
//	static MyLiveServer objMyLiveServer;
	public static class CustomGenerator implements BeanNameGenerator {

		@Override
		public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
			return definition.getBeanClassName();
		}
	}

	public static void main(String[] args) {
		// System.setProperty("spring.devtools.restart.enabled", "false");
		SpringApplication.run(RtmpServerApplication.class, args);
		StringBuilder sb = new StringBuilder();
		sb.append("\r\n======================================================================\r\n");
//		sb.append("\r\n    欢迎使用 " + Global.getName() + " Version: " + Global.getVersion()
//				+ "- Powered By http://www.szmkst.com\r\n");
		sb.append("\r\n======================================================================\r\n");
		System.out.println(sb.toString());
//		new Thread(new SegmentVideoRecorder()).start();
		// MyLiveServer objMyLiveServer = new MyLiveServer();
//		try {
//			objMyLiveServer.startLiveServer();
//		} catch (Exception e) {
//			System.out.println("启动直播服务异常：" + e.getMessage());
//		}
		// 查询设备协议加到redis
//       boolean addSuccess = commDeviceProtocolsService.selectAllDeviceProtocolsList();
//       if(addSuccess) {
//    	   sb = new StringBuilder();
//           sb.append("\r\n======================================================================\r\n");
//           sb.append("\r\n    增加设备协议列表到redis成功。    \r\n");
//           sb.append("\r\n======================================================================\r\n");
//           System.out.println(sb.toString());
//       }
	}

//	private CorsConfiguration buildConfig() {
//		CorsConfiguration corsConfiguration = new CorsConfiguration();
//		corsConfiguration.addAllowedOrigin("*");
//		corsConfiguration.addAllowedHeader("*");
//		corsConfiguration.addAllowedMethod("*");
//		// 这两句不加不能跨域上传文件
//		corsConfiguration.setAllowCredentials(true);
//		corsConfiguration.setMaxAge(3600l);
//		return corsConfiguration;
//	}
//
//	/**
//	 * 跨域过滤器
//	 * 
//	 * @return
//	 */
//	@Bean
//	@ConditionalOnMissingBean
//	public CorsFilter corsFilter() {
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", buildConfig());
//		return new CorsFilter(source);
//	}
}