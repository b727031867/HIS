package com.gxf.his;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author GXF
 * @date 2019-10-13 12:03:26
 * MapperScan只需要扫描子类则会自动注入父类Mapper
 * EnableAsync 启用异步任务 EnableTransactionManagement 启用事务
 * EnableScheduling 启用计划任务
 **/
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
@MapperScan("com.gxf.his.mapper.*")
@SpringBootApplication
public class HisApplication {

	public static void main(String[] args) {
		SpringApplication.run(HisApplication.class, args);
	}

	/**
	 * 设置异步定时任务的线程数量为10 默认为1
	 *
	 * @return 线程池
	 */
	@Bean
	public TaskScheduler taskScheduler() {
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(10);
		return taskScheduler;
	}
}
