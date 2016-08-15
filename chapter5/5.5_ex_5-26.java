public static Either<Exception, Integer> divide(int x, int y) {
        try {
                return Either.right(x / y);
        } catch (Exception e) {
                return Either.left(e);
        }
}
