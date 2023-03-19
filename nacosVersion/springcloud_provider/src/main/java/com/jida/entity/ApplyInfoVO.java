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
* ApplyInfoVO
*
* @author 系统
* @created Create Time: Sat Mar 11 22:10:27 CST 2023
*/
@ApiModel(value="com.jida.entity.ApplyInfoVO",description="")
@Data()
public class ApplyInfoVO {
    /**
     * 
     */
    @ApiModelProperty(value = "", example = "applyId")
    private Long applyId;

    /**
     * 
     */
    @ApiModelProperty(value = "", example = "groupCode")
    @Length(max = 255)
    @Size(max = 255)
    private String groupCode;

    /**
     * 
     */
    @ApiModelProperty(value = "", example = "calcSceneCode")
    @Length(max = 255)
    @Size(max = 255)
    private String calcSceneCode;

    /**
     * 
     */
    @ApiModelProperty(value = "", example = "bussSceneCode")
    @Length(max = 255)
    @Size(max = 255)
    private String bussSceneCode;

    /**
     * 
     */
    @ApiModelProperty(value = "", example = "applyName")
    @Length(max = 255)
    @Size(max = 255)
    private String applyName;

    /**
     * 
     */
    @ApiModelProperty(value = "", example = "roleNode")
    @Length(max = 255)
    @Size(max = 255)
    private String roleNode;

    /**
     * 
     */
    @ApiModelProperty(value = "", example = "checkRightMode")
    @Length(max = 255)
    @Size(max = 255)
    private String checkRightMode;

    private String currUserName;
}