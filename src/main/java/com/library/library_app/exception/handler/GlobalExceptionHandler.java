package com.library.library_app.exception.handler;



import com.library.library_app.dto.response.ErrorResponse;
import com.library.library_app.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.access.AccessDeniedException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.ExpiredJwtException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    // Genel Exception Handler
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception exception) {
        ProblemDetail errorDetail;

        if (exception instanceof BadCredentialsException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());
            errorDetail.setProperty("errorMessage", "The username or password is incorrect");
        } else if (exception instanceof AccountStatusException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, exception.getMessage());
            errorDetail.setProperty("errorMessage", "The account is locked");
        } else if (exception instanceof AccessDeniedException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, exception.getMessage());
            errorDetail.setProperty("errorMessage", "You are not authorized to access this resource");
        } else if (exception instanceof SignatureException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, exception.getMessage());
            errorDetail.setProperty("errorMessage", "The JWT signature is invalid");
        } else if (exception instanceof ExpiredJwtException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, exception.getMessage());
            errorDetail.setProperty("errorMessage", "The JWT token has expired");
        } else {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
            errorDetail.setProperty("errorMessage", "Unknown internal server error.");
        }

        return errorDetail;
    }

    // CustomIllegalArgumentException Handler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomIllegalArgumentException.class)
    protected ErrorResponse handleCustomIllegalArgumentException(CustomIllegalArgumentException ex) {
        return new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.toString()); // Error code ekledim
    }

    // CustomUsernameNotFoundException Handler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CustomUsernameNotFoundException.class)
    protected ErrorResponse handleCustomUsernameNotFoundException(CustomUsernameNotFoundException ex) {
        return new ErrorResponse(ex.getMessage(),HttpStatus.NOT_FOUND.toString()); // Error code ekledim
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RoomGroupCapacityExceededException.class)
    protected  ErrorResponse handleRoomGroupCapacityExceededException(RoomGroupCapacityExceededException ex){
        return new ErrorResponse(ex.getMessage(),HttpStatus.BAD_REQUEST.toString());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RoomInvalidCapacityExceededException.class)
    protected  ErrorResponse handleRoomInvalidCapacityExceededException(RoomInvalidCapacityExceededException ex){
        return new ErrorResponse(ex.getMessage(),HttpStatus.BAD_REQUEST.toString());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RoomMaxGroupSizeExceededException.class)
    protected  ErrorResponse handleRoomMaxGroupSizeExceededException(RoomMaxGroupSizeExceededException ex){
        return new ErrorResponse(ex.getMessage(),HttpStatus.BAD_REQUEST.toString());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RoomNotFoundException.class)
    protected  ErrorResponse handleRoomNotFoundException(RoomNotFoundException ex){
        return new ErrorResponse(ex.getMessage(),HttpStatus.NOT_FOUND.toString());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EndTimeBeAfterException.class)
    protected ErrorResponse handleEndTimeBeAfterException(EndTimeBeAfterException ex){
        return new ErrorResponse(ex.getMessage(),HttpStatus.BAD_REQUEST.toString());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(StartTimeCannotException.class)
    protected ErrorResponse handleStartTimeCannotException(StartTimeCannotException ex){
        return new ErrorResponse(ex.getMessage(),HttpStatus.BAD_REQUEST.toString());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RoomAlreadyReserveTimeException.class)
    protected ErrorResponse handleRoomAlreadyReserveTimeException(RoomAlreadyReserveTimeException ex){
        return new ErrorResponse(ex.getMessage(),HttpStatus.BAD_REQUEST.toString());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PastReservationException.class)
    protected ErrorResponse handlePastReservationException(PastReservationException ex){
        return new ErrorResponse(ex.getMessage(),HttpStatus.BAD_REQUEST.toString());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RoomReservationNotFoundException.class)
    protected ErrorResponse handleRoomReservationNotFoundException(RoomReservationNotFoundException ex){
        return new ErrorResponse(ex.getMessage(),HttpStatus.BAD_REQUEST.toString());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FutureReservationLimitExceededException.class)
    protected ErrorResponse handleFutureReservationLimitExceededException(FutureReservationLimitExceededException ex){
        return new ErrorResponse(ex.getMessage(),HttpStatus.BAD_REQUEST.toString());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserHasActiveReservationException.class)
    protected ErrorResponse handleUserHasActiveReservationException(UserHasActiveReservationException ex){
        return new ErrorResponse(ex.getMessage(),HttpStatus.BAD_REQUEST.toString());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidPeriodNumberException.class)
    protected ErrorResponse handleInvalidPeriodNumberException(InvalidPeriodNumberException ex){
        return new ErrorResponse(ex.getMessage(),HttpStatus.BAD_REQUEST.toString());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(UserNotAuthorizedException .class)
    protected ErrorResponse handleUserNotAuthorizedException (UserNotAuthorizedException  ex){
        return new ErrorResponse(ex.getMessage(),HttpStatus.FORBIDDEN.toString());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BookNotFoundException.class)
    protected ErrorResponse handleEntityNotFoundException (BookNotFoundException ex) {
        return new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.toString());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MaxBooksExceededException.class)
    protected ErrorResponse handleMaxBooksExceededException(MaxBooksExceededException ex) {
        return new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.toString());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BookAvailabilityNotFoundException.class)
    protected ErrorResponse handleBookAvailabilityNotFoundException(BookAvailabilityNotFoundException ex) {
        return new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.toString());
    }
}
