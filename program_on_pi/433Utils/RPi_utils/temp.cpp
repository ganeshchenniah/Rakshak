#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include<iostream>
int main( int argc, char *argv[] )
{
  FILE *fp;
  char file_type[40];
  char str[40];
  int temp;

while(1)
{
  fp = popen("/opt/vc/bin/vcgencmd measure_temp", "r");
  if (fp == NULL) {
      printf("Failed to run command\n" );
      exit -1;
  }

  while (fgets(file_type, sizeof(file_type), fp) != NULL) {
      printf("%s", file_type);
  }
//	printf("%c",file_type[5]);
//	printf("%c",file_type[6]);
	if(file_type[5] == '6')
	{
		printf("Temp Exceeded 60deg\n");
		system("curl \-X POST \-H \"x\-pushbots\-appid\: 55d56814177959d7738b4568\" \-H \"x\-pushbots\-secret\: c35be61b3d1f9dff64c96a6e66fae97c\" \-H \"Content\-Type\: application\/json\" \-d \'\{\"platform\" \: 1 \, \"msg\" \: \"ALERT: Temp Over 60. Turn OFF\" \, \"payload\" \: \{\"article_id\"\:\"207168165150\"\}\}\' https\:\/\/api\.pushbots\.com\/push\/all");
		
		sleep(7200);
		
	}
	else if(file_type[5] == '7')
	{
		printf("Temp Exceeded 70deg\n");
		system("curl \-X POST \-H \"x\-pushbots\-appid\: 55d56814177959d7738b4568\" \-H \"x\-pushbots\-secret\: c35be61b3d1f9dff64c96a6e66fae97c\" \-H \"Content\-Type\: application\/json\" \-d \'\{\"platform\" \: 1 \, \"msg\" \: \"ALERT: Temp Over 70. Powering OFF..\" \, \"payload\" \: \{\"article_id\"\:\"207168165150\"\}\}\' https\:\/\/api\.pushbots\.com\/push\/all");
		system("poweroff");
		
	}
	else
	{
		printf("Sleeping For 10 Sec....");
		sleep(7200);
	}
}
  pclose(fp);

  return 0;
}

