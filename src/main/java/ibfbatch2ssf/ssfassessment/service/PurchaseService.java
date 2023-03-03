package ibfbatch2ssf.ssfassessment.service;

import ibfbatch2ssf.ssfassessment.model.*;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {

    // autowire repo 

    // method to perform aggregation at cart level 
    public Cart aggregate(Cart cart, Item item) {

        List<Item> contents = cart.getContents(); 
        System.out.println(contents); 

        // loop through the list to see if there are similar item name already existing in the cart 
        for (int i = 0; i < contents.size(); i++) {

            String name = contents.get(i).getItemName(); // name is the item Name for existing cart items 
            System.out.println(name); 
            if (name.equals(item.getItemName())) {
                // add on to the existing list
                Integer qty = contents.get(i).getQuantity(); 
                qty += item.getQuantity(); 
                contents.get(i).setQuantity(qty);
                return cart; 
            }
        }
        // if reach this step it means itemName != all names in the cart
        cart.addItemToCart(item);
        return cart; 
    }
    
}
