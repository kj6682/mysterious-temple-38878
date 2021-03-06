package org.kj6682.mysterioustemple;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class FrontendController {


    @RequestMapping(value = {"/", "/main"})
    public String defaultModel(@RequestParam(value = "name", required = false, defaultValue = "World!") String name, Model model) {
        model.addAttribute("name", name);
        return "main";
    }

    @RequestMapping("/chef")
    public String chef(@RequestParam(value = "name", required = false, defaultValue = "Chef") String name, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("tomorrow", LocalDate.now().plusDays(1));
        return "chef";
    }

    @RequestMapping("/shop/{shopname}")
    public String shop(@PathVariable String shopname, Model model) {
        model.addAttribute("name", shopname);
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("tomorrow", LocalDate.now().plusDays(1));
        return "shop";
    }
}