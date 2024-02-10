package algorithms.minimumSpanningTree.prims;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class PrimsTest {
    @Test
    public void test_simpleGraph() {
        // Graph setup
        // A -1- B -1- C
        Node nodeActualA = new Node("A");
        Node nodeActualB = new Node("B");
        Node nodeActualC = new Node("C");

        nodeActualA.getAdjacentNodes().put(nodeActualB, 1);
        nodeActualB.getAdjacentNodes().put(nodeActualA, 1);
        nodeActualB.getAdjacentNodes().put(nodeActualC, 1);
        nodeActualC.getAdjacentNodes().put(nodeActualB, 1);

        List<Node> graph = Arrays.asList(nodeActualA, nodeActualB, nodeActualC);

        // Run Prim's algorithm
        List<Node> actualMST = Prim.getPrimsMST(graph);

        // Expected MST
        // A -1- B -1- C
        Node nodeExpectedA = new Node("A");
        Node nodeExpectedB = new Node("B");
        Node nodeExpectedC = new Node("C");

        nodeExpectedA.getAdjacentNodes().put(nodeExpectedB, 1);
        nodeExpectedB.getAdjacentNodes().put(nodeExpectedA, 1);
        nodeExpectedB.getAdjacentNodes().put(nodeExpectedC, 1);
        nodeExpectedC.getAdjacentNodes().put(nodeExpectedB, 1);

        // Expected MST (same as the original graph in this simple case)
        List<Node> expectedMST = Arrays.asList(nodeExpectedA, nodeExpectedB, nodeExpectedC);

        expectedMST.sort(Comparator.comparing(Node::getIdentifier));
        actualMST.sort(Comparator.comparing(Node::getIdentifier));

        // Assertion
        assertGraphsEqual(expectedMST, actualMST); // Direct comparison of lists
    }

    @Test
    public void test_complexGraph() {
        // Graph setup
        //     A
        //   / | \
        //  1  4  2
        // /   |   \
        // B --3-- D
        //  \  |  /
        //   2 3 3
        //   \ | /
        //     C
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");

        nodeA.getAdjacentNodes().put(nodeB, 1);
        nodeA.getAdjacentNodes().put(nodeC, 4);
        nodeA.getAdjacentNodes().put(nodeD, 2);
        nodeB.getAdjacentNodes().put(nodeA, 1);
        nodeB.getAdjacentNodes().put(nodeC, 3);
        nodeB.getAdjacentNodes().put(nodeD, 2);
        nodeC.getAdjacentNodes().put(nodeA, 4);
        nodeC.getAdjacentNodes().put(nodeB, 3);
        nodeC.getAdjacentNodes().put(nodeD, 3);
        nodeD.getAdjacentNodes().put(nodeA, 2);
        nodeD.getAdjacentNodes().put(nodeB, 2);
        nodeD.getAdjacentNodes().put(nodeC, 3);

        List<Node> graph = Arrays.asList(nodeA, nodeB, nodeC, nodeD);

        // Run Prim's algorithm
        List<Node> actualMST = Prim.getPrimsMST(graph);

        // Expected MST
        // D -2- A -1- B -3- C
        Node nodeExpectedA = new Node("A");
        Node nodeExpectedB = new Node("B");
        Node nodeExpectedC = new Node("C");
        Node nodeExpectedD = new Node("D");

        nodeExpectedA.getAdjacentNodes().put(nodeExpectedB, 1);
        nodeExpectedA.getAdjacentNodes().put(nodeExpectedD, 2);
        nodeExpectedB.getAdjacentNodes().put(nodeExpectedA, 1);
        nodeExpectedB.getAdjacentNodes().put(nodeExpectedC, 3);
        nodeExpectedC.getAdjacentNodes().put(nodeExpectedB, 3);
        nodeExpectedD.getAdjacentNodes().put(nodeExpectedA, 2);

        // Expected MST (based on the given graph)
        List<Node> expectedMST = Arrays.asList(nodeExpectedA, nodeExpectedB, nodeExpectedC, nodeExpectedD);

        expectedMST.sort(Comparator.comparing(Node::getIdentifier));
        actualMST.sort(Comparator.comparing(Node::getIdentifier));

        // Assertion
        assertGraphsEqual(expectedMST, actualMST); // Direct comparison of lists
    }

    private void assertGraphsEqual(List<Node> expected, List<Node> actual) {
        if (expected.size() != actual.size()) {
            fail("The MSTs do not have the same number of nodes. Expected size: "
                    + expected.size() + ", Actual size: " + actual.size());
        }
        for (int i = 0; i < expected.size(); i++) {
            Node expectedNode = expected.get(i);
            Node actualNode = actual.get(i);
            assertTrue("Nodes do not match at index " + i + ": Expected "
                            + expectedNode + ", Actual " + actualNode,
                    nodesAreEqual(expectedNode, actualNode));
        }
    }

    private boolean nodesAreEqual(Node node1, Node node2) {
        if (!node1.getIdentifier().equals(node2.getIdentifier())) {
            fail("Node identifiers do not match: Expected "
                    + node1.getIdentifier() + ", Actual " + node2.getIdentifier());
        }
        if (!nodesHaveSameEdges(node1, node2)) {
            fail("Adjacent nodes do not match for Node " + node1.getIdentifier());
        }
        return true;
    }

    private boolean nodesHaveSameEdges(Node node1, Node node2) {
        Map<Node, Integer> adj1 = node1.getAdjacentNodes();
        Map<Node, Integer> adj2 = node2.getAdjacentNodes();
        if (adj1.size() != adj2.size()) {
            fail("Different number of adjacent nodes for Node " + node1.getIdentifier()
                    + ". Expected size: " + adj1.size() + ", Actual size: " + adj2.size());
        }
        for (Map.Entry<Node, Integer> entry : adj1.entrySet()) {
            Node key = findNodeById(adj2.keySet(), entry.getKey().getIdentifier());
            if (key == null) {
                fail("Missing adjacent node '" + entry.getKey().getIdentifier()
                        + "' in Node " + node1.getIdentifier());
            }
            if (!adj2.get(key).equals(entry.getValue())) {
                fail("Edge weight does not match for edge " + node1.getIdentifier()
                        + "-" + key.getIdentifier() + ". Expected weight: "
                        + entry.getValue() + ", Actual weight: " + adj2.get(key));
            }
        }
        return true;
    }


    private Node findNodeById(Set<Node> nodes, String id) {
        for (Node node : nodes) {
            if (node.getIdentifier().equals(id)) {
                return node;
            }
        }
        return null;
    }
}
