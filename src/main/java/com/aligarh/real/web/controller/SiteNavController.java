package com.aligarh.real.web.controller;

import com.aligarh.real.RequestInterceptor;
import com.aligarh.real.data.CategoryRepository;
import com.aligarh.real.data.GifRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SiteNavController extends RequestInterceptor {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private GifRepository gifRepository;

   /* @RequestMapping("/")
    public String home(){
        return "index";
    }*/

    /*@RequestMapping("/categories")
    public String listCategories(ModelMap modelMap) {
        List<EventCategory> categories = categoryRepository.getAllCategories();
        modelMap.put("categories", categories);
        return "categories";
    }*/

    /*@RequestMapping("/category/{id}")
    public String category(@PathVariable int id, ModelMap modelMap) {
        EventCategory category = categoryRepository.findById(id);
        modelMap.put("category",category);

        List<Gif> gifs = gifRepository.findByCategoryId(id);
        modelMap.put("gifs",gifs);

        return "category";
    }*/
}