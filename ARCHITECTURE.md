# Plug-in Architecture

## Facade plugin(s)
These ones should be extracted from current compare contribution. It should be later made independent in the project, so the namespace should be closed to an EMF services contribution.

## Sequence notation model
The sequence notation diagram model is the model viewed while making a facade of the UML model. This is the specific implementation of gmf runtime notation for the sequence diagram. It still relies on notation interfaces, e.g the elements should implement notation.Edge, notation.Node, etc.
This plugin depends on UML and on gmf runtime notation. 
It should have no dependencies from UI. 

## Sequence diagram model 
This model is in charge of the specific values that should be used for the notation elements defined above. 
A mechanism dedicated to storing and retrieving custom values is mandatory, which should be not the notation bounds mechanism, but one adapted to sequence diagram specific algorithm 
The implementation for getting default values, overriding and resetting values should be developed as a service, to allow a custom implementation for setting new custom values
This plugin has no dependency from UI

## sequence diagram model editing 
This plugin provides the API for the sequence diagram manipulation. This may be splitted into several parts, one which is pure graphical manipulation (move the end of a message, without ordering) and one with interleaved semantic manipulation (insert the end of a message between 2 other ends, with semantic ordering). 
This distinction is made as the UML model manipulation may have to go through the element types framework from Papyrus, where I would prefer a delegation mechanism and additional plugin not to depend directly from Papyrus element types. This delegation would also allow to mock the dependency to UML edition and replace it in tests or for another semantic model implementation.
It should again not have dependencies to the UI.

## Sequence diagram figures
This plugin contains the definition of the figures & layout specific to the sequence diagram. This diagram is dependent on the draw2D/ SWT, and may be probably be declared as an implementation of a more generic figure service that may be implemented on javaFX & draw2D. 
This plugins should only be dependent on swt draw2d and maybe specific GMF draw2D figures. 
Specific figures should be testable on their own, without the integration within Papyrus.
Figures should be styled using a StyleService. This style service is in charge to provide the necessary configuration for the figure. It may rely on an implementation that gets access to the sequence notation model.
It should again not have dependencies to the UI. For that reasons, GMF draw2D figures may introduce some problems (as for example MapMode support)

## Diagram Runtime
This plugin contains the edit part definition, that should only be a container for the figure service. It may also contain the declaration & implementation of edit policies handled by the edit part.
It may also contain some definitions, like the ones required for the palette or for other gmf dependant implementation. At the end, this part should be implemented in GEF3 or GEF4 quite easily.

## Model explorer integration
This may contain the contribution to the model explorer, if any is required for the sequence diagram.
Property view integration
This may contain some specific adaptation for the appearance view, as much as the adapter for the semantic part. This would allow the property view framework to display contents when elements in the diagram are selected.

## Diagram contributions to Papyrus
This top level plugin should be the basic configuration under which the diagram is contributed to the Papyrus tool. This should contain all the extension points that are required for Papyrus integration. Code implementing those extension point may be located on dependent plugins, thus splitting the usage and definition of the classes. This would allow reuse and specialization of the diagram in a more specific context. 
This plugin should definitely be the integration of all sub-plugins, and so the main target of integration tests.
