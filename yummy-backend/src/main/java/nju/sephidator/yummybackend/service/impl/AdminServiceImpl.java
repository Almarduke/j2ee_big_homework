package nju.sephidator.yummybackend.service.impl;

import nju.sephidator.yummybackend.model.MemberDAO;
import nju.sephidator.yummybackend.model.RestaurantDAO;
import nju.sephidator.yummybackend.model.RestaurantInfoCheckDAO;
import nju.sephidator.yummybackend.repository.MemberJPA;
import nju.sephidator.yummybackend.repository.RestaurantInfoCheckJPA;
import nju.sephidator.yummybackend.repository.RestaurantJPA;
import nju.sephidator.yummybackend.service.AdminService;
import nju.sephidator.yummybackend.vo.restaurant.RestaurantInfoCheckVO;
import nju.sephidator.yummybackend.vo.util.StatisticsDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private RestaurantInfoCheckJPA restaurantInfoCheckJPA;

    @Autowired
    private RestaurantJPA restaurantJPA;

    @Autowired
    private MemberJPA memberJPA;

    @Override
    public List<RestaurantInfoCheckVO> getRestaurantInfoCheckList() {
        List<RestaurantInfoCheckDAO> restaurantInfoCheckDAOList =
                restaurantInfoCheckJPA.findAll();
        return restaurantInfoCheckDAOList.stream()
                .map(RestaurantInfoCheckVO::new)
                .collect(Collectors.toList());
    }

    @Override
    public void approveRestaurantInfoCheck(String restaurantId) {
        RestaurantInfoCheckDAO restaurantInfoCheckDAO =
                restaurantInfoCheckJPA.getOne(restaurantId);
        RestaurantDAO restaurantDAO = restaurantJPA.getOne(restaurantId);
        restaurantDAO.setName(restaurantInfoCheckDAO.getName());
        restaurantDAO.setPhone(restaurantInfoCheckDAO.getPhone());
        restaurantDAO.setAddress(restaurantInfoCheckDAO.getAddress());
        restaurantJPA.save(restaurantDAO);
        restaurantInfoCheckJPA.deleteById(restaurantId);
    }

    @Override
    public List<StatisticsDetailVO> getMemberStatistics() {
        List<MemberDAO> memberList = memberJPA.findAll();
        List<StatisticsDetailVO> result = new ArrayList<>();
        int[] levelList = {0, 1, 2, 3, 4, 5};
        for (int level: levelList) {
            StatisticsDetailVO detail = new StatisticsDetailVO();
            detail.setName("等级" + level);
            detail.setValue(memberList.stream().filter(x->x.getLevel()==level).count());
            result.add(detail);
        }
        return result;
    }

    @Override
    public List<StatisticsDetailVO> getRestaurantStatistics() {
        List<RestaurantDAO> restaurantList = restaurantJPA.findAll();
        List<StatisticsDetailVO> result = new ArrayList<>();
        int[] revenueLevel = {0, 100, 200, 300, 400, 500};
        for (int i = 0; i < revenueLevel.length - 1; i++) {
            StatisticsDetailVO detail = new StatisticsDetailVO();
            detail.setName(revenueLevel[i] + "~" + revenueLevel[i+1] + "元");
            int finalI = i;
            detail.setValue(restaurantList.stream()
                    .filter(x -> (x.getRevenue() >= revenueLevel[finalI]
                            && x.getRevenue() < revenueLevel[finalI +1])).count());
            result.add(detail);
        }
        StatisticsDetailVO detail = new StatisticsDetailVO();
        detail.setName(revenueLevel[revenueLevel.length - 1] + "元以上");
        detail.setValue(restaurantList.stream()
                .filter(x -> (x.getRevenue() >= revenueLevel[revenueLevel.length - 1]))
                .count());
        result.add(detail);
        return result;
    }

    @Override
    public List<StatisticsDetailVO> getFinanceStatistics() {
        return null;
    }
}
