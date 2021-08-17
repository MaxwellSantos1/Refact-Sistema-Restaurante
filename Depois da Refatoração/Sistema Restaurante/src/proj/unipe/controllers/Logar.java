package proj.unipe.controllers;

import javax.servlet.http.HttpSession;

import proj.unipe.entities.Usuario;

public interface Logar {

	String entrar(Usuario usuario, HttpSession sessao);
}