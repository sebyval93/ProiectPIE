package com.testConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import Services.*;
import Utils.Functions;
import entity.*;

public class TestConnection {

	public static void main(String[] args){
		Saptamana week = SaptamanaService.getSaptamanaByID(1);
		System.out.println(week.getId());
		System.out.println(week.toString());
		week = Functions.GetTodayWeek();
		System.out.println(week);
		//AnUniversitarService.addAn(5);
		//ProfesorService.addProfesor("Mirica dd");
		
	}
	
}
