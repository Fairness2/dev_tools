import lesson_125348.ForTesting;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ForTestingTest {

    @MethodSource("dataForSplitArray")
    @ParameterizedTest
    public void testSplitArray(int[] source, int[] result) {
        Assertions.assertArrayEquals(ForTesting.splitAfter4Array(source), result);
    }

    public static Stream<Arguments> dataForSplitArray() {
        List<Arguments> out = new ArrayList<>();
        out.add(Arguments.arguments(new int[]{1,2,3,4,5,6}, new int[]{5,6}));
        out.add(Arguments.arguments(new int[]{1,2,3,4}, new int[]{}));
        out.add(Arguments.arguments(new int[]{1,2,3}, new int[]{}));
        return out.stream();
    }

    @MethodSource("dataForContains1And4")
    @ParameterizedTest
    public void testContains1And4(int[] source) {
        Assertions.assertTrue(ForTesting.contains1And4(source));
    }

    public static Stream<Arguments> dataForContains1And4() {
        List<Arguments> out = new ArrayList<>();
        out.add(Arguments.arguments(new int[]{1,2,3,4,5,6}));
        out.add(Arguments.arguments(new int[]{1,2,3,4}));
        out.add(Arguments.arguments(new int[]{1,2,3}));
        return out.stream();
    }

}
