package com.testConnection;

import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.sun.deploy.uitoolkit.impl.fx.ui.UITextArea;

import Services.*;
import Utils.*;
import entity.*;

public class TestConnection {

	public static void main(String[] args){
				
		UtilizatorService.generateAccounts();
		
	}
	
}