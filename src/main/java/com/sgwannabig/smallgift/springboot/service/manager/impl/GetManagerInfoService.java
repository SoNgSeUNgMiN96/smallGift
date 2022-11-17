package com.sgwannabig.smallgift.springboot.service.manager.impl;

import com.sgwannabig.smallgift.springboot.config.advice.exception.ManagerNotFoundException;
import com.sgwannabig.smallgift.springboot.domain.Manager;
import com.sgwannabig.smallgift.springboot.repository.ManagerRepository;
import com.sgwannabig.smallgift.springboot.service.manager.GetManagerInfoUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetManagerInfoService implements GetManagerInfoUsecase {

  private final ManagerRepository managerRepository;

  @Override
  public Manager getManagerInfo(Long userId) {
    return managerRepository.findByUserId(userId).orElseThrow(() -> {
      throw new ManagerNotFoundException("존재하는 매니저가 없습니다");
    });
  }
}
