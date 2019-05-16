package kontrol

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    ExtensionsTest::class,
    ModelTest::class
)
class MainSuite