/**
 */
package org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.LwMessagePackage
 * @generated
 */
public class LwMessageAdapterFactory extends AdapterFactoryImpl
{
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static LwMessagePackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LwMessageAdapterFactory()
  {
    if (modelPackage == null)
    {
      modelPackage = LwMessagePackage.eINSTANCE;
    }
  }

  /**
   * Returns whether this factory is applicable for the type of the object.
   * <!-- begin-user-doc -->
   * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
   * <!-- end-user-doc -->
   * @return whether this factory is applicable for the type of the object.
   * @generated
   */
  @Override
  public boolean isFactoryForType(Object object)
  {
    if (object == modelPackage)
    {
      return true;
    }
    if (object instanceof EObject)
    {
      return ((EObject)object).eClass().getEPackage() == modelPackage;
    }
    return false;
  }

  /**
   * The switch that delegates to the <code>createXXX</code> methods.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected LwMessageSwitch<Adapter> modelSwitch =
    new LwMessageSwitch<Adapter>()
    {
      @Override
      public Adapter caseAbstractMessage(AbstractMessage object)
      {
        return createAbstractMessageAdapter();
      }
      @Override
      public Adapter caseRequestMessage(RequestMessage object)
      {
        return createRequestMessageAdapter();
      }
      @Override
      public Adapter caseAnyMessage(AnyMessage object)
      {
        return createAnyMessageAdapter();
      }
      @Override
      public Adapter caseMessageRequestArguments(MessageRequestArguments object)
      {
        return createMessageRequestArgumentsAdapter();
      }
      @Override
      public Adapter caseMessageArgument(MessageArgument object)
      {
        return createMessageArgumentAdapter();
      }
      @Override
      public Adapter caseReplyMessage(ReplyMessage object)
      {
        return createReplyMessageAdapter();
      }
      @Override
      public Adapter caseAssignmentTarget(AssignmentTarget object)
      {
        return createAssignmentTargetAdapter();
      }
      @Override
      public Adapter caseMessageReplyOutputs(MessageReplyOutputs object)
      {
        return createMessageReplyOutputsAdapter();
      }
      @Override
      public Adapter caseMessageReplyOutput(MessageReplyOutput object)
      {
        return createMessageReplyOutputAdapter();
      }
      @Override
      public Adapter caseValue(Value object)
      {
        return createValueAdapter();
      }
      @Override
      public Adapter caseBooleanValue(BooleanValue object)
      {
        return createBooleanValueAdapter();
      }
      @Override
      public Adapter caseIntegerValue(IntegerValue object)
      {
        return createIntegerValueAdapter();
      }
      @Override
      public Adapter caseUnlimitedNaturalValue(UnlimitedNaturalValue object)
      {
        return createUnlimitedNaturalValueAdapter();
      }
      @Override
      public Adapter caseRealValue(RealValue object)
      {
        return createRealValueAdapter();
      }
      @Override
      public Adapter caseNullValue(NullValue object)
      {
        return createNullValueAdapter();
      }
      @Override
      public Adapter caseStringValue(StringValue object)
      {
        return createStringValueAdapter();
      }
      @Override
      public Adapter caseUndefinedRule(UndefinedRule object)
      {
        return createUndefinedRuleAdapter();
      }
      @Override
      public Adapter caseWildcardMessageArgument(WildcardMessageArgument object)
      {
        return createWildcardMessageArgumentAdapter();
      }
      @Override
      public Adapter defaultCase(EObject object)
      {
        return createEObjectAdapter();
      }
    };

  /**
   * Creates an adapter for the <code>target</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param target the object to adapt.
   * @return the adapter for the <code>target</code>.
   * @generated
   */
  @Override
  public Adapter createAdapter(Notifier target)
  {
    return modelSwitch.doSwitch((EObject)target);
  }


  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.AbstractMessage <em>Abstract Message</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.AbstractMessage
   * @generated
   */
  public Adapter createAbstractMessageAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.RequestMessage <em>Request Message</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.RequestMessage
   * @generated
   */
  public Adapter createRequestMessageAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.AnyMessage <em>Any Message</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.AnyMessage
   * @generated
   */
  public Adapter createAnyMessageAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageRequestArguments <em>Message Request Arguments</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageRequestArguments
   * @generated
   */
  public Adapter createMessageRequestArgumentsAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageArgument <em>Message Argument</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageArgument
   * @generated
   */
  public Adapter createMessageArgumentAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.ReplyMessage <em>Reply Message</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.ReplyMessage
   * @generated
   */
  public Adapter createReplyMessageAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.AssignmentTarget <em>Assignment Target</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.AssignmentTarget
   * @generated
   */
  public Adapter createAssignmentTargetAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageReplyOutputs <em>Message Reply Outputs</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageReplyOutputs
   * @generated
   */
  public Adapter createMessageReplyOutputsAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageReplyOutput <em>Message Reply Output</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.MessageReplyOutput
   * @generated
   */
  public Adapter createMessageReplyOutputAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.Value <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.Value
   * @generated
   */
  public Adapter createValueAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.BooleanValue <em>Boolean Value</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.BooleanValue
   * @generated
   */
  public Adapter createBooleanValueAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.IntegerValue <em>Integer Value</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.IntegerValue
   * @generated
   */
  public Adapter createIntegerValueAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.UnlimitedNaturalValue <em>Unlimited Natural Value</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.UnlimitedNaturalValue
   * @generated
   */
  public Adapter createUnlimitedNaturalValueAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.RealValue <em>Real Value</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.RealValue
   * @generated
   */
  public Adapter createRealValueAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.NullValue <em>Null Value</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.NullValue
   * @generated
   */
  public Adapter createNullValueAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.StringValue <em>String Value</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.StringValue
   * @generated
   */
  public Adapter createStringValueAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.UndefinedRule <em>Undefined Rule</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.UndefinedRule
   * @generated
   */
  public Adapter createUndefinedRuleAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.WildcardMessageArgument <em>Wildcard Message Argument</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.papyrus.uml.diagram.lightweightsequence.message.xtext.lwMessage.WildcardMessageArgument
   * @generated
   */
  public Adapter createWildcardMessageArgumentAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for the default case.
   * <!-- begin-user-doc -->
   * This default implementation returns null.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @generated
   */
  public Adapter createEObjectAdapter()
  {
    return null;
  }

} //LwMessageAdapterFactory
