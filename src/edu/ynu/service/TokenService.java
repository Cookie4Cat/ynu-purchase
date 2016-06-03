package edu.ynu.service;

public interface TokenService {
    public String getToken(String userId);
    public String getUserFormToken(String token);
}
