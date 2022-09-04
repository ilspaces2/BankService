package bank.controller;

import bank.model.User;
import bank.service.SimpleBankService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Метод, помеченный как @ExceptionHandler, поддерживает внедрение аргументов и
 * возвращаемого типа в рантайме, указанных в
 * <a href="https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/ExceptionHandler.html">спецификации</a>.
 * По этому мы можем внедрить запрос, ответ и само исключение, чтобы прописать какую-либо логику.
 * <p>
 * В данном случае при возникновении исключения IllegalArgumentException,
 * метод exceptionHandler() отлавливает его и меняет ответ, а именно его статус и тело.
 * Также в последней строке происходит логгирование.
 * <p>
 * Недостаток этого подхода в том, что он работает только для класса,
 * в котором он используется, поэтому его стоит использовать только
 * для узко специфичных исключений, если Вы уверены, что в другом
 * классе они не возникнут или их нужно будет обработать по другому.
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class.getSimpleName());

    private final SimpleBankService simpleBankService;

    private final ObjectMapper objectMapper;

    @PostMapping("")
    public User save(@RequestBody Map<String, String> body) {
        var username = body.get("username");
        var password = body.get("password");
        if (username == null || password == null) {
            throw new NullPointerException("Username and password mustn't be empty");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("Invalid password. Password length must be more than 5 characters.");
        }
        var user = new User(username, password);
        simpleBankService.addUser(user);
        return user;
    }

    @GetMapping("")
    public User findByPassport(@RequestParam String password) {
        return simpleBankService.findByPassport(password).orElse(null);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public void exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() {
            {
                put("message", e.getMessage());
                put("type", e.getClass());
            }
        }));
        LOGGER.error(e.getLocalizedMessage());
    }
}
