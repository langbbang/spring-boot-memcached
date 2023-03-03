package me.songha.tutorial.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheService {
    private final ItemRepository itemRepository;

    @Cacheable(value = "findById", key = "#itemId")
    public ItemDto findById(Long itemId) {
        return ItemDto.builder().item(itemRepository.findById(itemId).get()).build();
    }

    @CacheEvict(value = "evictItem", key = "#itemId")
    public void evictItem(Long itemId) {
        log.info("evict item. itemId={}", itemId);
    }
}
