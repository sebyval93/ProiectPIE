package com.testConnection;

import Services.*;
import entity.*;

public class TestConnection {

	public static void main(String[] args){
				
		Saptamana week = SaptamanaService.getSaptamanaByID(2);
		System.out.println(week.getId());
		
	}
	
}