package net.jueb.util4j.beta.tools.avlMap;

/*
Course: Comp 282
Semester: Summer 2016
Assignment: AVLMap
FileName: AVLNode.java
Author: Piechota, Michael
 */

import java.util.ArrayList;

public class AVLNode<K extends Comparable<K>, V> implements Comparable<K>
{
    private int height;
    private K key;
    private V value;
    private AVLNode<K, V> left;
    private AVLNode<K, V> right;

    public AVLNode(K k)
    {
        key = k;
    }

    public AVLNode(K k, V v)
    {
        key = k;
        value = v;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public K getKey()
    {
        return key;
    }

    public void setKey(K key)
    {
        this.key = key;
    }

    public V getValue()
    {
        return value;
    }

    public void setValue(V value)
    {
        this.value = value;
    }

    public AVLNode<K, V> getLeft()
    {
        return left;
    }

    public void setLeft(AVLNode<K, V> left)
    {
        this.left = left;
    }

    public AVLNode<K, V> getRight()
    {
        return right;
    }

    public void setRight(AVLNode<K, V> right)
    {
        this.right = right;
    }

    @Override
    public boolean equals(Object obj)
    {
        return key.equals((AVLNode) obj);
    }

    @Override
    public int compareTo(K o)
    {
        return key.compareTo(o);
    }

    @Override
    public String toString()
    {
        return "<" + key + ", " + value + ">";
    }

    //this method fixes the tree by restoring balance to the tree
    public void fixMe(K o, AVLNode<K, V> rootArg) {
        //create an array list called "path" of nodes
        ArrayList<AVLNode<K, V>> path = new ArrayList<AVLNode<K, V>>();
        AVLNode<K, V> current = rootArg; // start from the root
        while (current != null)
        {
            path.add(current); //this adds a node to the list "path"
            if (o.compareTo(current.getKey()) < 0)
            {
                current = current.getLeft();
            }
            else if (o.compareTo(current.getKey()) > 0)
            {
                current = current.getRight();
            }
            else
            {
                break;
            }
        }

        //"root" needed to pass the root from AVLMap class
        AVLNode<K, V> root = rootArg;
        for (int i = path.size() - 1; i >= 0; i--) {
            AVLNode<K, V> A = (AVLNode<K, V>) (path.get(i));
            A.updateHeight(A);
            AVLNode<K, V> parentOfA = (A == root) ? null :
                    (AVLNode<K, V>) (path.get(i - 1));

            switch (A.nodeBalanceFactor(A))
            {
                case -2:
                    if(A.nodeBalanceFactor((AVLNode<K, V>) A.getLeft()) <= 0)
                    { // Perform left left rotation
                        A.balanceLeftLeft(A, parentOfA, root);
                    }
                    else
                    { // Perform a left right rotation
                        A.balanceLeftRight(A, parentOfA, root);
                    }
                    break;
                case +2:
                    if (A.nodeBalanceFactor((AVLNode<K, V>)
                                        A.getRight()) >= 0)
                    {
                        //do a right right rotation
                        A.balanceRightRight(A, parentOfA, root);
                    }
                    else //do a right left rotation
                    {
                        A.balanceRightLeft(A, parentOfA, root);
                    }
            }
        }
    }


    /*Finds the balance factor of a node which is defined to be
     the height difference:
     BalanceFactor(N) := –Height(LeftSubtree(N)) + Height(RightSubtree(N))*/
    public int nodeBalanceFactor(AVLNode<K, V> node)
    {
        if (node.getRight() == null) //if the node has no right subtree
        {
            return -node.getHeight();
        }
        else if (node.getLeft() == null)//if the node has no left subtree
        {
            return +node.getHeight();
        }
        else
        {
            return ((AVLNode<K, V>) node.getRight()).getHeight()
                    - ((AVLNode<K, V>) node.getLeft()).getHeight();
        }
    }


    /*performs a right right rotation */
    private void balanceRightRight(AVLNode<K, V> A_rightHeavy,
                                   AVLNode<K, V> parentOfA,
                                   AVLNode<K, V> rootArg)
    {
        AVLNode<K, V> B_rightHeavy = A_rightHeavy.right;
        AVLNode<K, V> root = rootArg;

        if(A_rightHeavy == root)
        {
            root = B_rightHeavy;
        }
        else if(parentOfA.left == A_rightHeavy)
        {
            parentOfA.left = B_rightHeavy;
        }
        else
        {
            parentOfA.right = B_rightHeavy;
        }

        //B_rightHeavy is now the subtree of A_rightHeavy
        A_rightHeavy.right = B_rightHeavy.left;
        B_rightHeavy.left = A_rightHeavy;
        updateHeight((AVLNode<K, V>) A_rightHeavy);
        updateHeight((AVLNode<K, V>) B_rightHeavy);
    }


    /*performs a left left rotation   */
    public void balanceLeftLeft(AVLNode<K, V> A_leftHeavy,
                                AVLNode<K, V> parentOfA,
                                AVLNode<K, V> rootArg)
    {
        AVLNode<K, V> B_leftHeavy = A_leftHeavy.getLeft();
        AVLNode<K, V> root = rootArg;

        if (A_leftHeavy == root)
        {
            root = B_leftHeavy;
        }
        else if (parentOfA.getLeft() == A_leftHeavy)
        {
            parentOfA.setLeft(B_leftHeavy);
        }
        else
        {
            parentOfA.setRight(B_leftHeavy);
        }

        A_leftHeavy.setLeft(B_leftHeavy.getRight());
        //now make A_leftHeavy the left child of B_leftHeavy
        B_leftHeavy.setRight(A_leftHeavy);

        A_leftHeavy.updateHeight((AVLNode<K, V>) A_leftHeavy);
        A_leftHeavy.updateHeight((AVLNode<K, V>) B_leftHeavy);

    }


    /*
     Performs a left right rotation
     */
    public void balanceLeftRight(AVLNode<K, V> nodeA,
                                 AVLNode<K, V> parentOfA,
                                 AVLNode<K, V> rootArg)
    {
        AVLNode<K, V> nodeB = nodeA.getLeft(); // A is left-heavy
        AVLNode<K, V> nodeC = nodeB.getRight(); // B is right-heavy
        AVLNode<K, V> root = rootArg;

        if (nodeA == root) {
            root = nodeC;
        } else if (parentOfA.getLeft() == nodeA) {
            parentOfA.setLeft(nodeC);
        } else {
            parentOfA.setRight(nodeC);
        }

        //now change subtrees
        nodeA.setLeft(nodeC.getRight());
        nodeB.setRight(nodeC.getLeft());
        nodeC.setLeft(nodeB);
        nodeC.setRight(nodeA);

        //now adjust the heights after the rotations
        nodeA.updateHeight((AVLNode<K, V>) nodeA);
        nodeB.updateHeight((AVLNode<K, V>) nodeB);
        nodeC.updateHeight((AVLNode<K, V>) nodeC);
    }


    /*performs a right left rotation */
    public void balanceRightLeft(AVLNode<K, V> nodeA,
                                 AVLNode<K, V> parentOfA,
                                 AVLNode<K, V> rootArg)
    {
        AVLNode<K, V> nodeB = nodeA.getRight(); // A is right-heavy
        AVLNode<K, V> nodeC = nodeB.getLeft(); // B is left-heavy
        AVLNode<K, V> root = rootArg;

        if (nodeA == root)
        {
            root = nodeC;
        }
        else if (parentOfA.getLeft() == nodeA)
        {
            parentOfA.setLeft(nodeC);
        }
        else
        {
            parentOfA.setRight(nodeC);
        }

        nodeA.setRight(nodeC.getLeft());
        nodeB.setLeft(nodeC.getRight());
        nodeC.setLeft(nodeA);
        nodeC.setRight(nodeB);

        // Adjust heights
        nodeA.updateHeight((AVLNode<K, V>) nodeA);
        nodeB.updateHeight((AVLNode<K, V>) nodeB);
        nodeC.updateHeight((AVLNode<K, V>) nodeC);
    }


    public void updateHeight(AVLNode<K, V> node)
    {
        //check if the node is a leaf
        if (node.getLeft() == null && node.getRight() == null)
        {
            node.setHeight(0);
        }
        //check if the node has no left subtree
        else if (node.getLeft() == null)
        {
            node.setHeight(1 + ((AVLNode<K, V>)
                    (node.getRight())).getHeight());
        }
        //check if the node has no right subtree
        else if (node.getRight() == null)
        {
            node.setHeight(1 + ((AVLNode<K, V>)
                    (node.getLeft())).getHeight());
        }
        else
        {
            node.setHeight(1 + Math.max(((AVLNode<K, V>)
                    (node.getRight())).getHeight(), ((AVLNode<K, V>)
                    (node.getLeft())).getHeight()));
        }
    }



}
