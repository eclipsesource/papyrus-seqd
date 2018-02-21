/**
 */
package org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Message Argument</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageArgument#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageArgument#getProperty <em>Property</em>}</li>
 *   <li>{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageArgument#getParameter <em>Parameter</em>}</li>
 *   <li>{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageArgument#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.LwMessagePackage#getMessageArgument()
 * @model
 * @generated
 */
public interface MessageArgument extends EObject
{
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.LwMessagePackage#getMessageArgument_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageArgument#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Property</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Property</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Property</em>' reference.
   * @see #setProperty(Property)
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.LwMessagePackage#getMessageArgument_Property()
   * @model
   * @generated
   */
  Property getProperty();

  /**
   * Sets the value of the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageArgument#getProperty <em>Property</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Property</em>' reference.
   * @see #getProperty()
   * @generated
   */
  void setProperty(Property value);

  /**
   * Returns the value of the '<em><b>Parameter</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Parameter</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Parameter</em>' reference.
   * @see #setParameter(Parameter)
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.LwMessagePackage#getMessageArgument_Parameter()
   * @model
   * @generated
   */
  Parameter getParameter();

  /**
   * Sets the value of the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageArgument#getParameter <em>Parameter</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Parameter</em>' reference.
   * @see #getParameter()
   * @generated
   */
  void setParameter(Parameter value);

  /**
   * Returns the value of the '<em><b>Value</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Value</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Value</em>' containment reference.
   * @see #setValue(Value)
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.LwMessagePackage#getMessageArgument_Value()
   * @model containment="true"
   * @generated
   */
  Value getValue();

  /**
   * Sets the value of the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageArgument#getValue <em>Value</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Value</em>' containment reference.
   * @see #getValue()
   * @generated
   */
  void setValue(Value value);

} // MessageArgument
