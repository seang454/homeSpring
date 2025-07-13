package kh.edu.istasd.fswdapi.exception;

import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handle(ResponseStatusException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", ex.getStatusCode().value());
        body.put("message", ex.getReason());
        return ResponseEntity.status(ex.getStatusCode()).body(body);
    }
    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<Object> handle(PSQLException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("sqlState", ex.getSQLState());
        body.put("message", ex.getMessage());

        HttpStatus status = mapSqlStateToHttpStatus(ex.getSQLState());

        return ResponseEntity.status(status).body(body);
    }
    private HttpStatus mapSqlStateToHttpStatus(String sqlState) {
        return switch (sqlState) {
            case "23505" -> HttpStatus.CONFLICT;       // Unique constraint violation
            case "23503" -> HttpStatus.BAD_REQUEST;    // Foreign key violation
            case "23502" -> HttpStatus.BAD_REQUEST;    // Not-null violation
            case "23514" -> HttpStatus.BAD_REQUEST;    // Check constraint
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

}

