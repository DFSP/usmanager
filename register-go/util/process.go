/*
 * MIT License
 *
 * Copyright (c) 2020 usmanager
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package util

import (
	"strings"

	"github.com/mitchellh/go-ps"
	"github.com/usmanager/manager/register-go/reglog"
)

// FindProcess : Find system process
func FindProcess(key string) (int, string, bool) {
	pname := ""
	pid := 0
	found := false
	ps, _ := ps.Processes()
	for i := range ps {
		reglog.Logger.Debugf("Process #%d in list -> PID: %d; Name: %s", i, ps[i].Pid(), ps[i].Executable())
		if strings.Contains(ps[i].Executable(), key) {
			pid = ps[i].Pid()
			pname = ps[i].Executable()
			found = true
			break
		}
	}
	return pid, pname, found
}
