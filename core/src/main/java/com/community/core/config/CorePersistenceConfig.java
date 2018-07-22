package com.community.core.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.broadleafcommerce.common.extensibility.context.merge.Merge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.MapFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

/**
 * 
 * 
 * @author Phillip Verheyden (phillipuniverse)
 */
@Configuration
public class CorePersistenceConfig {

	private JndiDataSourceLookup lookup = new JndiDataSourceLookup();
	
	
	@Lazy
	@Autowired
    @Qualifier("webDS")
    DataSource webDS;

	@Lazy
    @Autowired
    @Qualifier("webSecureDS")
    DataSource webSecureDS;

	@Lazy
    @Autowired
    @Qualifier("webStorageDS")
    DataSource webStorageDS;
	
	@Lazy
    @Primary
    @Bean(name = "webDS", destroyMethod="")
	public DataSource webDSDataSource() throws IllegalArgumentException, NamingException {
		return lookup.getDataSource("jdbc/web");
	}
    
	@Lazy
    @Bean(name = "webSecureDS", destroyMethod="")
	public DataSource webSecureDSDataSource() throws IllegalArgumentException, NamingException {
		return lookup.getDataSource("jdbc/secure");
	}
    
	@Lazy
    @Bean(name = "webStorageDS", destroyMethod="")
	public DataSource webStorageDSDataSource() throws IllegalArgumentException, NamingException {
		return lookup.getDataSource("jdbc/storage");
	}
    
    

    @Bean
    public MapFactoryBean blMergedDataSources() throws Exception {
        MapFactoryBean mapFactoryBean = new MapFactoryBean();
        Map<String, DataSource> sourceMap = new HashMap<>();
        sourceMap.put("jdbc/web", webDS);
        sourceMap.put("jdbc/webSecure", webSecureDS);
        sourceMap.put("jdbc/cmsStorage", webStorageDS);
        mapFactoryBean.setSourceMap(sourceMap);

        return mapFactoryBean;
    }
    
    @Merge(targetRef = "blMergedPersistenceXmlLocations", early = true)
    public List<String> corePersistenceXmlLocations() {
        return Arrays.asList("classpath*:/META-INF/persistence-core.xml");
    }
    
    @Merge(targetRef = "blMergedEntityContexts", early = true)
    public List<String> entityConfigurationLocations() {
        return Arrays.asList("classpath:applicationContext-entity.xml");
    }
}
