package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;


@RequiredArgsConstructor // 이걸 붙이면 @Autowired붙이 생성자를 final이 있는 변수를 찾아 생성해줌
@Controller
@RequestMapping("/basic/items")
public class BasicItemController {

    private final ItemRepository itemRepository;

//    @Autowired
//    public  BasicItemController(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }

    @GetMapping
    public String items(Model model) {
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
    public String addForm() {
        return "basic/addForm";
    }

//    @PostMapping("/add")
//    public String save(@ModelAttribute("item") Item item) {
//        itemRepository.save(item);
//        //model.addAttribute("item", item); @modelAttribute 에 이름을 붙이면 자동으로 저장된다.. 대박
//        //return "redirect:";
//
//         return "basic/item";
//    }

    /**
     * 사실상 다 생략해도 클래스명 앞글자만 소문자로 바꿔서 모델에 자동 등록된다.
     * @param item
     * @return
     */
    @PostMapping("/add")
        public String save(Item item) {
            itemRepository.save(item);
            return "basic/item";
        }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {

//        Item findItem = itemRepository.findById(itemId);
//        findItem.setItemName(item.getItemName());
//        findItem.setPrice(item.getPrice());
//        findItem.setQuantity(item.getQuantity());
        itemRepository.update(itemId,item);
        return "redirect:/basic/items/{itemId}";

    }



    // 테스트용 데이터 추가
    @PostConstruct
    public void init() {

        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 145000, 17));
        itemRepository.save(new Item("itemC", 34000, 12));

    }

}
