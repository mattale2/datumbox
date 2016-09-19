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
package com.datumbox.framework.core.statistics.parametrics.onesample;

import com.datumbox.framework.common.dataobjects.FlatDataCollection;
import com.datumbox.framework.tests.abstracts.AbstractTest;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Test cases for LjungBox.
 *
 * @author Vasilis Vryniotis <bbriniotis@datumbox.com>
 */
public class LjungBoxTest extends AbstractTest {
    
    /**
     * Test of testAutocorrelation method, of class LjungBox.
     */
    @Test
    public void testTestAutocorrelation() {
        logger.info("testAutocorrelation");
        FlatDataCollection pkList = new FlatDataCollection(Arrays.asList(new Object[]{0.810,0.631,0.469,0.349}));
        int n = 100;
        double aLevel = 0.05;
        boolean expResult = true;
        boolean result = LjungBox.testAutocorrelation(pkList, n, aLevel);
        assertEquals(expResult, result);
    }
    
}
