/**
 */
package org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.LwMessagePackage;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageReplyOutput;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageReplyOutputs;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Message Reply Outputs</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.MessageReplyOutputsImpl#getOutputs <em>Outputs</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MessageReplyOutputsImpl extends MinimalEObjectImpl.Container implements MessageReplyOutputs
{
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
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MessageReplyOutputsImpl()
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
    return LwMessagePackage.Literals.MESSAGE_REPLY_OUTPUTS;
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
      outputs = new EObjectContainmentEList<MessageReplyOutput>(MessageReplyOutput.class, this, LwMessagePackage.MESSAGE_REPLY_OUTPUTS__OUTPUTS);
    }
    return outputs;
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
      case LwMessagePackage.MESSAGE_REPLY_OUTPUTS__OUTPUTS:
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
      case LwMessagePackage.MESSAGE_REPLY_OUTPUTS__OUTPUTS:
        return getOutputs();
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
      case LwMessagePackage.MESSAGE_REPLY_OUTPUTS__OUTPUTS:
        getOutputs().clear();
        getOutputs().addAll((Collection<? extends MessageReplyOutput>)newValue);
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
      case LwMessagePackage.MESSAGE_REPLY_OUTPUTS__OUTPUTS:
        getOutputs().clear();
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
      case LwMessagePackage.MESSAGE_REPLY_OUTPUTS__OUTPUTS:
        return outputs != null && !outputs.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //MessageReplyOutputsImpl
