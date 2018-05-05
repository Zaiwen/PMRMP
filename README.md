# PMRMP
PMRMP: A Process Model Registration and Management Platform

What is PMRMP?

PMRMP is a business process model registration and management platform. Before PMRMP is immigrated from TaoBao code (http://code.taobao.org), it was also named as BPEP (Business Process Extension Platform).

To facilitate business collaboration and interopration among enterprises, it is critical to discover and reuse appropriate business processes modeled in different languages and stored in different repositories. However, the formats of business process models are very different, which makes it a challenge to fuse them in a unified way without chaning their orignial representations and semantics. To solve this problem, PMRMP uses semantic interoperability technique which is able to tranform heterogonous process models into the uniform registered items. Based on the general and unambiguous metamodel for process model registration (ISO/IEC 19763-5) that we proposed before, PMRMP provides a generic process model registration framework for reigstering heterogeneous business process models to facilitate the semantic discovery of business processes across enterprises, and promote process interoperation and business collaboration. So far, we have implemented the registration of business process model in EPC, BPEL and OWL-S Process model.

In a business cloud environment, a reference business process model needs to be customized in order to meet the individualized requirements of each organization. Consequently, a reference process model is generally evolved to a couple of process variants, known as a process family. Currently, there exist some approaches and tools that can efficiently configure a reference process model. However, a key issue is how to manage co-evolution appropriately within a family of process models if the base process model of a process family is changed. Contemporary process management tools do not adequately support the management of such co-evolution. Each process variant in the process family has to be changed as a separate process model which often leads to redundancies and inefficiencies. PMRMP is able to manage co-evolution of process families based on an aspect-oriented approach. Change options on base process model are abstracted and extracted as a pluggable component, which can be selectively reused for all members, and consequently, guides the co-evolution for the whole process family. In particular, the control-flow and data-flow relations between process extension and a member of process families can be built by leveraging process extensibility patterns. The correctness of our extension approach is proved based on graph theory and case study of SAP reference models.

PMRMP is involved in these two articles shown below:
1. Zaiwen Feng, Dickson K. W. Chiu, Rong Peng, Ping Gong, Keqing He, Yiwang Huang:
Facilitating Cloud Process Family Co-Evolution by Reusable Process Plug-in: An Open-source Prototype. IEEE Trans. Services Computing 10(6): 854-867 (2017)
2. Chen Wang, Chong Wang, Zaiwen Feng, Dickson K.W. Chiu, Keqing He, Yi Zhao, An approach for business process model
registration based on ISO 19763-5, Service Oriented Computing and Applications, Springer, under reviewed, 2018.



