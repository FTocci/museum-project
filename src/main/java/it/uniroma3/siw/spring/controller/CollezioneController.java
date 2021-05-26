package it.uniroma3.siw.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.spring.model.Collezione;
import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.service.CollezioneService;
import it.uniroma3.siw.spring.service.OperaService;

@Controller
public class CollezioneController {
	
	@Autowired
	private CollezioneService collezioneService;
	
	@Autowired
	private OperaService operaService;
		
    @Autowired
    private CollezioneValidator collezioneValidator;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    

    @RequestMapping(value="/addCollezione", method = RequestMethod.GET)
    public String addCollezione(Model model) {
    	logger.debug("addCollezione");
    	model.addAttribute("collezione", new Collezione());
        return "collezioneForm.html";
    }

    @RequestMapping(value = "/collezione/{id}", method = RequestMethod.GET)
    public String getCollezione(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("collezione", this.collezioneService.collezionePerId(id));
    	model.addAttribute("opera", new Opera());
    	model.addAttribute("opere",this.operaService.tutti());
    	model.addAttribute("opereCollezione",this.collezioneService.collezionePerId(id).getOpere());
    	return "collezione.html";
    }

    @RequestMapping(value = "/collezione", method = RequestMethod.GET)
    public String getCollezioni(Model model) {
    		model.addAttribute("collezioni", this.collezioneService.tutti());
    		return "collezioni.html";
    }
    
    @RequestMapping(value = "/collezione", method = RequestMethod.POST)
    public String newPersona(@ModelAttribute("collezione") Collezione collezione, 
    									Model model, BindingResult bindingResult) {
    	this.collezioneValidator.validate(collezione, bindingResult);
        if (!bindingResult.hasErrors()) {
        	this.collezioneService.inserisci(collezione);
            model.addAttribute("collezioni", this.collezioneService.tutti());
            return "collezioni.html";
        }
        return "collezioneForm.html";
    }
    
    @RequestMapping(value = "/addOperaACollezione/{id}", method = RequestMethod.POST)
    public String aggiungiOpera(@RequestParam("opera") Long idOpera, 
    									Model model, @PathVariable("id") Long idCollezione) {
    	
    	Opera o=operaService.operaPerId(idOpera);
    	Collezione c = this.collezioneService.collezionePerId(idCollezione);
    	o.setCollezione(c);
    	operaService.inserisci(o);
    	model.addAttribute("collezione", this.collezioneService.collezionePerId(idCollezione));
    	model.addAttribute("opera",new Opera());
    	model.addAttribute("opere",this.operaService.filtraLista(c.getOpere()));
    	model.addAttribute("opereCollezione",c.getOpere());
    	return "collezione.html";
    	
    }
    
    @RequestMapping(value = "/rimuoviOpera/{id}", method = RequestMethod.POST)
    public String rimuoviOpera(@RequestParam("opera") Long idOpera, 
    									Model model, @PathVariable("id") Long idCollezione) {
    	Collezione c = this.collezioneService.collezionePerId(idCollezione);
    	c.rimuoviOpera(operaService.operaPerId(idOpera));
    	collezioneService.inserisci(c);
    	model.addAttribute("collezione", this.collezioneService.collezionePerId(idCollezione));
    	model.addAttribute("opera",new Opera());
    	model.addAttribute("opere",this.operaService.filtraLista(c.getOpere()));
    	model.addAttribute("opereCollezione",c.getOpere());
    	return "collezione.html";
    }
    
}
