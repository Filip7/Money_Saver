package hr.java.web.milkovic.moneyapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @GetMapping("/")
    public String showStartPage(){
        return "index";
    }

}
