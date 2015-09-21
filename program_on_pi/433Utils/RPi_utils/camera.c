#include <stdio.h>
#include <string.h>

int main ()
{
   char command[50];

        int temperature;
        temperature = system("raspivid --width 640 --height 480 --timeout 10000 -vf -hf --output /home/pi/video/vid.h264");
        temperature = system("MP4Box -add /home/pi/video/vid.h264 /home/pi/video/vid$(date +'-%m%d%y-%H%M%S').mp4");

//   system("curl \-X POST \-H \"x\-pushbots\-appid\: 55d56814177959d7738b4568\" \-H \"x\-pushbots\-secret\: c35be61b3d1f9dff64c96a6e66fae97c\" \-H \"Content\-Type\: application\/json\" \-d \'\{\"platform\" \: 1 \, \"msg\" \: \"ALERT: Some One Entered ur House.\" \, \"payload\" \: \{\"article_id\"\:\"207168165150\"\}\}\' https\:\/\/api\.pushbots\.com\/push\/all");

   return(0);
}

