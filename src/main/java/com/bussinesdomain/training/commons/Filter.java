package com.bussinesdomain.training.commons;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class Filter {

    private String field;
    private String value;
    private String typeComparation;

    
}
