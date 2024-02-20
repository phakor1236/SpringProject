package com.ph.pojo.dto;

import lombok.Data;

/**
 * dto內是返回給使用者的數據
 */
@Data
public class PortalDto {

    private String keyWords;
    private int type = 0;
    private int pageNum=1;
    private int pageSize = 10;

}