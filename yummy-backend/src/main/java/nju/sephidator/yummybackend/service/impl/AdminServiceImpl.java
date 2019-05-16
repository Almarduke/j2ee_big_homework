package nju.sephidator.yummybackend.service.impl;

import nju.sephidator.yummybackend.model.Member;
import nju.sephidator.yummybackend.model.Restaurant;
import nju.sephidator.yummybackend.model.RestaurantInfoCheck;
import nju.sephidator.yummybackend.model.YummyFinance;
import nju.sephidator.yummybackend.repository.MemberJPA;
import nju.sephidator.yummybackend.repository.RestaurantInfoCheckJPA;
import nju.sephidator.yummybackend.repository.RestaurantJPA;
import nju.sephidator.yummybackend.repository.YummyFinanceJPA;
import nju.sephidator.yummybackend.service.AdminService;
import nju.sephidator.yummybackend.utils.MathUtil;
import nju.sephidator.yummybackend.utils.TimeUtil;
import nju.sephidator.yummybackend.vo.restaurant.RestaurantInfoCheckVO;
import nju.sephidator.yummybackend.vo.util.StatisticsDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private RestaurantInfoCheckJPA restaurantInfoCheckJPA;

    @Autowired
    private RestaurantJPA restaurantJPA;

    @Autowired
    private MemberJPA memberJPA;

    @Autowired
    private YummyFinanceJPA yummyFinanceJPA;

    @Override
    public List<RestaurantInfoCheckVO> getRestaurantInfoCheckList() {
        List<RestaurantInfoCheck> restaurantInfoCheckList =
                restaurantInfoCheckJPA.findAll();
        return restaurantInfoCheckList.stream()
                .map(RestaurantInfoCheckVO::new)
                .collect(Collectors.toList());
    }

    @Override
    public void approveRestaurantInfoCheck(String restaurantId) {
        RestaurantInfoCheck restaurantInfoCheck =
                restaurantInfoCheckJPA.getOne(restaurantId);
        Restaurant restaurant = restaurantJPA.getOne(restaurantId);
        restaurant.setName(restaurantInfoCheck.getName());
        restaurant.setPhone(restaurantInfoCheck.getPhone());
        restaurant.setAddress(restaurantInfoCheck.getAddress());
        restaurantJPA.save(restaurant);
        restaurantInfoCheckJPA.deleteById(restaurantId);
    }

    @Override
    public List<StatisticsDetailVO> getMemberStatistics() {
        List<Member> memberList = memberJPA.findAll();
        List<StatisticsDetailVO> result = new ArrayList<>();
        int[] levelList = {1, 2, 3, 4, 5, 6};
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
        List<Restaurant> restaurantList = restaurantJPA.findAll();
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
            Set<String> dateSet = new HashSet<>();
            for (YummyFinance financeDAO: yummyFinanceJPA.findAll()) {
                dateSet.add(TimeUtil.timeFormat(financeDAO.getTime()));
            }
            List<String> dateList = new ArrayList<>(dateSet);
            Collections.sort(dateList);
            List<StatisticsDetailVO> result = new ArrayList<>();
            for (String date: dateList) {
                StatisticsDetailVO detail = new StatisticsDetailVO();
                List<YummyFinance> financeOfDate =
                        yummyFinanceJPA.findAll().stream()
                        .filter(x -> TimeUtil.timeFormat(x.getTime())
                                .equals(date)).collect(Collectors.toList());
                Double totalRevenue = MathUtil.scaledDouble(financeOfDate.stream()
                        .map(YummyFinance::getIncome)
                        .collect(Collectors.toList()).stream()
                        .reduce((sum, item)->{ return sum + item; })
                        .get(), 2);
                detail.setName(date);
                detail.setValue(totalRevenue);
                result.add(detail);
            }
            return result;
        }
    }
