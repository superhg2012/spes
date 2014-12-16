package org.spes.bean;

import java.util.HashSet;
import java.util.Set;


/**
 * Post entity. @author MyEclipse Persistence Tools
 */

public class Post  implements java.io.Serializable {


    // Fields    

     private Integer postId;
     private String postName;
     private Boolean enabled;
     private String postDesc;
     private String backup1;
     private String backup2;
     private String backup3;
     private String backup4;
     private String backup5;
     private Set users = new HashSet(0);


    // Constructors

    /** default constructor */
    public Post() {
    }

    
    /** full constructor */
    public Post(String postName, Boolean enabled, String postDesc, String backup1, String backup2, String backup3, String backup4, String backup5, Set users) {
        this.postName = postName;
        this.enabled = enabled;
        this.postDesc = postDesc;
        this.backup1 = backup1;
        this.backup2 = backup2;
        this.backup3 = backup3;
        this.backup4 = backup4;
        this.backup5 = backup5;
        this.users = users;
    }

   
    // Property accessors

    public Integer getPostId() {
        return this.postId;
    }
    
    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getPostName() {
        return this.postName;
    }
    
    public void setPostName(String postName) {
        this.postName = postName;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getPostDesc() {
        return this.postDesc;
    }
    
    public void setPostDesc(String postDesc) {
        this.postDesc = postDesc;
    }

    public String getBackup1() {
        return this.backup1;
    }
    
    public void setBackup1(String backup1) {
        this.backup1 = backup1;
    }

    public String getBackup2() {
        return this.backup2;
    }
    
    public void setBackup2(String backup2) {
        this.backup2 = backup2;
    }

    public String getBackup3() {
        return this.backup3;
    }
    
    public void setBackup3(String backup3) {
        this.backup3 = backup3;
    }

    public String getBackup4() {
        return this.backup4;
    }
    
    public void setBackup4(String backup4) {
        this.backup4 = backup4;
    }

    public String getBackup5() {
        return this.backup5;
    }
    
    public void setBackup5(String backup5) {
        this.backup5 = backup5;
    }

    public Set getUsers() {
        return this.users;
    }
    
    public void setUsers(Set users) {
        this.users = users;
    }

}