package flapkap.demo;

import flapkap.demo.exceptions.NotExist;
import flapkap.demo.exceptions.NotValid;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class controllerAdvisor extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value= NotExist.class)
    protected ResponseEntity<Object> accountNotExist(RuntimeException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(value= NotValid.class)
    protected ResponseEntity<Object> notValidTransaction(RuntimeException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
