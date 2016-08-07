
def recurseList(listOfNums) {
  if (listOfNums.size == 0) return;
    println "${listOfNums.head()}"
    recurseList(listOfNums.tail())
}

println "\nRecurse List"
recurseList(numbers)
