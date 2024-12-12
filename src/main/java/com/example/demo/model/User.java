package com.example.demo.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.Name;

@Getter
@Setter
@Entity
@Table(name="userDetails")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "fullname",length = 255)
    private String fullName;
    private String userName;
    @Column(name = "address",length = 255)
    private String address;
    @Column(name = "phoneNo",length = 255)
    private String phoneNo;
    @Column(name = "email",length = 255)
    private String email;
    @Column(name = "password",length = 255)
    private String password;

    public int getId(){
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getUserName(){
        return userName;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getFullName(){
        return fullName;
    }
    public String getPassword(){
        return  password;
    }
    public String getAddress(){
        return  address;
    }
    public String getPhoneNo(){
        return phoneNo;
    }
}
