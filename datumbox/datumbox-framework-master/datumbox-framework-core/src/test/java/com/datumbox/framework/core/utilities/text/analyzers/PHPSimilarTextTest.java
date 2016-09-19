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
package com.datumbox.framework.core.utilities.text.analyzers;

import com.datumbox.framework.tests.Constants;
import com.datumbox.framework.tests.abstracts.AbstractTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test cases for PHPSimilarText.
 *
 * @author Vasilis Vryniotis <bbriniotis@datumbox.com>
 */
public class PHPSimilarTextTest extends AbstractTest {
    
    /**
     * Test of similarityChars method, of class PHPSimilarText.
     */
    @Test
    public void testSimilarityChars() {
        logger.info("similarityChars");
        String txt1 = "this is a fine text";
        String txt2 = "this is a great document";
        int expResult = 12;
        int result = PHPSimilarText.similarityChars(txt1, txt2);
        assertEquals(expResult, result);
    }

    /**
     * Test of similarityPercentage method, of class PHPSimilarText.
     */
    @Test
    public void testSimilarityPercentage() {
        logger.info("similarityPercentage");
        String txt1 = "this is a fine text";
        String txt2 = "this is a great document";
        double expResult = 55.813953488372;
        double result = PHPSimilarText.similarityPercentage(txt1, txt2);
        assertEquals(expResult, result, Constants.DOUBLE_ACCURACY_HIGH);
    }
    
}
