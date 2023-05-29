package net.jueb.util4j.beta.tools.avlMap;

/*
Course: Comp 282
Semester: Summer 2016
Assignment: AVLMap
FileName: AVLMap.java
Author: Piechota, Michael
 */

import java.util.Stack;

public class AVLMap<K extends Comparable<K>, V>
{

    private AVLNode<K, V> root;
    private int size;

    public AVLMap()
    {
        size = 0;
    }

    public void clear()
    {
        root = null;
        size = 0;
    }

    public int size()
    {
        return size;
    }

    public boolean containsKey(Object key)
    {
        if (key == null) {
            throw new NullPointerException();
        }
        AVLNode<K, V> node = root;
        while (node != null)
        {
            //if the key is smaller than go left
            if (node.getKey().compareTo((K) key) > 0)
            {
                node = node.getLeft();
            }
            //if the key is larger than go right
            else if (node.getKey().compareTo((K) key) < 0)
            {
                node = node.getRight();
            }
            else
            {
                return true;
            }
        }
        //if it is not found
        return false;
    }

    public boolean containsValue(Object value)
    {
        if (root == null) //if the tree is empty
        {
            return false;
        }

        /*in order to search all the nodes in the tree, an iterative
         stack is implemented. */
        Stack<AVLNode<K, V>> stack = new Stack<AVLNode<K, V>>();
        stack.push(root);

        while (!stack.empty())
        {
            AVLNode<K, V> node = stack.pop();
            if (node.getValue().equals((V) value))
            {
                return true;
            }
            if (node.getRight() != null)
            {
                stack.push(node.getRight());
            }
            if (node.getLeft() != null)
            {
                stack.push(node.getLeft());
            }
        }
        return false;
    }

    public V get(Object key)
    {
        if (key == null)
        {
            throw new NullPointerException();
        }

        AVLNode<K, V> node = root;
        while (node != null)
        {
            if (node.getKey().compareTo((K) key) > 0)
            {
                node = node.getLeft();
            }
            else if (node.getKey().compareTo((K) key) < 0)
            {
                node = node.getRight();
            }
            else
            {
                return node.getValue();
            }
        }
        return null;
    }

    public V put(K key, V value)
    {
        if (key == null)
        {
            throw new NullPointerException();
        }

        //if exist then update the value and return old value
        if (containsKey(key))
        {
            V v = get(key);
            AVLNode<K, V> node = root;
            while (node != null)
            {
                if (node.getKey().compareTo((K) key) > 0)
                {
                    node = node.getLeft();
                }
                else if (node.getKey().compareTo((K) key) < 0)
                {
                    node = node.getRight();
                }
                else
                {
                    node.setValue(value);
                    break;
                }
            }
            return v;
        }
        //If there is a NEW KEY, create a node and restore the balance
        else //if the tree IS EMPTY:
        {
            if (root == null) //if the root IS NULL, create a new root
            {
                root = new AVLNode<K, V>(key, value); // Create a NEW root
                AVLNode<K, V> rootArg = root;
                rootArg.fixMe(key, root);
                size++;
                return null;
            }
            else
            {
                //if the tree is NOT emptry, find the parent node.
                //rootArg2 needed to give the method fix me the root argument
                AVLNode<K, V> parent = null;
                AVLNode<K, V> current = root;
                AVLNode<K, V> rootArg2 = current;

                while (current != null)
                {
                    if (key.compareTo(current.getKey()) < 0)
                    {
                        parent = current;
                        current = current.getLeft();
                    }
                    else if (key.compareTo(current.getKey()) > 0)
                    {
                        parent = current;
                        current = current.getRight();
                    }
                }
                //Now create the new node and attach it to the parent node
                if (key.compareTo(parent.getKey()) < 0)
                {
                    parent.setLeft(new AVLNode<K, V>(key, value));
                }
                else
                {
                    parent.setRight(new AVLNode<K, V>(key, value));
                }
                //re balances the tree; increments the size.
                rootArg2.fixMe(key, current);
                size++;
                return null;
            }
        }
    }

    public V remove(Object key)
    {
        if (key == null)
        {
            throw new NullPointerException();
        }
        if (root == null)
        {//returns null if the element you want to remove is NOT in the tree
            return null;
        }

        V v = get(key);
        /*now locate the node to be removed, and also located that node's
         parent node */
        AVLNode<K, V> parent = null;
        AVLNode<K, V> current = root;
        while (current != null)
        {
            if (current.getKey().compareTo((K) key) > 0)
            {
                parent = current;
                current = current.getLeft();
            }
            else if (current.getKey().compareTo((K) key) < 0)
            {
                parent = current;
                current = current.getRight();
            }
            else //the element IS THE TREE poointed at by "current"
            {
                break;
            }
        }

        if (current == null)
        { //the element is NOT in the tree
            return null;
        }
        if (current.getLeft() == null)
        { //if "current" has NO LEFT CHILDREN:
            if (parent == null)
            {
                root = current.getRight();
            }
            else if (parent.getKey().compareTo((K) key) > 0)
            {
                parent.setLeft(current.getRight());
            }
            else
            {
                parent.setRight(current.getRight());
            }
            current.fixMe(parent.getKey(), current); //re balance tree
        }
        else
        {
            /* "current" node has a left child
             therefore locate the right most node in the left
              subtree of the current node & its parent*/
            AVLNode<K, V> parentOfRightMost = current;
            AVLNode<K, V> rightMost = current.getLeft();

            while (rightMost.getRight() != null)
            {
                parentOfRightMost = rightMost;
                //keep on going to the right
                rightMost = rightMost.getRight();
            }
            //now replace the key in "current" w/key in rightMostNode
            current.setKey(rightMost.getKey());
            //now delete the rightMostNode node
            if (parentOfRightMost.getRight() == rightMost)
            {
                parentOfRightMost.setRight(rightMost.getLeft());
            }
            else
            {//if the parentOfTheRightMostNode is "current"
                parentOfRightMost.setLeft(rightMost.getLeft());
            }
            //now re balance the tree
            current.fixMe(parentOfRightMost.getKey(), current);
        }
        size--;
        return v;
    }



}
