package com.hyl.services.nacosuser.demo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.*;

import javax.naming.Name;

@Entry(objectClasses = "inetOrgPerson,top,person,organizationalPerson")
public class Person {
    @Id
    @JsonIgnore
    private Name id;
//    @DnAttribute(value = "uid")
    private String uid;
    @Attribute(name = "cn")
    private String cn;
    @Attribute(name = "sn")
    private String sn;
    @Attribute(name="mail")
    private String mail;
    @Attribute(name = "homedirectory")
    private String homedirectory;
    @Attribute(name = "gidnumber")
    private String gidnumber;
    @Attribute(name = "uidnumber")
    private String uidnumber;

    @Attribute(name = "userPassword")
    private byte[] password;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                ", cn='" + cn + '\'' +
                ", sn='" + sn + '\'' +
                ", mail='" + mail + '\'' +
                ", homedirectory='" + homedirectory + '\'' +
                ", gidnumber='" + gidnumber + '\'' +
                ", uidnumber='" + uidnumber + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Name getId() {
        return id;
    }

    public void setId(Name id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getHomedirectory() {
        return homedirectory;
    }

    public void setHomedirectory(String homedirectory) {
        this.homedirectory = homedirectory;
    }

    public String getGidnumber() {
        return gidnumber;
    }

    public void setGidnumber(String gidnumber) {
        this.gidnumber = gidnumber;
    }

    public String getUidnumber() {
        return uidnumber;
    }

    public void setUidnumber(String uidnumber) {
        this.uidnumber = uidnumber;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }
}
