package com.destkbo.ppmtool.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.destkbo.ppmtool.domain.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project ,Long>{
	
	Project findByProjectIdentifier(String projectId);

	@Override
    Iterable<Project> findAll();
	
	Iterable<Project> findAllByprojectLeader(String username);
	

}
