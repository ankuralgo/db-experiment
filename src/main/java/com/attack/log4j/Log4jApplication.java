package com.attack.log4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
 import javax.annotation.PostConstruct;
import javax.sql.DataSource;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
 public class Log4jApplication {

	private static Logger log = LogManager.getLogger(Log4jApplication.class.getName());
	@Autowired  DataSource ds;
	@PostConstruct
	public void init() throws SQLException {
		DatabaseMetaData databaseMetaData = ds.getConnection().getMetaData();
		ResultSet resultSet = databaseMetaData.getTables(null, null, null, new String[] {"TABLE"});
		while (resultSet.next()) {
			String name = resultSet.getString("TABLE_NAME");
			String schema = resultSet.getString("TABLE_SCHEM");
			log.info(name + " on schema " + schema);
		}
	}





}
