@Test
public void parse_defaults_normal() { Either<Exception, Integer> result =
        RomanNumeralParser.parseNumberDefaults("XLII");
    assertEquals(42, result.right().intValue());
}
@Test
public void parse_defaults_triggered() { Either<Exception, Integer> result =
        RomanNumeralParser.parseNumberDefaults("MM");
    assertEquals(1000, result.right().intValue());
}
