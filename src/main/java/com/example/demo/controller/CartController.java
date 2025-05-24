package com.example.demo.controller;

import com.example.demo.dto.OrderDTO;
import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.service.CartService;
import com.example.demo.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/cart")
    public String viewCart(Model model, HttpSession session) {
        Cart cart = cartService.getCart(session);
        List<CartItem> cartItems = cart.getCartItems();
        double totalPrice = calculateTotalPrice(cartItems);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        return "cart";
    }

    @PostMapping("/addToCart")
    public String addToCart(@RequestParam("productId") Long productId,
                            @RequestParam("quantity") int quantity,
                            HttpSession session){
        cartService.addToCart(session,productId, quantity);
        return "redirect:/cart";
    }

    @GetMapping("/removeFromCart")
    public String removeFromCart(@RequestParam("productId") Long productId,
                                 HttpSession session) {
        cartService.removeFromCart(session, productId);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        Cart cart = cartService.getCart(session);
        if (cart == null || cart.getCartItems().isEmpty()) {
            return "redirect:/cart";
        }

        List<CartItem> cartItems = cart.getCartItems();
        double totalPrice = calculateTotalPrice(cartItems);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("user", user);
        return "checkout";
    }

    @PostMapping("/placeOrder")
    public String placeOrder(@RequestParam("name") String name,
                            @RequestParam("phone") String phone,
                            @RequestParam("address") String address,
                            @RequestParam("paymentMethod") String paymentMethod,
                            @RequestParam(value = "note", required = false) String note,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        // Get user from session
        User user = (User) session.getAttribute("user");
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Please login to continue");
            return "redirect:/login";
        }

        // Get cart from session
        Cart cart = cartService.getCart(session);
        if (cart == null || cart.getCartItems().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Your cart is empty");
            return "redirect:/cart";
        }

        // Create order
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUserEmail(user.getEmail());
        orderDTO.setCustomerName(name);
        orderDTO.setCustomerPhone(phone);
        orderDTO.setTotalPrice(calculateTotalPrice(cart.getCartItems()));
        orderDTO.setShippingAddress(address);
        orderDTO.setPaymentMethod(paymentMethod);
        orderDTO.setNote(note);
        orderDTO.setOrderDate(new Date());
        orderDTO.setStatus("PENDING");

        try {
            // Save order
            Order order = orderService.save(orderDTO);
            
            // Clear cart but keep user session
            session.removeAttribute("cart");
            session.setAttribute("user", user); // Ensure user session is maintained
            
            redirectAttributes.addFlashAttribute("success", "Order placed successfully!");
            return "redirect:/dashboard";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to process order. Please try again.");
            return "redirect:/checkout";
        }
    }

    private double calculateTotalPrice(List<CartItem> cartItems) {
        return cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }
}
