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
package com.datumbox.framework.core.machinelearning.common.abstracts.wrappers;

import com.datumbox.framework.common.Configuration;
import com.datumbox.framework.common.interfaces.Trainable;
import com.datumbox.framework.core.machinelearning.common.abstracts.AbstractTrainer;
import com.datumbox.framework.core.machinelearning.common.abstracts.datatransformers.AbstractTransformer;
import com.datumbox.framework.core.machinelearning.common.abstracts.featureselectors.AbstractFeatureSelector;
import com.datumbox.framework.core.machinelearning.common.abstracts.modelers.AbstractModeler;
import com.datumbox.framework.core.machinelearning.common.dataobjects.DoubleKnowledgeBase;
import com.datumbox.framework.core.machinelearning.common.interfaces.Parallelizable;
import com.datumbox.framework.core.machinelearning.common.interfaces.ValidationMetrics;

/**
 * The AbstractWrapper is a trainable object that uses composition instead of inheritance
 to extend the functionality of a AbstractModeler. It includes various internal objects
 * such as Data Transformers, Feature Selectors and Machine Learning models which 
 * are combined in the training and prediction process. 
 * 
 * @author Vasilis Vryniotis <bbriniotis@datumbox.com>
 * @param <MP>
 * @param <TP>
 */
public abstract class AbstractWrapper<MP extends AbstractWrapper.AbstractModelParameters, TP extends AbstractWrapper.AbstractTrainingParameters> extends AbstractTrainer<MP, TP, DoubleKnowledgeBase<MP, TP>> implements Parallelizable {
    
    /**
     * The AbstractTransformer instance of the wrapper.
     */
    protected AbstractTransformer dataTransformer = null;
    
    /**
     * The AbstractFeatureSelector instance of the wrapper.
     */
    protected AbstractFeatureSelector featureSelector = null;
    
    /**
     * The Machine Learning model instance of the wrapper.
     */
    protected AbstractModeler modeler = null;
        
    /**
     * The AbstractTrainingParameters class stores the parameters that can be changed
 before training the algorithm.
     * 
     * @param <DT>
     * @param <FS>
     * @param <ML>
     */
    public static abstract class AbstractTrainingParameters<DT extends AbstractTransformer, FS extends AbstractFeatureSelector, ML extends AbstractModeler> extends AbstractTrainer.AbstractTrainingParameters {
        
        //Classes
        private Class<? extends DT> dataTransformerClass;

        private Class<? extends FS> featureSelectorClass;
        
        private Class<? extends ML> modelerClass;
       
        //Parameter Objects
        private DT.AbstractTrainingParameters dataTransformerTrainingParameters;
        
        private FS.AbstractTrainingParameters featureSelectorTrainingParameters;
        
        private ML.AbstractTrainingParameters modelerTrainingParameters;

        /**
         * Getter for the Java class of the Data Transformer.
         * 
         * @return 
         */
        public Class<? extends DT> getDataTransformerClass() {
            return dataTransformerClass;
        }
        
        /**
         * Setter for the Java class of the Data Transformer. Pass null for none.
         * 
         * @param dataTransformerClass 
         */
        public void setDataTransformerClass(Class<? extends DT> dataTransformerClass) {
            this.dataTransformerClass = dataTransformerClass;
        }
        
        /**
         * Getter for the Java class of the Feature Selector.
         * 
         * @return 
         */
        public Class<? extends FS> getFeatureSelectorClass() {
            return featureSelectorClass;
        }
        
        /**
         * Setter for the Java class of the Feature Selector. Pass null for none.
         * 
         * @param featureSelectorClass 
         */
        public void setFeatureSelectorClass(Class<? extends FS> featureSelectorClass) {
            this.featureSelectorClass = featureSelectorClass;
        }
        
        /**
         * Getter for the Java class of the Machine Learning modeler which will
 be used internally.
         * 
         * @return 
         */
        public Class<? extends ML> getModelerClass() {
            return modelerClass;
        }
        
        /**
         * Setter for the Java class of the Machine Learning modeler which will
 be used internally.
         * 
         * @param modelerClass 
         */
        public void setModelerClass(Class<? extends ML> modelerClass) {
            this.modelerClass = modelerClass;
        }
        
        /**
         * Getter for the Training Parameters of the Data Transformer.
         * 
         * @return 
         */
        public DT.AbstractTrainingParameters getDataTransformerTrainingParameters() {
            return dataTransformerTrainingParameters;
        }
        
        /**
         * Setter for the Training Parameters of the Data Transformer. Pass null
         * for none.
         * 
         * @param dataTransformerTrainingParameters 
         */
        public void setDataTransformerTrainingParameters(DT.AbstractTrainingParameters dataTransformerTrainingParameters) {
            this.dataTransformerTrainingParameters = dataTransformerTrainingParameters;
        }

        /**
         * Getter for the Training Parameters of the Feature Selector.
         * 
         * @return 
         */
        public FS.AbstractTrainingParameters getFeatureSelectorTrainingParameters() {
            return featureSelectorTrainingParameters;
        }
        
        /**
         * Setter for the Training Parameters of the Feature Selector. Pass null
         * for none.
         * 
         * @param featureSelectorTrainingParameters 
         */
        public void setFeatureSelectorTrainingParameters(FS.AbstractTrainingParameters featureSelectorTrainingParameters) {
            this.featureSelectorTrainingParameters = featureSelectorTrainingParameters;
        }

        /**
         * Getter for the Training Parameters of the Machine Learning modeler.
         * 
         * @return 
         */
        public ML.AbstractTrainingParameters getModelerTrainingParameters() {
            return modelerTrainingParameters;
        }
        
        /**
         * Setter for the Training Parameters of the Machine Learning modeler.
         * 
         * @param modelerTrainingParameters 
         */
        public void setModelerTrainingParameters(ML.AbstractTrainingParameters modelerTrainingParameters) {
            this.modelerTrainingParameters = modelerTrainingParameters;
        }
        
    }
    
    /** 
     * @param dbName
     * @param conf
     * @param mpClass
     * @param tpClass
     * @see AbstractTrainer#AbstractTrainer(java.lang.String, Configuration, java.lang.Class, java.lang.Class...)
     */
    protected AbstractWrapper(String dbName, Configuration conf, Class<MP> mpClass, Class<TP> tpClass) {
        super(dbName, conf, DoubleKnowledgeBase.class, mpClass, tpClass);
    }
    
    private boolean parallelized = true;
    
    /** {@inheritDoc} */
    @Override
    public boolean isParallelized() {
        return parallelized;
    }

    /** {@inheritDoc} */
    @Override
    public void setParallelized(boolean parallelized) {
        this.parallelized = parallelized;
    }
    
    /** {@inheritDoc} */
    @Override
    public void delete() {
        if(dataTransformer!=null) {
            dataTransformer.delete();
        }
        if(featureSelector!=null) {
            featureSelector.delete();
        }
        if(modeler!=null) {
            modeler.delete();
        }
        kb().delete();
    }
      
    /** {@inheritDoc} */
    @Override
    public void close() {
        if(dataTransformer!=null) {
            dataTransformer.close();
        }
        if(featureSelector!=null) {
            featureSelector.close();
        }
        if(modeler!=null) {
            modeler.close();
        }
        kb().close();
    }

    /**
     * Getter for the Validation Metrics of the algorithm.
     * 
     * @param <VM>
     * @return 
     */
    public <VM extends ValidationMetrics> VM getValidationMetrics() {
        if(modeler!=null) {
            return (VM) modeler.getValidationMetrics();
        }
        else {
            return null;
        }
    }
    
    /**
     * Setter for the Validation Metrics of the algorithm.
     * 
     * @param <VM>
     * @param validationMetrics 
     */
    public <VM extends ValidationMetrics> void setValidationMetrics(VM validationMetrics) {
        if(modeler!=null) {
            modeler.setValidationMetrics((AbstractModeler.AbstractValidationMetrics) validationMetrics);
        }
    }
    
    /**
     * Updates the parallelized flag of the component if it supports it. This is
     * done just before the train and predict methods.
     * 
     * @param algorithm 
     */
    protected void setParallelized(Trainable algorithm) {
        if (algorithm != null && algorithm instanceof Parallelizable) {
            ((Parallelizable)algorithm).setParallelized(isParallelized());
        }
    }
}
