package com.nealford.ft.allaboutlists

import org.junit.Test
import static junit.framework.Assert.assertEquals

def prepend(val, closure) { new LazyList(val, closure) }
def integers(n) { prepend(n, { integers(n + 1) }) }

@Test
public void lazy_list_acts_like_a_list() {
  def naturalNumbers = integers(1)
  assertEquals('1 2 3 4 5 6 7 8 9 10', naturalNumbers.getHead(10).join(' '))

  def evenNumbers = naturalNumbers.filter { it % 2 == 0 }
  assertEquals('2 4 6 8 10 12 14 16 18 20', evenNumbers.getHead(10).join(' '))
}
