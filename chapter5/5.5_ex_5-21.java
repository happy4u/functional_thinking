@Test
public void parsing_success() {
        Either<Exception, Integer> result = RomanNumeralParser.parseNumber("XLII");
        assertEquals(Integer.valueOf(42), result.right());
}

@Test
public void parsing_failure() {
        Either<Exception, Integer> result = RomanNumeralParser.parseNumber("FOO");
        assertEquals(INVALID_ROMAN_NUMERAL, result.left().getMessage());
}
