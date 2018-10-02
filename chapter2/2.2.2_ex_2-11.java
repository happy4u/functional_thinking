import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class NumberClassifier {
    public static boolean isFactor(final int candidate, final int number) {   // (1)
        return number % candidate == 0;
    }

    public static Set<Integer> factors(final int number) {                    // (2)
        Set<Integer> factors = new HashSet<>();
        factors.add(1);
        factors.add(number);
        for (int i = 2; i < number; i++)
            if (isFactor(i, number)) factors.add(i);
        return factors;
    }

    public static int aliquotSum(final Collection<Integer> factors) {
        int sum = 0;

        int targetNumber = Collections.max(factors);                          // (3)
        for (int n : factors) {
            sum += n;
        }
        return sum - targetNumber;
    }

    public static boolean isPerfect(final int number) {                       // (4)
        return aliquotSum(factors(number)) == number;
    }

    public static boolean isAbundant(final int number) {
        return aliquotSum(factors(number)) > number;
    }

    public static boolean isDeficient(final int number) {
        return aliquotSum(factors(number)) < number;
    }
}

// _factor : 인수 - 기원이 되는 수, 인연이 있는 수_
// _aliquot : 약수 - 어떤 정수를 나머지 없이 나눌 수 있는 정수_
// _인수는 곱셈에 기반하고 약수는 나눗셈에 기반함_