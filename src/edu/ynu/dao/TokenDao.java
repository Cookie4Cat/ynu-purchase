package edu.ynu.dao;

public interface TokenDao {
    public String getToken(String userId);
    public String getUserIdFormToken(String token);
}
