package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class ItemRepositoryTest {

    ItemRepository repository = new ItemRepository();

    @AfterEach
    void afterEach(){
        repository.clearStore();
    }

    @Test
    void save() {
        //given
        Item item = new Item("itemA", 10000, 10);
        //when
        Item saveItem = repository.save(item);
        //then
        Item findItem = repository.findById(item.getId());
        assertThat(findItem).isEqualTo(saveItem);
    }

    @Test
    void findAll() {
        //given
        Item item1 = new Item("itemA", 10000, 10);
        Item item2 = new Item("itemB", 30000, 30);
        repository.save(item1);
        repository.save(item2);

        //when
        List<Item> result = repository.findAll();
        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(item1, item2);
    }

    @Test
    void updateItem() {
        //given
        Item item = new Item("itemA", 10000, 10);
        Item savedItem = repository.save(item);
        Long itemId = savedItem.getId();

        //when
        Item updateParam = new Item("item2",15000,24);
        repository.update(itemId, updateParam);
        //then
        Item findItem = repository.findById(itemId);
        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());

    }


}
