package com.sgwannabig.smallgift.springboot.util;

import lombok.Getter;

@Getter
public enum FileDir {
  REGIST_MANAGER("image/manager"),
  REGIST_PRODUCT("image/product");

  FileDir(String dir) {
    this.dir = dir;
  }

  private String dir;
}
