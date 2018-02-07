package org.eclipse.papyrus.uml.diagram.sequence.diagram.model.internal.layout;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ LayoutModelWithFragmentsNavigationTest.class, LayoutModelWithoutFragmentsNavigationTest.class,
		LayoutModelValidationAndFixingTest.class, LayoutModelGapDetectionAndFixingTest.class,
		LayoutModelWithFragmentsAutoLayoutTest.class, LayoutModelWithoutFragmentsAutoLayoutTest.class, })
public class LayoutModelTests {

}
