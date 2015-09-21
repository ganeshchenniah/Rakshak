/*
  Example for different sending methods

  http://code.google.com/p/rc-switch/

*/

#include <RCSwitch.h>
//#include <dht.h>

//dht DHT;
//#define DHT11_PIN 3
const int switchPin = 2;     //     // Reed switch to digital pin 2

RCSwitch mySwitch = RCSwitch();

unsigned long status_door,modified_val;
void setup() {

  Serial.begin(115200);

  // Transmitter is connected to Arduino Pin #10
  mySwitch.enableTransmit(10);
  pinMode(switchPin, INPUT);        // switchPin is an input
  digitalWrite(switchPin, HIGH);    // Activate internal pullup resistor

  // Optional set pulse length.
  // mySwitch.setPulseLength(320);

  // Optional set protocol (default is 1, will work for most outlets)
  // mySwitch.setProtocol(2);

  // Optional set number of transmission repetitions.
  // mySwitch.setRepeatTransmit(15);

}

void loop() {

  
  Serial.println(digitalRead(switchPin));  // Display current value
  delay(200);
  status_door = digitalRead(switchPin); // We are using NO switch by default 1 will be there , when contact happens it becomes 0


  if(status_door == 0)
   {
      modified_val = 1; //Closed
   }
   else
   {
      modified_val = 2; // Open
   }
    
  mySwitch.send(modified_val, 24);
  delay(500);
  /* See Example: TypeA_WithDIPSwitches */
 
  /*
  mySwitch.switchOn("11111", "00010");
  delay(1000);
  mySwitch.switchOn("11111", "00010");
  delay(1000);
 /*

   

  
  
  /* Same switch as above, but using binary code */
 /*
  mySwitch.send(hum,4);
  delay(1000);
  mySwitch.send(temp,4);
  delay(1000);
  */
  /* Same switch as above, but tri-state code */
 /* mySwitch.sendTriState("00000FFF0F0F");
  delay(1000);
  mySwitch.sendTriState("00000FFF0FF0");
  delay(1000);
*/
  //delay(1000);
}

