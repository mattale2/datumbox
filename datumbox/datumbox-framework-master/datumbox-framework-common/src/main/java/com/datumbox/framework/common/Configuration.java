/**
 * Copyright (C) 2013-2016 Vasilis Vryniotis <bbriniotis@datumbox.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.datumbox.framework.common;

import com.datumbox.framework.common.concurrency.ConcurrencyConfiguration;
import com.datumbox.framework.common.interfaces.Configurable;
import com.datumbox.framework.common.persistentstorage.interfaces.DatabaseConfiguration;

import java.util.Properties;

/**
 * The main Configuration object of the framework which information about the Database, the concurrency etc.
 *
 * @author vvryniotis
 */
public class Configuration implements Configurable {
    
    private DatabaseConfiguration dbConfig;
    private ConcurrencyConfiguration concurrencyConfig;
    
    /**
     * Protected constructor. Use the static getConfiguration method instead.
     */
    protected Configuration() {
        
    }
    
    /**
     * Getter for the Database Configuration object.
     * 
     * @return 
     */
    public DatabaseConfiguration getDbConfig() {
        return dbConfig;
    }
    
    /**
     * Setter for the Database Configuration object.
     * 
     * @param dbConfig 
     */
    public void setDbConfig(DatabaseConfiguration dbConfig) {
        this.dbConfig = dbConfig;
    }
    
    /**
     * Getter for the Concurrency Configuration object.
     * 
     * @return 
     */
    public ConcurrencyConfiguration getConcurrencyConfig() {
        return concurrencyConfig;
    }
    
    /**
     * Setter for the Concurrency Configuration object.
     * 
     * @param concurrencyConfig 
     */
    public void setConcurrencyConfig(ConcurrencyConfiguration concurrencyConfig) {
        this.concurrencyConfig = concurrencyConfig;
    }
    
    /** {@inheritDoc} */
    @Override
    public void load(Properties properties) {
        String dbConfigClassName = properties.getProperty("dbConfig.className");
        try {
            dbConfig = ConfigurableFactory.getConfiguration((Class<DatabaseConfiguration>) Class.forName(dbConfigClassName));
        }
        catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        concurrencyConfig = ConfigurableFactory.getConfiguration(ConcurrencyConfiguration.class);
    }
    
    /**
     * Creates a new configuration object based on the property file.
     * 
     * @return 
     */
    public static Configuration getConfiguration() {
        return ConfigurableFactory.getConfiguration(Configuration.class);
    }
    
}
