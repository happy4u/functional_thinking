@Test
public void flyweight_computers() {
  def bob = new AssignedComputer(ComputerFactory.instance.ofType("MacBookPro6_2"), "Bob")
  def steve = new AssignedComputer(ComputerFactory.instance.ofType("MacBookPro6_2"), "Steve")
  assertTrue(bob.computerType == steve.computerType)

  def sally = new AssignedComputer(computerOfType("MacBookPro6_2"), "Sally")
  def betty = new AssignedComputer(computerOfType("MacBookPro6_2"), "Betty")
  assertTrue sally.computerType == betty.computerType
}
