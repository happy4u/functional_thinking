@Test
public void multi_branch_tree_test() {
    Tree t = new Node(new Node(new Node(new Leaf(4),
            new Node(new Leaf(1), new Node(
                new Node(new Node(new Node(
            new Node(new Node(new Leaf(10), new Leaf(0)),
                new Leaf(22)), new Node(new Node(
                      new Node(new Leaf(4), new Empty()),
                      new Leaf(101)), new Leaf(555))),
                      new Leaf(201)), new Leaf(1000)),
                new Leaf(4)))),
            new Leaf(12)), new Leaf(27));
    assertEquals(12, depth(t));
    assertTrue(inTree(t, 555));
    assertEquals(3, occurrencesIn(t, 4));
}
