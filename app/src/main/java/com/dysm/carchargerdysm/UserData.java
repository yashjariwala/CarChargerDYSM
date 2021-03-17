package com.dysm.carchargerdysm;

public class UserData {
    private String userId;
    private String username;
    private String useremail;
    private String userpassword;
    private String usermobilenumber;
    private String userupiid;

    public UserData(String userId, String username, String useremail,String userpassword,String usermobilenumber,String userupiid){
    this.userId=userId;
    this.username=username;
    this.useremail=useremail;
    this.userpassword=userpassword;
    this.usermobilenumber=usermobilenumber;
    this.userupiid=userupiid;

    }
    public UserData(){

    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getUsermobilenumber() {
        return usermobilenumber;
    }

    public void setUserupiid(String userupiid) {
        this.userupiid = userupiid;
    }
}
