package com.flag.pam.resources.categories;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author rameshalagumalai
 * @Date 28/01/2025
 * */

@Getter
@Setter
public class CategoryPayload {

    private static final Map<String, Byte> CATEGORY_TYPE_MAP = Map.ofEntries(
            Map.entry("expense", (byte) 1),
            Map.entry("income", (byte) 2),
            Map.entry("both", (byte) 3)
    );

    @NotEmpty
    @Size(max = 30)
    private String name;

    @NotEmpty
    @Pattern(regexp = "expense|income|both", message = "Provide a valid category type")
    private String type;

    @NotEmpty
    @Pattern(regexp = "primary|secondary|success|danger|warning|info|dark", message = "Provide a valid color")
    private String color;

    @NotEmpty
    @Pattern(regexp = "^fa-(solid|brands)\\sfa-[a-z][a-z-]*[a-z]$", message = "Provide a valid icon")
    @Size(max = 30)
    private String icon;

    public byte getTypeValue() {
        return CATEGORY_TYPE_MAP.get(this.type);
    }

}
