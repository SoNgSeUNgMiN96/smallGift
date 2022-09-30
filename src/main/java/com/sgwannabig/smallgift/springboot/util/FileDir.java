package com.sgwannabig.smallgift.springboot.util;

import lombok.Getter;

@Getter
public enum FileDir {
  REGIST_MANAGER("manager/regist");

  FileDir(String dir) {
    this.dir = dir;
  }

  private String dir;
}
