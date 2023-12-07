package assignment09;

import java.util.ArrayList;
import java.util.List;

public class BSPTree{

    //-------------------------------------------NODE CLASS--------------------------------------------------\\
    private static class Node {
        Segment segment_;
        Node left_;
        Node right_;

        Node(Segment value) {
            segment_ = value;
            left_ = null;
            right_ = null;
        }
    }
    //---------------------------------------------------------------------------------------------------------\\

    private Node root_ = null;


    //empty constructor for a blank tree
    public BSPTree(){
        root_ = null;
    }

    //constructor
    public BSPTree(ArrayList<Segment> segments){
        if(segments != null && !segments.isEmpty()){
            root_ = buildBSPTree(segments);
        }
    }

/**
 //     *constructs the BSP tree recursively with the list of provided segments
 //     * @param segments an arraylist of segments
 //     * @return a node of the BSPTree
 //     */
    private Node buildBSPTree(ArrayList<Segment> segments){

        if(segments.isEmpty()) {
            return null;
        }

        int index = (int) (Math.random() * segments.size());
        //selecting a random segment from the list of segments provided
        Segment randomSegment = segments.get(index);
        //new node instance from the random segment
        Node node = new Node(segments.remove(index));


        //partition the segments into left and right
        ArrayList<Segment> leftSegments = new ArrayList<>();
        ArrayList<Segment> rightSegments = new ArrayList<>();

        for(Segment loopSegment : segments) {
            int side = node.segment_.whichSide(loopSegment);
            if (side < 0) {
                leftSegments.add(loopSegment);
            } else if (side > 0) {
                rightSegments.add(loopSegment);
            } else { //segment intersect the chosen segment
                Segment[] split = node.segment_.split(loopSegment);
                leftSegments.add(split[0]);
                rightSegments.add(split[1]);
            }
        }
        node.left_ = buildBSPTree(leftSegments);
        node.right_ = buildBSPTree(rightSegments);
        return node;
    }


    /**
     * add a segment to an existing tree
     * @param segment a segment that needs to be added to the BSPTree
     */
    public void insert(Segment segment){
        if(root_ == null){
            root_ = new Node(segment);
        }else {
            insertNodeRecursive(root_, segment);
        }
    }

    /**
     * traverses down the tree based on relationship between segments
     * splits lines / planes represented by each node
     * @param node starts at the root
     * @param segment the segment to be added to the tree
     * @return
     */
    private void insertNodeRecursive(Node node, Segment segment) {
        if (node == null) {
            node = new Node(segment);
        }

        int whichSide = segment.whichSide(node.segment_);

        if (whichSide > 0) { //new segment is on the left
            if (node.left_ == null) {
                node.left_ = new Node(segment);
            }else {
                insertNodeRecursive(node.left_, segment);
            }
        }
        if (whichSide < 0) { //new segment is on the right
            if (node.right_ == null) {
                node.right_ = new Node(segment);
            } else {
                insertNodeRecursive(node.right_, segment);
            }
        } else {//crosses the line
            Segment[] splitSegmentArray = segment.split(segment);
            if (node.left_ == null) {
                node.left_ = new Node(splitSegmentArray[0]);
            } else {
                insertNodeRecursive(node.left_, splitSegmentArray[0]);
            }
            //put the right half on the right side (or send it through the method again)
            if (node.right_ == null) {
                node.right_ = new Node(splitSegmentArray[1]);
            } else {
                insertNodeRecursive(node.right_, splitSegmentArray[1]);
            }
        }

    }


    /**
     * traverse the segments in "far to near" order relative to the point (x,y)
     * painters algorithm
     *
     * @param x coordinate of a point
     * @param y coordinate of a point
     * @param callback callback function to handle each segment encountered in the traversal
     */
    public void traverseFarToNear(double x, double y, SegmentCallback callback){
        traverseRecursive(root_, x, y, callback);
    }

    /**
     * initiate the traverse process from the root of the tree
     * recursively navigates down the tree based on the spatial relationship between segments
     * and the x,y
     * determines if the point is on the left or right side of the splitting line
     * @param node starts at root
     * @param x coordinate
     * @param y coordinate
     * @param callback callback function to handle each segment encountered in the traversal
     * //@param orderedSegments maintains a list of the segments in an order
     * @return
     */
    private void traverseRecursive(Node node, double x, double y, SegmentCallback callback){
        if(node == null) {
            return;
        }

        int side = node.segment_.whichSidePoint(x, y);
        //the + side is the front of the segment, the - side is behind it

        //navigate to the right side
        //
        if (side < 0) {
            //right side
            traverseRecursive(node.right_, x, y, callback);
            callback.callback(node.segment_);
            traverseRecursive(node.left_, x, y, callback);
        } else {
            //left side
            traverseRecursive(node.left_, x, y, callback);
            callback.callback(node.segment_);
            traverseRecursive(node.right_, x, y, callback);
        }
    }

    /**
     * @param query the segment in which to check for colissions
     * @return any segment in the tree which intersects with query
     */
    public Segment collision (Segment query){
        return collisionRecursive(root_, query);

    }

    /**
     * if an intersection is fund, returns the segment stored in that node
     * which is considered a collision
     * @param node starts at the root
     * @param query the segment checking for collision
     * @return
     */
    private Segment collisionRecursive(Node node, Segment query){
        //reached end of branch
        if(node == null){
            return null;
        }

        //find which side the query is on from the node
        int side = query.whichSide(node.segment_);
        //if it intersects

        //if the query is on the right side , recurse to the right subtree
        if(side < 0){
            return collisionRecursive(node.right_,query);
        }
        //else if it is on the left, recurse to the left subtree
        else if (side > 0){
            return collisionRecursive(node.left_,query);
        }
        //else the query segment could intersect the node segment
        //returns node segment as a collision
        else {
            if (query.intersects(node.segment_)) {
                return node.segment_;
            }
            //if there is no intersection, explore both subtrees again
            else{
                Segment left = collisionRecursive(node.right_, query);
                if(left != null){
                    return left;
                }else{
                    return collisionRecursive(node.left_, query);
                }
            }
        }
    }
}
