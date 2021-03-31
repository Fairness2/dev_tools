package lesson_125348;

import java.util.Arrays;
import java.util.List;

public class ForTesting {
    public static int[] splitAfter4Array (int[] sourceArr) {
        Integer[] intSource = convertToIntArr(sourceArr);
        for (int i = 0; i < sourceArr.length; i++) {
            intSource[i] = sourceArr[i];
        }
        List<Integer> asList = Arrays.asList(intSource);
        int index4 = asList.lastIndexOf(4);
        if (index4 == -1) {
            throw new RuntimeException("Array doesn't contain 4");
        }
        return Arrays.copyOfRange(sourceArr, index4 + 1, sourceArr.length);
    }

    public static boolean contains1And4 (int[] sourceArr) {
        Integer[] intSource = convertToIntArr(sourceArr);
        List<Integer> asList = Arrays.asList(intSource);
        return asList.contains(1) && asList.contains(4);
    }

    private static Integer[] convertToIntArr(int[] sourceArr) {
        return Arrays.stream(sourceArr).boxed().toArray(Integer[]::new);
    }
}
