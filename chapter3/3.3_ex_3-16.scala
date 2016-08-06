def inc: PartialFunction[Any, Int] = {casei:Int=>i+1}

assert(inc(41) == 42)
//inc("Forty-one")
//scala.MatchError: Forty-one (of class java.lang.String)

assert(inc.isDefinedAt(41))
assert(! inc.isDefinedAt("Forty-one"))

assert(List(42) == (List(41, "cat") collect inc))
