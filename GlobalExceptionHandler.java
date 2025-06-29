public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
    List<String> errorMessages = getErrorMessages(ex);
    return createErrorResponse(errorMessages);
}

private List<String> getErrorMessages(MethodArgumentNotValidException ex) {
    // Lógica para recuperar errores del excepción
}

private ResponseEntity<Object> createErrorResponse(List<String> errorMessages) {
    // Lógica para crear una respuesta con los mensajes de error
}