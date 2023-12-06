package assignment09;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BSPTreeTest {

    class CountCallBack implements SegmentCallback{

        int countCounter;

        ArrayList<Segment> callbackList = new ArrayList<>();
        @Override
        public void callback(Segment s) {
            countCounter++;
            callbackList.add(s);
        }
    }

    @Test
    void testTreeConstruction(){
        ArrayList<Segment> segments = new ArrayList<>();
        segments.add(new Segment(3,4,5,1));
        segments.add(new Segment(5,5,3,1));
        BSPTree tree = new BSPTree(segments);
        assertNotNull(tree);
    }

    @Test
    void testLines(){
        ArrayList<Segment> segments = new ArrayList<>();
        segments.add(new Segment(5,2, 10, 2));
        segments.add(new Segment(5,5, 10, 5));
        BSPTree tree = new BSPTree(segments);

        Segment hi = new Segment(5, 8, 10, 8);
        tree.insert(hi);
        assertNotEquals(tree.collision(hi), hi);

        //bye and hi are colliding
        Segment bye = new Segment(6, 6, 6, 10);
        tree.insert(bye);
        //tree returns that bye is coliding with hi
        assertEquals(tree.collision(bye), hi);
    }

    @Test
    void testTraversalCount(){
        ArrayList<Segment> segments = new ArrayList<>();

        segments.add(new Segment(5,2, 10, 2));
        segments.add(new Segment(5,5, 10, 5));
        BSPTree tree = new BSPTree(segments);

        Segment hi = new Segment(5, 7, 10, 7);

        CountCallBack aboveSegments = new CountCallBack();

        tree.insert(hi);

        tree.traverseFarToNear(9, 1, aboveSegments);
        //run traversal to count how many segments it sees

        assertNotNull(tree);
        assertEquals(3, aboveSegments.countCounter);
        assertNotNull(aboveSegments);

        assertEquals(hi, new Segment(5, 7, 10, 7));

        ArrayList<Segment> TopDown = new ArrayList<>();
        //in reverse order because it is at the top
        TopDown.add(new Segment(5,7,10,7));
        TopDown.add(new Segment(5,5,10,5));
        TopDown.add(new Segment(5, 2, 10, 2));

        assertEquals(aboveSegments.callbackList, TopDown);
        System.out.println(aboveSegments.callbackList);

        //OTHER WAY
        CountCallBack belowSegment = new CountCallBack();

        tree.traverseFarToNear(6, 10, belowSegment);

        ArrayList<Segment> bottomUp = new ArrayList<>();
        bottomUp.add(new Segment(5, 2, 10, 2));
        bottomUp.add(new Segment(5,5,10,5));
        bottomUp.add(new Segment(5,7,10,7));

        assertEquals(belowSegment.callbackList, bottomUp);

    }

}
