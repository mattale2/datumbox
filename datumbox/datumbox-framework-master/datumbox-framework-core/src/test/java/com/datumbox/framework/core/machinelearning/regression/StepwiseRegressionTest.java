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
package com.datumbox.framework.core.machinelearning.regression;

import com.datumbox.framework.common.Configuration;
import com.datumbox.framework.common.dataobjects.Dataframe;
import com.datumbox.framework.common.dataobjects.Record;
import com.datumbox.framework.common.dataobjects.TypeInference;
import com.datumbox.framework.core.machinelearning.datatransformation.DummyXYMinMaxNormalizer;
import com.datumbox.framework.tests.Constants;
import com.datumbox.framework.tests.Datasets;
import com.datumbox.framework.tests.abstracts.AbstractTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test cases for StepwiseRegression.
 *
 * @author Vasilis Vryniotis <bbriniotis@datumbox.com>
 */
public class StepwiseRegressionTest extends AbstractTest {

    /**
     * Test of validate method, of class StepwiseRegression.
     */
    @Test
    public void testValidate() {
        logger.info("validate");
        
        Configuration conf = Configuration.getConfiguration();
        
        Dataframe[] data = Datasets.regressionNumeric(conf);
        
        Dataframe trainingData = data[0];
        Dataframe validationData = data[1];
        
        String dbName = this.getClass().getSimpleName();
        
        DummyXYMinMaxNormalizer df = new DummyXYMinMaxNormalizer(dbName, conf);
        df.fit_transform(trainingData, new DummyXYMinMaxNormalizer.TrainingParameters());
        
        StepwiseRegression instance = new StepwiseRegression(dbName, conf);
        
        StepwiseRegression.TrainingParameters param = new StepwiseRegression.TrainingParameters();
        param.setAout(0.05);
        param.setRegressionClass(MatrixLinearRegression.class);
        
        MatrixLinearRegression.TrainingParameters trainingParams = new MatrixLinearRegression.TrainingParameters();
        param.setRegressionTrainingParameters(trainingParams);
                
        instance.fit(trainingData, param);
        
        df.denormalize(trainingData);
        
        
        instance.close();
        df.close();
        //instance = null;
        //df = null;
        
        df = new DummyXYMinMaxNormalizer(dbName, conf);
        df.transform(validationData);
        
        instance = new StepwiseRegression(dbName, conf);
        instance.validate(validationData);
        
        df.denormalize(validationData);
        
        for(Record r : validationData) {
            Assert.assertEquals(TypeInference.toDouble(r.getY()), TypeInference.toDouble(r.getYPredicted()), Constants.DOUBLE_ACCURACY_HIGH);
        }
        
        df.delete();
        instance.delete();
        
        trainingData.delete();
        validationData.delete();
    }


}
