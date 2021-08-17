package proj.unipe.services;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import proj.unipe.controllers.ReservaControllerProduct;
import proj.unipe.entities.Reserva;

@Controller
@RequestMapping(value="reserva")
public class ReservaController {

	public ReservaControllerProduct reservaControllerProduct = new ReservaControllerProduct();

	@Autowired
	public ReservaService reservaService;
	
	@RequestMapping(method = RequestMethod.GET, value = {"listar"})
	public String list(ModelMap map) {
		
		List<Reserva> reservas = reservaService.listar();
		map.addAttribute("reservas", reservas);
		map.addAttribute("selectMesas", reservaControllerProduct.selectMesas());
		map.addAttribute("filtro", new Reserva());
		return "/reserva/listar";
		
	}
	
	
	public Map<Long, String> selectMesas(){
		return reservaControllerProduct.selectMesas();
	}
	
	

	@RequestMapping(method = RequestMethod.GET, value = {"filtrar"})
	public String filter(@ModelAttribute("filtro") Reserva filtro, ModelMap map) {
		return filtro.filter(this, map);
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = {"novo"})
	public String insertForm(ModelMap map) {
		return reservaControllerProduct.insertForm(map);
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = {"salvar"})
	public String save(@ModelAttribute("reserva") @Valid Reserva reserva, BindingResult result, ModelMap map) {
		if(result.hasErrors()){
			map.addAttribute("reserva", reserva);
			map.addAttribute("selectMesas", reservaControllerProduct.selectMesas());
			return "reserva/novo";
		}
		try {
			
			if(reserva.getId() != null && reserva.getId() != 0){
				reservaService.atualizar(reserva);
			}else{
				reservaService.inserir(reserva);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/reserva/listar";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = {"/{id}/updateForm"})
	public String updateForm(@PathVariable("id") Long id, ModelMap map) {
		Reserva reserva = reservaService.buscarPorId(id);
		map.addAttribute("reserva", reserva );
		map.addAttribute("selectMesas", reservaControllerProduct.selectMesas());
		return "/reserva/novo";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
