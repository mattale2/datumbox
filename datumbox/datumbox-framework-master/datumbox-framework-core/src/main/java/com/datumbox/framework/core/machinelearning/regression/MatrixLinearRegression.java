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
import com.datumbox.framework.common.dataobjects.MatrixDataframe;
import com.datumbox.framework.common.dataobjects.Record;
import com.datumbox.framework.common.persistentstorage.interfaces.BigMap;
import com.datumbox.framework.common.persistentstorage.interfaces.DatabaseConnector;
import com.datumbox.framework.common.persistentstorage.interfaces.DatabaseConnector.MapType;
import com.datumbox.framework.common.persistentstorage.interfaces.DatabaseConnector.StorageHint;
import com.datumbox.framework.common.utilities.PHPMethods;
import com.datumbox.framework.core.machinelearning.common.abstracts.AbstractTrainer;
import com.datumbox.framework.core.machinelearning.common.abstracts.algorithms.AbstractLinearRegression;
import com.datumbox.framework.core.machinelearning.common.interfaces.StepwiseCompatible;
import com.datumbox.framework.core.statistics.distributions.ContinuousDistributions;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import java.util.HashMap;
import java.util.Map;


/**
 * Performs Linear Regression using Matrices.
 * 
 * WARNING: This class copies the Dataframe to a RealMatrix which forces all of the
 * data to be loaded in memory.
 * 
 * @author Vasilis Vryniotis <bbriniotis@datumbox.com>
 */
public class MatrixLinearRegression extends AbstractLinearRegression<MatrixLinearRegression.ModelParameters, MatrixLinearRegression.TrainingParameters, MatrixLinearRegression.ValidationMetrics> implements StepwiseCompatible {

    /** {@inheritDoc} */
    public static class ModelParameters extends AbstractLinearRegression.AbstractModelParameters {
        private static final long serialVersionUID = 1L;

        /**
         * Feature set
         */
        @BigMap(mapType=MapType.HASHMAP, storageHint=StorageHint.IN_MEMORY, concurrent=false)
        private Map<Object, Integer> featureIds; //list of all the supported features
        
        private Map<Object, Double> featurePvalues; //array with all the pvalues of the features
    
        /** 
         * @param dbc
         * @see AbstractTrainer.AbstractModelParameters#AbstractModelParameters(DatabaseConnector)
         */
        protected ModelParameters(DatabaseConnector dbc) {
            super(dbc);
        }
        
        /**
         * Getter for the mapping of the column names to column ids. The implementation
         * internally converts the data into vectors and as a result we need to 
         * estimate and store the mapping between the column names and their 
         * positions in the array. This mapping is estimated during training.
         * 
         * @return 
         */
        public Map<Object, Integer> getFeatureIds() {
            return featureIds;
        }
        
        /**
         * Setter for the mapping of the column names to column ids.
         * 
         * @param featureIds 
         */
        protected void setFeatureIds(Map<Object, Integer> featureIds) {
            this.featureIds = featureIds;
        }
        
        /**
         * Getter for the p-values of the variables which are estimated during 
         * the regression. 
         * This is NOT always available. Calculated during training ONLY if the model is
         * configured to. It is useful when we perform StepwiseRegression.
         * 
         * @return 
         */
        public Map<Object, Double> getFeaturePvalues() {
            return featurePvalues;
        } 
        
        /**
         * Setter for the p-values of the variables which are estimated during 
         * the regression.
         * 
         * @param featurePvalues 
         */
        protected void setFeaturePvalues(Map<Object, Double> featurePvalues) {
            this.featurePvalues = featurePvalues;
        } 
    } 

    /** {@inheritDoc} */
    public static class TrainingParameters extends AbstractLinearRegression.AbstractTrainingParameters {
        private static final long serialVersionUID = 1L;

    } 
    
    /** {@inheritDoc} */
    public static class ValidationMetrics extends AbstractLinearRegression.AbstractValidationMetrics {
        private static final long serialVersionUID = 1L;
        
    }

    /**
     * Public constructor of the algorithm.
     * 
     * @param dbName
     * @param conf 
     */
    public MatrixLinearRegression(String dbName, Configuration conf) {
        super(dbName, conf, MatrixLinearRegression.ModelParameters.class, MatrixLinearRegression.TrainingParameters.class, MatrixLinearRegression.ValidationMetrics.class);
    }

    /** {@inheritDoc} */
    @Override
    protected void _predictDataset(Dataframe newData) {
        //read model params
        ModelParameters modelParameters = kb().getModelParameters();

        int d = modelParameters.getD()+1; //plus one for the constant
        
        Map<Object, Double> thitas = modelParameters.getThitas();
        Map<Object, Integer> featureIds = modelParameters.getFeatureIds();
        
        RealVector coefficients = new ArrayRealVector(d);
        for(Map.Entry<Object, Double> entry : thitas.entrySet()) {
            Integer featureId = featureIds.get(entry.getKey());
            coefficients.setEntry(featureId, entry.getValue());
        }
        
        Map<Integer, Integer> recordIdsReference = new HashMap<>(); //use a mapping between recordIds and rowIds in Matrix
        MatrixDataframe matrixDataset = MatrixDataframe.parseDataset(newData, recordIdsReference, featureIds);
        
        RealMatrix X = matrixDataset.getX();
        
        RealVector Y = X.operate(coefficients);
        for(Map.Entry<Integer, Record> e : newData.entries()) {
            Integer rId = e.getKey();
            Record r = e.getValue();
            int rowId = recordIdsReference.get(rId);
            newData._unsafe_set(rId, new Record(r.getX(), r.getY(), Y.getEntry(rowId), r.getYPredictedProbabilities()));
        }
        
        //recordIdsReference = null;
        //matrixDataset = null;
    }

    /** {@inheritDoc} */
    @Override
    protected void _fit(Dataframe trainingData) {
        ModelParameters modelParameters = kb().getModelParameters();
        int n = modelParameters.getN();
        int d = modelParameters.getD();
        
        Map<Object, Double> thitas = modelParameters.getThitas();
        Map<Object, Integer> featureIds = modelParameters.getFeatureIds();
        //Map<Integer, Integer> recordIdsReference = null;
        MatrixDataframe matrixDataset = MatrixDataframe.newInstance(trainingData, true, null, featureIds);
        
        RealVector Y = matrixDataset.getY();
        RealMatrix X = matrixDataset.getX();
        
        //(X'X)^-1
        RealMatrix Xt = X.transpose();
        LUDecomposition lud = new LUDecomposition(Xt.multiply(X));
        //W = (X'X)^-1 * X'Y
        RealMatrix XtXinv = lud.getSolver().getInverse();
        RealVector coefficients = XtXinv.multiply(Xt).operate(Y);
        // instead of matrix inversion calculate (X'X) * W = X'Y
        //RealVector coefficients = lud.getSolver().solve(Xt.operate(Y));
        //lud =null;
        
        
        //Xt = null;
        
        //put the features coefficients in the thita map
        thitas.put(Dataframe.COLUMN_NAME_CONSTANT, coefficients.getEntry(0));
        for(Map.Entry<Object, Integer> entry : featureIds.entrySet()) {
            Object feature = entry.getKey();
            Integer featureId = entry.getValue();
            
            thitas.put(feature, coefficients.getEntry(featureId));
        }
        
        
        //get the predictions and subtact the Y vector. Sum the squared differences to get the error
        double SSE = 0.0;
        for(double v : X.operate(coefficients).subtract(Y).toArray()) {
            SSE += v*v;
        }
        //Y = null;

        //standard error matrix
        double MSE = SSE/(n-(d+1)); //mean square error = SSE / dfResidual
        RealMatrix SE = XtXinv.scalarMultiply(MSE);
        //XtXinv = null;

        //creating a flipped map of ids to features
        Map<Integer, Object> idsFeatures = PHPMethods.array_flip(featureIds);


        Map<Object, Double> pvalues = new HashMap<>(); //This is not small, but it does not make sense to store it in the db
        for(int i =0;i<(d+1);++i) {
            double error = SE.getEntry(i, i);
            Object feature = idsFeatures.get(i);
            if(error<=0.0) {
                //double tstat = Double.MAX_VALUE;
                pvalues.put(feature, 0.0);
            }
            else {
                double tstat = coefficients.getEntry(i)/Math.sqrt(error);
                pvalues.put(feature, 1.0-ContinuousDistributions.studentsCdf(tstat, n-(d+1))); //n-d degrees of freedom
            }
        }
        //SE=null;
        //coefficients=null;
        //idsFeatures=null;
        //matrixDataset = null;

        modelParameters.setFeaturePvalues(pvalues);

    }
    
    /** {@inheritDoc} */
    @Override
    public Map<Object, Double> getFeaturePvalues() {
        return kb().getModelParameters().getFeaturePvalues();
    }
    
}
