#!/bin/sh
raspivid -o - -t 99999 -w 640 -h 360 -fps 20 |cvlc stream:///dev/stdin --sout '#standard{access=http,mux=ts,dst=:8090}' :demux=h264
