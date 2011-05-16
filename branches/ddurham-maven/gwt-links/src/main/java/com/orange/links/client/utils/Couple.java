package com.orange.links.client.utils;

public class Couple<K,V> {

	private K o1;
	private V o2;
	
	public Couple(K o1, V o2){
		setCouple(o1,o2);
	}
	
	public void setCouple(K o1, V o2){
		this.o1 = o1;
		this.o2 = o2;
	}
	
	public K getFirstArg(){
		return o1;
	}
	
	public V getSecondArg(){
		return o2;
	}
}
