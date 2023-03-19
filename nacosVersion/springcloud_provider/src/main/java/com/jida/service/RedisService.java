package com.jida.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Splitter;
import com.jida.common.cache.CacheService;
import com.jida.common.dto.FindDiReq;
import com.jida.entity.ApplyInfoVO;
import com.jida.entity.DimensionRightVO;
import com.jida.entity.LoginUserVO;
import com.jida.entity.MyOrderVO;
import com.jida.mapper.ApplyInfoVOMapper;
import com.jida.mapper.DimensionRightVOMapper;
import com.jida.mapper.LoginUserVOMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RedisService {

	String KEY_TEST_OPS_FOR_HASH= "testOpsForHash";
	@Resource
	RedisTemplate redisTemplate;

	@Resource
	CacheService cacheService;

	@Resource
	ApplyInfoVOMapper applyInfoVOMapper;

	public String setKey() {
		for (int i = 0; i < 500; i++) {
			MyOrderVO myOrderVO = new MyOrderVO();
			myOrderVO.setOrderNo(""+i*2);
			myOrderVO.setOrderId(Long.valueOf(i*2));
			redisTemplate.opsForHash().putIfAbsent(KEY_TEST_OPS_FOR_HASH, i+"",myOrderVO);
//			redisTemplate.opsForHash().putIfAbsent(KEY_TEST_OPS_FOR_HASH, i*2+"",myOrderVO);
		}
		return "ok";
	}

	public String getHashField() {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < 502; i++) {
			list.add(i+"");
		}
		StopWatch stopWatch = new StopWatch("getHashField");
		stopWatch.start("multiGet");
		List<MyOrderVO> list1 = (List<MyOrderVO>)redisTemplate.opsForHash().multiGet(KEY_TEST_OPS_FOR_HASH, list);
		stopWatch.stop();
		System.out.println(JSONObject.toJSONString(list1));
		stopWatch.start("get");
		for (int i = 0; i < 502; i++) {
			MyOrderVO myOrderVO = (MyOrderVO)redisTemplate.opsForHash().get(KEY_TEST_OPS_FOR_HASH, i+"");
			System.out.println();
		}
		stopWatch.stop();
		stopWatch.start("entries");
		Map<String, MyOrderVO> entries = redisTemplate.boundHashOps(KEY_TEST_OPS_FOR_HASH).entries();
		System.out.println(entries);
		stopWatch.stop();
		log.info(stopWatch.prettyPrint());
		return "ok";
	}
	String KEY_GROUP_DI = "groupDi";

	String KEY_CALC_DI = "calcSceneDi";

	@Autowired
	LoginUserVOMapper loginUserVOMapper;
	public String fillCurrUserName() {
		List<ApplyInfoVO> applyInfoVOS = applyInfoVOMapper.selectList();
		List<ApplyInfoVO> right = getRight(applyInfoVOS);
		System.out.println(JSONObject.toJSONString(right));
		return "ok";
	}

	public void bb() {
	}

	private List<ApplyInfoVO> getRight(List<ApplyInfoVO> applyInfoVOS) {
		Map<String, List<Long>> entriesGroup = redisTemplate.boundHashOps(KEY_GROUP_DI).entries();
		Map<String, List<Long>> entriesCalc = redisTemplate.boundHashOps(KEY_CALC_DI).entries();
		List<FindDiReq> findDiReqs = new ArrayList<>();
		Map<Long, FindDiReq> map = new HashMap<>();
		for (ApplyInfoVO applyInfoVO : applyInfoVOS) {
			FindDiReq findDiReq = new FindDiReq();
			findDiReq.setGroupCodeList(Splitter.on(",").splitToList(applyInfoVO.getGroupCode()));
			findDiReq.setCalcSceneCodeList(Splitter.on(",").splitToList(applyInfoVO.getCheckRightMode()));
			findDiReq.setCheckMode("1");
			findDiReq.setRoleCode(applyInfoVO.getRoleNode());
			findDiReq.setId(applyInfoVO.getApplyId().toString());
			findDiReqs.add(findDiReq);
			map.put(applyInfoVO.getApplyId(), findDiReq);
		}
//		BiFunction<String,String, List<Long>> funcGroup = (groupCode, roleCode) -> cacheService.groupDi(groupCode, roleCode);
//		BiFunction<String,String, List<Long>> funcCalc = (groupCode, roleCode) -> cacheService.calcSceneDi(groupCode, roleCode);
		BiFunction<String,String, List<Long>> funcGroup = (groupCode, roleCode) -> {
			List<DimensionRightVO> pageList = dimensionRightVOMapper.getDiByDiCode(groupCode, "group", roleCode);
			List<Long> collect = pageList.stream().map(DimensionRightVO::getUserId).distinct().collect(Collectors.toList());
			redisTemplate.opsForHash().put(KEY_GROUP_DI, roleCode+"_"+groupCode,collect);
			return collect;};
		BiFunction<String,String, List<Long>> funcCalc = (groupCode, roleCode) -> {
			List<DimensionRightVO> pageList = dimensionRightVOMapper.getDiByDiCode(groupCode, "calcScene", roleCode);
			List<Long> collect = pageList.stream().map(DimensionRightVO::getUserId).distinct().collect(Collectors.toList());
			redisTemplate.opsForHash().put(KEY_CALC_DI, roleCode+"_"+groupCode,collect);
			return collect;};
		Set<Long> allUserId = new HashSet<>();
		for (FindDiReq findDiReq : findDiReqs) {
			List<Long> aa = aa(findDiReq.getGroupCodeList(), findDiReq.getRoleCode(), entriesGroup, funcGroup);
			List<Long> aa1 = aa(findDiReq.getCalcSceneCodeList(), findDiReq.getRoleCode(), entriesCalc, funcCalc);
			List<Long> intersection = intersection(aa, aa1);
			findDiReq.setUserIdList(intersection);
			allUserId.addAll(intersection);
		}
		if (CollectionUtils.isNotEmpty(allUserId)) {
			List<LoginUserVO> loginUserVOS = loginUserVOMapper.selectByIdList(allUserId);
			Map<Long, String> collect = loginUserVOS.stream().collect(Collectors.toMap(LoginUserVO::getId, LoginUserVO::getUsername));
			for (ApplyInfoVO applyInfoVO : applyInfoVOS) {
				FindDiReq findDiReq = map.get(applyInfoVO.getApplyId());
				List<Long> userIdList = findDiReq.getUserIdList();
				String collect1 = userIdList.stream().map(o -> collect.get(o)).collect(Collectors.joining(","));
				applyInfoVO.setCurrUserName(collect1);
			}
		}
		Long expire = redisTemplate.getExpire(KEY_GROUP_DI);
		if (expire == -1) {
			redisTemplate.expire(KEY_GROUP_DI, 3, TimeUnit.MINUTES);
		}
		Long expire1 = redisTemplate.getExpire(KEY_CALC_DI);
		if (expire1 == -1) {
			redisTemplate.expire(KEY_CALC_DI, 3, TimeUnit.MINUTES);
		}
		return applyInfoVOS;
	}

	@Resource
	private DimensionRightVOMapper dimensionRightVOMapper;

	private List<Long> aa(List<String> groupCodeList, String roleCode, Map<String, List<Long>> entriesGroup, BiFunction<String,String, List<Long>> funcGroup) {
		List<Long> prev = new ArrayList<>();
		List<CompletableFuture<List<Long>>> futures = new ArrayList<>();
		for (int i = 0; i < groupCodeList.size(); i++) {
			String groupCode = groupCodeList.get(i);
			String myKey = roleCode+"_"+groupCode;
			if (!entriesGroup.containsKey(myKey)) {
//				longs1 = funcGroup.apply(roleCode, groupCode);
//				entriesGroup.put(myKey, longs1);
				CompletableFuture<List<Long>> group = CompletableFuture.supplyAsync(() -> {
					List<Long> apply = funcGroup.apply(groupCode, roleCode);
					entriesGroup.put(myKey, apply);
					return apply;
				});
				futures.add(group);
			}

		}
		CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).join();
		for (int i = 0; i < groupCodeList.size(); i++) {
			String groupCode = groupCodeList.get(i);
			String myKey = roleCode+"_"+groupCode;
			List<Long> longs1 = entriesGroup.get(myKey);
			if (CollectionUtils.isEmpty(longs1)) {
				return new ArrayList<>();
			} else if (i == 0) {
				prev = longs1;
			} else {
				prev = intersection(prev, longs1);
			}
		}
		return prev;
	}

	//获取两个集合交集
	public static  <T> List<T> intersection(List<T> list1, List<T> list2){
		List<T> union = (List<T>) CollectionUtils.intersection(list1, list2);
		return union;
	}
}
