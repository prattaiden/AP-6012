package assignment09;

import java.util.ArrayList;

public class BSPTree{

    //-------------------------------------------NODE CLASS--------------------------------------------------\\
    class Node {
        Segment segment_;
        Node left_;
        Node right_;
        public int height_ = -1;

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
        if(segments != null && segments.isEmpty()){
            root_ = makeNode(segments);
        }
    }



    public Node getRoot(){
        return root_;
    }
    private Node makeNode(ArrayList<Segment> segments){

        //TODO FILL IN
        Segment randomSegment = segments.get((int) (Math.random() * segments.size()));
        Node node = new Node(randomSegment);

        //setting the random segment to the node's segment
        node.segment_ = randomSegment;

        //partition the segments into left and right
        ArrayList<Segment> leftSegments = new ArrayList<>();
        ArrayList<Segment> rightSegments = new ArrayList<>();

        for(Segment loopSegment : segments){
            int side = node.segment_.whichSide(loopSegment);
            if(side < 0 ){
                leftSegments.add(loopSegment);
            }
            else if (side > 0){
                rightSegments.add(loopSegment);
            }
        }
        node.left_ = makeNode(leftSegments);
        node.right_ = makeNode(rightSegments);
        return node;
    }

    /**
     * add a segment to an existing tree
     * @param segment
     */
    public void insert(Segment segment){

        root_ = insertNodeRecursive(root_, segment);
    }

    private Node insertNodeRecursive(Node node, Segment segment){
        if(node == null){
            return new Node(segment);
        }

        int whichSide = segment.whichSide(node.segment_);

        if(whichSide < 0) { //new segment is on the left
            node.left_ = insertNodeRecursive(node.left_, segment);
        } else if (whichSide > 0) { //new segment is on the right
            node.right_ = insertNodeRecursive(node.right_, segment);
        }
        else{//crosses the line
            Segment[] splitSegmentArray = segment.split(node.segment_);

            //insert segments that were split
            node.left_ = insertNodeRecursive(node.left_, splitSegmentArray[1]);
            node.right_ = insertNodeRecursive(node.right_, splitSegmentArray[0]);
        }
        return node;
    }


    /**
     * traverse the segments in "far to near" order relative to the point (x,y)
     * painters algorithm
     *
     * @param x
     * @param y
     * @param callback
     */
    void traverseFarToNear(double x, double y, SegmentCallback callback){
        traverseRecursive(root_, x, y, callback);

    }

    private void  traverseRecursive(Node node, double x, double y, SegmentCallback callback){
        if(node == null){
            return;
        }

        int Side = node.segment_.whichSidePoint(x, y);

        //similar to insertion sort
        if(Side < 0){
            traverseRecursive(node.right_,x,y,callback);
            callback.callback(node.segment_);
            traverseRecursive(node.left_,x,y,callback);
        } else{
            traverseRecursive(node.left_,x,y,callback);
            callback.callback(node.segment_);
            traverseRecursive(node.right_,x,y,callback);
        }
    }

    /**
     *
     * @param query
     * @return any segment in the tree which intersects with query
     */
    Segment collision (Segment query){
        return collisionRecursive(root_, query);

    }

    private Segment collisionRecursive(Node node, Segment query){
        if(node == null){
            return null;
        }

        //if it intersects
        if(node.segment_.intersects(query)){
            return node.segment_;
        }

        if(node.segment_.whichSide(query) > 0) {
            Segment left = collisionRecursive(node.left_, query);
            if (left != null) {
                return left;
            }

            return collisionRecursive(node.right_,query);

        }
        //else will return the searching in the right subtree
        else{
            Segment right = collisionRecursive(node.right_, query);
            if(right != null){
                return right;
            }

            return collisionRecursive(node.left_,query);
        }
    }
}
