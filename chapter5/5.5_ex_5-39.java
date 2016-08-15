static public int occurrencesIn(Tree t, int value) {
        for (Empty e: t.toEither().left())
                return 0;
        for (Either<Leaf, Node> ln: t.toEither().right()) {
                for (Leaf leaf : ln.left())
                        if (value == leaf.n) return 1;
                for (Node node : ln.right())
                        return occurrencesIn(node.left, value)
                               + occurrencesIn(node.right, value);
        }
        return 0;
}
