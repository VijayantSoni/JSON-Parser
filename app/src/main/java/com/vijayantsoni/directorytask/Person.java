package com.vijayantsoni.directorytask;

/**
 * Created by Vijayant Soni on 13/12/2015.
 */


//A POJO class for Person.
public class Person
{
    String fName,lName,mName,address1,address2,profilePic,city,blood,mobibe,homeNo,birth;

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getmName() {
        return mName;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getCity() {
        return city;
    }

    public String getBlood() {
        return blood;
    }

    public String getMobibe() {
        return mobibe;
    }

    public String getHomeNo() {
        return homeNo;
    }

    public String getBirth() {
        return birth;
    }

    Person(String fName,String lName,String mName,String address1,String address2
            ,String profilePic,String city,String blood,String mobibe,String homeNo,String birth)
    {
        this.fName = fName;
        this.lName = lName;
        this.mName = mName;
        this.address1 = address1;
        this.address2 = address2;
        this.profilePic = profilePic;
        this.city = city;
        this.blood = blood;
        this.mobibe = mobibe;
        this.homeNo = homeNo;
        this.birth = birth;
    }

}
