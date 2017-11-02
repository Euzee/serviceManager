[![Android Arsenal]( https://img.shields.io/badge/Android%20Arsenal-serviceManager-green.svg?style=flat )]( https://android-arsenal.com/details/1/6354 )
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![API](https://img.shields.io/badge/API-19%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=19)
[![CircleCI](https://circleci.com/gh/Euzee/serviceManager/tree/master.svg?style=svg)](https://circleci.com/gh/Euzee/serviceManager/tree/master)<br>
<a href="http://apptractor.ru/info/articles/interesnyie-materialyi-dlya-razrabotchika-mobilnyih-prilozheniy-187-23-29-oktyabrya.html"><img src="http://apptractor.ru/logo_trans.png" height="30" width="118" ></a> 
<br><a href="https://habrahabr.ru/post/341106/"><p>Habrahabr</a>  


# ServiceManager
A simple easy-to-use service manager for Android.
Since Google released [Android O](https://developer.android.com/about/versions/oreo/background.html#services) - hard days for service background execution are come.
You can use this library **without even declaration of your service in manifest**.
Built over JobIntentService so it is compatible with all OS versions including **Android O**.
ServiceManager can be used from any place with context only.

``` java
//No need to create classes and add manifest declarations

ServiceManager.runService(context, () -> {Logg.e(TAG,"Some Action");},true);

//if you have already prepared service you can us it as :
ServiceManager.runService(context, GeofenceTransitionsIntentService.class);

//or if you need to add some data or actions you can use it like :
 Intent geo = new Intent(context, GeofenceTransitionsIntentService.class);
 geo.setAction(GeofenceTransitionsIntentService.ACTION_REQUEST_LOCATIONS);
 ServiceManager.runService(context, geo);
```

In order to use it as last two ways - your service should extend `CompatService`
``` java
public abstract class CompatService extends JobIntentService
```

Example project could be found [here](https://github.com/Euzee/serviceManagerExample)

## Callback

ServiceCallback will be called when service will be ready to execute some job.
Just add your code in method `onHandleWork()` and that's it.
``` java
public interface ServiceCallback {
    void onHandleWork();
}
```

# Download

[ ![Download](https://api.bintray.com/packages/euzee/Libs/serviceManager/images/download.svg) ](https://bintray.com/euzee/Libs/serviceManager/_latestVersion) [![](https://jitpack.io/v/Euzee/serviceManager.svg)](https://jitpack.io/#Euzee/serviceManager)

``` groovy
repositories {
    // yo can use 
    mavenCentral() // or jcenter()
    
    //or direct link to repository or jitpack
    maven { url  "http://dl.bintray.com/euzee/Libs" } // or maven { url "https://jitpack.io" }
}
compile 'com.github.euzee:serviceManager:1.0.1'
```

# License

    Copyright 2017 Euzee, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
