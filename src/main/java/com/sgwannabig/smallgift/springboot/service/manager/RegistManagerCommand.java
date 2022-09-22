package com.sgwannabig.smallgift.springboot.service.manager;

import com.sgwannabig.smallgift.springboot.domain.Manager;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

@Value
public class RegistManagerCommand {
  private Manager manager;
  private MultipartFile businessRegistration;
  private MultipartFile mailOrderSalesRegistration;

  public RegistManagerCommand(Manager manager, MultipartFile businessRegistration,
      MultipartFile mailOrderSalesRegistration) {
    this.manager = manager;
    this.businessRegistration = businessRegistration;
    this.mailOrderSalesRegistration = mailOrderSalesRegistration;
  }
}
