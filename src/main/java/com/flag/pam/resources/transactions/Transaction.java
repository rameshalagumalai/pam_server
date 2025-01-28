package com.flag.pam.resources.transactions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.flag.pam.resources.accounts.Account;
import com.flag.pam.resources.categories.Category;
import com.flag.pam.resources.users.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "transactions")
public class Transaction {

    private static final Map<Byte, String> TRANSACTION_TYPE_MAP = Map.ofEntries(
            Map.entry((byte) 1, "expense"),
            Map.entry((byte) 2, "income"),
            Map.entry((byte) 3,"self transfer")
    );

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false)
    @JsonIgnore
    private byte type;

    @Column(nullable = false)
    private float amount;

    @Column(nullable = false)
    private long createdAt;

    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "account_id")
    private long accountId;

    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "category_id")
    private long categoryId;

    @JsonProperty("type")
    public String getTypeValue() {
        return TRANSACTION_TYPE_MAP.get(this.type);
    }

}
