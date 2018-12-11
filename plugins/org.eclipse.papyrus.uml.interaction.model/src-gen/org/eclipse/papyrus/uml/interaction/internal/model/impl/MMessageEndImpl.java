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
import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.gmf.runtime.notation.IdentityAnchor;
import org.eclipse.papyrus.uml.interaction.internal.model.SequenceDiagramPackage;
import org.eclipse.papyrus.uml.interaction.model.MElement;
import org.eclipse.papyrus.uml.interaction.model.MLifeline;
import org.eclipse.papyrus.uml.interaction.model.MMessage;
import org.eclipse.papyrus.uml.interaction.model.MMessageEnd;
import org.eclipse.papyrus.uml.interaction.model.NudgeKind;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.MessageEnd;
import org.eclipse.uml2.uml.OccurrenceSpecification;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>MMessage End</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MMessageEndImpl#isSend
 * <em>Send</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MMessageEndImpl#isReceive
 * <em>Receive</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.interaction.internal.model.impl.MMessageEndImpl#getOtherEnd <em>Other
 * End</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MMessageEndImpl extends MOccurrenceImpl<MessageEnd> implements MMessageEnd {
	/**
	 * The default value of the '{@link #isSend() <em>Send</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #isSend()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SEND_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isReceive() <em>Receive</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #isReceive()
	 * @generated
	 * @ordered
	 */
	protected static final boolean RECEIVE_EDEFAULT = false;

	private Optional<MLifeline> covered;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected MMessageEndImpl() {
		super();
	}

	protected MMessageEndImpl(MMessageImpl owner, MessageEnd messageEnd) {
		super(owner, messageEnd);

		// A gate, for example, is not an interaction fragment
		if (messageEnd instanceof InteractionFragment) {
			covered = getCovered((InteractionFragment)messageEnd);
		}
	}

	protected MMessageEndImpl(MElement<? extends Element> owner, MessageEnd messageEnd) {
		super(owner, messageEnd);

		if (owner instanceof MLifeline) {
			covered = Optional.of((MLifeline)owner);
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SequenceDiagramPackage.Literals.MMESSAGE_END;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public boolean isSend() {
		return getElement().isSend();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public boolean isReceive() {
		return getElement().isReceive();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Optional<MMessageEnd> getOtherEnd() {
		return isSend() ? getOwner().getReceive() : getOwner().getSend();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public MMessage getOwner() {
		return (MMessage)super.getOwner();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Optional<MLifeline> getCovered() {
		if (covered == null) {
			covered = getFragment().flatMap(this::getCovered);
		}
		return covered;
	}

	private Optional<MLifeline> getCovered(InteractionFragment fragment) {
		Lifeline result = fragment.getCovereds().isEmpty() ? null : fragment.getCovereds().get(0);
		return getInteraction().getLifeline(result);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Optional<IdentityAnchor> getDiagramView() {
		Optional<MessageEnd> self = Optional.of(getElement());
		Optional<IdentityAnchor> result = self.flatMap(this::getAnchor);

		if (!result.isPresent()) {
			// Maybe it's a message-less destruction occurrence with a general ordering?
			result = self.filter(OccurrenceSpecification.class::isInstance)
					.map(OccurrenceSpecification.class::cast).flatMap(this::getAnchor);
		}

		return result;
	}

	protected Optional<IdentityAnchor> getAnchor(MessageEnd end) {
		Optional<IdentityAnchor> result = Optional.empty();

		if (end.getMessage() != null) {
			if (end.isReceive()) {
				result = Optional.ofNullable(getTargetAnchor(end.getMessage()));
			} else {
				result = Optional.ofNullable(getSourceAnchor(end.getMessage()));
			}
		}

		return result;
	}

	@SuppressWarnings("boxing")
	@Override
	public Command nudge(int deltaY, NudgeKind mode) {
		/*
		 * If I am the receiving end of a message, only reorient me if I am a found message or if I am part of
		 * a sloped message. Otherwise move the sending end.
		 */
		if (getTop().orElse(0) != getOtherEnd().map(me -> me.getTop().orElse(0)).orElse(0)) {
			/* part of sloped message, move me */
			return basicNudge(deltaY, mode);
		}

		return getOtherEnd().filter(MMessageEnd::isSend).map(MMessageEndImpl.class::cast).orElse(this)
				.basicNudge(deltaY, mode);
	}

	/**
	 * The default (superclass) algorithm for the <em>nudge</em> command.
	 * 
	 * @param deltaY
	 *            the amount by which to nudge down (up if negative)
	 * @param mode
	 *            the nudge mode
	 * @return the basic nudge command
	 */
	protected Command basicNudge(int deltaY, NudgeKind mode) {
		return super.nudge(deltaY, mode);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SequenceDiagramPackage.MMESSAGE_END__SEND:
				return isSend();
			case SequenceDiagramPackage.MMESSAGE_END__RECEIVE:
				return isReceive();
			case SequenceDiagramPackage.MMESSAGE_END__OTHER_END:
				return getOtherEnd();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case SequenceDiagramPackage.MMESSAGE_END__SEND:
				return isSend() != SEND_EDEFAULT;
			case SequenceDiagramPackage.MMESSAGE_END__RECEIVE:
				return isReceive() != RECEIVE_EDEFAULT;
			case SequenceDiagramPackage.MMESSAGE_END__OTHER_END:
				return getOtherEnd() != null;
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
			case SequenceDiagramPackage.MMESSAGE_END___GET_OWNER:
				return getOwner();
			case SequenceDiagramPackage.MMESSAGE_END___GET_DIAGRAM_VIEW:
				return getDiagramView();
		}
		return super.eInvoke(operationID, arguments);
	}

} // MMessageEndImpl
