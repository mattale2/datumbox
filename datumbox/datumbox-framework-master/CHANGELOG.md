CHANGELOG
=========

Version 0.7.0 - Build 20160319
------------------------------

- Speed & Memory:
	- Added multi-threading support on the majority of algorithms and methods, making the 0.7.0 version several times faster than 0.6.x.
	- Implemented Storage Hints & hybrid strategies which enable the efficient use of LRU cache and faster training for large datasets that don't fit in memory.
	- All the algorithms which require Matrixes now use sparse implementations to reduce the memory footprint.
	- Fixed a limitation on clustering algorithms which forced us to store the list of clusters in memory.
- Algorithms & Methods:
	- Added L1, L2 and ElasticNet regularization in Logistic, Ordinal and Linear Regression algorithms.
	- The Collaborative Filtering algorithm was modified to support more generic User-user CF models.	
	- Updated the NgramsExtractor algorithm to export more keywords and provide better signals for NLP models.
- Framework Architecture: 
	- The framework is now split to separate modules and the main library is renamed to "datumbox-framework-lib".
	- The Dataset class is replaced with the Dataframe class, which implements the Collection interface and enables the processing of the records in parallel. 
	- Major changes on the structure of Interfaces and inheritance to simplify the architecture.
	- BaseMLrecommender now inherits from BaseMLmodel.
- Code Improvements & Bug Fixes:
	- Added serialVersionUID in every serializable class.
	- Improved Exceptions and error messages.
	- Fixed a bug on Adaboost which resulted in mapping incorrectly the recordIds.
	- Improved documentation and javadocs comments.
	- Increased the test-coverage.

Version 0.6.1 - Build 20160102
------------------------------

- Fixed a minor issue related to Unreleased Resources on tests.
- Resolved a memory leak on the AutoCloseConnector class. The shutdown hooks are now removed when close() is called.
- Updated the TypeInference class to reduce memory consumption.
- Refactored the TextClassifier class and improved its speed.
- Updated all dependencies and maven plugins to the latest stable versions.

Version 0.6.0 - Build 20150502
------------------------------

- Added support of [MapDB](http://www.mapdb.org/) database engine.
- Removed MongoDB support due to performance issues.
- Reduced the level of abstraction and simplify framework's architecture.
- Rewrote the persistence mechanisms, remove unnecessary data structures and features that increased the complexity.
- Changed the public methods of the Machine Learning models to resemble Python's Scikit-Learn APIs.
- Added convenience methods to build a dataset from CSV or text files.
- Enhanced the documentation and Javadoc comments of the Framework.
- Changed the software License from "GNU General Public License v3.0" to "Apache License, Version 2.0".

Version 0.5.1 - Build 20141105
------------------------------

- Updated the pom.xml file.
- Submitted the Datumbox Framework and the LPSolve library in Maven Central Repository.
- Resolved issues [#1](https://github.com/datumbox/datumbox-framework/issues/1), [#2](https://github.com/datumbox/datumbox-framework/issues/2) and [#4](https://github.com/datumbox/datumbox-framework/issues/4). All the dependencies are now on Maven.

Version 0.5.0 - Build 20141018
------------------------------

- First open-source version of Datumbox Framework.

