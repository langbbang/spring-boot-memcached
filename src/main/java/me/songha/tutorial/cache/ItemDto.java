package me.songha.tutorial.cache;

import lombok.Builder;
import lombok.Data;

@Data
public class ItemDto {
    private Long itemId;

    @Builder
    public ItemDto(Item item) {
        this.itemId = item.getItemId();
    }

}
