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
* LoginUserVO
*
* @author 系统
* @created Create Time: Sun Mar 12 17:06:40 CST 2023
*/
@ApiModel(value="com.jida.entity.LoginUserVO",description="")
@Data()
public class LoginUserVO {
    /**
     * 
     */
    @ApiModelProperty(value = "", example = "id")
    private Long id;

    /**
     * 
     */
    @ApiModelProperty(value = "", example = "username")
    @Length(max = 255)
    @Size(max = 255)
    private String username;

    /**
     * 
     */
    @ApiModelProperty(value = "", example = "password")
    @Length(max = 255)
    @Size(max = 255)
    private String password;

    /**
     * 
     */
    @ApiModelProperty(value = "", example = "nickName")
    @Length(max = 255)
    @Size(max = 255)
    private String nickName;

    /**
     * 
     */
    @ApiModelProperty(value = "", example = "hobbyId")
    @Length(max = 255)
    @Size(max = 255)
    private String hobbyId;

    /**
     * 
     */
    @ApiModelProperty(value = "", example = "schoolId")
    private Integer schoolId;
}