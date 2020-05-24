package com.betroix.tiktok;

import com.betroix.tiktok.service.EmulatorIOResolver;
import com.betroix.tiktok.service.EmulatorService;
import com.betroix.tiktok.service.IEmulatorService;
import com.github.ulisesbocchio.jar.resources.JarResourceLoader;

import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.target.CommonsPool2TargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ResourceLoader;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

@SpringBootApplication
@ComponentScan("com.betroix.tiktok.controller")
@ComponentScan("com.betroix.tiktok.service")
public class TiktokApplication {

	public static void main(String[] args) {
		SpringApplicationBuilder application = new SpringApplicationBuilder().sources(TiktokApplication.class)
				.resourceLoader(new JarResourceLoader());
		application.run(args);
	}

	@Bean
	public CommonsPool2TargetSource emulatorPooledTargetSource() {
		CommonsPool2TargetSource commonsPoolTargetSource = new CommonsPool2TargetSource();
		commonsPoolTargetSource.setTargetBeanName("emulatorService");
		commonsPoolTargetSource.setTargetClass(IEmulatorService.class);
		commonsPoolTargetSource.setMaxSize(5);
		return commonsPoolTargetSource;
	}

	@Bean
	@Autowired
	public ProxyFactoryBean emulatorProxyFactoryBean(CommonsPool2TargetSource emulatorPooledTargetSource) {
		ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
		proxyFactoryBean.setTargetSource(emulatorPooledTargetSource);
		return proxyFactoryBean;
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@Autowired
	public IEmulatorService emulatorService(ResourceLoader resourceLoader, EmulatorIOResolver emulatorIOResolver) {
		return new EmulatorService(resourceLoader, emulatorIOResolver);
	}
}
