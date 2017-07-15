package org.kj6682.peakycake;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FrontendController {

    @RequestMapping("/peakycake")
    public String peakycake(@RequestParam(value="name", required=false, defaultValue="World!") String name, Model model) {
        model.addAttribute("name", name);
        return "peakycake";
    }

    @RequestMapping("/chef")
    public String chef(@RequestParam(value="name", required=false, defaultValue="Chef") String name, Model model) {
        model.addAttribute("name", name);
        return "chef";
    }

    @RequestMapping("/shop/{shopname}")
    public String shop(@PathVariable String shopname, @RequestParam(value="name", required=false, defaultValue="Shop") String name, Model model) {
        model.addAttribute("name", shopname);
        return "shop";
    }
}