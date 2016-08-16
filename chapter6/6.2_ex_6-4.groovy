package com.nealford.ft.strategy

interface Calc {
  def product(n, m)
}

class CalcMult implements Calc {
  def product(n,m){n*m}
}

class CalcAdds implements Calc {
  def product(n, m) {
    def result = 0
    n.times {
      result += m
    }
    result
  }
}
