package com.flag.pam.resources.accounts;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author rameshalagumalai
 * @Date 27/01/2025
 * */

@Getter
@Setter
public class AccountPayload {

    private static final Map<String, Byte> ACCOUNT_TYPE_MAP = Map.ofEntries(
        Map.entry("bank", (byte) 1),
        Map.entry("cash", (byte) 2),
        Map.entry("credits", (byte) 3)
    );

    @NotEmpty
    @Size(max = 30)
    private String name;

    @NotEmpty
    @Size(max = 7)
    @Pattern(regexp = "cash|bank|credits", message = "Invalid value passed for account type")
    private String type;

    @Min(value = 0, message = "Make sure that balance has a maximum of 2 decimal places")
    private float balance;

    public byte getTypeValue() {
        return ACCOUNT_TYPE_MAP.get(this.type);
    }

}
