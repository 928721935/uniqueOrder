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
* DimensionRightVO
*
* @author 系统
* @created Create Time: Sat Mar 11 22:11:40 CST 2023
*/
@ApiModel(value="com.jida.entity.DimensionRightVO",description="")
@Data()
public class DimensionRightVO {
    /**
     * 
     */
    @ApiModelProperty(value = "", example = "dimensionRightId")
    private Long dimensionRightId;

    /**
     * 
     */
    @ApiModelProperty(value = "", example = "roleCode")
    @Length(max = 255)
    @Size(max = 255)
    private String roleCode;

    /**
     * 
     */
    @ApiModelProperty(value = "", example = "userId")
    private Long userId;

    /**
     * 
     */
    @ApiModelProperty(value = "", example = "groupCodeList")
    @Length(max = 255)
    @Size(max = 255)
    private String groupCodeList;

    /**
     * 
     */
    @ApiModelProperty(value = "", example = "calcSceneList")
    @Length(max = 255)
    @Size(max = 255)
    private String calcSceneList;

    /**
     * 
     */
    @ApiModelProperty(value = "", example = "bussSneneList")
    @Length(max = 255)
    @Size(max = 255)
    private String bussSneneList;
}