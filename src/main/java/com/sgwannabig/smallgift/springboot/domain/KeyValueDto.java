package com.sgwannabig.smallgift.springboot.domain;


import lombok.*;

@Data
@EqualsAndHashCode(callSuper=false)
public class KeyValueDto <IdType, ValueType> {
    private IdType id;
    private ValueType data;

    public KeyValueDto(IdType id, ValueType data) {
        this.id = id;
        this.data = data;
    }
}
