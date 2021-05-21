package com.butlersuite.djinn.service.impl;

import com.butlersuite.djinn.dto.ProductDTO;
import com.butlersuite.djinn.exception.InsufficientStockException;
import com.butlersuite.djinn.model.Cart;
import com.butlersuite.djinn.model.Item;
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
import java.time.format.DateTimeFormatter;
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

   /**
    * Adds an Item to Cart, with the quantity to be purchased.
    * If  no pending cart is available for the Customer, a new one is created.
    *
    * @param customerId identifies the Cart.
    * @param productDTO is transformed to Item with the purchase quantity.
    * @param quantity   indicated the quantity to be purchased.
    * @return updated Cart.
    */
   @Override
   public Cart addToCart(Long customerId, ProductDTO productDTO, int quantity)
         throws IllegalArgumentException, InsufficientStockException {
      if (quantity <= 0) {
         throw new IllegalArgumentException("Only positive values are accepted");
      } else if (quantity > productDTO.getStockQuantity()) {
         throw new InsufficientStockException("Not enough stock for this request. Maximum order quantity is: " +
               productDTO.getStockQuantity() + " units.");
      } else {
         var cart = getCart(customerId);
         var item = itemService.generateItem(productDTO, quantity);
         cart.addItem(item);
         cartRepository.save(cart);
         return cart;
      }
   }

   @Override
   public Cart getCart(Long customerId) {
      var existingCart = getExistingCart(customerId);
      if (existingCart != null) {
         return existingCart;
      } else {
         return cartRepository.save(new Cart(customerService.getCustomer(customerId),
               formatDate(LocalDateTime.now()), new ArrayList<>(), new BigDecimal(0), PENDING));
      }
   }

   /**
    * removes an item line from the Cart, adjusting the total amount.
    *
    * @param customerId identifies the pending cart.
    * @param item       defines the item to be removed.
    * @return updated Cart.
    * @throws NoSuchElementException if ispecified tem is not found.
    */
   @Override
   public Cart removeFromCart(Long customerId, Item item) throws NoSuchElementException {
      var cart = getCart(customerId);
      Optional<Item> existingItem = cart.getItemsList().stream().filter(item1 ->
            item1.getItemId().equals(item.getItemId())).findAny();
      if (existingItem.isPresent()) {
         var toBeRemoved = existingItem.get();
         cart.removeItem(toBeRemoved);
         itemService.deleteItem(toBeRemoved);
         return cartRepository.save(cart);
      } else {
         throw new NoSuchElementException("The specified item is not in cart.");
      }
   }

   /**
    * validates the Cart contests, issues Oder and adjusts the stock.
    *
    * @param customerId identifies the pending Cart.
    */
   @Override
   public void validateCart(Long customerId) throws NoSuchElementException {
      var cart = getExistingCart(customerId);
      if (cart != null) {
         cart.setStatus(VALIDATED);
         cart.getItemsList().forEach(item -> itemService.decreaseProductStock(item));
         writeOrder(cart);
         cartRepository.save(cart);
      } else {
         throw new NoSuchElementException("No pending cart active for the specified User.");
      }
   }

   /**
    * Deletes the specified pending Cart from database.
    *
    * @param customerId identifies the pending Cart
    */
   @Override
   public void destroyCart(Long customerId) throws NoSuchElementException {
      Optional<Cart> cart = cartRepository.findByCustomerAndStatus(
            customerService.getCustomer(customerId), PENDING);
      if (cart.isPresent()) {
         cartRepository.delete(cart.get());
      } else {
         throw new NoSuchElementException("User has no active cart.");
      }
   }

   //---------------- HELPER METHODS --------------- //

   private Cart getExistingCart(Long customerId) {
      var customer = customerService.getCustomer(customerId);
      Optional<Cart> cart = cartRepository.findByCustomerAndStatus(customer, PENDING);
      return cart.orElse(null);
   }

   private String formatDate(LocalDateTime localDateTime) {
      var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh-mm-ss");
      return localDateTime.format(formatter);
   }

   private String createOrder(Cart cart) {
      var sb = new StringBuilderPlus();
      sb.appendLine("ID comanda: " + cart.getCartId().toString());
      sb.appendLine("Nume societate: " + cart.getCustomer().getCompanyName());
      sb.appendLine(cart.getCustomer().getCustomerDetails().getRegistrationNumber());
      cart.getItemsList().forEach(item ->
            sb.appendLine(item.getOrderDetail().getProductInfo().getName() + ": " +
                  item.getOrderDetail().getQuantity() + " x " + item.getOrderDetail().getProductInfo().getUnitPrice() +
                  "  = " + item.getItemValue() + " RON"));
      sb.appendLine("Total de plata: " + cart.getTotalAmount().toString() + " RON");
      sb.appendLine(" ");
      sb.appendLine("Status comanda: " + cart.getStatus().toString());
      return sb.toString();
   }

   private void writeOrder(Cart cart) {
      String order = createOrder(cart);
      String filename = cart.getCustomer().getCompanyName() + " " + cart.getCreationDate() + ".txt";

      var file = new File("D:/" + filename);
      try (var bufferedWriter = new BufferedWriter(new FileWriter(file))) {
         bufferedWriter.write(order);
      } catch (IOException exception) {
         exception.printStackTrace();
      }
   }
}

