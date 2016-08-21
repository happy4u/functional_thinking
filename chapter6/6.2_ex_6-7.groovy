class Computer {
  def type
  def cpu
  def memory
  def hardDrive
  def cd
}

class Desktop extends Computer {
  def driveBays
  def fanWattage
  def videoCard
}

class Laptop extends Computer {
  def usbPorts
  def dockingBay
}

class AssignedComputer {
  def computerType
  def userId

  public AssignedComputer(computerType, userId) {
    this.computerType = computerType
    this.userId = userId
  }
}
