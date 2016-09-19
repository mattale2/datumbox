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
package com.datumbox.framework.common.dataobjects;

import java.util.List;

/**
 * Abstract class for every DataStructure which internally uses a List
 * Object.
 * 
 * @author Vasilis Vryniotis <bbriniotis@datumbox.com>
 * @param <T>
 */
public abstract class AbstractDataStructureList<T extends List<?>> extends AbstractDataStructureCollection<T> {
    
    /**
     * Public constructor which takes as argument the appropriate Java collection.
     * 
     * @param data 
     */
    public AbstractDataStructureList(T data) {
        super(data);
    }
    
}
