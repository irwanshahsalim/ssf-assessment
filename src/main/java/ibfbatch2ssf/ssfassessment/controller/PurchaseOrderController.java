package ibfbatch2ssf.ssfassessment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ibfbatch2ssf.ssfassessment.model.Cart;
import ibfbatch2ssf.ssfassessment.model.Delivery;
import ibfbatch2ssf.ssfassessment.model.Item;
import ibfbatch2ssf.ssfassessment.service.PurchaseService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PurchaseOrderController {

    // autowire service
    @Autowired
    private PurchaseService purchaseSvc;

    // landing page = view1
    @GetMapping(path={"/", "view1.html"})
    public String getLanding(Model model, HttpSession session) {

        // check if cart has item using session
        Cart cart = (Cart) session.getAttribute("cart"); 
        if (null == cart) {
            cart = new Cart(); 
            session.setAttribute("cart", cart);
        }
        model.addAttribute("item", new Item()); 
        model.addAttribute("cart", cart);   

        return "view1"; 
    }

    // post add button 
    @PostMapping("/")
    public String postAdd(@Valid Item item, BindingResult result, Model model, HttpSession session) {

        Cart cart = (Cart) session.getAttribute("cart"); 

        // perform error check
        if (result.hasErrors()) {
            // reset page 
            model.addAttribute("item", item);
            model.addAttribute("cart", cart); 
            return "view1"; 
        }
        // perform aggregation 
        cart = purchaseSvc.aggregate(cart, item); 
        session.setAttribute("cart", cart);
        session.setAttribute("item", item);

        model.addAttribute("item", item); 
        model.addAttribute("cart", cart);        



        return "view1"; 
    }

    // next button pressed
    @GetMapping("/shippingaddress")
    public String getAddress(Model model, HttpSession session) {

        Cart cart = (Cart) session.getAttribute("cart"); 
        System.out.println(cart); 

        // cannot navigate to view 2 without a valid cart
        if (cart.getContents().isEmpty()) {
            model.addAttribute("item", new Item());
            model.addAttribute("cart", cart);            
            return "view1"; 
        }

        // task 2 
        model.addAttribute("delivery", new Delivery());

        Delivery delivery = (Delivery) session.getAttribute("delivery"); 
        if (null == delivery) {
            delivery = new Delivery(); 
            session.setAttribute("delivery", delivery);
        }

        model.addAttribute("delivery", delivery);      

        return "view2"; 
    }

    @PostMapping("/quotation")
    public String postAddress(@Valid Delivery delivery, BindingResult result, HttpSession session, Model model) { 

        // check for delivery details errors 
        if (result.hasErrors()) {
            model.addAttribute("delivery", delivery); 
            return "view2"; 
        }

        // if no error, continue 


        // make HTTP call to get quotation (svc)

        return "view3"; 
    }
    
}
