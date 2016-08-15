@Test
public void maps_success() {
        Map<String, Object> result = RomanNumeralParser.divide(4, 2);
        assertEquals(2.0, (Double) result.get("answer"), 0.1);
}

@Test
public void maps_failure() {
        Map<String, Object> result = RomanNumeralParser.divide(4, 0);
        assertEquals("div by zero", ((Exception) result.get("exception")).getMessage());
}
