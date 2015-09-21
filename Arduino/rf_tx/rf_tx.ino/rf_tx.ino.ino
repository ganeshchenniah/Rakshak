/*
  Example for different sending methods

  http://code.google.com/p/rc-switch/

*/

#include <RCSwitch.h>
#include <dht.h>

dht DHT;
#define DHT11_PIN 2

RCSwitch mySwitch = RCSwitch();

unsigned long temp,hum;
void setup() {

  Serial.begin(115200);

  // Transmitter is connected to Arduino Pin #10
  mySwitch.enableTransmit(10);

  // Optional set pulse length.
  // mySwitch.setPulseLength(320);

  // Optional set protocol (default is 1, will work for most outlets)
  // mySwitch.setProtocol(2);

  // Optional set number of transmission repetitions.
  // mySwitch.setRepeatTransmit(15);

}

void loop() {

  // READ DATA
 // Serial.print("DHT11, \t");
  int chk = DHT.read11(DHT11_PIN);
  switch (chk)
  {
    case DHTLIB_OK:  
  //  Serial.print("OK,\t"); 
    break;
    case DHTLIB_ERROR_CHECKSUM: 
    Serial.print("Checksum error,\t"); 
    break;
    case DHTLIB_ERROR_TIMEOUT: 
    Serial.print("Time out error,\t"); 
    break;
    case DHTLIB_ERROR_CONNECT:
        Serial.print("Connect error,\t");
        break;
    case DHTLIB_ERROR_ACK_L:
        Serial.print("Ack Low error,\t");
        break;
    case DHTLIB_ERROR_ACK_H:
        Serial.print("Ack High error,\t");
        break;
    default: 
    Serial.print("Unknown error,\t"); 
    break;
  }
  // DISPLAY DATA
//  Serial.print(DHT.humidity, 1);
//  Serial.print(",\t");
 // Serial.println(DHT.temperature, 1);
 /*
  temp = (long)DHT.temperature;
  hum = (long)DHT.humidity;
  Serial.print(hum, 1);
  Serial.print(",\t");
  Serial.println(temp, 1);
  delay(2000);
*/
  /* See Example: TypeA_WithDIPSwitches */
 
  /*
  mySwitch.switchOn("11111", "00010");
  delay(1000);
  mySwitch.switchOn("11111", "00010");
  delay(1000);
 /*

   

  /* Same switch as above, but using decimal code */
  temp = (long)DHT.temperature;
  hum = (long)DHT.humidity;
  
  mySwitch.send(temp, 24);
  delay(1000);
  mySwitch.send(hum, 24);
  delay(1000);
  
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
  delay(1000);
}

