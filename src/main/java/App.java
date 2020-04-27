import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class App {
    public static double[][] multiply(double[][] firstMatrix, double[][] secondMatrix) {
        if (firstMatrix[0].length != secondMatrix.length) {
            throw new IllegalArgumentException("Incorrect arguments");
        }
        double[][] result = new double[firstMatrix.length][secondMatrix[0].length];
        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                result[row][col] = multiplyMatricesCell(firstMatrix, secondMatrix, row, col);
            }
        }
        return result;
    }

    public static double[][] addMatrix(double[][] firstMatrix, double[][] secondMatrix) {
        if (firstMatrix.length != secondMatrix.length || firstMatrix[0].length != secondMatrix[0].length) {
            throw new IllegalArgumentException("Incorrect arguments");
        }
        double[][] result = new double[firstMatrix.length][firstMatrix[0].length];
        for (int i = 0; i < firstMatrix.length; ++i) {
            for (int j = 0; j < firstMatrix[0].length; ++j) {
                result[i][j] = firstMatrix[i][j] + secondMatrix[i][j];
            }
        }
        return result;
    }

    public static double[][] multiplyCoef(double[][] matrix, int k) {
        double[][] result = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[0].length; ++j) {
                result[i][j] = matrix[i][j] * k;
            }
        }
        return result;
    }

    public static double[][] subtractMatrix(double[][] firstMatrix, double[][] secondMatrix) {
        return addMatrix(firstMatrix, multiplyCoef(secondMatrix, -1));
    }

    private static double multiplyMatricesCell(double[][] firstMatrix, double[][] secondMatrix, int row, int col) {
        double cell = 0;
        for (int i = 0; i < secondMatrix.length; ++i) {
            cell += firstMatrix[row][i] * secondMatrix[i][col];
        }
        return cell;
    }

    public static void print(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        for(int i = 0; i < rows; ++i) {
            for(int j = 0; j < cols; ++j) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static double[][] readMatrix(String fileName) {
        File file = new File(fileName);
        double[][] result;
        try {
            Scanner scanner = new Scanner(file);
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            result = new double[n][m];
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < m; ++j) {
                    result[i][j] = scanner.nextDouble();
                }
            }
        } catch (Exception e){
            result = null;
        }
        return result;
    }
}
