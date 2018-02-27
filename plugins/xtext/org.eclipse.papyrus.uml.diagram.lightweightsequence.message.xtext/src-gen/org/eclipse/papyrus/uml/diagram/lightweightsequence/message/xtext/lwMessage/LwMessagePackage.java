/**
 */
package org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.LwMessageFactory
 * @model kind="package"
 * @generated
 */
public interface LwMessagePackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "lwMessage";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.eclipse.org/papyrus/uml/diagram/lightweightsequence/message/xtext/LwMessage";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "lwMessage";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  LwMessagePackage eINSTANCE = org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl.init();

  /**
   * The meta object id for the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.AbstractMessageImpl <em>Abstract Message</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.AbstractMessageImpl
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl#getAbstractMessage()
   * @generated
   */
  int ABSTRACT_MESSAGE = 0;

  /**
   * The number of structural features of the '<em>Abstract Message</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ABSTRACT_MESSAGE_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.RequestMessageImpl <em>Request Message</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.RequestMessageImpl
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl#getRequestMessage()
   * @generated
   */
  int REQUEST_MESSAGE = 1;

  /**
   * The feature id for the '<em><b>Arguments</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REQUEST_MESSAGE__ARGUMENTS = ABSTRACT_MESSAGE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REQUEST_MESSAGE__NAME = ABSTRACT_MESSAGE_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Signal</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REQUEST_MESSAGE__SIGNAL = ABSTRACT_MESSAGE_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Operation</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REQUEST_MESSAGE__OPERATION = ABSTRACT_MESSAGE_FEATURE_COUNT + 3;

  /**
   * The number of structural features of the '<em>Request Message</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REQUEST_MESSAGE_FEATURE_COUNT = ABSTRACT_MESSAGE_FEATURE_COUNT + 4;

  /**
   * The meta object id for the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.AnyMessageImpl <em>Any Message</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.AnyMessageImpl
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl#getAnyMessage()
   * @generated
   */
  int ANY_MESSAGE = 2;

  /**
   * The number of structural features of the '<em>Any Message</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANY_MESSAGE_FEATURE_COUNT = ABSTRACT_MESSAGE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.MessageRequestArgumentsImpl <em>Message Request Arguments</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.MessageRequestArgumentsImpl
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl#getMessageRequestArguments()
   * @generated
   */
  int MESSAGE_REQUEST_ARGUMENTS = 3;

  /**
   * The feature id for the '<em><b>Arguments</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MESSAGE_REQUEST_ARGUMENTS__ARGUMENTS = 0;

  /**
   * The number of structural features of the '<em>Message Request Arguments</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MESSAGE_REQUEST_ARGUMENTS_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.MessageArgumentImpl <em>Message Argument</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.MessageArgumentImpl
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl#getMessageArgument()
   * @generated
   */
  int MESSAGE_ARGUMENT = 4;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MESSAGE_ARGUMENT__NAME = 0;

  /**
   * The feature id for the '<em><b>Property</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MESSAGE_ARGUMENT__PROPERTY = 1;

  /**
   * The feature id for the '<em><b>Parameter</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MESSAGE_ARGUMENT__PARAMETER = 2;

  /**
   * The feature id for the '<em><b>Value</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MESSAGE_ARGUMENT__VALUE = 3;

  /**
   * The number of structural features of the '<em>Message Argument</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MESSAGE_ARGUMENT_FEATURE_COUNT = 4;

  /**
   * The meta object id for the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.ReplyMessageImpl <em>Reply Message</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.ReplyMessageImpl
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl#getReplyMessage()
   * @generated
   */
  int REPLY_MESSAGE = 5;

  /**
   * The feature id for the '<em><b>Target</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REPLY_MESSAGE__TARGET = ABSTRACT_MESSAGE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Value</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REPLY_MESSAGE__VALUE = ABSTRACT_MESSAGE_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Outputs</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REPLY_MESSAGE__OUTPUTS = ABSTRACT_MESSAGE_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REPLY_MESSAGE__NAME = ABSTRACT_MESSAGE_FEATURE_COUNT + 3;

  /**
   * The feature id for the '<em><b>Operation</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REPLY_MESSAGE__OPERATION = ABSTRACT_MESSAGE_FEATURE_COUNT + 4;

  /**
   * The number of structural features of the '<em>Reply Message</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REPLY_MESSAGE_FEATURE_COUNT = ABSTRACT_MESSAGE_FEATURE_COUNT + 5;

  /**
   * The meta object id for the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.AssignmentTargetImpl <em>Assignment Target</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.AssignmentTargetImpl
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl#getAssignmentTarget()
   * @generated
   */
  int ASSIGNMENT_TARGET = 6;

  /**
   * The feature id for the '<em><b>Target</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGNMENT_TARGET__TARGET = 0;

  /**
   * The feature id for the '<em><b>Value</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGNMENT_TARGET__VALUE = 1;

  /**
   * The number of structural features of the '<em>Assignment Target</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGNMENT_TARGET_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.MessageReplyOutputsImpl <em>Message Reply Outputs</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.MessageReplyOutputsImpl
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl#getMessageReplyOutputs()
   * @generated
   */
  int MESSAGE_REPLY_OUTPUTS = 7;

  /**
   * The feature id for the '<em><b>Outputs</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MESSAGE_REPLY_OUTPUTS__OUTPUTS = 0;

  /**
   * The number of structural features of the '<em>Message Reply Outputs</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MESSAGE_REPLY_OUTPUTS_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.MessageReplyOutputImpl <em>Message Reply Output</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.MessageReplyOutputImpl
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl#getMessageReplyOutput()
   * @generated
   */
  int MESSAGE_REPLY_OUTPUT = 8;

  /**
   * The feature id for the '<em><b>Target</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MESSAGE_REPLY_OUTPUT__TARGET = ASSIGNMENT_TARGET__TARGET;

  /**
   * The feature id for the '<em><b>Value</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MESSAGE_REPLY_OUTPUT__VALUE = ASSIGNMENT_TARGET__VALUE;

  /**
   * The feature id for the '<em><b>Parameter</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MESSAGE_REPLY_OUTPUT__PARAMETER = ASSIGNMENT_TARGET_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Message Reply Output</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MESSAGE_REPLY_OUTPUT_FEATURE_COUNT = ASSIGNMENT_TARGET_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.ValueImpl <em>Value</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.ValueImpl
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl#getValue()
   * @generated
   */
  int VALUE = 9;

  /**
   * The number of structural features of the '<em>Value</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VALUE_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.BooleanValueImpl <em>Boolean Value</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.BooleanValueImpl
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl#getBooleanValue()
   * @generated
   */
  int BOOLEAN_VALUE = 10;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BOOLEAN_VALUE__VALUE = VALUE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Boolean Value</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BOOLEAN_VALUE_FEATURE_COUNT = VALUE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.IntegerValueImpl <em>Integer Value</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.IntegerValueImpl
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl#getIntegerValue()
   * @generated
   */
  int INTEGER_VALUE = 11;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEGER_VALUE__VALUE = VALUE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Integer Value</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEGER_VALUE_FEATURE_COUNT = VALUE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.UnlimitedNaturalValueImpl <em>Unlimited Natural Value</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.UnlimitedNaturalValueImpl
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl#getUnlimitedNaturalValue()
   * @generated
   */
  int UNLIMITED_NATURAL_VALUE = 12;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNLIMITED_NATURAL_VALUE__VALUE = VALUE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Unlimited Natural Value</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNLIMITED_NATURAL_VALUE_FEATURE_COUNT = VALUE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.RealValueImpl <em>Real Value</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.RealValueImpl
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl#getRealValue()
   * @generated
   */
  int REAL_VALUE = 13;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REAL_VALUE__VALUE = VALUE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Real Value</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REAL_VALUE_FEATURE_COUNT = VALUE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.NullValueImpl <em>Null Value</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.NullValueImpl
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl#getNullValue()
   * @generated
   */
  int NULL_VALUE = 14;

  /**
   * The number of structural features of the '<em>Null Value</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NULL_VALUE_FEATURE_COUNT = VALUE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.StringValueImpl <em>String Value</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.StringValueImpl
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl#getStringValue()
   * @generated
   */
  int STRING_VALUE = 15;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STRING_VALUE__VALUE = VALUE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>String Value</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STRING_VALUE_FEATURE_COUNT = VALUE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.UndefinedRuleImpl <em>Undefined Rule</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.UndefinedRuleImpl
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl#getUndefinedRule()
   * @generated
   */
  int UNDEFINED_RULE = 16;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNDEFINED_RULE__VALUE = 0;

  /**
   * The number of structural features of the '<em>Undefined Rule</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNDEFINED_RULE_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.WildcardMessageArgumentImpl <em>Wildcard Message Argument</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.WildcardMessageArgumentImpl
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl#getWildcardMessageArgument()
   * @generated
   */
  int WILDCARD_MESSAGE_ARGUMENT = 17;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WILDCARD_MESSAGE_ARGUMENT__NAME = MESSAGE_ARGUMENT__NAME;

  /**
   * The feature id for the '<em><b>Property</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WILDCARD_MESSAGE_ARGUMENT__PROPERTY = MESSAGE_ARGUMENT__PROPERTY;

  /**
   * The feature id for the '<em><b>Parameter</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WILDCARD_MESSAGE_ARGUMENT__PARAMETER = MESSAGE_ARGUMENT__PARAMETER;

  /**
   * The feature id for the '<em><b>Value</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WILDCARD_MESSAGE_ARGUMENT__VALUE = MESSAGE_ARGUMENT__VALUE;

  /**
   * The number of structural features of the '<em>Wildcard Message Argument</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WILDCARD_MESSAGE_ARGUMENT_FEATURE_COUNT = MESSAGE_ARGUMENT_FEATURE_COUNT + 0;


  /**
   * Returns the meta object for class '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.AbstractMessage <em>Abstract Message</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Abstract Message</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.AbstractMessage
   * @generated
   */
  EClass getAbstractMessage();

  /**
   * Returns the meta object for class '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.RequestMessage <em>Request Message</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Request Message</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.RequestMessage
   * @generated
   */
  EClass getRequestMessage();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.RequestMessage#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.RequestMessage#getName()
   * @see #getRequestMessage()
   * @generated
   */
  EAttribute getRequestMessage_Name();

  /**
   * Returns the meta object for the reference '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.RequestMessage#getSignal <em>Signal</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Signal</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.RequestMessage#getSignal()
   * @see #getRequestMessage()
   * @generated
   */
  EReference getRequestMessage_Signal();

  /**
   * Returns the meta object for the reference '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.RequestMessage#getOperation <em>Operation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Operation</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.RequestMessage#getOperation()
   * @see #getRequestMessage()
   * @generated
   */
  EReference getRequestMessage_Operation();

  /**
   * Returns the meta object for class '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.AnyMessage <em>Any Message</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Any Message</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.AnyMessage
   * @generated
   */
  EClass getAnyMessage();

  /**
   * Returns the meta object for class '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageRequestArguments <em>Message Request Arguments</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Message Request Arguments</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageRequestArguments
   * @generated
   */
  EClass getMessageRequestArguments();

  /**
   * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageRequestArguments#getArguments <em>Arguments</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Arguments</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageRequestArguments#getArguments()
   * @see #getMessageRequestArguments()
   * @generated
   */
  EReference getMessageRequestArguments_Arguments();

  /**
   * Returns the meta object for class '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageArgument <em>Message Argument</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Message Argument</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageArgument
   * @generated
   */
  EClass getMessageArgument();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageArgument#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageArgument#getName()
   * @see #getMessageArgument()
   * @generated
   */
  EAttribute getMessageArgument_Name();

  /**
   * Returns the meta object for the reference '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageArgument#getProperty <em>Property</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Property</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageArgument#getProperty()
   * @see #getMessageArgument()
   * @generated
   */
  EReference getMessageArgument_Property();

  /**
   * Returns the meta object for the reference '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageArgument#getParameter <em>Parameter</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Parameter</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageArgument#getParameter()
   * @see #getMessageArgument()
   * @generated
   */
  EReference getMessageArgument_Parameter();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageArgument#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Value</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageArgument#getValue()
   * @see #getMessageArgument()
   * @generated
   */
  EReference getMessageArgument_Value();

  /**
   * Returns the meta object for class '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.ReplyMessage <em>Reply Message</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Reply Message</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.ReplyMessage
   * @generated
   */
  EClass getReplyMessage();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.ReplyMessage#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.ReplyMessage#getName()
   * @see #getReplyMessage()
   * @generated
   */
  EAttribute getReplyMessage_Name();

  /**
   * Returns the meta object for the reference '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.ReplyMessage#getOperation <em>Operation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Operation</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.ReplyMessage#getOperation()
   * @see #getReplyMessage()
   * @generated
   */
  EReference getReplyMessage_Operation();

  /**
   * Returns the meta object for class '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.AssignmentTarget <em>Assignment Target</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Assignment Target</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.AssignmentTarget
   * @generated
   */
  EClass getAssignmentTarget();

  /**
   * Returns the meta object for the reference '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.AssignmentTarget#getTarget <em>Target</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Target</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.AssignmentTarget#getTarget()
   * @see #getAssignmentTarget()
   * @generated
   */
  EReference getAssignmentTarget_Target();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.AssignmentTarget#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Value</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.AssignmentTarget#getValue()
   * @see #getAssignmentTarget()
   * @generated
   */
  EReference getAssignmentTarget_Value();

  /**
   * Returns the meta object for class '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageReplyOutputs <em>Message Reply Outputs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Message Reply Outputs</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageReplyOutputs
   * @generated
   */
  EClass getMessageReplyOutputs();

  /**
   * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageReplyOutputs#getOutputs <em>Outputs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Outputs</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageReplyOutputs#getOutputs()
   * @see #getMessageReplyOutputs()
   * @generated
   */
  EReference getMessageReplyOutputs_Outputs();

  /**
   * Returns the meta object for class '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageReplyOutput <em>Message Reply Output</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Message Reply Output</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageReplyOutput
   * @generated
   */
  EClass getMessageReplyOutput();

  /**
   * Returns the meta object for the reference '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageReplyOutput#getParameter <em>Parameter</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Parameter</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageReplyOutput#getParameter()
   * @see #getMessageReplyOutput()
   * @generated
   */
  EReference getMessageReplyOutput_Parameter();

  /**
   * Returns the meta object for class '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.Value <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Value</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.Value
   * @generated
   */
  EClass getValue();

  /**
   * Returns the meta object for class '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.BooleanValue <em>Boolean Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Boolean Value</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.BooleanValue
   * @generated
   */
  EClass getBooleanValue();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.BooleanValue#isValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.BooleanValue#isValue()
   * @see #getBooleanValue()
   * @generated
   */
  EAttribute getBooleanValue_Value();

  /**
   * Returns the meta object for class '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.IntegerValue <em>Integer Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Integer Value</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.IntegerValue
   * @generated
   */
  EClass getIntegerValue();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.IntegerValue#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.IntegerValue#getValue()
   * @see #getIntegerValue()
   * @generated
   */
  EAttribute getIntegerValue_Value();

  /**
   * Returns the meta object for class '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.UnlimitedNaturalValue <em>Unlimited Natural Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Unlimited Natural Value</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.UnlimitedNaturalValue
   * @generated
   */
  EClass getUnlimitedNaturalValue();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.UnlimitedNaturalValue#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.UnlimitedNaturalValue#getValue()
   * @see #getUnlimitedNaturalValue()
   * @generated
   */
  EAttribute getUnlimitedNaturalValue_Value();

  /**
   * Returns the meta object for class '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.RealValue <em>Real Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Real Value</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.RealValue
   * @generated
   */
  EClass getRealValue();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.RealValue#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.RealValue#getValue()
   * @see #getRealValue()
   * @generated
   */
  EAttribute getRealValue_Value();

  /**
   * Returns the meta object for class '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.NullValue <em>Null Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Null Value</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.NullValue
   * @generated
   */
  EClass getNullValue();

  /**
   * Returns the meta object for class '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.StringValue <em>String Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>String Value</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.StringValue
   * @generated
   */
  EClass getStringValue();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.StringValue#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.StringValue#getValue()
   * @see #getStringValue()
   * @generated
   */
  EAttribute getStringValue_Value();

  /**
   * Returns the meta object for class '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.UndefinedRule <em>Undefined Rule</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Undefined Rule</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.UndefinedRule
   * @generated
   */
  EClass getUndefinedRule();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.UndefinedRule#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.UndefinedRule#getValue()
   * @see #getUndefinedRule()
   * @generated
   */
  EAttribute getUndefinedRule_Value();

  /**
   * Returns the meta object for class '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.WildcardMessageArgument <em>Wildcard Message Argument</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Wildcard Message Argument</em>'.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.WildcardMessageArgument
   * @generated
   */
  EClass getWildcardMessageArgument();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  LwMessageFactory getLwMessageFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals
  {
    /**
     * The meta object literal for the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.AbstractMessageImpl <em>Abstract Message</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.AbstractMessageImpl
     * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl#getAbstractMessage()
     * @generated
     */
    EClass ABSTRACT_MESSAGE = eINSTANCE.getAbstractMessage();

    /**
     * The meta object literal for the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.RequestMessageImpl <em>Request Message</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.RequestMessageImpl
     * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl#getRequestMessage()
     * @generated
     */
    EClass REQUEST_MESSAGE = eINSTANCE.getRequestMessage();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute REQUEST_MESSAGE__NAME = eINSTANCE.getRequestMessage_Name();

    /**
     * The meta object literal for the '<em><b>Signal</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference REQUEST_MESSAGE__SIGNAL = eINSTANCE.getRequestMessage_Signal();

    /**
     * The meta object literal for the '<em><b>Operation</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference REQUEST_MESSAGE__OPERATION = eINSTANCE.getRequestMessage_Operation();

    /**
     * The meta object literal for the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.AnyMessageImpl <em>Any Message</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.AnyMessageImpl
     * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl#getAnyMessage()
     * @generated
     */
    EClass ANY_MESSAGE = eINSTANCE.getAnyMessage();

    /**
     * The meta object literal for the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.MessageRequestArgumentsImpl <em>Message Request Arguments</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.MessageRequestArgumentsImpl
     * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl#getMessageRequestArguments()
     * @generated
     */
    EClass MESSAGE_REQUEST_ARGUMENTS = eINSTANCE.getMessageRequestArguments();

    /**
     * The meta object literal for the '<em><b>Arguments</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MESSAGE_REQUEST_ARGUMENTS__ARGUMENTS = eINSTANCE.getMessageRequestArguments_Arguments();

    /**
     * The meta object literal for the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.MessageArgumentImpl <em>Message Argument</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.MessageArgumentImpl
     * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl#getMessageArgument()
     * @generated
     */
    EClass MESSAGE_ARGUMENT = eINSTANCE.getMessageArgument();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MESSAGE_ARGUMENT__NAME = eINSTANCE.getMessageArgument_Name();

    /**
     * The meta object literal for the '<em><b>Property</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MESSAGE_ARGUMENT__PROPERTY = eINSTANCE.getMessageArgument_Property();

    /**
     * The meta object literal for the '<em><b>Parameter</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MESSAGE_ARGUMENT__PARAMETER = eINSTANCE.getMessageArgument_Parameter();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MESSAGE_ARGUMENT__VALUE = eINSTANCE.getMessageArgument_Value();

    /**
     * The meta object literal for the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.ReplyMessageImpl <em>Reply Message</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.ReplyMessageImpl
     * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl#getReplyMessage()
     * @generated
     */
    EClass REPLY_MESSAGE = eINSTANCE.getReplyMessage();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute REPLY_MESSAGE__NAME = eINSTANCE.getReplyMessage_Name();

    /**
     * The meta object literal for the '<em><b>Operation</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference REPLY_MESSAGE__OPERATION = eINSTANCE.getReplyMessage_Operation();

    /**
     * The meta object literal for the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.AssignmentTargetImpl <em>Assignment Target</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.AssignmentTargetImpl
     * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl#getAssignmentTarget()
     * @generated
     */
    EClass ASSIGNMENT_TARGET = eINSTANCE.getAssignmentTarget();

    /**
     * The meta object literal for the '<em><b>Target</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ASSIGNMENT_TARGET__TARGET = eINSTANCE.getAssignmentTarget_Target();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ASSIGNMENT_TARGET__VALUE = eINSTANCE.getAssignmentTarget_Value();

    /**
     * The meta object literal for the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.MessageReplyOutputsImpl <em>Message Reply Outputs</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.MessageReplyOutputsImpl
     * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl#getMessageReplyOutputs()
     * @generated
     */
    EClass MESSAGE_REPLY_OUTPUTS = eINSTANCE.getMessageReplyOutputs();

    /**
     * The meta object literal for the '<em><b>Outputs</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MESSAGE_REPLY_OUTPUTS__OUTPUTS = eINSTANCE.getMessageReplyOutputs_Outputs();

    /**
     * The meta object literal for the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.MessageReplyOutputImpl <em>Message Reply Output</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.MessageReplyOutputImpl
     * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl#getMessageReplyOutput()
     * @generated
     */
    EClass MESSAGE_REPLY_OUTPUT = eINSTANCE.getMessageReplyOutput();

    /**
     * The meta object literal for the '<em><b>Parameter</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MESSAGE_REPLY_OUTPUT__PARAMETER = eINSTANCE.getMessageReplyOutput_Parameter();

    /**
     * The meta object literal for the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.ValueImpl <em>Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.ValueImpl
     * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl#getValue()
     * @generated
     */
    EClass VALUE = eINSTANCE.getValue();

    /**
     * The meta object literal for the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.BooleanValueImpl <em>Boolean Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.BooleanValueImpl
     * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl#getBooleanValue()
     * @generated
     */
    EClass BOOLEAN_VALUE = eINSTANCE.getBooleanValue();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute BOOLEAN_VALUE__VALUE = eINSTANCE.getBooleanValue_Value();

    /**
     * The meta object literal for the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.IntegerValueImpl <em>Integer Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.IntegerValueImpl
     * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl#getIntegerValue()
     * @generated
     */
    EClass INTEGER_VALUE = eINSTANCE.getIntegerValue();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INTEGER_VALUE__VALUE = eINSTANCE.getIntegerValue_Value();

    /**
     * The meta object literal for the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.UnlimitedNaturalValueImpl <em>Unlimited Natural Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.UnlimitedNaturalValueImpl
     * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl#getUnlimitedNaturalValue()
     * @generated
     */
    EClass UNLIMITED_NATURAL_VALUE = eINSTANCE.getUnlimitedNaturalValue();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute UNLIMITED_NATURAL_VALUE__VALUE = eINSTANCE.getUnlimitedNaturalValue_Value();

    /**
     * The meta object literal for the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.RealValueImpl <em>Real Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.RealValueImpl
     * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl#getRealValue()
     * @generated
     */
    EClass REAL_VALUE = eINSTANCE.getRealValue();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute REAL_VALUE__VALUE = eINSTANCE.getRealValue_Value();

    /**
     * The meta object literal for the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.NullValueImpl <em>Null Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.NullValueImpl
     * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl#getNullValue()
     * @generated
     */
    EClass NULL_VALUE = eINSTANCE.getNullValue();

    /**
     * The meta object literal for the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.StringValueImpl <em>String Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.StringValueImpl
     * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl#getStringValue()
     * @generated
     */
    EClass STRING_VALUE = eINSTANCE.getStringValue();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute STRING_VALUE__VALUE = eINSTANCE.getStringValue_Value();

    /**
     * The meta object literal for the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.UndefinedRuleImpl <em>Undefined Rule</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.UndefinedRuleImpl
     * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl#getUndefinedRule()
     * @generated
     */
    EClass UNDEFINED_RULE = eINSTANCE.getUndefinedRule();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute UNDEFINED_RULE__VALUE = eINSTANCE.getUndefinedRule_Value();

    /**
     * The meta object literal for the '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.WildcardMessageArgumentImpl <em>Wildcard Message Argument</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.WildcardMessageArgumentImpl
     * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.LwMessagePackageImpl#getWildcardMessageArgument()
     * @generated
     */
    EClass WILDCARD_MESSAGE_ARGUMENT = eINSTANCE.getWildcardMessageArgument();

  }

} //LwMessagePackage
