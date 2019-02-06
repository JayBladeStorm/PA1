#../PA1Start/src/mjparser/sym.java
	# f=open("../pa1start/TestCases/resrv.in","r",encoding="utf-8")
# cmnt.in		errcmnt.in	errid.in	funny.in	id.in		plus.in		regress.sh	resrv.in.OK
# cmnt.in.OK	errcmnt.in.OK	errid.in.OK	funny.in.OK	id.in.OK	plus.in.OK	resrv.in	t
#test

# for the sym.java
# test for git
num2token={}
token2num={}
test_case_category="../pa1start/TestCases/"
def read_sym():
	f=open("../pa1start/src/mjparser/sym.java","r",encoding="utf-8")
	should_start=False
	for line in f:
		if "}" in line:
			break
		if "terminals" in line:
			should_start=True
		elif should_start:
			deal_sym(line)
	f.close()

def deal_sym(string):
	string=string.split(" ")
	num2token[string[6]]=string[8].split(";")[0]# store the value into dict
	token2num[string[8].split(";")[0]]=string[6]
	print(string[6],num2token[string[6]])

# for the testcase input:
# analysis all the key word
def read_testcase(file_name,f):
	file_name=open(test_case_category+file_name,"r",encoding="utf-8")
	for line in file_name:
		f(line)

keyword_dict={}
def find_key_word(string):
	string=string.split(" ")
	value=string[11][:-2]
	if value=='-1':# is matching -1
		num=string[1][1:]
		key_word=string[5][1:]
		keyword_dict[key_word]=num



keyword2token={}
keyword2value={}

def find_key_word_from_rule():
	file=open("test.txt","r",encoding="utf-8")
	for line in file:
		string=line.split(" ")
		keyword2token[string[0]]=string[1]
		keyword2value[string[0]]=string[2][:-1]#remove \n
		# print(string[0]," ",keyword2token[string[0]])
		# print(string[0]," ",keyword2value[string[0]])

def keyword_generate(key,token,value):
	# if(value=='-1'):
	# 	return "\"{0}\"(\" \"|{{EOF}}) {{return new Symbol(sym.{1},new SymbolValue(yyline+1, yychar+1, yytext()));}}".format(key,token)
	# else:
	# 	return "\"{0}\"(\" \"|{{EOF}}) {{return new Symbol(sym.{1},new SymbolValue(yyline+1, yychar+1, yytext(),{2}));}}".format(key,token,value)

	if(value=='-1' or value=='-'):
		return "\"{0}\"         {{return new Symbol(sym.{1},new SymbolValue(yyline+1, yychar+1, yytext()));}}".format(key,token)
	else:
		return "\"{0}\"         {{return new Symbol(sym.{1},new SymbolValue(yyline+1, yychar+1, yytext(),{2}));}}".format(key,token,value)
def comparator(value):
	return len(value)
def generate_expression_for_lex_key_word():
	keys=list(keyword2token.keys())
	# keys.sort(key=comparator,reverse=True)
	for k in keys:
		print(keyword_generate(k,keyword2token[k],keyword2value[k]))



def main():
	find_key_word_from_rule()
	generate_expression_for_lex_key_word()
	# read_testcase("resrv.in.OK",find_key_word)





if __name__=='__main__':
	main()