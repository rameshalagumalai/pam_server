package com.flag.pam.resources.categories;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * @author rameshalagumalai
 * @Date 28/01/2025
 * */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    private static final Map<Byte, String> CATEGORY_TYPE_MAP = Map.ofEntries(
            Map.entry((byte) 1, "expense"),
            Map.entry((byte) 2, "income"),
            Map.entry((byte) 3,"both")
    );

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false)
    @JsonIgnore
    private byte type;

    @Column(nullable = false, length = 10)
    private String color;

    @Column(nullable = false, length = 30)
    private String icon;

    @JsonProperty("type")
    public String getTypeValue() {
        return CATEGORY_TYPE_MAP.get(this.type);
    }

}
