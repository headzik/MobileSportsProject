package fh.ooe.mc.mobilesportsapp;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

import android.app.Application;
 
public class ParseApplication extends Application {
 
    @Override
    public void onCreate() {
        super.onCreate();
        
        Parse.enableLocalDatastore(this);
        
        // Add your initialization code here
        Parse.initialize(this, "lflIbMjfPWl23Hdqj6Z7EMjuqBix8YreTIwNkJLh", "cTLgLxd1ybMeH5roNY6a83xDyUHEaVzyp9KMwNC0");
 
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
 
        // If you would like all objects to be private by default, remove this
        // line.
        defaultACL.setPublicReadAccess(true);
 
        ParseACL.setDefaultACL(defaultACL, true);
    }
 
}