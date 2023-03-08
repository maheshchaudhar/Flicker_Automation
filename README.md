Flickr_Automation



Autmation Framwork Architecture,

<img width="732" alt="ArchitectureDig" src="https://user-images.githubusercontent.com/11026791/223644777-415d663a-e588-4a1e-95b0-e63e2e1a33d1.png">


Steps To Run Mobile Automation
1. Start appium server, make sure you have setup ready for ios simulators.
2. Update application properties file, details for all parameters are provided as below.
3. Build maven project with goal "mvn test"
4. Test will get executed and testng reports will be available in "test-output"
5. Allure report : "mvn allure:serve"

Application Properties,
      
      * serverURL=http://localhost:4723/wd/hub    [Appium Server URL]
      * platform=iOS [Platform on which you want to run automation]
      * platformVersion =15.5. [Platform Version]
      * automationName =XCUITest  [Automation Name]
      * app =/Users/maheshchaudhar/Desktop/Personal/MyFlickr.app. [Application file path]
      * deviceName=iPhone 8 [Device name]
      * implicitWait=20   [Implicit Wait in Seconds]
      * defaultTimeout=20   [Explicit Wait in Seconds]
      
      **Example For iOS
      platform=iOS
      platformVersion =15.5
      automationName =XCUITest
      app =/Users/maheshchaudhar/Desktop/Personal/MyFlickr.app
      deviceName=iPhone 8

      **Example For Android
      platform=android
      app =/Users/maheshchaudhar/Desktop/Personal/app-debug.apk
      deviceName=emulator-5554
      appPackage=com.example.imagegallery
      automationName=UiAutomator2


Improvements For Framwork [Needs to implemented when time permits] 
1. Test data need to be handled seperately using JSON/XML/Excel files.
2. We can enable video recording for execution, this will help to debug failures easily. 
4. We can add Visual validation for mobile application pages Image plugin which is recently added in Appium 2.0.
5. Logging and documentation needs to improved. 
