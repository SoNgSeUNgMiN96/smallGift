package com.sgwannabig.smallgift.springboot.service;


import com.sgwannabig.smallgift.springboot.service.result.MultipleResult;
import com.sgwannabig.smallgift.springboot.service.result.Result;
import com.sgwannabig.smallgift.springboot.service.result.SingleResult;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ResponseService {
    public <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        setSuccessResult(result);
        result.setData(data);
        return result;
    }

    public <T> SingleResult<T> getfailResult(int code, T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setCode(code);
        result.setData(data);
        return result;
    }

    public <T> MultipleResult<T> getMultipleResult(List<T> data) {
        MultipleResult<T> result = new MultipleResult<>();
        setSuccessResult(result);
        result.setData(data);

        return result;
    }

    public Result getSuccessResult() {
        Result result = new Result();
        setSuccessResult(result);

        return result;
    }

    public void setSuccessResult(Result result) {
        result.setSuccess(true);
        result.setCode(0);
        result.setMsg("성공");
    }

    public Result getFailureResult(int code, String msg) {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);

        return result;
    }
}
