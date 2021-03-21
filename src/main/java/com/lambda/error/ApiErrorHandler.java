package com.lambda.error;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@Log4j2
@RestControllerAdvice(annotations = {RestController.class})
@SuppressWarnings("deprecation")
public class ApiErrorHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleAllException(Exception ex, WebRequest request) {
        // quá trình kiểm soat lỗi diễn ra ở đây
        log.error(ex);
        return new ApiError(9999, ex.getLocalizedMessage());
    }

    /**
     * IndexOutOfBoundsException sẽ được xử lý riêng tại đây
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiError handleBusinessException(BusinessException ex, WebRequest request) {
        return new ApiError(ex.getCode(), ex.getLocalizedMessage());
    }

    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiError handleInvalidTokenException(InvalidTokenException ex, WebRequest request) {
        return new ApiError(2000, ex.getLocalizedMessage());
    }

    @ExceptionHandler(InvalidBearerTokenException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiError handleInvalidTokenException(InvalidBearerTokenException ex, WebRequest request) {
        return new ApiError(2000, ex.getLocalizedMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ApiError handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        return new ApiError(1500, ex.getLocalizedMessage());
    }
}
