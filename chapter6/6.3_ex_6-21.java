public class FPrime {
  public static boolean isPrime(int number) {
          Set<Integer> factors = Factors.of(number);
          return number > 1 &&
                 factors.size() == 2 &&
                 factors.contains(1) &&
                 factors.contains(number);
  }
}
