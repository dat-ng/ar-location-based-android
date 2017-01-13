# AR Location-based for Android
This AR app generally show where things are in the real-world by indicating where the app thinks they are over the camera view when the user holds the phone up and moves it about.

### Theoretical base
Augmented Reality will transfer real coordinates system to camera coordinates system. In AR Location-based, the real coordinate is [Geographic coordinate system] (https://en.wikipedia.org/wiki/Geographic_coordinate_system).
We will convert the GPS coordinate (Latitude, Longitude, Altitude) to Navigation coordinate (East, North, Up), then transfer Navigation coordinate to Camera coordinate and display it on camera view.

### GPS coordinate to Navigation coordinate
There are two steps:
1. Convert GPS coordinate to ECEF coordinate (Earth-centered Earth-fixed coordinate): 
2. Convert ECEF coordinate to Navigation coordinate

[Here is PDF file about convert GPS coordinate to Navigation coordinate.](http://digext6.defence.gov.au/dspace/bitstream/1947/3538/1/DSTO-TN-0432.pdf)

###ENU coordinate to Camera coordinate
