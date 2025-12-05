package main

import (
	"fmt"
	"os"
	"sort"
	"strconv"
	"strings"
)

func main() {

	dat, err := os.ReadFile("./01.in")
	if err != nil {
		panic(err)
	}

	left := []int{}
	right := []int{}

	for _, line := range strings.Split(string(dat), "\n") {
		l, r, _ := strings.Cut(line, "   ")
		lv, _ := strconv.Atoi(l)
		rv, _ := strconv.Atoi(r)

		left = append(left, lv)
		right = append(right, rv)
	}

	sort.Ints(left)
	sort.Ints(right)

	fmt.Print(left)

}
