package lesson_125349;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Start testing");
            TestExecutor.start("lesson_125349.SomeTest");
            System.out.println("End testing");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
