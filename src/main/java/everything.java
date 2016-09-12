import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import static java.nio.file.FileSystems.getDefault;
import static java.nio.file.Files.lines;

/**
 * Created by wessel on 9/12/16.
 */
public class everything {

    public static final String ALPHABET = "abcdefghijklmnopqrstufwxyz";

    public static void main(String[] args) throws IOException {
        Path path = getDefault().getPath("./encrypthed");
        char[] encrypted = lines(path).map(String::toCharArray)
                .reduce(ArrayUtils::addAll)
                .get();

        double[] stdDevSums = new double[10];
        for (int i = 5; i <= 15; ++i) {
            for (int k = 0; k <= i; ++k) {
                ArrayList<Character> charList = new ArrayList<>();
                for (int j = i; j < encrypted.length; j += i) {
                    charList.add(encrypted[j]);
                }

                stdDevSums[i] += getFrequencies(charList);
            }
        }
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
    }
}
