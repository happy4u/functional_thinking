class StrategyTest {
  def listOfStrategies = [new CalcMult(), new CalcAdds()]

  @Test
  public void product_verifier() {
    listOfStrategies.each { s ->
      assertEquals(10, s.product(5, 2))
  }
}
