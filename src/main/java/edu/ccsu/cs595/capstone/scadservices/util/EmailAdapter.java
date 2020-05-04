package edu.ccsu.cs595.capstone.scadservices.util;


public interface EmailAdapter {
    void sendEmail(SCADMail mail) throws RuntimeException;
}
