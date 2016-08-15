@Test
public void catching_other_people_exceptions() {
        Either<Exception, Integer> result = FjRomanNumeralParser.divide(4, 2);
        assertEquals((long) 2, (long) result.right().value());
        
        Either<Exception, Integer> failure = FjRomanNumeralParser.divide(4, 0);
        assertEquals("/ by zero", failure.left().value().getMessage());
}
