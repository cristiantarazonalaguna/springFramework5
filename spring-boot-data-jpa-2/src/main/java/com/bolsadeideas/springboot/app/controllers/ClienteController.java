package com.bolsadeideas.springboot.app.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.models.dao.IClienteDao;
import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.service.IClienteService;
import com.bolsadeideas.springboot.app.util.paginator.PageRender;

@Controller
@SessionAttributes("Cliente")
public class ClienteController {

	@Autowired
	//@Qualifier("clienteDaoJPA")//se usa para identificar el repositorio que se usara
	private IClienteService clienteService;
	
	@RequestMapping(value="/listar",method=RequestMethod.GET)
	public String listar(@RequestParam(name="page",defaultValue="0") int page, Model model) {
		
		Pageable pageRequest = PageRequest.of(page, 4);
		
		Page<Cliente> clientes = clienteService.findAll(pageRequest);
		
		PageRender<Cliente> pagerender = new PageRender<Cliente>("/listar", clientes);
		model.addAttribute("page",pagerender);
		model.addAttribute("titulo","Listado de clientes");
		model.addAttribute("clientes",clientes);
		return "listar";
	}
	
	@RequestMapping(value="/form")
	public String crear(Map<String,Object> model) {
		Cliente cliente=new Cliente();
		model.put("cliente", cliente);
		model.put("titulo", "Formulario de clientes");
		return "form";
	}
	
	@RequestMapping(value="/form/{id}")
	public String edit(@PathVariable(value="id") Long id, Map<String, Object> model) {
		Cliente cliente=null;
		if(id>0) {
			cliente= clienteService.findOne(id);
		}else {
			return "redirect:/listar";
		}
		 model.put("cliente", cliente);
		 model.put("titulo", "Editar Cliente");
		 return "form";
	}
	
	@RequestMapping(value="/form",method=RequestMethod.POST)
	public String guardar(Cliente cliente, BindingResult result, Model model,RedirectAttributes flash, SessionStatus status) {
		if(result.hasErrors()){
			model.addAttribute("titulo","Formulario de Cliente");
			return "form";
		}
		clienteService.save(cliente);
		//con esto eliminamos la session del cliente que se uso para el editar se declara en la parte inicial como: @SessionAttributes("Cliente") es aca donde se crea la session
		
		status.setComplete();
		flash.addFlashAttribute("success","Cliente Creado con exito");
		return "redirect:listar";
	}
	@RequestMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable(value="id")Long id,RedirectAttributes flash){
		if(id>0){
			clienteService.delete(id);
			flash.addFlashAttribute("success","Cliente eliminado con exito");
		}
		return "redirect:/listar";
	}
	
	
}
