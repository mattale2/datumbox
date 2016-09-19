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
package com.datumbox.framework.development.switchers;

import com.datumbox.framework.development.interfaces.FeatureMark;

import java.lang.annotation.*;

/**
 * Example class for the Mark annotation which is used to mark fields, methods etc 
 * which belong only to specific options.
 * 
 * @author Vasilis Vryniotis <bbriniotis@datumbox.com>
 */
@FeatureMark
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PACKAGE, ElementType.PARAMETER, ElementType.TYPE})
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface ExampleMark { 
    
    /**
     * Parameter that passes the options of the enum.
     * 
     * @return 
     */
    public Example[] options();

}