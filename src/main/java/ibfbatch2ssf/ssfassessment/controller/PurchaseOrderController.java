package ibfbatch2ssf.ssfassessment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ibfbatch2ssf.ssfassessment.model.*;
import ibfbatch2ssf.ssfassessment.service.*;
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

    // post add item 
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

        model.addAttribute("item", item); 
        model.addAttribute("cart", cart);

        return "view1"; 
    }

    // next button pressed
    @GetMapping("/shippingaddress")
    public String getAddress(Model model, HttpSession session) {


        return "view2"; 
    }
    
}
