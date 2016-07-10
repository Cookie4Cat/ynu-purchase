package edu.ynu.service;

public interface TokenService {
    String getToken(String userId,String userName);
    String getUserFormToken(String token);
}
