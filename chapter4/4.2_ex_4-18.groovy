package com.nealford.ft.lazy_primes

class PLazyList {
  private Closure list

  private PLazyList(list) {
    this.list = list
  }
  static PLazyList nil() {                        // (1)
    new PLazyList({-> []})
  }

  PLazyList cons(head) {                          // (2)
    new PLazyList({-> [head, list]})
  }

  def head() {
    def lst = list.call()
    lst ? lst[0] : null
  }

  def tail() {
    def lst = list.call()
    lst ? new PLazyList(lst.tail()[0]) : nil()
  }

  boolean isEmpty() {
    list.call() == []
  }

  def fold(n, acc, f) {
    n == 0 || isEmpty() ? acc : tail().fold(n - 1, f.call(acc, head()), f)
  }

  def foldAll(acc, f) {
    isEmpty() ? acc : tail().foldAll(f.call(acc, head()), f)
  }

  def take(n) {
    fold(n, []) {acc, item -> acc << item}
  }

  def takeAll() {
    foldAll([]) {acc, item -> acc << item}
  }

  def toList() {
    takeAll()
  }
}
