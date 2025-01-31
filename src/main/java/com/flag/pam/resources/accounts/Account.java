package com.flag.pam.resources.accounts;

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
 * @Date 27/01/2025
 * */

@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {

    private static final Map<Byte, String> ACCOUNT_TYPE_MAP = Map.ofEntries(
            Map.entry((byte) 1, "bank"),
            Map.entry((byte) 2, "cash"),
            Map.entry((byte) 3,"credits")
    );

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private long id;

    @Column(nullable = false, length = 30)
    @Getter
    private String name;

    @Column(nullable = false)
    private byte type;

    @Column(nullable = false)
    @Getter
    private float balance;

    @JsonProperty("type")
    public String getTypeValue() {
        return ACCOUNT_TYPE_MAP.get(this.type);
    }

}
