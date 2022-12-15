package com.bootcamp.java.customer.web.model;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerModel {

    @JsonIgnore
    private String id;
    
    @NotBlank(message="Identity Number cannot be null or empty")
    private String identityNumber;
    
    @NotBlank(message="Name cannot be null or empty")
    private String name;
    
    @NotBlank(message="LastName cannot be null or empty")
    private String lastName;
    
    @NotBlank(message="BusinessName cannot be null or empty")
    private String businessName;
    
    @NotBlank(message="Email cannot be null or empty")
    private String email;
    
    @NotBlank(message="PhoneNumber cannot be null or empty")
    private String phoneNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    
    @NotBlank(message="Type cannot be null or empty")
    private String type;
   
}
