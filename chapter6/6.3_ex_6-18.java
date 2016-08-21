public class ClassifierBeta extends FactorsBeta {
  public ClassifierBeta(int number) {
          super(number);
  }

  public int sum() {
          Iterator it = getFactors().iterator(); intsum=0;
          while (it.hasNext())
                  sum += (Integer) it.next();
          return sum;
  }

  public boolean isPerfect() {
          return sum() - number == number;
  }

  public boolean isAbundant() {
          return sum() - number > number;
  }

  public boolean isDeficient() {
          return sum() - number < number;
  }
}
