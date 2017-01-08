package com.testConnection;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.JOptionPane;

import org.hibernate.Session;
import org.hibernate.engine.jdbc.connections.internal.UserSuppliedConnectionProviderImpl;

import com.sun.deploy.uitoolkit.impl.fx.ui.UITextArea;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import Services.*;
import Singleton.Singleton;
import Utils.*;
import antlr.Utils;
import entity.*;
import gui_system.ModalFrame;
import gui_system.NewPasswordModal;
import gui_system.ResetWeeksModal;
import gui_system.ResetWeeksPanel;

public class TestConnection {

	public static void main(String[] args) throws FileNotFoundException{

			
		PrezentaService.resetPrezentaSeq();
		Modul modul = ModulService.getModulByID(24);
		Saptamana sapt = SaptamanaService.getSaptamanaByID(1);
		Student s = StudentService.getStudentByID(22);
		System.out.println(PrezentaService.addPrezenta(modul, sapt, s, 0));
			
			
		
	}
	
}