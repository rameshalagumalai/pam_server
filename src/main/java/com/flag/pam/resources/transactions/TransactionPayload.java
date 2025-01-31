package com.flag.pam.resources.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * @author rameshalagumalai
 * @Date 30/01/2025
 * */

@Getter
@Setter
public class TransactionPayload {

    private static final Map<String, Byte> TRANSACTION_TYPE_MAP = Map.ofEntries(
            Map.entry("expense", (byte) 1),
            Map.entry("income", (byte) 2)
    );

    @NotEmpty
    @Size(max = 30)
    private String name;

    @NotEmpty
    @Pattern(regexp = "expense|income", message = "Provide a valid transaction type")
    private String type;

    @Min(value = 1, message = "Make sure that amount is greater than 0")
    private float amount;

    @NotEmpty
    @Pattern(regexp = "^(0[1-9]|[1-2][0-9]|3[0-1]) (Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec) [0-9]{4}, (0[1-9]|1[0-2]):(0[1-9]|[1-5][0-9]) (A|P)M$", message = "Date must be specified like \"01 Jan 2025, 01:00 AM\"")
    @JsonProperty("created_at")
    private String createdAt;

    @NotNull
    @JsonProperty("account_id")
    private long accountId;

    @NotNull
    @JsonProperty("category_id")
    private long categoryId;

    public byte getTypeValue() {
        return TRANSACTION_TYPE_MAP.get(this.type);
    }

    public long getCreatedAtValue() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy, hh:mm a");
        return simpleDateFormat.parse(this.createdAt).getDate();
    }

}
