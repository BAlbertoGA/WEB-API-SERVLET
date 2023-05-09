package com.alura.gerenciador.servlet;

import java.io.IOException;

import com.alura.gerenciador.accion.Accion;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class ControladorFilter implements Filter {

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		
		System.out.println("Controlador");
		
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String paramAccion = request.getParameter("accion");
		
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
