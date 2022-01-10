package com.attack.log4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.filter.ForwardedHeaderFilter;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
 import javax.annotation.PostConstruct;
import javax.sql.DataSource;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
 public class Log4jApplication {

	@Autowired  DataSource ds;

	@Autowired  MongoTemplate mongoTemplate;
	@PostConstruct
	public void init() throws SQLException {
		DatabaseMetaData databaseMetaData = ds.getConnection().getMetaData();
		ResultSet resultSet = databaseMetaData.getTables(null, null, null, new String[] {"TABLE"});
		while (resultSet.next()) {
			String name = resultSet.getString("TABLE_NAME");
			String schema = resultSet.getString("TABLE_SCHEM");
			log.info(name + " on schema " + schema);
		}
		log.info("Mongo db details --------------->>");
		mongoTemplate.getDb().listCollections().forEach( c->{
			c.keySet().forEach( k -> {
				log.info("{} ---> {} ", k, c.get(k));
			});
		});
	}

	private final int a =10;
	private final int b=12;
	 static Logger log = LogManager.getLogger(Log4jApplication.class.getName());
	public static void main(final String[] args) {
		SpringApplication.run(Log4jApplication.class, args);

 	}

}
