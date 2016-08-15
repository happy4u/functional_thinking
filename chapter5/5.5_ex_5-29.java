@Test
public void lazily_catching_other_peoples_exceptions() {
        P1<Either<Exception, Integer> > result = FjRomanNumeralParser.divideLazily(4, 2);
        assertEquals((long) 2, (long) result._1().right().value());

        P1<Either<Exception, Integer> > failure = FjRomanNumeralParser.divideLazily(4, 0);
        assertEquals("/ by zero", failure._1().left().value().getMessage());
}
