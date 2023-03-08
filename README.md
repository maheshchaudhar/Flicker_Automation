Flickr_Automation



Autmation Framwork Architecture,

<img width="732" alt="ArchitectureDig" src="https://user-images.githubusercontent.com/11026791/223644777-415d663a-e588-4a1e-95b0-e63e2e1a33d1.png">


Steps To Run Mobile Automation
1. Start appium server, make sure you have setup ready for ios simulators.
2. Update application properties file, details for all parameters are provided as below.
  Appium
    1. serverURL=http://localhost:4723/wd/hub

  Platform Sepecific
    1. platform=iOS [Platform on which you want to run automation]
    2. platformVersion =15.5
    automationName =XCUITest
    app =/Users/maheshchaudhar/Desktop/Personal/MyFlickr.app
    deviceName=iPhone 8

  Timeouts
    implicitWait=20
    defaultTimeout=20


Improvements For Framwork [Needs to implemented when time permits] 
1. We can enable video recording for execution, this will help to debug failures easily.
2. We can add Visual validation for mobile application pages Image plugin which is recently added in Appium 2.0.
