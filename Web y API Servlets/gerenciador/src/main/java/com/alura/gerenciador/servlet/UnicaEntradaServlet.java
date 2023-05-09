package com.alura.gerenciador.servlet;

import java.io.IOException;

import com.alura.gerenciador.accion.Accion;
import com.alura.gerenciador.accion.EliminarEmpresa;
import com.alura.gerenciador.accion.ListaEmpresas;
import com.alura.gerenciador.accion.ModificarEmpresa;
import com.alura.gerenciador.accion.MostrarEmpresa;
import com.alura.gerenciador.accion.NuevaEmpresa;
import com.alura.gerenciador.accion.NuevaEmpresaForm;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//@WebServlet("/entrada")
public class UnicaEntradaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String paramAccion = request.getParameter("accion");
//		HttpSession sesion = request.getSession();
//		
//		
//		Boolean usuarioNoLogado = (sesion.getAttribute("loginUsuario") == null);
//		Boolean accionProtegida = !(paramAccion.equals("Login") || paramAccion.equals("LoginForm"));
//		
//		if (usuarioNoLogado && accionProtegida) {
//			response.sendRedirect("entrada?accion=LoginForm");
//			return;
//		}
		
		
		
		String nombreClase = "com.alura.gerenciador.accion."+paramAccion;
		String nombre;
		
		try {
			Class clase = Class.forName(nombreClase);
			Accion accion = (Accion) clase.newInstance();
			nombre = accion.ejecutar(request, response);
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
		
		String[] tipoDireccion = nombre.split(":");
		
		
		if (tipoDireccion[0].equals("forward") ) {
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/view/"+tipoDireccion[1]);
			rd.forward(request, response);
		} else {
			response.sendRedirect(tipoDireccion[1]);
		}
		
	}

}
