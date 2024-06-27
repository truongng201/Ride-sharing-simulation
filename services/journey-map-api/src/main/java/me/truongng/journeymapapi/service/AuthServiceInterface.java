package me.truongng.journeymapapi.service;

interface AuthServiceInterface {
    public Boolean signin();

    public Boolean signup();

    public Boolean signout();

    public Boolean resetPassword();

    public Boolean forgotPassword();
}
