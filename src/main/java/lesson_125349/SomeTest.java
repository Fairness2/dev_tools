package lesson_125349;

public class SomeTest {

    @BeforeSuite
    public void beforeMethod() {
        System.out.println("Before method");
    }

    @AfterSuite
    public void afterMethod() {
        System.out.println("After method");
    }

    @Test(3)
    public void test1Method() {
        System.out.println("Test 1 method, priority 3");
    }

    @Test(1)
    public void test2Method() {
        System.out.println("Test 2 method, priority 1");
    }

    @Test(2)
    public void test3Method() {
        System.out.println("Test 3 method, priority 2");
    }
}
