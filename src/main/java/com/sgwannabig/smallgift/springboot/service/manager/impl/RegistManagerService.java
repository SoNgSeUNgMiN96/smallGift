package com.sgwannabig.smallgift.springboot.service.manager.impl;

import com.sgwannabig.smallgift.springboot.config.advice.exception.ManagerExistedException;
import com.sgwannabig.smallgift.springboot.domain.Manager;
import com.sgwannabig.smallgift.springboot.repository.ManagerRepository;
import com.sgwannabig.smallgift.springboot.service.manager.RegistManagerCommand;
import com.sgwannabig.smallgift.springboot.service.manager.RegistManagerUsecase;
import com.sgwannabig.smallgift.springboot.util.FileDir;
import com.sgwannabig.smallgift.springboot.util.aws.S3Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class RegistManagerService implements RegistManagerUsecase {

  private final S3Manager s3Manager;
  private final ManagerRepository managerRepository;

  @Override
  public Manager apply(RegistManagerCommand registManagerCommand) {
    validateExistedManager(registManagerCommand.getManager().getUserId());
    MultipartFile businessRegistration = registManagerCommand.getBusinessRegistration();
    MultipartFile mailOrderSalesRegistration = registManagerCommand.getMailOrderSalesRegistration();

    String businessRegistrationUrl = getUploadFileUrl(businessRegistration, FileDir.REGIST_MANAGER);
    String mailOrderSalesRegistrationUrl = getUploadFileUrl(mailOrderSalesRegistration,
        FileDir.REGIST_MANAGER);


    Manager manager = registManagerCommand.getManager();

    manager.setBusinessRegistration(businessRegistrationUrl);
    manager.setMailOrderSalesRegistraion(mailOrderSalesRegistrationUrl);

    return managerRepository.save(manager);
  }

  private String getUploadFileUrl(MultipartFile file, FileDir fileDir) {
    return s3Manager.uploadFile(file, fileDir);
  }

  private void validateExistedManager(Long userId) {
    managerRepository.findByUserId(userId).ifPresent(m -> {
      throw new ManagerExistedException("이미 존재하는 매니저입니다");
    });
  }
}
