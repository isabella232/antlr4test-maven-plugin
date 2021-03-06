/*
 [The "BSD license"]
 Copyright (c) 2014 Tom Everett
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:
 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in the
    documentation and/or other materials provided with the distribution.
 3. The name of the author may not be used to endorse or promote products
    derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.khubla.antlr.antlr4test.filestream;

import java.io.*;

import org.antlr.v4.runtime.*;

import com.khubla.antlr.antlr4test.*;

@SuppressWarnings("deprecation")
public class AntlrCaseInsensitiveFileStream extends ANTLRFileStream {
	protected char[] lookaheadData;

	public AntlrCaseInsensitiveFileStream(String fileName, String encoding, CaseInsensitiveType caseInsensitiveType) throws IOException {
		super(fileName, encoding);
		lookaheadData = new char[data.length];
		for (int i = 0; i < data.length; i++) {
			lookaheadData[i] = caseInsensitiveType == CaseInsensitiveType.None ? data[i]
					: caseInsensitiveType == CaseInsensitiveType.UPPER ? Character.toUpperCase(data[i]) : Character.toLowerCase(data[i]);
		}
	}

	@Override
	public int LA(int i) {
		if (i == 0) {
			return 0; // undefined
		}
		if (i < 0) {
			i++; // e.g., translate LA(-1) to use offset i=0; then data[p+0-1]
			if (((p + i) - 1) < 0) {
				return IntStream.EOF; // invalid; no char before first char
			}
		}
		if (((p + i) - 1) >= n) {
			return IntStream.EOF;
		}
		return lookaheadData[(p + i) - 1];
	}
}
