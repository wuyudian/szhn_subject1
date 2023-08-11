package com.wuyudian;

import org.junit.jupiter.api.Test;

public class test {

	@Test
	void contextLoads() { 
		boolean string = isValid("{}");
		System.out.println("arbitrary text");
	}
	
	public boolean isValid(String s) {
        boolean f = false;
        char[] strArray = s.toCharArray();
        for(int i=0; i<s.length();i++) {
            String a = String.valueOf(strArray[i]);
            String bString = s.substring(i+1,s.length());
            boolean b = bString.contains("}");
            boolean c = a.equals("{");
            if(a.equals("(") && s.substring(i+1,s.length()+1).contains(")")) {
                f = true;
            } else if(a.equals("[") && s.substring(i+1,s.length()+1).contains("]")) {
                f = true;
            } else if(a.equals("{") && s.substring(i+1,s.length()).contains("}")) {
                f = true;
            } else {
                f = false;
            }
        }
        return f;
    }
}
