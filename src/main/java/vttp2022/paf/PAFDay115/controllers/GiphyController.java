package vttp2022.paf.PAFDay115.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp2022.paf.PAFDay115.service.GiphyService;

@Controller
@RequestMapping(path="/")
public class GiphyController {

    @Autowired
    private GiphyService gSvc;

    @GetMapping(path="/result")
    public String getResult(@RequestParam String Phrase, @RequestParam Integer Limit, @RequestParam String Rating, Model model) {

        List<String> gifList = gSvc.getGifs(Phrase, Rating, Limit);

        model.addAttribute("Phrase", Phrase);
        model.addAttribute("Limit", Limit);
        model.addAttribute("Rating", Rating);
        model.addAttribute("gifList", gifList);

        return "Result";
    }
    
}
