package backend

import (
	"log"
	"runtime/debug"
)

func checkErr(err error) {
	if err != nil {
		log.Panicln(err.Error())
	}
}

func logErr(err error) {
	if err != nil {
		debug.PrintStack()
		log.Println(err.Error())
	}
}
