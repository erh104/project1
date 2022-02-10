package cs1501_p1;
public class BST<T extends Comparable<T>> implements BST_Inter<T>
{
    private BTNode<T> root;
    private String holderText;
    private BTNode<T> temp;
    public BST(){
        root = null;
    }
    public BST(T value){
        root = new BTNode<T>(value);
    }
    private BTNode<T> getRoot()
    {
        return root;
    }
    private void setRoot(BTNode<T> value)
    {
        root = value;
    }
    public void put(T key)
    {
        root = put_Recursive(root, key);
        
    }

    private BTNode<T> put_Recursive(BTNode<T> home, T key)
    {
        
        if(home == null)
        {
            return new BTNode<T>(key);
        }
        
        
        if(key.compareTo(home.getKey()) < 0)
        {
            
            if(home.getLeft() != null)
                home.setLeft(put_Recursive(home.getLeft(), key));
            else{
                home.setLeft(new BTNode<T>(key));
                
            return home;
            }
        }
        else if(key.compareTo(home.getKey()) > 0)
        {
            if(home.getRight() !=null)
                home.setRight(put_Recursive(home.getRight(),key));
            else
            {
                home.setRight(new BTNode<T>(key));
                
            }
        }
        return home;

    }
    public boolean contains(T key)
    {
        return contains_Recursive(root,key);
    }
    private boolean contains_Recursive(BTNode<T> home, T key)
    {
        
        if(home == null)
        {
            return false;
        }
        
        
        if(home.getKey().compareTo(key) != 0)
        {
            
            
            
            if(key.compareTo(home.getKey()) < 0)
            {
                
                
                return contains_Recursive(home.getLeft(),key);
            }
            else if(key.compareTo(home.getKey()) > 0)
            {
               
                return contains_Recursive(home.getRight(),key);
            }
            else
                return false;

        }
        else
        {
            
            return true;
        }
                
    }

    public void delete(T key)
    {
        if(contains_Recursive(root,key) == false)
            return;
        root = delete_Recursive(root,key,root);
    }
    private BTNode<T> delete_Recursive(BTNode<T> home, T key, BTNode<T> rootCopy)
    {
        //is root null?
        if(home == null)
            return home;
        
        //is the key less than the current node values key?
        if(key.compareTo(home.getKey()) < 0)
        {
            rootCopy = home;
            home = delete_Recursive(home.getLeft(),key,rootCopy);
        }
        //is it greater?
        else if(key.compareTo(home.getKey()) > 0)
        {
            rootCopy = home;
            home = delete_Recursive(home.getRight(),key,rootCopy);
        }
        else
        {
            //take left and right child
            BTNode<T> left = home.getLeft();
            BTNode<T> right = home.getRight();
            //if no left child; give the right
            if(left == null)
                return right;
            //if no right child; give left
            else if(right == null)
                return left;
            else if(left == null && right == null)
            {
                //if no children..its not in our data structure
                return null;
            }
            //if value is found!!
            else
            {
                BTNode<T> child;
                //find the child to replace the value
                child = traverseChildren(home.getRight());
                //if the child is less than the root, place to the left; then set its right child to the other home value
                if(rootCopy.getKey().compareTo(child.getKey()) > 0)
                {
                    rootCopy.setLeft(child);
                    
                    child.setRight(home.getRight());
                }
                else if(rootCopy.getKey().compareTo(child.getKey()) < 0)
                {
                    rootCopy.setRight(child);
                    
                    child.setLeft(home.getLeft());
                }
                return rootCopy;
                
                
                
            }
        }
        return home;
    }
    public BTNode<T> traverseChildren(BTNode<T> root)
    {
        T key = root.getKey();
        while(root.getLeft() != null)
        {
            key = root.getLeft().getKey();
            root = root.getLeft();
        }
        //return leftmost child
        return new BTNode<T>(key);
        
    }
    public int height()
    {
        if(root == null)
            return 0;
        return height(root);
        
    }
    private int height(BTNode<T> home)
    {
        if(home == null)
            return 0;
        return 1 + Math.max(height(home.getLeft()),height(home.getRight()));
    }
    public boolean isBalanced()
    {
        return verifyIsBalanced(root);
    }
    private boolean verifyIsBalanced(BTNode<T> home)
    {
        if(home == null)
            return true;
        int leftH = height(home.getLeft());
        int rightH = height(home.getRight());
        return Math.abs(leftH - rightH) <= 1 && verifyIsBalanced(home.getLeft()) && verifyIsBalanced(home.getRight());
        
    }
    public String inOrderTraversal()
    {
        holderText = "";
        holderText = inOrder(root);
        if(holderText.endsWith(":"))
        {
            holderText = holderText.substring(0, holderText.length() -1);
        }
        return holderText;
    }
    private String inOrder(BTNode<T> home)
    {
        if(home == null)
            return "";
        inOrder(home.getLeft());
        holderText = holderText + home.getKey() + ":";
        inOrder(home.getRight());
        return holderText;
    }
    public String serialize()
    {
        holderText = "";
        holderText = serialize_Recurse(root, 0);    
        if(holderText.endsWith(","))
        {
            holderText = holderText.substring(0, holderText.length() -1);
        }
        return holderText.trim();
    }
    private String serialize_Recurse(BTNode<T> home, int counter)
    {
        
        switch(counter)
            {
                case 0: holderText = holderText + "R(" + home.getKey() + "),"; break;
                case 1: 
                if(home.getLeft() != null || home.getRight() != null)
                     holderText = holderText + "I(" + home.getKey() + "),";
                else   
                    holderText = holderText + "L(" + home.getKey() + "),";
                break;
                case 2: holderText = holderText + "L(" + home.getKey() + "),"; break;
            }
        
        counter ++;
        if(home.getLeft() != null)
            serialize_Recurse(home.getLeft(),counter);
        if(home.getRight() != null)
            serialize_Recurse(home.getRight(),counter);
        if(home.getLeft() == null && home.getRight() != null && counter < 2) 
            holderText = holderText + "X(null),";
        if(home.getRight() == null && home.getLeft() != null && counter < 2) 
            holderText = holderText + "X(null),";
        return holderText;
    }
    public BST_Inter<T> reverse()
    {
        temp = root;
        BST reverseBST = new BST(temp.getKey());
        BTNode<T> saved = reverseTree(root,reverseBST.getRoot());
        reverseBST.setRoot(saved);
        return reverseBST;
    }
    private BTNode<T> reverseTree(BTNode<T> home, BTNode<T> tempRoot)
    {
        if(home==null)
            return home;
        else
        {
            
            BTNode<T> tempNode;
            tempNode = home.getLeft();
            tempRoot.setLeft(home.getRight());
            tempRoot.setRight(tempNode);
            
            
            if(tempNode.getLeft()!=null)
                reverseTree(tempNode, tempRoot.getRight());
            else if(home.getRight().getRight()!=null)
                reverseTree(home.getRight(), tempRoot.getLeft());

            
            return tempRoot;
        }
    }

}