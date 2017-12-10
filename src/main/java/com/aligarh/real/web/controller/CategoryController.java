package com.aligarh.real.web.controller;

import com.aligarh.real.RequestInterceptor;
import com.aligarh.real.model.Category;
import com.aligarh.real.service.CategoryService;
import com.aligarh.real.service.UserService;
import com.aligarh.real.web.Color;
import com.aligarh.real.web.FlashMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class CategoryController extends RequestInterceptor {
    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    protected final Log logger = LogFactory.getLog(getClass());

    // Index of all categories
    @SuppressWarnings("unchecked")
    @RequestMapping("/categories")
    public String listCategories(Model model) {
        // Get all categories
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "category/index";
    }

    // Single category page
    @RequestMapping("/categories/{categoryId}")
    public String category(@PathVariable Long categoryId, Model model, HttpServletRequest request) {
        // Get the category given by categoryId

        String mobileNum = getCookieValue(request, "mobileNumber");
        boolean guestMember = true;
        if(!StringUtils.isEmptyOrWhitespace(mobileNum) && null != userService.findById(Long.parseLong(mobileNum))){
            guestMember = false;
        }
        model.addAttribute("guestMember", guestMember);
        logger.info(" guestMember :" + guestMember);
        Category category = categoryService.findById(categoryId);
        model.addAttribute("category", category);
        return "category/details";
    }

    // Form for adding a new category
    @RequestMapping("categories/add")
    public String formNewCategory(Model model) {
        // Add model attributes needed for new form
        if (!model.containsAttribute("category")) {
            model.addAttribute("category", new Category());
        }
        model.addAttribute("colors", Color.values());
        model.addAttribute("action", "/categories");
        model.addAttribute("heading", "New Category");
        model.addAttribute("submit", "Add");

        return "category/form";
    }

    // Form for editing an existing category
    @RequestMapping("categories/{categoryId}/edit")
    public String formEditCategory(@PathVariable Long categoryId, Model model) {
        // Add model attributes needed for edit form

        if (!model.containsAttribute("category")) {
            model.addAttribute("category", categoryService.findById(categoryId));
        }
        model.addAttribute("colors", Color.values());
        model.addAttribute("action", String.format("/categories/%s", categoryId));
        model.addAttribute("heading", "Edit Category");
        model.addAttribute("submit", "Update");

        return "category/form";
    }

    // Update an existing category
    @RequestMapping(value = "/categories/{categoryId}", method = RequestMethod.POST)
    public String updateCategory(@Valid Category category, BindingResult result, RedirectAttributes redirectAttributes) {
        // Update category if valid data was received
        if (result.hasErrors()) {
            // Include validation errors upon redirect
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.category", result);

            // Add  category if invalid was received
            redirectAttributes.addFlashAttribute("category", category);

            // Redirect back to the form (In location header -> /categories/add)
            return String.format("redirect:/categories/%s/edit", category.getId());
        }

        categoryService.save(category);

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Category successfully updated!", FlashMessage.Status.SUCCESS));

        // Redirect browser to /categories
        return "redirect:/categories";
    }

    // Add a category
    @RequestMapping(value = "/categories", method = RequestMethod.POST)
    public String addCategory(@Valid Category category, BindingResult result, RedirectAttributes redirectAttributes) {
        // Add category if valid data was received
        if (result.hasErrors()) {
            // Include validation errors upon redirect
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.category", result);

            // Add  category if invalid was received
            redirectAttributes.addFlashAttribute("category", category);

            // Redirect back to the form (In location header -> /categories/add)
            return "redirect:/categories/add";
        }

        categoryService.save(category);

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Category successfully added!", FlashMessage.Status.SUCCESS));

        // Redirect browser to /categories
        return "redirect:/categories";
    }

    // Delete an existing category
    @RequestMapping(value = "/categories/{categoryId}/delete", method = RequestMethod.POST)
    public String deleteCategory(@PathVariable Long categoryId, RedirectAttributes redirectAttributes) {
        Category cat = categoryService.findById(categoryId);

        // Delete category if it contains no GIFs
        if (cat.getProducts().size() > 0) {
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Only empty categories can be deleted!", FlashMessage.Status.FAILURE));
            return String.format("redirect:/categories/%s/edit", categoryId);
        }
        categoryService.delete(cat);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Category deleted!", FlashMessage.Status.SUCCESS));

        // Redirect browser to /categories
        return "redirect:/categories";
    }
}
