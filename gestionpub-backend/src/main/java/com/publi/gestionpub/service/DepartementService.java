package com.publi.gestionpub.service;



import java.util.List;

import com.publi.gestionpub.Dto.DepartementDto;



public interface DepartementService {
	public DepartementDto addDepartement(DepartementDto departementDto);
	public  DepartementDto modifierdepartement(Long id ,DepartementDto departementDto);
	DepartementDto getOne(Long id);
	List<DepartementDto>affichertous();

}
