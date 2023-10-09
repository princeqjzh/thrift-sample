package com.hgwz;

import com.testerhome.service.ComputeServer;
import com.testerhome.service.ComputeRequest;
import com.testerhome.service.ComputeResponse;
import com.testerhome.service.ComputeType;

public class Impl implements ComputeServer.Iface {

    public ComputeResponse getComputeResult(ComputeRequest request) {
        ComputeType computeType = request.getComputeType();
        long x = request.getX();
        long y = request.getY();
        System.out.println("Compute result begin. [x:" + x + "] [y:" + y + "] [type:" + computeType.toString() + "]");
        long begin = System.currentTimeMillis();

        ComputeResponse response = new ComputeResponse();
        response.setErrorNo(0);
        try {
            long ret;
            if (computeType == ComputeType.ADD) {
                ret = add(x, y);
                response.setComputeRet(ret);
            } else {
                ret = sub(x, y);
                response.setComputeRet(ret);
            }
        } catch (Exception e) {
            response.setErrorNo(1001);
            response.setErrorMsg(e.getMessage());
            System.err.println("Exception:" + e);
        }

        long end = System.currentTimeMillis();
        System.out.println("Compute result end. [errno:" +  response.getErrorNo() + "] cost:[" + (end - begin) + "ms]");
        return response;
    }

    public long add(long x, long y) {
        return x + y;
    }
    public long sub(long x, long y) {
        return x - y;
    }
}
