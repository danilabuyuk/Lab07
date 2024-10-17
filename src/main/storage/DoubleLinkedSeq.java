package storage;

public class DoubleLinkedSeq implements Cloneable 
{

    private int manyNodes;
    private DoubleNode tail;
    private DoubleNode head;
    private DoubleNode precursor;
    private DoubleNode cursor;

    public DoubleLinkedSeq() 
    {
        manyNodes = 0;
        tail = new DoubleNode();
        head = new DoubleNode();
        precursor = new DoubleNode();
        cursor = new DoubleNode();
    }

    public void addAfter(double element) throws OutOfMemoryError
    {
        if(isCurrent())
        {
            if(cursor.getLink() != null)
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
            if(manyNodes == 1) 
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
            if(manyNodes == 0) 
            {
                head = tail;
            }
        }
        manyNodes++;
    }
    
    public void addBefore(double element) throws OutOfMemoryError
    {
        if(isCurrent())
        {
            if(precursor != null)
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

    public void addAll(DoubleLinkedSeq addend) throws OutOfMemoryError, NullPointerException
    {
        if(addend != null)
        {
            if(this.equals(addend))
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
        else
        {
            throw new NullPointerException();
        }
    }

    public void advance() throws IllegalStateException
    {
        if(isCurrent())
        {
            if(!cursor.equals(tail))
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

    public void start() 
    {
        if(manyNodes > 0)
        {
            precursor = null;
            cursor = head;
        }
    }

    public double getCurrent() throws IllegalStateException
    {
        if(isCurrent())
        {
            return cursor.getData();
        }
        throw new IllegalStateException();
    }

    public void removeCurrent() throws IllegalStateException
    {
        if(isCurrent())
        {
            if(precursor != null)
            {
                if(cursor.getLink() != null)
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

    public boolean isCurrent()
    {
        if(cursor != null)
        {
            return cursor.getData() != 0;
        }
        else
        {
            return cursor != null;
        }
    }

    public int size()
    {
        return manyNodes;
    }

    public DoubleLinkedSeq clone() throws OutOfMemoryError
    {
        DoubleLinkedSeq clone = new DoubleLinkedSeq();
        DoubleNode i = head;
        /*clone.cursor.setData(cursor.getData());
        clone.precursor.setData(precursor.getData());
        clone.head.setData(head.getData());
        clone.tail.setData(tail.getData());
        clone.cursor.setLink(cursor.getLink());
        clone.precursor.setLink(precursor.getLink());
        clone.head.setLink(head.getLink());
        clone.tail.setLink(tail.getLink());
        clone.manyNodes = size();*/
        while(i != null && i.getData() != 0)
        {
            clone.addAfter(i.getData());
            i = i.getLink();
        }
        i = clone.head;
        if(precursor != null)
        {
            while(i.getData() != precursor.getData())
            {
               i = i.getLink();
            }
            clone.precursor = i;
            clone.cursor = i.getLink();
        }
        else
        {
            while(i.getData() != cursor.getData())
            {
                i = i.getLink();
            }
            clone.precursor = null;
            clone.cursor = i;
        }
        return clone;
    }

    public String toString()
    {
        String out = "<";
        DoubleNode i = head;
        while(i != null && i.getData() != 0)
        {
            if(cursor != null && i.getData() == cursor.getData() && i.getLink() == cursor.getLink())
            {
                out += "[" + i.getData() + "]";
            }
            else
            {
                out += i.getData();
            }
            i = i.getLink();
            if(i != null && i.getData() != 0)
            {
                out += ", ";
            }
        }
        out += ">";
        return out;
    }

    public boolean equals(Object equal)
    {
        DoubleLinkedSeq temp = (DoubleLinkedSeq)equal;
        return compareNodes(this.tail, temp.tail) && 
        compareNodes(this.head, temp.head) &&
        compareNodes(this.cursor, temp.cursor) &&
        compareNodes(this.precursor, temp.precursor);
    }

    public static DoubleLinkedSeq concatenation(DoubleLinkedSeq s1, DoubleLinkedSeq s2) throws OutOfMemoryError, IllegalArgumentException
    {
        DoubleLinkedSeq s11 = s1.clone();
        DoubleLinkedSeq s22 = s2.clone();
        s11.addAll(s22);
        s11.cursor = null;
        s11.precursor = null;
        return s11;
    }

    public boolean compareNodes(DoubleNode n1, DoubleNode n2) 
    {
        if(n1 != null && n2 != null)
        {       
            if(n1.getLink() != null && n2.getLink() != null)
            {
                return n1.getData() == n2.getData() && n1.getLink().getData() == n2.getLink().getData();
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
    
    public DoubleNode getNode(DoubleNode node)
    {
        return node;
    }
}