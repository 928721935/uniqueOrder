package com.jida.mapper;

import com.jida.entity.LoginUserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
* 开发公司：青岛海豚数据技术有限公司
* 版权：青岛海豚数据技术有限公司
* Interface
* LoginUserVOMapper
*
* @author 系统
* @created Create Time: Sun Mar 12 17:09:07 CST 2023
*/
@Mapper()
public interface LoginUserVOMapper {
    /**
     * 根据主键删除数据
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入数据库记录（不建议使用）
     * @param record
     */
    int insert(LoginUserVO record);

    /**
     * 插入数据库记录（建议使用）
     * @param record
     */
    int insertSelective(LoginUserVO record);

    /**
     * 根据主键id查询
     * @param id
     */
    LoginUserVO selectByPrimaryKey(Integer id);

    /**
     * 修改数据(推荐使用)
     * @param record
     */
    int updateByPrimaryKeySelective(LoginUserVO record);

    /**
     * 修改数据
     * @param record
     */
    int updateByPrimaryKey(LoginUserVO record);

    List<LoginUserVO> selectByIdList(@Param("list") Collection<Long> id);

    Long getNextSerialNo(@Param("type") String type);

    Long getNextSerialNoForUpdate(@Param("type") String type);

    int updateSerial(@Param("type") String type, Long oldVal, Long newVal);
}