package edu.ynu.dao;

public interface TokenDao {
    public String getToken(String userId,String userName);
    public String getUserIdFormToken(String token);
    public String getUserNameByUID(String uid);
}
