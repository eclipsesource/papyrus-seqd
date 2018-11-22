/**
 * Copyright (c) 2018 Christian W. Damus and others.
 *  
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 * 
 */
package org.eclipse.papyrus.uml.interaction.internal.model.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.EnumSet;
import java.util.Optional;
import java.util.function.Supplier;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.gmf.runtime.notation.Connector;
import org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage;
import org.eclipse.papyrus.uml.interaction.internal.model.commands.RemoveMessageCommand;
import org.eclipse.papyrus.uml.interaction.model.MInteraction;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.papyrus.uml.interaction.model.MOccurrence;
import org.eclipse.uml2.uml.DestructionOccurrenceSpecification;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageEnd;
import org.eclipse.uml2.uml.MessageSort;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>MMessage</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MMessageImpl#getSend <em>Send</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MMessageImpl#getReceive
 * <em>Receive</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MMessageImpl extends MElementImpl<Message> implements MMessage {
	private static final EnumSet<MessageSort> SYNC_MESSAGE_SORTS = EnumSet
			.complementOf(EnumSet.of(MessageSort.ASYNCH_CALL_LITERAL, MessageSort.ASYNCH_SIGNAL_LITERAL));

	/**
	 * The cached value of the '{@link #getSendEnd() <em>Send End</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSendEnd()
	 * @generated
	 * @ordered
	 */
	protected MMessageEnd sendEnd;

	/**
	 * The cached value of the '{@link #getReceiveEnd() <em>Receive End</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getReceiveEnd()
	 * @generated
	 * @ordered
	 */
	protected MMessageEnd receiveEnd;

	/**
	 * The default value of the '{@link #isSynchronous() <em>Synchronous</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #isSynchronous()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SYNCHRONOUS_EDEFAULT = false;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected MMessageImpl() {
		super();
	}

	protected MMessageImpl(MInteractionImpl owner, Message message) {
		super(owner, message);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SequenceDiagramPackage.Literals.MMESSAGE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public MMessageEnd getSendEnd() {
		return sendEnd;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetSendEnd(MMessageEnd newSendEnd, NotificationChain msgs) {
		MMessageEnd oldSendEnd = sendEnd;
		sendEnd = newSendEnd;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					SequenceDiagramPackage.MMESSAGE__SEND_END, oldSendEnd, newSendEnd);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setSendEnd(MMessageEnd newSendEnd) {
		if (newSendEnd != sendEnd) {
			NotificationChain msgs = null;
			if (sendEnd != null) {
				msgs = ((InternalEObject)sendEnd).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - SequenceDiagramPackage.MMESSAGE__SEND_END, null, msgs);
			}
			if (newSendEnd != null) {
				msgs = ((InternalEObject)newSendEnd).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - SequenceDiagramPackage.MMESSAGE__SEND_END, null, msgs);
			}
			msgs = basicSetSendEnd(newSendEnd, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, SequenceDiagramPackage.MMESSAGE__SEND_END,
					newSendEnd, newSendEnd));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public MMessageEnd getReceiveEnd() {
		return receiveEnd;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetReceiveEnd(MMessageEnd newReceiveEnd, NotificationChain msgs) {
		MMessageEnd oldReceiveEnd = receiveEnd;
		receiveEnd = newReceiveEnd;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					SequenceDiagramPackage.MMESSAGE__RECEIVE_END, oldReceiveEnd, newReceiveEnd);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setReceiveEnd(MMessageEnd newReceiveEnd) {
		if (newReceiveEnd != receiveEnd) {
			NotificationChain msgs = null;
			if (receiveEnd != null) {
				msgs = ((InternalEObject)receiveEnd).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - SequenceDiagramPackage.MMESSAGE__RECEIVE_END, null, msgs);
			}
			if (newReceiveEnd != null) {
				msgs = ((InternalEObject)newReceiveEnd).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - SequenceDiagramPackage.MMESSAGE__RECEIVE_END, null, msgs);
			}
			msgs = basicSetReceiveEnd(newReceiveEnd, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					SequenceDiagramPackage.MMESSAGE__RECEIVE_END, newReceiveEnd, newReceiveEnd));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Optional<MMessageEnd> getSend() {
		return Optional.ofNullable(getSendEnd());
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Optional<MMessageEnd> getReceive() {
		return Optional.ofNullable(getReceiveEnd());
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Optional<MLifeline> getSender() {
		return getSend().flatMap(MOccurrence::getCovered);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Optional<MLifeline> getReceiver() {
		return getReceive().flatMap(MOccurrence::getCovered);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public boolean isSynchronous() {
		Message uml = getElement();
		// Assume synchronous if no UML underpinning
		return (uml == null) || SYNC_MESSAGE_SORTS.contains(uml.getMessageSort());
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public MInteraction getOwner() {
		return (MInteraction)super.getOwner();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Optional<Connector> getDiagramView() {
		return super.getDiagramView().map(Connector.class::cast);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SequenceDiagramPackage.MMESSAGE__SEND_END:
				return basicSetSendEnd(null, msgs);
			case SequenceDiagramPackage.MMESSAGE__RECEIVE_END:
				return basicSetReceiveEnd(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SequenceDiagramPackage.MMESSAGE__SEND_END:
				return getSendEnd();
			case SequenceDiagramPackage.MMESSAGE__RECEIVE_END:
				return getReceiveEnd();
			case SequenceDiagramPackage.MMESSAGE__SEND:
				return getSend();
			case SequenceDiagramPackage.MMESSAGE__RECEIVE:
				return getReceive();
			case SequenceDiagramPackage.MMESSAGE__SENDER:
				return getSender();
			case SequenceDiagramPackage.MMESSAGE__RECEIVER:
				return getReceiver();
			case SequenceDiagramPackage.MMESSAGE__SYNCHRONOUS:
				return isSynchronous();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SequenceDiagramPackage.MMESSAGE__SEND_END:
				setSendEnd((MMessageEnd)newValue);
				return;
			case SequenceDiagramPackage.MMESSAGE__RECEIVE_END:
				setReceiveEnd((MMessageEnd)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case SequenceDiagramPackage.MMESSAGE__SEND_END:
				setSendEnd((MMessageEnd)null);
				return;
			case SequenceDiagramPackage.MMESSAGE__RECEIVE_END:
				setReceiveEnd((MMessageEnd)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case SequenceDiagramPackage.MMESSAGE__SEND_END:
				return sendEnd != null;
			case SequenceDiagramPackage.MMESSAGE__RECEIVE_END:
				return receiveEnd != null;
			case SequenceDiagramPackage.MMESSAGE__SEND:
				return getSend() != null;
			case SequenceDiagramPackage.MMESSAGE__RECEIVE:
				return getReceive() != null;
			case SequenceDiagramPackage.MMESSAGE__SENDER:
				return getSender() != null;
			case SequenceDiagramPackage.MMESSAGE__RECEIVER:
				return getReceiver() != null;
			case SequenceDiagramPackage.MMESSAGE__SYNCHRONOUS:
				return isSynchronous() != SYNCHRONOUS_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case SequenceDiagramPackage.MMESSAGE___GET_OWNER:
				return getOwner();
			case SequenceDiagramPackage.MMESSAGE___GET_DIAGRAM_VIEW:
				return getDiagramView();
			case SequenceDiagramPackage.MMESSAGE___GET_END__MESSAGEEND:
				return getEnd((MessageEnd)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

	MMessageEndImpl setSend(MessageEnd newSend) {
		return setSend(getEnd(newSend).map(MMessageEndImpl.class::cast)
				.orElseGet(() -> new MMessageEndImpl(this, newSend)));
	}

	MMessageEndImpl setSend(MMessageEndImpl newSend) {
		setSendEnd(newSend);
		return newSend;
	}

	MMessageEndImpl setReceive(MessageEnd newReceive) {
		Supplier<? extends MMessageEndImpl> messageEndFactory = newReceive instanceof DestructionOccurrenceSpecification
				? () -> new MDestructionImpl(this, (DestructionOccurrenceSpecification)newReceive)
				: () -> new MMessageEndImpl(this, newReceive);

		return setReceive(getEnd(newReceive).map(MMessageEndImpl.class::cast).orElseGet(messageEndFactory));
	}

	MMessageEndImpl setReceive(MMessageEndImpl newReceive) {
		setReceiveEnd(newReceive);
		return newReceive;
	}

	@Override
	public Optional<MMessageEnd> getEnd(MessageEnd end) {
		return getElement(end, getSend(), getReceive());
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Command remove() {
		return this.removeLogicalElement(RemoveMessageCommand.class,
				() -> new RemoveMessageCommand(this, true));
	}

} // MMessageImpl
