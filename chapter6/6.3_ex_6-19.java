public class PrimeBeta extends FactorsBeta {
  public PrimeBeta(int number) {
          super(number);
  }

  public boolean isPrime() {
          Set<Integer> primeSet = new HashSet<Integer>() {{
                  add(1);
                  add(number); }};
          return getFactors().equals(primeSet);
  }
}
