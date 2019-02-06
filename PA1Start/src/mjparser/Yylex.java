/*
  mj.lex
    Tokens/Symbols for MeggyJava language.
    NO dollars, but underscores anywhere in identifiers 
    (to avoid problems later when generating AVR labels from id-s)
    8 colors (not all the CPP colors)
    The values for the colors, buttons, and tones were found in
    MeggyJrSimple.h and MeggyJrSimple.cpp.
    Ignore single line comments: // until eol 
    ALSO: ignore C style comments, see http://ostermiller.org/findcomment.html
    Wim Bohm and Michelle Strout, 6/2011
*/
package mjparser;
import java_cup.runtime.Symbol;


public class Yylex implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yychar;
	private int yyline;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	public Yylex (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	public Yylex (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Yylex () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yychar = 0;
		yyline = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;
	}

	private boolean yy_eof_done = false;
	private final int YYINITIAL = 0;
	private final int yy_state_dtrans[] = {
		0
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		int i;
		for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
			if ('\n' == yy_buffer[i] && !yy_last_was_cr) {
				++yyline;
			}
			if ('\r' == yy_buffer[i]) {
				++yyline;
				yy_last_was_cr=true;
			} else yy_last_was_cr=false;
		}
		yychar = yychar
			+ yy_buffer_index - yy_buffer_start;
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NOT_ACCEPT,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NOT_ACCEPT,
		/* 88 */ YY_NO_ANCHOR,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NOT_ACCEPT,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NOT_ACCEPT,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NOT_ACCEPT,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NOT_ACCEPT,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NOT_ACCEPT,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NOT_ACCEPT,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NOT_ACCEPT,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NOT_ACCEPT,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NOT_ACCEPT,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NOT_ACCEPT,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NOT_ACCEPT,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NOT_ACCEPT,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NOT_ACCEPT,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NOT_ACCEPT,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NOT_ACCEPT,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NOT_ACCEPT,
		/* 121 */ YY_NO_ANCHOR,
		/* 122 */ YY_NOT_ACCEPT,
		/* 123 */ YY_NO_ANCHOR,
		/* 124 */ YY_NOT_ACCEPT,
		/* 125 */ YY_NO_ANCHOR,
		/* 126 */ YY_NOT_ACCEPT,
		/* 127 */ YY_NO_ANCHOR,
		/* 128 */ YY_NOT_ACCEPT,
		/* 129 */ YY_NO_ANCHOR,
		/* 130 */ YY_NOT_ACCEPT,
		/* 131 */ YY_NOT_ACCEPT,
		/* 132 */ YY_NOT_ACCEPT,
		/* 133 */ YY_NOT_ACCEPT,
		/* 134 */ YY_NOT_ACCEPT,
		/* 135 */ YY_NOT_ACCEPT,
		/* 136 */ YY_NOT_ACCEPT,
		/* 137 */ YY_NOT_ACCEPT,
		/* 138 */ YY_NOT_ACCEPT,
		/* 139 */ YY_NOT_ACCEPT,
		/* 140 */ YY_NOT_ACCEPT,
		/* 141 */ YY_NOT_ACCEPT,
		/* 142 */ YY_NOT_ACCEPT,
		/* 143 */ YY_NOT_ACCEPT,
		/* 144 */ YY_NOT_ACCEPT,
		/* 145 */ YY_NOT_ACCEPT,
		/* 146 */ YY_NOT_ACCEPT,
		/* 147 */ YY_NOT_ACCEPT,
		/* 148 */ YY_NOT_ACCEPT,
		/* 149 */ YY_NOT_ACCEPT,
		/* 150 */ YY_NOT_ACCEPT,
		/* 151 */ YY_NOT_ACCEPT,
		/* 152 */ YY_NOT_ACCEPT,
		/* 153 */ YY_NOT_ACCEPT,
		/* 154 */ YY_NOT_ACCEPT,
		/* 155 */ YY_NOT_ACCEPT,
		/* 156 */ YY_NOT_ACCEPT,
		/* 157 */ YY_NOT_ACCEPT,
		/* 158 */ YY_NOT_ACCEPT,
		/* 159 */ YY_NOT_ACCEPT,
		/* 160 */ YY_NOT_ACCEPT,
		/* 161 */ YY_NOT_ACCEPT,
		/* 162 */ YY_NOT_ACCEPT,
		/* 163 */ YY_NOT_ACCEPT,
		/* 164 */ YY_NOT_ACCEPT,
		/* 165 */ YY_NOT_ACCEPT,
		/* 166 */ YY_NOT_ACCEPT,
		/* 167 */ YY_NOT_ACCEPT,
		/* 168 */ YY_NOT_ACCEPT,
		/* 169 */ YY_NOT_ACCEPT,
		/* 170 */ YY_NOT_ACCEPT,
		/* 171 */ YY_NOT_ACCEPT,
		/* 172 */ YY_NOT_ACCEPT,
		/* 173 */ YY_NOT_ACCEPT,
		/* 174 */ YY_NOT_ACCEPT,
		/* 175 */ YY_NOT_ACCEPT,
		/* 176 */ YY_NOT_ACCEPT,
		/* 177 */ YY_NOT_ACCEPT,
		/* 178 */ YY_NOT_ACCEPT,
		/* 179 */ YY_NOT_ACCEPT,
		/* 180 */ YY_NOT_ACCEPT,
		/* 181 */ YY_NOT_ACCEPT,
		/* 182 */ YY_NOT_ACCEPT,
		/* 183 */ YY_NOT_ACCEPT,
		/* 184 */ YY_NOT_ACCEPT,
		/* 185 */ YY_NOT_ACCEPT,
		/* 186 */ YY_NOT_ACCEPT,
		/* 187 */ YY_NOT_ACCEPT,
		/* 188 */ YY_NOT_ACCEPT,
		/* 189 */ YY_NOT_ACCEPT,
		/* 190 */ YY_NOT_ACCEPT,
		/* 191 */ YY_NOT_ACCEPT,
		/* 192 */ YY_NOT_ACCEPT,
		/* 193 */ YY_NOT_ACCEPT,
		/* 194 */ YY_NOT_ACCEPT,
		/* 195 */ YY_NOT_ACCEPT,
		/* 196 */ YY_NOT_ACCEPT,
		/* 197 */ YY_NOT_ACCEPT,
		/* 198 */ YY_NOT_ACCEPT,
		/* 199 */ YY_NOT_ACCEPT,
		/* 200 */ YY_NOT_ACCEPT,
		/* 201 */ YY_NO_ANCHOR,
		/* 202 */ YY_NOT_ACCEPT,
		/* 203 */ YY_NOT_ACCEPT,
		/* 204 */ YY_NOT_ACCEPT,
		/* 205 */ YY_NOT_ACCEPT,
		/* 206 */ YY_NOT_ACCEPT,
		/* 207 */ YY_NOT_ACCEPT,
		/* 208 */ YY_NOT_ACCEPT,
		/* 209 */ YY_NOT_ACCEPT,
		/* 210 */ YY_NOT_ACCEPT,
		/* 211 */ YY_NOT_ACCEPT,
		/* 212 */ YY_NOT_ACCEPT,
		/* 213 */ YY_NOT_ACCEPT,
		/* 214 */ YY_NOT_ACCEPT,
		/* 215 */ YY_NOT_ACCEPT,
		/* 216 */ YY_NOT_ACCEPT,
		/* 217 */ YY_NOT_ACCEPT,
		/* 218 */ YY_NOT_ACCEPT,
		/* 219 */ YY_NO_ANCHOR,
		/* 220 */ YY_NOT_ACCEPT,
		/* 221 */ YY_NOT_ACCEPT,
		/* 222 */ YY_NOT_ACCEPT,
		/* 223 */ YY_NOT_ACCEPT,
		/* 224 */ YY_NOT_ACCEPT,
		/* 225 */ YY_NOT_ACCEPT,
		/* 226 */ YY_NO_ANCHOR,
		/* 227 */ YY_NO_ANCHOR,
		/* 228 */ YY_NO_ANCHOR,
		/* 229 */ YY_NO_ANCHOR,
		/* 230 */ YY_NO_ANCHOR,
		/* 231 */ YY_NO_ANCHOR,
		/* 232 */ YY_NO_ANCHOR,
		/* 233 */ YY_NO_ANCHOR,
		/* 234 */ YY_NO_ANCHOR,
		/* 235 */ YY_NO_ANCHOR,
		/* 236 */ YY_NO_ANCHOR,
		/* 237 */ YY_NO_ANCHOR,
		/* 238 */ YY_NO_ANCHOR,
		/* 239 */ YY_NO_ANCHOR,
		/* 240 */ YY_NO_ANCHOR,
		/* 241 */ YY_NO_ANCHOR,
		/* 242 */ YY_NO_ANCHOR,
		/* 243 */ YY_NO_ANCHOR,
		/* 244 */ YY_NO_ANCHOR,
		/* 245 */ YY_NOT_ACCEPT,
		/* 246 */ YY_NOT_ACCEPT,
		/* 247 */ YY_NO_ANCHOR,
		/* 248 */ YY_NO_ANCHOR,
		/* 249 */ YY_NO_ANCHOR,
		/* 250 */ YY_NO_ANCHOR,
		/* 251 */ YY_NO_ANCHOR,
		/* 252 */ YY_NO_ANCHOR,
		/* 253 */ YY_NO_ANCHOR,
		/* 254 */ YY_NO_ANCHOR,
		/* 255 */ YY_NO_ANCHOR,
		/* 256 */ YY_NO_ANCHOR,
		/* 257 */ YY_NO_ANCHOR,
		/* 258 */ YY_NO_ANCHOR,
		/* 259 */ YY_NO_ANCHOR,
		/* 260 */ YY_NO_ANCHOR,
		/* 261 */ YY_NO_ANCHOR,
		/* 262 */ YY_NO_ANCHOR,
		/* 263 */ YY_NO_ANCHOR,
		/* 264 */ YY_NO_ANCHOR,
		/* 265 */ YY_NOT_ACCEPT,
		/* 266 */ YY_NO_ANCHOR,
		/* 267 */ YY_NO_ANCHOR,
		/* 268 */ YY_NO_ANCHOR,
		/* 269 */ YY_NO_ANCHOR,
		/* 270 */ YY_NO_ANCHOR,
		/* 271 */ YY_NO_ANCHOR,
		/* 272 */ YY_NO_ANCHOR,
		/* 273 */ YY_NO_ANCHOR,
		/* 274 */ YY_NO_ANCHOR,
		/* 275 */ YY_NO_ANCHOR,
		/* 276 */ YY_NO_ANCHOR,
		/* 277 */ YY_NO_ANCHOR,
		/* 278 */ YY_NO_ANCHOR,
		/* 279 */ YY_NO_ANCHOR,
		/* 280 */ YY_NO_ANCHOR,
		/* 281 */ YY_NO_ANCHOR,
		/* 282 */ YY_NO_ANCHOR,
		/* 283 */ YY_NO_ANCHOR,
		/* 284 */ YY_NO_ANCHOR,
		/* 285 */ YY_NO_ANCHOR,
		/* 286 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"64:9,71,65,64,71,70,64:18,71,59,64:4,49,64,53,54,52,50,60,51,5,63,66,67:2,4" +
"2,67:6,64,57,58,48,64:3,12,22,6,31,15,43,14,30,20,69,36,17,1,13,10,41,69,11" +
",38,21,37,19,18,69,16,69,61,64,62,64,68,64,39,46,28,45,2,33,3,27,26,69,29,8" +
",44,25,7,40,69,9,34,24,23,47,32,35,4,69,55,64,56,64:2,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,287,
"0,1,2,1,3,4,5,1:15,6,1:2,7,6:19,8,9,1:2,10,1:31,11,12,1,13,14,1,15,16,1,17," +
"18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42," +
"43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67," +
"68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92," +
"93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,11" +
"3,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,1" +
"32,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,149,150," +
"151,152,153,154,155,156,157,158,159,160,161,162,163,164,165,166,167,168,169" +
",170,171,172,173,174,175,176,177,178,179,180,181,182,183,184,185,186,187,18" +
"8,189,190,191,192,193,194,195,6,196,197,198,199,200,201,202,203,204,205,206" +
",6,207,208,209,210,211")[0];

	private int yy_nxt[][] = unpackFromString(212,72,
"1,2,244,281:2,3,281:2,282,283,281:14,247,201,82,281,264,281:3,266,267,284,2" +
"81:3,285,281,286,281,4,281,248,281,249,250,5,6,7,8,9,10,11,12,13,14,15,16,1" +
"7,18,19,84,89,20,83,4,281:2,85,21,-1:73,281,268,281:2,-1,281:36,269,281:5,-" +
"1:18,269:3,281,-1:44,4,-1:23,4:2,-1:52,23,-1:72,24,-1:23,281:4,-1,281:36,26" +
"9,281:5,-1:18,269:3,281,-1:3,25:64,-1,25:4,-1,25,-1:5,137,-1:71,142,-1:71,1" +
"64,-1:67,81:51,87,81:19,-1,281:4,-1,281:19,91,281:7,22,281:8,269,281,273,28" +
"1:3,-1:18,269:3,281,-1:54,81,-1:10,25,-1:73,20,-1:7,81:51,87,81:10,86,81:8," +
"-1,281:4,-1,281:26,26,281:9,269,281:5,-1:18,269:3,281,-1:5,94,-1:2,96,-1:14" +
",203,98,-1,221,-1:3,100,-1:5,245,-1:10,202,-1:27,281:4,-1,281:18,27,281:17," +
"269,281:5,-1:18,269:3,281,-1:3,220,-1:71,281,28,281:2,-1,281:36,269,281:5,-" +
"1:18,269:3,281,-1:4,102,-1:70,281,29,281:2,-1,281:36,269,281:5,-1:18,269:3," +
"281,-1:9,104,-1:65,281:4,-1,281:28,30,281:7,269,281:5,-1:18,269:3,281,-1:25" +
",204,-1:49,281:4,-1,281:19,31,281:16,269,281:5,-1:18,269:3,281,-1:29,108,-1" +
":45,281,32,281:2,-1,281:36,269,281:5,-1:18,269:3,281,-1:26,112,-1:48,281:4," +
"-1,281:36,269,281:2,33,281:2,-1:18,269:3,281,-1:10,114,-1:64,281:4,-1,281:2" +
"8,34,281:7,269,281:5,-1:18,269:3,281,-1:27,116,-1:47,281,35,281:2,-1,281:36" +
",269,281:5,-1:18,269:3,281,-1:4,120,-1:70,281,36,281:2,-1,281:36,269,281:5," +
"-1:18,269:3,281,-1:5,208,-1:69,281:4,90,281:36,269,281:5,-1:18,269:3,281,-1" +
":43,126,-1:31,281:4,-1,281:21,37,281:14,269,281:5,-1:18,269:3,281,-1:9,128," +
"-1:65,281:4,-1,281:19,38,281:16,269,281:5,-1:18,269:3,281,-1:4,45,-1:70,281" +
":4,-1,281:18,39,281:17,269,281:5,-1:18,269:3,281,-1:26,130,-1:48,281:4,-1,2" +
"81:22,40,281:13,269,281:5,-1:18,269:3,281,-1:30,132,-1:44,281:2,41,281,-1,2" +
"81:36,269,281:5,-1:18,269:3,281,-1:14,133,-1:28,265,-1:31,281:4,-1,281:22,4" +
"2,281:13,269,281:5,-1:18,269:3,281,-1:41,134,-1:33,281:4,92,281:36,269,281:" +
"5,-1:18,269:3,281,-1:28,136,-1:46,281:4,-1,281:28,43,281:7,269,281:5,-1:18," +
"269:3,281,-1:11,46,-1:63,281:4,-1,281:19,44,281:16,269,281:5,-1:18,269:3,28" +
"1,-1:9,138,-1:102,139,-1:62,140,-1:65,209,-1:52,47,-1:71,48,-1:102,141,-1:4" +
"2,143,-1:5,144,-1,145,146,-1:6,147,-1:8,148,-1:11,149,-1:53,49,-1:70,150,-1" +
":69,151,-1:51,153,-1:79,154,155,-1:2,212,-1,213,-1,156,157,-1:2,211,-1:8,15" +
"8,-1:74,159,-1:7,50,-1:63,160,-1:7,51,-1:63,161,-1:7,52,-1:71,53,-1:71,54,-" +
"1:63,162,-1:7,55,-1:63,163,-1:7,56,-1:68,165,-1:55,166,-1:65,224,-1:62,57,-" +
"1:74,215,-1:75,168,-1:86,214,-1:61,171,-1:63,173,-1:101,58,-1:71,59,-1:71,6" +
"0,-1:71,61,-1:71,62,-1:40,174,63,-1:4,175,-1:4,64,-1:8,176,-1:5,177,-1:43,1" +
"78,-1:86,216,-1:55,65,-1:94,66,-1:55,217,-1:73,218,-1:64,182,-1:98,183,-1:4" +
"5,184,-1:86,223,-1:47,185,-1:76,186,-1:104,67,-1:55,68,-1:78,188,-1:53,189," +
"-1:79,192,-1:67,225,-1:69,69,-1:92,70,-1:68,194,-1:70,195,-1:46,196,-1:98,7" +
"1,-1:51,197,-1:70,72,-1:68,198,-1:76,73,-1:83,200,-1:68,74,-1:72,75,-1:71,7" +
"6,-1:61,77,-1:74,78,-1:74,79,-1:74,80,-1:48,281,88,281:2,-1,281:36,269,281:" +
"5,-1:18,269:3,281,-1:4,205,-1:76,106,-1:88,118,-1:55,124,-1:88,207,-1:48,13" +
"1,-1:72,135,-1:103,152,-1:38,167,-1:86,172,-1:65,169,-1:75,170,-1:76,181,-1" +
":63,180,-1:83,187,-1:62,190,-1:73,191,-1:55,281:4,-1,281:28,93,281:7,269,28" +
"1:5,-1:18,269:3,281,-1:4,110,-1:76,206,-1:88,122,-1:50,193,-1:83,179,-1:71," +
"199,-1:57,281:4,-1,281:17,95,281:18,269,281:5,-1:18,269:3,281,-1:3,281:4,-1" +
",281:20,97,281:15,269,281:5,-1:18,269:3,281,-1:3,281:4,-1,281:20,99,281:15," +
"269,281:5,-1:18,269:3,281,-1:3,281:4,-1,281:18,101,281:17,269,281:5,-1:18,2" +
"69:3,281,-1:3,281:4,-1,281:20,103,281:15,269,281:5,-1:18,269:3,281,-1:3,281" +
":4,-1,281:28,105,281:7,269,281:5,-1:18,269:3,281,-1:3,281:4,-1,281:2,107,28" +
"1:33,269,281:5,-1:18,269:3,281,-1:3,281:4,-1,281:28,109,281:7,269,281:5,-1:" +
"18,269:3,281,-1:3,281:3,111,-1,281:36,269,281:5,-1:18,269:3,281,-1:3,281:4," +
"-1,281:18,113,281:17,269,281:5,-1:18,269:3,281,-1:3,281:4,-1,281:3,115,281:" +
"32,269,281:5,-1:18,269:3,281,-1:3,281:4,-1,281:3,117,281:32,269,281:5,-1:18" +
",269:3,281,-1:3,281:4,-1,281:20,119,281:15,269,281:5,-1:18,269:3,281,-1:3,2" +
"81:4,-1,281:19,121,281:16,269,281:5,-1:18,269:3,281,-1:3,281:4,-1,281:20,12" +
"3,281:15,269,281:5,-1:18,269:3,281,-1:3,281:3,125,-1,281:36,269,281:5,-1:18" +
",269:3,281,-1:3,281:4,-1,281:36,269,281:2,127,281:2,-1:18,269:3,281,-1:3,28" +
"1:4,-1,281:33,129,281:2,269,281:5,-1:18,269:3,281,-1:3,281:4,-1,281:2,219,2" +
"81:26,270,281:6,269,281:5,-1:18,269:3,281,-1:4,222,-1:104,210,-1:37,281:4,-" +
"1,281:3,226,281:17,227,281:14,269,281:5,-1:18,269:3,281,-1:3,281,277,281:2," +
"-1,281:33,228,281:2,269,281:5,-1:18,269:3,281,-1:3,281:3,229,-1,281,278,281" +
":34,269,281:5,-1:18,269:3,281,-1:3,281:4,-1,281,230,281:34,269,281:5,-1:18," +
"269:3,281,-1:3,281:4,-1,281:33,231,281:2,269,281:5,-1:18,269:3,281,-1:3,281" +
":4,-1,281:20,232,281:15,269,281:5,-1:18,269:3,281,-1:3,281:4,-1,281:2,233,2" +
"81:33,269,281:5,-1:18,269:3,281,-1:3,281:2,234,281,-1,281:36,269,281:5,-1:1" +
"8,269:3,281,-1:3,281:2,235,281,-1,281:36,269,281:5,-1:18,269:3,281,-1:3,281" +
":4,-1,281:17,236,281:18,269,281:5,-1:18,269:3,281,-1:3,281:4,-1,281,237,281" +
":34,269,281:5,-1:18,269:3,281,-1:3,281:4,-1,281:18,238,281:17,269,281:5,-1:" +
"18,269:3,281,-1:3,281:4,-1,281:20,239,281:15,269,281:5,-1:18,269:3,281,-1:3" +
",281:4,-1,281:2,240,281:33,269,281:5,-1:18,269:3,281,-1:3,281:2,241,281,-1," +
"281:36,269,281:5,-1:18,269:3,281,-1:3,281:4,-1,281:19,242,281:16,269,281:5," +
"-1:18,269:3,281,-1:3,281,243,281:2,-1,281:36,269,281:5,-1:18,269:3,281,-1:3" +
",281:4,-1,281:2,251,281:33,269,281:5,-1:18,269:3,281,-1:28,246,-1:46,281:4," +
"-1,281:21,252,281:14,269,281:5,-1:18,269:3,281,-1:3,281:4,-1,281:33,253,281" +
":2,269,281:5,-1:18,269:3,281,-1:3,281:2,254,281,-1,281:36,269,281:5,-1:18,2" +
"69:3,281,-1:3,281:4,-1,281:18,279,281:17,269,281:5,-1:18,269:3,281,-1:3,281" +
":4,-1,281:19,255,281:16,269,281:5,-1:18,269:3,281,-1:3,281:4,-1,281:18,256," +
"281:17,269,281:5,-1:18,269:3,281,-1:3,281:4,-1,281:34,257,281,269,281:5,-1:" +
"18,269:3,281,-1:3,281:4,-1,281:33,258,281:2,269,281:5,-1:18,269:3,281,-1:3," +
"281:4,-1,281:3,259,281:32,269,281:5,-1:18,269:3,281,-1:3,281:4,-1,281:36,26" +
"9,281:3,260,281,-1:18,269:3,281,-1:3,281:2,261,281,-1,281:36,269,281:5,-1:1" +
"8,269:3,281,-1:3,281:4,-1,281,280,281:34,269,281:5,-1:18,269:3,281,-1:3,281" +
",262,281:2,-1,281:36,269,281:5,-1:18,269:3,281,-1:3,281:4,-1,281:2,263,281:" +
"33,269,281:5,-1:18,269:3,281,-1:3,281,271,281:2,-1,281:36,269,281:5,-1:18,2" +
"69:3,281,-1:3,281,272,281:2,-1,281:36,269,281:5,-1:18,269:3,281,-1:3,281:4," +
"-1,281:18,274,281:17,269,281:5,-1:18,269:3,281,-1:3,281:4,-1,281:18,275,281" +
":17,269,281:5,-1:18,269:3,281,-1:3,281:4,-1,281:17,276,281:18,269,281:5,-1:" +
"18,269:3,281,-1:2");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

  return new Symbol(sym.EOF, new SymbolValue(yyline, yychar+1, "EOF"));
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -3:
						break;
					case 3:
						{return new Symbol(sym.DOT,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -4:
						break;
					case 4:
						{return new Symbol(sym.INT_LITERAL,new SymbolValue(yyline+1, yychar+1, yytext(), Integer.parseInt(yytext())));}
					case -5:
						break;
					case 5:
						{return new Symbol(sym.ASSIGN,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -6:
						break;
					case 6:
						{ System.err.println("Illegal character: "+yytext()); }
					case -7:
						break;
					case 7:
						{return new Symbol(sym.PLUS,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -8:
						break;
					case 8:
						{return new Symbol(sym.MINUS,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -9:
						break;
					case 9:
						{return new Symbol(sym.TIMES,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -10:
						break;
					case 10:
						{return new Symbol(sym.LPAREN,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -11:
						break;
					case 11:
						{return new Symbol(sym.RPAREN,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -12:
						break;
					case 12:
						{return new Symbol(sym.LBRACE,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -13:
						break;
					case 13:
						{return new Symbol(sym.RBRACE,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -14:
						break;
					case 14:
						{return new Symbol(sym.SEMI,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -15:
						break;
					case 15:
						{return new Symbol(sym.LT,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -16:
						break;
					case 16:
						{return new Symbol(sym.NOT,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -17:
						break;
					case 17:
						{return new Symbol(sym.COMMA,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -18:
						break;
					case 18:
						{return new Symbol(sym.LBRACKET,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -19:
						break;
					case 19:
						{return new Symbol(sym.RBRACKET,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -20:
						break;
					case 20:
						{/*reset pos to -1, if 0, otherwise line 1 starts at 0, rest start at 1 */ yychar=-1;}
					case -21:
						break;
					case 21:
						{ /* ignore white space. */ }
					case -22:
						break;
					case 22:
						{return new Symbol(sym.IF,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -23:
						break;
					case 23:
						{return new Symbol(sym.EQUAL,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -24:
						break;
					case 24:
						{return new Symbol(sym.AND,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -25:
						break;
					case 25:
						{ }
					case -26:
						break;
					case 26:
						{return new Symbol(sym.NEW,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -27:
						break;
					case 27:
						{return new Symbol(sym.INT,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -28:
						break;
					case 28:
						{return new Symbol(sym.ELSE,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -29:
						break;
					case 29:
						{return new Symbol(sym.TRUE,new SymbolValue(yyline+1, yychar+1, yytext(),1));}
					case -30:
						break;
					case 30:
						{return new Symbol(sym.THIS,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -31:
						break;
					case 31:
						{return new Symbol(sym.MAIN,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -32:
						break;
					case 32:
						{return new Symbol(sym.BYTE,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -33:
						break;
					case 33:
						{return new Symbol(sym.VOID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -34:
						break;
					case 34:
						{return new Symbol(sym.CLASS,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -35:
						break;
					case 35:
						{return new Symbol(sym.WHILE,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -36:
						break;
					case 36:
						{return new Symbol(sym.FALSE,new SymbolValue(yyline+1, yychar+1, yytext(),0));}
					case -37:
						break;
					case 37:
						{return new Symbol(sym.LENGTH,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -38:
						break;
					case 38:
						{return new Symbol(sym.RETURN,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -39:
						break;
					case 39:
						{return new Symbol(sym.IMPORT,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -40:
						break;
					case 40:
						{return new Symbol(sym.STATIC,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -41:
						break;
					case 41:
						{return new Symbol(sym.STRING,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -42:
						break;
					case 42:
						{return new Symbol(sym.PUBLIC,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -43:
						break;
					case 43:
						{return new Symbol(sym.EXTENDS,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -44:
						break;
					case 44:
						{return new Symbol(sym.BOOLEAN,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -45:
						break;
					case 45:
						{return new Symbol(sym.MEGGYTONE,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -46:
						break;
					case 46:
						{return new Symbol(sym.MEGGYCOLOR,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -47:
						break;
					case 47:
						{return new Symbol(sym.MEGGYDELAY,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -48:
						break;
					case 48:
						{return new Symbol(sym.MEGGY,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -49:
						break;
					case 49:
						{return new Symbol(sym.MEGGYBUTTON,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -50:
						break;
					case 50:
						{return new Symbol(sym.TONE_LITERAL,new SymbolValue(yyline+1, yychar+1, yytext(),61157));}
					case -51:
						break;
					case 51:
						{return new Symbol(sym.TONE_LITERAL,new SymbolValue(yyline+1, yychar+1, yytext(),36363));}
					case -52:
						break;
					case 52:
						{return new Symbol(sym.TONE_LITERAL,new SymbolValue(yyline+1, yychar+1, yytext(),40816));}
					case -53:
						break;
					case 53:
						{return new Symbol(sym.TONE_LITERAL,new SymbolValue(yyline+1, yychar+1, yytext(),48541));}
					case -54:
						break;
					case 54:
						{return new Symbol(sym.TONE_LITERAL,new SymbolValue(yyline+1, yychar+1, yytext(),32397));}
					case -55:
						break;
					case 55:
						{return new Symbol(sym.TONE_LITERAL,new SymbolValue(yyline+1, yychar+1, yytext(),54485));}
					case -56:
						break;
					case 56:
						{return new Symbol(sym.TONE_LITERAL,new SymbolValue(yyline+1, yychar+1, yytext(),45816));}
					case -57:
						break;
					case 57:
						{return new Symbol(sym.MEGGYGETPIXEL,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -58:
						break;
					case 58:
						{return new Symbol(sym.TONE_LITERAL,new SymbolValue(yyline+1, yychar+1, yytext(),57724));}
					case -59:
						break;
					case 59:
						{return new Symbol(sym.TONE_LITERAL,new SymbolValue(yyline+1, yychar+1, yytext(),34323));}
					case -60:
						break;
					case 60:
						{return new Symbol(sym.TONE_LITERAL,new SymbolValue(yyline+1, yychar+1, yytext(),38526));}
					case -61:
						break;
					case 61:
						{return new Symbol(sym.TONE_LITERAL,new SymbolValue(yyline+1, yychar+1, yytext(),51427));}
					case -62:
						break;
					case 62:
						{return new Symbol(sym.TONE_LITERAL,new SymbolValue(yyline+1, yychar+1, yytext(),43243));}
					case -63:
						break;
					case 63:
						{return new Symbol(sym.BUTTON_LITERAL,new SymbolValue(yyline+1, yychar+1, yytext(),2));}
					case -64:
						break;
					case 64:
						{return new Symbol(sym.BUTTON_LITERAL,new SymbolValue(yyline+1, yychar+1, yytext(),1));}
					case -65:
						break;
					case 65:
						{return new Symbol(sym.MEGGYSETPIXEL,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -66:
						break;
					case 66:
						{return new Symbol(sym.COLOR_LITERAL,new SymbolValue(yyline+1, yychar+1, yytext(),1));}
					case -67:
						break;
					case 67:
						{return new Symbol(sym.BUTTON_LITERAL,new SymbolValue(yyline+1, yychar+1, yytext(),4));}
					case -68:
						break;
					case 68:
						{return new Symbol(sym.MEGGYTONESTART,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -69:
						break;
					case 69:
						{return new Symbol(sym.COLOR_LITERAL,new SymbolValue(yyline+1, yychar+1, yytext(),5));}
					case -70:
						break;
					case 70:
						{return new Symbol(sym.COLOR_LITERAL,new SymbolValue(yyline+1, yychar+1, yytext(),0));}
					case -71:
						break;
					case 71:
						{return new Symbol(sym.MEGGYSETAUXLEDS,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -72:
						break;
					case 72:
						{return new Symbol(sym.COLOR_LITERAL,new SymbolValue(yyline+1, yychar+1, yytext(),4));}
					case -73:
						break;
					case 73:
						{return new Symbol(sym.COLOR_LITERAL,new SymbolValue(yyline+1, yychar+1, yytext(),7));}
					case -74:
						break;
					case 74:
						{return new Symbol(sym.BUTTON_LITERAL,new SymbolValue(yyline+1, yychar+1, yytext(),16));}
					case -75:
						break;
					case 75:
						{return new Symbol(sym.BUTTON_LITERAL,new SymbolValue(yyline+1, yychar+1, yytext(),8));}
					case -76:
						break;
					case 76:
						{return new Symbol(sym.MEGGYCHECKBUTTON,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -77:
						break;
					case 77:
						{return new Symbol(sym.COLOR_LITERAL,new SymbolValue(yyline+1, yychar+1, yytext(),2));}
					case -78:
						break;
					case 78:
						{return new Symbol(sym.COLOR_LITERAL,new SymbolValue(yyline+1, yychar+1, yytext(),3));}
					case -79:
						break;
					case 79:
						{return new Symbol(sym.COLOR_LITERAL,new SymbolValue(yyline+1, yychar+1, yytext(),6));}
					case -80:
						break;
					case 80:
						{return new Symbol(sym.BUTTON_LITERAL,new SymbolValue(yyline+1, yychar+1, yytext(),32));}
					case -81:
						break;
					case 82:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -82:
						break;
					case 83:
						{return new Symbol(sym.INT_LITERAL,new SymbolValue(yyline+1, yychar+1, yytext(), Integer.parseInt(yytext())));}
					case -83:
						break;
					case 84:
						{ System.err.println("Illegal character: "+yytext()); }
					case -84:
						break;
					case 85:
						{/*reset pos to -1, if 0, otherwise line 1 starts at 0, rest start at 1 */ yychar=-1;}
					case -85:
						break;
					case 86:
						{ }
					case -86:
						break;
					case 88:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -87:
						break;
					case 89:
						{ System.err.println("Illegal character: "+yytext()); }
					case -88:
						break;
					case 91:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -89:
						break;
					case 93:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -90:
						break;
					case 95:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -91:
						break;
					case 97:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -92:
						break;
					case 99:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -93:
						break;
					case 101:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -94:
						break;
					case 103:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -95:
						break;
					case 105:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -96:
						break;
					case 107:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -97:
						break;
					case 109:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -98:
						break;
					case 111:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -99:
						break;
					case 113:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -100:
						break;
					case 115:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -101:
						break;
					case 117:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -102:
						break;
					case 119:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -103:
						break;
					case 121:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -104:
						break;
					case 123:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -105:
						break;
					case 125:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -106:
						break;
					case 127:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -107:
						break;
					case 129:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -108:
						break;
					case 201:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -109:
						break;
					case 219:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -110:
						break;
					case 226:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -111:
						break;
					case 227:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -112:
						break;
					case 228:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -113:
						break;
					case 229:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -114:
						break;
					case 230:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -115:
						break;
					case 231:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -116:
						break;
					case 232:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -117:
						break;
					case 233:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -118:
						break;
					case 234:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -119:
						break;
					case 235:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -120:
						break;
					case 236:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -121:
						break;
					case 237:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -122:
						break;
					case 238:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -123:
						break;
					case 239:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -124:
						break;
					case 240:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -125:
						break;
					case 241:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -126:
						break;
					case 242:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -127:
						break;
					case 243:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -128:
						break;
					case 244:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -129:
						break;
					case 247:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -130:
						break;
					case 248:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -131:
						break;
					case 249:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -132:
						break;
					case 250:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -133:
						break;
					case 251:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -134:
						break;
					case 252:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -135:
						break;
					case 253:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -136:
						break;
					case 254:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -137:
						break;
					case 255:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -138:
						break;
					case 256:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -139:
						break;
					case 257:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -140:
						break;
					case 258:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -141:
						break;
					case 259:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -142:
						break;
					case 260:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -143:
						break;
					case 261:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -144:
						break;
					case 262:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -145:
						break;
					case 263:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -146:
						break;
					case 264:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -147:
						break;
					case 266:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -148:
						break;
					case 267:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -149:
						break;
					case 268:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -150:
						break;
					case 269:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -151:
						break;
					case 270:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -152:
						break;
					case 271:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -153:
						break;
					case 272:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -154:
						break;
					case 273:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -155:
						break;
					case 274:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -156:
						break;
					case 275:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -157:
						break;
					case 276:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -158:
						break;
					case 277:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -159:
						break;
					case 278:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -160:
						break;
					case 279:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -161:
						break;
					case 280:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -162:
						break;
					case 281:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -163:
						break;
					case 282:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -164:
						break;
					case 283:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -165:
						break;
					case 284:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -166:
						break;
					case 285:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -167:
						break;
					case 286:
						{return new Symbol(sym.ID,new SymbolValue(yyline+1, yychar+1, yytext()));}
					case -168:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
