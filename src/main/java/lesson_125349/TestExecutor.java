package lesson_125349;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class TestExecutor {
    public static void start(String className) throws ClassNotFoundException {
        start(Class.forName(className));
    }

    public static void start(Class testClass) throws RuntimeException {
        Method[] methods = testClass.getMethods();
        Method[] testMethods = Arrays.stream(methods)
                .filter((item) -> item.getAnnotation(Test.class) != null)
                .sorted((item1, item2) -> {
                    int priority1 = item1.getAnnotation(Test.class).value();
                    int priority2 = item2.getAnnotation(Test.class).value();
                    return priority1 == priority2 ? 0 : (priority1 <= priority2 ? 1 : -1);
                })
                .toArray(Method[]::new);
        Method[] beforeMethods = Arrays.stream(methods).filter((item) -> item.getAnnotation(BeforeSuite.class) != null).toArray(Method[]::new);
        Method[] afterMethods = Arrays.stream(methods).filter((item) -> item.getAnnotation(AfterSuite.class) != null).toArray(Method[]::new);
        if (beforeMethods.length > 1 || afterMethods.length > 1) {
            throw new RuntimeException("Before or after methods more than 1");
        }
        Object classItem = null;
        try {
            classItem = testClass.getDeclaredConstructor().newInstance();
        }
        catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't create class instance", e);
        }

        if (beforeMethods.length == 1) {
            try {
                beforeMethods[0].invoke(classItem);
            }
            catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                throw new RuntimeException("Can't execute before method", e);
            }
        }

        for (Method testMethod: testMethods) {
            try {
                testMethod.invoke(classItem);
            }
            catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                throw new RuntimeException("Can't execute test method", e);
            }

        }

        if (afterMethods.length == 1) {
            try {
                afterMethods[0].invoke(classItem);
            }
            catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                throw new RuntimeException("Can't execute after method", e);
            }
        }
    }
}
