@Test
public void parse_lazy() {
        P1<Either<Exception, Integer> > result =
                RomanNumeralParser.parseNumberLazy("XLII");
        assertEquals(42, result._1().right().intValue());
}
@Test
public void parse_lazy_exception() {
        P1<Either<Exception, Integer> > result =
                RomanNumeralParser.parseNumberLazy("FOO");
        assertTrue(result._1().isLeft());
        assertEquals(INVALID_ROMAN_NUMERAL, result._1().left().getMessage());
}
