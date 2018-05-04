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
package org.eclipse.papyrus.uml.interaction.model.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MExecution;
import org.eclipse.papyrus.uml.interaction.model.MExecutionOccurrence;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.papyrus.uml.interaction.model.MObject;
import org.eclipse.papyrus.uml.interaction.model.MOccurrence;
import org.eclipse.uml2.uml.Element;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the call
 * {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the
 * model, starting with the actual class of the object and proceeding up the inheritance hierarchy until a
 * non-null result is returned, which is the result of the switch. <!-- end-user-doc -->
 * 
 * @see org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage
 * @generated
 */
public class SequenceDiagramSwitch<T1> extends Switch<T1> {
	/**
	 * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static SequenceDiagramPackage modelPackage;

	/**
	 * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public SequenceDiagramSwitch() {
		if (modelPackage == null) {
			modelPackage = SequenceDiagramPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param ePackage
	 *            the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	public T1 doSwitch(MObject mObject) {
		return super.doSwitch((EObject)mObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields
	 * that result. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T1 doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case SequenceDiagramPackage.MELEMENT: {
				MElement<?> mElement = (MElement<?>)theEObject;
				T1 result = caseMElement(mElement);
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case SequenceDiagramPackage.MINTERACTION: {
				MInteraction mInteraction = (MInteraction)theEObject;
				T1 result = caseMInteraction(mInteraction);
				if (result == null) {
					result = caseMElement(mInteraction);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case SequenceDiagramPackage.MLIFELINE: {
				MLifeline mLifeline = (MLifeline)theEObject;
				T1 result = caseMLifeline(mLifeline);
				if (result == null) {
					result = caseMElement(mLifeline);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case SequenceDiagramPackage.MEXECUTION: {
				MExecution mExecution = (MExecution)theEObject;
				T1 result = caseMExecution(mExecution);
				if (result == null) {
					result = caseMElement(mExecution);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case SequenceDiagramPackage.MOCCURRENCE: {
				MOccurrence<?> mOccurrence = (MOccurrence<?>)theEObject;
				T1 result = caseMOccurrence(mOccurrence);
				if (result == null) {
					result = caseMElement(mOccurrence);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case SequenceDiagramPackage.MEXECUTION_OCCURRENCE: {
				MExecutionOccurrence mExecutionOccurrence = (MExecutionOccurrence)theEObject;
				T1 result = caseMExecutionOccurrence(mExecutionOccurrence);
				if (result == null) {
					result = caseMOccurrence(mExecutionOccurrence);
				}
				if (result == null) {
					result = caseMElement(mExecutionOccurrence);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case SequenceDiagramPackage.MMESSAGE_END: {
				MMessageEnd mMessageEnd = (MMessageEnd)theEObject;
				T1 result = caseMMessageEnd(mMessageEnd);
				if (result == null) {
					result = caseMOccurrence(mMessageEnd);
				}
				if (result == null) {
					result = caseMElement(mMessageEnd);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case SequenceDiagramPackage.MMESSAGE: {
				MMessage mMessage = (MMessage)theEObject;
				T1 result = caseMMessage(mMessage);
				if (result == null) {
					result = caseMElement(mMessage);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			default:
				return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>MElement</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>MElement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T extends Element> T1 caseMElement(MElement<T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>MInteraction</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>MInteraction</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseMInteraction(MInteraction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>MLifeline</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>MLifeline</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseMLifeline(MLifeline object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>MExecution</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>MExecution</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseMExecution(MExecution object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>MOccurrence</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>MOccurrence</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T extends Element> T1 caseMOccurrence(MOccurrence<T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>MExecution Occurrence</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>MExecution Occurrence</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseMExecutionOccurrence(MExecutionOccurrence object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>MMessage End</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>MMessage End</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseMMessageEnd(MMessageEnd object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>MMessage</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>MMessage</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseMMessage(MMessage object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch, but this
	 * is the last case anyway. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated NOT
	 */
	@Override
	public T1 defaultCase(EObject object) {
		T1 result = null;

		if (object instanceof MObject) {
			result = defaultCase((MObject)object);
		}

		return result;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>MObject</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch, but this
	 * is the last case anyway. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>MObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated NOT
	 */
	public T1 defaultCase(MObject object) {
		return null;
	}

} // SequenceDiagramSwitch
