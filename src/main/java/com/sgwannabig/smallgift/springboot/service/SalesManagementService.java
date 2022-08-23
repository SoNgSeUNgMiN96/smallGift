package com.sgwannabig.smallgift.springboot.service;

import com.sgwannabig.smallgift.springboot.domain.Manager;
import com.sgwannabig.smallgift.springboot.domain.Product;
import com.sgwannabig.smallgift.springboot.dto.sales.ManagerDto;
import com.sgwannabig.smallgift.springboot.dto.sales.ProductRequestDto;
import com.sgwannabig.smallgift.springboot.dto.sales.ShopProductDto;
import com.sgwannabig.smallgift.springboot.dto.sales.UpdateProductRequestDto;
import com.sgwannabig.smallgift.springboot.repository.ManagerRepository;
import com.sgwannabig.smallgift.springboot.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SalesManagementService {
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private ProductRepository productRepository;
//    private final FileStore fileStore;

    // 사업자 등록
    public Manager saveManager(ManagerDto managerDto) throws IOException {

        Manager manager = managerRepository.findById(managerDto.getManagerId()).orElse(null);

        if(manager == null) {
            manager = new Manager();
//            UploadFile managerImage = fileStore.storeFile(managerDto.getManagerAttachFile());
//            UploadFile salesImage = fileStore.storeFile(managerDto.getSalesAttachFile());

            manager.setId(managerDto.getManagerId());
            manager.setUsername(managerDto.getUsername());
            manager.setBusinessName(managerDto.getAddress());
            manager.setBusinessTel(managerDto.getBusinessTel());
            manager.setBusinessType(managerDto.getBusinessType());
            manager.setSettlementBank(managerDto.getBankName());
            manager.setSettlementAccount(managerDto.getBankAccount());
//            manager.setManagerAttachFile(managerImage);
            //manager.setSalesAttachFile(salesImage);
            managerRepository.save(manager);
        }

        return manager;
    }

    // 상품 등록
    public Product saveProduct(ProductRequestDto productRequestDto) throws IOException {
        Product product = productRepository.findById(productRequestDto.getProductId()).orElse(null);
        if(product == null) {
            product = new Product();
//            UploadFile imageFile = fileStore.storeFile(productRequestDto.getImageFile());

            product.setId(productRequestDto.getProductId());
            product.setCategory(productRequestDto.getCategory());
            product.setProductName(productRequestDto.getProductName());
            product.setProductPrice(productRequestDto.getProductPrice());
            product.setProductStock(productRequestDto.getProductStock());
            product.setStatus(productRequestDto.getStatus());
            product.setStartDate(productRequestDto.getStart_dt());
            product.setEndDate(productRequestDto.getEnd_dt());
//            product.setImageFile(imageFile);
            productRepository.save(product);
        }
        return product;
    }

    // 상품 관리
    public List<ShopProductDto> showProducts(Long shopId) {
        List<Product> products = productRepository.findAllById(shopId);
        if(products.isEmpty()) {
            return null;
        }

        List<ShopProductDto> showProductDtos = new ArrayList<>();
        long listNum = 1;
        for(Product product : products) {
            ShopProductDto tmpProduct = new ShopProductDto();
            tmpProduct.setListNum(listNum++);
            tmpProduct.setProductId(product.getId());
            tmpProduct.setProductName(product.getProductName());
            tmpProduct.setProductPrice(product.getProductPrice());
            tmpProduct.setStatus(product.getStatus());
            showProductDtos.add(tmpProduct);
        }
        return showProductDtos;
    }

    // 상품 삭제
    public String deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if(product == null) return "존재하지 않는 상품입니다.";

        productRepository.deleteById(productId);
        return "상품이 삭제되었습니다.";
    }

    // 상품 수정
    public Product updateProduct(UpdateProductRequestDto updateProductRequestDto) throws IOException {
        Product product = productRepository.findById(updateProductRequestDto.getProductId()).orElse(null);
        if(product == null) return null;

//        UploadFile imageFile = fileStore.storeFile(updateProductRequestDto.getImageFile());

        product.setProductName(updateProductRequestDto.getProductName());
        product.setProductPrice(updateProductRequestDto.getProductPrice());
        product.setProductStock(updateProductRequestDto.getProductPrice());
        product.setStartDate(updateProductRequestDto.getStart_dt());
        product.setEndDate(updateProductRequestDto.getEnd_dt());
//        product.setImageFile(imageFile);
        productRepository.save(product);
        return product;
    }

    // 상품 상태 변경
    public boolean changeProductStatus(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if(product.getStatus() == 1) {
            product.setStatus(0); // 상품 노출 X
            productRepository.save(product);
            return true;
        } else {
            product.setStatus(1); // 상품 노출 O
            productRepository.save(product);
            return false;
        }
    }
}
