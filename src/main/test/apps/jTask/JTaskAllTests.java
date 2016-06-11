package apps.jTask;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	JTaskRestServiceHelperIntegrationTest.class, 
	JTaskRestServiceHelperTest.class})
public class JTaskAllTests {

}
