import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.rules.ExpectedException;

import java.util.stream.Stream;

public class AppTests {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void addMatrix() {
        double[][] A = {{2, 3, 5}, {3.5, -8.9, 0}};
        double[][] B = {{-4, 6.4, 5.2}, {1, 234, 8}};
        double[][] C = {{-2, 9.4, 10.2}, {4.5, 225.1, 8}};
        assertArrayEquals(C, App.addMatrix(A, B));
        double[][] D = {{3, 5}, {0, 1}};
        thrown.expectMessage("Incorrect arguments");
        App.addMatrix(A, D);
    }

    @Test
    public void multiplyCoef() {
        int k = 4;
        double[][] A = {{2, 3}, {6, -3}};
        double[][] result = {{8, 12}, {24, -12}};
        assertArrayEquals(result, App.multiplyCoef(A, k));
    }

    @Test
    public void subtractMatrix() {
        double[][] A = {{2, 3, 5}, {3.5, -8, 0}};
        double[][] B = {{-4, 6, 5}, {1, 234, 8}};
        double[][] C = {{6, -3, 0}, {2.5, -242, -8}};
        double[][] D = App.subtractMatrix(A, B);

        assertArrayEquals(C, D);
        double[][] F = {{3, 5}, {0, 1}};
        thrown.expectMessage("Incorrect arguments");
        App.subtractMatrix(A, F);
    }

    @Test
    public void Multiply() {
        double[][] A = {
                {1, 2},
                {3, 4}
        };
        double[][] B = {
                {5, 6},
                {7, 8}
        };
        double[][] C = {
                {19, 22},
                {43, 50}
        };
        assertArrayEquals(App.multiply(A, B), C);
    }

    @Test
    public void Throw_When_Multiply_With_Incorrect_Size() {
        double[][] A = {
                {1, 2},
                {3, 4}
        };
        double[][] B = {
                {5, 6},
                {7, 8},
                {9, 10}
        };
        thrown.expectMessage("Incorrect arguments");
        App.addMatrix(A, B);
    }

    @Test
    public void CanReadFromFile() {
        double[][] A = App.readMatrix(getClass().getResource("/secondArgument.txt").getFile());
        App.print(A);
        assertTrue(A != null);
    }

    @Test
    public void Work_With_Files() {
        double[][] A = App.readMatrix(getClass().getResource("/firstArgument.txt").getFile());
        double[][] B = App.readMatrix(getClass().getResource("/secondArgument.txt").getFile());
        double[][] C = App.readMatrix(getClass().getResource("/result.txt").getFile());
        assertArrayEquals(App.addMatrix(A, B), C);
    }

    @ParameterizedTest(name = "{0} + {1} = {2}")
    @CsvSource({
            "1, 1, 2",
            "-1, 1, 0",
            "50, 50, 100"
    })
    public void AddMatrixCsv(int first, int second, int result) {
        double[][] A = new double[2][3];
        double[][] B = new double[2][3];
        double[][] C = new double[2][3];
        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 3; ++j) {
                A[i][j] = first;
                B[i][j] = second;
                C[i][j] = result;
            }
        }
        assertArrayEquals(App.addMatrix(A, B), C);
    }

    @ParameterizedTest(name = "{0} - {1} = {2}")
    @CsvSource({
            "1, 1, 0",
            "10, 0, 10",
            "-9, 9, -18"
    })
    public void SubtractMatrixCsv(int first, int second, int result) {
        double[][] A = new double[2][3];
        double[][] B = new double[2][3];
        double[][] C = new double[2][3];
        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 3; ++j) {
                A[i][j] = first;
                B[i][j] = second;
                C[i][j] = result;
            }
        }
        assertArrayEquals(App.subtractMatrix(A, B), C);
    }

    @ParameterizedTest(name = "{0} * {1} = {2}")
    @CsvSource({
            "10, 0, 0",
            "2, 3, 18",
            "1, 1, 3"
    })
    public void MultiplyMatrixCsv(int first, int second, int result) {
        double[][] A = new double[2][3];
        double[][] B = new double[3][2];
        double[][] C = new double[2][2];
        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 3; ++j) {
                A[i][j] = first;
            }
        }
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 2; ++j) {
                B[i][j] = second;
            }
        }
        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 2; ++j) {
                C[i][j] = result;
            }
        }
        assertArrayEquals(App.multiply(A, B), C);
    }

    double[][] A = {
            {1, 2},
            {3, 4}
    };

    double[][] B = {
            {5, 6},
            {7, 8}
    };

    double[][][] C = {
            {{6, 8}, {10, 12}},
            {{-4, -4}, {-4, -4}},
            {{19, 22}, {43, 50}}
    };

    @ParameterizedTest
    @CsvSource({
            "add",
            "sub",
            "mul"
    })
    public void Operations(String op) {
        switch (op) {
            case "add":
                assertArrayEquals(App.addMatrix(A, B), C[0]);
                break;
            case "sub":
                assertArrayEquals(App.subtractMatrix(A, B), C[1]);
                break;
            case "mul":
                assertArrayEquals(App.multiply(A, B), C[2]);
                break;
        }
    }
}
