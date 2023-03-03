package me.songha.tutorial.cache;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Table(name = "ITEM")
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    private Long itemId;
}
