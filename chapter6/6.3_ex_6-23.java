public class FClassifier {
  public static int sumOfFactors(int number) {
          Iterator<Integer> it = Factors.of(number).iterator(); intsum=0;

          while (it.hasNext()) sum += it.next();
          return sum;
  }

  public static boolean isPerfect(int number) {
          return sumOfFactors(number) - number == number;
  }

  public static boolean isAbundant(int number) {
          return sumOfFactors(number) - number > number;
  }

  public static boolean isDeficient(int number) {
          return sumOfFactors(number) - number < number;
  }
}
