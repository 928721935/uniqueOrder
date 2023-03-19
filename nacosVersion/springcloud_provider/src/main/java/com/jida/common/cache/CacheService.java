package com.jida.common.cache;

import com.jida.entity.DimensionRightVO;
import com.jida.mapper.DimensionRightVOMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CacheService {
    @Resource
    private DimensionRightVOMapper dimensionRightVOMapper;

    @Cacheable(value = "groupDi#5#m", key = "#roleCode+'_'+#code",unless = "#result.size()==0||#result==null")
    public List<Long> groupDi(String code, String roleCode) {
        List<DimensionRightVO> pageList = dimensionRightVOMapper.getDiByDiCode(code, "group", roleCode);
        return pageList.stream().map(DimensionRightVO::getUserId).distinct().collect(Collectors.toList());
    }

    @Cacheable(value = "calcSceneDi#5#m", key = "#roleCode+'_'+#code",unless = "#result.size()==0||#result==null")
    public List<Long> calcSceneDi(String code, String roleCode) {
        List<DimensionRightVO> pageList = dimensionRightVOMapper.getDiByDiCode(code, "calcScene", roleCode);
        return pageList.stream().map(DimensionRightVO::getUserId).distinct().collect(Collectors.toList());
    }

    @Cacheable(value = "bussSceneDi#5#m", key = "#roleCode+'_'+#code",unless = "#result.size()==0||#result==null")
    public List<Long> bussSceneDi(String code, String roleCode) {
        List<DimensionRightVO> pageList = dimensionRightVOMapper.getDiByDiCode(code, "bussScene", roleCode);
        return pageList.stream().map(DimensionRightVO::getUserId).distinct().collect(Collectors.toList());
    }
}
