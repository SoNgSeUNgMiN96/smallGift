package com.sgwannabig.smallgift.springboot.service.manager;

import com.sgwannabig.smallgift.springboot.domain.Manager;

public interface GetManagerInfoUsecase {
  Manager getManagerInfo(Long userId);
}
