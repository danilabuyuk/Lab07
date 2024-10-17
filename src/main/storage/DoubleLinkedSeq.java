package storage;

/**
* This class is a homework assignment;
* A <CODE>DoubleLinkedSeq</CODE> is a collection of 
<CODE>double</CODE> numbers.
* The sequence can have a special "current element," which is specified and 
* accessed through four methods that are not available in the sequence class 
* (start, getCurrent, advance and isCurrent).
* @version Oct 17, 2024
* @author Daniel Buyuk
*/
public class DoubleLinkedSeq implements Cloneable 
{

    private int manyNodes;
    private DoubleNode tail;
    private DoubleNode head;
    private DoubleNode precursor;
    private DoubleNode cursor;

    /**
   * Initialize an empty sequence.
   */  
    public DoubleLinkedSeq() 
    {
        manyNodes = 0;
        tail = new DoubleNode();
        head = new DoubleNode();
        precursor = new DoubleNode();
        cursor = new DoubleNode();
    }

    /**
   * Add a new element to this sequence, after the current element. 
   * @param element
   *   the new element that is being added
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for a new node.
   **/
    public void addAfter(double element) throws OutOfMemoryError
    {
        if (isCurrent())
        {
            if (cursor.getLink() != null)
            {
                cursor.setLink(new DoubleNode(element, cursor.getLink()));
                precursor = cursor;
                cursor = cursor.getLink();
            }
            else
            {
                cursor.setLink(new DoubleNode(element));
                precursor = cursor;
                cursor = cursor.getLink();
                tail = cursor;
            }
            if (manyNodes == 1) 
            {
                tail = cursor;
                head = precursor;
            }
        }
        else
        {
            tail.setLink(new DoubleNode(element, null));
            precursor = tail;
            cursor = tail.getLink();
            tail = cursor;
            if (manyNodes == 0) 
            {
                head = tail;
            }
        }
        manyNodes++;
    }
    
    /**
   * Add a new element to this sequence, before the current element. 
   * @param element
   *   the new element that is being added
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for a new node.
   **/
    public void addBefore(double element) throws OutOfMemoryError
    {
        if (isCurrent())
        {
            if (precursor != null)
            {
                precursor.setLink(new DoubleNode(element, cursor));
                cursor = precursor.getLink();
                
            }
            else
            {
                head = new DoubleNode(element, head);
                precursor = null;
                cursor = head;
            }
        }
        else
        {
            head = new DoubleNode(element, head);
            precursor = null;
            cursor = head;
        }
        manyNodes++;
    }

    /**
   * Add a new element to this sequence, before the current element. 
   * @param addend
   *   a sequence whose contents will be placed at the end of this sequence
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for a new node.
   * @exception NullPointerException
   *   Indicates that addend is null.
   **/
    public void addAll(DoubleLinkedSeq addend)
        throws OutOfMemoryError, NullPointerException
    {
        //if (addend != null)
        //{
        if (this.equals(addend))
        {
            DoubleLinkedSeq temp = addend.clone();
            tail.setLink(temp.head);
            tail = temp.tail;
            manyNodes += temp.manyNodes;
        }
        else
        {
            tail.setLink(addend.head);
            tail = addend.tail;
            manyNodes += addend.manyNodes;
        }
    }

    /**
   * Move forward, so that the current element is now the next element in
   * this sequence.
   * @exception IllegalStateException
   *   Indicates that there is no current element, so 
   *   advance may not be called.
   **/
    public void advance() throws IllegalStateException
    {
        if (isCurrent())
        {
            if (!cursor.equals(tail))
            {
                precursor = cursor;
                cursor = cursor.getLink();
            }
            else
            {
                precursor = null;
                cursor = null;
            }
        }
        else
        {
            throw new IllegalStateException();
        }
    }

    /**
   * Set the current element at the front of this sequence.
   **/ 
    public void start() 
    {
        if (manyNodes > 0)
        {
            precursor = null;
            cursor = head;
        }
    }

    /**
   * Accessor method to get the current element of this sequence.
   * @return
   *   the current capacity of this sequence
   * @exception IllegalStateException
   *   Indicates that there is no current element, so 
   *   getCurrent may not be called.
   **/
    public double getCurrent() throws IllegalStateException
    {
        if (isCurrent())
        {
            return cursor.getData();
        }
        throw new IllegalStateException();
    }

    /**
   * Remove the current element from this sequence.
   * @exception IllegalStateException
   *   Indicates that there is no current element, so 
   *   removeCurrent may not be called. 
   **/
    public void removeCurrent() throws IllegalStateException
    {
        if (isCurrent())
        {
            if (precursor != null)
            {
                if (cursor.getLink() != null)
                {
                    precursor.setLink(cursor.getLink());
                    cursor = cursor.getLink();
                    
                }
                else
                {
                    precursor.setLink(null);
                    cursor = null;
                }
            }
            else
            {
                head = head.getLink();
                cursor = cursor.getLink();
            }
            manyNodes--;
        }
        else
        {
            throw new IllegalStateException();
        }
    }

    /**
   * Accessor method to determine whether this sequence has a specified 
   * current element that can be retrieved with the 
   * getCurrent method. 
   * @return
   *   true (there is a current element) 
   *   or false (there is no current element at the moment)
   **/
    public boolean isCurrent()
    {
        if (cursor != null)
        {
            return cursor.getData() != 0;
        }
        else
        {
            return cursor != null;
        }
    }

    /**
   * Determine the number of elements in this sequence.
   * @return
   *   the number of elements in this sequence
   **/ 
    public int size()
    {
        return manyNodes;
    }

    /**
   * Generate a copy of this sequence.
   * @return
   *   The return value is a copy of this sequence. Subsequent changes to the
   *   copy will not affect the original, nor vice versa.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for creating the clone.
   **/ 
    public DoubleLinkedSeq clone() throws OutOfMemoryError
    {
        DoubleLinkedSeq clone = new DoubleLinkedSeq();
        DoubleNode i = head;
        while (i != null && i.getData() != 0)
        {
            clone.addAfter(i.getData());
            i = i.getLink();
        }
        i = clone.head;
        if (precursor != null)
        {
            while (i.getData() != precursor.getData())
            {
                i = i.getLink();
            }
            clone.precursor = i;
            clone.cursor = i.getLink();
        }
        else
        {
            while (i.getData() != cursor.getData())
            {
                i = i.getLink();
            }
            clone.precursor = null;
            clone.cursor = i;
        }
        return clone;
    }

    /**
     * Print the linked sequence into a String.
     * @return
     *String object with all of the values listed and current value 
     (if present) encased.
     */
    public String toString()
    {
        String out = "<";
        DoubleNode i = head;
        while (i != null && i.getData() != 0)
        {
            if (cursor != null 
                && i.getData() == cursor.getData() 
                && i.getLink() == cursor.getLink())
            {
                out += "[" + i.getData() + "]";
            }
            else
            {
                out += i.getData();
            }
            i = i.getLink();
            if (i != null && i.getData() != 0)
            {
                out += ", ";
            }
        }
        out += ">";
        return out;
    }

    /**
     * Compares the object passed as a parameter 
     * to the current linked sequence.
     * @param equal
     *  Object to be compared to the linked sequence.
     * @return
     *  true if they are equal, false otherwise.
     */
    public boolean equals(Object equal)
    {
        DoubleLinkedSeq temp = (DoubleLinkedSeq) equal;
        return compareNodes(this.tail, temp.tail) 
            && compareNodes(this.head, temp.head) 
            && compareNodes(this.cursor, temp.cursor) 
            && compareNodes(this.precursor, temp.precursor);
    }

    /**
     * Joins two linked sequences together 
     * and returns the combination of both.
     * @param s1
     *  First linked sequence to be added to.
     * @param s2
     *  Second linked sequence that is added.
     * @return
     *  Combination of the two parameters
     * @throws OutOfMemoryError
     *  Thrown if there is insufficient memory for the new sequence.
     * @throws IllegalArgumentException
     *  Thrown if one of the sequences is null.
     */
    public static DoubleLinkedSeq concatenation(DoubleLinkedSeq s1, 
        DoubleLinkedSeq s2) throws OutOfMemoryError, IllegalArgumentException
    {
        DoubleLinkedSeq s11 = s1.clone();
        DoubleLinkedSeq s22 = s2.clone();
        s11.addAll(s22);
        s11.cursor = null;
        s11.precursor = null;
        return s11;
    }

    /**
     * Compares two DoubleNode objects passed as parameters.
     * @param n1
     * DoubleNode object to compare
     * @param n2
     * DoubleNode object to compare
     * @return
     * true if the two nodes are equal, false otherwise.
     */
    public boolean compareNodes(DoubleNode n1, DoubleNode n2) 
    {
        if (n1 != null && n2 != null)
        {       
            if (n1.getLink() != null && n2.getLink() != null)
            {
                return n1.getData() == n2.getData() 
                    && n1.getLink().getData() == n2.getLink().getData();
            }
            else
            {
                return n1.getData() == n2.getData();
            }
        }
        else
        {
            return n1 == n2;
        }
    }
}