package com.inmaytide.orbit.commons.service.configuration;

import com.inmaytide.orbit.commons.constants.Constants;
import com.inmaytide.orbit.commons.utils.CodecUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

/**
 * @author inmaytide
 * @since 2025/1/3
 */
public class InternalServiceExchangeFilter implements ExchangeFilterFunction {

    @NonNull
    @Override
    public Mono<ClientResponse> filter(@NonNull ClientRequest request, @NonNull ExchangeFunction next) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = attributes != null ? attributes.getRequest() : null;
        String chainId = httpServletRequest == null ? null : httpServletRequest.getParameter(Constants.HttpHeaderNames.CALL_CHAIN);
        if (StringUtils.isBlank(chainId)) {
            chainId = CodecUtils.randomUUID();
        }
        ClientRequest updatedRequest = ClientRequest.from(request)
                .header(Constants.HttpHeaderNames.CALL_CHAIN, chainId)
                .build();
        return next.exchange(updatedRequest);
    }

}
