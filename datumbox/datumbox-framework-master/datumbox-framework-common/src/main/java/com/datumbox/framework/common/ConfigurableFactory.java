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

import com.datumbox.framework.common.interfaces.Configurable;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/**
 * Factory that initializes and returns the Configurable objects based on the datumbox.config.properties file.
 * 
 * @author Vasilis Vryniotis <bbriniotis@datumbox.com>
 */
public class ConfigurableFactory {
    
    /**
     * Initializes the Configuration Object based on the config file.
     * 
     * @param <C>
     * @param klass
     * @return 
     */
    public static <C extends Configurable> C getConfiguration(Class<C> klass) {
        //Initialize config object
        C conf;
        try {
            Constructor<C> constructor = klass.getDeclaredConstructor();
            constructor.setAccessible(true);
            conf = constructor.newInstance();
        } 
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
            throw new RuntimeException(ex);
        }
        
        Properties properties = new Properties();
        
        ClassLoader cl = ConfigurableFactory.class.getClassLoader();
        
        //Load default properties from jar
        try (InputStream in = cl.getResourceAsStream("datumbox.config.default.properties")) {
            properties.load(in);
        }
        catch(IOException ex) {
            throw new UncheckedIOException(ex);
        }
        
        //Look for user defined properties
        if(cl.getResource("datumbox.config.properties")!=null) {
            //Override the default if they exist
            try (InputStream in = cl.getResourceAsStream("datumbox.config.properties")) {
                properties.load(in);
            }
            catch(IOException ex) {
                throw new UncheckedIOException(ex);
            }
        }
        
        conf.load(properties);
        
        return conf;
    }
}
