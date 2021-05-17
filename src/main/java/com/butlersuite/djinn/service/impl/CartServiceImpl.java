package com.butlersuite.djinn.service.impl;

import com.butlersuite.djinn.dto.ProductDTO;
import com.butlersuite.djinn.exception.InsufficientStockException;
import com.butlersuite.djinn.model.Cart;
import com.butlersuite.djinn.model.Item;
import com.butlersuite.djinn.model.Order;
import com.butlersuite.djinn.repository.CartRepository;
import com.butlersuite.djinn.service.CustomerService;
import com.butlersuite.djinn.service.ItemService;
import com.butlersuite.djinn.utils.builder.StringBuilderPlus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.butlersuite.djinn.model.Status.PENDING;
import static com.butlersuite.djinn.model.Status.VALIDATED;

@Service
@Transactional
public class CartServiceImpl implements com.butlersuite.djinn.service.CartService {

   private CartRepository cartRepository;

   private CustomerService customerService;

   private ItemService itemService;

   @Autowired
   public CartServiceImpl(CartRepository cartRepository, CustomerService customerService, ItemService itemService) {
      this.cartRepository = cartRepository;
      this.customerService = customerService;
      this.itemService = itemService;
   }

   @Override
   public Cart getCart(Long customerId) {
      var existingCart = getExistingCart(customerId);
      if (existingCart != null) {
         return existingCart;
      } else {
         return cartRepository.save(new Cart(customerService.getCustomer(customerId), LocalDateTime.now().toString(), new ArrayList<>(), new BigDecimal(0), PENDING));
      }
   }

   @Override
   public Cart addToCart(Long customerId, ProductDTO productDTO, int quantity) {
      if (quantity < 0) {
         throw new IllegalArgumentException("Negative values are not accepted");
      } else if (quantity > productDTO.getStockQuantity()) {
         throw new InsufficientStockException("Not enough stock for this product.Maximum order quantity is: " + productDTO.getStockQuantity() + " units.");
      } else {
         var cart = getCart(customerId);
         var item = itemService.generateItem(productDTO, quantity);
         cart.addItem(item);
         cartRepository.save(cart);
         return cart;
      }
   }

   @Override
   public Cart removeFromCart(Long customerId, Item item) throws NoSuchElementException {
      var cart = getCart(customerId);
      Optional<Item> existingItem = cart.getItemsList().stream().filter(item1 -> item1.getItemId().equals(item.getItemId())).findAny();
      if (existingItem.isPresent()) {
         var toBeRemoved = existingItem.get();
         cart.removeItem(toBeRemoved);
         itemService.deleteItem(toBeRemoved);
         return cartRepository.save(cart);
      } else {
         throw new NoSuchElementException("The specified item is not in cart.");
      }
   }

   public String createOrder(Cart cart) {
      StringBuilderPlus sb = new StringBuilderPlus();
      sb.appendLine("ID comanda: " +  cart.getCartId().toString());
      sb.appendLine("Nume societate: " + cart.getCustomer().getCompanyName());
      sb.appendLine(cart.getCustomer().getCustomerDetails().getRegistrationNumber());
      cart.getItemsList().forEach(item -> {
         sb.appendLine(item.getProduct().getName() + ": " + item.getItemQuantity() + " x " +  item.getProduct().getUnitPrice() +  "  = " + item.getItemValue() + " RON");
      });
      sb.appendLine("Total de plata: " + cart.getTotalAmount().toString() + " RON");
      sb.appendLine(" ");
      sb.appendLine("Status comanda: " + cart.getStatus().toString());
      return sb.toString();
   }

   public void writeOrder(Cart cart) {

      String order = createOrder(cart);
      BufferedWriter bufferedWriter = null;

      try {
         File file = new File("D:/x.txt");
         if(!file.exists()) {
            file.createNewFile();
         }
         FileWriter fw = new FileWriter(file);
         bufferedWriter = new BufferedWriter(fw);
         bufferedWriter.write(order);

      } catch (IOException exception) {
         exception.printStackTrace();
      } finally {
         try {
            if (bufferedWriter != null)
               bufferedWriter.close();

         } catch (IOException exception) {
            exception.printStackTrace();
         }
      }
   }

   @Override
   public void validateCart(Long customerId) {
      var cart = getExistingCart(customerId);
      cart.setStatus(VALIDATED);
      writeOrder(cart);
      cartRepository.save(cart);
   }

   @Override
   public void destroyCart(Long customerId) {
      Optional<Cart> cart = cartRepository.findByCustomerAndStatus(customerService.getCustomer(customerId), PENDING);
      if (cart.isPresent()) {
         cartRepository.delete(cart.get());
      } else {
         throw new NoSuchElementException("User has no active cart.");
      }
   }

   public Cart getExistingCart(Long customerId) {
      var customer = customerService.getCustomer(customerId);
      Optional<Cart> cart = cartRepository.findByCustomerAndStatus(customer, PENDING);
      if (cart.isPresent()) {
         return cart.get();
      } else {
         return null;
      }
   }
}

