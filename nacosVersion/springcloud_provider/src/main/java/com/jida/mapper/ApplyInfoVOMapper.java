package com.jida.mapper;

import com.jida.entity.ApplyInfoVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* 开发公司：青岛海豚数据技术有限公司
* 版权：青岛海豚数据技术有限公司
* Interface
* ApplyInfoVOMapper
*
* @author 系统
* @created Create Time: Sat Mar 11 22:10:27 CST 2023
*/
@Mapper()
public interface ApplyInfoVOMapper {
    /**
     * 根据主键删除数据
     * @param applyId
     */
    int deleteByPrimaryKey(Long applyId);

    /**
     * 插入数据库记录（不建议使用）
     * @param record
     */
    int insert(ApplyInfoVO record);

    /**
     * 插入数据库记录（建议使用）
     * @param record
     */
    int insertSelective(ApplyInfoVO record);

    /**
     * 根据主键id查询
     * @param applyId
     */
    ApplyInfoVO selectByPrimaryKey(Long applyId);

    /**
     * 修改数据(推荐使用)
     * @param record
     */
    int updateByPrimaryKeySelective(ApplyInfoVO record);

    /**
     * 修改数据
     * @param record
     */
    int updateByPrimaryKey(ApplyInfoVO record);

    List<ApplyInfoVO> selectList();
}