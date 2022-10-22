package com.sgwannabig.smallgift.springboot.service.manager;

import com.sgwannabig.smallgift.springboot.domain.Manager;
import java.util.function.Function;

public interface RegistManagerUsecase extends Function<RegistManagerCommand, Manager> {

}
