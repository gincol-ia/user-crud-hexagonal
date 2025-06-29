public User createUser(User newUser) {
    validateUser(newUser);
    User createdUser = createNewUser(newUser);
    saveToDatabase(createdUser);
    return createdUser;
}

private void validateUser(User newUser) {
    // Lógica de validación aquí
}

private User createNewUser(User newUser) {
    // Lógica para crear un nuevo objeto usuario
}

private void saveToDatabase(User user) {
    // Guardar el usuario en la base de datos
}