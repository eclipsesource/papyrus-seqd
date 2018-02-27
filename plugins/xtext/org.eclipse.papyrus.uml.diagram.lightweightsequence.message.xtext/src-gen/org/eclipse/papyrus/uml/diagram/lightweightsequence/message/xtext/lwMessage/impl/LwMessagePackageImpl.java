/**
 */
package org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.AbstractMessage;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.AnyMessage;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.AssignmentTarget;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.BooleanValue;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.IntegerValue;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.LwMessageFactory;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.LwMessagePackage;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageArgument;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageReplyOutput;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageReplyOutputs;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageRequestArguments;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.NullValue;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.RealValue;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.ReplyMessage;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.RequestMessage;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.StringValue;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.UndefinedRule;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.UnlimitedNaturalValue;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.Value;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.WildcardMessageArgument;

import org.eclipse.uml2.types.TypesPackage;

import org.eclipse.uml2.uml.UMLPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class LwMessagePackageImpl extends EPackageImpl implements LwMessagePackage
{
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass abstractMessageEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass requestMessageEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass anyMessageEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass messageRequestArgumentsEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass messageArgumentEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass replyMessageEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass assignmentTargetEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass messageReplyOutputsEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass messageReplyOutputEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass valueEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass booleanValueEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass integerValueEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass unlimitedNaturalValueEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass realValueEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass nullValueEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass stringValueEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass undefinedRuleEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass wildcardMessageArgumentEClass = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
   * package URI value.
   * <p>Note: the correct way to create the package is via the static
   * factory method {@link #init init()}, which also performs
   * initialization of the package, or returns the registered package,
   * if one already exists.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.LwMessagePackage#eNS_URI
   * @see #init()
   * @generated
   */
  private LwMessagePackageImpl()
  {
    super(eNS_URI, LwMessageFactory.eINSTANCE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
   * 
   * <p>This method is used to initialize {@link LwMessagePackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static LwMessagePackage init()
  {
    if (isInited) return (LwMessagePackage)EPackage.Registry.INSTANCE.getEPackage(LwMessagePackage.eNS_URI);

    // Obtain or create and register package
    LwMessagePackageImpl theLwMessagePackage = (LwMessagePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof LwMessagePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new LwMessagePackageImpl());

    isInited = true;

    // Initialize simple dependencies
    EcorePackage.eINSTANCE.eClass();
    UMLPackage.eINSTANCE.eClass();
    TypesPackage.eINSTANCE.eClass();

    // Create package meta-data objects
    theLwMessagePackage.createPackageContents();

    // Initialize created meta-data
    theLwMessagePackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theLwMessagePackage.freeze();

  
    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(LwMessagePackage.eNS_URI, theLwMessagePackage);
    return theLwMessagePackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getAbstractMessage()
  {
    return abstractMessageEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getRequestMessage()
  {
    return requestMessageEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getRequestMessage_Name()
  {
    return (EAttribute)requestMessageEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getRequestMessage_Signal()
  {
    return (EReference)requestMessageEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getRequestMessage_Operation()
  {
    return (EReference)requestMessageEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getAnyMessage()
  {
    return anyMessageEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getMessageRequestArguments()
  {
    return messageRequestArgumentsEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMessageRequestArguments_Arguments()
  {
    return (EReference)messageRequestArgumentsEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getMessageArgument()
  {
    return messageArgumentEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getMessageArgument_Name()
  {
    return (EAttribute)messageArgumentEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMessageArgument_Property()
  {
    return (EReference)messageArgumentEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMessageArgument_Parameter()
  {
    return (EReference)messageArgumentEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMessageArgument_Value()
  {
    return (EReference)messageArgumentEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getReplyMessage()
  {
    return replyMessageEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getReplyMessage_Name()
  {
    return (EAttribute)replyMessageEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getReplyMessage_Operation()
  {
    return (EReference)replyMessageEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getAssignmentTarget()
  {
    return assignmentTargetEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getAssignmentTarget_Target()
  {
    return (EReference)assignmentTargetEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getAssignmentTarget_Value()
  {
    return (EReference)assignmentTargetEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getMessageReplyOutputs()
  {
    return messageReplyOutputsEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMessageReplyOutputs_Outputs()
  {
    return (EReference)messageReplyOutputsEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getMessageReplyOutput()
  {
    return messageReplyOutputEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMessageReplyOutput_Parameter()
  {
    return (EReference)messageReplyOutputEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getValue()
  {
    return valueEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getBooleanValue()
  {
    return booleanValueEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getBooleanValue_Value()
  {
    return (EAttribute)booleanValueEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getIntegerValue()
  {
    return integerValueEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getIntegerValue_Value()
  {
    return (EAttribute)integerValueEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getUnlimitedNaturalValue()
  {
    return unlimitedNaturalValueEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getUnlimitedNaturalValue_Value()
  {
    return (EAttribute)unlimitedNaturalValueEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getRealValue()
  {
    return realValueEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getRealValue_Value()
  {
    return (EAttribute)realValueEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getNullValue()
  {
    return nullValueEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getStringValue()
  {
    return stringValueEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getStringValue_Value()
  {
    return (EAttribute)stringValueEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getUndefinedRule()
  {
    return undefinedRuleEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getUndefinedRule_Value()
  {
    return (EAttribute)undefinedRuleEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getWildcardMessageArgument()
  {
    return wildcardMessageArgumentEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LwMessageFactory getLwMessageFactory()
  {
    return (LwMessageFactory)getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package.  This method is
   * guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void createPackageContents()
  {
    if (isCreated) return;
    isCreated = true;

    // Create classes and their features
    abstractMessageEClass = createEClass(ABSTRACT_MESSAGE);

    requestMessageEClass = createEClass(REQUEST_MESSAGE);
    createEAttribute(requestMessageEClass, REQUEST_MESSAGE__NAME);
    createEReference(requestMessageEClass, REQUEST_MESSAGE__SIGNAL);
    createEReference(requestMessageEClass, REQUEST_MESSAGE__OPERATION);

    anyMessageEClass = createEClass(ANY_MESSAGE);

    messageRequestArgumentsEClass = createEClass(MESSAGE_REQUEST_ARGUMENTS);
    createEReference(messageRequestArgumentsEClass, MESSAGE_REQUEST_ARGUMENTS__ARGUMENTS);

    messageArgumentEClass = createEClass(MESSAGE_ARGUMENT);
    createEAttribute(messageArgumentEClass, MESSAGE_ARGUMENT__NAME);
    createEReference(messageArgumentEClass, MESSAGE_ARGUMENT__PROPERTY);
    createEReference(messageArgumentEClass, MESSAGE_ARGUMENT__PARAMETER);
    createEReference(messageArgumentEClass, MESSAGE_ARGUMENT__VALUE);

    replyMessageEClass = createEClass(REPLY_MESSAGE);
    createEAttribute(replyMessageEClass, REPLY_MESSAGE__NAME);
    createEReference(replyMessageEClass, REPLY_MESSAGE__OPERATION);

    assignmentTargetEClass = createEClass(ASSIGNMENT_TARGET);
    createEReference(assignmentTargetEClass, ASSIGNMENT_TARGET__TARGET);
    createEReference(assignmentTargetEClass, ASSIGNMENT_TARGET__VALUE);

    messageReplyOutputsEClass = createEClass(MESSAGE_REPLY_OUTPUTS);
    createEReference(messageReplyOutputsEClass, MESSAGE_REPLY_OUTPUTS__OUTPUTS);

    messageReplyOutputEClass = createEClass(MESSAGE_REPLY_OUTPUT);
    createEReference(messageReplyOutputEClass, MESSAGE_REPLY_OUTPUT__PARAMETER);

    valueEClass = createEClass(VALUE);

    booleanValueEClass = createEClass(BOOLEAN_VALUE);
    createEAttribute(booleanValueEClass, BOOLEAN_VALUE__VALUE);

    integerValueEClass = createEClass(INTEGER_VALUE);
    createEAttribute(integerValueEClass, INTEGER_VALUE__VALUE);

    unlimitedNaturalValueEClass = createEClass(UNLIMITED_NATURAL_VALUE);
    createEAttribute(unlimitedNaturalValueEClass, UNLIMITED_NATURAL_VALUE__VALUE);

    realValueEClass = createEClass(REAL_VALUE);
    createEAttribute(realValueEClass, REAL_VALUE__VALUE);

    nullValueEClass = createEClass(NULL_VALUE);

    stringValueEClass = createEClass(STRING_VALUE);
    createEAttribute(stringValueEClass, STRING_VALUE__VALUE);

    undefinedRuleEClass = createEClass(UNDEFINED_RULE);
    createEAttribute(undefinedRuleEClass, UNDEFINED_RULE__VALUE);

    wildcardMessageArgumentEClass = createEClass(WILDCARD_MESSAGE_ARGUMENT);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model.  This
   * method is guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void initializePackageContents()
  {
    if (isInitialized) return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Obtain other dependent packages
    EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);
    UMLPackage theUMLPackage = (UMLPackage)EPackage.Registry.INSTANCE.getEPackage(UMLPackage.eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes
    requestMessageEClass.getESuperTypes().add(this.getAbstractMessage());
    requestMessageEClass.getESuperTypes().add(this.getMessageRequestArguments());
    anyMessageEClass.getESuperTypes().add(this.getAbstractMessage());
    replyMessageEClass.getESuperTypes().add(this.getAbstractMessage());
    replyMessageEClass.getESuperTypes().add(this.getAssignmentTarget());
    replyMessageEClass.getESuperTypes().add(this.getMessageReplyOutputs());
    messageReplyOutputEClass.getESuperTypes().add(this.getAssignmentTarget());
    booleanValueEClass.getESuperTypes().add(this.getValue());
    integerValueEClass.getESuperTypes().add(this.getValue());
    unlimitedNaturalValueEClass.getESuperTypes().add(this.getValue());
    realValueEClass.getESuperTypes().add(this.getValue());
    nullValueEClass.getESuperTypes().add(this.getValue());
    stringValueEClass.getESuperTypes().add(this.getValue());
    wildcardMessageArgumentEClass.getESuperTypes().add(this.getMessageArgument());

    // Initialize classes and features; add operations and parameters
    initEClass(abstractMessageEClass, AbstractMessage.class, "AbstractMessage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(requestMessageEClass, RequestMessage.class, "RequestMessage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getRequestMessage_Name(), theEcorePackage.getEString(), "name", null, 0, 1, RequestMessage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getRequestMessage_Signal(), theUMLPackage.getSignal(), null, "signal", null, 0, 1, RequestMessage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getRequestMessage_Operation(), theUMLPackage.getOperation(), null, "operation", null, 0, 1, RequestMessage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(anyMessageEClass, AnyMessage.class, "AnyMessage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(messageRequestArgumentsEClass, MessageRequestArguments.class, "MessageRequestArguments", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getMessageRequestArguments_Arguments(), this.getMessageArgument(), null, "arguments", null, 0, -1, MessageRequestArguments.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(messageArgumentEClass, MessageArgument.class, "MessageArgument", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getMessageArgument_Name(), theEcorePackage.getEString(), "name", null, 0, 1, MessageArgument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getMessageArgument_Property(), theUMLPackage.getProperty(), null, "property", null, 0, 1, MessageArgument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getMessageArgument_Parameter(), theUMLPackage.getParameter(), null, "parameter", null, 0, 1, MessageArgument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getMessageArgument_Value(), this.getValue(), null, "value", null, 0, 1, MessageArgument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(replyMessageEClass, ReplyMessage.class, "ReplyMessage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getReplyMessage_Name(), theEcorePackage.getEString(), "name", null, 0, 1, ReplyMessage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getReplyMessage_Operation(), theUMLPackage.getOperation(), null, "operation", null, 0, 1, ReplyMessage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(assignmentTargetEClass, AssignmentTarget.class, "AssignmentTarget", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getAssignmentTarget_Target(), theUMLPackage.getConnectableElement(), null, "target", null, 0, 1, AssignmentTarget.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getAssignmentTarget_Value(), this.getMessageArgument(), null, "value", null, 0, 1, AssignmentTarget.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(messageReplyOutputsEClass, MessageReplyOutputs.class, "MessageReplyOutputs", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getMessageReplyOutputs_Outputs(), this.getMessageReplyOutput(), null, "outputs", null, 0, -1, MessageReplyOutputs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(messageReplyOutputEClass, MessageReplyOutput.class, "MessageReplyOutput", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getMessageReplyOutput_Parameter(), theUMLPackage.getParameter(), null, "parameter", null, 0, 1, MessageReplyOutput.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(valueEClass, Value.class, "Value", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(booleanValueEClass, BooleanValue.class, "BooleanValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getBooleanValue_Value(), theEcorePackage.getEBoolean(), "value", null, 0, 1, BooleanValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(integerValueEClass, IntegerValue.class, "IntegerValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getIntegerValue_Value(), theEcorePackage.getEInt(), "value", null, 0, 1, IntegerValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(unlimitedNaturalValueEClass, UnlimitedNaturalValue.class, "UnlimitedNaturalValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getUnlimitedNaturalValue_Value(), theEcorePackage.getEIntegerObject(), "value", null, 0, 1, UnlimitedNaturalValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(realValueEClass, RealValue.class, "RealValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getRealValue_Value(), theEcorePackage.getEDouble(), "value", null, 0, 1, RealValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(nullValueEClass, NullValue.class, "NullValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(stringValueEClass, StringValue.class, "StringValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getStringValue_Value(), theEcorePackage.getEString(), "value", null, 0, 1, StringValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(undefinedRuleEClass, UndefinedRule.class, "UndefinedRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getUndefinedRule_Value(), theEcorePackage.getEString(), "value", null, 0, 1, UndefinedRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(wildcardMessageArgumentEClass, WildcardMessageArgument.class, "WildcardMessageArgument", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    // Create resource
    createResource(eNS_URI);
  }

} //LwMessagePackageImpl
