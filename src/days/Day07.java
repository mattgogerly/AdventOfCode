package days;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static utils.InputUtils.asStringStream;

class Day07 extends Day {

    public static void main(String[] args) {
        new Day07().printAnswers();
    }

    private static final Pattern parentPattern = Pattern.compile("(.+) bags contain (.+)\\.");
    private static final Pattern childPattern = Pattern.compile("(\\d+?) (.+?) bags?");

    private static Map<String, Node> tree = buildTree();

    Day07() {
        super(7);
    }

    @Override
    public Object partOne() {
        Node goldBag = tree.get("shiny gold");

        // set to contain the nodes we have visited
        Set<Node> visited = new HashSet<>();
        // stack of nodes left to visit
        Stack<Node> fringe = new Stack<>();

        // start by checking the parents of the shiny gold bag
        fringe.addAll(goldBag.parents);

        while (!fringe.isEmpty()) {
            Node current = fringe.pop();
            if (visited.contains(current)) {
                // skip if we've already been to this node
                continue;
            }

            // add the parents of this node to the stack
            fringe.addAll(current.parents);

            // add this node to the visited set
            visited.add(current);
        }

        // number of colours that contain a shiny gold is the number of colours we've visited
        // by traversing the tree from the shiny gold node
        return visited.size();
    }

    @Override
    public Object partTwo() {
        Node goldBag = tree.get("shiny gold");
        return goldBag.numberOfInnerBags();
    }


    private static Map<String, Node> buildTree() {
        tree = new HashMap<>();

        asStringStream(DAY).forEach(l -> {
            Matcher parentMatches = parentPattern.matcher(l);
            if (!parentMatches.matches()) {
                throw new IllegalArgumentException("Parent input does not match required format");
            }

            // extract the description (e.g. "shiny gold") and add it to the tree if it doesn't already exist
            String parentDesc = parentMatches.group(1);
            Node parentNode = tree.computeIfAbsent(parentDesc, Node::new);

            // extract the bags inside the parent bag
            String children = parentMatches.group(2);
            for (String child : children.split(", ")) {
                // if the child is "no other bags" we've reached a leaf
                if (child.equals("no other bags")) {
                    break;
                }

                Matcher childMatches = childPattern.matcher(child);
                if (!childMatches.matches()) {
                    throw new IllegalArgumentException("Child input does not match required format");
                }

                // get the number and description
                int number = Integer.parseInt(childMatches.group(1));
                String childDesc = childMatches.group(2);

                // add the child bag to the tree if it doesn't exist
                Node childNode = tree.computeIfAbsent(childDesc, Node::new);
                // set the parent of the child node
                childNode.parents.add(parentNode);
                // add this child to the parent with its number
                parentNode.children.put(childNode, number);
            }
        });

        return tree;
    }


    private static class Node {

        public String bag;
        public Set<Node> parents;
        public Map<Node, Integer> children;

        public Node(String bag) {
            this.bag = bag;
            this.parents = new HashSet<>();
            this.children = new HashMap<>();
        }

        public int numberOfInnerBags() {
            int total = 0;

            // iterate through the children of this node
            for (Map.Entry<Node, Integer> child : children.entrySet()) {
                Node n = child.getKey();
                int numberOfBags = child.getValue();

                // total number of bags is the number of times this child appears as a child of this node
                // multiplied by the child bag + number of bags that child contains
                total += numberOfBags * (1 + n.numberOfInnerBags());
            }

            return total;
        }
    }
}
