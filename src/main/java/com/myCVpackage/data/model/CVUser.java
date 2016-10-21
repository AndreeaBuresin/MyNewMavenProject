package com.myCVpackage.data.model;

import javax.persistence.*;

/**
 * Created by buresina on 21/10/2016.
 */

@Entity
@Table(name = "cvuser")
public class CVUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    String cvTitle;
    String cvContent;
    int userId;

    public String getCvTitle() {
        return cvTitle;
    }

    public void setCvTitle(String cvTitle) {
        this.cvTitle = cvTitle;
    }

    public String getCvContent() {
        return cvContent;
    }

    public void setCvContent(String cvContent) {
        this.cvContent = cvContent;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CVUser)) return false;

        CVUser cvUser = (CVUser) o;

        if (userId != cvUser.userId) return false;
        if (cvTitle != null ? !cvTitle.equals(cvUser.cvTitle) : cvUser.cvTitle != null) return false;
        return cvContent != null ? cvContent.equals(cvUser.cvContent) : cvUser.cvContent == null;

    }

    @Override
    public int hashCode() {
        int result = cvTitle != null ? cvTitle.hashCode() : 0;
        result = 31 * result + (cvContent != null ? cvContent.hashCode() : 0);
        result = 31 * result + userId;
        return result;
    }

    @Override
    public String toString() {
        return "CVUser{" +
                "cvTitle='" + cvTitle + '\'' +
                ", cvContent='" + cvContent + '\'' +
                ", userId=" + userId +
                '}';
    }
}
