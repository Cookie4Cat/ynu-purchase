package edu.ynu.service;

public interface TokenService {
    String getToken(String userId);
    String getUserFormToken(String token);
}
