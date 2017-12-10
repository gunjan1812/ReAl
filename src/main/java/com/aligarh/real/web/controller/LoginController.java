package com.aligarh.real.web.controller;

import com.aligarh.real.RequestInterceptor;
import com.aligarh.real.model.Like;
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
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class LoginController extends RequestInterceptor {
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

    @RequestMapping("/signUp")
    public String signUpPage(Model model, RedirectAttributes redirectAttributes) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        model.addAttribute("action", "/signUp");
        model.addAttribute("heading", "SignUp");
        return "signUp";
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public String signUp(@Valid User user, BindingResult result,
                         RedirectAttributes redirectAttributes, HttpServletResponse response, HttpServletRequest req) {
        if (result.hasErrors()) {
            // Include validation errors upon redirect
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.image", result);

            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Invalid input!", FlashMessage.Status.FAILURE));
            logger.info(" user details invalid ? :" + user);
            return "redirect:/signUp";
        }
        userService.addUser(user);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage(user.getUsername() + " successfully added!", FlashMessage.Status.SUCCESS));
        String referrer = req.getHeader("Referer");
        logger.info("referrer " + referrer);
        if (null != referrer && !StringUtils.hasText("/signUp")) {
            return String.format("redirect:%s", referrer);
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/signIn")
    public String signIn(Model model, HttpServletRequest req, RedirectAttributes redirectAttributes) {
        String num = getCookieValue(req, "mobileNumber");
        Long mobileNumber;
        if (null != num && !StringUtils.isEmpty(num)) {
            mobileNumber = Long.parseLong(num);
            User member = userService.findById(mobileNumber);
            if (null != member) {
                String referrer = req.getHeader("Referer");
                logger.info("referrer " + referrer);
                if (null != referrer) {
                    return String.format("redirect:%s", req.getHeader("Referer"));
                } else {
                    return "redirect:/";
                }
            }
        }
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        logger.info(" user exists in cookie ? :" + model.containsAttribute("user"));
        model.addAttribute("action", "/signInValidate");
        model.addAttribute("heading", "SignIn");
        return "signIn";
    }

    @RequestMapping(value = "/signInValidate", method = RequestMethod.POST)
    public String signInValidate(@Valid User user, BindingResult result,
                                 RedirectAttributes redirectAttributes, HttpServletResponse response) {
        if (result.hasErrors()) {
            // Include validation errors upon redirect
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.image", result);

            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Invalid Credentials!", FlashMessage.Status.FAILURE));
            logger.info(" user credentials invalid ? :" + user);
            return "redirect:/signIn";
        }
        User member = userService.findById(user.getMobileNumber());

        if (null != member) {
            logger.info(" user credentials valid :" + member);

            //Cookie cookie = new Cookie("username", user.getUsername());
            //cookie.setMaxAge(1000); //set expire time to 1000 sec
            response.addCookie(new Cookie("username", member.getUsername())); //put cookie in response
            response.addCookie(new Cookie("mobileNumber", member.getMobileNumber().toString())); //put cookie in response

            redirectAttributes.addFlashAttribute("flash", new FlashMessage(member.getUsername() + " successfully loggedIn",
                    FlashMessage.Status.SUCCESS));

            redirectAttributes.addFlashAttribute("user", member);
            return "redirect:/";
        }
        logger.info(" user details Not found in DB:" + user);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage(user.getMobileNumber() + " Invalid Credentials!",
                FlashMessage.Status.FAILURE));
        return "redirect:/signIn";
    }

    // IMAGE image data
    @RequestMapping(value = "/like", method = RequestMethod.GET)
    public String toggleLike(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        // Return image data as byte array of the IMAGE whose id is gifId
        int count;
        String username = getCookieValue(request, "username");
        logger.info("cookie username::value " + username);
        String referrer = request.getHeader("Referer");
        logger.info("referrer " + referrer);

        boolean addClass = true;

        if (null != username) {
            Like liker = likeService.findAllLikers().stream().filter(isLiker ->
                    username.equalsIgnoreCase(isLiker.getUsername())).findAny().orElse(null);

            if (null != liker) {
                logger.info(liker.getUsername() + " user exists in db");
                likeService.removeLiker(liker);
            } else {
                logger.info(username + " user doesn't exists in db");
                Like li = new Like(username);
                logger.info(li + " Like");
                likeService.addLiker(li);
                addClass = false;
            }
        } else {
            logger.info("redirecting to SignUp page");
            return "redirect:/signUp";
        }

        count = likeService.getCount();
        request.setAttribute("hitcount", count);
        redirectAttributes.addFlashAttribute("aclass", addClass);
        return String.format("redirect:%s", referrer);
    }
}