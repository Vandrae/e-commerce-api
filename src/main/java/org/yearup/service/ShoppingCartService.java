package org.yearup.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.yearup.models.CartItem;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.repository.ShoppingCartRepository;

import java.util.List;

@Service
public class ShoppingCartService
{
    // a shopping cart is built from cart rows plus a product lookup for each row
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductService productService)
    {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
    }

    public ShoppingCart getByUserId(int userId)
    {
        // load the user's cart rows, look up each product, and build the ShoppingCart
        List<CartItem> cartItems = shoppingCartRepository.findByUserId(userId);
        ShoppingCart cart = new ShoppingCart();
        for(CartItem cartItem:cartItems){
            Product product = productService.getById(cartItem.getProductId());
            ShoppingCartItem item = new ShoppingCartItem();
            item.setProduct(product);
            item.setQuantity(cartItem.getQuantity());
            cart.add(item);
        }
        return cart;
    }

    // add additional methods here
    public ShoppingCart addToCart(int userId, int productId){
    CartItem items = shoppingCartRepository.findByUserIdAndProductId(userId, productId);
    if (items == null){

        CartItem cartItem = new CartItem();
        cartItem.setProductId(productId);
        cartItem.setUserId(userId);
        cartItem.setQuantity(1);
        shoppingCartRepository.save(cartItem);
    } else if (items != null) {
        items.setQuantity(items.getQuantity() + 1);
        shoppingCartRepository.save(items);
    }
        return getByUserId(userId);
    }

    public ShoppingCart update(int userId, int productId, int quantity){
        CartItem item = shoppingCartRepository.findByUserIdAndProductId(userId,productId);
        item.setQuantity(quantity);
        shoppingCartRepository.save(item);
        return getByUserId(userId);
    }

    @Transactional
    public ShoppingCart delete(int userId){
        shoppingCartRepository.deleteByUserId(userId);
        return getByUserId(userId);
    }
}
