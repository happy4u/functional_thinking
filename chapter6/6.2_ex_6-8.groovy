class ComputerFactory {
  def types = [:]
  static def instance;

  private ComputerFactory() {
    def laptop = new Laptop()
    def tower = new Desktop()
    types.put("MacBookPro6_2", laptop)
    types.put("SunTower", tower)
  }

  static def getInstance() {
    if (instance == null)
      instance = new CompFactory()
    instance
  }

  def ofType(computer) {
    types[computer]
  }
}
