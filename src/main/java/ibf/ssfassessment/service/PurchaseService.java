package ibf.ssfassessment.service;

import ibf.ssfassessment.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {

    @Autowired
    private QuotationService qSvc; 

    public Cart aggregate(Cart cart, Item item) {

        List<Item> contents = cart.getContents(); 

        for (int i = 0; i < contents.size(); i++) {

            String name = contents.get(i).getItemName();
            System.out.println(name); 
            if (name.equals(item.getItemName())) {
                Integer qty = contents.get(i).getQuantity(); 
                qty += item.getQuantity(); 
                contents.get(i).setQuantity(qty);
                return cart; 
            }
        }
        cart.addItemToCart(item);
        return cart; 
    }

    public Invoice createInvoice(Quotation quote, Delivery delivery, Cart cart) {

        Invoice invoice = new Invoice(); 
        invoice.setInvoiceId(quote.getQuoteId()); 
        invoice.setDelivery(delivery);

        Float cost = qSvc.calculateCost(quote, cart); 
        invoice.setTotal(cost);

        return invoice; 
    }
    
}
