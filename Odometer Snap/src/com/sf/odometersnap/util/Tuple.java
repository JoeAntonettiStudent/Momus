package com.sf.odometersnap.util;

/*
 * Class Tuple - Parameterized class for a tuple object. This class is a general utility class, as Java does not have a tuple by default.
 */

public class Tuple<A, B> {
	
	public final A first;
	public final B second;
	
	public Tuple(A a, B b){
		this.first = a;
		this.second = b;
	}

}
