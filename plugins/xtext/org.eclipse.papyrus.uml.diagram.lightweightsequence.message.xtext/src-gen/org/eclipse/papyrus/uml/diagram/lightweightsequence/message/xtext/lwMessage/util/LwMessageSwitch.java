/**
 */
package org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.LwMessagePackage
 * @generated
 */
public class LwMessageSwitch<T> extends Switch<T>
{
  /**
   * The cached model package
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static LwMessagePackage modelPackage;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LwMessageSwitch()
  {
    if (modelPackage == null)
    {
      modelPackage = LwMessagePackage.eINSTANCE;
    }
  }

  /**
   * Checks whether this is a switch for the given package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param ePackage the package in question.
   * @return whether this is a switch for the given package.
   * @generated
   */
  @Override
  protected boolean isSwitchFor(EPackage ePackage)
  {
    return ePackage == modelPackage;
  }

  /**
   * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the first non-null result returned by a <code>caseXXX</code> call.
   * @generated
   */
  @Override
  protected T doSwitch(int classifierID, EObject theEObject)
  {
    switch (classifierID)
    {
      case LwMessagePackage.ABSTRACT_MESSAGE:
      {
        AbstractMessage abstractMessage = (AbstractMessage)theEObject;
        T result = caseAbstractMessage(abstractMessage);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case LwMessagePackage.REQUEST_MESSAGE:
      {
        RequestMessage requestMessage = (RequestMessage)theEObject;
        T result = caseRequestMessage(requestMessage);
        if (result == null) result = caseAbstractMessage(requestMessage);
        if (result == null) result = caseMessageRequestArguments(requestMessage);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case LwMessagePackage.ANY_MESSAGE:
      {
        AnyMessage anyMessage = (AnyMessage)theEObject;
        T result = caseAnyMessage(anyMessage);
        if (result == null) result = caseAbstractMessage(anyMessage);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case LwMessagePackage.MESSAGE_REQUEST_ARGUMENTS:
      {
        MessageRequestArguments messageRequestArguments = (MessageRequestArguments)theEObject;
        T result = caseMessageRequestArguments(messageRequestArguments);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case LwMessagePackage.MESSAGE_ARGUMENT:
      {
        MessageArgument messageArgument = (MessageArgument)theEObject;
        T result = caseMessageArgument(messageArgument);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case LwMessagePackage.REPLY_MESSAGE:
      {
        ReplyMessage replyMessage = (ReplyMessage)theEObject;
        T result = caseReplyMessage(replyMessage);
        if (result == null) result = caseAbstractMessage(replyMessage);
        if (result == null) result = caseAssignmentTarget(replyMessage);
        if (result == null) result = caseMessageReplyOutputs(replyMessage);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case LwMessagePackage.ASSIGNMENT_TARGET:
      {
        AssignmentTarget assignmentTarget = (AssignmentTarget)theEObject;
        T result = caseAssignmentTarget(assignmentTarget);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case LwMessagePackage.MESSAGE_REPLY_OUTPUTS:
      {
        MessageReplyOutputs messageReplyOutputs = (MessageReplyOutputs)theEObject;
        T result = caseMessageReplyOutputs(messageReplyOutputs);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case LwMessagePackage.MESSAGE_REPLY_OUTPUT:
      {
        MessageReplyOutput messageReplyOutput = (MessageReplyOutput)theEObject;
        T result = caseMessageReplyOutput(messageReplyOutput);
        if (result == null) result = caseAssignmentTarget(messageReplyOutput);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case LwMessagePackage.VALUE:
      {
        Value value = (Value)theEObject;
        T result = caseValue(value);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case LwMessagePackage.BOOLEAN_VALUE:
      {
        BooleanValue booleanValue = (BooleanValue)theEObject;
        T result = caseBooleanValue(booleanValue);
        if (result == null) result = caseValue(booleanValue);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case LwMessagePackage.INTEGER_VALUE:
      {
        IntegerValue integerValue = (IntegerValue)theEObject;
        T result = caseIntegerValue(integerValue);
        if (result == null) result = caseValue(integerValue);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case LwMessagePackage.UNLIMITED_NATURAL_VALUE:
      {
        UnlimitedNaturalValue unlimitedNaturalValue = (UnlimitedNaturalValue)theEObject;
        T result = caseUnlimitedNaturalValue(unlimitedNaturalValue);
        if (result == null) result = caseValue(unlimitedNaturalValue);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case LwMessagePackage.REAL_VALUE:
      {
        RealValue realValue = (RealValue)theEObject;
        T result = caseRealValue(realValue);
        if (result == null) result = caseValue(realValue);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case LwMessagePackage.NULL_VALUE:
      {
        NullValue nullValue = (NullValue)theEObject;
        T result = caseNullValue(nullValue);
        if (result == null) result = caseValue(nullValue);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case LwMessagePackage.STRING_VALUE:
      {
        StringValue stringValue = (StringValue)theEObject;
        T result = caseStringValue(stringValue);
        if (result == null) result = caseValue(stringValue);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case LwMessagePackage.UNDEFINED_RULE:
      {
        UndefinedRule undefinedRule = (UndefinedRule)theEObject;
        T result = caseUndefinedRule(undefinedRule);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case LwMessagePackage.WILDCARD_MESSAGE_ARGUMENT:
      {
        WildcardMessageArgument wildcardMessageArgument = (WildcardMessageArgument)theEObject;
        T result = caseWildcardMessageArgument(wildcardMessageArgument);
        if (result == null) result = caseMessageArgument(wildcardMessageArgument);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      default: return defaultCase(theEObject);
    }
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Abstract Message</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Abstract Message</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAbstractMessage(AbstractMessage object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Request Message</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Request Message</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseRequestMessage(RequestMessage object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Any Message</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Any Message</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAnyMessage(AnyMessage object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Message Request Arguments</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Message Request Arguments</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseMessageRequestArguments(MessageRequestArguments object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Message Argument</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Message Argument</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseMessageArgument(MessageArgument object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Reply Message</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Reply Message</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseReplyMessage(ReplyMessage object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Assignment Target</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Assignment Target</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAssignmentTarget(AssignmentTarget object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Message Reply Outputs</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Message Reply Outputs</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseMessageReplyOutputs(MessageReplyOutputs object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Message Reply Output</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Message Reply Output</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseMessageReplyOutput(MessageReplyOutput object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Value</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Value</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseValue(Value object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Boolean Value</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Boolean Value</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseBooleanValue(BooleanValue object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Integer Value</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Integer Value</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseIntegerValue(IntegerValue object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Unlimited Natural Value</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Unlimited Natural Value</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseUnlimitedNaturalValue(UnlimitedNaturalValue object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Real Value</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Real Value</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseRealValue(RealValue object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Null Value</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Null Value</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseNullValue(NullValue object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>String Value</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>String Value</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseStringValue(StringValue object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Undefined Rule</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Undefined Rule</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseUndefinedRule(UndefinedRule object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Wildcard Message Argument</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Wildcard Message Argument</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseWildcardMessageArgument(WildcardMessageArgument object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch, but this is the last case anyway.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject)
   * @generated
   */
  @Override
  public T defaultCase(EObject object)
  {
    return null;
  }

} //LwMessageSwitch
