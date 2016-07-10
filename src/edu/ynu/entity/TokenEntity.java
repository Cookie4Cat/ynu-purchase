package edu.ynu.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "token", schema = "purchase", catalog = "")
public class TokenEntity {
    private int tokenId;
    private String userId;
    private String token;
    private Timestamp dateline;
    private String userName;

    @Basic
    @Column(name = "user_name", nullable = false)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Id
    @Column(name = "token_id", nullable = false)
    public int getTokenId() {
        return tokenId;
    }

    public void setTokenId(int tokenId) {
        this.tokenId = tokenId;
    }

    @Basic
    @Column(name = "user_id", nullable = false)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "token", nullable = false, length = 100)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Basic
    @Column(name = "dateline", nullable = false)
    public Timestamp getDateline() {
        return dateline;
    }

    public void setDateline(Timestamp dateline) {
        this.dateline = dateline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TokenEntity that = (TokenEntity) o;

        if (tokenId != that.tokenId) return false;
        if (userId != that.userId) return false;
        if (token != null ? !token.equals(that.token) : that.token != null) return false;
        if (dateline != null ? !dateline.equals(that.dateline) : that.dateline != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tokenId;
        result = 31 * result + Integer.valueOf(userId);
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (dateline != null ? dateline.hashCode() : 0);
        return result;
    }
}
