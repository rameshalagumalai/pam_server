package com.flag.pam.resources.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * @author rameshalagumalai
 * @Date 27/01/2025
 * */

@Getter
@Setter
public class UserEditPayload {

    @NotEmpty
    @Size(min = 2, max = 30)
    private String name;

    @Size(max = 30)
    @Pattern(regexp = "^[a-zA-Z0-9.-]{2,256}@[a-zA-Z][a-zA-Z]{2,64}$", message = "Provide a valid UPI ID")
    @JsonProperty("upi_id")
    private String upiId;

}