package edu.wmich.cs3310.hw2.main;

/**
 * We define a class that will contain data,
 * as well as links to the previous and next object
 * in the list of similar objects
 * We indicate that our class will be generalized
 * and in the object of our class in the data field,
 * data of any type can be stored
 * @param <T>
 */
public class Element<T> {

    private T data;

    private Element AddressNext;

    private Element AddressPrevious;
    public Element(){ }

    /**
     * as soon as an object is created,
     * information from the constructor is
     * stored in the data field
     * @param data
     */
    public Element(T data){
        this.data = data;
    }
    public Element(T Data,Element next, Element prev){
        this.data = Data;
        AddressNext = next;
        AddressPrevious = prev;
    }

    /**
     * announce getters and setters for working with our private fields
     */
    public void setData(T data) {
        this.data = data;
    }

    public void setAddressNext(Element addressNext) {
        this.AddressNext = addressNext;
    }

    public void setAddressPrevious(Element addressPrevious) {
        this.AddressPrevious = addressPrevious;
    }
    public T getData() {
        return data;
    }
    public Element getAddressNext() {
        return AddressNext;
    }
    public Element getAddressPrevious() {
        return AddressPrevious;
    }
}

