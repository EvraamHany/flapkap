package flapkap.demo.service;

import flapkap.demo.exceptions.NotExist;
import flapkap.demo.exceptions.NotValid;
import flapkap.demo.model.Product;
import flapkap.demo.model.PurchaseDto;
import flapkap.demo.model.PurchaseResponseDto;
import flapkap.demo.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PurchaseService {
    private final UserService userService;
    private final ProductService productService;

    public PurchaseService(UserService userService, ProductService productServicce) {
        this.userService = userService;
        this.productService = productServicce;
    }

    @Transactional
    public PurchaseResponseDto processPurchase(List<PurchaseDto> purchasesDto) throws NotExist, NotValid {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userService.loadUserByUsername(authentication.getName());
        Integer totalPurchaseAmount = 0;
        List<Product> purchasedProducts = new ArrayList<>();
        for (PurchaseDto purchaseDto : purchasesDto) {
            Optional<Product> product = productService.getProductById(purchaseDto.getProductId());
            Integer productPurchaseAmount = product.isPresent() ? purchaseDto.getAmount() * product.get().getCost() : null;
            if (productPurchaseAmount == null) {
                throw new NotExist("one of the products not exist");
            }
            totalPurchaseAmount += productPurchaseAmount;
            purchasedProducts.add(product.get());
        }
        if (user.getDeposit() >= totalPurchaseAmount) {
            Integer userBalance = user.getDeposit() - totalPurchaseAmount;
            user.setDeposit(userBalance);
            userService.saveUser(user);
            PurchaseResponseDto purchaseResponseDto = new PurchaseResponseDto();
            purchaseResponseDto.setPurchasedProducts(purchasedProducts);
            purchaseResponseDto.setChange(calculateChange(userBalance));
            purchaseResponseDto.setTotalSpent(totalPurchaseAmount);
            return purchaseResponseDto;
        }
        throw new NotValid("not valid amount");
    }


    public Map<Integer, Integer> calculateChange(Integer amount) {
        int[] coinDenominations = {100, 50, 20, 10, 5};
        Map<Integer, Integer> change = new HashMap<>();

        for (int denomination : coinDenominations) {
            if (amount >= denomination) {
                int numberOfCoins = amount / denomination;
                change.put(denomination, numberOfCoins);
                amount -= numberOfCoins * denomination;
            }
        }

        return change;
    }
}
