package assignment09;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BSPTreeTest {

    @Test
    void testTreeConstruction(){
        ArrayList<Segment> segments = new ArrayList<>();
        segments.add(new Segment(3,4,5,1));
        segments.add(new Segment(5,5,3,1));

        BSPTree tree = new BSPTree(segments);

        assertNotNull(tree.getRoot());
    }

}