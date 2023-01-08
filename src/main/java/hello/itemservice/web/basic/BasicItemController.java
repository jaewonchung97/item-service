package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    /**
     * Set Basic Items
     */
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA", 199, 50));
        itemRepository.save(new Item("itemB", 299, 100));
    }


    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }


    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1(
            @RequestParam String itemName,
            @RequestParam Integer price,
            @RequestParam Integer quantity,
            Model model
    ){
        Item savedItem = itemRepository.save(new Item(itemName, price, quantity));
        model.addAttribute("item", savedItem);
        return "/basic/item";
    }

//    @PostMapping("/add")
    public String addItemV2(
            @ModelAttribute("item") Item item
    ){
        itemRepository.save(item);
        return "/basic/item";
    }

//    @PostMapping("/add")
    public String addItemV3(
            @ModelAttribute("item") Item item
    ){
        itemRepository.save(item);
        return "/basic/item";
    }

//    @PostMapping("/add")
    public String addItemV4(Item item){
        itemRepository.save(item);
        return "/basic/item";
    }

//    @PostMapping("/add")
    public String addItemV5(@ModelAttribute Item item){
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }


    @PostMapping("/add")
    public String addItemV6(@ModelAttribute Item item, RedirectAttributes redirectAttributes){
        itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", item.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/basic/items/{itemId}";
    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        model.addAttribute("item", itemRepository.findById(itemId));
        return "/basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item updateParam) {
        itemRepository.update(itemId, updateParam);
        return "redirect:/basic/items/{itemId}";
    }
}