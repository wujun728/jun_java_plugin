#!/usr/bin/python
#-*-coding:UTF-8 -*-
import sys

rack ={"192.168.16.135":"rack_1",
       "192.168.16.136":"rack_1",
       "192.168.16.137":"rack_2",
       "192.168.16.138":"rack_2",
       "192.168.16.139":"rack_2",
       "192.168.16.140":"rack_2",
       "192.168.16.141":"rack_2",
       "192.168.16.142":"rack_2",
       "192.168.16.143":"rack_1",
       "192.168.16.144":"rack_1",
       "dn135":"rack_1",
       "dn136":"rack_1",
       "dn137":"rack_2",
       "dn138":"rack_2",
       "dn139":"rack_2",
       "dn140":"rack_2",
       "dn141":"rack_2",
       "dn142":"rack_2",
       "dn143":"rack_1",
       "dn144":"rack_1"}

if __name__=="__main__":
        print "/" + rack.get(sys.argv[1],"rack_default")
