
# mobile-web

Simple mobile app consisting of a web view container of https://www.brainup.site

To run the app simply sync and run de developDebug build variant of the app.

To deploy to the app store get a **keystore.properties** file with the required configuration along with the **keystore** to generate a signed release file.

Sample **keystore.properties** file:

    storeFile=[/path/to/keystore.jks]
    storePassword=[password]  
    keyAlias=[key alias]  
    keyPassword=[password]  

to build a bundle -> **./gradlew bundleProdRelease**
apk -> **./gradlew assembleProdRelease**

Contact:
https://t.me/+c5-JlYtQNcNmZjUy dev chat
