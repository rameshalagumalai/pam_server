package com.flag.pam.resources.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author rameshalagumalai
 * @Date 30/01/2025
 * */

@Getter
@Setter
@Builder
public class TransactionData {

    private long id;

    private String name;

    private byte type;

    private float amount;

    @JsonProperty("created_at")
    private long createdAt;

    @JsonProperty("account_id")
    private long accountId;

    @JsonProperty("account_name")
    private String accountName;

    @JsonProperty("category_id")
    private long categoryId;

    @JsonProperty("category_name")
    private String categoryName;

    @JsonProperty("category_color")
    private String categoryColor;

    @JsonProperty("category_icon")
    private String categoryIcon;

}
