package edu.wmich.cs3310.hw2.main;

/**
 * We define the class in which work will
 * be performed on the search , the storage and removal of our data links
 * since the objects themselves
 * (data links are generalized),
 * the class for working with them will be generalized
 */
public class DoublyLinkList<T> {


    /**
     * for work we need the following fields.
     * Length - which characterizes the number
     * of elements in the list. And two fields containing elements
     */
    private   int length = 0;
    /**
     * when creating a list,
     * there are no elements yet,
     * therefore the fields characterizing the beginning and end
     * of the list are empty
     */
    Element begin = null;
    Element end = null;


    /**
     * this method adds an item to the begin of the list
     */
    public Element PushToBegin(T data){

        //create a new element and put data in it
        Element element = new Element(data);
        //if the list is empty
        //then the created element is
        // both the beginning and the end of the list
        if(length==0){
            begin = element;
            end = element;
        }else{
            /**
             * If there is an element in the list, in this case,
             * the value of addressnext  will be "begin".
             * And the field "begin" is assigned the value of the current element
             */
            element.setAddressNext(begin);
            begin.setAddressPrevious(element);
            begin = element;
        }
        //also increase the length of the list
        length++;
        return element;
    }

    /**
     * this method has the same algorithm
     * of actions, but is the opposite
     * of the previous method
     */
    public Element PushEnd(T data){
        Element element = new Element(data);
        if(length==0){
            begin = element;
        }else{
            end.setAddressNext(element);
            element.setAddressPrevious(end);
        }
        end = element;
        length++;
        return element;
    }

    /**
     *
     * This method adds elements of
     * another list to the current one.
     */
    public DoublyLinkList<T> Append( DoublyLinkList<T> b ){

        for (int i=1;i<=b.Length();i++){
            this.PushEnd((T) b.GetNth(i));
        }
        var c= this;
        b.DeleteList();
        return (DoublyLinkList<T>) c;
    }

    /**
     * This method given a list that is sorted in increasing order, and a
     * single node, inserts the node into the correct sorted position in the list.
     * @param element
     */
    public void sortedInsert(Element element)
    {
        Element current;

        /* Special case for head node */
        if (begin == null || (int)begin.getData() >= (int)element.getData())
        {
            element.setAddressNext(begin);
            begin = element;
        }
        else {

            current = begin;

            while (current.getAddressNext() != null &&
                    (int)current.getAddressNext().getData() < (int)element.getData())
                current = current.getAddressNext();

            element.setAddressNext(current.getAddressNext());
            current.setAddressNext(element);
        }
    }

    /**
     * In this method, we InsertNth an
     * element after the specified element.
     * For the specified position,
     * the index of the element is used,
     * also the method accepts the data
     * that needs to be placed in the list
     */
    public void InsertNth(int index, T value)
    {
        if(index > length || index < 0)
        {
            throw new IndexOutOfBoundsException();
        }
        if (begin == null)
        {
            Element n = new Element();
            n.setAddressNext(n);
            n.setAddressPrevious(n);
            begin = n;
        }
        else
        {
            Element current = begin;
            for (int i = 0; i < index; i++)
            {
                current = current.getAddressNext();
            }
            //current points to node that will follow new node.
            Element n2 = new Element(value, current, current.getAddressPrevious());
            current.getAddressPrevious().setAddressNext(n2);
            current.setAddressPrevious(n2);
            if(index == 0)
            {
                begin = n2;
            }
        }
        length++;
    }

    /**
     * This method removes items from the begin of the list.
     * @return
     */
    public T PopBegin(){
        if(length>0){
            begin = begin.getAddressNext();
            length--;

        }
        return (T) begin.getData();
    }
    public T DeleteList()
    {
        begin=null;
        return (T) begin;
    }
    /**
     * This method removes items from the end of the list.
     */
    public T PopEnd(){
        if(length==0){
            return null;
        }else if(length==1){
            PopBegin();
        }else{
            var element = end.getAddressPrevious();
            end = element;
            end.setAddressNext(null);
            length--;

        }
        return (T) end.getData();
    }

    /**
     * This method provides information using
     * the current position of the object.
     * @param index
     * @return
     */
    public T GetNth(int index){
        if(index>length){
            return null;
        }
        Element element = begin;
        while(index-1!=0){
            element=element.getAddressNext();
            index--;
        }
        return (T) element.getData();
    }

    /**
     * This method counts the number of elements
     * that correspond to the object passed as a parameter
     */
    int Count(T need)
    {
        Element current = begin;
        int count = 0;
        while (current != null) {
            if (current.getData().equals(need))
                count++;
            current = current.getAddressNext();
        }
        return count;
    }

    /**
     * This method indicates the number of items in the list.
     */
    public int Length(){
        return length;
    }


}
