/*
   RF_Sniffer

   Hacked from http://code.google.com/p/rc-switch/

   by @justy to provide a handy RF code sniffer
 */

#include "RCSwitch.h"
#include<iostream>
#include <stdlib.h>
#include <stdio.h>
#include <bitset>
using namespace std;     

RCSwitch mySwitch;



int main(int argc, char *argv[]) {

	// This pin is not the first pin on the RPi GPIO header!
	// Consult https://projects.drogon.net/raspberry-pi/wiringpi/pins/
	// for more information.
	int PIN = 2;
	char command[100];
	int door_value = 0;//Normally closed When it closed it reads 1 , when it is open it reads 0

	if(wiringPiSetup() == -1)
		return 0;

	mySwitch = RCSwitch();
	mySwitch.enableReceive(PIN);  // Receiver on inerrupt 0 => that is pin #2

	while(1) {


		if (mySwitch.available()) {
			int value = mySwitch.getReceivedValue();
//			printf("value %d\n",value);
			if (value == 0) 
			{
				printf("Unknown encoding\n");
			}
			if(value == 2)
                        {
                                cout << "\tALERT DOOR State  : [YES]\n";
                                cout << "\tTriggering Camera : [YES]\n";
				system("raspivid --width 640 --height 480 --timeout 10000 -vf -hf --output /home/pi/video/vid.h264");
				system("MP4Box -add /home/pi/video/vid.h264 /home/pi/video/vid$(date +'-%m%d%y-%H%M%S').mp4");
				system("curl \-X POST \-H \"x\-pushbots\-appid\: 55d56814177959d7738b4568\" \-H \"x\-pushbots\-secret\: c35be61b3d1f9dff64c96a6e66fae97c\" \-H \"Content\-Type\: application\/json\" \-d \'\{\"platform\" \: 1 \, \"msg\" \: \"ALERT: Some One Entered ur House.\" \, \"payload\" \: \{\"article_id\"\:\"207168165150\"\}\}\' https\:\/\/api\.pushbots\.com\/push\/all");
				sleep(60);
                        }
 
			else 
			{    

//				cout << "Received :"<<mySwitch.getReceivedValue()<<endl;
				// printf("Received %s\n", mySwitch.getReceivedValue() );
				// cout << "Received \n"<< bitset <32>(mySwitch.getReceivedValue()) <<endl;
			}

			mySwitch.resetAvailable();

		}

	}
	exit(0);

}

