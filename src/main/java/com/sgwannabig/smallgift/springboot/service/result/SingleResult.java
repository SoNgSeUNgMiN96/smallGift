package com.sgwannabig.smallgift.springboot.service.result;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleResult<T> extends Result {
    private T data;
}