package com.basic_board.exception;

public class BoardNotFoundException extends RuntimeException {

    public BoardNotFoundException(Long id) {
        super("Board not found with id " + id);
    }

    public BoardNotFoundException(String message) {
        super(message);
    }
}
