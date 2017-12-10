package com.aligarh.real.web.controller;

import com.aligarh.real.RequestInterceptor;
import com.aligarh.real.model.Product;
import com.aligarh.real.model.User;
import com.aligarh.real.service.CategoryService;
import com.aligarh.real.service.LikeService;
import com.aligarh.real.service.ProductService;
import com.aligarh.real.service.UserService;
import com.aligarh.real.web.FlashMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController extends RequestInterceptor {
    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    // Initializing Logger
    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("ProductInterceptor: REQUEST Intercepted for URI: "
                + request.getRequestURI());
        String username = request.getAttribute("username").toString();
        request.setAttribute("username", "sample");
        return true;
    }

    // Home page - index of all GIFs
    @RequestMapping("/")
    public String listProducts(Model model, HttpSession httpSession, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        String mobileNum = getCookieValue(request, "mobileNumber");
        boolean guestMember = true;
        if (!StringUtils.isEmptyOrWhitespace(mobileNum) && null != userService.findById(Long.parseLong(mobileNum))) {
            guestMember = false;
        }
        model.addAttribute("guestMember", guestMember);
        logger.info(" guestMember :" + guestMember);
        // Get all products
        List<Product> products = new ArrayList<>();
        products = productService.findAll();
        boolean aclass = false;
        model.addAttribute("products", products);
        if (null != redirectAttributes.getFlashAttributes().get("aclass")) {
            logger.info("Map of redirectAttributes: " + redirectAttributes.getFlashAttributes());
            logger.info("redirectAttribute->aclass: " + redirectAttributes.getFlashAttributes().get("aclass"));
            aclass = Boolean.valueOf(redirectAttributes.getFlashAttributes().get("aclass").toString());
        }
        return "product/index";
    }

    // Single PRODUCT page
    @RequestMapping("/products/{productId}")
    public String productDetails(@PathVariable Long productId, Model model, HttpSession httpSession, HttpServletRequest request) {
        // Get product whose id is gifId

        String mobileNum = getCookieValue(request, "mobileNumber");
        boolean guestMember = true;
        if (!StringUtils.isEmptyOrWhitespace(mobileNum) && null != userService.findById(Long.parseLong(mobileNum))) {
            guestMember = false;
        }
        model.addAttribute("guestMember", guestMember);
        logger.info(" guestMember :" + guestMember);
        Product product = productService.findById(productId);

        model.addAttribute("product", product);
        return "product/details";
    }

    // PRODUCT product data
    @RequestMapping("/products/{productId}.product")
    @ResponseBody //no thymeleaf comin in picture in case of this URI
    public byte[] productImage(@PathVariable Long productId, HttpSession httpSession) {
        // Return image data as byte array of the PRODUCT whose id is gifId
        return productService.findById(productId).getBytes();
    }


    // Favorites - index of all GIFs marked favorite
    @RequestMapping("/favorites")
    public String favorites(Model model, HttpSession httpSession, HttpServletRequest request) {
        // Get list of all Products marked as favorite for the user
        String number = getCookieValue(request, "mobileNumber");
        logger.info(" User number :" + number);
        if (null != number && !StringUtils.isEmptyOrWhitespace(number)) {
            User member = userService.findById(Long.parseLong(number));
            logger.info(" User is a Member :" + member);

            List<Product> list = !member.getFavoriteProducts().isEmpty() ? member.getFavoriteProducts() : new ArrayList<Product>(0);
            logger.info(" Fav Product List1 :" + list);

            logger.info(" Fav Product List2 :" + member.getFavoriteProducts());
            model.addAttribute("products", list);
            model.addAttribute("user", member); // Static username
            return "product/favorites";
        } else {
            logger.info(" User is not a Member :");
            return "redirect:/signIn";
        }
    }

    // Upload a new PRODUCT
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public String addProduct(Product product, @RequestParam MultipartFile file, RedirectAttributes redirectAttributes,
                             HttpServletResponse response, HttpServletRequest request) {
        // Upload new PRODUCT if data is valid
        String mobileNum = getCookieValue(request, "mobileNumber");
        boolean guestMember = true;
        if (null != mobileNum) {
            User member = userService.findById(Long.parseLong(mobileNum));
            if (!StringUtils.isEmptyOrWhitespace(mobileNum) && null != member) {
                guestMember = false;
            }
        }
        productService.save(product, file);
        logger.info(" Product details :" + product);

        Cookie cookie = new Cookie("username", product.getUsername());
        cookie.setMaxAge(1000); //set expire time to 1000 sec
        response.addCookie(new Cookie("username", product.getUsername())); //put cookie in response

        // Add flash message for success
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("PRODUCT successfully uploaded", FlashMessage.Status.SUCCESS));

        // Redirect browser to new PRODUCT's detail view
        return String.format("redirect:/products/%s", product.getId());
    }

    // Form for uploading a new PRODUCT
    @RequestMapping("/upload")
    public String formNewProduct(Model model, HttpSession httpSession) {
        // Add model attributes needed for new PRODUCT upload form
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("action", "/products");
        model.addAttribute("heading", "Upload");
        model.addAttribute("submit", "Add");
        return "product/form";
    }

    // Form for editing an existing PRODUCT
    @RequestMapping(value = "/products/{productId}/edit")
    public String formEditProduct(@PathVariable Long productId, Model model, HttpSession httpSession) {
        // Add model attributes needed for edit form
        if (!model.containsAttribute("product")) {
            model.addAttribute("product", productService.findById(productId));
        }
        model.addAttribute("action", String.format("/products/%s", productId));
        model.addAttribute("heading", "Edit");
        model.addAttribute("submit", "Update");
        model.addAttribute("categories", categoryService.findAll());
        return "product/form";
    }

    // Update an existing PRODUCT
    @RequestMapping(value = "/products/{productId}", method = RequestMethod.POST)
    public String updateProduct(@Valid Product product, @RequestParam MultipartFile file,
                                BindingResult result, RedirectAttributes redirectAttributes, HttpSession httpSession) {
        // TODO: Update PRODUCT if data is valid
        if (result.hasErrors()) {
            // Include validation errors upon redirect
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.product", result);

            // Add  category if invalid was received
            redirectAttributes.addFlashAttribute("product", product);

            // Redirect back to the form (In location header -> /categories/add)
            return String.format("redirect:/products/%s/edit", product.getId());
        }

        productService.save(product, file);

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Product successfully updated!", FlashMessage.Status.SUCCESS));

        // TODO: Redirect browser to updated PRODUCT's detail view
        return "redirect:/";
    }

    // Delete an existing PRODUCT
    @RequestMapping(value = "/products/{productId}/delete", method = RequestMethod.POST)
    public String deleteProduct(@PathVariable Long productId, RedirectAttributes redirectAttributes, HttpSession httpSession) {
        // TODO: Delete the PRODUCT whose id is gifId
        Product product = productService.findById(productId);

        productService.delete(product);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Product deleted!", FlashMessage.Status.SUCCESS));

        // Redirect to app root
        return "redirect:/";
    }

    // Mark/unmark an existing PRODUCT as a favorite
    @RequestMapping(value = "/products/{productId}/favorite", method = RequestMethod.POST)
    public String toggleFavorite(@PathVariable Long productId, HttpServletRequest request, RedirectAttributes redirectAttributes,
                                 HttpSession httpSession) {
        Product product = productService.findById(productId);
        // TODO: With PRODUCT whose id is gifId, toggle the favorite field
        String message = product.isFavorite() ? "Removed from favorites" : "Marked as favorite";

        String number = getCookieValue(request, "mobileNumber");
        if (null != number && !StringUtils.isEmptyOrWhitespace(number)) {
            User member = userService.findById(Long.parseLong(number));
            List<Product> favoriteProducts = member.getFavoriteProducts();
            if (!favoriteProducts.contains(product)) {
                favoriteProducts.add(product);
            } else {
                favoriteProducts.remove(product);
            }
            productService.toggleFavorite(product);
            member.setFavoriteProducts(favoriteProducts);
            userService.addUser(member);
        }

        logger.info(" User number :" + number);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage(message, FlashMessage.Status.SUCCESS));

        // TODO: Redirect to PRODUCT's detail view
        //Sending user to referrer, although this is introducing vulnerability

        if (request.getHeader("referrer") != null) {
            return String.format("redirect:%s", request.getHeader("referrer"));
        }
        return "redirect:/";
    }

    // Search results
    @RequestMapping("/search")
    public String searchResults(@RequestParam String q, Model model, HttpSession httpSession) {
        // Get list of PRODUCTs whose description contains value specified by q
        List<Product> products = new ArrayList<>();
        productService.findAll().forEach(product -> {
            if ((product.getDescription().toLowerCase()).indexOf(q.toLowerCase()) != -1) {
                products.add(product);
            }
        });

        model.addAttribute("products", products);
        return "product/index";
    }
}