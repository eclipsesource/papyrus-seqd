/**
 */
package org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage;

import org.eclipse.uml2.uml.Parameter;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Message Reply Output</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageReplyOutput#getParameter <em>Parameter</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.LwMessagePackage#getMessageReplyOutput()
 * @model
 * @generated
 */
public interface MessageReplyOutput extends AssignmentTarget
{
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
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.LwMessagePackage#getMessageReplyOutput_Parameter()
   * @model
   * @generated
   */
  Parameter getParameter();

  /**
   * Sets the value of the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageReplyOutput#getParameter <em>Parameter</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Parameter</em>' reference.
   * @see #getParameter()
   * @generated
   */
  void setParameter(Parameter value);

} // MessageReplyOutput
