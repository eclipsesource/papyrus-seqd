/**
 * Copyright (c) 2018 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 */
package org.eclipse.papyrus.uml.diagram.sequence.assistants;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta objects to
 * represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.papyrus.uml.diagram.sequence.assistants.DiagramAssistantsFactory
 * @model kind="package"
 * @generated
 */
public interface DiagramAssistantsPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "assistants"; //$NON-NLS-1$

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/Papyrus/2018/uml/diagram/sequence/assistants"; //$NON-NLS-1$

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "assist"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	DiagramAssistantsPackage eINSTANCE = org.eclipse.papyrus.uml.diagram.sequence.assistants.impl.DiagramAssistantsPackageImpl
			.init();

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.papyrus.uml.diagram.sequence.assistants.impl.SequenceDiagramAssistantProviderImpl
	 * <em>Sequence Diagram Assistant Provider</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.papyrus.uml.diagram.sequence.assistants.impl.SequenceDiagramAssistantProviderImpl
	 * @see org.eclipse.papyrus.uml.diagram.sequence.assistants.impl.DiagramAssistantsPackageImpl#getSequenceDiagramAssistantProvider()
	 * @generated
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER = 0;

	/**
	 * The feature id for the '<em><b>Listener</b></em>' attribute list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER__LISTENER = AssistantPackage.MODELING_ASSISTANT_PROVIDER__LISTENER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER__NAME = AssistantPackage.MODELING_ASSISTANT_PROVIDER__NAME;

	/**
	 * The feature id for the '<em><b>Assistant</b></em>' reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER__ASSISTANT = AssistantPackage.MODELING_ASSISTANT_PROVIDER__ASSISTANT;

	/**
	 * The feature id for the '<em><b>Owned Filter</b></em>' containment reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER__OWNED_FILTER = AssistantPackage.MODELING_ASSISTANT_PROVIDER__OWNED_FILTER;

	/**
	 * The feature id for the '<em><b>Popup Assistant</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER__POPUP_ASSISTANT = AssistantPackage.MODELING_ASSISTANT_PROVIDER__POPUP_ASSISTANT;

	/**
	 * The feature id for the '<em><b>Connection Assistant</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER__CONNECTION_ASSISTANT = AssistantPackage.MODELING_ASSISTANT_PROVIDER__CONNECTION_ASSISTANT;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER__ELEMENT_TYPE = AssistantPackage.MODELING_ASSISTANT_PROVIDER__ELEMENT_TYPE;

	/**
	 * The feature id for the '<em><b>Element Type ID</b></em>' attribute list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER__ELEMENT_TYPE_ID = AssistantPackage.MODELING_ASSISTANT_PROVIDER__ELEMENT_TYPE_ID;

	/**
	 * The feature id for the '<em><b>Client Context</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER__CLIENT_CONTEXT = AssistantPackage.MODELING_ASSISTANT_PROVIDER__CLIENT_CONTEXT;

	/**
	 * The feature id for the '<em><b>Client Context ID</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER__CLIENT_CONTEXT_ID = AssistantPackage.MODELING_ASSISTANT_PROVIDER__CLIENT_CONTEXT_ID;

	/**
	 * The feature id for the '<em><b>Excluded Element Type</b></em>' attribute list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER__EXCLUDED_ELEMENT_TYPE = AssistantPackage.MODELING_ASSISTANT_PROVIDER__EXCLUDED_ELEMENT_TYPE;

	/**
	 * The feature id for the '<em><b>Excluded Element Type ID</b></em>' attribute list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER__EXCLUDED_ELEMENT_TYPE_ID = AssistantPackage.MODELING_ASSISTANT_PROVIDER__EXCLUDED_ELEMENT_TYPE_ID;

	/**
	 * The feature id for the '<em><b>Relationship Type</b></em>' attribute list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER__RELATIONSHIP_TYPE = AssistantPackage.MODELING_ASSISTANT_PROVIDER__RELATIONSHIP_TYPE;

	/**
	 * The feature id for the '<em><b>Relationship Type ID</b></em>' attribute list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER__RELATIONSHIP_TYPE_ID = AssistantPackage.MODELING_ASSISTANT_PROVIDER__RELATIONSHIP_TYPE_ID;

	/**
	 * The number of structural features of the '<em>Sequence Diagram Assistant Provider</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER_FEATURE_COUNT = AssistantPackage.MODELING_ASSISTANT_PROVIDER_FEATURE_COUNT
			+ 0;

	/**
	 * The operation id for the '<em>Provides</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER___PROVIDES__IOPERATION = AssistantPackage.MODELING_ASSISTANT_PROVIDER___PROVIDES__IOPERATION;

	/**
	 * The operation id for the '<em>Add Provider Change Listener</em>' operation. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER___ADD_PROVIDER_CHANGE_LISTENER__IPROVIDERCHANGELISTENER = AssistantPackage.MODELING_ASSISTANT_PROVIDER___ADD_PROVIDER_CHANGE_LISTENER__IPROVIDERCHANGELISTENER;

	/**
	 * The operation id for the '<em>Remove Provider Change Listener</em>' operation. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER___REMOVE_PROVIDER_CHANGE_LISTENER__IPROVIDERCHANGELISTENER = AssistantPackage.MODELING_ASSISTANT_PROVIDER___REMOVE_PROVIDER_CHANGE_LISTENER__IPROVIDERCHANGELISTENER;

	/**
	 * The operation id for the '<em>Get Types</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER___GET_TYPES__STRING_IADAPTABLE = AssistantPackage.MODELING_ASSISTANT_PROVIDER___GET_TYPES__STRING_IADAPTABLE;

	/**
	 * The operation id for the '<em>Get Rel Types On Source</em>' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER___GET_REL_TYPES_ON_SOURCE__IADAPTABLE = AssistantPackage.MODELING_ASSISTANT_PROVIDER___GET_REL_TYPES_ON_SOURCE__IADAPTABLE;

	/**
	 * The operation id for the '<em>Get Rel Types On Target</em>' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER___GET_REL_TYPES_ON_TARGET__IADAPTABLE = AssistantPackage.MODELING_ASSISTANT_PROVIDER___GET_REL_TYPES_ON_TARGET__IADAPTABLE;

	/**
	 * The operation id for the '<em>Get Rel Types On Source And Target</em>' operation. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER___GET_REL_TYPES_ON_SOURCE_AND_TARGET__IADAPTABLE_IADAPTABLE = AssistantPackage.MODELING_ASSISTANT_PROVIDER___GET_REL_TYPES_ON_SOURCE_AND_TARGET__IADAPTABLE_IADAPTABLE;

	/**
	 * The operation id for the '<em>Get Rel Types For SRE On Target</em>' operation. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER___GET_REL_TYPES_FOR_SRE_ON_TARGET__IADAPTABLE = AssistantPackage.MODELING_ASSISTANT_PROVIDER___GET_REL_TYPES_FOR_SRE_ON_TARGET__IADAPTABLE;

	/**
	 * The operation id for the '<em>Get Rel Types For SRE On Source</em>' operation. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER___GET_REL_TYPES_FOR_SRE_ON_SOURCE__IADAPTABLE = AssistantPackage.MODELING_ASSISTANT_PROVIDER___GET_REL_TYPES_FOR_SRE_ON_SOURCE__IADAPTABLE;

	/**
	 * The operation id for the '<em>Get Types For Source</em>' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER___GET_TYPES_FOR_SOURCE__IADAPTABLE_IELEMENTTYPE = AssistantPackage.MODELING_ASSISTANT_PROVIDER___GET_TYPES_FOR_SOURCE__IADAPTABLE_IELEMENTTYPE;

	/**
	 * The operation id for the '<em>Get Types For Target</em>' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER___GET_TYPES_FOR_TARGET__IADAPTABLE_IELEMENTTYPE = AssistantPackage.MODELING_ASSISTANT_PROVIDER___GET_TYPES_FOR_TARGET__IADAPTABLE_IELEMENTTYPE;

	/**
	 * The operation id for the '<em>Select Existing Element For Source</em>' operation. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER___SELECT_EXISTING_ELEMENT_FOR_SOURCE__IADAPTABLE_IELEMENTTYPE = AssistantPackage.MODELING_ASSISTANT_PROVIDER___SELECT_EXISTING_ELEMENT_FOR_SOURCE__IADAPTABLE_IELEMENTTYPE;

	/**
	 * The operation id for the '<em>Select Existing Element For Target</em>' operation. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER___SELECT_EXISTING_ELEMENT_FOR_TARGET__IADAPTABLE_IELEMENTTYPE = AssistantPackage.MODELING_ASSISTANT_PROVIDER___SELECT_EXISTING_ELEMENT_FOR_TARGET__IADAPTABLE_IELEMENTTYPE;

	/**
	 * The operation id for the '<em>Get Types For Popup Bar</em>' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER___GET_TYPES_FOR_POPUP_BAR__IADAPTABLE = AssistantPackage.MODELING_ASSISTANT_PROVIDER___GET_TYPES_FOR_POPUP_BAR__IADAPTABLE;

	/**
	 * The operation id for the '<em>Get Element Types</em>' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER___GET_ELEMENT_TYPES = AssistantPackage.MODELING_ASSISTANT_PROVIDER___GET_ELEMENT_TYPES;

	/**
	 * The operation id for the '<em>Get Client Context</em>' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER___GET_CLIENT_CONTEXT = AssistantPackage.MODELING_ASSISTANT_PROVIDER___GET_CLIENT_CONTEXT;

	/**
	 * The operation id for the '<em>Get Element Type</em>' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER___GET_ELEMENT_TYPE__STRING = AssistantPackage.MODELING_ASSISTANT_PROVIDER___GET_ELEMENT_TYPE__STRING;

	/**
	 * The operation id for the '<em>Get Excluded Element Types</em>' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER___GET_EXCLUDED_ELEMENT_TYPES = AssistantPackage.MODELING_ASSISTANT_PROVIDER___GET_EXCLUDED_ELEMENT_TYPES;

	/**
	 * The operation id for the '<em>Get Relationship Types</em>' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER___GET_RELATIONSHIP_TYPES = AssistantPackage.MODELING_ASSISTANT_PROVIDER___GET_RELATIONSHIP_TYPES;

	/**
	 * The operation id for the '<em>Is Relationship Type</em>' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER___IS_RELATIONSHIP_TYPE__IELEMENTTYPE = AssistantPackage.MODELING_ASSISTANT_PROVIDER___IS_RELATIONSHIP_TYPE__IELEMENTTYPE;

	/**
	 * The number of operations of the '<em>Sequence Diagram Assistant Provider</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER_OPERATION_COUNT = AssistantPackage.MODELING_ASSISTANT_PROVIDER_OPERATION_COUNT
			+ 0;

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.papyrus.uml.diagram.sequence.assistants.SequenceDiagramAssistantProvider
	 * <em>Sequence Diagram Assistant Provider</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Sequence Diagram Assistant Provider</em>'.
	 * @see org.eclipse.papyrus.uml.diagram.sequence.assistants.SequenceDiagramAssistantProvider
	 * @generated
	 */
	EClass getSequenceDiagramAssistantProvider();

	/**
	 * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DiagramAssistantsFactory getDiagramAssistantsFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each operation of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.papyrus.uml.diagram.sequence.assistants.impl.SequenceDiagramAssistantProviderImpl
		 * <em>Sequence Diagram Assistant Provider</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.papyrus.uml.diagram.sequence.assistants.impl.SequenceDiagramAssistantProviderImpl
		 * @see org.eclipse.papyrus.uml.diagram.sequence.assistants.impl.DiagramAssistantsPackageImpl#getSequenceDiagramAssistantProvider()
		 * @generated
		 */
		EClass SEQUENCE_DIAGRAM_ASSISTANT_PROVIDER = eINSTANCE.getSequenceDiagramAssistantProvider();

	}

} // DiagramAssistantsPackage
