/**
 */
package org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.LwMessagePackage
 * @generated
 */
public interface LwMessageFactory extends EFactory
{
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  LwMessageFactory eINSTANCE = org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessageFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Abstract Message</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Abstract Message</em>'.
   * @generated
   */
  AbstractMessage createAbstractMessage();

  /**
   * Returns a new object of class '<em>Request Message</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Request Message</em>'.
   * @generated
   */
  RequestMessage createRequestMessage();

  /**
   * Returns a new object of class '<em>Any Message</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Any Message</em>'.
   * @generated
   */
  AnyMessage createAnyMessage();

  /**
   * Returns a new object of class '<em>Message Request Arguments</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Message Request Arguments</em>'.
   * @generated
   */
  MessageRequestArguments createMessageRequestArguments();

  /**
   * Returns a new object of class '<em>Message Argument</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Message Argument</em>'.
   * @generated
   */
  MessageArgument createMessageArgument();

  /**
   * Returns a new object of class '<em>Reply Message</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Reply Message</em>'.
   * @generated
   */
  ReplyMessage createReplyMessage();

  /**
   * Returns a new object of class '<em>Assignment Target</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Assignment Target</em>'.
   * @generated
   */
  AssignmentTarget createAssignmentTarget();

  /**
   * Returns a new object of class '<em>Message Reply Outputs</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Message Reply Outputs</em>'.
   * @generated
   */
  MessageReplyOutputs createMessageReplyOutputs();

  /**
   * Returns a new object of class '<em>Message Reply Output</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Message Reply Output</em>'.
   * @generated
   */
  MessageReplyOutput createMessageReplyOutput();

  /**
   * Returns a new object of class '<em>Value</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Value</em>'.
   * @generated
   */
  Value createValue();

  /**
   * Returns a new object of class '<em>Boolean Value</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Boolean Value</em>'.
   * @generated
   */
  BooleanValue createBooleanValue();

  /**
   * Returns a new object of class '<em>Integer Value</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Integer Value</em>'.
   * @generated
   */
  IntegerValue createIntegerValue();

  /**
   * Returns a new object of class '<em>Unlimited Natural Value</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Unlimited Natural Value</em>'.
   * @generated
   */
  UnlimitedNaturalValue createUnlimitedNaturalValue();

  /**
   * Returns a new object of class '<em>Real Value</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Real Value</em>'.
   * @generated
   */
  RealValue createRealValue();

  /**
   * Returns a new object of class '<em>Null Value</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Null Value</em>'.
   * @generated
   */
  NullValue createNullValue();

  /**
   * Returns a new object of class '<em>String Value</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>String Value</em>'.
   * @generated
   */
  StringValue createStringValue();

  /**
   * Returns a new object of class '<em>Undefined Rule</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Undefined Rule</em>'.
   * @generated
   */
  UndefinedRule createUndefinedRule();

  /**
   * Returns a new object of class '<em>Wildcard Message Argument</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Wildcard Message Argument</em>'.
   * @generated
   */
  WildcardMessageArgument createWildcardMessageArgument();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  LwMessagePackage getLwMessagePackage();

} //LwMessageFactory
