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
 * 
 */
package org.eclipse.papyrus.uml.interaction.internal.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramFactory
 * @model kind="package"
 * @generated
 */
public interface SequenceDiagramPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "interaction"; //$NON-NLS-1$

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/papyrus/2018/uml/interaction/logical"; //$NON-NLS-1$

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "seqd"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	SequenceDiagramPackage eINSTANCE = org.eclipse.papyrus.uml.interaction.internal.model.impl.SequenceDiagramPackageImpl
			.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MElementImpl
	 * <em>MElement</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.MElementImpl
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.SequenceDiagramPackageImpl#getMElement()
	 * @generated
	 */
	int MELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Interaction</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MELEMENT__INTERACTION = 0;

	/**
	 * The feature id for the '<em><b>Element</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MELEMENT__ELEMENT = 1;

	/**
	 * The feature id for the '<em><b>Top</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MELEMENT__TOP = 2;

	/**
	 * The feature id for the '<em><b>Bottom</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MELEMENT__BOTTOM = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MELEMENT__NAME = 4;

	/**
	 * The number of structural features of the '<em>MElement</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MELEMENT_FEATURE_COUNT = 5;

	/**
	 * The operation id for the '<em>Get Owner</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MELEMENT___GET_OWNER = 0;

	/**
	 * The operation id for the '<em>Get Diagram View</em>' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MELEMENT___GET_DIAGRAM_VIEW = 1;

	/**
	 * The operation id for the '<em>Vertical Distance</em>' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MELEMENT___VERTICAL_DISTANCE__MELEMENT = 2;

	/**
	 * The operation id for the '<em>Following</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MELEMENT___FOLLOWING = 3;

	/**
	 * The operation id for the '<em>Nudge</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MELEMENT___NUDGE__INT = 4;

	/**
	 * The operation id for the '<em>Remove</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MELEMENT___REMOVE = 5;

	/**
	 * The number of operations of the '<em>MElement</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MELEMENT_OPERATION_COUNT = 6;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MInteractionImpl
	 * <em>MInteraction</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.MInteractionImpl
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.SequenceDiagramPackageImpl#getMInteraction()
	 * @generated
	 */
	int MINTERACTION = 1;

	/**
	 * The feature id for the '<em><b>Interaction</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MINTERACTION__INTERACTION = MELEMENT__INTERACTION;

	/**
	 * The feature id for the '<em><b>Element</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MINTERACTION__ELEMENT = MELEMENT__ELEMENT;

	/**
	 * The feature id for the '<em><b>Top</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MINTERACTION__TOP = MELEMENT__TOP;

	/**
	 * The feature id for the '<em><b>Bottom</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MINTERACTION__BOTTOM = MELEMENT__BOTTOM;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MINTERACTION__NAME = MELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Lifelines</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MINTERACTION__LIFELINES = MELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Messages</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MINTERACTION__MESSAGES = MELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>MInteraction</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MINTERACTION_FEATURE_COUNT = MELEMENT_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Owner</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MINTERACTION___GET_OWNER = MELEMENT___GET_OWNER;

	/**
	 * The operation id for the '<em>Vertical Distance</em>' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MINTERACTION___VERTICAL_DISTANCE__MELEMENT = MELEMENT___VERTICAL_DISTANCE__MELEMENT;

	/**
	 * The operation id for the '<em>Following</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MINTERACTION___FOLLOWING = MELEMENT___FOLLOWING;

	/**
	 * The operation id for the '<em>Nudge</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MINTERACTION___NUDGE__INT = MELEMENT___NUDGE__INT;

	/**
	 * The operation id for the '<em>Remove</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MINTERACTION___REMOVE = MELEMENT___REMOVE;

	/**
	 * The operation id for the '<em>Get Diagram View</em>' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MINTERACTION___GET_DIAGRAM_VIEW = MELEMENT_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Element</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MINTERACTION___GET_ELEMENT__ELEMENT = MELEMENT_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Lifeline</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MINTERACTION___GET_LIFELINE__LIFELINE = MELEMENT_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Message</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MINTERACTION___GET_MESSAGE__MESSAGE = MELEMENT_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Add Lifeline</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MINTERACTION___ADD_LIFELINE__INT_INT = MELEMENT_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Get Lifeline At</em>' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MINTERACTION___GET_LIFELINE_AT__INT = MELEMENT_OPERATION_COUNT + 5;

	/**
	 * The number of operations of the '<em>MInteraction</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MINTERACTION_OPERATION_COUNT = MELEMENT_OPERATION_COUNT + 6;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MLifelineImpl <em>MLifeline</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.MLifelineImpl
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.SequenceDiagramPackageImpl#getMLifeline()
	 * @generated
	 */
	int MLIFELINE = 2;

	/**
	 * The feature id for the '<em><b>Interaction</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MLIFELINE__INTERACTION = MELEMENT__INTERACTION;

	/**
	 * The feature id for the '<em><b>Element</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MLIFELINE__ELEMENT = MELEMENT__ELEMENT;

	/**
	 * The feature id for the '<em><b>Top</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MLIFELINE__TOP = MELEMENT__TOP;

	/**
	 * The feature id for the '<em><b>Bottom</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MLIFELINE__BOTTOM = MELEMENT__BOTTOM;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MLIFELINE__NAME = MELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Execution Occurrences</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MLIFELINE__EXECUTION_OCCURRENCES = MELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Executions</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MLIFELINE__EXECUTIONS = MELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Left</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MLIFELINE__LEFT = MELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Right</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MLIFELINE__RIGHT = MELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>MLifeline</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MLIFELINE_FEATURE_COUNT = MELEMENT_FEATURE_COUNT + 4;

	/**
	 * The operation id for the '<em>Vertical Distance</em>' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MLIFELINE___VERTICAL_DISTANCE__MELEMENT = MELEMENT___VERTICAL_DISTANCE__MELEMENT;

	/**
	 * The operation id for the '<em>Following</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MLIFELINE___FOLLOWING = MELEMENT___FOLLOWING;

	/**
	 * The operation id for the '<em>Nudge</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MLIFELINE___NUDGE__INT = MELEMENT___NUDGE__INT;

	/**
	 * The operation id for the '<em>Remove</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MLIFELINE___REMOVE = MELEMENT___REMOVE;

	/**
	 * The operation id for the '<em>Get Owner</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MLIFELINE___GET_OWNER = MELEMENT_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Diagram View</em>' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MLIFELINE___GET_DIAGRAM_VIEW = MELEMENT_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Following</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MLIFELINE___FOLLOWING__MELEMENT = MELEMENT_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Execution Occurrence</em>' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MLIFELINE___GET_EXECUTION_OCCURRENCE__EXECUTIONOCCURRENCESPECIFICATION = MELEMENT_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Get Execution</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MLIFELINE___GET_EXECUTION__EXECUTIONSPECIFICATION = MELEMENT_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Insert Execution After</em>' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MLIFELINE___INSERT_EXECUTION_AFTER__MELEMENT_INT_INT_ELEMENT = MELEMENT_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Insert Execution After</em>' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MLIFELINE___INSERT_EXECUTION_AFTER__MELEMENT_INT_INT_ECLASS = MELEMENT_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Insert Message After</em>' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MLIFELINE___INSERT_MESSAGE_AFTER__MELEMENT_INT_MLIFELINE_MESSAGESORT_NAMEDELEMENT = MELEMENT_OPERATION_COUNT
			+ 7;

	/**
	 * The operation id for the '<em>Element At</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MLIFELINE___ELEMENT_AT__INT = MELEMENT_OPERATION_COUNT + 8;

	/**
	 * The operation id for the '<em>Nudge Horizontally</em>' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MLIFELINE___NUDGE_HORIZONTALLY__INT = MELEMENT_OPERATION_COUNT + 9;

	/**
	 * The number of operations of the '<em>MLifeline</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MLIFELINE_OPERATION_COUNT = MELEMENT_OPERATION_COUNT + 10;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MExecutionImpl <em>MExecution</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.MExecutionImpl
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.SequenceDiagramPackageImpl#getMExecution()
	 * @generated
	 */
	int MEXECUTION = 3;

	/**
	 * The feature id for the '<em><b>Interaction</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MEXECUTION__INTERACTION = MELEMENT__INTERACTION;

	/**
	 * The feature id for the '<em><b>Element</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MEXECUTION__ELEMENT = MELEMENT__ELEMENT;

	/**
	 * The feature id for the '<em><b>Top</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MEXECUTION__TOP = MELEMENT__TOP;

	/**
	 * The feature id for the '<em><b>Bottom</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MEXECUTION__BOTTOM = MELEMENT__BOTTOM;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MEXECUTION__NAME = MELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Start</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MEXECUTION__START = MELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Finish</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MEXECUTION__FINISH = MELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>MExecution</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MEXECUTION_FEATURE_COUNT = MELEMENT_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Vertical Distance</em>' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MEXECUTION___VERTICAL_DISTANCE__MELEMENT = MELEMENT___VERTICAL_DISTANCE__MELEMENT;

	/**
	 * The operation id for the '<em>Following</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MEXECUTION___FOLLOWING = MELEMENT___FOLLOWING;

	/**
	 * The operation id for the '<em>Nudge</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MEXECUTION___NUDGE__INT = MELEMENT___NUDGE__INT;

	/**
	 * The operation id for the '<em>Remove</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MEXECUTION___REMOVE = MELEMENT___REMOVE;

	/**
	 * The operation id for the '<em>Get Owner</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MEXECUTION___GET_OWNER = MELEMENT_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Diagram View</em>' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MEXECUTION___GET_DIAGRAM_VIEW = MELEMENT_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>MExecution</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MEXECUTION_OPERATION_COUNT = MELEMENT_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MOccurrenceImpl <em>MOccurrence</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.MOccurrenceImpl
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.SequenceDiagramPackageImpl#getMOccurrence()
	 * @generated
	 */
	int MOCCURRENCE = 4;

	/**
	 * The feature id for the '<em><b>Interaction</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MOCCURRENCE__INTERACTION = MELEMENT__INTERACTION;

	/**
	 * The feature id for the '<em><b>Element</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MOCCURRENCE__ELEMENT = MELEMENT__ELEMENT;

	/**
	 * The feature id for the '<em><b>Top</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MOCCURRENCE__TOP = MELEMENT__TOP;

	/**
	 * The feature id for the '<em><b>Bottom</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MOCCURRENCE__BOTTOM = MELEMENT__BOTTOM;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MOCCURRENCE__NAME = MELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Covered</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MOCCURRENCE__COVERED = MELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Started Execution</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MOCCURRENCE__STARTED_EXECUTION = MELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Finished Execution</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MOCCURRENCE__FINISHED_EXECUTION = MELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>MOccurrence</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MOCCURRENCE_FEATURE_COUNT = MELEMENT_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Get Owner</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MOCCURRENCE___GET_OWNER = MELEMENT___GET_OWNER;

	/**
	 * The operation id for the '<em>Get Diagram View</em>' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MOCCURRENCE___GET_DIAGRAM_VIEW = MELEMENT___GET_DIAGRAM_VIEW;

	/**
	 * The operation id for the '<em>Vertical Distance</em>' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MOCCURRENCE___VERTICAL_DISTANCE__MELEMENT = MELEMENT___VERTICAL_DISTANCE__MELEMENT;

	/**
	 * The operation id for the '<em>Following</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MOCCURRENCE___FOLLOWING = MELEMENT___FOLLOWING;

	/**
	 * The operation id for the '<em>Nudge</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MOCCURRENCE___NUDGE__INT = MELEMENT___NUDGE__INT;

	/**
	 * The operation id for the '<em>Remove</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MOCCURRENCE___REMOVE = MELEMENT___REMOVE;

	/**
	 * The number of operations of the '<em>MOccurrence</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MOCCURRENCE_OPERATION_COUNT = MELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MExecutionOccurrenceImpl <em>MExecution
	 * Occurrence</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.MExecutionOccurrenceImpl
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.SequenceDiagramPackageImpl#getMExecutionOccurrence()
	 * @generated
	 */
	int MEXECUTION_OCCURRENCE = 5;

	/**
	 * The feature id for the '<em><b>Interaction</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MEXECUTION_OCCURRENCE__INTERACTION = MOCCURRENCE__INTERACTION;

	/**
	 * The feature id for the '<em><b>Element</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MEXECUTION_OCCURRENCE__ELEMENT = MOCCURRENCE__ELEMENT;

	/**
	 * The feature id for the '<em><b>Top</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MEXECUTION_OCCURRENCE__TOP = MOCCURRENCE__TOP;

	/**
	 * The feature id for the '<em><b>Bottom</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MEXECUTION_OCCURRENCE__BOTTOM = MOCCURRENCE__BOTTOM;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MEXECUTION_OCCURRENCE__NAME = MOCCURRENCE__NAME;

	/**
	 * The feature id for the '<em><b>Covered</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MEXECUTION_OCCURRENCE__COVERED = MOCCURRENCE__COVERED;

	/**
	 * The feature id for the '<em><b>Started Execution</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MEXECUTION_OCCURRENCE__STARTED_EXECUTION = MOCCURRENCE__STARTED_EXECUTION;

	/**
	 * The feature id for the '<em><b>Finished Execution</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MEXECUTION_OCCURRENCE__FINISHED_EXECUTION = MOCCURRENCE__FINISHED_EXECUTION;

	/**
	 * The number of structural features of the '<em>MExecution Occurrence</em>' class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MEXECUTION_OCCURRENCE_FEATURE_COUNT = MOCCURRENCE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Vertical Distance</em>' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MEXECUTION_OCCURRENCE___VERTICAL_DISTANCE__MELEMENT = MOCCURRENCE___VERTICAL_DISTANCE__MELEMENT;

	/**
	 * The operation id for the '<em>Following</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MEXECUTION_OCCURRENCE___FOLLOWING = MOCCURRENCE___FOLLOWING;

	/**
	 * The operation id for the '<em>Nudge</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MEXECUTION_OCCURRENCE___NUDGE__INT = MOCCURRENCE___NUDGE__INT;

	/**
	 * The operation id for the '<em>Remove</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MEXECUTION_OCCURRENCE___REMOVE = MOCCURRENCE___REMOVE;

	/**
	 * The operation id for the '<em>Get Owner</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MEXECUTION_OCCURRENCE___GET_OWNER = MOCCURRENCE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Diagram View</em>' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MEXECUTION_OCCURRENCE___GET_DIAGRAM_VIEW = MOCCURRENCE_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>MExecution Occurrence</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MEXECUTION_OCCURRENCE_OPERATION_COUNT = MOCCURRENCE_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MMessageEndImpl <em>MMessage End</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.MMessageEndImpl
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.SequenceDiagramPackageImpl#getMMessageEnd()
	 * @generated
	 */
	int MMESSAGE_END = 6;

	/**
	 * The feature id for the '<em><b>Interaction</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE_END__INTERACTION = MOCCURRENCE__INTERACTION;

	/**
	 * The feature id for the '<em><b>Element</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE_END__ELEMENT = MOCCURRENCE__ELEMENT;

	/**
	 * The feature id for the '<em><b>Top</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE_END__TOP = MOCCURRENCE__TOP;

	/**
	 * The feature id for the '<em><b>Bottom</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE_END__BOTTOM = MOCCURRENCE__BOTTOM;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE_END__NAME = MOCCURRENCE__NAME;

	/**
	 * The feature id for the '<em><b>Covered</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE_END__COVERED = MOCCURRENCE__COVERED;

	/**
	 * The feature id for the '<em><b>Started Execution</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE_END__STARTED_EXECUTION = MOCCURRENCE__STARTED_EXECUTION;

	/**
	 * The feature id for the '<em><b>Finished Execution</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE_END__FINISHED_EXECUTION = MOCCURRENCE__FINISHED_EXECUTION;

	/**
	 * The feature id for the '<em><b>Send</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE_END__SEND = MOCCURRENCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Receive</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE_END__RECEIVE = MOCCURRENCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Other End</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE_END__OTHER_END = MOCCURRENCE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>MMessage End</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE_END_FEATURE_COUNT = MOCCURRENCE_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Vertical Distance</em>' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE_END___VERTICAL_DISTANCE__MELEMENT = MOCCURRENCE___VERTICAL_DISTANCE__MELEMENT;

	/**
	 * The operation id for the '<em>Following</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE_END___FOLLOWING = MOCCURRENCE___FOLLOWING;

	/**
	 * The operation id for the '<em>Nudge</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE_END___NUDGE__INT = MOCCURRENCE___NUDGE__INT;

	/**
	 * The operation id for the '<em>Remove</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE_END___REMOVE = MOCCURRENCE___REMOVE;

	/**
	 * The operation id for the '<em>Get Owner</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE_END___GET_OWNER = MOCCURRENCE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Diagram View</em>' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE_END___GET_DIAGRAM_VIEW = MOCCURRENCE_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>MMessage End</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE_END_OPERATION_COUNT = MOCCURRENCE_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MMessageImpl
	 * <em>MMessage</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.MMessageImpl
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.SequenceDiagramPackageImpl#getMMessage()
	 * @generated
	 */
	int MMESSAGE = 7;

	/**
	 * The feature id for the '<em><b>Interaction</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE__INTERACTION = MELEMENT__INTERACTION;

	/**
	 * The feature id for the '<em><b>Element</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE__ELEMENT = MELEMENT__ELEMENT;

	/**
	 * The feature id for the '<em><b>Top</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE__TOP = MELEMENT__TOP;

	/**
	 * The feature id for the '<em><b>Bottom</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE__BOTTOM = MELEMENT__BOTTOM;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE__NAME = MELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Send End</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE__SEND_END = MELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Receive End</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE__RECEIVE_END = MELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Send</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE__SEND = MELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Receive</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE__RECEIVE = MELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Sender</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE__SENDER = MELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Receiver</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE__RECEIVER = MELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>MMessage</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE_FEATURE_COUNT = MELEMENT_FEATURE_COUNT + 6;

	/**
	 * The operation id for the '<em>Vertical Distance</em>' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE___VERTICAL_DISTANCE__MELEMENT = MELEMENT___VERTICAL_DISTANCE__MELEMENT;

	/**
	 * The operation id for the '<em>Following</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE___FOLLOWING = MELEMENT___FOLLOWING;

	/**
	 * The operation id for the '<em>Nudge</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE___NUDGE__INT = MELEMENT___NUDGE__INT;

	/**
	 * The operation id for the '<em>Remove</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE___REMOVE = MELEMENT___REMOVE;

	/**
	 * The operation id for the '<em>Get Owner</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE___GET_OWNER = MELEMENT_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Diagram View</em>' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE___GET_DIAGRAM_VIEW = MELEMENT_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Get End</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE___GET_END__MESSAGEEND = MELEMENT_OPERATION_COUNT + 2;

	/**
	 * The number of operations of the '<em>MMessage</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MMESSAGE_OPERATION_COUNT = MELEMENT_OPERATION_COUNT + 3;

	/**
	 * The meta object id for the '<em>Optional</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see java.util.Optional
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.SequenceDiagramPackageImpl#getOptional()
	 * @generated
	 */
	int OPTIONAL = 8;

	/**
	 * The meta object id for the '<em>Optional Int</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see java.util.OptionalInt
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.SequenceDiagramPackageImpl#getOptionalInt()
	 * @generated
	 */
	int OPTIONAL_INT = 9;

	/**
	 * The meta object id for the '<em>Command</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.common.command.Command
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.SequenceDiagramPackageImpl#getCommand()
	 * @generated
	 */
	int COMMAND = 10;

	/**
	 * The meta object id for the '<em>Creation Command</em>' data type. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.eclipse.papyrus.uml.interaction.model.CreationCommand
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.SequenceDiagramPackageImpl#getCreationCommand()
	 * @generated
	 */
	int CREATION_COMMAND = 11;

	/**
	 * The meta object id for the '<em>EObject</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EObject
	 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.SequenceDiagramPackageImpl#getEObject()
	 * @generated
	 */
	int EOBJECT = 12;

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.uml.interaction.model.MElement
	 * <em>MElement</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>MElement</em>'.
	 * @see org.eclipse.papyrus.uml.interaction.model.MElement
	 * @generated
	 */
	EClass getMElement();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MElement#getInteraction <em>Interaction</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Interaction</em>'.
	 * @see org.eclipse.papyrus.uml.interaction.model.MElement#getInteraction()
	 * @see #getMElement()
	 * @generated
	 */
	EReference getMElement_Interaction();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MElement#getElement <em>Element</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Element</em>'.
	 * @see org.eclipse.papyrus.uml.interaction.model.MElement#getElement()
	 * @see #getMElement()
	 * @generated
	 */
	EReference getMElement_Element();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MElement#getTop <em>Top</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Top</em>'.
	 * @see org.eclipse.papyrus.uml.interaction.model.MElement#getTop()
	 * @see #getMElement()
	 * @generated
	 */
	EAttribute getMElement_Top();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MElement#getBottom <em>Bottom</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Bottom</em>'.
	 * @see org.eclipse.papyrus.uml.interaction.model.MElement#getBottom()
	 * @see #getMElement()
	 * @generated
	 */
	EAttribute getMElement_Bottom();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MElement#getName <em>Name</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.papyrus.uml.interaction.model.MElement#getName()
	 * @see #getMElement()
	 * @generated
	 */
	EAttribute getMElement_Name();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.uml.interaction.model.MElement#getOwner()
	 * <em>Get Owner</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Get Owner</em>' operation.
	 * @see org.eclipse.papyrus.uml.interaction.model.MElement#getOwner()
	 * @generated
	 */
	EOperation getMElement__GetOwner();

	/**
	 * Returns the meta object for the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MElement#getDiagramView() <em>Get Diagram View</em>}'
	 * operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Get Diagram View</em>' operation.
	 * @see org.eclipse.papyrus.uml.interaction.model.MElement#getDiagramView()
	 * @generated
	 */
	EOperation getMElement__GetDiagramView();

	/**
	 * Returns the meta object for the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MElement#verticalDistance(org.eclipse.papyrus.uml.interaction.model.MElement)
	 * <em>Vertical Distance</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Vertical Distance</em>' operation.
	 * @see org.eclipse.papyrus.uml.interaction.model.MElement#verticalDistance(org.eclipse.papyrus.uml.interaction.model.MElement)
	 * @generated
	 */
	EOperation getMElement__VerticalDistance__MElement();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.uml.interaction.model.MElement#following()
	 * <em>Following</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Following</em>' operation.
	 * @see org.eclipse.papyrus.uml.interaction.model.MElement#following()
	 * @generated
	 */
	EOperation getMElement__Following();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.uml.interaction.model.MElement#nudge(int)
	 * <em>Nudge</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Nudge</em>' operation.
	 * @see org.eclipse.papyrus.uml.interaction.model.MElement#nudge(int)
	 * @generated
	 */
	EOperation getMElement__Nudge__int();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.uml.interaction.model.MElement#remove()
	 * <em>Remove</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Remove</em>' operation.
	 * @see org.eclipse.papyrus.uml.interaction.model.MElement#remove()
	 * @generated
	 */
	EOperation getMElement__Remove();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.uml.interaction.model.MInteraction
	 * <em>MInteraction</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>MInteraction</em>'.
	 * @see org.eclipse.papyrus.uml.interaction.model.MInteraction
	 * @generated
	 */
	EClass getMInteraction();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MInteraction#getLifelines <em>Lifelines</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Lifelines</em>'.
	 * @see org.eclipse.papyrus.uml.interaction.model.MInteraction#getLifelines()
	 * @see #getMInteraction()
	 * @generated
	 */
	EReference getMInteraction_Lifelines();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MInteraction#getMessages <em>Messages</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Messages</em>'.
	 * @see org.eclipse.papyrus.uml.interaction.model.MInteraction#getMessages()
	 * @see #getMInteraction()
	 * @generated
	 */
	EReference getMInteraction_Messages();

	/**
	 * Returns the meta object for the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MInteraction#getDiagramView() <em>Get Diagram
	 * View</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Get Diagram View</em>' operation.
	 * @see org.eclipse.papyrus.uml.interaction.model.MInteraction#getDiagramView()
	 * @generated
	 */
	EOperation getMInteraction__GetDiagramView();

	/**
	 * Returns the meta object for the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MInteraction#getElement(org.eclipse.uml2.uml.Element)
	 * <em>Get Element</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Get Element</em>' operation.
	 * @see org.eclipse.papyrus.uml.interaction.model.MInteraction#getElement(org.eclipse.uml2.uml.Element)
	 * @generated
	 */
	EOperation getMInteraction__GetElement__Element();

	/**
	 * Returns the meta object for the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MInteraction#getLifeline(org.eclipse.uml2.uml.Lifeline)
	 * <em>Get Lifeline</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Get Lifeline</em>' operation.
	 * @see org.eclipse.papyrus.uml.interaction.model.MInteraction#getLifeline(org.eclipse.uml2.uml.Lifeline)
	 * @generated
	 */
	EOperation getMInteraction__GetLifeline__Lifeline();

	/**
	 * Returns the meta object for the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MInteraction#getMessage(org.eclipse.uml2.uml.Message)
	 * <em>Get Message</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Get Message</em>' operation.
	 * @see org.eclipse.papyrus.uml.interaction.model.MInteraction#getMessage(org.eclipse.uml2.uml.Message)
	 * @generated
	 */
	EOperation getMInteraction__GetMessage__Message();

	/**
	 * Returns the meta object for the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MInteraction#addLifeline(int, int) <em>Add
	 * Lifeline</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Add Lifeline</em>' operation.
	 * @see org.eclipse.papyrus.uml.interaction.model.MInteraction#addLifeline(int, int)
	 * @generated
	 */
	EOperation getMInteraction__AddLifeline__int_int();

	/**
	 * Returns the meta object for the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MInteraction#getLifelineAt(int) <em>Get Lifeline
	 * At</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Get Lifeline At</em>' operation.
	 * @see org.eclipse.papyrus.uml.interaction.model.MInteraction#getLifelineAt(int)
	 * @generated
	 */
	EOperation getMInteraction__GetLifelineAt__int();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.uml.interaction.model.MLifeline
	 * <em>MLifeline</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>MLifeline</em>'.
	 * @see org.eclipse.papyrus.uml.interaction.model.MLifeline
	 * @generated
	 */
	EClass getMLifeline();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#getExecutionOccurrences <em>Execution
	 * Occurrences</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Execution Occurrences</em>'.
	 * @see org.eclipse.papyrus.uml.interaction.model.MLifeline#getExecutionOccurrences()
	 * @see #getMLifeline()
	 * @generated
	 */
	EReference getMLifeline_ExecutionOccurrences();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#getExecutions <em>Executions</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Executions</em>'.
	 * @see org.eclipse.papyrus.uml.interaction.model.MLifeline#getExecutions()
	 * @see #getMLifeline()
	 * @generated
	 */
	EReference getMLifeline_Executions();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#getLeft <em>Left</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Left</em>'.
	 * @see org.eclipse.papyrus.uml.interaction.model.MLifeline#getLeft()
	 * @see #getMLifeline()
	 * @generated
	 */
	EAttribute getMLifeline_Left();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#getRight <em>Right</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Right</em>'.
	 * @see org.eclipse.papyrus.uml.interaction.model.MLifeline#getRight()
	 * @see #getMLifeline()
	 * @generated
	 */
	EAttribute getMLifeline_Right();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#getOwner()
	 * <em>Get Owner</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Get Owner</em>' operation.
	 * @see org.eclipse.papyrus.uml.interaction.model.MLifeline#getOwner()
	 * @generated
	 */
	EOperation getMLifeline__GetOwner();

	/**
	 * Returns the meta object for the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#getDiagramView() <em>Get Diagram
	 * View</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Get Diagram View</em>' operation.
	 * @see org.eclipse.papyrus.uml.interaction.model.MLifeline#getDiagramView()
	 * @generated
	 */
	EOperation getMLifeline__GetDiagramView();

	/**
	 * Returns the meta object for the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#following(org.eclipse.papyrus.uml.interaction.model.MElement)
	 * <em>Following</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Following</em>' operation.
	 * @see org.eclipse.papyrus.uml.interaction.model.MLifeline#following(org.eclipse.papyrus.uml.interaction.model.MElement)
	 * @generated
	 */
	EOperation getMLifeline__Following__MElement();

	/**
	 * Returns the meta object for the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#getExecutionOccurrence(org.eclipse.uml2.uml.ExecutionOccurrenceSpecification)
	 * <em>Get Execution Occurrence</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Get Execution Occurrence</em>' operation.
	 * @see org.eclipse.papyrus.uml.interaction.model.MLifeline#getExecutionOccurrence(org.eclipse.uml2.uml.ExecutionOccurrenceSpecification)
	 * @generated
	 */
	EOperation getMLifeline__GetExecutionOccurrence__ExecutionOccurrenceSpecification();

	/**
	 * Returns the meta object for the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#getExecution(org.eclipse.uml2.uml.ExecutionSpecification)
	 * <em>Get Execution</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Get Execution</em>' operation.
	 * @see org.eclipse.papyrus.uml.interaction.model.MLifeline#getExecution(org.eclipse.uml2.uml.ExecutionSpecification)
	 * @generated
	 */
	EOperation getMLifeline__GetExecution__ExecutionSpecification();

	/**
	 * Returns the meta object for the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#insertExecutionAfter(org.eclipse.papyrus.uml.interaction.model.MElement, int, int, org.eclipse.uml2.uml.Element)
	 * <em>Insert Execution After</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Insert Execution After</em>' operation.
	 * @see org.eclipse.papyrus.uml.interaction.model.MLifeline#insertExecutionAfter(org.eclipse.papyrus.uml.interaction.model.MElement,
	 *      int, int, org.eclipse.uml2.uml.Element)
	 * @generated
	 */
	EOperation getMLifeline__InsertExecutionAfter__MElement_int_int_Element();

	/**
	 * Returns the meta object for the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#insertExecutionAfter(org.eclipse.papyrus.uml.interaction.model.MElement, int, int, org.eclipse.emf.ecore.EClass)
	 * <em>Insert Execution After</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Insert Execution After</em>' operation.
	 * @see org.eclipse.papyrus.uml.interaction.model.MLifeline#insertExecutionAfter(org.eclipse.papyrus.uml.interaction.model.MElement,
	 *      int, int, org.eclipse.emf.ecore.EClass)
	 * @generated
	 */
	EOperation getMLifeline__InsertExecutionAfter__MElement_int_int_EClass();

	/**
	 * Returns the meta object for the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#insertMessageAfter(org.eclipse.papyrus.uml.interaction.model.MElement, int, org.eclipse.papyrus.uml.interaction.model.MLifeline, org.eclipse.uml2.uml.MessageSort, org.eclipse.uml2.uml.NamedElement)
	 * <em>Insert Message After</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Insert Message After</em>' operation.
	 * @see org.eclipse.papyrus.uml.interaction.model.MLifeline#insertMessageAfter(org.eclipse.papyrus.uml.interaction.model.MElement,
	 *      int, org.eclipse.papyrus.uml.interaction.model.MLifeline, org.eclipse.uml2.uml.MessageSort,
	 *      org.eclipse.uml2.uml.NamedElement)
	 * @generated
	 */
	EOperation getMLifeline__InsertMessageAfter__MElement_int_MLifeline_MessageSort_NamedElement();

	/**
	 * Returns the meta object for the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#elementAt(int) <em>Element At</em>}'
	 * operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Element At</em>' operation.
	 * @see org.eclipse.papyrus.uml.interaction.model.MLifeline#elementAt(int)
	 * @generated
	 */
	EOperation getMLifeline__ElementAt__int();

	/**
	 * Returns the meta object for the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MLifeline#nudgeHorizontally(int) <em>Nudge
	 * Horizontally</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Nudge Horizontally</em>' operation.
	 * @see org.eclipse.papyrus.uml.interaction.model.MLifeline#nudgeHorizontally(int)
	 * @generated
	 */
	EOperation getMLifeline__NudgeHorizontally__int();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.uml.interaction.model.MExecution
	 * <em>MExecution</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>MExecution</em>'.
	 * @see org.eclipse.papyrus.uml.interaction.model.MExecution
	 * @generated
	 */
	EClass getMExecution();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MExecution#getStart <em>Start</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Start</em>'.
	 * @see org.eclipse.papyrus.uml.interaction.model.MExecution#getStart()
	 * @see #getMExecution()
	 * @generated
	 */
	EAttribute getMExecution_Start();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MExecution#getFinish <em>Finish</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Finish</em>'.
	 * @see org.eclipse.papyrus.uml.interaction.model.MExecution#getFinish()
	 * @see #getMExecution()
	 * @generated
	 */
	EAttribute getMExecution_Finish();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.uml.interaction.model.MExecution#getOwner()
	 * <em>Get Owner</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Get Owner</em>' operation.
	 * @see org.eclipse.papyrus.uml.interaction.model.MExecution#getOwner()
	 * @generated
	 */
	EOperation getMExecution__GetOwner();

	/**
	 * Returns the meta object for the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MExecution#getDiagramView() <em>Get Diagram
	 * View</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Get Diagram View</em>' operation.
	 * @see org.eclipse.papyrus.uml.interaction.model.MExecution#getDiagramView()
	 * @generated
	 */
	EOperation getMExecution__GetDiagramView();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.uml.interaction.model.MOccurrence
	 * <em>MOccurrence</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>MOccurrence</em>'.
	 * @see org.eclipse.papyrus.uml.interaction.model.MOccurrence
	 * @generated
	 */
	EClass getMOccurrence();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MOccurrence#getCovered <em>Covered</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Covered</em>'.
	 * @see org.eclipse.papyrus.uml.interaction.model.MOccurrence#getCovered()
	 * @see #getMOccurrence()
	 * @generated
	 */
	EAttribute getMOccurrence_Covered();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MOccurrence#getStartedExecution <em>Started
	 * Execution</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Started Execution</em>'.
	 * @see org.eclipse.papyrus.uml.interaction.model.MOccurrence#getStartedExecution()
	 * @see #getMOccurrence()
	 * @generated
	 */
	EAttribute getMOccurrence_StartedExecution();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MOccurrence#getFinishedExecution <em>Finished
	 * Execution</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Finished Execution</em>'.
	 * @see org.eclipse.papyrus.uml.interaction.model.MOccurrence#getFinishedExecution()
	 * @see #getMOccurrence()
	 * @generated
	 */
	EAttribute getMOccurrence_FinishedExecution();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MExecutionOccurrence <em>MExecution
	 * Occurrence</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>MExecution Occurrence</em>'.
	 * @see org.eclipse.papyrus.uml.interaction.model.MExecutionOccurrence
	 * @generated
	 */
	EClass getMExecutionOccurrence();

	/**
	 * Returns the meta object for the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MExecutionOccurrence#getOwner() <em>Get Owner</em>}'
	 * operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Get Owner</em>' operation.
	 * @see org.eclipse.papyrus.uml.interaction.model.MExecutionOccurrence#getOwner()
	 * @generated
	 */
	EOperation getMExecutionOccurrence__GetOwner();

	/**
	 * Returns the meta object for the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MExecutionOccurrence#getDiagramView() <em>Get Diagram
	 * View</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Get Diagram View</em>' operation.
	 * @see org.eclipse.papyrus.uml.interaction.model.MExecutionOccurrence#getDiagramView()
	 * @generated
	 */
	EOperation getMExecutionOccurrence__GetDiagramView();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.uml.interaction.model.MMessageEnd
	 * <em>MMessage End</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>MMessage End</em>'.
	 * @see org.eclipse.papyrus.uml.interaction.model.MMessageEnd
	 * @generated
	 */
	EClass getMMessageEnd();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MMessageEnd#isSend <em>Send</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Send</em>'.
	 * @see org.eclipse.papyrus.uml.interaction.model.MMessageEnd#isSend()
	 * @see #getMMessageEnd()
	 * @generated
	 */
	EAttribute getMMessageEnd_Send();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MMessageEnd#isReceive <em>Receive</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Receive</em>'.
	 * @see org.eclipse.papyrus.uml.interaction.model.MMessageEnd#isReceive()
	 * @see #getMMessageEnd()
	 * @generated
	 */
	EAttribute getMMessageEnd_Receive();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MMessageEnd#getOtherEnd <em>Other End</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Other End</em>'.
	 * @see org.eclipse.papyrus.uml.interaction.model.MMessageEnd#getOtherEnd()
	 * @see #getMMessageEnd()
	 * @generated
	 */
	EAttribute getMMessageEnd_OtherEnd();

	/**
	 * Returns the meta object for the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MMessageEnd#getOwner() <em>Get Owner</em>}'
	 * operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Get Owner</em>' operation.
	 * @see org.eclipse.papyrus.uml.interaction.model.MMessageEnd#getOwner()
	 * @generated
	 */
	EOperation getMMessageEnd__GetOwner();

	/**
	 * Returns the meta object for the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MMessageEnd#getDiagramView() <em>Get Diagram
	 * View</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Get Diagram View</em>' operation.
	 * @see org.eclipse.papyrus.uml.interaction.model.MMessageEnd#getDiagramView()
	 * @generated
	 */
	EOperation getMMessageEnd__GetDiagramView();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.uml.interaction.model.MMessage
	 * <em>MMessage</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>MMessage</em>'.
	 * @see org.eclipse.papyrus.uml.interaction.model.MMessage
	 * @generated
	 */
	EClass getMMessage();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MMessage <em>Send End</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Send End</em>'.
	 * @see org.eclipse.papyrus.uml.interaction.model.MMessage
	 * @see #getMMessage()
	 * @generated
	 */
	EReference getMMessage_SendEnd();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MMessage <em>Receive End</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Receive End</em>'.
	 * @see org.eclipse.papyrus.uml.interaction.model.MMessage
	 * @see #getMMessage()
	 * @generated
	 */
	EReference getMMessage_ReceiveEnd();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MMessage#getSend <em>Send</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Send</em>'.
	 * @see org.eclipse.papyrus.uml.interaction.model.MMessage#getSend()
	 * @see #getMMessage()
	 * @generated
	 */
	EAttribute getMMessage_Send();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MMessage#getReceive <em>Receive</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Receive</em>'.
	 * @see org.eclipse.papyrus.uml.interaction.model.MMessage#getReceive()
	 * @see #getMMessage()
	 * @generated
	 */
	EAttribute getMMessage_Receive();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MMessage#getSender <em>Sender</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Sender</em>'.
	 * @see org.eclipse.papyrus.uml.interaction.model.MMessage#getSender()
	 * @see #getMMessage()
	 * @generated
	 */
	EAttribute getMMessage_Sender();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MMessage#getReceiver <em>Receiver</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Receiver</em>'.
	 * @see org.eclipse.papyrus.uml.interaction.model.MMessage#getReceiver()
	 * @see #getMMessage()
	 * @generated
	 */
	EAttribute getMMessage_Receiver();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.uml.interaction.model.MMessage#getOwner()
	 * <em>Get Owner</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Get Owner</em>' operation.
	 * @see org.eclipse.papyrus.uml.interaction.model.MMessage#getOwner()
	 * @generated
	 */
	EOperation getMMessage__GetOwner();

	/**
	 * Returns the meta object for the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MMessage#getDiagramView() <em>Get Diagram View</em>}'
	 * operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Get Diagram View</em>' operation.
	 * @see org.eclipse.papyrus.uml.interaction.model.MMessage#getDiagramView()
	 * @generated
	 */
	EOperation getMMessage__GetDiagramView();

	/**
	 * Returns the meta object for the
	 * '{@link org.eclipse.papyrus.uml.interaction.model.MMessage#getEnd(org.eclipse.uml2.uml.MessageEnd)
	 * <em>Get End</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Get End</em>' operation.
	 * @see org.eclipse.papyrus.uml.interaction.model.MMessage#getEnd(org.eclipse.uml2.uml.MessageEnd)
	 * @generated
	 */
	EOperation getMMessage__GetEnd__MessageEnd();

	/**
	 * Returns the meta object for data type '{@link java.util.Optional <em>Optional</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>Optional</em>'.
	 * @see java.util.Optional
	 * @model instanceClass="java.util.Optional" serializeable="false" typeParameters="T"
	 * @generated
	 */
	EDataType getOptional();

	/**
	 * Returns the meta object for data type '{@link java.util.OptionalInt <em>Optional Int</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>Optional Int</em>'.
	 * @see java.util.OptionalInt
	 * @model instanceClass="java.util.OptionalInt" serializeable="false"
	 * @generated
	 */
	EDataType getOptionalInt();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.command.Command
	 * <em>Command</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>Command</em>'.
	 * @see org.eclipse.emf.common.command.Command
	 * @model instanceClass="org.eclipse.emf.common.command.Command" serializeable="false"
	 * @generated
	 */
	EDataType getCommand();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.papyrus.uml.interaction.model.CreationCommand
	 * <em>Creation Command</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>Creation Command</em>'.
	 * @see org.eclipse.papyrus.uml.interaction.model.CreationCommand
	 * @model instanceClass="org.eclipse.papyrus.uml.interaction.model.CreationCommand" serializeable="false"
	 *        typeParameters="T" TBounds="org.eclipse.papyrus.uml.interaction.model.EObject"
	 * @generated
	 */
	EDataType getCreationCommand();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.ecore.EObject <em>EObject</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>EObject</em>'.
	 * @see org.eclipse.emf.ecore.EObject
	 * @model instanceClass="org.eclipse.emf.ecore.EObject"
	 * @generated
	 */
	EDataType getEObject();

	/**
	 * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SequenceDiagramFactory getSequenceDiagramFactory();

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
		 * '{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MElementImpl <em>MElement</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.MElementImpl
		 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.SequenceDiagramPackageImpl#getMElement()
		 * @generated
		 */
		EClass MELEMENT = eINSTANCE.getMElement();

		/**
		 * The meta object literal for the '<em><b>Interaction</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MELEMENT__INTERACTION = eINSTANCE.getMElement_Interaction();

		/**
		 * The meta object literal for the '<em><b>Element</b></em>' reference feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MELEMENT__ELEMENT = eINSTANCE.getMElement_Element();

		/**
		 * The meta object literal for the '<em><b>Top</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MELEMENT__TOP = eINSTANCE.getMElement_Top();

		/**
		 * The meta object literal for the '<em><b>Bottom</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MELEMENT__BOTTOM = eINSTANCE.getMElement_Bottom();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MELEMENT__NAME = eINSTANCE.getMElement_Name();

		/**
		 * The meta object literal for the '<em><b>Get Owner</b></em>' operation. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation MELEMENT___GET_OWNER = eINSTANCE.getMElement__GetOwner();

		/**
		 * The meta object literal for the '<em><b>Get Diagram View</b></em>' operation. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation MELEMENT___GET_DIAGRAM_VIEW = eINSTANCE.getMElement__GetDiagramView();

		/**
		 * The meta object literal for the '<em><b>Vertical Distance</b></em>' operation. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation MELEMENT___VERTICAL_DISTANCE__MELEMENT = eINSTANCE
				.getMElement__VerticalDistance__MElement();

		/**
		 * The meta object literal for the '<em><b>Following</b></em>' operation. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation MELEMENT___FOLLOWING = eINSTANCE.getMElement__Following();

		/**
		 * The meta object literal for the '<em><b>Nudge</b></em>' operation. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation MELEMENT___NUDGE__INT = eINSTANCE.getMElement__Nudge__int();

		/**
		 * The meta object literal for the '<em><b>Remove</b></em>' operation. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation MELEMENT___REMOVE = eINSTANCE.getMElement__Remove();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MInteractionImpl
		 * <em>MInteraction</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.MInteractionImpl
		 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.SequenceDiagramPackageImpl#getMInteraction()
		 * @generated
		 */
		EClass MINTERACTION = eINSTANCE.getMInteraction();

		/**
		 * The meta object literal for the '<em><b>Lifelines</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MINTERACTION__LIFELINES = eINSTANCE.getMInteraction_Lifelines();

		/**
		 * The meta object literal for the '<em><b>Messages</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MINTERACTION__MESSAGES = eINSTANCE.getMInteraction_Messages();

		/**
		 * The meta object literal for the '<em><b>Get Diagram View</b></em>' operation. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation MINTERACTION___GET_DIAGRAM_VIEW = eINSTANCE.getMInteraction__GetDiagramView();

		/**
		 * The meta object literal for the '<em><b>Get Element</b></em>' operation. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation MINTERACTION___GET_ELEMENT__ELEMENT = eINSTANCE.getMInteraction__GetElement__Element();

		/**
		 * The meta object literal for the '<em><b>Get Lifeline</b></em>' operation. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation MINTERACTION___GET_LIFELINE__LIFELINE = eINSTANCE.getMInteraction__GetLifeline__Lifeline();

		/**
		 * The meta object literal for the '<em><b>Get Message</b></em>' operation. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation MINTERACTION___GET_MESSAGE__MESSAGE = eINSTANCE.getMInteraction__GetMessage__Message();

		/**
		 * The meta object literal for the '<em><b>Add Lifeline</b></em>' operation. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation MINTERACTION___ADD_LIFELINE__INT_INT = eINSTANCE.getMInteraction__AddLifeline__int_int();

		/**
		 * The meta object literal for the '<em><b>Get Lifeline At</b></em>' operation. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation MINTERACTION___GET_LIFELINE_AT__INT = eINSTANCE.getMInteraction__GetLifelineAt__int();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MLifelineImpl <em>MLifeline</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.MLifelineImpl
		 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.SequenceDiagramPackageImpl#getMLifeline()
		 * @generated
		 */
		EClass MLIFELINE = eINSTANCE.getMLifeline();

		/**
		 * The meta object literal for the '<em><b>Execution Occurrences</b></em>' containment reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MLIFELINE__EXECUTION_OCCURRENCES = eINSTANCE.getMLifeline_ExecutionOccurrences();

		/**
		 * The meta object literal for the '<em><b>Executions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MLIFELINE__EXECUTIONS = eINSTANCE.getMLifeline_Executions();

		/**
		 * The meta object literal for the '<em><b>Left</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MLIFELINE__LEFT = eINSTANCE.getMLifeline_Left();

		/**
		 * The meta object literal for the '<em><b>Right</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MLIFELINE__RIGHT = eINSTANCE.getMLifeline_Right();

		/**
		 * The meta object literal for the '<em><b>Get Owner</b></em>' operation. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation MLIFELINE___GET_OWNER = eINSTANCE.getMLifeline__GetOwner();

		/**
		 * The meta object literal for the '<em><b>Get Diagram View</b></em>' operation. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation MLIFELINE___GET_DIAGRAM_VIEW = eINSTANCE.getMLifeline__GetDiagramView();

		/**
		 * The meta object literal for the '<em><b>Following</b></em>' operation. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation MLIFELINE___FOLLOWING__MELEMENT = eINSTANCE.getMLifeline__Following__MElement();

		/**
		 * The meta object literal for the '<em><b>Get Execution Occurrence</b></em>' operation. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation MLIFELINE___GET_EXECUTION_OCCURRENCE__EXECUTIONOCCURRENCESPECIFICATION = eINSTANCE
				.getMLifeline__GetExecutionOccurrence__ExecutionOccurrenceSpecification();

		/**
		 * The meta object literal for the '<em><b>Get Execution</b></em>' operation. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation MLIFELINE___GET_EXECUTION__EXECUTIONSPECIFICATION = eINSTANCE
				.getMLifeline__GetExecution__ExecutionSpecification();

		/**
		 * The meta object literal for the '<em><b>Insert Execution After</b></em>' operation. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation MLIFELINE___INSERT_EXECUTION_AFTER__MELEMENT_INT_INT_ELEMENT = eINSTANCE
				.getMLifeline__InsertExecutionAfter__MElement_int_int_Element();

		/**
		 * The meta object literal for the '<em><b>Insert Execution After</b></em>' operation. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation MLIFELINE___INSERT_EXECUTION_AFTER__MELEMENT_INT_INT_ECLASS = eINSTANCE
				.getMLifeline__InsertExecutionAfter__MElement_int_int_EClass();

		/**
		 * The meta object literal for the '<em><b>Insert Message After</b></em>' operation. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation MLIFELINE___INSERT_MESSAGE_AFTER__MELEMENT_INT_MLIFELINE_MESSAGESORT_NAMEDELEMENT = eINSTANCE
				.getMLifeline__InsertMessageAfter__MElement_int_MLifeline_MessageSort_NamedElement();

		/**
		 * The meta object literal for the '<em><b>Element At</b></em>' operation. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation MLIFELINE___ELEMENT_AT__INT = eINSTANCE.getMLifeline__ElementAt__int();

		/**
		 * The meta object literal for the '<em><b>Nudge Horizontally</b></em>' operation. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation MLIFELINE___NUDGE_HORIZONTALLY__INT = eINSTANCE.getMLifeline__NudgeHorizontally__int();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MExecutionImpl
		 * <em>MExecution</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.MExecutionImpl
		 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.SequenceDiagramPackageImpl#getMExecution()
		 * @generated
		 */
		EClass MEXECUTION = eINSTANCE.getMExecution();

		/**
		 * The meta object literal for the '<em><b>Start</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MEXECUTION__START = eINSTANCE.getMExecution_Start();

		/**
		 * The meta object literal for the '<em><b>Finish</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MEXECUTION__FINISH = eINSTANCE.getMExecution_Finish();

		/**
		 * The meta object literal for the '<em><b>Get Owner</b></em>' operation. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation MEXECUTION___GET_OWNER = eINSTANCE.getMExecution__GetOwner();

		/**
		 * The meta object literal for the '<em><b>Get Diagram View</b></em>' operation. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation MEXECUTION___GET_DIAGRAM_VIEW = eINSTANCE.getMExecution__GetDiagramView();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MOccurrenceImpl
		 * <em>MOccurrence</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.MOccurrenceImpl
		 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.SequenceDiagramPackageImpl#getMOccurrence()
		 * @generated
		 */
		EClass MOCCURRENCE = eINSTANCE.getMOccurrence();

		/**
		 * The meta object literal for the '<em><b>Covered</b></em>' attribute feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MOCCURRENCE__COVERED = eINSTANCE.getMOccurrence_Covered();

		/**
		 * The meta object literal for the '<em><b>Started Execution</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MOCCURRENCE__STARTED_EXECUTION = eINSTANCE.getMOccurrence_StartedExecution();

		/**
		 * The meta object literal for the '<em><b>Finished Execution</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MOCCURRENCE__FINISHED_EXECUTION = eINSTANCE.getMOccurrence_FinishedExecution();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MExecutionOccurrenceImpl
		 * <em>MExecution Occurrence</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.MExecutionOccurrenceImpl
		 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.SequenceDiagramPackageImpl#getMExecutionOccurrence()
		 * @generated
		 */
		EClass MEXECUTION_OCCURRENCE = eINSTANCE.getMExecutionOccurrence();

		/**
		 * The meta object literal for the '<em><b>Get Owner</b></em>' operation. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation MEXECUTION_OCCURRENCE___GET_OWNER = eINSTANCE.getMExecutionOccurrence__GetOwner();

		/**
		 * The meta object literal for the '<em><b>Get Diagram View</b></em>' operation. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation MEXECUTION_OCCURRENCE___GET_DIAGRAM_VIEW = eINSTANCE
				.getMExecutionOccurrence__GetDiagramView();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MMessageEndImpl <em>MMessage
		 * End</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.MMessageEndImpl
		 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.SequenceDiagramPackageImpl#getMMessageEnd()
		 * @generated
		 */
		EClass MMESSAGE_END = eINSTANCE.getMMessageEnd();

		/**
		 * The meta object literal for the '<em><b>Send</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MMESSAGE_END__SEND = eINSTANCE.getMMessageEnd_Send();

		/**
		 * The meta object literal for the '<em><b>Receive</b></em>' attribute feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MMESSAGE_END__RECEIVE = eINSTANCE.getMMessageEnd_Receive();

		/**
		 * The meta object literal for the '<em><b>Other End</b></em>' attribute feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MMESSAGE_END__OTHER_END = eINSTANCE.getMMessageEnd_OtherEnd();

		/**
		 * The meta object literal for the '<em><b>Get Owner</b></em>' operation. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation MMESSAGE_END___GET_OWNER = eINSTANCE.getMMessageEnd__GetOwner();

		/**
		 * The meta object literal for the '<em><b>Get Diagram View</b></em>' operation. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation MMESSAGE_END___GET_DIAGRAM_VIEW = eINSTANCE.getMMessageEnd__GetDiagramView();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MMessageImpl <em>MMessage</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.MMessageImpl
		 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.SequenceDiagramPackageImpl#getMMessage()
		 * @generated
		 */
		EClass MMESSAGE = eINSTANCE.getMMessage();

		/**
		 * The meta object literal for the '<em><b>Send End</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MMESSAGE__SEND_END = eINSTANCE.getMMessage_SendEnd();

		/**
		 * The meta object literal for the '<em><b>Receive End</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MMESSAGE__RECEIVE_END = eINSTANCE.getMMessage_ReceiveEnd();

		/**
		 * The meta object literal for the '<em><b>Send</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MMESSAGE__SEND = eINSTANCE.getMMessage_Send();

		/**
		 * The meta object literal for the '<em><b>Receive</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MMESSAGE__RECEIVE = eINSTANCE.getMMessage_Receive();

		/**
		 * The meta object literal for the '<em><b>Sender</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MMESSAGE__SENDER = eINSTANCE.getMMessage_Sender();

		/**
		 * The meta object literal for the '<em><b>Receiver</b></em>' attribute feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MMESSAGE__RECEIVER = eINSTANCE.getMMessage_Receiver();

		/**
		 * The meta object literal for the '<em><b>Get Owner</b></em>' operation. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation MMESSAGE___GET_OWNER = eINSTANCE.getMMessage__GetOwner();

		/**
		 * The meta object literal for the '<em><b>Get Diagram View</b></em>' operation. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation MMESSAGE___GET_DIAGRAM_VIEW = eINSTANCE.getMMessage__GetDiagramView();

		/**
		 * The meta object literal for the '<em><b>Get End</b></em>' operation. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation MMESSAGE___GET_END__MESSAGEEND = eINSTANCE.getMMessage__GetEnd__MessageEnd();

		/**
		 * The meta object literal for the '<em>Optional</em>' data type. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see java.util.Optional
		 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.SequenceDiagramPackageImpl#getOptional()
		 * @generated
		 */
		EDataType OPTIONAL = eINSTANCE.getOptional();

		/**
		 * The meta object literal for the '<em>Optional Int</em>' data type. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see java.util.OptionalInt
		 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.SequenceDiagramPackageImpl#getOptionalInt()
		 * @generated
		 */
		EDataType OPTIONAL_INT = eINSTANCE.getOptionalInt();

		/**
		 * The meta object literal for the '<em>Command</em>' data type. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.emf.common.command.Command
		 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.SequenceDiagramPackageImpl#getCommand()
		 * @generated
		 */
		EDataType COMMAND = eINSTANCE.getCommand();

		/**
		 * The meta object literal for the '<em>Creation Command</em>' data type. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.papyrus.uml.interaction.model.CreationCommand
		 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.SequenceDiagramPackageImpl#getCreationCommand()
		 * @generated
		 */
		EDataType CREATION_COMMAND = eINSTANCE.getCreationCommand();

		/**
		 * The meta object literal for the '<em>EObject</em>' data type. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecore.EObject
		 * @see org.eclipse.papyrus.uml.interaction.internal.model.impl.SequenceDiagramPackageImpl#getEObject()
		 * @generated
		 */
		EDataType EOBJECT = eINSTANCE.getEObject();

	}

} // SequenceDiagramPackage
