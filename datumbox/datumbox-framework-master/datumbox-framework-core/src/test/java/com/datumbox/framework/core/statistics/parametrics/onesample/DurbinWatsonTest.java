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

import com.datumbox.framework.common.dataobjects.FlatDataList;
import com.datumbox.framework.tests.abstracts.AbstractTest;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Test cases for DurbinWatson.
 *
 * @author Vasilis Vryniotis <bbriniotis@datumbox.com>
 */
public class DurbinWatsonTest extends AbstractTest {
   
    /**
     * Test of test method, of class DurbinWatson.
     */
    @Test
    public void testTest() {
        logger.info("test");
        FlatDataList errorList = new FlatDataList(Arrays.asList(new Object[]{-2.7755575615629e-15,-2.0816681711722e-15,-2.2204460492503e-16,-1.9984014443253e-15,3.3306690738755e-16,-4.9960036108132e-16,2.2204460492503e-16,4.4408920985006e-16,2.4424906541753e-15,-1.2212453270877e-15,-2.2204460492503e-16,-2.7755575615629e-15,1.3322676295502e-15,1.6653345369377e-15,-1.8318679906315e-15,-1.2212453270877e-15,2.3314683517128e-15,8.8817841970013e-16,-2.2204460492503e-15,-1.2212453270877e-15,-3.1641356201817e-15,-6.6613381477509e-16,2.7755575615629e-16,-5.5511151231258e-16,-1.193489751472e-15,1.9984014443253e-15,-1.1657341758564e-15,-6.1062266354384e-16,2.2204460492503e-16,-1.1657341758564e-15,-4.4408920985006e-16,-2.3314683517128e-15,-5.5511151231258e-16,4.4408920985006e-16,6.6613381477509e-16,1.1102230246252e-15,2.2204460492503e-15,-6.6613381477509e-16,2.7755575615629e-16,-9.4368957093138e-16,1.3322676295502e-15,-1.3322676295502e-15,6.6613381477509e-16,1.8873791418628e-15,-2.9976021664879e-15,-1.8873791418628e-15,-1.4710455076283e-15,-2.1649348980191e-15,-7.2164496600635e-16,-1.498801083244e-15,2.2204460492503e-15,2.2204460492503e-15,6.6613381477509e-16,6.6613381477509e-16,-1.1379786002408e-15,2.9976021664879e-15,-8.8817841970013e-16,-1.5473733405713e-15,9.9920072216264e-16,2.4424906541753e-15,-1.7763568394003e-15,-2.7755575615629e-16,-7.7715611723761e-16,1.8873791418628e-15,-1.1657341758564e-15,1.6653345369377e-16,4.4408920985006e-16,4.4408920985006e-16,1.7763568394003e-15,-3.5249581031849e-15,-6.1062266354384e-16,-1.5300261058115e-15,1.8873791418628e-15,1.7763568394003e-15,-2.2204460492503e-16,-3.885780586188e-16,-6.6613381477509e-16,5.5511151231258e-17,-1.3877787807814e-15,1.1102230246252e-15,1.8873791418628e-15,2.6645352591004e-15,1.6653345369377e-15,1.6653345369377e-15,2.2204460492503e-15,-4.1633363423443e-15,6.6613381477509e-16,1.3322676295502e-15,-1.6930901125534e-15,-1.5404344466674e-15,2.3314683517128e-15,5.5511151231258e-16,1.8873791418628e-15,-8.3266726846887e-16,-2.2204460492503e-16,-4.2743586448069e-15,1.6653345369377e-15,2.4424906541753e-15,-5.5511151231258e-17,-3.3306690738755e-16}));
        int k = 10;
        boolean is_twoTailed = true;
        double aLevel = 0.05;
        boolean expResult = false;
        boolean result = DurbinWatson.test(errorList, k, is_twoTailed, aLevel);
        assertEquals(expResult, result);
    }
 
}
