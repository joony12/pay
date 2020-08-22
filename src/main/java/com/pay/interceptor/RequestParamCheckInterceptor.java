package com.pay.interceptor;

import com.pay.exception.UnAuthorizedException;
import com.pay.vo.HeaderRequestVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Order(1)
@Component
public class RequestParamCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HeaderRequestVO headerRequestVO = getHeaderRequestVO(request);

        if (!headerRequestVO.isValid()) {
            throw new UnAuthorizedException();
        }
        return true;
    }

    private HeaderRequestVO getHeaderRequestVO(HttpServletRequest request) {
        String userId = request.getHeader("X-USER-ID");
        String roomId = request.getHeader("X-ROOM-ID");
        boolean validRequestParam = isValidRequestParam(userId, roomId);

        return HeaderRequestVO.builder()
                .valid(validRequestParam)
                .userId(userId)
                .roomId(roomId)
                .build();
    }

    private boolean isValidRequestParam(String userId, String roomId) {
        return StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(roomId);
    }
}