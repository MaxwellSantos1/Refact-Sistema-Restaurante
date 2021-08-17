package proj.unipe.controllers;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import proj.unipe.entities.Mesa;
import proj.unipe.entities.Reserva;
import proj.unipe.services.MesaService;

public class ReservaControllerProduct {
	private MesaService mesaService;

	public Map<Long, String> selectMesas() {
		List<Mesa> mesas = mesaService.listar();
		Map<Long, String> mapa = new HashMap<Long, String>();
		mapa.put(0L, "selecione");
		for (Mesa mesa : mesas) {
			mapa.put(mesa.getId(), mesa.getNumero());
		}
		return mapa;
	}

	@RequestMapping(method = RequestMethod.GET, value = { "novo" })
	public String insertForm(ModelMap map) {
		Reserva reserva = new Reserva();
		reserva.setMesa(new Mesa());
		map.addAttribute("reserva", reserva);
		map.addAttribute("selectMesas", selectMesas());
		return "/reserva/novo";
	}
}