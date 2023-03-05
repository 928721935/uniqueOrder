package com.jida.mapper;

import com.jida.entity.MyOrderVO;
import org.apache.ibatis.annotations.Mapper;

/**
* 开发公司：青岛海豚数据技术有限公司
* 版权：青岛海豚数据技术有限公司
* Interface
* MyOrderVOMapper
*
* @author 系统
* @created Create Time: Sun Feb 05 20:08:27 CST 2023
*/
@Mapper()
public interface MyOrderVOMapper {
    /**
     * 根据主键删除数据
     * @param orderId
     */
    int deleteByPrimaryKey(Integer orderId);

    /**
     * 插入数据库记录（不建议使用）
     * @param record
     */
    int insert(MyOrderVO record);

    /**
     * 插入数据库记录（建议使用）
     * @param record
     */
    int insertSelective(MyOrderVO record);

    /**
     * 根据主键id查询
     * @param orderId
     */
    MyOrderVO selectByPrimaryKey(Integer orderId);

    /**
     * 修改数据(推荐使用)
     * @param record
     */
    int updateByPrimaryKeySelective(MyOrderVO record);

    /**
     * 修改数据
     * @param record
     */
    int updateByPrimaryKey(MyOrderVO record);

    Long getMaxOrderNo();
}