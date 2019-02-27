package nju.sephidator.yummybackend.utils;

import nju.sephidator.yummybackend.vo.ResultVO;
import org.springframework.http.HttpStatus;

public class ResultVOUtil {

    public static ResultVO success(Object object, String message) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(HttpStatus.OK.value());
        resultVO.setMsg(message);
        return resultVO;
    }

    public static ResultVO success(String message) {
        return success(null, message);
    }

    public static ResultVO error(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
