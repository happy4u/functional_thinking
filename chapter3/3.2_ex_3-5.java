package com.nealford.ft;

import java.util.List;
import java.util.ArrayList;

class Counter {
  public int varField;

  Counter(int var) {
          varField = var;
  }

  public static Counter makeCounter() {
          return new Counter(0);
  }

  public int execute() {
          return ++varField;
  }
}
