package ibf.ssfassessment.model;

import java.util.LinkedList;
import java.util.List;

public class Cart {

    private List<Item> contents = new LinkedList<>();

    // getter setter
    public List<Item> getContents() {return contents;}
    public void setContents(List<Item> contents) {this.contents = contents;} 

    // method to add item to cart 
    public void addItemToCart(Item item) {
        contents.add(item);
    }
    @Override
    public String toString() {
        return "Cart [contents=" + contents + "]";
    }
    
    
    
}
