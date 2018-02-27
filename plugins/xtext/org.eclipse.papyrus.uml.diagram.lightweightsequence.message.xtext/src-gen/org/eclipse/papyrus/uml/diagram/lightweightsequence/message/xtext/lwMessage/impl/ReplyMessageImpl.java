/**
 */
package org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.AssignmentTarget;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.LwMessagePackage;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageArgument;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageReplyOutput;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageReplyOutputs;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.ReplyMessage;

import org.eclipse.uml2.uml.ConnectableElement;
import org.eclipse.uml2.uml.Operation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Reply Message</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.ReplyMessageImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.ReplyMessageImpl#getValue <em>Value</em>}</li>
 *   <li>{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.ReplyMessageImpl#getOutputs <em>Outputs</em>}</li>
 *   <li>{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.ReplyMessageImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.ReplyMessageImpl#getOperation <em>Operation</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ReplyMessageImpl extends AbstractMessageImpl implements ReplyMessage
{
  /**
   * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTarget()
   * @generated
   * @ordered
   */
  protected ConnectableElement target;

  /**
   * The cached value of the '{@link #getValue() <em>Value</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getValue()
   * @generated
   * @ordered
   */
  protected MessageArgument value;

  /**
   * The cached value of the '{@link #getOutputs() <em>Outputs</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOutputs()
   * @generated
   * @ordered
   */
  protected EList<MessageReplyOutput> outputs;

  /**
   * The default value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected static final String NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected String name = NAME_EDEFAULT;

  /**
   * The cached value of the '{@link #getOperation() <em>Operation</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOperation()
   * @generated
   * @ordered
   */
  protected Operation operation;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ReplyMessageImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return LwMessagePackage.Literals.REPLY_MESSAGE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ConnectableElement getTarget()
  {
    if (target != null && target.eIsProxy())
    {
      InternalEObject oldTarget = (InternalEObject)target;
      target = (ConnectableElement)eResolveProxy(oldTarget);
      if (target != oldTarget)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, LwMessagePackage.REPLY_MESSAGE__TARGET, oldTarget, target));
      }
    }
    return target;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ConnectableElement basicGetTarget()
  {
    return target;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setTarget(ConnectableElement newTarget)
  {
    ConnectableElement oldTarget = target;
    target = newTarget;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, LwMessagePackage.REPLY_MESSAGE__TARGET, oldTarget, target));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MessageArgument getValue()
  {
    return value;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetValue(MessageArgument newValue, NotificationChain msgs)
  {
    MessageArgument oldValue = value;
    value = newValue;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LwMessagePackage.REPLY_MESSAGE__VALUE, oldValue, newValue);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setValue(MessageArgument newValue)
  {
    if (newValue != value)
    {
      NotificationChain msgs = null;
      if (value != null)
        msgs = ((InternalEObject)value).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LwMessagePackage.REPLY_MESSAGE__VALUE, null, msgs);
      if (newValue != null)
        msgs = ((InternalEObject)newValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LwMessagePackage.REPLY_MESSAGE__VALUE, null, msgs);
      msgs = basicSetValue(newValue, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, LwMessagePackage.REPLY_MESSAGE__VALUE, newValue, newValue));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<MessageReplyOutput> getOutputs()
  {
    if (outputs == null)
    {
      outputs = new EObjectContainmentEList<MessageReplyOutput>(MessageReplyOutput.class, this, LwMessagePackage.REPLY_MESSAGE__OUTPUTS);
    }
    return outputs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getName()
  {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setName(String newName)
  {
    String oldName = name;
    name = newName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, LwMessagePackage.REPLY_MESSAGE__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Operation getOperation()
  {
    if (operation != null && operation.eIsProxy())
    {
      InternalEObject oldOperation = (InternalEObject)operation;
      operation = (Operation)eResolveProxy(oldOperation);
      if (operation != oldOperation)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, LwMessagePackage.REPLY_MESSAGE__OPERATION, oldOperation, operation));
      }
    }
    return operation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Operation basicGetOperation()
  {
    return operation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setOperation(Operation newOperation)
  {
    Operation oldOperation = operation;
    operation = newOperation;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, LwMessagePackage.REPLY_MESSAGE__OPERATION, oldOperation, operation));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case LwMessagePackage.REPLY_MESSAGE__VALUE:
        return basicSetValue(null, msgs);
      case LwMessagePackage.REPLY_MESSAGE__OUTPUTS:
        return ((InternalEList<?>)getOutputs()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case LwMessagePackage.REPLY_MESSAGE__TARGET:
        if (resolve) return getTarget();
        return basicGetTarget();
      case LwMessagePackage.REPLY_MESSAGE__VALUE:
        return getValue();
      case LwMessagePackage.REPLY_MESSAGE__OUTPUTS:
        return getOutputs();
      case LwMessagePackage.REPLY_MESSAGE__NAME:
        return getName();
      case LwMessagePackage.REPLY_MESSAGE__OPERATION:
        if (resolve) return getOperation();
        return basicGetOperation();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case LwMessagePackage.REPLY_MESSAGE__TARGET:
        setTarget((ConnectableElement)newValue);
        return;
      case LwMessagePackage.REPLY_MESSAGE__VALUE:
        setValue((MessageArgument)newValue);
        return;
      case LwMessagePackage.REPLY_MESSAGE__OUTPUTS:
        getOutputs().clear();
        getOutputs().addAll((Collection<? extends MessageReplyOutput>)newValue);
        return;
      case LwMessagePackage.REPLY_MESSAGE__NAME:
        setName((String)newValue);
        return;
      case LwMessagePackage.REPLY_MESSAGE__OPERATION:
        setOperation((Operation)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case LwMessagePackage.REPLY_MESSAGE__TARGET:
        setTarget((ConnectableElement)null);
        return;
      case LwMessagePackage.REPLY_MESSAGE__VALUE:
        setValue((MessageArgument)null);
        return;
      case LwMessagePackage.REPLY_MESSAGE__OUTPUTS:
        getOutputs().clear();
        return;
      case LwMessagePackage.REPLY_MESSAGE__NAME:
        setName(NAME_EDEFAULT);
        return;
      case LwMessagePackage.REPLY_MESSAGE__OPERATION:
        setOperation((Operation)null);
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case LwMessagePackage.REPLY_MESSAGE__TARGET:
        return target != null;
      case LwMessagePackage.REPLY_MESSAGE__VALUE:
        return value != null;
      case LwMessagePackage.REPLY_MESSAGE__OUTPUTS:
        return outputs != null && !outputs.isEmpty();
      case LwMessagePackage.REPLY_MESSAGE__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case LwMessagePackage.REPLY_MESSAGE__OPERATION:
        return operation != null;
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass)
  {
    if (baseClass == AssignmentTarget.class)
    {
      switch (derivedFeatureID)
      {
        case LwMessagePackage.REPLY_MESSAGE__TARGET: return LwMessagePackage.ASSIGNMENT_TARGET__TARGET;
        case LwMessagePackage.REPLY_MESSAGE__VALUE: return LwMessagePackage.ASSIGNMENT_TARGET__VALUE;
        default: return -1;
      }
    }
    if (baseClass == MessageReplyOutputs.class)
    {
      switch (derivedFeatureID)
      {
        case LwMessagePackage.REPLY_MESSAGE__OUTPUTS: return LwMessagePackage.MESSAGE_REPLY_OUTPUTS__OUTPUTS;
        default: return -1;
      }
    }
    return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass)
  {
    if (baseClass == AssignmentTarget.class)
    {
      switch (baseFeatureID)
      {
        case LwMessagePackage.ASSIGNMENT_TARGET__TARGET: return LwMessagePackage.REPLY_MESSAGE__TARGET;
        case LwMessagePackage.ASSIGNMENT_TARGET__VALUE: return LwMessagePackage.REPLY_MESSAGE__VALUE;
        default: return -1;
      }
    }
    if (baseClass == MessageReplyOutputs.class)
    {
      switch (baseFeatureID)
      {
        case LwMessagePackage.MESSAGE_REPLY_OUTPUTS__OUTPUTS: return LwMessagePackage.REPLY_MESSAGE__OUTPUTS;
        default: return -1;
      }
    }
    return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (name: ");
    result.append(name);
    result.append(')');
    return result.toString();
  }

} //ReplyMessageImpl
