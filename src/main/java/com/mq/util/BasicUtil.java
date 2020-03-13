package com.mq.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

public class BasicUtil {

	private static final Logger LOG = LoggerFactory.getLogger(BasicUtil.class);

	public static Properties fetchProperties() {
		Properties properties = new Properties();
		try {
			File file = ResourceUtils.getFile("classpath:application.properties");
			InputStream in = new FileInputStream(file);
			properties.load(in);
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
		return properties;
	}

	public static void runEnvInfo() {
		Properties prop = fetchProperties();
		LOG.info("----------------------------------------------------");
		LOG.info("App name='{}'", prop.getProperty("app.name", "Active MQ"));
		LOG.info("App version='{}'", prop.getProperty("app.verion", "Version 1.0.0"));
		LOG.info("----------------------------------------------------");
	}

	public static String getUniqueId() {
		return UUID.randomUUID().toString();
	}
}
