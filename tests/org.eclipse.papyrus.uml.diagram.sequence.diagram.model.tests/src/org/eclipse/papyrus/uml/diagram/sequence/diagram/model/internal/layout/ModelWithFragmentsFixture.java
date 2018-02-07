package org.eclipse.papyrus.uml.diagram.sequence.diagram.model.internal.layout;

public class ModelWithFragmentsFixture {

	public MLifeline user = new MLifeline();
	public MLifeline webBrowser = new MLifeline();
	public MLifeline applicationServer = new MLifeline();

	public MMessage obtainResource = new MMessage(user, webBrowser);
	public MMessage requestAccess = new MMessage(webBrowser, applicationServer);
	public MMessage grantAccess = new MMessage(applicationServer, webBrowser);
	public MMessage redirectRequest = new MMessage(applicationServer, webBrowser);
	public MRegion altRegion1 = new MRegion().withElements(grantAccess);
	public MRegion altRegion2 = new MRegion().withElements(redirectRequest);
	public MFragment alternative = new MFragment().covering(webBrowser, applicationServer) //
			.with(altRegion1, altRegion2);

	public MInteraction interaction = new MInteraction() //
			.withLifelines(user, webBrowser, applicationServer) //
			.withElements(obtainResource, requestAccess, alternative);

}