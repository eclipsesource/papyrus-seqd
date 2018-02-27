/**
 */
package org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class LwMessageFactoryImpl extends EFactoryImpl implements LwMessageFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static LwMessageFactory init()
  {
    try
    {
      LwMessageFactory theLwMessageFactory = (LwMessageFactory)EPackage.Registry.INSTANCE.getEFactory(LwMessagePackage.eNS_URI);
      if (theLwMessageFactory != null)
      {
        return theLwMessageFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new LwMessageFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LwMessageFactoryImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass)
  {
    switch (eClass.getClassifierID())
    {
      case LwMessagePackage.ABSTRACT_MESSAGE: return createAbstractMessage();
      case LwMessagePackage.REQUEST_MESSAGE: return createRequestMessage();
      case LwMessagePackage.ANY_MESSAGE: return createAnyMessage();
      case LwMessagePackage.MESSAGE_REQUEST_ARGUMENTS: return createMessageRequestArguments();
      case LwMessagePackage.MESSAGE_ARGUMENT: return createMessageArgument();
      case LwMessagePackage.REPLY_MESSAGE: return createReplyMessage();
      case LwMessagePackage.ASSIGNMENT_TARGET: return createAssignmentTarget();
      case LwMessagePackage.MESSAGE_REPLY_OUTPUTS: return createMessageReplyOutputs();
      case LwMessagePackage.MESSAGE_REPLY_OUTPUT: return createMessageReplyOutput();
      case LwMessagePackage.VALUE: return createValue();
      case LwMessagePackage.BOOLEAN_VALUE: return createBooleanValue();
      case LwMessagePackage.INTEGER_VALUE: return createIntegerValue();
      case LwMessagePackage.UNLIMITED_NATURAL_VALUE: return createUnlimitedNaturalValue();
      case LwMessagePackage.REAL_VALUE: return createRealValue();
      case LwMessagePackage.NULL_VALUE: return createNullValue();
      case LwMessagePackage.STRING_VALUE: return createStringValue();
      case LwMessagePackage.UNDEFINED_RULE: return createUndefinedRule();
      case LwMessagePackage.WILDCARD_MESSAGE_ARGUMENT: return createWildcardMessageArgument();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AbstractMessage createAbstractMessage()
  {
    AbstractMessageImpl abstractMessage = new AbstractMessageImpl();
    return abstractMessage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RequestMessage createRequestMessage()
  {
    RequestMessageImpl requestMessage = new RequestMessageImpl();
    return requestMessage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AnyMessage createAnyMessage()
  {
    AnyMessageImpl anyMessage = new AnyMessageImpl();
    return anyMessage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MessageRequestArguments createMessageRequestArguments()
  {
    MessageRequestArgumentsImpl messageRequestArguments = new MessageRequestArgumentsImpl();
    return messageRequestArguments;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MessageArgument createMessageArgument()
  {
    MessageArgumentImpl messageArgument = new MessageArgumentImpl();
    return messageArgument;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ReplyMessage createReplyMessage()
  {
    ReplyMessageImpl replyMessage = new ReplyMessageImpl();
    return replyMessage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AssignmentTarget createAssignmentTarget()
  {
    AssignmentTargetImpl assignmentTarget = new AssignmentTargetImpl();
    return assignmentTarget;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MessageReplyOutputs createMessageReplyOutputs()
  {
    MessageReplyOutputsImpl messageReplyOutputs = new MessageReplyOutputsImpl();
    return messageReplyOutputs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MessageReplyOutput createMessageReplyOutput()
  {
    MessageReplyOutputImpl messageReplyOutput = new MessageReplyOutputImpl();
    return messageReplyOutput;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Value createValue()
  {
    ValueImpl value = new ValueImpl();
    return value;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BooleanValue createBooleanValue()
  {
    BooleanValueImpl booleanValue = new BooleanValueImpl();
    return booleanValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IntegerValue createIntegerValue()
  {
    IntegerValueImpl integerValue = new IntegerValueImpl();
    return integerValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public UnlimitedNaturalValue createUnlimitedNaturalValue()
  {
    UnlimitedNaturalValueImpl unlimitedNaturalValue = new UnlimitedNaturalValueImpl();
    return unlimitedNaturalValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RealValue createRealValue()
  {
    RealValueImpl realValue = new RealValueImpl();
    return realValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NullValue createNullValue()
  {
    NullValueImpl nullValue = new NullValueImpl();
    return nullValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public StringValue createStringValue()
  {
    StringValueImpl stringValue = new StringValueImpl();
    return stringValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public UndefinedRule createUndefinedRule()
  {
    UndefinedRuleImpl undefinedRule = new UndefinedRuleImpl();
    return undefinedRule;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public WildcardMessageArgument createWildcardMessageArgument()
  {
    WildcardMessageArgumentImpl wildcardMessageArgument = new WildcardMessageArgumentImpl();
    return wildcardMessageArgument;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LwMessagePackage getLwMessagePackage()
  {
    return (LwMessagePackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static LwMessagePackage getPackage()
  {
    return LwMessagePackage.eINSTANCE;
  }

} //LwMessageFactoryImpl
