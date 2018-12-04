package com.bolsadeideas.springboot.app.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageRender<T> {

	private String url;
	private Page<T> page;
	
	private int TotalPaginas;
	
	private int numElemnetosPorPagina;
	
	private int paginaActual;
	
	private List<PageItem> paginas;
	
	public PageRender(String url, Page<T> page) {
		
		this.url = url;
		this.page = page;
		this.paginas=new ArrayList<PageItem>();
		
		numElemnetosPorPagina=page.getSize();
		TotalPaginas=page.getTotalPages();
		paginaActual=page.getNumber()+1;
		
		int desde,hasta;
		if(TotalPaginas <=numElemnetosPorPagina) {
			desde=1;
			hasta=TotalPaginas;
		}else {
			if(paginaActual <= numElemnetosPorPagina/2) {
				desde=1;
				hasta=numElemnetosPorPagina;
			}else if(paginaActual >= TotalPaginas - numElemnetosPorPagina/2){
				desde=TotalPaginas - numElemnetosPorPagina +1;
				hasta=numElemnetosPorPagina;
			}else{
				desde=paginaActual-numElemnetosPorPagina/2;
				hasta=numElemnetosPorPagina;
			}
		}
		
		for(int i=0; i< hasta; i++) {
			paginas.add(new PageItem(desde+i, paginaActual == desde+i));
		}
	}

	public String getUrl() {
		return url;
	}

	public int getTotalPaginas() {
		return TotalPaginas;
	}

	public int getPaginaActual() {
		return paginaActual;
	}

	public List<PageItem> getPaginas() {
		return paginas;
	}
	
	public boolean isFirst() {
		return page.isFirst();
	}
	
	public boolean isLast() {
		return page.isLast();
	}
	
	public boolean isHasNext() {
		return page.hasNext();
	}
	
	public boolean isHasPrevious() {
		return page.hasPrevious();
	}
}
