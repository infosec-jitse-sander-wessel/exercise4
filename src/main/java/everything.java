import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

import static java.nio.file.FileSystems.getDefault;
import static java.nio.file.Files.lines;

/**
 * Created by wessel on 9/12/16.
 */
public class everything {

    public static final String ALPHABET = "abcdefghijklmnopqrstufwxyz";

    public static void main(String[] args) throws IOException {
        Path path = getDefault().getPath("./encrypted");
        char[] encrypted = lines(path).map(String::toCharArray)
                .reduce(ArrayUtils::addAll)
                .get();

        StandardDeviation standardDeviation = new StandardDeviation();
        double[] stdDevSums = new double[11];
        for (int i = 5; i <= 15; ++i) {
            System.out.printf("==========================================\nFor keySize %d \n", i);
            for (int k = 0; k <= i; ++k) {
                ArrayList<Character> charList = new ArrayList<>();
                for (int j = i; j < encrypted.length; j += i) {
                    charList.add(encrypted[j]);
                }
                double[] frequencies = getFrequencies(charList);
                stdDevSums[i - 5] += standardDeviation.evaluate(frequencies);
                printFrequencies(frequencies, k);
            }
        }

        printStdDevs(stdDevSums);
    }

    private static void printStdDevs(double[] stdDevSums) {
        System.out.println("==========================================");
        int i = 5;
        for (double stdDevSum : stdDevSums) {
            System.out.printf("Sum of  %d std. devs: %f\n", i++, stdDevSum);
        }
        System.out.println("==========================================");
    }

    private static void printFrequencies(double[] frequencies, int charCount) {
        String concattenated = Arrays.stream(frequencies)
                .mapToObj(Double::toString)
                .reduce(((left, right) -> left + ", " + right))
                .get();
        System.out.printf("For %d th letter: {%s }\n", charCount, concattenated);
    }

    private static double[] getFrequencies(ArrayList<Character> charList) {
        double[] frequencies = new double[26];
        int charNumber = 0;

        for (char alphabetChar: ALPHABET.toCharArray()) {
            for (char encryptedChar: charList) {
                if (alphabetChar == encryptedChar) {
                    ++frequencies[charNumber];
                }
            }
            ++charNumber;
        }

        return frequencies;
    }
}
