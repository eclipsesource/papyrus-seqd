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
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageArgument;
import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageRequestArguments;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Message Request Arguments</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.impl.MessageRequestArgumentsImpl#getArguments <em>Arguments</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MessageRequestArgumentsImpl extends MinimalEObjectImpl.Container implements MessageRequestArguments
{
  /**
   * The cached value of the '{@link #getArguments() <em>Arguments</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getArguments()
   * @generated
   * @ordered
   */
  protected EList<MessageArgument> arguments;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MessageRequestArgumentsImpl()
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
    return LwMessagePackage.Literals.MESSAGE_REQUEST_ARGUMENTS;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<MessageArgument> getArguments()
  {
    if (arguments == null)
    {
      arguments = new EObjectContainmentEList<MessageArgument>(MessageArgument.class, this, LwMessagePackage.MESSAGE_REQUEST_ARGUMENTS__ARGUMENTS);
    }
    return arguments;
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
      case LwMessagePackage.MESSAGE_REQUEST_ARGUMENTS__ARGUMENTS:
        return ((InternalEList<?>)getArguments()).basicRemove(otherEnd, msgs);
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
      case LwMessagePackage.MESSAGE_REQUEST_ARGUMENTS__ARGUMENTS:
        return getArguments();
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
      case LwMessagePackage.MESSAGE_REQUEST_ARGUMENTS__ARGUMENTS:
        getArguments().clear();
        getArguments().addAll((Collection<? extends MessageArgument>)newValue);
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
      case LwMessagePackage.MESSAGE_REQUEST_ARGUMENTS__ARGUMENTS:
        getArguments().clear();
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
      case LwMessagePackage.MESSAGE_REQUEST_ARGUMENTS__ARGUMENTS:
        return arguments != null && !arguments.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //MessageRequestArgumentsImpl
