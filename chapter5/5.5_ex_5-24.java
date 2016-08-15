private static final int MIN = 0;
private static final int MAX = 1000;

public static Either<Exception, Integer> parseNumberDefaults(final String s) {
        if (!s.matches("[IVXLXCDM]+"))
                return Either.left(new Exception("Invalid Roman numeral"));
        else {
                int number = new RomanNumeral(s).toInt();
                return Either.right(new RomanNumeral(number >= MAX ? MAX : number).toInt());
        }
}
