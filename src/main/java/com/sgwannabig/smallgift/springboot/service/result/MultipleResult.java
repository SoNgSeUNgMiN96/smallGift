package com.sgwannabig.smallgift.springboot.service.result;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MultipleResult <T> extends Result {
    private List<T> data;
}