package org.eclipse.papyrus.uml.diagram.sequence.diagram.model.internal.layout;

public class ModelWithoutFragmentsFixture {

	public MLifeline user = new MLifeline();
	public MLifeline webBrowser = new MLifeline();
	public MLifeline applicationServer = new MLifeline();
	public MLifeline authenticationServer = new MLifeline();

	public MMessage obtainResource = new MMessage(user, webBrowser);
	public MMessage requestAccess = new MMessage(webBrowser, applicationServer);
	public MMessage redirectRequest = new MMessage(applicationServer, webBrowser);
	public MMessage authorize = new MMessage(webBrowser, authenticationServer);
	public MMessage permissionForm1 = new MMessage(authenticationServer, webBrowser);
	public MMessage permissionForm2 = new MMessage(webBrowser, user);

	public MInteraction interaction = new MInteraction() //
			.withLifelines(user, webBrowser, applicationServer, authenticationServer) //
			.withElements(obtainResource, requestAccess, redirectRequest, //
					authorize, permissionForm1, permissionForm2);

}