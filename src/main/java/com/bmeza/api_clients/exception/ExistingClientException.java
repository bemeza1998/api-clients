package com.bmeza.api_clients.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class ExistingClientException extends RuntimeException {

  public ExistingClientException(String message) {
    super(message);
  }

}
