package com.jida.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Size;

/**
* 开发公司：青岛海豚数据技术有限公司
* 版权：青岛海豚数据技术有限公司
* Class
* MyOrderVO
*
* @author 系统
* @created Create Time: Sun Feb 05 19:59:05 CST 2023
*/
@ApiModel(value="com.jida.entity.MyOrderVO",description="")
@Data
public class MyOrderVO {
    /**
     * 
     */
    @ApiModelProperty(value = "", example = "orderId")
    private Long orderId;

    /**
     * 
     */
    @ApiModelProperty(value = "", example = "orderNo")
    @Length(max = 11)
    @Size(max = 11)
    private String orderNo;
}