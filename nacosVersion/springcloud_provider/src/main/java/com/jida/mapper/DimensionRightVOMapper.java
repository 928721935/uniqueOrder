package com.jida.mapper;

import com.jida.entity.DimensionRightVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 开发公司：青岛海豚数据技术有限公司
* 版权：青岛海豚数据技术有限公司
* Interface
* DimensionRightVOMapper
*
* @author 系统
* @created Create Time: Sat Mar 11 22:11:40 CST 2023
*/
@Mapper()
public interface DimensionRightVOMapper {
    /**
     * 根据主键删除数据
     * @param dimensionRightId
     */
    int deleteByPrimaryKey(Long dimensionRightId);

    /**
     * 插入数据库记录（不建议使用）
     * @param record
     */
    int insert(DimensionRightVO record);

    /**
     * 插入数据库记录（建议使用）
     * @param record
     */
    int insertSelective(DimensionRightVO record);

    /**
     * 根据主键id查询
     * @param dimensionRightId
     */
    DimensionRightVO selectByPrimaryKey(Long dimensionRightId);

    /**
     * 修改数据(推荐使用)
     * @param record
     */
    int updateByPrimaryKeySelective(DimensionRightVO record);

    /**
     * 修改数据
     * @param record
     */
    int updateByPrimaryKey(DimensionRightVO record);

    List<DimensionRightVO> getDiByDiCode(@Param("code") String code, @Param("type") String type, @Param("roleCode") String roleCode);
}