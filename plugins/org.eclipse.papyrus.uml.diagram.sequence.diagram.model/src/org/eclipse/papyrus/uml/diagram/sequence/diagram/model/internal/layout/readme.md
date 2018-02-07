Sequence Diagram Layout API
===========================

The Sequence Diagram layout model API is intended to be used for auto-layouting new diagrams or manipulating existing diagrams in a way that the semantic consistency is always ensured. The basic usage scenarios are as follows.

No existing diagram
-------------------

If there is no existing diagram, a to-be-developed builder should read the semantic model and construct a layout model as follows. The order in which the messages and regions are added determines the correct semantic order of the interaction elements. 

```java
MLifeline user = new MLifeline();
MLifeline webBrowser = new MLifeline();
MLifeline applicationServer = new MLifeline();

MMessage obtainResource = new MMessage(user, webBrowser);
MMessage requestAccess = new MMessage(webBrowser, applicationServer);
MMessage grantAccess = new MMessage(applicationServer, webBrowser);
MMessage redirectRequest = new MMessage(applicationServer, webBrowser);
MRegion altRegion1 = new MRegion().withElements(grantAccess);
MRegion altRegion2 = new MRegion().withElements(redirectRequest);
MFragment alternative = new MFragment().covering(webBrowser, applicationServer) //
		.with(altRegion1, altRegion2);

MInteraction interaction = new MInteraction() //
		.withLifelines(user, webBrowser, applicationServer) //
		.withElements(obtainResource, requestAccess, alternative);
```

As no diagram exists yet, the builder doesn't need to set any positions. However, it can already set the preferred heights of elements using `MElement.setPreferredHeight(int height)`, if needed.

Next, we should set layout preferences to determine paddings, etc., and finally compute the automatic layout.

```java
interaction.using(getAllTenPrefs());
interaction.applyAutoLayout();
```

A to-be-developed processor may now construct a diagram notation model based on the computed positions.

```java
MPosition position = obtainResource.getPosition();
// returns MPosition [begin=MPoint [x=5, y=20], end=MPoint [x=15, y=50]]
```

These positions are absolute, i.e., based on a coordinate system that spans the entire interaction. In future iterations, we may add auxiliary functions to ease applying those coordinates to the notation model.

```java
// e.g. the coordinates relative to the source lifeline in percent for IdentityAnchor values
obtainResource.getPosition().relativeTo(obtainResource.getSource()).inPercent();
...
```

Existing diagram
----------------

If a diagram exists, the to-be-developed builder not only creates the layout model as above, but can also set the existing coordinates stored in the diagram.

```java
obtainResource.setPosition(new MPosition(beginX, beginY, endX, endY));
...
```

The layout model can now be used to validate and fix the layout, either only for one element or the entire interaction. 

```java
if (interaction.areAllYRangesValid()) {
	for (MElement element : interaction.getElementsWithInvalidYRange()) {
		element.fixPosition();
	}
}
// interaction.areAllYRangesValid() == true
```

Fixing the layout will only move the elements to the right place to restore a valid graphical order. If an element is moved, all subsequent elements will automatically be adjusted accordingly. It will leave the elements' heights untouched. With this action, gaps may occur, because elements above will not be moved up. If the client want to achieve that, we can use `MElement.fixPosition()` either on single elements, fragments and their children, or entire interactions.

```java
if (!interaction.isLayoutFreeOfGaps()) {
	interaction.fixPosition();
}
```

Clients of the layout model may also just modify the model to reflect changes in the semantic model, such as adding elements at a certain position, swapping elements, removing or moving elements. Currently there is no nice API for manipulating the model yet, however, all it has to do is to modify the element tree, as it is currently done using, e.g., `MInteraction.addElement(fragment)` (see above).

After the semantic model has been changed, clients may use `interaction.fixPosition()` to compute and assign the corresponding position and adjust the positions of all subsequent elements.  

TODO
====

  * Add more convenient methods to manipulate the semantic model, such as adding elements at a certain position, swapping elements, removing or moving elements, etc.
  * Add test cases for manipulating the semantic model and calling `fixPosition()`.
  * Add `MElement.getSemanticData()` and `MElement.getNotationData()` to allow clients storing a reference to the corresponding semantic and notation elements.
  * Add `MPosition.toRelative(MElement)` to ease applying the values to the diagram notation model
  * Introduce pack() method that sets everything to the preferred height and removes all spaces
  * Consider more padding top and bottom values (see `computeAutoLayout()` and `hasGapToPreviousElement()`) 

Architectural constraints
=========================

Implementations of `MElement.getInternalMinHeight()` must not depend on computePositions to avoid cyclic dependencies in the algorithm (Stackoverflow!).
Implementations of `MElement.computeAutoPosition()` must only depend on compute position of parent or previous elements. In other words, they must not depend on compute position of their children, only their height can be used 

Terminology
===========

* 'Invalid': Graphical order is inconsistent with semantic order
* 'Fix layout': fixes the layout so that the semantic oder is preserved, doesn't affect heights, just order. May be more than just a local fix, as it propagates to all below
* 'Auto layout': completely automatic layout, has to be done for the entire interaction as the computations are interdependent.
* 'Min height': the internal minimum height that can never be undershot
* 'Preferred height': a height that the client likes not to be undershot. The algorithm will never compute heights smaller than that, but may compute heights that are larger than that, if the elements are containers, such as fragments, and their children require more space than specified in the preferred height.
